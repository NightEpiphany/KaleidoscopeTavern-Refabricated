package com.github.ysbbbbbb.kaleidoscopetavern.block.mixology;

import com.github.ysbbbbbb.kaleidoscopetavern.blockentity.mixology.ShakerBlockEntity;
import com.github.ysbbbbbb.kaleidoscopetavern.init.ModItems;
import com.github.ysbbbbbb.kaleidoscopetavern.item.ShakerItem;
import com.github.ysbbbbbb.kaleidoscopetavern.util.ItemUtils;
import com.github.ysbbbbbb.kaleidoscopetavern.util.neo.ItemStackHandler;
import com.google.common.collect.Lists;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ScheduledTickAccess;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jspecify.annotations.NonNull;

import java.util.List;

import static com.github.ysbbbbbb.kaleidoscopetavern.init.tag.TagMod.COCKTAIL_INGREDIENT;

public class ShakerBlock extends Block implements EntityBlock, SimpleWaterloggedBlock {
    private static final VoxelShape SHAPE = Block.box(4, 0, 4, 12, 16, 12);
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    public ShakerBlock(Properties properties) {
        super(properties
                .noOcclusion()
                .instabreak()
                .pushReaction(PushReaction.POPPED)
                .sound(SoundType.LANTERN)
        );
        this.registerDefaultState(
                this.stateDefinition.any()
                        .setValue(WATERLOGGED, false)
        );
    }

    @Deprecated
    public ShakerBlock() {
        this(Properties.of());
    }

    @Override
    public @NotNull FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    @Override
    public boolean canSurvive(@NotNull BlockState blockState, LevelReader levelReader, BlockPos blockPos) {
        BlockPos blockPosBelow = blockPos.below();
        BlockState blockStateBelow = levelReader.getBlockState(blockPosBelow);
        return this.canSurviveOn(levelReader, blockPosBelow, blockStateBelow);
    }

    private boolean canSurviveOn(BlockGetter blockGetter, BlockPos blockPos, BlockState blockState) {
        return blockState.isFaceSturdy(blockGetter, blockPos, Direction.UP);
    }

    @Override
    protected @NonNull BlockState updateShape(
            @NonNull BlockState state,
            @NonNull LevelReader level,
            @NonNull ScheduledTickAccess ticks,
            @NonNull BlockPos pos,
            @NonNull Direction directionToNeighbour,
            @NonNull BlockPos neighbourPos,
            @NonNull BlockState neighbourState,
            @NonNull RandomSource random
    ) {
        if (state.getValue(WATERLOGGED)) {
            ticks.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
        }
        return !state.canSurvive(level, pos) ? Blocks.AIR.defaultBlockState() : super.updateShape(state, level, ticks, pos, directionToNeighbour, neighbourPos, neighbourState, random);
    }

    @Override
    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        boolean hasWater = context.getLevel().getFluidState(context.getClickedPos()).getType() == Fluids.WATER;
        return this.defaultBlockState().setValue(WATERLOGGED, hasWater);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.@NonNull Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(WATERLOGGED);
    }

    @Override
    protected @NotNull InteractionResult useItemOn(@NonNull ItemStack stack, @NonNull BlockState state, @NonNull Level level, @NonNull BlockPos pos,
                                                   @NonNull Player player, @NonNull InteractionHand hand, @NonNull BlockHitResult hitResult) {
        if (hand != InteractionHand.MAIN_HAND) {
            return InteractionResult.PASS;
        }
        if (!(level.getBlockEntity(pos) instanceof ShakerBlockEntity shaker)) {
            return InteractionResult.PASS;
        }

        ItemStack itemInHand = player.getItemInHand(hand);
        // 绌烘墜鍙栦笅
        if (itemInHand.isEmpty() && level instanceof ServerLevel serverLevel) {
            getDrops(state, serverLevel, pos, shaker)
                    .forEach(s -> ItemUtils.giveItemToPlayer(player, s));
            level.setBlock(pos, Blocks.AIR.defaultBlockState(), Block.UPDATE_SUPPRESS_DROPS | Block.UPDATE_ALL);
            level.playSound(null, pos, SoundEvents.LANTERN_BREAK, SoundSource.BLOCKS);
            return InteractionResult.SUCCESS;
        }

        if (itemInHand.is(COCKTAIL_INGREDIENT)) {
            shaker.addIngredient(itemInHand, player);
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.PASS;
    }

    @Override
    public void setPlacedBy(@NonNull Level pLevel, @NonNull BlockPos pPos, @NonNull BlockState pState, @Nullable LivingEntity placer, @NonNull ItemStack pStack) {
        if (ShakerItem.hasStorage(pStack) && pLevel.getBlockEntity(pPos) instanceof ShakerBlockEntity shaker) {
            ItemStackHandler storage = ShakerItem.getStorage(pStack);
            shaker.setStorage(storage);
            if (ShakerItem.hasResult(pStack)) {
                shaker.setResult(ShakerItem.getResult(pStack));
            }
            shaker.refresh();
        }
    }

    @Override
    public @NotNull List<ItemStack> getDrops(@NonNull BlockState state, LootParams.@NonNull Builder params) {
        List<ItemStack> stacks = Lists.newArrayList(super.getDrops(state, params));
        BlockEntity blockEntity = params.getOptionalParameter(LootContextParams.BLOCK_ENTITY);
        if (blockEntity instanceof ShakerBlockEntity be) {
            ItemStack instance = ModItems.SHAKER.getDefaultInstance();
            ItemStackHandler storage = be.getStorage();
            ShakerItem.setStorage(instance, storage);
            if (!be.getResult().isEmpty()) {
                ShakerItem.setResult(instance, be.getResult());
            }
            stacks.add(instance);
        }
        return stacks;
    }

    @Override
    @Nullable
    public BlockEntity newBlockEntity(@NonNull BlockPos pPos, @NonNull BlockState pState) {
        return new ShakerBlockEntity(pPos, pState);
    }

    @Override
    public @NotNull VoxelShape getShape(@NonNull BlockState pState, @NonNull BlockGetter pLevel, @NonNull BlockPos pPos, @NonNull CollisionContext pContext) {
        return SHAPE;
    }
}
