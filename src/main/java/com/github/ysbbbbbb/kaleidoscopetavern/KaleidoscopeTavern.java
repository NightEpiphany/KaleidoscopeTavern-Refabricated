package com.github.ysbbbbbb.kaleidoscopetavern;

import com.github.ysbbbbbb.kaleidoscopetavern.init.ModBlocks;
import com.github.ysbbbbbb.kaleidoscopetavern.init.ModCreativeTabs;
import com.github.ysbbbbbb.kaleidoscopetavern.init.ModEntities;
import com.github.ysbbbbbb.kaleidoscopetavern.init.ModItems;
import com.mojang.logging.LogUtils;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

@Mod(KaleidoscopeTavern.MOD_ID)
public class KaleidoscopeTavern {
    public static final String MOD_ID = "kaleidoscope_tavern";
    public static final Logger LOGGER = LogUtils.getLogger();

    public KaleidoscopeTavern() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        ModBlocks.BLOCKS.register(modEventBus);
        ModBlocks.BLOCK_ENTITIES.register(modEventBus);
        ModItems.ITEMS.register(modEventBus);
        ModCreativeTabs.TABS.register(modEventBus);
        ModEntities.ENTITY_TYPES.register(modEventBus);
    }
}
