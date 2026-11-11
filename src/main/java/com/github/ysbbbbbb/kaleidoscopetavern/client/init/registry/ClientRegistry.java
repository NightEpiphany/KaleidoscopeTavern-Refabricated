package com.github.ysbbbbbb.kaleidoscopetavern.client.init.registry;

import com.github.ysbbbbbb.kaleidoscopetavern.client.animation.ShakerAnimation;
import com.github.ysbbbbbb.kaleidoscopetavern.client.gui.overlay.ShakerOverlay;
import com.github.ysbbbbbb.kaleidoscopetavern.client.init.ClientSetupEvent;
import com.github.ysbbbbbb.kaleidoscopetavern.client.init.ModParticleFactoryRegistry;
import com.github.ysbbbbbb.kaleidoscopetavern.client.render.misc.PotionBottleColor;
import com.github.ysbbbbbb.kaleidoscopetavern.client.render.misc.SignatureCocktailColor;
import com.github.ysbbbbbb.kaleidoscopetavern.compat.create.ponder.init.PonderCompat;
import com.github.ysbbbbbb.kaleidoscopetavern.compat.trinkets.init.TrinketsCompactClient;
import com.github.ysbbbbbb.kaleidoscopetavern.init.ModBlocks;
import com.github.ysbbbbbb.kaleidoscopetavern.init.ModFluids;
import com.github.ysbbbbbb.kaleidoscopetavern.network.NetworkHandler;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.BlockColorRegistry;

@Environment(EnvType.CLIENT)
public final class ClientRegistry {
    public static void init() {
        ShakerAnimation.trigger();
        NetworkHandler.Clientside.init();
        ClientSetupEvent.init();
        ModFluids.registerFluidRenderers();
        ModParticleFactoryRegistry.init();
        renderType();
        color();
        events();
        modCompatClient();
    }

    private static void renderType() {

    }

    private static void color() {
        BlockColorRegistry.register(new SignatureCocktailColor.Block(), ModBlocks.SIGNATURE_COCKTAIL);
        BlockColorRegistry.register(new PotionBottleColor(), ModBlocks.POTION_BOTTLE);

    }

    private static void events() {
        ShakerOverlay.register();
    }

    private static void modCompatClient() {
        PonderCompat.init();
        TrinketsCompactClient.init();
    }
}
