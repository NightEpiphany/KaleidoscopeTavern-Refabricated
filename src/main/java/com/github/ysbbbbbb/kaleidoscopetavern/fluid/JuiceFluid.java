package com.github.ysbbbbbb.kaleidoscopetavern.fluid;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.NonNull;

public final class JuiceFluid extends FlowingFluid {
    private final ResourceKey<Block> blockKey;
    private final int tickRate;
    private final int slopeFindDistance;
    private final int dropOff;
    private final float blastResistance;

    public JuiceFluid(final Fluid.Properties properties,
                      ResourceKey<Block> blockKey) {
        this(properties, blockKey, 5, 4, 1, 100.0F);
    }

    public JuiceFluid(final Fluid.Properties properties,
                      ResourceKey<Block> blockKey,
                      int tickRate,
                      int slopeFindDistance,
                      int dropOff,
                      float blastResistance) {
        super(properties);
        this.blockKey = blockKey;
        this.tickRate = tickRate;
        this.slopeFindDistance = slopeFindDistance;
        this.dropOff = dropOff;
        this.blastResistance = blastResistance;
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
        return BuiltInRegistries.BLOCK.getValueOrThrow(blockKey)
                .defaultBlockState()
                .setValue(LiquidBlock.LEVEL, getLegacyLevel(state));
    }

    @Override
    protected void beforeDestroyingBlock(@NonNull LevelAccessor world, @NonNull BlockPos pos, BlockState state) {
        BlockEntity blockEntity = state.hasBlockEntity() ? world.getBlockEntity(pos) : null;
        Block.dropResources(state, world, pos, blockEntity);
    }

    @Override
    public boolean canBeReplacedWith(@NonNull FluidState state,
                                     @NonNull BlockGetter world,
                                     @NonNull BlockPos pos,
                                     @NonNull FluidState replacement,
                                     @NonNull Direction direction) {
        return direction == Direction.DOWN && replacement.getType() != this;
    }

    @Override
    protected boolean canConvertToSource(@NonNull ServerLevel serverLevel) {
        return false;
    }
}
