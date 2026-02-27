package com.github.ysbbbbbb.kaleidoscopetavern.init.registery;

import com.github.ysbbbbbb.kaleidoscopetavern.datamap.resources.DrinkEffectDataReloadListener;
import com.github.ysbbbbbb.kaleidoscopetavern.network.NetworkHandler;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.minecraft.server.packs.PackType;

public class CommonRegistry {
    public static void init() {
        NetworkHandler.init();
        ResourceManagerHelper.get(PackType.SERVER_DATA).registerReloadListener(new DrinkEffectDataReloadListener());
    }
}
