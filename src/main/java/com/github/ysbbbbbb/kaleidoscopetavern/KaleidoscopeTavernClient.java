package com.github.ysbbbbbb.kaleidoscopetavern;

import com.github.ysbbbbbb.kaleidoscopetavern.client.registry.ClientRegistry;
import com.github.ysbbbbbb.kaleidoscopetavern.init.ModFluids;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public class KaleidoscopeTavernClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ClientRegistry.init();
        ModFluids.registerFluidRenderers();
    }
}
