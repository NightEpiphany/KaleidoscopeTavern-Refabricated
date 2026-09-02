package com.github.ysbbbbbb.kaleidoscopetavern.block.deco;

import com.github.ysbbbbbb.kaleidoscopetavern.blockentity.deco.IncenseBlockEntity;
import com.github.ysbbbbbb.kaleidoscopetavern.init.ModBlocks;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ScheduledTickAccess;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.redstone.Orientation;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jspecify.annotations.NonNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;

import static net.minecraft.sounds.SoundEvents.STONE_BUTTON_CLICK_OFF;
import static net.minecraft.sounds.SoundEvents.STONE_BUTTON_CLICK_ON;

public class IncenseBlock extends HorizontalDirectionalBlock implements EntityBlock, SimpleWaterloggedBlock {
    private static final VoxelShape SHAPE = Block.box(5, 0, 5, 11, 7, 11);
    private static final MapCodec<IncenseBlock> CODEC = simpleCodec(p -> new IncenseBlock(
            () -> ParticleTypes.CHERRY_LEAVES, () -> ParticleTypes.CHERRY_LEAVES
    ));
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    private static final BooleanProperty POWERED = BlockStateProperties.POWERED;
    private static final BooleanProperty OPEN = BlockStateProperties.OPEN;

    private final Supplier<? extends ParticleOptions> smallParticle;
    private final Supplier<? extends ParticleOptions> largeParticle;

    private final double largeParticleYOffset;
    private final double largeParticleYRange;

    public IncenseBlock(Supplier<? extends ParticleOptions> smallParticle,
                        Supplier<? extends ParticleOptions> largeParticle) {
        this(smallParticle, largeParticle, -2, 16);
    }

    public IncenseBlock(Supplier<? extends ParticleOptions> smallParticle,
                        Supplier<? extends ParticleOptions> largeParticle,
                        double largeParticleYOffset,
                        double largeParticleYRange) {
        super(Properties.of()
                .instabreak()
                .noOcclusion()
                .pushReaction(PushReaction.DESTROY)
                .sound(SoundType.DECORATED_POT));

        this.smallParticle = smallParticle;
        this.largeParticle = largeParticle;

        this.largeParticleYOffset = largeParticleYOffset;
        this.largeParticleYRange = largeParticleYRange;

        this.registerDefaultState(this.stateDefinition.any()
                .setValue(FACING, Direction.NORTH)
                .setValue(OPEN, false)
                .setValue(WATERLOGGED, false)
                .setValue(POWERED, false)
        );
    }

    public IncenseBlock(Properties properties,
                        Supplier<? extends ParticleOptions> smallParticle,
                        Supplier<? extends ParticleOptions> largeParticle) {
        this(properties, smallParticle, largeParticle, -2, 16);
    }

    public IncenseBlock(Properties properties,
                        Supplier<? extends ParticleOptions> smallParticle,
                        Supplier<? extends ParticleOptions> largeParticle,
                        double largeParticleYOffset,
                        double largeParticleYRange) {
        super(properties
                .instabreak()
                .noOcclusion()
                .pushReaction(PushReaction.DESTROY)
                .sound(SoundType.DECORATED_POT));

        this.smallParticle = smallParticle;
        this.largeParticle = largeParticle;

        this.largeParticleYOffset = largeParticleYOffset;
        this.largeParticleYRange = largeParticleYRange;

        this.registerDefaultState(this.stateDefinition.any()
                .setValue(FACING, Direction.NORTH)
                .setValue(OPEN, false)
                .setValue(WATERLOGGED, false)
                .setValue(POWERED, false)
        );
    }

    @Override
    public @NonNull FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    @Override
    public boolean canSurvive(@NonNull BlockState blockState, LevelReader levelReader, BlockPos blockPos) {
        BlockPos blockPos2 = blockPos.below();
        BlockState blockState2 = levelReader.getBlockState(blockPos2);
        return this.canSurviveOn(levelReader, blockPos2, blockState2);
    }

    private boolean canSurviveOn(BlockGetter blockGetter, BlockPos blockPos, BlockState blockState) {
        return blockState.isFaceSturdy(blockGetter, blockPos, Direction.UP);
    }

