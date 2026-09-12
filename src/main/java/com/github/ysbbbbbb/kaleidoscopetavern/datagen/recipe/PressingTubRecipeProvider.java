package com.github.ysbbbbbb.kaleidoscopetavern.datagen.recipe;

import com.github.ysbbbbbb.kaleidoscopetavern.datagen.builder.PressingTubBuilder;
import com.github.ysbbbbbb.kaleidoscopetavern.init.ModFluids;
import com.github.ysbbbbbb.kaleidoscopetavern.init.ModItems;
import com.github.ysbbbbbb.kaleidoscopetavern.init.tag.TagCommon;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.world.item.Items;

import java.util.concurrent.CompletableFuture;

public class PressingTubRecipeProvider extends ModRecipeProvider {
    public PressingTubRecipeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);
    }

    @Override
    public void buildRecipes(RecipeOutput output) {
        PressingTubBuilder.builder()
                .setIngredient(TagCommon.FRUITS_GRAPES)
                .setFluid(ModFluids.GRAPE_JUICE)
                .save(output);

        PressingTubBuilder.builder()
                .setIngredient(ModItems.ICE_GRAPE)
                .setFluid(ModFluids.ICE_GRAPE_JUICE)
                .save(output);

        PressingTubBuilder.builder()
                .setIngredient(ModItems.GOLD_GRAPE)
                .setFluid(ModFluids.GOLD_GRAPE_JUICE)
                .save(output);

        PressingTubBuilder.builder()
                .setIngredient(ModItems.GREEN_GRAPE)
                .setFluid(ModFluids.GREEN_GRAPE_JUICE)
                .save(output);

        PressingTubBuilder.builder()
                .setIngredient(Items.SWEET_BERRIES)
                .setFluid(ModFluids.SWEET_BERRIES_JUICE)
                .save(output);

        PressingTubBuilder.builder()
                .setIngredient(Items.GLOW_BERRIES)
                .setFluid(ModFluids.GLOW_BERRIES_JUICE)
                .save(output);
    }

    @Override
    public void buildRecipes() {

    }
}
