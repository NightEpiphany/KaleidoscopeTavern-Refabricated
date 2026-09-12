package com.github.ysbbbbbb.kaleidoscopetavern.datagen.recipe;

import net.minecraft.advancements.Advancement;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.world.item.crafting.Recipe;

public class ShapedRecipeProvider extends ModRecipeProvider {
    public ShapedRecipeProvider(final BootstrapContext<Recipe<?>> recipeOutput, final BootstrapContext<Advancement> advancementOutput) {
        super(recipeOutput, advancementOutput);
    }

    @Override
    public void buildRecipes(RecipeOutput consumer) {
    }

    @Override
    public void buildRecipes() {

    }
}
