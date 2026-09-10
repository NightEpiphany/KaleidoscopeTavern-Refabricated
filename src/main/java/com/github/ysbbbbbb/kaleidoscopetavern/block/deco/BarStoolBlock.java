package com.github.ysbbbbbb.kaleidoscopetavern.block.deco;

import com.github.ysbbbbbb.kaleidoscopetavern.api.entity.ISittable;
import com.github.ysbbbbbb.kaleidoscopetavern.blockentity.deco.BarStoolBlockEntity;
import com.github.ysbbbbbb.kaleidoscopetavern.entity.SitEntity;
import com.github.ysbbbbbb.kaleidoscopetavern.init.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class BarStoolBlock extends BaseEntityBlock implements SimpleWaterloggedBlock, ISittable {
    public static final EnumProperty<Direction> FACING = BlockStateProperties.HORIZONTAL_FACING;
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    private final DyeColor color;

    private static final VoxelShape SHAPE = Shapes.or(
            Shapes.box(0.3125, 0, 0.3125, 0.6875, 0.125, 0.6875),
            Shapes.box(0.4375, 0.0625, 0.4375, 0.5625, 0.75, 0.5625),
            Shapes.box(0.125, 0.75, 0.125, 0.875, 0.9375, 0.875)
    );

    public static final VoxelShape NORTH_SHAPE = Shapes.or(
            Block.box(5, 0, 5, 11, 2, 11),
            Block.box(7, 1, 7, 9, 12, 9),
            Block.box(2, 12, 3, 14, 15, 14),
            Block.box(2, 15, 11, 14, 21, 14)
    );

    public static final VoxelShape SOUTH_SHAPE = Shapes.or(
            Block.box(5, 0, 5, 11, 2, 11),
            Block.box(7, 1, 7, 9, 12, 9),
            Block.box(2, 12, 2, 14, 15, 13),
            Block.box(2, 15, 2, 14, 21, 5)
    );

    public static final VoxelShape EAST_SHAPE = Shapes.or(
            Block.box(5, 0, 5, 11, 2, 11),
            Block.box(7, 1, 7, 9, 12, 9),
            Block.box(2, 12, 2, 13, 15, 14),
            Block.box(2, 15, 2, 5, 21, 14)
    );

    public static final VoxelShape WEST_SHAPE = Shapes.or(
            Block.box(5, 0, 5, 11, 2, 11),
            Block.box(7, 1, 7, 9, 12, 9),
            Block.box(3, 12, 2, 14, 15, 14),
            Block.box(11, 15, 2, 14, 21, 14)
    );

    public BarStoolBlock(Properties properties, DyeColor color) {
        super(properties
                .mapColor(MapColor.WOOD)
                .instrument(NoteBlockInstrument.GUITAR)
                .strength(0.8F)
                .sound(SoundType.WOOD)
                .noOcclusion()
                .ignitedByLava());
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(FACING, Direction.NORTH)
                .setValue(WATERLOGGED, false));
        this.color = color;
    }

    @Override
    protected @NotNull BlockState updateShape(@NotNull BlockState blockState, @NotNull LevelReader levelReader, @NotNull ScheduledTickAccess scheduledTickAccess, @NotNull BlockPos blockPos, @NotNull Direction direction, @NotNull BlockPos blockPos2, @NotNull BlockState blockState2, @NotNull RandomSource randomSource) {
        if (blockState.getValue(WATERLOGGED)) {
            scheduledTickAccess.scheduleTick(blockPos, Fluids.WATER, Fluids.WATER.getTickDelay(levelReader));
        }
        return super.updateShape(blockState, levelReader, scheduledTickAccess, blockPos, direction, blockPos2, blockState2, randomSource);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, WATERLOGGED);
    }

    @Override
    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        FluidState fluidState = context.getLevel().getFluidState(context.getClickedPos());
        return this.defaultBlockState()
                .setValue(FACING, context.getHorizontalDirection().getOpposite())
                .setValue(WATERLOGGED, fluidState.getType() == Fluids.WATER);
    }

    @Override
    public void destroy(LevelAccessor levelAccessor, @NotNull BlockPos pos, @NotNull BlockState state) {
        levelAccessor.getEntitiesOfClass(SitEntity.class, new AABB(pos)).forEach(Entity::discard);
    }

    @Override
    public @NotNull FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    @Override
    protected @NotNull BlockState rotate(BlockState blockState, Rotation rotation) {
        return blockState.setValue(FACING, rotation.rotate(blockState.getValue(FACING)));
    }

    @Override
    protected @NotNull BlockState mirror(BlockState blockState, Mirror mirror) {
        return blockState.rotate(mirror.getRotation(blockState.getValue(FACING)));
    }

    @Override
    public @NotNull VoxelShape getShape(@NotNull BlockState state, @NotNull BlockGetter blockGetter, @NotNull BlockPos pos, @NotNull CollisionContext ctx) {
        if (blockGetter.getBlockEntity(pos) instanceof BarStoolBlockEntity barStool) {
            return switch (barStool.currentFacing(3.25F, state)) {
                case NORTH -> NORTH_SHAPE;
                case SOUTH -> SOUTH_SHAPE;
                case EAST -> EAST_SHAPE;
                case WEST -> WEST_SHAPE;
                case null, default -> SHAPE;
            };
        }
        return SHAPE;
    }

    @Override
    public @NotNull RenderShape getRenderShape(@NotNull BlockState state) {
        return RenderShape.MODEL;
    }

    @Override
    public float getSitHeight() {
        return 0.9483f;
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(@NotNull BlockPos blockPos, @NotNull BlockState blockState) {
        return new BarStoolBlockEntity(blockPos, blockState, color);
    }

    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, @NotNull BlockState state, @NotNull BlockEntityType<T> blockEntityType) {
        if (level.isClientSide()) {
            return null;
        }
        return createTickerHelper(blockEntityType, ModBlocks.BAR_STOOL_BE, BarStoolBlockEntity::tick);
    }
}
