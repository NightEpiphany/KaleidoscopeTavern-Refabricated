package com.github.ysbbbbbb.kaleidoscopetavern.client.init;

import com.github.ysbbbbbb.kaleidoscopetavern.client.particle.*;
import com.github.ysbbbbbb.kaleidoscopetavern.init.ModParticles;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.minecraft.client.particle.FallingLeavesParticle;
import net.minecraft.client.particle.Particle;

@Environment(EnvType.CLIENT)
public final class ModParticleFactoryRegistry {

    public static void init() {
        ParticleFactoryRegistry registry = ParticleFactoryRegistry.getInstance();

        // 龙头粒子
        registry.register(ModParticles.WATER_TAP_DRIP, TapDripParticle.WaterProvider::new);
        registry.register(ModParticles.LAVA_TAP_DRIP, TapDripParticle.LavaProvider::new);

        // 小型香薰粒子
        registry.register(ModParticles.SAKURA_INCENSE_PARTICLE, IncenseParticle.Provider::new);
        registry.register(ModParticles.PINE_INCENSE_PARTICLE, IncenseParticle.Provider::new);
        registry.register(ModParticles.GINKGO_INCENSE_PARTICLE, IncenseParticle.Provider::new);
        registry.register(ModParticles.SPORE_INCENSE_PARTICLE, IncenseParticle.Provider::new);
        registry.register(ModParticles.CATNIP_INCENSE_PARTICLE, IncenseParticle.Provider::new);
        registry.register(ModParticles.SNOW_INCENSE_PARTICLE, IncenseParticle.Provider::new);
        registry.register(ModParticles.BUTTERFLY_INCENSE_PARTICLE, IncenseParticle.Provider::new);
        registry.register(ModParticles.FIREFLY_INCENSE_PARTICLE, IncenseParticle.Provider::new);

        // 大型香薰粒子
        registry.register(ModParticles.PINE_INCENSE_LARGE_PARTICLE, FallingLeavesParticle.CherryProvider::new);
        registry.register(ModParticles.GINKGO_INCENSE_LARGE_PARTICLE, spriteSet ->
                (type, level, x, y, z, xSpeed, ySpeed, zSpeed, randomSource) -> {
                    Particle particle = new FallingLeavesParticle.CherryProvider(spriteSet)
                            .createParticle(type, level, x, y, z, xSpeed, ySpeed, zSpeed, randomSource);
                    particle.scale(1.5f);
                    return particle;
                });
        registry.register(ModParticles.CATNIP_INCENSE_LARGE_PARTICLE, IncenseSuspendedParticle.Provider::new);
        registry.register(ModParticles.SNOW_INCENSE_LARGE_PARTICLE, FallingLeavesParticle.CherryProvider::new);
        registry.register(ModParticles.BUTTERFLY_INCENSE_LARGE_PARTICLE, ButterflyIncenseLargeParticle.Provider::new);
        registry.register(ModParticles.FIREFLY_INCENSE_LARGE_PARTICLE, FireflyIncenseLargeParticle.Provider::new);
    }
}