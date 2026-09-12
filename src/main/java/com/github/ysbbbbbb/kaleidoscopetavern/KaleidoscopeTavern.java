package com.github.ysbbbbbb.kaleidoscopetavern;

import com.github.ysbbbbbb.kaleidoscopetavern.compat.jei.ModJeiPlugin;
import com.github.ysbbbbbb.kaleidoscopetavern.config.ConfigGetter;
import com.github.ysbbbbbb.kaleidoscopetavern.config.GeneralConfig;
import com.github.ysbbbbbb.kaleidoscopetavern.init.*;
import com.github.ysbbbbbb.kaleidoscopetavern.init.registery.CommonRegistry;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class KaleidoscopeTavern implements ModInitializer {

    public static final String MOD_ID = "kaleidoscope_tavern";

    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
    @Override
    public void onInitialize() {
        if (FabricLoader.getInstance().isModLoaded(ConfigGetter.ID))
            GeneralConfig.init();
        CommonRegistry.init();
        ModEffects.registerEffects();
        ModDataComponents.register();
        ModItems.registerItems();
        ModFluids.registerFluids();
        ModBlocks.registerBlocks();
        ModRecipes.registerRecipes();
        ModEntities.init();
        ModCreativeTabs.registerTabs();
        ModParticles.registerParticles();
        ModSounds.registerSounds();
        if (FabricLoader.getInstance().isModLoaded("jei"))
            ModJeiPlugin.syncRecipes();
    }
}
