package com.github.ysbbbbbb.kaleidoscopetavern.block.brew;

import com.github.ysbbbbbb.kaleidoscopetavern.block.AbstractStorageBlock;
import com.github.ysbbbbbb.kaleidoscopetavern.block.properties.PositionType;
import com.github.ysbbbbbb.kaleidoscopetavern.blockentity.brew.CellarCabinetBlockEntity;
import com.github.ysbbbbbb.kaleidoscopetavern.init.tag.TagMod;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ScheduledTickAccess;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.util.RandomSource;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.core.Vec3i;
import org.jspecify.annotations.NonNull;
import org.jetbrains.annotations.Nullable;

public class CellarCabinetBlock extends AbstractStorageBlock implements SimpleWaterloggedBlock {
    private static final MapCodec<CellarCabinetBlock> CODEC = simpleCodec(p -> new CellarCabinetBlock());
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    public CellarCabinetBlock() {
        super();
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(FACING, Direction.NORTH)
                .setValue(POWERED, false)
                .setValue(WATERLOGGED, false)
                .setValue(BarCabinetBlock.POSITION, PositionType.SINGLE));
    }

    public CellarCabinetBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(FACING, Direction.NORTH)
                .setValue(POWERED, false)
                .setValue(WATERLOGGED, false)
                .setValue(BarCabinetBlock.POSITION, PositionType.SINGLE));
    }

    @Override
    protected @NonNull InteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos,
                                                       Player player, InteractionHand hand, BlockHitResult hitResult) {
        return super.handleUse(state, level, pos, player, hand, hitResult);
    }

    @Override
    public @NonNull FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    @Override
    protected boolean blockListCheck(ItemStack stack) {
        return stack.is(TagMod.CELLAR_CABINET_BLOCKLIST);
    }

    @Override
    protected int getClickedSlot(Direction direction, BlockPos pos, BlockHitResult hitResult) {
        // 只能点击正面
        if (hitResult.getDirection() != direction) {
            return -1;
        }

        double localX = this.getLocalX(direction, pos, hitResult);
        double relativeY = hitResult.getLocation().y - pos.getY();

        // 九宫格，点那个选择哪个
        int column = (int) (localX * 3) % 3;
        int row = 2 - (int) (relativeY * 3) % 3;

        return column + row * 3;
    }

    @Override
    protected Vec3 getShootPos(Direction direction, BlockPos pos, int slot) {
        Vec3 center = Vec3.atLowerCornerOf(pos).add(0.5, 0.5, 0.5);
        Vec3 scale = Vec3.atLowerCornerOf(new Vec3i(direction.getStepX(), direction.getStepY(), direction.getStepZ())).scale(0.5);
        return center.add(scale);
    }

    @Override
    protected Vec3 getMovement(Direction direction, BlockPos pos, int slot) {
        double factor = Math.random() * 2 + 0.5;
        Vec3 normal = Vec3.atLowerCornerWithOffset(new Vec3i(direction.getStepX(), direction.getStepY(), direction.getStepZ()), 0, 0.1, 0);
        return normal.scale(factor);
    }

    @Override
    protected @NonNull BlockState updateShape(BlockState state, @NonNull LevelReader levelReader, @NonNull ScheduledTickAccess scheduledTickAccess, @NonNull BlockPos pos, @NonNull Direction direction, @NonNull BlockPos neighborPos, @NonNull BlockState neighborState, @NonNull RandomSource randomSource) {
        if (state.getValue(WATERLOGGED)) {
            scheduledTickAccess.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(levelReader));
        }
        Direction self = state.getValue(FACING);
        Direction left = self.getClockWise();
        Direction right = self.getCounterClockWise();

        if (direction == left) {
            boolean leftIsCabinet = neighborState.is(this) && neighborState.getValue(FACING) == self;
            PositionType position = state.getValue(BarCabinetBlock.POSITION);
            if (leftIsCabinet) {
                if (position == PositionType.SINGLE) {
                    return state.setValue(BarCabinetBlock.POSITION, PositionType.RIGHT);
                } else if (position == PositionType.LEFT) {
                    return state.setValue(BarCabinetBlock.POSITION, PositionType.MIDDLE);
                }
            } else {
                if (position == PositionType.RIGHT) {
                    return state.setValue(BarCabinetBlock.POSITION, PositionType.SINGLE);
                } else if (position == PositionType.MIDDLE) {
                    return state.setValue(BarCabinetBlock.POSITION, PositionType.LEFT);
                }
            }
        } else if (direction == right) {
            boolean rightIsCabinet = neighborState.is(this) && neighborState.getValue(FACING) == self;
            PositionType position = state.getValue(BarCabinetBlock.POSITION);
            if (rightIsCabinet) {
                if (position == PositionType.SINGLE) {
                    return state.setValue(BarCabinetBlock.POSITION, PositionType.LEFT);
                } else if (position == PositionType.RIGHT) {
                    return state.setValue(BarCabinetBlock.POSITION, PositionType.MIDDLE);
                }
            } else {
                if (position == PositionType.LEFT) {
                    return state.setValue(BarCabinetBlock.POSITION, PositionType.SINGLE);
                } else if (position == PositionType.MIDDLE) {
                    return state.setValue(BarCabinetBlock.POSITION, PositionType.RIGHT);
                }
            }
        }
        return super.updateShape(state, levelReader, scheduledTickAccess, pos, direction, neighborPos, neighborState, randomSource);
    }

    @Override
    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        Level level = context.getLevel();
        BlockPos pos = context.getClickedPos();
        Direction opposite = context.getHorizontalDirection().getOpposite();
        boolean hasWater = context.getLevel().getFluidState(context.getClickedPos()).getType() == Fluids.WATER;
        BlockState left = level.getBlockState(pos.relative(opposite.getClockWise()));
        BlockState right = level.getBlockState(pos.relative(opposite.getCounterClockWise()));

        PositionType position = PositionType.SINGLE;
        boolean leftIsCabinet = left.is(this) && left.getValue(FACING) == opposite;
        boolean rightIsCabinet = right.is(this) && right.getValue(FACING) == opposite;

        if (leftIsCabinet && rightIsCabinet) {
            position = PositionType.MIDDLE;
        } else if (leftIsCabinet) {
            position = PositionType.RIGHT;
        } else if (rightIsCabinet) {
            position = PositionType.LEFT;
        }

        boolean signal = level.hasNeighborSignal(pos);

        return this.defaultBlockState()
                .setValue(FACING, opposite)
                .setValue(POWERED, signal)
                .setValue(WATERLOGGED, hasWater)
                .setValue(BarCabinetBlock.POSITION, position);
    }

    @Override
    @Nullable
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new CellarCabinetBlockEntity(pos, state);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, POWERED, BarCabinetBlock.POSITION, WATERLOGGED);
    }

    @Override
    public float getShadeBrightness(BlockState pState, BlockGetter pLevel, BlockPos pPos) {
        return 0.2F;
    }

    @Override
    protected @NonNull MapCodec<? extends HorizontalDirectionalBlock> codec() {
        return CODEC;
    }
}
