package com.github.ysbbbbbb.kaleidoscopetavern.fluid;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import org.jetbrains.annotations.NotNull;

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
    protected int getSlopeFindDistance(LevelReader world) {
        return slopeFindDistance;
    }

    @Override
    protected int getDropOff(LevelReader world) {
        return dropOff;
    }

    @Override
    public int getTickDelay(LevelReader world) {
        return tickRate;
    }

    @Override
    protected boolean canConvertToSource(Level world) {
        return false;
    }

    @Override
    protected float getExplosionResistance() {
        return blastResistance;
    }

    @Override
    public @NotNull BlockState createLegacyBlock(FluidState state) {
        return block.get().defaultBlockState();
    }

    @Override
    protected void beforeDestroyingBlock(LevelAccessor world, BlockPos pos, BlockState state) {
    }

    @Override
    public boolean canBeReplacedWith(FluidState state, BlockGetter world, BlockPos pos, Fluid fluid, Direction direction) {
        return false;
    }

    public static class Flowing extends JuiceFluid {
        public Flowing(Supplier<? extends FlowingFluid> flowing,
                       Supplier<? extends FlowingFluid> still,
                       Supplier<? extends Item> bucket,
                       Supplier<? extends Block> block) {
            super(flowing, still, bucket, block);
        }

        @Override
        protected void createFluidStateDefinition(StateDefinition.Builder<Fluid, FluidState> builder) {
            super.createFluidStateDefinition(builder);
            builder.add(LEVEL);
        }

        @Override
        public int getAmount(FluidState state) {
            return state.getValue(LEVEL);
        }

        @Override
        public boolean isSource(FluidState state) {
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
        public int getAmount(FluidState state) {
            return 8;
        }

        @Override
        public boolean isSource(FluidState state) {
            return true;
        }
    }
}
