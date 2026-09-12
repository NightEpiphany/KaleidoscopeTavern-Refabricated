package com.github.ysbbbbbb.kaleidoscopetavern.datagen.recipe;

import com.google.common.collect.Lists;
import net.minecraft.advancements.Advancement;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.world.item.crafting.Recipe;

import java.util.List;

public class ModRecipeGenerator extends ModRecipeProvider {
    private final List<ModRecipeProvider> providers = Lists.newArrayList();

    public ModRecipeGenerator(final BootstrapContext<Recipe<?>> recipeOutput, final BootstrapContext<Advancement> advancementOutput) {
        super(recipeOutput, advancementOutput);
        providers.add(new PressingTubRecipeProvider(recipeOutput, advancementOutput));
        providers.add(new BarrelRecipeProvider(recipeOutput, advancementOutput));
        providers.add(new ShapedRecipeProvider(recipeOutput, advancementOutput));
        providers.add(new ShapelessRecipeProvider(recipeOutput, advancementOutput));
        providers.add(new ShakerRecipeProvider(recipeOutput, advancementOutput));
    }

    @Override
    public void buildRecipes(RecipeOutput output) {
        for (ModRecipeProvider provider : providers) {
            provider.buildRecipes(output);
        }
    }

    @Override
    public void buildRecipes() {

    }
}
