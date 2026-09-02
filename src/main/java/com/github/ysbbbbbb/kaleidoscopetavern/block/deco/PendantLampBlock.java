package com.github.ysbbbbbb.kaleidoscopetavern.block.deco;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ScheduledTickAccess;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jspecify.annotations.NonNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.List;

public class PendantLampBlock extends HorizontalDirectionalBlock implements SimpleWaterloggedBlock {
    public static final EnumProperty<DoubleBlockHalf> HALF = BlockStateProperties.DOUBLE_BLOCK_HALF;

    public static final VoxelShape UPPER_NORTH_SOUTH_SHAPE = Block.box(1, 0, 5, 15, 16, 11);
    public static final VoxelShape UPPER_EAST_WEST_SHAPE = Block.box(5, 0, 1, 11, 16, 15);
    public static final VoxelShape LOWER_NORTH_SOUTH_SHAPE = Block.box(1, 1, 5, 15, 16, 11);
    public static final VoxelShape LOWER_EAST_WEST_SHAPE = Block.box(5, 1, 1, 11, 16, 15);

    private static final MapCodec<PendantLampBlock> CODEC = simpleCodec(p -> new PendantLampBlock());
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    public PendantLampBlock() {
        super(Properties.of()
                .mapColor(MapColor.METAL)
                .strength(0.8F)
                .sound(SoundType.CHAIN)
                .lightLevel(state -> state.getValue(HALF) == DoubleBlockHalf.UPPER ? 0 : 13)
                .noOcclusion()
                .noCollision()
        );
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(HALF, DoubleBlockHalf.LOWER)
                .setValue(WATERLOGGED, false)
                .setValue(FACING, Direction.NORTH));
    }

    public PendantLampBlock(Properties properties) {
        super(properties
                .mapColor(MapColor.METAL)
                .strength(0.8F)
                .sound(SoundType.CHAIN)
                .lightLevel(state -> state.getValue(HALF) == DoubleBlockHalf.UPPER ? 0 : 13)
                .noOcclusion()
                .noCollision());
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(HALF, DoubleBlockHalf.LOWER)
                .setValue(WATERLOGGED, false)
                .setValue(FACING, Direction.NORTH));
    }

    @Override
    public @NonNull FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    @Override
    protected @NonNull BlockState updateShape(@NonNull BlockState state, @NonNull LevelReader levelReader, @NonNull ScheduledTickAccess scheduledTickAccess, @NonNull BlockPos pos, @NonNull Direction direction, @NonNull BlockPos neighborPos, @NonNull BlockState neighborState, @NonNull RandomSource randomSource) {
        if (state.getValue(WATERLOGGED)) {
            scheduledTickAccess.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(levelReader));
        }
        DoubleBlockHalf half = state.getValue(HALF);
        boolean isLowerHalf = half == DoubleBlockHalf.LOWER && direction == Direction.UP;
        boolean isUpperHalf = half == DoubleBlockHalf.UPPER && direction == Direction.DOWN;
        if (direction.getAxis() == Direction.Axis.Y && (isLowerHalf || isUpperHalf)) {
            if (neighborState.is(this) && neighborState.getValue(HALF) != half) {
                return state.setValue(FACING, neighborState.getValue(FACING));
            }
            return Blocks.AIR.defaultBlockState();
        }
        return super.updateShape(state, levelReader, scheduledTickAccess, pos, direction, neighborPos, neighborState, randomSource);
    }

    @Override
    public @NonNull BlockState playerWillDestroy(Level level, BlockPos pos, BlockState state, Player player) {
        if (!level.isClientSide() && player.isCreative() && state.getValue(HALF) == DoubleBlockHalf.UPPER) {
            BlockPos below = pos.below();
            BlockState belowState = level.getBlockState(below);
            if (belowState.is(state.getBlock()) && belowState.getValue(HALF) == DoubleBlockHalf.LOWER) {
                BlockState airBlockState = belowState.getFluidState().is(Fluids.WATER) ? Blocks.WATER.defaultBlockState() : Blocks.AIR.defaultBlockState();
                level.setBlock(below, airBlockState, Block.UPDATE_SUPPRESS_DROPS | Block.UPDATE_ALL);
                level.levelEvent(player, LevelEvent.PARTICLES_DESTROY_BLOCK, below, Block.getId(belowState));
            }
        }
        return super.playerWillDestroy(level, pos, state, player);
    }

    @Override
    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockPos pos = context.getClickedPos();
        Level level = context.getLevel();
        boolean hasWater = context.getLevel().getFluidState(context.getClickedPos()).getType() == Fluids.WATER;
        if (pos.getY() > level.getMinY() + 1 && level.getBlockState(pos.below()).canBeReplaced(context)) {
            return this.defaultBlockState()
                    .setValue(FACING, context.getHorizontalDirection())
                    .setValue(WATERLOGGED, hasWater)
                    .setValue(HALF, DoubleBlockHalf.UPPER);
        }
        return null;
    }

    @Override
    public void setPlacedBy(Level level, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack) {
        BlockPos below = pos.below();
        boolean hasWater = level.getFluidState(below).getType() == Fluids.WATER;
        BlockState blockState = state
                .setValue(HALF, DoubleBlockHalf.LOWER)
                .setValue(WATERLOGGED, hasWater);
        level.setBlockAndUpdate(below, blockState);
    }

    @Override
    public @NonNull List<ItemStack> getDrops(BlockState state, LootParams.Builder lootParamsBuilder) {
        if (state.getValue(HALF) == DoubleBlockHalf.LOWER) {
            return super.getDrops(state, lootParamsBuilder);
        }
        return Collections.emptyList();
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, HALF, WATERLOGGED);
    }

    @Override
    public @NonNull VoxelShape getShape(BlockState state, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        boolean isLower = state.getValue(HALF) == DoubleBlockHalf.LOWER;
        return switch (state.getValue(FACING)) {
            case NORTH, SOUTH -> isLower ? LOWER_NORTH_SOUTH_SHAPE : UPPER_NORTH_SOUTH_SHAPE;
            default -> isLower ? LOWER_EAST_WEST_SHAPE : UPPER_EAST_WEST_SHAPE;
        };
    }

    @Override
    protected @NonNull MapCodec<? extends HorizontalDirectionalBlock> codec() {
        return CODEC;
    }
}
