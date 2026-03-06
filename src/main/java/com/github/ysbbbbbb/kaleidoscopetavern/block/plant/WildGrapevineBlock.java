package com.github.ysbbbbbb.kaleidoscopetavern.block.plant;

import com.github.ysbbbbbb.kaleidoscopetavern.init.ModBlocks;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.GrowingPlantHeadBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.NonNull;

import static com.github.ysbbbbbb.kaleidoscopetavern.util.PortHelper.getSlotForHand;

public class WildGrapevineBlock extends GrowingPlantHeadBlock implements BonemealableBlock {
    public static final MapCodec<WildGrapevineBlock> CODEC = simpleCodec(WildGrapevineBlock::new);
    /**
     * 被剪刀修剪过后，无法再随机生长了，直到被重新种植
     */
    public static BooleanProperty SHEARED = BooleanProperty.create("sheared");

    private static final VoxelShape SHAPE = Block.box(1, 0, 1, 15, 16, 15);

    public WildGrapevineBlock(Properties properties) {
        super(properties.mapColor(MapColor.PLANT)
                .randomTicks()
                .noCollision()
                .instabreak()
                .sound(SoundType.CAVE_VINES)
                .pushReaction(PushReaction.DESTROY), Direction.DOWN, SHAPE, false, 0.15);
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(AGE, 0)
                .setValue(SHEARED, false));
    }

    @Override
    public @NonNull InteractionResult useItemOn(ItemStack stack, @NonNull BlockState state, @NonNull Level level, @NonNull BlockPos pos,
                                                @NonNull Player player, @NonNull InteractionHand hand, @NonNull BlockHitResult hitResult) {
        if (stack.is(Items.SHEARS)) {
            if (state.getValue(SHEARED)) {
                return InteractionResult.CONSUME;
            } else {
                level.setBlockAndUpdate(pos, state.setValue(SHEARED, true));
                stack.hurtAndBreak(1, player, getSlotForHand(hand));
                player.playSound(SoundEvents.SHEEP_SHEAR);
                return InteractionResult.SUCCESS;
            }
        }
        return super.useItemOn(stack, state, level, pos, player, hand, hitResult);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.@NonNull Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(SHEARED);
    }

    @Override
    public boolean canSurvive(@NonNull BlockState state, LevelReader level, BlockPos pos) {
        BlockPos relative = pos.relative(this.growthDirection.getOpposite());
        BlockState relativeState = level.getBlockState(relative);
        return relativeState.is(this.getHeadBlock())
               || relativeState.is(this.getBodyBlock())
               || this.canAttachTo(relativeState)
               || relativeState.isFaceSturdy(level, relative, this.growthDirection);
    }

    @Override
    protected boolean canAttachTo(BlockState state) {
        // 树叶不属于 SupportType.FULL，故需要特殊判断一下
        return state.is(BlockTags.LEAVES);
    }

    @Override
    protected @NotNull MapCodec<? extends GrowingPlantHeadBlock> codec() {
        return CODEC;
    }

    @Override
    public void randomTick(BlockState state, @NonNull ServerLevel level, @NonNull BlockPos pos, @NonNull RandomSource random) {
        // 如果被剪刀修剪过了，就不再随机生长了，直到被重新种植
        if (state.getValue(SHEARED)) {
            return;
        }
        super.randomTick(state, level, pos, random);
    }

    @Override
    public boolean isValidBonemealTarget(@NonNull LevelReader level, @NonNull BlockPos pos, BlockState state) {
        // 只有当没有被剪刀修剪过，才可以使用骨粉生长
        return !state.getValue(SHEARED) && super.isValidBonemealTarget(level, pos, state);
    }

    @Override
    protected int getBlocksToGrowWhenBonemealed(@NonNull RandomSource randomSource) {
        return 1;
    }

    @Override
    protected boolean canGrowInto(BlockState state) {
        return state.isAir();
    }

    @Override
    protected @NotNull Block getBodyBlock() {
        return ModBlocks.WILD_GRAPEVINE_PLANT;
    }
}
