package com.github.ysbbbbbb.kaleidoscopetavern;

import com.github.ysbbbbbb.kaleidoscopetavern.init.*;
import com.github.ysbbbbbb.kaleidoscopetavern.init.register.CommonRegistry;
import com.github.ysbbbbbb.kaleidoscopetavern.network.NetworkHandler;
import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class KaleidoscopeTavern implements ModInitializer {
	public static final String MOD_ID = "kaleidoscope_tavern";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		CommonRegistry.init();
		ModFluids.registerFluids();
		ModBlocks.registerBlocks();
		ModItems.registerItems();
		ModRecipes.registerRecipes();
		ModEntities.registerEntities();
		ModCreativeTabs.registerTabs();
	}
}
