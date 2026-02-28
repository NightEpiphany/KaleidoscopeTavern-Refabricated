package com.github.ysbbbbbb.kaleidoscopetavern.init;

import com.github.ysbbbbbb.kaleidoscopetavern.datagen.datamap.DrinkEffectDataProvider;
import com.github.ysbbbbbb.kaleidoscopetavern.datagen.loottable.LootTableGenerator;
import com.github.ysbbbbbb.kaleidoscopetavern.datagen.model.BlockModelsGenerator;
import com.github.ysbbbbbb.kaleidoscopetavern.datagen.recipe.ModRecipeGenerator;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

public class ModDataGen implements DataGeneratorEntrypoint {
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
        FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();
        pack.addProvider(DrinkEffectDataProvider::new);
        pack.addProvider(LootTableGenerator::new);
        pack.addProvider(ModRecipeGenerator::new);
        pack.addProvider(BlockModelsGenerator::new);
    }
}
