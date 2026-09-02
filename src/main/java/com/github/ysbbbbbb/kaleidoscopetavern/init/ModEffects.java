package com.github.ysbbbbbb.kaleidoscopetavern.init;

import com.github.ysbbbbbb.kaleidoscopetavern.KaleidoscopeTavern;
import com.github.ysbbbbbb.kaleidoscopetavern.effect.*;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;

import java.util.function.Supplier;
public final class ModEffects {

    public static Holder<MobEffect> SLIGHTLY_TIPSY = register("slightly_tipsy",() -> new BaseEffect(MobEffectCategory.NEUTRAL, 0xFFD94A));
    public static Holder<MobEffect> HIGH_HEELS = register("high_heels",() -> new HighHeelsEffect(0xE85BAA));
    public static Holder<MobEffect> GRASS_STEALTH = register("grass_stealth",() -> new GrassStealthEffect(0x71BDE7));
    public static Holder<MobEffect> VISION = register("vision",() -> new VisionEffect(0x408997));
    public static Holder<MobEffect> BLOODY_MARY = register("bloody_mary",() -> new BaseEffect(0xF73A36));
    public static Holder<MobEffect> ARDENT_HEAT = register("ardent_heat",() -> new ArdentHeatEffect(0xFF6B35));
    public static Holder<MobEffect> LONG_REACH = register("long_reach",() -> new LongReachEffect(0x8B6914));
    public static Holder<MobEffect> TOMB_RAIDER = register("tomb_raider",() -> new TombRaiderEffect(0xDAA520));
    public static Holder<MobEffect> XP_DRAIN = register("xp_drain",() -> new XpDrainEffect(0x7CFC00));
    public static Holder<MobEffect> UPSIDE_DOWN = register("upside_down",() -> new UpsideDownEffect(0x9B59B6));
    public static Holder<MobEffect> ZENITH = register("zenith",() -> new ZenithEffect(0x87CEEB));
    public static Holder<MobEffect> SHRIEK_ATTACK = register("shriek_attack",() -> new  ShriekAttackEffect(0x0D4C4A));

    private static Holder<MobEffect> register(String s, Supplier<MobEffect> effectSupplier) {
        return Registry.registerForHolder(BuiltInRegistries.MOB_EFFECT, Identifier.fromNamespaceAndPath(KaleidoscopeTavern.MOD_ID, s), effectSupplier.get());
    }

    public static void registerEffects() {
        // 加载注册
    }
}
