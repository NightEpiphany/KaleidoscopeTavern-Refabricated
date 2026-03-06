package com.github.ysbbbbbb.kaleidoscopetavern.fluid;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.NonNull;

import java.util.function.Supplier;

public abstract class JuiceFluid extends FlowingFluid {
    private final Supplier<? extends FlowingFluid> flowing;
    private final Supplier<? extends FlowingFluid> still;
    private final Supplier<? extends Item> bucket;
    private final Supplier<? extends Block> block;
    private final int tickRate;
    private final int slopeFindDistance;
    private final int dropOff;
    private final float blastResistance;

    protected JuiceFluid(Supplier<? extends FlowingFluid> flowing,
                         Supplier<? extends FlowingFluid> still,
                         Supplier<? extends Item> bucket,
                         Supplier<? extends Block> block) {
        this(flowing, still, bucket, block, 5, 4, 1, 100.0F);
    }

    protected JuiceFluid(Supplier<? extends FlowingFluid> flowing,
                         Supplier<? extends FlowingFluid> still,
                         Supplier<? extends Item> bucket,
                         Supplier<? extends Block> block,
                         int tickRate,
                         int slopeFindDistance,
                         int dropOff,
                         float blastResistance) {
        this.flowing = flowing;
        this.still = still;
        this.bucket = bucket;
        this.block = block;
        this.tickRate = tickRate;
        this.slopeFindDistance = slopeFindDistance;
        this.dropOff = dropOff;
        this.blastResistance = blastResistance;
    }

    @Override
    public @NotNull Fluid getFlowing() {
        return flowing.get();
    }

    @Override
    public @NotNull Fluid getSource() {
        return still.get();
    }

    @Override
    public @NotNull Item getBucket() {
        return bucket.get();
    }

    @Override
    protected int getSlopeFindDistance(@NonNull LevelReader world) {
        return slopeFindDistance;
    }

    @Override
    protected int getDropOff(@NonNull LevelReader world) {
        return dropOff;
    }

    @Override
    public int getTickDelay(@NonNull LevelReader world) {
        return tickRate;
    }



    @Override
    protected float getExplosionResistance() {
        return blastResistance;
    }

    @Override
    public @NotNull BlockState createLegacyBlock(@NonNull FluidState state) {
        return block.get().defaultBlockState().setValue(LiquidBlock.LEVEL, getLegacyLevel(state));
    }

    @Override
    protected void beforeDestroyingBlock(@NonNull LevelAccessor world, @NonNull BlockPos pos, BlockState state) {
        BlockEntity blockEntity = state.hasBlockEntity() ? world.getBlockEntity(pos) : null;
        Block.dropResources(state, world, pos, blockEntity);
    }

    @Override
    public boolean canBeReplacedWith(@NonNull FluidState state, @NonNull BlockGetter world, @NonNull BlockPos pos, @NonNull Fluid fluid, @NonNull Direction direction) {
        return direction == Direction.DOWN && !fluid.isSame(this);
    }

    @Override
    public boolean isSame(@NonNull Fluid fluid) {
        return fluid == getFlowing() || fluid == getSource();
    }

    public static class Flowing extends JuiceFluid {
        public Flowing(Supplier<? extends FlowingFluid> flowing,
                       Supplier<? extends FlowingFluid> still,
                       Supplier<? extends Item> bucket,
                       Supplier<? extends Block> block) {
            super(flowing, still, bucket, block);
        }

        @Override
        protected void createFluidStateDefinition(StateDefinition.@NonNull Builder<Fluid, FluidState> builder) {
            super.createFluidStateDefinition(builder);
            builder.add(LEVEL);
        }

        @Override
        protected boolean canConvertToSource(@NonNull ServerLevel serverLevel) {
            return false;
        }

        @Override
        public int getAmount(FluidState state) {
            return state.getValue(LEVEL);
        }

        @Override
        public boolean isSource(@NonNull FluidState state) {
            return false;
        }
    }

    public static class Still extends JuiceFluid {
        public Still(Supplier<? extends FlowingFluid> flowing,
                     Supplier<? extends FlowingFluid> still,
                     Supplier<? extends Item> bucket,
                     Supplier<? extends Block> block) {
            super(flowing, still, bucket, block);
        }

        @Override
        protected boolean canConvertToSource(@NonNull ServerLevel serverLevel) {
            return false;
        }

        @Override
        public int getAmount(@NonNull FluidState state) {
            return 8;
        }

        @Override
        public boolean isSource(@NonNull FluidState state) {
            return true;
        }
    }
}