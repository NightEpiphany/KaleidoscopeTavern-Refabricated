package com.github.ysbbbbbb.kaleidoscopetavern.datagen.recipe;

import com.github.ysbbbbbb.kaleidoscopetavern.datagen.builder.ShakerBuilder;
import com.github.ysbbbbbb.kaleidoscopetavern.init.ModItems;
import com.github.ysbbbbbb.kaleidoscopetavern.init.tag.TagMod;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeOutput;

import java.util.concurrent.CompletableFuture;

public class ShakerRecipeProvider extends ModRecipeProvider {
    public ShakerRecipeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);
    }

    @Override
    public void buildRecipes(RecipeOutput consumer) {
        // 血腥玛丽
        ShakerBuilder.builder()
                .addIngredient(TagMod.COCKTAIL_INGREDIENT_RED)
                .addIngredient(TagMod.COCKTAIL_INGREDIENT_RED)
                .addIngredient(TagMod.COCKTAIL_INGREDIENT_RED)
                .setResult(ModItems.BLOODY_MARY)
                .save(consumer);

        // 翡翠
        ShakerBuilder.builder()
                .addIngredient(TagMod.COCKTAIL_INGREDIENT_GREEN)
                .addIngredient(TagMod.COCKTAIL_INGREDIENT_GREEN)
                .addIngredient(TagMod.COCKTAIL_INGREDIENT_GREEN)
                .setResult(ModItems.EMERALD)
                .save(consumer);

        // 绿色蚱蜢
        ShakerBuilder.builder()
                .addIngredient(TagMod.COCKTAIL_INGREDIENT_GREEN)
                .addIngredient(TagMod.COCKTAIL_INGREDIENT_GREEN)
                .addIngredient(TagMod.COCKTAIL_INGREDIENT_WHITE)
                .setResult(ModItems.GRASSHOPPER)
                .save(consumer);

        // 绒球葱花园
        ShakerBuilder.builder()
                .addIngredient(TagMod.COCKTAIL_INGREDIENT_LIGHT_PURPLE)
                .addIngredient(TagMod.COCKTAIL_INGREDIENT_LIGHT_PURPLE)
                .addIngredient(TagMod.COCKTAIL_INGREDIENT_LIGHT_PURPLE)
                .setResult(ModItems.ALLIUM_GARDEN)
                .save(consumer);

        // 深水炸弹
        ShakerBuilder.builder()
                .addIngredient(TagMod.COCKTAIL_INGREDIENT_BLUE)
                .addIngredient(TagMod.COCKTAIL_INGREDIENT_LIGHT_PURPLE)
                .addIngredient(TagMod.COCKTAIL_INGREDIENT_WHITE)
                .setResult(ModItems.DEPTH_CHARGE)
                .save(consumer);

        // 螺丝起子
        ShakerBuilder.builder()
                .addIngredient(TagMod.COCKTAIL_INGREDIENT_YELLOW)
                .addIngredient(TagMod.COCKTAIL_INGREDIENT_YELLOW)
                .addIngredient(TagMod.COCKTAIL_INGREDIENT_GOLD)
                .setResult(ModItems.SCREWDRIVER)
                .save(consumer);

        // 教父
        ShakerBuilder.builder()
                .addIngredient(TagMod.COCKTAIL_INGREDIENT_RED)
                .addIngredient(TagMod.COCKTAIL_INGREDIENT_RED)
                .addIngredient(TagMod.COCKTAIL_INGREDIENT_GOLD)
                .setResult(ModItems.GODFATHER)
                .save(consumer);

        // 白色佳人
        ShakerBuilder.builder()
                .addIngredient(TagMod.COCKTAIL_INGREDIENT_WHITE)
                .addIngredient(TagMod.COCKTAIL_INGREDIENT_GREEN)
                .addIngredient(TagMod.COCKTAIL_INGREDIENT_YELLOW)
                .setResult(ModItems.WHITE_LADY)
                .save(consumer);

        // 莫吉托
        ShakerBuilder.builder()
                .addIngredient(TagMod.COCKTAIL_INGREDIENT_WHITE)
                .addIngredient(TagMod.COCKTAIL_INGREDIENT_WHITE)
                .addIngredient(TagMod.COCKTAIL_INGREDIENT_GREEN)
                .setResult(ModItems.MOJITO)
                .save(consumer);

        // 黄铜心脏
        ShakerBuilder.builder()
                .addIngredient(TagMod.COCKTAIL_INGREDIENT_GOLD)
                .addIngredient(TagMod.COCKTAIL_INGREDIENT_GOLD)
                .addIngredient(TagMod.COCKTAIL_INGREDIENT_GOLD)
                .setResult(ModItems.BRASS_HEART)
                .save(consumer);

        // 下界特调
        ShakerBuilder.builder()
                .addIngredient(TagMod.COCKTAIL_INGREDIENT_RED)
                .addIngredient(TagMod.COCKTAIL_INGREDIENT_GREEN)
                .addIngredient(TagMod.COCKTAIL_INGREDIENT_BLUE)
                .setResult(ModItems.NETHER_SPECIAL)
                .save(consumer);

        // 幽匿特调
        ShakerBuilder.builder()
                .addIngredient(TagMod.COCKTAIL_INGREDIENT_BLUE)
                .addIngredient(TagMod.COCKTAIL_INGREDIENT_BLUE)
                .addIngredient(TagMod.COCKTAIL_INGREDIENT_LIGHT_PURPLE)
                .setResult(ModItems.SCULK_SPECIAL)
                .save(consumer);
    }

    @Override
    public void buildRecipes() {

    }
}
