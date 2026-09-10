package com.github.ysbbbbbb.kaleidoscopetavern.block.brew;

import com.github.ysbbbbbb.kaleidoscopetavern.util.ItemUtils;
import com.github.ysbbbbbb.kaleidoscopetavern.util.PortHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jspecify.annotations.NonNull;

import java.util.Objects;

public class BottleBlock extends HorizontalDirectionalBlock implements SimpleWaterloggedBlock {
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    public static final VoxelShape SHAPE = Block.box(5, 0, 5, 11, 14, 11);
    public static final VoxelShape SIMPLE_BOTTLE_SHAPE = Block.box(5, 0, 5, 11, 10, 11);

    /**
     * 是否为异形酒瓶，这决定了酒柜中可以放入一瓶还是两瓶
     * @deprecated 现在通过 Item Tag 来决定了，不应当再使用此 tag
     */
    @SuppressWarnings("all")
    @Deprecated(forRemoval = true)
    private final boolean irregular = false;

    private @Nullable VoxelShape shape;

    public BottleBlock(String id, @Nullable VoxelShape shape) {
        this.shape = shape;
        this(id);
    }

    public BottleBlock(String id) {
        super(Properties.of()
                .noOcclusion()
                .instabreak()
                .pushReaction(PushReaction.POPPED)
                .setId(PortHelper.createBlockId(id))
                .sound(SoundType.GLASS));
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(FACING, Direction.NORTH)
                .setValue(WATERLOGGED, false));
    }

    public BottleBlock(Properties properties, @Nullable VoxelShape shape) {
        this.shape = shape;
        this(properties);
    }

    public BottleBlock(Properties properties) {
        super(properties
                .noOcclusion()
                .instabreak()
                .pushReaction(PushReaction.POPPED)
                .sound(SoundType.GLASS));
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(FACING, Direction.NORTH)
                .setValue(WATERLOGGED, false));
    }


    @SuppressWarnings("unused")
    @Deprecated(forRemoval = true)
    public BottleBlock(Properties properties, boolean irregular) {
        this(properties, null);
    }

    public static BottleBlock simpleBottle(Properties properties) {
        return new BottleBlock(properties, SIMPLE_BOTTLE_SHAPE);
    }

    @Override
    protected @NonNull InteractionResult useWithoutItem(@NonNull BlockState state, @NonNull Level level, @NonNull BlockPos pos, @NonNull Player player, @NonNull BlockHitResult blockHitResult) {
        // 如果是空手，那么可以尝试取回
        if (level instanceof ServerLevel serverLevel) {
            getDrops(state, serverLevel, pos, level.getBlockEntity(pos))
                    .forEach(stack -> ItemUtils.giveItemToPlayer(player, stack));
            level.setBlock(pos, Blocks.AIR.defaultBlockState(), Block.UPDATE_SUPPRESS_DROPS | Block.UPDATE_ALL);
            level.playSound(null, pos, SoundType.STONE.getPlaceSound(), player.getSoundSource(), 1.0F, 1.0F);
        }
        return InteractionResult.SUCCESS;
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
        FluidState fluidState = context.getLevel().getFluidState(context.getClickedPos());
        return this.defaultBlockState()
                .setValue(FACING, context.getHorizontalDirection().getOpposite())
                .setValue(WATERLOGGED, fluidState.getType() == Fluids.WATER);
    }

    @Override
    public void onProjectileHit(Level level, @NonNull BlockState state, @NonNull BlockHitResult hit, @NonNull Projectile projectile) {
        if (!level.isClientSide() && level instanceof ServerLevel serverLevel) {
            BlockPos pos = hit.getBlockPos();
            if (projectile.mayInteract(serverLevel, pos)) {
                level.removeBlock(pos, false);
                // 播放玻璃的粒子效果
                int id = Block.getId(Blocks.GLASS.defaultBlockState());
                level.levelEvent(LevelEvent.PARTICLES_DESTROY_BLOCK, pos, id);
            }
        }
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, WATERLOGGED);
    }

    @Override
    public @NotNull FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    @Override
    public float getShadeBrightness(@NonNull BlockState state, @NonNull BlockGetter level, @NonNull BlockPos pos) {
        return 1.0F;
    }

    @Override
    public @NotNull VoxelShape getShape(@NonNull BlockState pState, @NonNull BlockGetter pLevel, @NonNull BlockPos pPos, @NonNull CollisionContext pContext) {
        return Objects.requireNonNullElse(this.shape, SHAPE);
    }

    /**
     * 是否为异形酒瓶，这决定了酒柜中可以放入一瓶还是两瓶
     * @deprecated 现在通过 Item Tag 来决定了，不应当再使用此 tag
     */
    @Deprecated(forRemoval = true)
    public boolean irregular() {
        return this.irregular;
    }
}
