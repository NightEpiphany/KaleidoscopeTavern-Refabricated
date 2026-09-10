package com.github.ysbbbbbb.kaleidoscopetavern.block.mixology;

import com.github.ysbbbbbb.kaleidoscopetavern.api.client.IModelModifyRotationAfterBake;
import com.github.ysbbbbbb.kaleidoscopetavern.util.ItemUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ScheduledTickAccess;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.RotationSegment;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jspecify.annotations.NonNull;

public class GlasswareBlock extends Block implements SimpleWaterloggedBlock, IModelModifyRotationAfterBake<IntegerProperty> {
    public static final IntegerProperty ROTATION = BlockStateProperties.ROTATION_16;
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    public static final VoxelShape SHAPE = Block.box(4, 0, 4, 12, 10, 12);

    public GlasswareBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(ROTATION, 0)
                .setValue(WATERLOGGED, false));
    }

    public GlasswareBlock() {
        this(Properties.of()
                .noOcclusion()
                .instabreak()
                .pushReaction(PushReaction.POPPED)
                .sound(SoundType.GLASS));
    }

    @Override
    protected @NotNull InteractionResult useItemOn(@NonNull ItemStack stack, @NonNull BlockState state, @NonNull Level level, @NonNull BlockPos pos,
                                                   Player player, @NonNull InteractionHand hand, @NonNull BlockHitResult hitResult) {
        // 濡傛灉鏄┖鎵嬶紝閭ｄ箞鍙互灏濊瘯鍙栧洖
        if (!player.getItemInHand(hand).isEmpty()) {
            return InteractionResult.PASS;
        }
        if (level instanceof ServerLevel serverLevel) {
            getDrops(state, serverLevel, pos, level.getBlockEntity(pos))
                    .forEach(s -> ItemUtils.giveItemToPlayer(player, s));
            level.setBlock(pos, Blocks.AIR.defaultBlockState(), Block.UPDATE_SUPPRESS_DROPS | Block.UPDATE_ALL);
            level.playSound(null, pos, SoundType.STONE.getPlaceSound(), player.getSoundSource(), 1.0F, 1.0F);
        }
        return InteractionResult.SUCCESS;
    }

    @Override
    protected @NotNull BlockState updateShape(BlockState state, @NonNull LevelReader level, @NonNull ScheduledTickAccess scheduledTickAccess,
                                              @NonNull BlockPos pos, @NonNull Direction direction, @NonNull BlockPos neighborPos,
                                              @NonNull BlockState neighborState, @NonNull RandomSource random) {
        if (state.getValue(WATERLOGGED)) {
            scheduledTickAccess.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
        }
        return super.updateShape(state, level, scheduledTickAccess, pos, direction, neighborPos, neighborState, random);
    }

    @Override
    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        boolean isWaterAt = context.getLevel().isWaterAt(context.getClickedPos());
        int rotation = RotationSegment.convertToSegment(context.getRotation());
        return this.defaultBlockState()
                .setValue(ROTATION, rotation)
                .setValue(WATERLOGGED, isWaterAt);
    }

    @Override
    public void onProjectileHit(Level level, @NonNull BlockState state, @NonNull BlockHitResult hit, @NonNull Projectile projectile) {
        if (!level.isClientSide()) {
            BlockPos pos = hit.getBlockPos();
            if (level instanceof ServerLevel serverLevel && projectile.mayInteract(serverLevel, pos)) {
                level.removeBlock(pos, false);
                int id = Block.getId(Blocks.GLASS.defaultBlockState());
                level.levelEvent(LevelEvent.PARTICLES_DESTROY_BLOCK, pos, id);
            }
        }
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(ROTATION, WATERLOGGED);
    }

    @Override
    public @NotNull FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    @Override
    public @NotNull VoxelShape getShape(@NonNull BlockState pState, @NonNull BlockGetter pLevel, @NonNull BlockPos pPos, @NonNull CollisionContext pContext) {
        return SHAPE;
    }

    @Override
    public float getShadeBrightness(@NonNull BlockState pState, @NonNull BlockGetter pLevel, @NonNull BlockPos pPos) {
        return 1.0F;
    }

    @Override
    public @NotNull BlockState rotate(BlockState state, Rotation rotation) {
        int max = RotationSegment.getMaxSegmentIndex() + 1;
        return state.setValue(ROTATION, rotation.rotate(state.getValue(ROTATION), max));
    }

    @Override
    public @NotNull BlockState mirror(BlockState pState, Mirror pMirror) {
        int max = RotationSegment.getMaxSegmentIndex() + 1;
        return pState.setValue(ROTATION, pMirror.mirror(pState.getValue(ROTATION), max));
    }

    @Override
    public IntegerProperty getRotationProperty() {
        return ROTATION;
    }
}
