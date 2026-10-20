package com.github.ysbbbbbb.kaleidoscopetavern.init.registery.ids;

import com.github.ysbbbbbb.kaleidoscopetavern.KaleidoscopeTavern;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.material.Fluid;

public final class ModFluidIds {

    public static final ResourceKey<Fluid> GRAPE_JUICE = create("grape_juice");
    public static final ResourceKey<Fluid> ICE_GRAPE_JUICE = create("ice_grape_juice");
    public static final ResourceKey<Fluid> GOLD_GRAPE_JUICE = create("gold_grape_juice");
    public static final ResourceKey<Fluid> GREEN_GRAPE_JUICE = create("green_grape_juice");
    public static final ResourceKey<Fluid> SWEET_BERRIES_JUICE = create("sweet_berries_juice");
    public static final ResourceKey<Fluid> GLOW_BERRIES_JUICE = create("glow_berries_juice");

    private static ResourceKey<Fluid> create(String name) {
        return ResourceKey.create(
                Registries.FLUID,
                Identifier.fromNamespaceAndPath(KaleidoscopeTavern.MOD_ID, name)
        );
    }
}
