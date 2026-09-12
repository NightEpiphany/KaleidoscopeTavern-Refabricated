package com.github.ysbbbbbb.kaleidoscopetavern.datagen.builder;

import com.github.ysbbbbbb.kaleidoscopetavern.KaleidoscopeTavern;
import com.github.ysbbbbbb.kaleidoscopetavern.crafting.serializer.BarrelRecipeSerializer;
import com.github.ysbbbbbb.kaleidoscopetavern.init.ModItems;
import com.google.common.collect.Lists;
import net.minecraft.advancements.triggers.Criterion;
import net.minecraft.core.NonNullList;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemInstance;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import org.jetbrains.annotations.Nullable;
import org.jspecify.annotations.NonNull;

import java.util.List;

@SuppressWarnings("unused")
public class BarrelBuilder implements RecipeBuilder {
    private static final String NAME = "barrel";

    private final List<Ingredient> ingredients = Lists.newArrayList();

    private Fluid fluid = Fluids.WATER;
    private Ingredient carrier = Ingredient.of(ModItems.EMPTY_BOTTLE);
    private ItemStack result = ItemStack.EMPTY;

    public static BarrelBuilder builder() {
        return new BarrelBuilder();
    }

    public BarrelBuilder addIngredient(ItemLike itemLike) {
        this.ingredients.add(Ingredient.of(itemLike));
        return this;
    }

    public BarrelBuilder addIngredient(TagKey<Item> tag) {
        this.ingredients.add(Ingredient.of());
        return this;
    }

    public BarrelBuilder addIngredient(Ingredient ingredient) {
        this.ingredients.add(ingredient);
        return this;
    }

    public BarrelBuilder setFluid(Fluid fluid) {
        this.fluid = fluid;
        return this;
    }

    public BarrelBuilder setCarrier(ItemLike itemLike) {
        this.carrier = Ingredient.of(itemLike);
        return this;
    }

    public BarrelBuilder setCarrier(TagKey<Item> tag) {
        this.carrier = Ingredient.of();
        return this;
    }

    public BarrelBuilder setResult(ItemLike itemLike) {
        this.result = new ItemStack(itemLike);
        return this;
    }

    public BarrelBuilder setUnitTime(int unitTime) {
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
        return ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(KaleidoscopeTavern.MOD_ID, "barrel"));
    }

    @Override
    public void save(@NonNull RecipeOutput output, @NonNull ResourceKey<Recipe<?>> location) {

    }


    public Item getResult() {
        return this.result.getItem();
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
        NonNullList<Ingredient> nullList = NonNullList.withSize(BarrelRecipeSerializer.MAX_INGREDIENTS, Ingredient.of());
        int size = Math.min(this.ingredients.size(), BarrelRecipeSerializer.MAX_INGREDIENTS);
        for (int i = 0; i < size; i++) {
            nullList.set(i, this.ingredients.get(i));
        }
    }
}
