package com.github.ysbbbbbb.kaleidoscopetavern.block.plant;

import com.github.ysbbbbbb.kaleidoscopetavern.init.ModItems;
import com.github.ysbbbbbb.kaleidoscopetavern.util.event.EventHooks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ScheduledTickAccess;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jspecify.annotations.NonNull;

import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;

import static com.github.ysbbbbbb.kaleidoscopetavern.util.PortHelper.getSlotForHand;

public class GrapeCropBlock extends Block implements BonemealableBlock, SimpleWaterloggedBlock {
    public static final IntegerProperty AGE = BlockStateProperties.AGE_5;
    public static final int MAX_AGE = BlockStateProperties.MAX_AGE_5;
    public static final VoxelShape SHAPE = Block.box(2, 6, 2, 14, 16, 14);
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    private final GrowPerTickProbability probability;
    private final Supplier<ItemStack> shearResult;

    public GrapeCropBlock(Properties properties, GrowPerTickProbability probability, Supplier<ItemStack> shearResult) {
        super(properties
                .mapColor(MapColor.PLANT)
                .noOcclusion()
                .randomTicks()
                .instabreak()
                .sound(SoundType.CROP)
                .offsetType(OffsetType.XYZ)
                .pushReaction(PushReaction.POPPED));
        this.registerDefaultState(
                this.stateDefinition.any()
                        .setValue(AGE, 0)
                        .setValue(WATERLOGGED, false));
        this.probability = probability;
        this.shearResult = shearResult;
    }

    @Deprecated(since = "1.1.0")
    public GrapeCropBlock(Properties properties) {
        this(
                properties,
                (_, _, _, _) -> 0.25F,
                () -> new ItemStack(ModItems.GRAPE, 3)
        );
    }

    @Override
    public @NotNull FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    @Override
    public @Nullable BlockState getStateForPlacement(@NotNull BlockPlaceContext context) {
        BlockState stateForPlacement = super.getStateForPlacement(context);
        if (stateForPlacement == null)
            return null;
        boolean hasWater = context.getLevel().getFluidState(context.getClickedPos()).getType() == Fluids.WATER;
        return stateForPlacement.setValue(WATERLOGGED, hasWater);
    }

    @Override
    public @NotNull InteractionResult useItemOn(@NonNull ItemStack stack, @NonNull BlockState state, @NonNull Level level, @NonNull BlockPos pos, Player player, @NonNull InteractionHand hand, @NonNull BlockHitResult hitResult) {
        // 只有成熟的葡萄才可以被剪刀收获
        ItemStack heldItem = player.getItemInHand(hand);
        if (heldItem.is(Items.SHEARS) && isMaxAge(state)) {
            level.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
            Block.popResource(level, pos, this.shearResult.get());

            // 有 30% 强制额外掉落 1-2 青提葡萄
            if (level.getRandom().nextFloat() < 0.3F) {
                int count = level.getRandom().nextInt(1, 3);
                Block.popResource(level, pos, new ItemStack(ModItems.GREEN_GRAPE, count));
            }

            heldItem.hurtAndBreak(1, player, getSlotForHand(hand));
            player.playSound(SoundEvents.BEEHIVE_SHEAR);
            return InteractionResult.SUCCESS;
        }
        return super.useItemOn(stack, state, level, pos, player, hand, hitResult);
    }

    @Override
    public boolean isRandomlyTicking(@NonNull BlockState state) {
        return super.isRandomlyTicking(state) && state.getValue(AGE) < MAX_AGE;
    }

    @Override
    public void randomTick(@NonNull BlockState state, @NonNull ServerLevel level, @NonNull BlockPos pos, RandomSource random) {
        if (EventHooks.onCropsGrowPre(level, pos, state, random.nextDouble() < this.probability.getProbability(state, level, pos, random))) {
            int nextAge = state.getValue(AGE) + random.nextInt(1, 3);
            level.setBlockAndUpdate(pos, state.setValue(AGE, Math.min(nextAge, MAX_AGE)));
            EventHooks.onCropsGrowPost(level, pos, state);
        }
    }

    @Override
    protected @NonNull BlockState updateShape(BlockState blockState, @NonNull LevelReader levelReader, @NonNull ScheduledTickAccess scheduledTickAccess, @NonNull BlockPos blockPos, @NonNull Direction direction, @NonNull BlockPos blockPos2, @NonNull BlockState blockState2, @NonNull RandomSource randomSource) {
        if (blockState.getValue(WATERLOGGED)) {
            scheduledTickAccess.scheduleTick(blockPos, Fluids.WATER, Fluids.WATER.getTickDelay(levelReader));
        }
        if (blockState.canSurvive(levelReader, blockPos)) {
            return super.updateShape(blockState, levelReader, scheduledTickAccess, blockPos, direction, blockPos2, blockState2, randomSource);
        }
        return Blocks.AIR.defaultBlockState();
    }

    @Override
    public boolean canSurvive(@NonNull BlockState state, LevelReader level, BlockPos pos) {
        // 上方必须是葡萄藤架
        var aboveState = level.getBlockState(pos.above());
        if (aboveState.getBlock() instanceof GrapevineTrellisBlock trellis) {
            return trellis.isMaxAge(aboveState);
        }
        return false;
    }

    public boolean isMaxAge(BlockState state) {
        return state.getValue(AGE) >= MAX_AGE;
    }

    @Override
    public boolean isValidBonemealTarget(
            @NonNull LevelReader level,
            @NonNull BlockPos pos,
            @NonNull BlockState state,
            @NonNull BonemealSource source
    ) {
        return !this.isMaxAge(state);
    }

    @Override
    public boolean isBonemealSuccess(
            @NonNull Level level,
            @NonNull RandomSource random,
            @NonNull BlockPos pos,
            @NonNull BlockState state,
            @NonNull BonemealSource source
    ) {
        return true;
    }

    @Override
    public void performBonemeal(
            @NonNull ServerLevel level,
            @NonNull RandomSource random,
            @NonNull BlockPos pos,
            @NonNull BlockState state,
            @NonNull BonemealSource source) {
        int newAge = Math.min(state.getValue(AGE) + random.nextInt(1, 3), MAX_AGE);
        level.setBlockAndUpdate(pos, state.setValue(AGE, newAge));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(AGE, WATERLOGGED);
    }

    @Override
    public @NotNull VoxelShape getShape(@NonNull BlockState pState, @NonNull BlockGetter pLevel, @NonNull BlockPos pPos, @NonNull CollisionContext pContext) {
        return SHAPE;
    }

    @Override
    public @NotNull List<ItemStack> getDrops(@NonNull BlockState state, LootParams.@NonNull Builder lootParamsBuilder) {
        // 只有成熟的葡萄才会掉落物品
        if (isMaxAge(state)) {
            return super.getDrops(state, lootParamsBuilder);
        }
        return Collections.emptyList();
    }

    @Override
    protected @NonNull ItemStack getCloneItemStack(@NonNull LevelReader levelReader, @NonNull BlockPos blockPos, @NonNull BlockState blockState, boolean bl) {
        return ModItems.GRAPE.getDefaultInstance();
    }
}
