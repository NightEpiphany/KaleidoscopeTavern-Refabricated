package com.github.ysbbbbbb.kaleidoscopetavern.init.registery;

import com.github.ysbbbbbb.kaleidoscopetavern.block.brew.BottleBlock;
import com.github.ysbbbbbb.kaleidoscopetavern.block.dispenser.BottleBlockDispenseBehavior;
import com.github.ysbbbbbb.kaleidoscopetavern.datamap.resources.DrinkEffectDataReloadListener;
import com.github.ysbbbbbb.kaleidoscopetavern.init.ModBlocks;
import com.github.ysbbbbbb.kaleidoscopetavern.item.BottleBlockItem;
import com.github.ysbbbbbb.kaleidoscopetavern.network.NetworkHandler;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.server.packs.PackType;
import net.minecraft.world.level.block.DispenserBlock;

public class CommonRegistry {
    public static void init() {
        NetworkHandler.init();
        ResourceManagerHelper.get(PackType.SERVER_DATA).registerReloadListener(new DrinkEffectDataReloadListener());
        dispenseRegister();
    }

    public static void dispenseRegister() {
        BuiltInRegistries.BLOCK.forEach(block -> {
            if (block instanceof BottleBlock) {
                if (block.asItem() instanceof BottleBlockItem blockItem) {
                    DispenserBlock.registerBehavior(blockItem, new BottleBlockDispenseBehavior());
                }
        }});
    }
}
