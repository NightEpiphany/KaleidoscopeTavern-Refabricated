package com.github.ysbbbbbb.kaleidoscopetavern;

import com.github.ysbbbbbb.kaleidoscopetavern.init.*;
import com.github.ysbbbbbb.kaleidoscopetavern.init.registery.CommonRegistry;
import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class KaleidoscopeTavern implements ModInitializer {
	public static final String MOD_ID = "kaleidoscope_tavern";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		CommonRegistry.init();
		ModItems.registerItems();
		ModFluids.registerFluids();
		ModBlocks.registerBlocks();
		ModRecipes.registerRecipes();
		ModEntities.registerEntities();
		ModCreativeTabs.registerTabs();
	}
}
