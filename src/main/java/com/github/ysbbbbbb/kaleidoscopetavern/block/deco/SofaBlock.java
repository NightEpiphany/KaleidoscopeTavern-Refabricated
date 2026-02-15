package com.github.ysbbbbbb.kaleidoscopetavern.block.deco;

import com.github.ysbbbbbb.kaleidoscopetavern.block.properties.ConnectionType;
import com.github.ysbbbbbb.kaleidoscopetavern.entity.SitEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.SoundType;
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
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import java.util.List;

@SuppressWarnings("deprecation")
public class SofaBlock extends HorizontalDirectionalBlock implements SimpleWaterloggedBlock {
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    public static final EnumProperty<ConnectionType> CONNECTION = EnumProperty.create("connection", ConnectionType.class);

    // 普通情况
    public static final VoxelShape NORTH_SHAPE = Shapes.or(
            Block.box(0, 0, 0, 16, 8, 16),
            Block.box(0, 8, 11, 16, 18, 16)
    );
    public static final VoxelShape SOUTH_SHAPE = Shapes.or(
            Block.box(0, 0, 0, 16, 8, 16),
            Block.box(0, 8, 0, 16, 18, 5)
    );
    public static final VoxelShape WEST_SHAPE = Shapes.or(
            Block.box(0, 0, 0, 16, 8, 16),
            Block.box(11, 8, 0, 16, 18, 16)
    );
    public static final VoxelShape EAST_SHAPE = Shapes.or(
            Block.box(0, 0, 0, 16, 8, 16),
            Block.box(0, 8, 0, 5, 18, 16)
    );

    // 转角
    public static final VoxelShape NORTH_LEFT_CORNER_SHAPE = Shapes.or(NORTH_SHAPE, Block.box(11, 8, 0, 16, 18, 16));
    public static final VoxelShape NORTH_RIGHT_CORNER_SHAPE = Shapes.or(NORTH_SHAPE, Block.box(0, 8, 0, 5, 18, 16));

    public static final VoxelShape SOUTH_LEFT_CORNER_SHAPE = Shapes.or(SOUTH_SHAPE, Block.box(0, 8, 0, 5, 18, 16));
    public static final VoxelShape SOUTH_RIGHT_CORNER_SHAPE = Shapes.or(SOUTH_SHAPE, Block.box(11, 8, 0, 16, 18, 16));

    public static final VoxelShape WEST_LEFT_CORNER_SHAPE = Shapes.or(WEST_SHAPE, Block.box(0, 8, 0, 16, 18, 5));
    public static final VoxelShape WEST_RIGHT_CORNER_SHAPE = Shapes.or(WEST_SHAPE, Block.box(0, 8, 11, 16, 18, 16));

    public static final VoxelShape EAST_LEFT_CORNER_SHAPE = Shapes.or(EAST_SHAPE, Block.box(0, 8, 11, 16, 18, 16));
    public static final VoxelShape EAST_RIGHT_CORNER_SHAPE = Shapes.or(EAST_SHAPE, Block.box(0, 8, 0, 16, 18, 5));

