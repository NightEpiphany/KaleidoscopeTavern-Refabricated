package com.github.ysbbbbbb.kaleidoscopetavern.config;

import net.fabricmc.loader.api.FabricLoader;

public interface ConfigGetter {
    String ID = "forgeconfigapiport";

    static boolean getPressingTubDropContentsOnNonJuiceable() {
        return !FabricLoader.getInstance().isModLoaded(ID) || GeneralConfig.PRESSING_TUB_DROP_CONTENTS_ON_NON_JUICEABLE.get();
    }

    static boolean getInfiniteLavaFromTap() {
        return !FabricLoader.getInstance().isModLoaded(ID) || GeneralConfig.INFINITE_LAVA_FROM_TAP.get();
    }

    static boolean getWaterBottlePlacement() {
        return !FabricLoader.getInstance().isModLoaded(ID) || GeneralConfig.WATER_BOTTLE_PLACEMENT.get();
    }

    static boolean getHoneyBottlePlacement() {
        return !FabricLoader.getInstance().isModLoaded(ID) || GeneralConfig.HONEY_BOTTLE_PLACEMENT.get();
    }

    static boolean getPotionBottlePlacement() {
        return !FabricLoader.getInstance().isModLoaded(ID) || GeneralConfig.POTION_BOTTLE_PLACEMENT.get();
    }

    static boolean getDragonBreathBottlePlacement() {
        return !FabricLoader.getInstance().isModLoaded(ID) || GeneralConfig.DRAGON_BREATH_BOTTLE_PLACEMENT.get();
    }

    static boolean getExperienceBottlePlacement() {
        return !FabricLoader.getInstance().isModLoaded(ID) || GeneralConfig.EXPERIENCE_BOTTLE_PLACEMENT.get();
    }
}
