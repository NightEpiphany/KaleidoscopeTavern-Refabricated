package com.github.ysbbbbbb.kaleidoscopetavern.block.brew;

import com.github.ysbbbbbb.kaleidoscopetavern.api.blockentity.ITapBehavior;
import com.github.ysbbbbbb.kaleidoscopetavern.blockentity.brew.TapBlockEntity;
import com.github.ysbbbbbb.kaleidoscopetavern.game.tap.TapBehaviorManager;
import com.github.ysbbbbbb.kaleidoscopetavern.init.ModBlocks;
import com.github.ysbbbbbb.kaleidoscopetavern.util.VoxelShapeUtils;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.*;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.redstone.Orientation;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jspecify.annotations.NonNull;

import java.util.EnumMap;

import static com.github.ysbbbbbb.kaleidoscopetavern.blockentity.brew.TapBlockEntity.*;

public class TapBlock extends BaseEntityBlock implements SimpleWaterloggedBlock {
    public static final MapCodec<TapBlock> CODEC = simpleCodec(TapBlock::new);

    public static final EnumProperty<Direction> FACING = BlockStateProperties.HORIZONTAL_FACING;
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    public static final BooleanProperty TRIGGERED = BlockStateProperties.TRIGGERED;
    public static final BooleanProperty OPEN = BlockStateProperties.OPEN;

    public final EnumMap<Direction, VoxelShape> shapes;

