package com.github.ysbbbbbb.kaleidoscopetavern.datagen.recipe;

import com.github.ysbbbbbb.kaleidoscopetavern.datagen.builder.PressingTubBuilder;
import com.github.ysbbbbbb.kaleidoscopetavern.init.ModFluids;
import com.github.ysbbbbbb.kaleidoscopetavern.init.ModItems;
import com.github.ysbbbbbb.kaleidoscopetavern.init.tag.TagCommon;
import net.minecraft.advancements.Advancement;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Recipe;

public class PressingTubRecipeProvider extends ModRecipeProvider {
    public PressingTubRecipeProvider(final BootstrapContext<Recipe<?>> recipeOutput, final BootstrapContext<Advancement> advancementOutput) {
        super(recipeOutput, advancementOutput);
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
