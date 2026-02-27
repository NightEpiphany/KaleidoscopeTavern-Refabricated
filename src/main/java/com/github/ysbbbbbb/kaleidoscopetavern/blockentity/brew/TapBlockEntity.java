package com.github.ysbbbbbb.kaleidoscopetavern.blockentity.brew;

import com.github.ysbbbbbb.kaleidoscopetavern.block.brew.TapBlock;
import com.github.ysbbbbbb.kaleidoscopetavern.blockentity.BaseBlockEntity;
import com.github.ysbbbbbb.kaleidoscopetavern.init.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class TapBlockEntity extends BaseBlockEntity {
    private @Nullable ParticleOptions particle = null;
    private int tickCounter = 0;

    public TapBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlocks.TAP_BE, pos, state);
    }

    public void tick(Level level) {
        BlockState state = this.getBlockState();
        if (!state.getValue(TapBlock.OPEN)) {
            return;
        }

        if (this.particle == null) {
            return;
        }

        this.tickCounter++;

        if (this.tickCounter > 38) {
            return;
        }
        if (level instanceof ServerLevel serverLevel) {
            BlockPos pos = this.getBlockPos();
            serverLevel.sendParticles(particle,
                    pos.getX() + 0.5, pos.getY() + 0.25, pos.getZ() + 0.5,
                    1, 0, 0, 0, 0);
        }
    }

    public void setParticle(@Nullable ParticleOptions particle) {
        this.particle = particle;
        this.tickCounter = 0;
    }
}