    public TapBlock(Properties properties) {
        super(properties
                .mapColor(MapColor.METAL)
                .strength(0.8F)
                .sound(SoundType.METAL));
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(FACING, Direction.NORTH)
                .setValue(OPEN, false)
                .setValue(TRIGGERED, false)
                .setValue(WATERLOGGED, false));
        this.shapes = VoxelShapeUtils.horizontalShapes(
                Block.box(5, 5, 6, 11, 13, 16)
        );
    }

    @Override
    protected void neighborChanged(@NonNull BlockState state, @NonNull Level level, @NonNull BlockPos pos, @NonNull Block block, @Nullable Orientation orientation, boolean isMoving) {
        boolean hasSignal = level.hasNeighborSignal(pos) || level.hasNeighborSignal(pos.above());
        boolean triggered = state.getValue(TRIGGERED);
        // 如果有信号，且是关闭状态，此时可以尝试打开
        if (hasSignal && !triggered) {
            if (!state.getValue(OPEN)) {
                this.tryOpen(state, level, pos, null);
            }
            BlockState newState = level.getBlockState(pos);
            level.setBlock(pos, newState.setValue(TRIGGERED, true), Block.UPDATE_NONE);
        } else if (!hasSignal && triggered) {
            BlockState newState = level.getBlockState(pos);
            level.setBlock(pos, newState.setValue(TRIGGERED, false), Block.UPDATE_NONE);
        }
    }
    @Override
    public @NotNull InteractionResult useItemOn(@NonNull ItemStack stack, @NonNull BlockState state, @NonNull Level level, @NonNull BlockPos pos,
                                                @NonNull Player player, @NonNull InteractionHand hand, @NonNull BlockHitResult hitResult) {
        if (hand == InteractionHand.MAIN_HAND) {
            // 如果龙头已经是开启的，直接无视条件关闭
            if (state.getValue(OPEN)) {
                state = state.setValue(OPEN, false);
                level.setBlockAndUpdate(pos, state);

                level.playSound(null, pos, SoundEvents.IRON_TRAPDOOR_CLOSE, SoundSource.BLOCKS, 1.0F, 0.8F);
                return InteractionResult.SUCCESS;
            }

            this.tryOpen(state, level, pos, player);
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.PASS;
    }

    private void tryOpen(BlockState state, Level level, BlockPos pos, @Nullable Player player) {
        Direction tapFacing = state.getValue(FACING);
        BlockPos sourcePos = pos.relative(tapFacing.getOpposite());
        BlockState sourceState = level.getBlockState(sourcePos);

        if (!TapBehaviorManager.contains(sourceState)) {
            this.emptyOpen(level, pos, state);
            return;
        }

        BlockPos belowPos = pos.below();
        BlockState belowState = level.getBlockState(belowPos);
        ITapBehavior behavior = TapBehaviorManager.get(sourceState);

        if (behavior.isMatch(level, player, pos, state, sourceState, belowState)) {
            ParticleOptions particle = behavior.onStartExtract(level, player, pos, state, sourceState, belowState);
            if (level.getBlockEntity(pos) instanceof TapBlockEntity tapEntity) {
                if (particle != null) {
                    tapEntity.setParticle(particle);
                }
                tapEntity.setState(TAKE_DRINK_STATE);
            }
            state = state.setValue(OPEN, true);
            level.setBlockAndUpdate(pos, state);
            level.playSound(null, pos, SoundEvents.IRON_TRAPDOOR_OPEN, SoundSource.BLOCKS, 1.0F, 0.8F);
            level.scheduleTick(pos, this, TAKE_DRINK_TICKS);
            return;
        }

        this.emptyOpen(level, pos, state);
    }

    /**
     * 龙头空拧，此时会有一些特殊的粒子效果，并且持续一小段时间，最后自动关闭
     */
    private void emptyOpen(Level level, BlockPos pos, BlockState blockState) {
        // 将龙头设置为关闭状态
        level.setBlockAndUpdate(pos, blockState.setValue(OPEN, true));
        level.playSound(null, pos, SoundEvents.IRON_TRAPDOOR_OPEN, SoundSource.BLOCKS, 1.0F, 0.8F);

        // 将 BE 设置为此状态
        if (level.getBlockEntity(pos) instanceof TapBlockEntity tapEntity) {
            tapEntity.setState(EMPTY_OPEN_STATE);
        }
    }

    @Override
    public void tick(BlockState state, @NonNull ServerLevel level, @NonNull BlockPos pos, @NonNull RandomSource random) {
        // 如果此时龙头已经被关闭了，那么停止
        if (!state.getValue(OPEN)) {
            return;
        }

        Direction tapFacing = state.getValue(FACING);
        BlockPos sourcePos = pos.relative(tapFacing.getOpposite());
        BlockState sourceState = level.getBlockState(sourcePos);

        // 先正常进行关闭
        level.setBlockAndUpdate(pos, state.setValue(OPEN, false));
        // 关闭音效
        level.playSound(null, pos, SoundEvents.IRON_TRAPDOOR_CLOSE, SoundSource.BLOCKS, 1.0F, 0.8F);
        if (level.getBlockEntity(pos) instanceof TapBlockEntity tapEntity) {
            tapEntity.setParticle(null);
            tapEntity.setState(DEFAULT_STATE);
        }

        if (!TapBehaviorManager.contains(sourceState)) {
            return;
        }

        BlockPos belowPos = pos.below();
        BlockState belowState = level.getBlockState(belowPos);
        ITapBehavior behavior = TapBehaviorManager.get(sourceState);
        if (behavior.isMatch(level, null, pos, state, sourceState, belowState)) {
            behavior.onEndExtract(level, pos, state, sourceState, belowState);
        }
    }

    @Override
    public @NotNull RenderShape getRenderShape(@NonNull BlockState state) {
        return RenderShape.MODEL;
    }

    @Override
    @Nullable
    public BlockEntity newBlockEntity(@NonNull BlockPos pos, @NonNull BlockState state) {
        return new TapBlockEntity(pos, state);
    }

    @Override
    @Nullable
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, @NonNull BlockState state, @NonNull BlockEntityType<T> blockEntityType) {
        if (level.isClientSide()) {
            return null;
        }
        return createTickerHelper(blockEntityType, ModBlocks.TAP_BE,
                (levelIn, pos, stateIn, tap) -> tap.tick(levelIn));
    }

    private boolean isValidConnection(BlockState barrelState, Direction tapFacing) {
        Direction barrelFacing = barrelState.getValue(BarrelBlock.FACING);
        AttachFace layer = barrelState.getValue(BarrelBlock.LAYER);
        int index = barrelState.getValue(BarrelBlock.INDEX);
        // 必须紧贴桶第二层正面
        if (layer == AttachFace.WALL) {
            if (barrelFacing == Direction.NORTH && index == 1) {
                return tapFacing == Direction.NORTH;
            } else if (barrelFacing == Direction.SOUTH && index == 7) {
                return tapFacing == Direction.SOUTH;
            } else if (barrelFacing == Direction.WEST && index == 3) {
                return tapFacing == Direction.WEST;
            } else if (barrelFacing == Direction.EAST && index == 5) {
                return tapFacing == Direction.EAST;
            }
        }
        return false;
    }

    @Override
    protected @NonNull BlockState updateShape(@NonNull BlockState blockState, @NonNull LevelReader levelReader, @NonNull ScheduledTickAccess scheduledTickAccess, @NonNull BlockPos blockPos, @NonNull Direction direction, @NonNull BlockPos blockPos2, @NonNull BlockState blockState2, @NonNull RandomSource randomSource) {
        if (blockState.getValue(WATERLOGGED)) {
            scheduledTickAccess.scheduleTick(blockPos, Fluids.WATER, Fluids.WATER.getTickDelay(levelReader));
        }
        return super.updateShape(blockState, levelReader, scheduledTickAccess, blockPos, direction, blockPos2, blockState2, randomSource);
    }

    @Override
    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockPos pos = context.getClickedPos();
        Direction clickedFace = context.getClickedFace();
        if (clickedFace.getAxis().isVertical()) {
            clickedFace = context.getHorizontalDirection().getOpposite();
        }
        return this.defaultBlockState()
                .setValue(FACING, clickedFace)
                .setValue(WATERLOGGED, context.getLevel().isWaterAt(pos));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, OPEN, TRIGGERED, WATERLOGGED);
    }

    @Override
    public @NotNull VoxelShape getShape(BlockState pState, @NonNull BlockGetter pLevel, @NonNull BlockPos pPos, @NonNull CollisionContext pContext) {
        Direction direction = pState.getValue(FACING);
        return this.shapes.getOrDefault(direction, Shapes.block());
    }

    @Override
    public @NotNull FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    @Override
    public @NotNull BlockState rotate(BlockState state, Rotation rot) {
        return state.setValue(FACING, rot.rotate(state.getValue(FACING)));
    }

    @Override
    public @NotNull BlockState mirror(BlockState state, Mirror mirror) {
        return state.rotate(mirror.getRotation(state.getValue(FACING)));
    }

    @Override
    protected @NotNull MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

}
