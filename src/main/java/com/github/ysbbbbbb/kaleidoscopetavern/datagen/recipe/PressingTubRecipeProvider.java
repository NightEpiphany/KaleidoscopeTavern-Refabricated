package com.github.ysbbbbbb.kaleidoscopetavern.datagen.recipe;

import com.github.ysbbbbbb.kaleidoscopetavern.KaleidoscopeTavern;
import com.github.ysbbbbbb.kaleidoscopetavern.datagen.builder.PressingTubBuilder;
import com.github.ysbbbbbb.kaleidoscopetavern.init.ModItems;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.world.item.Items;

import java.util.function.Consumer;

public class PressingTubRecipeProvider extends ModRecipeProvider {
    public PressingTubRecipeProvider(PackOutput output) {
        super(output);
    }

    @Override
    public void buildRecipes(Consumer<FinishedRecipe> consumer) {
        PressingTubBuilder.builder()
                .setIngredient(ModItems.GRAPE.get())
                .setResult(ModItems.GRAPE_BUCKET.get())
                .setLiquidTexture(KaleidoscopeTavern.modLoc("textures/pressing_tub/grape_juice.png"))
                .save(consumer);

        PressingTubBuilder.builder()
                .setIngredient(Items.SWEET_BERRIES)
                .setResult(ModItems.SWEET_BERRIES_BUCKET.get())
                .setLiquidTexture(KaleidoscopeTavern.modLoc("textures/pressing_tub/sweet_berries_juice.png"))
                .save(consumer);

        PressingTubBuilder.builder()
                .setIngredient(Items.GLOW_BERRIES)
                .setResult(ModItems.GLOW_BERRIES_BUCKET.get())
                .setLiquidTexture(KaleidoscopeTavern.modLoc("textures/pressing_tub/glow_berries_juice.png"))
                .save(consumer);
    }
}
