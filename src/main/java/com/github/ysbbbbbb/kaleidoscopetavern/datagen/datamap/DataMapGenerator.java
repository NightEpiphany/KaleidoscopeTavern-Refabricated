package com.github.ysbbbbbb.kaleidoscopetavern.datagen.datamap;


import com.github.ysbbbbbb.kaleidoscopetavern.init.ModItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Item;

import java.util.concurrent.CompletableFuture;

public class DataMapGenerator {

    public DataMapGenerator(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> lookupProvider) {

    }


    protected void gather(HolderLookup.Provider provider) {

        addCompostable(ModItems.GRAPEVINE, 0.25F);
        addCompostable(ModItems.GRAPE, 0.5F);
        addCompostable(ModItems.ICE_GRAPE, 0.5F);
        addCompostable(ModItems.GOLD_GRAPE, 0.5F);
        addCompostable(ModItems.GREEN_GRAPE, 0.5F);
    }

    private void addCompostable(Item item, float chance) {

    }
}
