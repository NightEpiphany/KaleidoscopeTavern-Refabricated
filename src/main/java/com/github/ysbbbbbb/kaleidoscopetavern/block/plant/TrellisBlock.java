package com.github.ysbbbbbb.kaleidoscopetavern.block.plant;

import com.github.ysbbbbbb.kaleidoscopetavern.block.properties.TrellisType;
import com.github.ysbbbbbb.kaleidoscopetavern.init.ModBlocks;
import com.github.ysbbbbbb.kaleidoscopetavern.init.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jspecify.annotations.NonNull;

import static com.github.ysbbbbbb.kaleidoscopetavern.block.plant.ITrellis.*;

public class TrellisBlock extends Block implements SimpleWaterloggedBlock, ITrellis {
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    public TrellisBlock(Properties properties) {
        super(properties
                .mapColor(MapColor.WOOD)
                .instrument(NoteBlockInstrument.GUITAR)
                .strength(0.8F)
                .sound(SoundType.WOOD)
                .noOcclusion()
                .ignitedByLava());
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(TYPE, TrellisType.SINGLE)
                .setValue(WATERLOGGED, false));
    }

    @Override
    public @NonNull InteractionResult useItemOn(@NonNull ItemStack stack, @NonNull BlockState state, @NonNull Level level, @NonNull BlockPos pos,
                                                Player player, @NonNull InteractionHand hand, @NonNull BlockHitResult hitResult) {
        // 玩家手持的是葡萄藤
        ItemStack itemInHand = player.getItemInHand(hand);
        if (!itemInHand.is(ModItems.GRAPEVINE)) {
            return super.useItemOn(stack, state, level, pos, player, hand, hitResult);
        }
        // 如果藤架是单个的
        TrellisType type = state.getValue(TYPE);
        if (type != TrellisType.SINGLE) {
            return super.useItemOn(stack, state, level, pos, player, hand, hitResult);
        }
        // 并且下方是泥土，那么可以种植
        BlockState belowState = level.getBlockState(pos.below());
        if (belowState.is(BlockTags.DIRT)) {
            BlockState plantedState = ModBlocks.GRAPEVINE_TRELLIS
                    .defaultBlockState()
                    .setValue(WATERLOGGED, state.getValue(WATERLOGGED));
            level.setBlockAndUpdate(pos, plantedState);
            level.playSound(null, pos, SoundEvents.CROP_PLANTED, SoundSource.BLOCKS);
            if (!player.isCreative()) {
                itemInHand.shrink(1);
            }
            return InteractionResult.SUCCESS;
        }
        return super.useItemOn(stack, state, level, pos, player, hand, hitResult);
    }

    @Override
    protected @NonNull BlockState updateShape(@NonNull BlockState blockState, @NonNull LevelReader levelReader, @NonNull ScheduledTickAccess scheduledTickAccess, @NonNull BlockPos blockPos, @NonNull Direction direction, @NonNull BlockPos blockPos2, @NonNull BlockState blockState2, @NonNull RandomSource randomSource) {
        if (blockState.getValue(WATERLOGGED)) {
            scheduledTickAccess.scheduleTick(blockPos, Fluids.WATER, Fluids.WATER.getTickDelay(levelReader));
        }

        boolean xHas = axisHasTrellis(levelReader, blockPos, Direction.Axis.X);
        boolean yHas = axisHasTrellis(levelReader, blockPos, Direction.Axis.Y);
        boolean zHas = axisHasTrellis(levelReader, blockPos, Direction.Axis.Z);
        var trellisType = updateType(blockState.getValue(TYPE), xHas, yHas, zHas);

        blockState = blockState.setValue(TYPE, trellisType);
        return super.updateShape(blockState, levelReader, scheduledTickAccess, blockPos, direction, blockPos2, blockState2, randomSource);
    }

    @Override
    public boolean sameType(BlockState state) {
        return state.is(ModBlocks.TRELLIS) || state.is(ModBlocks.GRAPEVINE_TRELLIS);
    }

    @Override
    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        Level level = context.getLevel();
        BlockPos pos = context.getClickedPos();
        Direction.Axis clickAxis = context.getClickedFace().getAxis();

        boolean xHas = axisHasTrellis(level, pos, Direction.Axis.X);
        boolean yHas = axisHasTrellis(level, pos, Direction.Axis.Y);
        boolean zHas = axisHasTrellis(level, pos, Direction.Axis.Z);
        var type = getTypeForPlacement(xHas, yHas, zHas, clickAxis);

        return this.defaultBlockState()
                .setValue(TYPE, type)
                .setValue(WATERLOGGED, level.isWaterAt(pos));
    }

    @Override
    public @NotNull VoxelShape getCollisionShape(BlockState state, @NonNull BlockGetter level, @NonNull BlockPos pos, @NonNull CollisionContext context) {
        return collisionShape(state.getValue(TYPE));
    }

    @Override
    public @NotNull VoxelShape getShape(BlockState state, @NonNull BlockGetter level, @NonNull BlockPos pos, @NonNull CollisionContext context) {
        return selectShape(state.getValue(TYPE));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(TYPE, WATERLOGGED);
    }

    @Override
    public @NotNull FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }
}
