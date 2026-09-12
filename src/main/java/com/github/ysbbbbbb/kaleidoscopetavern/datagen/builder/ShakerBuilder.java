package com.github.ysbbbbbb.kaleidoscopetavern.datagen.builder;

import com.github.ysbbbbbb.kaleidoscopetavern.KaleidoscopeTavern;
import com.github.ysbbbbbb.kaleidoscopetavern.crafting.recipe.ShakerRecipe;
import com.github.ysbbbbbb.kaleidoscopetavern.crafting.serializer.ShakerRecipeSerializer;
import com.google.common.collect.Lists;
import it.unimi.dsi.fastutil.ints.Int2ObjectMaps;
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
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.ItemLike;
import org.jetbrains.annotations.Nullable;
import org.jspecify.annotations.NonNull;

import java.util.List;

@SuppressWarnings("unused")
public class ShakerBuilder implements RecipeBuilder {
    private static final String NAME = "shaker";

    private final List<Ingredient> ingredients = Lists.newArrayList();
    private ItemStack result = ItemStack.EMPTY;

    public static ShakerBuilder builder() {
        return new ShakerBuilder();
    }

    public ShakerBuilder addIngredient(ItemLike itemLike) {
        this.ingredients.add(Ingredient.of(itemLike));
        return this;
    }

    public ShakerBuilder addIngredient(TagKey<Item> tag) {
        this.ingredients.add(Ingredient.of());
        return this;
    }

    public ShakerBuilder addIngredient(Ingredient ingredient) {
        this.ingredients.add(ingredient);
        return this;
    }

    public ShakerBuilder setResult(ItemLike itemLike) {
        this.result = new ItemStack(itemLike);
        return this;
    }

    @Override
    public @NonNull RecipeBuilder unlockedBy(@NonNull String s, @NonNull Criterion<?> criterion) {
        return this;
    }

    @Override
    public @NonNull RecipeBuilder group(@Nullable String groupName) {
        return this;
    }

    @Override
    public @NonNull ResourceKey<Recipe<?>> defaultId() {
        return ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(KaleidoscopeTavern.MOD_ID, "shaker"));
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
        NonNullList<Ingredient> nullList = NonNullList.withSize(ShakerRecipeSerializer.MAX_INGREDIENTS, Ingredient.of());
        int size = Math.min(this.ingredients.size(), ShakerRecipeSerializer.MAX_INGREDIENTS);
        for (int i = 0; i < size; i++) {
            nullList.set(i, this.ingredients.get(i));
        }
        ShakerRecipe recipe = new ShakerRecipe(nullList, ItemStackTemplate.fromNonEmptyStack(result), Int2ObjectMaps.emptyMap());
    }
}
