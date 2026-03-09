package com.github.ysbbbbbb.kaleidoscopetavern.compat.rei;

import com.github.ysbbbbbb.kaleidoscopetavern.crafting.recipe.BarrelRecipe;
import com.github.ysbbbbbb.kaleidoscopetavern.crafting.recipe.PressingTubRecipe;
import com.github.ysbbbbbb.kaleidoscopetavern.init.ModRecipes;
import com.google.common.collect.ImmutableList;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.util.EntryStacks;
import net.fabricmc.fabric.api.recipe.v1.sync.SynchronizedRecipes;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.material.Fluid;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@SuppressWarnings("all")
public enum ReiUtil {
    INSTANCE;

    private final SynchronizedRecipes synchronizedRecipes;
    private ReiUtil() {
        Minecraft minecraft = Minecraft.getInstance();
        ClientLevel level = minecraft.level;

        if (level != null) {
            synchronizedRecipes = level.recipeAccess().getSynchronizedRecipes();
        } else {
            throw new NullPointerException("minecraft world must not be null.");
        }
    }

    public List<RecipeHolder<BarrelRecipe>> barrelRecipes() {
        return List.copyOf(synchronizedRecipes.getAllOfType(ModRecipes.BARREL_RECIPE));
    }

    public List<RecipeHolder<PressingTubRecipe>> pressingTubRecipes(){
        return List.copyOf(synchronizedRecipes.getAllOfType(ModRecipes.PRESSING_TUB_RECIPE));
    }

    public static EntryIngredient ofIngredient(Ingredient ingredient) {
        return EntryIngredient.of(ingredient.items().map(r -> EntryStacks.of(r.value())).toList());
    }

    public static EntryIngredient ofIngredient(Ingredient ingredient, int count) {
        return EntryIngredient.of(ingredient.items()
                .map(s -> EntryStacks.of(s.value().getDefaultInstance().copyWithCount(count)))
                .toList());
    }

    public static List<EntryIngredient> ofIngredients(List<Ingredient> ingredients, Fluid base) {
        if (ingredients.size() == 0) return ImmutableList.of(ofItem(base.getBucket(), 4));
        List<EntryIngredient> result = new ArrayList<>(ingredients.size() + 1);
        result.addFirst(ofItemStack(new ItemStack(base.getBucket(), 4)));
        for (int i = 0; i < ingredients.size(); i++) {
            Ingredient ingredient = ingredients.get(i);
            EntryIngredient entryIngredient = ofIngredient(ingredient, 16);
            if (entryIngredient.isEmpty()) continue;
            result.add(entryIngredient);
        }
        return ImmutableList.copyOf(result);
    }

    public static EntryIngredient ofItem(Item item) {
        return EntryIngredient.of(EntryStacks.of(item));
    }

    public static EntryIngredient ofItem(Item item, int count) {
        return EntryIngredient.of(EntryStacks.of(new ItemStack(item, count)));
    }

    public static EntryIngredient ofItemStack(ItemStack itemStack) {
        return EntryIngredient.of(EntryStacks.of(itemStack));
    }

    public static List<EntryIngredient> ofItems(Item... items) {
        return Arrays.stream(items).map(ReiUtil::ofItem).toList();
    }

    public static List<EntryIngredient> ofItemStacks(ItemStack... items) {
        return Arrays.stream(items).map(ReiUtil::ofItemStack).toList();
    }

    public static List<EntryIngredient> ofItems(List<Item> items) {
        return items.stream().map(ReiUtil::ofItem).toList();
    }

    public static List<EntryIngredient> ofIngredients(Ingredient... ingredients) {
        return Arrays.stream(ingredients).map(ReiUtil::ofIngredient).toList();
    }
}
