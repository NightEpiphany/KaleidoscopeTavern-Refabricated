package com.github.ysbbbbbb.kaleidoscopetavern.datagen.recipe;

import com.github.ysbbbbbb.kaleidoscopetavern.datagen.builder.BarrelBuilder;
import com.github.ysbbbbbb.kaleidoscopetavern.init.ModFluids;
import com.github.ysbbbbbb.kaleidoscopetavern.init.ModItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.material.Fluids;

import java.util.concurrent.CompletableFuture;

public class BarrelRecipeProvider extends ModRecipeProvider {
    public BarrelRecipeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);
    }

    @Override
    public void buildRecipes(RecipeOutput output) {
        // 葡萄酒
        BarrelBuilder.builder()
                .setFluid(ModFluids.GRAPE_JUICE)
                .setResult(ModItems.WINE)
                .save(output);

        // 香槟
        BarrelBuilder.builder()
                .setFluid(ModFluids.GRAPE_JUICE)
                .addIngredient(Items.SUGAR)
                .setResult(ModItems.CHAMPAGNE)
                .save(output);

        // 白兰地
        BarrelBuilder.builder()
                .setFluid(ModFluids.GRAPE_JUICE)
                .addIngredient(Items.APPLE)
                .setResult(ModItems.BRANDY)
                .save(output);

        // 佳丽酿
        BarrelBuilder.builder()
                .setFluid(ModFluids.GRAPE_JUICE)
                .addIngredient(Items.SWEET_BERRIES)
                .setResult(ModItems.CARIGNAN)
                .save(output);

        // 樱花葡萄酒
        BarrelBuilder.builder()
                .setFluid(ModFluids.GRAPE_JUICE)
                .addIngredient(Items.PINK_PETALS)
                .setResult(ModItems.SAKURA_WINE)
                .save(output);

        // 冰葡萄酒
        BarrelBuilder.builder()
                .setFluid(ModFluids.GRAPE_JUICE)
                .addIngredient(Items.ICE)
                .setResult(ModItems.ICE_WINE)
                .save(output);

        // 梅酒
        BarrelBuilder.builder()
                .setFluid(ModFluids.SWEET_BERRIES_JUICE)
                .setResult(ModItems.PLUM_WINE)
                .save(output);

        // 伏特加
        BarrelBuilder.builder()
                .setFluid(Fluids.WATER)
                .addIngredient(Items.POTATO)
                .setResult(ModItems.VODKA)
                .save(output);

        // 威士忌
        BarrelBuilder.builder()
                .setFluid(Fluids.WATER)
                .addIngredient(Items.WHEAT)
                .setResult(ModItems.WHISKEY)
                .save(output);

        // 燃烧瓶
        BarrelBuilder.builder()
                .setFluid(Fluids.LAVA)
                .setResult(ModItems.MOLOTOV)
                .save(output);
    }
}
