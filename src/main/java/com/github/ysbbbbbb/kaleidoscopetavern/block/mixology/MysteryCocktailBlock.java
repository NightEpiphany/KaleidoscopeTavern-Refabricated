package com.github.ysbbbbbb.kaleidoscopetavern.block.mixology;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SpellParticleOption;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class MysteryCocktailBlock extends CocktailBlock {
    public MysteryCocktailBlock(Properties properties) {
        super(properties);
    }

    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
        double x = pos.getX() + 0.5;
        double y = pos.getY() + 0.5;
        double z = pos.getZ() + 0.5;

        double xSpeed = random.nextDouble();
        double ySpeed = random.nextDouble();
        double zSpeed = random.nextDouble();

        level.addParticle(SpellParticleOption.create(ParticleTypes.INSTANT_EFFECT, 1.0F, 0.8F, 0.2F, 1.0F),
                x + random.nextDouble() / 5 * (random.nextBoolean() ? 1 : -1),
                y + random.nextDouble() / 5,
                z + random.nextDouble() / 5 * (random.nextBoolean() ? 1 : -1),
                xSpeed, ySpeed, zSpeed
        );
    }
}
