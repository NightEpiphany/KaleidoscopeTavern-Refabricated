package com.github.ysbbbbbb.kaleidoscopetavern.datagen.recipe;

import com.github.ysbbbbbb.kaleidoscopetavern.datagen.builder.BarrelBuilder;
import com.github.ysbbbbbb.kaleidoscopetavern.init.ModFluids;
import com.github.ysbbbbbb.kaleidoscopetavern.init.ModItems;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.material.Fluids;

import java.util.function.Consumer;

public class BarrelRecipeProvider extends ModRecipeProvider {
    public BarrelRecipeProvider(PackOutput output) {
        super(output);
    }

    @Override
    public void buildRecipes(Consumer<FinishedRecipe> consumer) {
        // 葡萄酒
        BarrelBuilder.builder()
                .setFluid(ModFluids.GRAPE_JUICE)
                .setResult(ModItems.WINE)
                .save(consumer);

        // 香槟
        BarrelBuilder.builder()
                .setFluid(ModFluids.GRAPE_JUICE)
                .addIngredient(Items.SUGAR)
                .setResult(ModItems.CHAMPAGNE)
                .save(consumer);

        // 白兰地
        BarrelBuilder.builder()
                .setFluid(ModFluids.GRAPE_JUICE)
                .addIngredient(Items.APPLE)
                .setResult(ModItems.BRANDY)
                .save(consumer);

        // 佳丽酿
        BarrelBuilder.builder()
                .setFluid(ModFluids.GRAPE_JUICE)
                .addIngredient(Items.SWEET_BERRIES)
                .setResult(ModItems.CARIGNAN)
                .save(consumer);

        // 樱花葡萄酒
        BarrelBuilder.builder()
                .setFluid(ModFluids.GRAPE_JUICE)
                .addIngredient(Items.PINK_PETALS)
                .setResult(ModItems.SAKURA_WINE)
                .save(consumer);

        // 冰葡萄酒
        BarrelBuilder.builder()
                .setFluid(ModFluids.GRAPE_JUICE)
                .addIngredient(Items.ICE)
                .setResult(ModItems.ICE_WINE)
                .save(consumer);

        // 梅酒
        BarrelBuilder.builder()
                .setFluid(ModFluids.SWEET_BERRIES_JUICE)
                .setResult(ModItems.PLUM_WINE)
                .save(consumer);

        // 伏特加
        BarrelBuilder.builder()
                .setFluid(Fluids.WATER)
                .addIngredient(Items.POTATO)
                .setResult(ModItems.VODKA)
                .save(consumer);

        // 威士忌
        BarrelBuilder.builder()
                .setFluid(Fluids.WATER)
                .addIngredient(Items.WHEAT)
                .setResult(ModItems.WHISKEY)
                .save(consumer);

        // 燃烧瓶
        BarrelBuilder.builder()
                .setFluid(Fluids.LAVA)
                .setResult(ModItems.MOLOTOV)
                .save(consumer);
    }
}
