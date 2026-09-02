package com.github.ysbbbbbb.kaleidoscopetavern.block.deco;

import com.github.ysbbbbbb.kaleidoscopetavern.block.AbstractStorageBlock;
import com.github.ysbbbbbb.kaleidoscopetavern.blockentity.deco.CircularRackBlockEntity;
import com.github.ysbbbbbb.kaleidoscopetavern.init.tag.TagMod;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.Mth;
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
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jspecify.annotations.NonNull;
import org.jetbrains.annotations.Nullable;

public class CircularRackBlock extends AbstractStorageBlock implements SimpleWaterloggedBlock {
    public static final VoxelShape SHAPE = Block.box(0, 0, 0, 16, 2, 16);
    private static final MapCodec<CircularRackBlock> CODEC = simpleCodec(p -> new CircularRackBlock());
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    public CircularRackBlock() {
        super(Properties.of()
                .mapColor(MapColor.WOOD)
                .strength(2.5F)
                .sound(SoundType.WOOD)
                .noOcclusion()
                .lightLevel(s -> 14)
                .ignitedByLava());
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(FACING, Direction.NORTH)
                .setValue(WATERLOGGED, false)
                .setValue(POWERED, false));
    }

    public CircularRackBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(FACING, Direction.NORTH)
                .setValue(WATERLOGGED, false)
                .setValue(POWERED, false));
    }

    @Override
    public @NonNull FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    @Override
    protected @NonNull BlockState updateShape(@NonNull BlockState blockState, @NonNull LevelReader levelReader, @NonNull ScheduledTickAccess scheduledTickAccess, @NonNull BlockPos blockPos, @NonNull Direction direction, @NonNull BlockPos blockPos2, @NonNull BlockState blockState2, @NonNull RandomSource randomSource) {
        if (blockState.getValue(WATERLOGGED)) {
            scheduledTickAccess.scheduleTick(blockPos, Fluids.WATER, Fluids.WATER.getTickDelay(levelReader));
        }
        return super.updateShape(blockState, levelReader, scheduledTickAccess, blockPos, direction, blockPos2, blockState2, randomSource);
    }

    @Override
    public @Nullable BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockState stateForPlacement = super.getStateForPlacement(context);
        if (stateForPlacement == null)
            return null;
        boolean hasWater = context.getLevel().getFluidState(context.getClickedPos()).getType() == Fluids.WATER;
        return stateForPlacement.setValue(WATERLOGGED, hasWater);
    }

    @Override
    protected @NonNull InteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos,
                                                       Player player, InteractionHand hand, BlockHitResult hitResult) {
        return super.handleUse(state, level, pos, player, hand, hitResult);
    }

    @Override
    protected boolean blockListCheck(ItemStack stack) {
        return stack.is(TagMod.CIRCULAR_RACK_BLOCKLIST);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(WATERLOGGED);
    }

    @Override
    protected int getClickedSlot(Direction direction, BlockPos pos, BlockHitResult hitResult) {
        double localX = getLocalX(direction, pos, hitResult);
        double localZ = getLocalZ(direction, pos, hitResult);

        // 依据 x z 计算角度，然后决定位置
        double angle = Math.atan2(localZ - 0.5, localX - 0.5) * Mth.RAD_TO_DEG;
        angle = (angle + 360) % 360;

        if (angle > 300) {
            return 5;
        } else if (angle > 240) {
            return 0;
        } else if (angle > 180) {
            return 1;
        } else if (angle > 120) {
            return 2;
        } else if (angle > 60) {
            return 3;
        } else {
            return 4;
        }
    }

    @Override
    protected Vec3 getShootPos(Direction direction, BlockPos pos, int slot) {
        return Vec3.atCenterOf(pos);
    }

    @Override
    protected Vec3 getMovement(Direction direction, BlockPos pos, int slot) {
        double factor = Math.random() * 2 + 0.5;
        return new Vec3(0, factor, 0);
    }

    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
        if (random.nextInt(8) != 0) {
            return;
        }
        if (level.getBlockEntity(pos) instanceof CircularRackBlockEntity rack && rack.hasAnyItem()) {
            // 让粒子在边线飘动
            double x = pos.getX();
            double y = pos.getY();
            double z = pos.getZ();

            x = random.nextBoolean() ? x + 0.125 + random.nextDouble() * 0.25 : x + 0.875 - random.nextDouble() * 0.25;
            y = y + random.nextDouble();
            z = random.nextBoolean() ? z + 0.125 + random.nextDouble() * 0.25 : z + 0.875 - random.nextDouble() * 0.25;

            level.addParticle(ParticleTypes.END_ROD, x, y, z, 0.01, 0.01, 0.01);
        }
    }

    @Override
    @Nullable
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new CircularRackBlockEntity(pos, state);
    }

    @Override
    public @NonNull VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @Override
    protected @NonNull MapCodec<? extends HorizontalDirectionalBlock> codec() {
        return CODEC;
    }
}

