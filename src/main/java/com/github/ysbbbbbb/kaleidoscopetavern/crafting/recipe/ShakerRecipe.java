package com.github.ysbbbbbb.kaleidoscopetavern.crafting.recipe;

import com.github.ysbbbbbb.kaleidoscopetavern.crafting.container.SimpleInput;
import com.github.ysbbbbbb.kaleidoscopetavern.init.ModRecipes;
import com.github.ysbbbbbb.kaleidoscopetavern.util.ColorUtils;
import com.github.ysbbbbbb.kaleidoscopetavern.util.RecipeMatcher;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMaps;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.PlacementInfo;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeBookCategory;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public final class ShakerRecipe implements Recipe<SimpleInput> {
    private final NonNullList<Ingredient> ingredients;
    private final ItemStack result;

    private Int2ObjectMap<ChatFormatting> ingredientColors;

    public ShakerRecipe(
            NonNullList<Ingredient> ingredients,
            ItemStack result,
            Int2ObjectMap<ChatFormatting> ingredientColors
    ) {
        // 不在这里检查 isEmpty()，因为数据包加载早期 tag 可能未绑定
        this.ingredients = ingredients;
        this.result = result;
        this.ingredientColors = ingredientColors;
    }

    @Override
    public boolean matches(SimpleInput input, Level level) {
        // 过滤掉非空输入和原料，数量必须匹配
        List<ItemStack> nonEmptyInputs = input.inputs().stream()
                .filter(stack -> !stack.isEmpty())
                .toList();
        List<Ingredient> nonEmptyIngredients = this.ingredients.stream()
                .filter(ing -> !ing.isEmpty())
                .toList();
        if (nonEmptyInputs.size() != nonEmptyIngredients.size()) {
            return false;
        }
        return RecipeMatcher.findMatches(nonEmptyInputs, nonEmptyIngredients) != null;
    }

    @Override
    public @NotNull ItemStack assemble(SimpleInput input, HolderLookup.Provider registries) {
        return this.result.copy();
    }

    public @NotNull NonNullList<Ingredient> getIngredients() {
        return this.ingredients;
    }

    public @NotNull ItemStack getResultItem(HolderLookup.Provider registries) {
        return this.result;
    }

    @Override
    public @NotNull RecipeSerializer<? extends Recipe<SimpleInput>> getSerializer() {
        return ModRecipes.SHAKER_SERIALIZER;
    }

    @Override
    public boolean isSpecial() {
        return true;
    }

    @Override
    public @NotNull RecipeType<? extends Recipe<SimpleInput>> getType() {
        return ModRecipes.SHAKER_RECIPE;
    }

    @Override
    public @NotNull PlacementInfo placementInfo() {
        return PlacementInfo.NOT_PLACEABLE;
    }

    @Override
    public @NotNull RecipeBookCategory recipeBookCategory() {
        return new RecipeBookCategory();
    }

    public NonNullList<Ingredient> ingredients() {
        return ingredients;
    }

    public ItemStack result() {
        return result;
    }

    public Int2ObjectMap<ChatFormatting> ingredientColors() {
        if (this.ingredientColors.equals(Int2ObjectMaps.emptyMap())) {
            this.ingredientColors = getIngredientColors(this.ingredients);
        }
        return ingredientColors;
    }

    private static Int2ObjectMap<ChatFormatting> getIngredientColors(NonNullList<Ingredient> ingredients) {
        Int2ObjectMap<ChatFormatting> ingredientColors = new Int2ObjectOpenHashMap<>();
        for (int i = 0; i < ingredients.size(); i++) {
            ChatFormatting formatting = getColor(ingredients.get(i));
            if (formatting != null) {
                ingredientColors.put(i, formatting);
            }
        }
        return ingredientColors;
    }

    @Nullable
    private static ChatFormatting getColor(Ingredient ingredient) {
        for (Holder<Item> holder : ingredient.items().toList()) {
            for (var entry : ColorUtils.COCKTAIL_INGREDIENT_COLORS.entrySet()) {
                if (holder.value().equals(entry.getKey())) {
                    return entry.getValue();
                }
            }
        }
        return null;
    }
}