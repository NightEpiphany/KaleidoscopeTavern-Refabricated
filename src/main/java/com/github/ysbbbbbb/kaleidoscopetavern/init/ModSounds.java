package com.github.ysbbbbbb.kaleidoscopetavern.init;

import com.github.ysbbbbbb.kaleidoscopetavern.KaleidoscopeTavern;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.sounds.SoundEvent;

import java.util.function.Supplier;

public final class ModSounds {
    public static void registerSounds() {
    }

    public static final SoundEvent EFFECT_VISION = registerSound("effect.vision");
    public static final SoundEvent HOLDER_POP = registerSound("block.holder.pop");
    public static final SoundEvent SHAKER_SHAKING = registerSound("item.shaker.shaking");
    public static final SoundEvent SHAKER_END = registerSound("item.shaker.end");

    private static SoundEvent registerSound(String name) {
        return Registry.register(BuiltInRegistries.SOUND_EVENT, Identifier.fromNamespaceAndPath(KaleidoscopeTavern.MOD_ID, name), SoundEvent.createFixedRangeEvent(Identifier.fromNamespaceAndPath(KaleidoscopeTavern.MOD_ID, name), 16.0F));
    }
}