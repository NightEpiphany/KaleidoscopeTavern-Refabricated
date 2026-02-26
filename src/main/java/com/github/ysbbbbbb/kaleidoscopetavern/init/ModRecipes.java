package com.github.ysbbbbbb.kaleidoscopetavern.init;

import com.github.ysbbbbbb.kaleidoscopetavern.KaleidoscopeTavern;
import com.github.ysbbbbbb.kaleidoscopetavern.crafting.recipe.BarrelRecipe;
import com.github.ysbbbbbb.kaleidoscopetavern.crafting.recipe.PressingTubRecipe;
import com.github.ysbbbbbb.kaleidoscopetavern.crafting.serializer.BarrelRecipeSerializer;
import com.github.ysbbbbbb.kaleidoscopetavern.crafting.serializer.PressingTubRecipeSerializer;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;

public class ModRecipes {
    public static final RecipeSerializer<PressingTubRecipe> PRESSING_TUB_SERIALIZER = new PressingTubRecipeSerializer();
    public static final RecipeSerializer<BarrelRecipe> BARREL_SERIALIZER = new BarrelRecipeSerializer();

    public static final RecipeType<PressingTubRecipe> PRESSING_TUB_RECIPE = simple(new ResourceLocation(KaleidoscopeTavern.MOD_ID, "pressing_tub"));
    public static final RecipeType<BarrelRecipe> BARREL_RECIPE = simple(new ResourceLocation(KaleidoscopeTavern.MOD_ID, "barrel"));

    public static void registerRecipes() {
        Registry.register(BuiltInRegistries.RECIPE_SERIALIZER, new ResourceLocation(KaleidoscopeTavern.MOD_ID, "pressing_tub"), PRESSING_TUB_SERIALIZER);
        Registry.register(BuiltInRegistries.RECIPE_TYPE, new ResourceLocation(KaleidoscopeTavern.MOD_ID, "pressing_tub"), PRESSING_TUB_RECIPE);
        Registry.register(BuiltInRegistries.RECIPE_SERIALIZER, new ResourceLocation(KaleidoscopeTavern.MOD_ID, "barrel"), BARREL_SERIALIZER);
        Registry.register(BuiltInRegistries.RECIPE_TYPE, new ResourceLocation(KaleidoscopeTavern.MOD_ID, "barrel"), BARREL_RECIPE);
    }
    private static <T extends Recipe<?>> RecipeType<T> simple(final ResourceLocation id) {
        return new RecipeType<>() {
            @Override
            public String toString() {
                return id.toString();
            }
        };
    }
}
