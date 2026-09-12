package com.github.ysbbbbbb.kaleidoscopetavern.datagen.recipe;

import net.minecraft.advancements.Advancement;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.ItemLike;

public class ShapelessRecipeProvider extends ModRecipeProvider {
    public ShapelessRecipeProvider(final BootstrapContext<Recipe<?>> recipeOutput, final BootstrapContext<Advancement> advancementOutput) {
        super(recipeOutput, advancementOutput);
    }

    @Override
    public void buildRecipes(RecipeOutput consumer) {

    }

    @SuppressWarnings("all")
    static Identifier getId(ItemLike itemLike) {
        return BuiltInRegistries.ITEM.getKey(itemLike.asItem());
    }

    @Override
    public void buildRecipes() {

    }
}