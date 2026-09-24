package com.github.ysbbbbbb.kaleidoscopetavern.block.deco;

import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.*;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

@SuppressWarnings("deprecation")
public class PaintingBlock extends HorizontalDirectionalBlock implements SimpleWaterloggedBlock {
    public static final EnumProperty<AttachFace> ATTACH_FACE = BlockStateProperties.ATTACH_FACE;
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    public static final VoxelShape NORTH_SHAPE = Block.box(1, 1, 15, 15, 15, 16);
    public static final VoxelShape SOUTH_SHAPE = Block.box(1, 1, 0, 15, 15, 1);
    public static final VoxelShape WEST_SHAPE = Block.box(15, 1, 1, 16, 15, 15);
    public static final VoxelShape EAST_SHAPE = Block.box(0, 1, 1, 1, 15, 15);
    public static final VoxelShape FLOOR_SHAPE = Block.box(1, 0, 1, 15, 1, 15);
    public static final VoxelShape CEILING_SHAPE = Block.box(1, 15, 1, 15, 16, 15);

    private @Nullable String tooltipKey;

    public PaintingBlock() {
        super(Properties.of()
                .mapColor(MapColor.WOOD)
                .instrument(NoteBlockInstrument.HAT)
                .strength(0.8F)
                .sound(SoundType.WOOD)
                .noOcclusion());
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(FACING, Direction.NORTH)
                .setValue(ATTACH_FACE, AttachFace.WALL)
                .setValue(WATERLOGGED, false));
    }

    public boolean canSurvive(final BlockState state, final @NotNull LevelReader level, final @NotNull BlockPos pos) {
        switch (state.getValue(ATTACH_FACE)) {
            case WALL -> {
                Direction direction = state.getValue(FACING);
                BlockPos adjacentPos = pos.relative(direction.getOpposite());
                return level.getBlockState(adjacentPos).isFaceSturdy(level, adjacentPos, direction);
            }
            case FLOOR -> {
                BlockPos adjacentPos = pos.below();
                return level.getBlockState(adjacentPos).isFaceSturdy(level, adjacentPos, Direction.UP);
            }
            default -> {
                BlockPos adjacentPos = pos.above();
                return level.getBlockState(adjacentPos).isFaceSturdy(level, adjacentPos, Direction.DOWN);
            }
        }
    }

    @Override
    public @NotNull BlockState updateShape(BlockState state, @NotNull Direction direction, @NotNull BlockState neighborState,
                                           @NotNull LevelAccessor level, @NotNull BlockPos pos, @NotNull BlockPos neighborPos) {
        if (state.getValue(WATERLOGGED)) {
            level.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
        }
        boolean faceValidation = switch (state.getValue(ATTACH_FACE)) {
            case WALL -> direction == state.getValue(FACING).getOpposite();
            case FLOOR ->  direction == Direction.DOWN;
            default -> direction == Direction.UP;
        };
        return faceValidation
                && !state.canSurvive(level, pos) ? Blocks.AIR.defaultBlockState() : super.updateShape(state, direction, neighborState, level, pos, neighborPos);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, ATTACH_FACE, WATERLOGGED);
    }

    @Override
    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        Direction clickedFace = context.getClickedFace();
        AttachFace attachFace = AttachFace.WALL;
        if (clickedFace.getAxis().isVertical()) {
            attachFace = clickedFace == Direction.UP ? AttachFace.FLOOR : AttachFace.CEILING;
            Direction horizontalDirection = context.getHorizontalDirection();
            clickedFace = clickedFace == Direction.UP ? horizontalDirection.getOpposite() : horizontalDirection;
        }
        boolean waterLogged = context.getLevel().getFluidState(context.getClickedPos()).is(FluidTags.WATER);
        return this.defaultBlockState()
                .setValue(FACING, clickedFace)
                .setValue(ATTACH_FACE, attachFace)
                .setValue(WATERLOGGED, waterLogged);
    }

    @Override
    public @NotNull FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    @Override
    public @NotNull VoxelShape getShape(BlockState pState, @NotNull BlockGetter pLevel, @NotNull BlockPos pPos, @NotNull CollisionContext pContext) {
        AttachFace attachFace = pState.getValue(ATTACH_FACE);
        if (attachFace == AttachFace.FLOOR) {
            return FLOOR_SHAPE;
        } else if (attachFace == AttachFace.CEILING) {
            return CEILING_SHAPE;
        }
        return switch (pState.getValue(FACING)) {
            case SOUTH -> SOUTH_SHAPE;
            case EAST -> EAST_SHAPE;
            case WEST -> WEST_SHAPE;
            default -> NORTH_SHAPE;
        };
    }

    @Override
    public @NotNull String getDescriptionId() {
        return "block.kaleidoscope_tavern.painting";
    }

    @Override
    public void appendHoverText(@NotNull ItemStack stack, @Nullable BlockGetter level, @NotNull List<Component> tooltip, @NotNull TooltipFlag flag) {
        if (this.tooltipKey == null) {
            this.tooltipKey = Util.makeDescriptionId("tooltip", BuiltInRegistries.BLOCK.getKey(this));
        }
        tooltip.add(Component.translatable(this.tooltipKey).withStyle(ChatFormatting.GRAY));
    }
}