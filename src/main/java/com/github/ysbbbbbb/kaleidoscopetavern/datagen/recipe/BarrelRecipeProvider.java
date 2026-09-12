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

        // 樱花葡萄酒
        BarrelBuilder.builder()
                .setFluid(ModFluids.GRAPE_JUICE)
                .addIngredient(Items.PINK_PETALS)
                .setResult(ModItems.SAKURA_WINE)
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

        // 冰葡萄酒
        BarrelBuilder.builder()
                .setFluid(ModFluids.ICE_GRAPE_JUICE)
                .setResult(ModItems.ICE_WINE)
                .save(output);

        // 北极星甜白
        BarrelBuilder.builder()
                .setFluid(ModFluids.ICE_GRAPE_JUICE)
                .addIngredient(Items.PACKED_ICE)
                .setResult(ModItems.POLARIS_SWEET_WHITE)
                .save(output);

        // 雪婆婆
        BarrelBuilder.builder()
                .setFluid(ModFluids.ICE_GRAPE_JUICE)
                .addIngredient(Items.ICE)
                .setResult(ModItems.MOTHER_SNOW)
                .save(output);

        // 雪莉
        BarrelBuilder.builder()
                .setFluid(ModFluids.ICE_GRAPE_JUICE)
                .addIngredient(Items.BLUE_ICE)
                .setResult(ModItems.SHERRY)
                .save(output);

        // 矿工之星
        BarrelBuilder.builder()
                .setFluid(ModFluids.GOLD_GRAPE_JUICE)
                .addIngredient(Items.IRON_NUGGET)
                .setResult(ModItems.MINERS_STAR)
                .save(output);

        // 蜂蜜葡萄酒
        BarrelBuilder.builder()
                .setFluid(ModFluids.GOLD_GRAPE_JUICE)
                .addIngredient(Items.HONEYCOMB)
                .setResult(ModItems.HONEY_WINE)
                .save(output);

        // 奢香夫人
        BarrelBuilder.builder()
                .setFluid(ModFluids.GOLD_GRAPE_JUICE)
                .addIngredient(Items.GOLD_NUGGET)
                .setResult(ModItems.MADAME_SHEXIANG)
                .save(output);

        // 落日余晖
        BarrelBuilder.builder()
                .setFluid(ModFluids.GOLD_GRAPE_JUICE)
                .addIngredient(Items.BLAZE_POWDER)
                .setResult(ModItems.SUNSET_GLOW)
                .save(output);

        // 长相思干白
        BarrelBuilder.builder()
                .setFluid(ModFluids.GREEN_GRAPE_JUICE)
                .addIngredient(Items.SUGAR_CANE)
                .addIngredient(Items.SUGAR)
                .setResult(ModItems.SAUVIGNON_BLANC_DRY_WHITE)
                .save(output);

        // 雷司令干白
        BarrelBuilder.builder()
                .setFluid(ModFluids.GREEN_GRAPE_JUICE)
                .addIngredient(Items.GUNPOWDER)
                .addIngredient(Items.SUGAR)
                .setResult(ModItems.RIESLING_DRY_WHITE)
                .save(output);

        // 夜光新娘
        BarrelBuilder.builder()
                .setFluid(ModFluids.GLOW_BERRIES_JUICE)
                .addIngredient(Items.GLOW_INK_SAC)
                .setResult(ModItems.LUMINOUS_BRIDE)
                .save(output);

        // 萤花酿
        BarrelBuilder.builder()
                .setFluid(ModFluids.GLOW_BERRIES_JUICE)
                .addIngredient(Items.GLOWSTONE_DUST)
                .setResult(ModItems.GLOWFLOWER_BREW)
                .save(output);

        // 梅酒
        BarrelBuilder.builder()
                .setFluid(ModFluids.SWEET_BERRIES_JUICE)
                .addIngredient(Items.SUGAR)
                .setResult(ModItems.PLUM_WINE)
                .save(output);

        // 甜浆果酒
        BarrelBuilder.builder()
                .setFluid(ModFluids.SWEET_BERRIES_JUICE)
                .addIngredient(Items.SWEET_BERRIES)
                .setResult(ModItems.SWEET_BERRY_WINE)
                .save(output);

        // 红皇后
        BarrelBuilder.builder()
                .setFluid(ModFluids.SWEET_BERRIES_JUICE)
                .addIngredient(Items.REDSTONE)
                .setResult(ModItems.RED_QUEEN)
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

        // 朗姆酒
        BarrelBuilder.builder()
                .setFluid(Fluids.WATER)
                .addIngredient(Items.SUGAR_CANE)
                .setResult(ModItems.RUM)
                .save(output);

        // 燃烧瓶
        BarrelBuilder.builder()
                .setFluid(Fluids.LAVA)
                .setResult(ModItems.MOLOTOV)
                .save(output);
    }

    @Override
    public void buildRecipes() {

    }
}
