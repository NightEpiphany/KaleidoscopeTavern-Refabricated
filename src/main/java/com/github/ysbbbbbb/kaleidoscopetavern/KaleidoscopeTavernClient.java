package com.github.ysbbbbbb.kaleidoscopetavern;

import com.github.ysbbbbbb.kaleidoscopetavern.client.init.ClientSetupEvent;
import com.github.ysbbbbbb.kaleidoscopetavern.client.init.ModEntitiesRender;
import com.github.ysbbbbbb.kaleidoscopetavern.client.registry.ClientRegistry;
import com.github.ysbbbbbb.kaleidoscopetavern.init.ModFluids;
import com.github.ysbbbbbb.kaleidoscopetavern.network.NetworkHandler;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public class KaleidoscopeTavernClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ClientRegistry.init();
        ModFluids.registerFluidRenderers();
        ClientSetupEvent.init();
        ModEntitiesRender.init();
    }
}