    public SofaBlock() {
        super(Properties.of()
                .mapColor(MapColor.WOOL)
                .instrument(NoteBlockInstrument.GUITAR)
                .strength(0.8F)
                .sound(SoundType.WOOL)
                .noOcclusion()
                .ignitedByLava());
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(FACING, Direction.NORTH)
                .setValue(CONNECTION, ConnectionType.SINGLE)
                .setValue(WATERLOGGED, false));
    }

    /**
     * 判断左侧是否连接
     * <p>
     * 连接条件：
     * 1. 同朝向
     * 2. 朝右，但是 type 是单个、右侧、右拐角
     */
    public static boolean leftConnected(BlockState leftState, Direction self) {
        if (leftState.getBlock() instanceof SofaBlock) {
            Direction check = leftState.getValue(FACING);
            Direction right = self.getCounterClockWise();
            if (check == right) {
                ConnectionType connection = leftState.getValue(CONNECTION);
                return connection == ConnectionType.SINGLE || connection == ConnectionType.RIGHT || connection == ConnectionType.RIGHT_CORNER;
            }
            return check == self;
        }
        return false;
    }

    /**
     * 判断右侧是否连接
     * <p>
     * 连接条件：
     * 1. 同朝向
     * 2. 朝左，但是 type 是单个、左侧、左拐角
     */
    public static boolean rightConnected(BlockState rightState, Direction self) {
        if (rightState.getBlock() instanceof SofaBlock) {
            Direction check = rightState.getValue(FACING);
            Direction left = self.getClockWise();
            if (check == left) {
                ConnectionType connection = rightState.getValue(CONNECTION);
                return connection == ConnectionType.SINGLE || connection == ConnectionType.LEFT || connection == ConnectionType.LEFT_CORNER;
            }
            return check == self;
        }
        return false;
    }

    /**
     * 判断前侧是否左拐角连接
     * <p>
     * 连接条件：
     * 朝左，type 为单个、右拐角、左侧
     */
    public static boolean frontLeftConnected(BlockState frontState, Direction direction, ConnectionType connectionType) {
        if (frontState.getBlock() instanceof SofaBlock) {
            Direction check = frontState.getValue(FACING);
            Direction left = direction.getClockWise();
            ConnectionType connection = frontState.getValue(CONNECTION);
            if (check == left) {
                return connection != ConnectionType.LEFT_CORNER;
            }
        }
        return false;
    }

    /**
     * 判断前侧是否右拐角连接
     * <p>
     * 连接条件：
     * 朝右，type 为单个、右拐角、左侧
     */
    public static boolean frontRightConnected(BlockState frontState, Direction direction, ConnectionType connectionType) {
        if (frontState.getBlock() instanceof SofaBlock) {
            Direction check = frontState.getValue(FACING);
            Direction right = direction.getCounterClockWise();
            ConnectionType connection = frontState.getValue(CONNECTION);
            if (check == right) {
                return connection != ConnectionType.RIGHT_CORNER;
            }
        }
        return false;
    }

    /**
     * 根据连接状态获取连接类型
     */
    public static ConnectionType getConnectionType(boolean leftConnected, boolean rightConnected,
                                                   boolean frontLeftConnected, boolean frontRightConnected) {
        if (leftConnected && rightConnected) {
            // 优先横向连接
            return ConnectionType.MIDDLE;
        } else if (frontLeftConnected) {
            // 其次是左拐角连接
            if (rightConnected) {
                return ConnectionType.LEFT;
            }
            return ConnectionType.RIGHT_CORNER;
        } else if (frontRightConnected) {
            // 其次是右拐角连接
            if (leftConnected) {
                return ConnectionType.RIGHT;
            }
            return ConnectionType.LEFT_CORNER;
        } else if (leftConnected) {
            return ConnectionType.RIGHT;
        } else if (rightConnected) {
            return ConnectionType.LEFT;
        } else {
            return ConnectionType.SINGLE;
        }
    }

    @Override
    public BlockState updateShape(BlockState state, Direction direction, BlockState neighborState,
                                  LevelAccessor level, BlockPos pos, BlockPos neighborPos) {
        if (state.getValue(WATERLOGGED)) {
            level.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
        }

        Direction facing = state.getValue(FACING);
        boolean horizontalChange = direction.getAxis() == facing.getClockWise().getAxis();
        boolean frontChange = direction == facing;

        if (horizontalChange || frontChange) {
            Direction left = facing.getClockWise();
            Direction right = facing.getCounterClockWise();
            ConnectionType type = state.getValue(CONNECTION);

            // 如果自己是左拐角，更新来自左侧，不需要刷新状态
            if (type == ConnectionType.LEFT_CORNER && direction == left) {
                return super.updateShape(state, direction, neighborState, level, pos, neighborPos);
            }

            // 如果自己是右拐角，更新来自右侧，不需要刷新状态
            if (type == ConnectionType.RIGHT_CORNER && direction == right) {
                return super.updateShape(state, direction, neighborState, level, pos, neighborPos);
            }

            BlockState leftState = level.getBlockState(pos.relative(left));
            BlockState rightState = level.getBlockState(pos.relative(right));
            BlockState frontState = level.getBlockState(pos.relative(facing));

            boolean leftConnected = leftConnected(leftState, facing);
            boolean rightConnected = rightConnected(rightState, facing);
            boolean frontLeftConnected = frontLeftConnected(frontState, facing, type);
            boolean frontRightConnected = frontRightConnected(frontState, facing, type);

            ConnectionType changedType = getConnectionType(leftConnected, rightConnected, frontLeftConnected, frontRightConnected);
            state = state.setValue(CONNECTION, changedType);
        }

        return super.updateShape(state, direction, neighborState, level, pos, neighborPos);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, CONNECTION, WATERLOGGED);
    }

    @Override
    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        // 先检查左右两侧是否有沙发
        Level level = context.getLevel();
        BlockPos pos = context.getClickedPos();
        Direction direction = context.getHorizontalDirection().getOpposite();

        Direction left = direction.getClockWise();
        Direction right = direction.getCounterClockWise();

        BlockState leftState = level.getBlockState(pos.relative(left));
        BlockState rightState = level.getBlockState(pos.relative(right));
        BlockState frontState = level.getBlockState(pos.relative(direction));

        boolean leftConnected = leftConnected(leftState, direction);
        boolean rightConnected = rightConnected(rightState, direction);
        boolean frontLeftConnected = frontLeftConnected(frontState, direction, ConnectionType.SINGLE);
        boolean frontRightConnected = frontRightConnected(frontState, direction, ConnectionType.SINGLE);

        ConnectionType connectionType = getConnectionType(leftConnected, rightConnected, frontLeftConnected, frontRightConnected);

        FluidState fluidState = level.getFluidState(pos);
        return this.defaultBlockState()
                .setValue(FACING, direction)
                .setValue(CONNECTION, connectionType)
                .setValue(WATERLOGGED, fluidState.getType() == Fluids.WATER);
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        List<SitEntity> entities = level.getEntitiesOfClass(SitEntity.class, new AABB(pos));
        if (entities.isEmpty()) {
            SitEntity entitySit = new SitEntity(level, pos, 0.5125);
            entitySit.setYRot(state.getValue(FACING).toYRot());
            level.addFreshEntity(entitySit);
            player.startRiding(entitySit, true);
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.PASS;
    }

    @Override
    public void destroy(LevelAccessor levelAccessor, BlockPos pos, BlockState state) {
        levelAccessor.getEntitiesOfClass(SitEntity.class, new AABB(pos)).forEach(Entity::discard);
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        ConnectionType type = pState.getValue(CONNECTION);
        Direction direction = pState.getValue(FACING);

        if (direction == Direction.NORTH) {
            return switch (type) {
                case LEFT_CORNER -> NORTH_LEFT_CORNER_SHAPE;
                case RIGHT_CORNER -> NORTH_RIGHT_CORNER_SHAPE;
                default -> NORTH_SHAPE;
            };
        } else if (direction == Direction.SOUTH) {
            return switch (type) {
                case LEFT_CORNER -> SOUTH_LEFT_CORNER_SHAPE;
                case RIGHT_CORNER -> SOUTH_RIGHT_CORNER_SHAPE;
                default -> SOUTH_SHAPE;
            };
        } else if (direction == Direction.WEST) {
            return switch (type) {
                case LEFT_CORNER -> WEST_LEFT_CORNER_SHAPE;
                case RIGHT_CORNER -> WEST_RIGHT_CORNER_SHAPE;
                default -> WEST_SHAPE;
            };
        } else {
            return switch (type) {
                case LEFT_CORNER -> EAST_LEFT_CORNER_SHAPE;
                case RIGHT_CORNER -> EAST_RIGHT_CORNER_SHAPE;
                default -> EAST_SHAPE;
            };
        }
    }
}
