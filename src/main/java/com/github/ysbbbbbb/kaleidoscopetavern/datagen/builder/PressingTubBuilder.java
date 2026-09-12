package com.github.ysbbbbbb.kaleidoscopetavern.datagen.builder;

import com.github.ysbbbbbb.kaleidoscopetavern.KaleidoscopeTavern;
import com.github.ysbbbbbb.kaleidoscopetavern.crafting.recipe.PressingTubRecipe;
import com.github.ysbbbbbb.kaleidoscopetavern.crafting.serializer.PressingTubRecipeSerializer;
import net.minecraft.advancements.triggers.Criterion;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemInstance;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import org.jetbrains.annotations.Nullable;
import org.jspecify.annotations.NonNull;

@SuppressWarnings("unused")
public class PressingTubBuilder implements RecipeBuilder {
    private static final String NAME = "pressing_tub";

    private Ingredient ingredient = Ingredient.of();
    private Fluid fluid = Fluids.WATER;
    private int fluidAmount = PressingTubRecipeSerializer.DEFAULT_FLUID_AMOUNT;

    public static PressingTubBuilder builder() {
        return new PressingTubBuilder();
    }

    public PressingTubBuilder setIngredient(ItemLike itemLike) {
        this.ingredient = Ingredient.of(itemLike);
        return this;
    }

    public PressingTubBuilder setIngredient(TagKey<Item> itemLike) {
        this.ingredient = Ingredient.of();
        return this;
    }

    public PressingTubBuilder setFluid(Fluid fluid) {
        this.fluid = fluid;
        return this;
    }

    public PressingTubBuilder setFluidAmount(int amount) {
        this.fluidAmount = amount;
        return this;
    }

    @Override
    public @NonNull RecipeBuilder unlockedBy(@NonNull String name, @NonNull Criterion<?> trigger) {
        return this;
    }

    @Override
    public @NonNull RecipeBuilder group(@Nullable String groupName) {
        return this;
    }

    @Override
    public @NonNull ResourceKey<Recipe<?>> defaultId() {
        return ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(KaleidoscopeTavern.MOD_ID, "pressing_tub"));
    }

    @Override
    public void save(@NonNull RecipeOutput output, @NonNull ResourceKey<Recipe<?>> location) {

    }

    public Item getResult() {
        return this.fluid.getBucket();
    }

    @Override
    public void save(@NonNull RecipeOutput output) {
        String path = RecipeBuilder.getDefaultRecipeId((ItemInstance) this.getResult()).toString();
        Identifier filePath = Identifier.fromNamespaceAndPath(KaleidoscopeTavern.MOD_ID, NAME + "/" + path);
        this.save(output, filePath);
    }

    @Override
    public void save(@NonNull RecipeOutput output, @NonNull String recipeId) {
        Identifier filePath = Identifier.fromNamespaceAndPath(KaleidoscopeTavern.MOD_ID, NAME + "/" + recipeId);
        this.save(output, filePath);
    }


    public void save(RecipeOutput recipeOutput, Identifier id) {
        PressingTubRecipe recipe = new PressingTubRecipe(this.ingredient, this.fluid, this.fluidAmount);
    }
}
