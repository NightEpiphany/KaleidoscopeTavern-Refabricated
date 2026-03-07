package com.github.ysbbbbbb.kaleidoscopetavern.compat.create.ponder.init;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.loader.api.FabricLoader;

public class PonderCompat {
    public static final String ID = "create";

    public static boolean PONDER_LOADED = false;

    @Environment(EnvType.CLIENT)
    public static void init() {
        if (FabricLoader.getInstance().isModLoaded(ID)) {
            PONDER_LOADED = true;
            TavernPonderPlugin.init();
        }
    }
}
