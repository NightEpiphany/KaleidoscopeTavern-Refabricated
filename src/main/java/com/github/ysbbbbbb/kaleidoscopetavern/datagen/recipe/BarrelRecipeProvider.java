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
                .setFluid(ModFluids.GRAPE_JUICE.get())
                .setResult(ModItems.WINE.get())
                .save(consumer);

        // 香槟
        BarrelBuilder.builder()
                .setFluid(ModFluids.GRAPE_JUICE.get())
                .addIngredient(Items.SUGAR)
                .setResult(ModItems.CHAMPAGNE.get())
                .save(consumer);

        // 白兰地
        BarrelBuilder.builder()
                .setFluid(ModFluids.GRAPE_JUICE.get())
                .addIngredient(Items.APPLE)
                .setResult(ModItems.BRANDY.get())
                .save(consumer);

        // 佳丽酿
        BarrelBuilder.builder()
                .setFluid(ModFluids.GRAPE_JUICE.get())
                .addIngredient(Items.SWEET_BERRIES)
                .setResult(ModItems.CARIGNAN.get())
                .save(consumer);

        // 樱花葡萄酒
        BarrelBuilder.builder()
                .setFluid(ModFluids.GRAPE_JUICE.get())
                .addIngredient(Items.PINK_PETALS)
                .setResult(ModItems.SAKURA_WINE.get())
                .save(consumer);

        // 冰葡萄酒
        BarrelBuilder.builder()
                .setFluid(ModFluids.GRAPE_JUICE.get())
                .addIngredient(Items.ICE)
                .setResult(ModItems.ICE_WINE.get())
                .save(consumer);

        // 梅酒
        BarrelBuilder.builder()
                .setFluid(ModFluids.SWEET_BERRIES_JUICE.get())
                .setResult(ModItems.PLUM_WINE.get())
                .save(consumer);

        // 伏特加
        BarrelBuilder.builder()
                .setFluid(Fluids.WATER)
                .addIngredient(Items.POTATO)
                .setResult(ModItems.VODKA.get())
                .save(consumer);

        // 威士忌
        BarrelBuilder.builder()
                .setFluid(Fluids.WATER)
                .addIngredient(Items.WHEAT)
                .setResult(ModItems.WHISKEY.get())
                .save(consumer);

        // 燃烧瓶
        BarrelBuilder.builder()
                .setFluid(Fluids.LAVA)
                .setResult(ModItems.MOLOTOV.get())
                .save(consumer);
    }
}
