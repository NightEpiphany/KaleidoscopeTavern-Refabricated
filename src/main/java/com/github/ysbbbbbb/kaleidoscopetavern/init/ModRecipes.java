package com.github.ysbbbbbb.kaleidoscopetavern.init;

import com.github.ysbbbbbb.kaleidoscopetavern.KaleidoscopeTavern;
import com.github.ysbbbbbb.kaleidoscopetavern.crafting.recipe.BarrelRecipe;
import com.github.ysbbbbbb.kaleidoscopetavern.crafting.recipe.PressingTubRecipe;
import com.github.ysbbbbbb.kaleidoscopetavern.crafting.recipe.ShakerRecipe;
import com.github.ysbbbbbb.kaleidoscopetavern.crafting.serializer.BarrelRecipeSerializer;
import com.github.ysbbbbbb.kaleidoscopetavern.crafting.serializer.PressingTubRecipeSerializer;
import com.github.ysbbbbbb.kaleidoscopetavern.crafting.serializer.ShakerRecipeSerializer;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;

public final class ModRecipes {
    public static final RecipeSerializer<PressingTubRecipe> PRESSING_TUB_SERIALIZER = new PressingTubRecipeSerializer();
    public static final RecipeSerializer<BarrelRecipe> BARREL_SERIALIZER = new BarrelRecipeSerializer();
    public static final RecipeSerializer<ShakerRecipe> SHAKER_SERIALIZER = new ShakerRecipeSerializer();

    public static final RecipeType<PressingTubRecipe> PRESSING_TUB_RECIPE = simple(Identifier.fromNamespaceAndPath(KaleidoscopeTavern.MOD_ID, "pressing_tub"));
    public static final RecipeType<BarrelRecipe> BARREL_RECIPE = simple(Identifier.fromNamespaceAndPath(KaleidoscopeTavern.MOD_ID, "barrel"));
    public static final RecipeType<ShakerRecipe> SHAKER_RECIPE = simple(Identifier.fromNamespaceAndPath(KaleidoscopeTavern.MOD_ID, "shaker"));

    public static void registerRecipes() {
        Registry.register(BuiltInRegistries.RECIPE_SERIALIZER, Identifier.fromNamespaceAndPath(KaleidoscopeTavern.MOD_ID, "pressing_tub"), PRESSING_TUB_SERIALIZER);
        Registry.register(BuiltInRegistries.RECIPE_TYPE, Identifier.fromNamespaceAndPath(KaleidoscopeTavern.MOD_ID, "pressing_tub"), PRESSING_TUB_RECIPE);
        Registry.register(BuiltInRegistries.RECIPE_SERIALIZER, Identifier.fromNamespaceAndPath(KaleidoscopeTavern.MOD_ID, "barrel"), BARREL_SERIALIZER);
        Registry.register(BuiltInRegistries.RECIPE_TYPE, Identifier.fromNamespaceAndPath(KaleidoscopeTavern.MOD_ID, "barrel"), BARREL_RECIPE);
        Registry.register(BuiltInRegistries.RECIPE_SERIALIZER, Identifier.fromNamespaceAndPath(KaleidoscopeTavern.MOD_ID, "shaker"), SHAKER_SERIALIZER);
        Registry.register(BuiltInRegistries.RECIPE_TYPE, Identifier.fromNamespaceAndPath(KaleidoscopeTavern.MOD_ID, "shaker"), SHAKER_RECIPE);
    }
    private static <T extends Recipe<?>> RecipeType<T> simple(final Identifier id) {
        return new RecipeType<>() {
            @Override
            public String toString() {
                return id.toString();
            }
        };
    }
}
