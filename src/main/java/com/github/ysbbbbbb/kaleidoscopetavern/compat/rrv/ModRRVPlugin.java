package com.github.ysbbbbbb.kaleidoscopetavern.compat.rrv;

import cc.cassian.rrv.api.ReliableRecipeViewerClientPlugin;
import cc.cassian.rrv.api.recipe.ItemView;
import cc.cassian.rrv.api.recipe.ReliableClientRecipe;
import cc.cassian.rrv.backport.ItemStackTemplate;
import cc.cassian.rrv.client.recipe.ClientRecipeManager;
import com.github.ysbbbbbb.kaleidoscopetavern.KaleidoscopeTavern;
import com.github.ysbbbbbb.kaleidoscopetavern.compat.rrv.barrel.BarrelViewRecipe;
import com.github.ysbbbbbb.kaleidoscopetavern.compat.rrv.pressing_tub.PressingTubViewRecipe;
import com.github.ysbbbbbb.kaleidoscopetavern.compat.rrv.shaker.ShakerViewRecipe;
import com.github.ysbbbbbb.kaleidoscopetavern.init.ModRecipes;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.ArrayList;
import java.util.List;

public class ModRRVPlugin implements ReliableRecipeViewerClientPlugin {
    @Override
    public void onIntegrationInitialize() {
        KaleidoscopeTavern.LOGGER.info("Initializing RRV integration");
        ItemView.addClientRecipeProvider(recipeList -> {
            addBarrelRecipes(recipeList);
            addPressingTubRecipes(recipeList);
            addShakerRecipes(recipeList);
        });
    }

    private static void addBarrelRecipes(List<ReliableClientRecipe> recipeList) {
        ClientRecipeManager.INSTANCE.getRecipesForType(ModRecipes.BARREL_RECIPE).forEach(holder -> {
            var recipe = holder.value();
            List<Ingredient> ingredients = new ArrayList<>();
            ingredients.addFirst(Ingredient.of(recipe.fluid().getBucket()));
            ingredients.addAll(recipe.ingredients());
            recipeList.add(new BarrelViewRecipe(holder.id().identifier(), ItemStackTemplate.fromNonEmptyStack(recipe.result()), ingredients, recipe.carrier()));
        });
    }

    private static void addPressingTubRecipes(List<ReliableClientRecipe> recipeList) {
        ClientRecipeManager.INSTANCE.getRecipesForType(ModRecipes.PRESSING_TUB_RECIPE).forEach(holder -> {
            var recipe = holder.value();
            recipeList.add(new PressingTubViewRecipe(holder.id().identifier(), ItemStackTemplate.fromNonEmptyStack(recipe.getResult()), recipe.getIngredient()));
        });
    }

    private static void addShakerRecipes(List<ReliableClientRecipe> recipeList) {
        ClientRecipeManager.INSTANCE.getRecipesForType(ModRecipes.SHAKER_RECIPE).forEach(holder -> {
            var recipe = holder.value();
            recipeList.add(new ShakerViewRecipe(holder.id().identifier(), ItemStackTemplate.fromNonEmptyStack(recipe.result()), recipe.ingredients()));
        });
    }
}
