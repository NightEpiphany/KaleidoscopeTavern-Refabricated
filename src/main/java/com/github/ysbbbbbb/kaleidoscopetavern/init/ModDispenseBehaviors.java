package com.github.ysbbbbbb.kaleidoscopetavern.init;

import com.github.ysbbbbbb.kaleidoscopetavern.KaleidoscopeTavern;
import com.github.ysbbbbbb.kaleidoscopetavern.block.dispenser.BottleBlockDispenseBehavior;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.Registry;
import net.minecraft.core.dispenser.DispenseBehavior;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;

import java.util.List;

@SuppressWarnings("SameParameterValue")
public final class ModDispenseBehaviors {
    public static final ResourceKey<List<DispenseBehavior>> BOTTLE = key("bottle");

    public static void registerDispenseBehaviors() {
        register("bottle", BottleBlockDispenseBehavior.CODEC);
    }

    private static ResourceKey<List<DispenseBehavior>> key(String name) {
        return ResourceKey.create(Registries.DISPENSE_BEHAVIOR,
                Identifier.fromNamespaceAndPath(KaleidoscopeTavern.MOD_ID, name));
    }

    private static void register(String name, MapCodec<? extends DispenseBehavior> codec) {
        Registry.register(BuiltInRegistries.DISPENSE_BEHAVIOR_TYPE,
                Identifier.fromNamespaceAndPath(KaleidoscopeTavern.MOD_ID, name), codec);
    }
}
