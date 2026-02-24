package com.github.ysbbbbbb.kaleidoscopetavern.datagen.recipe;

import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.FinishedRecipe;

import java.util.function.Consumer;

public class BarrelRecipeProvider extends ModRecipeProvider {
    public BarrelRecipeProvider(PackOutput output) {
        super(output);
    }

    @Override
    public void buildRecipes(Consumer<FinishedRecipe> consumer) {
        // TODO: 在此添加酒桶配方
    }
}