    @Override
    protected @NonNull BlockState updateShape(@NonNull BlockState blockState, @NonNull LevelReader levelReader, @NonNull ScheduledTickAccess scheduledTickAccess, @NonNull BlockPos blockPos, @NonNull Direction direction, @NonNull BlockPos blockPos2, @NonNull BlockState blockState2, @NonNull RandomSource randomSource) {
        if (blockState.getValue(WATERLOGGED)) {
            scheduledTickAccess.scheduleTick(blockPos, Fluids.WATER, Fluids.WATER.getTickDelay(levelReader));
        }
        return !blockState.canSurvive(levelReader, blockPos) ? Blocks.AIR.defaultBlockState() : super.updateShape(blockState, levelReader, scheduledTickAccess, blockPos, direction, blockPos2, blockState2, randomSource);
    }

    @Nullable
    @SuppressWarnings("unchecked")
    protected static <E extends BlockEntity, A extends BlockEntity> BlockEntityTicker<A> createTickerHelper(
            BlockEntityType<A> serverType, BlockEntityType<E> clientType, BlockEntityTicker<? super E> ticker
    ) {
        return clientType == serverType ? (BlockEntityTicker<A>) ticker : null;
    }

    @Override
    @Nullable
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        if (!level.isClientSide() && state.getValue(OPEN)) {
            return createTickerHelper(type, ModBlocks.INCENSE_BE, IncenseBlockEntity::serverTick);
        }
        return null;
    }

    @Override
    public @NonNull InteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos,
                                                    Player player, InteractionHand hand, BlockHitResult hitResult) {
        state = state.cycle(OPEN);
        level.setBlock(pos, state, Block.UPDATE_CLIENTS);
        playSound(state, level, pos);
        return level.isClientSide() ? InteractionResult.SUCCESS : InteractionResult.SUCCESS_SERVER;
    }

    private void playSound(BlockState state, Level level, BlockPos pos) {
        SoundEvent event = state.getValue(OPEN) ? STONE_BUTTON_CLICK_ON : STONE_BUTTON_CLICK_OFF;
        level.playSound(null, pos, event, SoundSource.BLOCKS);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, OPEN, POWERED, WATERLOGGED);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        Direction opposite = context.getHorizontalDirection().getOpposite();
        boolean signal = context.getLevel().hasNeighborSignal(context.getClickedPos());
        boolean hasWater = context.getLevel().getFluidState(context.getClickedPos()).getType() == Fluids.WATER;
        return this.defaultBlockState()
                .setValue(FACING, opposite)
                .setValue(OPEN, signal)
                .setValue(WATERLOGGED, hasWater)
                .setValue(POWERED, signal);
    }

    @Override
    public void neighborChanged(BlockState state, Level level, BlockPos pos, Block block, Orientation orientation, boolean isMoving) {
        if (level.isClientSide()) {
            return;
        }
        boolean powered = level.hasNeighborSignal(pos);
        if (powered != state.getValue(POWERED)) {
            if (state.getValue(OPEN) != powered) {
                state = state.setValue(OPEN, powered);
                this.playSound(state, level, pos);
            }
            level.setBlock(pos, state.setValue(POWERED, powered), Block.UPDATE_CLIENTS);
        }
    }

    @Override
    public @NonNull VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
        boolean open = state.getValue(OPEN);

        double x = pos.getX() + 0.5;
        double y = pos.getY() + 0.5;
        double z = pos.getZ() + 0.5;

        // 小型粒子
        if (random.nextInt(3) == 0) {
            double dx = random.nextGaussian() * 0.01;
            double dy = 0.02 + random.nextDouble() * 0.01;
            double dz = random.nextGaussian() * 0.01;

            level.addParticle(smallParticle.get(), x, y, z, dx, dy, dz);
        }

        if (!open) {
            return;
        }

        // 充能后有大型粒子
        for (int i = 0; i < 5; i++) {
            double ox = x + (random.nextDouble() - 0.5) * 32;
            double oy = y + largeParticleYOffset + random.nextDouble() * largeParticleYRange;
            double oz = z + (random.nextDouble() - 0.5) * 32;

            level.addParticle(largeParticle.get(), ox, oy, oz, 0, 0, 0);
        }
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new IncenseBlockEntity(pPos, pState);
    }

    @Override
    protected @NonNull MapCodec<? extends HorizontalDirectionalBlock> codec() {
        return CODEC;
    }
}
