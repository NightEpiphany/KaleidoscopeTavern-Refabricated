package com.github.ysbbbbbb.kaleidoscopetavern.datagen.recipe;

import com.github.ysbbbbbb.kaleidoscopetavern.KaleidoscopeTavern;
import com.github.ysbbbbbb.kaleidoscopetavern.datagen.builder.PressingTubBuilder;
import com.github.ysbbbbbb.kaleidoscopetavern.init.ModItems;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.FinishedRecipe;

import java.util.function.Consumer;

public class PressingTubRecipeProvider extends ModRecipeProvider {
    public PressingTubRecipeProvider(PackOutput output) {
        super(output);
    }

    @Override
    public void buildRecipes(Consumer<FinishedRecipe> consumer) {
        PressingTubBuilder.builder()
                .setIngredient(ModItems.GRAPE.get())
                .setResult(ModItems.GRAPEVINE.get())
                .setLiquidTexture(KaleidoscopeTavern.modLoc("block/pressing_tub/grape_juice"))
                .save(consumer);
    }
}
