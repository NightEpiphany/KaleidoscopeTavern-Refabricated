package com.github.ysbbbbbb.kaleidoscopetavern.init;

import com.github.ysbbbbbb.kaleidoscopetavern.KaleidoscopeTavern;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;

public final class ModParticles {

    public static final SimpleParticleType WATER_TAP_DRIP = FabricParticleTypes.simple(true);
    public static final SimpleParticleType LAVA_TAP_DRIP = FabricParticleTypes.simple(true);

    // 小型香薰粒子
    public static final SimpleParticleType SAKURA_INCENSE_PARTICLE = FabricParticleTypes.simple(false);
    public static final SimpleParticleType PINE_INCENSE_PARTICLE = FabricParticleTypes.simple(false);
    public static final SimpleParticleType GINKGO_INCENSE_PARTICLE = FabricParticleTypes.simple(false);
    public static final SimpleParticleType SPORE_INCENSE_PARTICLE = FabricParticleTypes.simple(false);
    public static final SimpleParticleType CATNIP_INCENSE_PARTICLE = FabricParticleTypes.simple(false);
    public static final SimpleParticleType SNOW_INCENSE_PARTICLE = FabricParticleTypes.simple(false);
    public static final SimpleParticleType BUTTERFLY_INCENSE_PARTICLE = FabricParticleTypes.simple(false);
    public static final SimpleParticleType FIREFLY_INCENSE_PARTICLE = FabricParticleTypes.simple(false);

    // 大型香薰粒子
    public static final SimpleParticleType PINE_INCENSE_LARGE_PARTICLE = FabricParticleTypes.simple(false);
    public static final SimpleParticleType GINKGO_INCENSE_LARGE_PARTICLE = FabricParticleTypes.simple(false);
    public static final SimpleParticleType CATNIP_INCENSE_LARGE_PARTICLE = FabricParticleTypes.simple(false);
    public static final SimpleParticleType SNOW_INCENSE_LARGE_PARTICLE = FabricParticleTypes.simple(false);
    public static final SimpleParticleType BUTTERFLY_INCENSE_LARGE_PARTICLE = FabricParticleTypes.simple(false);
    public static final SimpleParticleType FIREFLY_INCENSE_LARGE_PARTICLE = FabricParticleTypes.simple(false);

    public static void registerParticles() {
        Registry.register(BuiltInRegistries.PARTICLE_TYPE, Identifier.fromNamespaceAndPath(KaleidoscopeTavern.MOD_ID, "water_tap_drip"), WATER_TAP_DRIP);
        Registry.register(BuiltInRegistries.PARTICLE_TYPE, Identifier.fromNamespaceAndPath(KaleidoscopeTavern.MOD_ID, "lava_tap_drip"), LAVA_TAP_DRIP);
        Registry.register(BuiltInRegistries.PARTICLE_TYPE, Identifier.fromNamespaceAndPath(KaleidoscopeTavern.MOD_ID, "sakura_incense_particle"), SAKURA_INCENSE_PARTICLE);
        Registry.register(BuiltInRegistries.PARTICLE_TYPE, Identifier.fromNamespaceAndPath(KaleidoscopeTavern.MOD_ID, "pine_incense_particle"), PINE_INCENSE_PARTICLE);
        Registry.register(BuiltInRegistries.PARTICLE_TYPE, Identifier.fromNamespaceAndPath(KaleidoscopeTavern.MOD_ID, "ginkgo_incense_particle"), GINKGO_INCENSE_PARTICLE);
        Registry.register(BuiltInRegistries.PARTICLE_TYPE, Identifier.fromNamespaceAndPath(KaleidoscopeTavern.MOD_ID, "spore_incense_particle"), SPORE_INCENSE_PARTICLE);
        Registry.register(BuiltInRegistries.PARTICLE_TYPE, Identifier.fromNamespaceAndPath(KaleidoscopeTavern.MOD_ID, "catnip_incense_particle"), CATNIP_INCENSE_PARTICLE);
        Registry.register(BuiltInRegistries.PARTICLE_TYPE, Identifier.fromNamespaceAndPath(KaleidoscopeTavern.MOD_ID, "snow_incense_particle"), SNOW_INCENSE_PARTICLE);
        Registry.register(BuiltInRegistries.PARTICLE_TYPE, Identifier.fromNamespaceAndPath(KaleidoscopeTavern.MOD_ID, "butterfly_incense_particle"), BUTTERFLY_INCENSE_PARTICLE);
        Registry.register(BuiltInRegistries.PARTICLE_TYPE, Identifier.fromNamespaceAndPath(KaleidoscopeTavern.MOD_ID, "firefly_incense_particle"), FIREFLY_INCENSE_PARTICLE);
        Registry.register(BuiltInRegistries.PARTICLE_TYPE, Identifier.fromNamespaceAndPath(KaleidoscopeTavern.MOD_ID, "pine_incense_large_particle"), PINE_INCENSE_LARGE_PARTICLE);
        Registry.register(BuiltInRegistries.PARTICLE_TYPE, Identifier.fromNamespaceAndPath(KaleidoscopeTavern.MOD_ID, "ginkgo_incense_large_particle"), GINKGO_INCENSE_LARGE_PARTICLE);
        Registry.register(BuiltInRegistries.PARTICLE_TYPE, Identifier.fromNamespaceAndPath(KaleidoscopeTavern.MOD_ID, "catnip_incense_large_particle"), CATNIP_INCENSE_LARGE_PARTICLE);
        Registry.register(BuiltInRegistries.PARTICLE_TYPE, Identifier.fromNamespaceAndPath(KaleidoscopeTavern.MOD_ID, "snow_incense_large_particle"), SNOW_INCENSE_LARGE_PARTICLE);
        Registry.register(BuiltInRegistries.PARTICLE_TYPE, Identifier.fromNamespaceAndPath(KaleidoscopeTavern.MOD_ID, "butterfly_incense_large_particle"), BUTTERFLY_INCENSE_LARGE_PARTICLE);
        Registry.register(BuiltInRegistries.PARTICLE_TYPE, Identifier.fromNamespaceAndPath(KaleidoscopeTavern.MOD_ID, "firefly_incense_large_particle"), FIREFLY_INCENSE_LARGE_PARTICLE);
    }
}
