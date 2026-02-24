package com.github.ysbbbbbb.kaleidoscopetavern.crafting.serializer;

import com.github.ysbbbbbb.kaleidoscopetavern.crafting.recipe.BarrelRecipe;
import com.github.ysbbbbbb.kaleidoscopetavern.item.BottleBlockItem;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import net.minecraft.core.NonNullList;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.common.crafting.CraftingHelper;

import java.util.Arrays;

public class BarrelRecipeSerializer implements RecipeSerializer<BarrelRecipe> {
    public static final String DEFAULT_LIQUID = "minecraft:water";
    public static final int DEFAULT_UNIT_TIME = 2400;
    public static final int MAX_INGREDIENTS = 4;

    @Override
    public BarrelRecipe fromJson(ResourceLocation recipeId, JsonObject json) {
        JsonArray ingredientsJson = GsonHelper.getAsJsonArray(json, "ingredients");
        NonNullList<Ingredient> ingredients = NonNullList.withSize(MAX_INGREDIENTS, Ingredient.EMPTY);
        for (int i = 0; i < ingredientsJson.size(); i++) {
            // 最大支持 4 个原料，超过部分将被忽略
            if (i >= MAX_INGREDIENTS) {
                break;
            }
            ingredients.set(i, Ingredient.fromJson(ingredientsJson.get(i)));
        }

        String liquidJson = GsonHelper.getAsString(json, "liquid", DEFAULT_LIQUID);
        ResourceLocation liquid = ResourceLocation.tryParse(liquidJson);
        if (liquid == null) {
            throw new JsonParseException("Invalid liquid resource location: " + liquidJson);
        }

        JsonObject carrierJson = GsonHelper.getAsJsonObject(json, "carrier");
        Ingredient carrier = Ingredient.fromJson(carrierJson);
        // 检查 carrier 是否为 ItemBlock 及其子类
        Arrays.stream(carrier.getItems()).forEach(stack -> {
            if (!(stack.getItem() instanceof BlockItem)) {
                throw new JsonParseException("Carrier must extend class BlockItem, but found: " + stack.getItem());
            }
        });

        JsonObject resultJson = GsonHelper.getAsJsonObject(json, "result");
        ItemStack result = CraftingHelper.getItemStack(resultJson, true, true);
        if (!(result.getItem() instanceof BottleBlockItem)) {
            throw new JsonParseException("Result must extend class BottleBlockItem, but found: " + result.getItem());
        }

        int unitTime = GsonHelper.getAsInt(json, "unit_time", DEFAULT_UNIT_TIME);
        if (unitTime <= 0) {
            throw new JsonParseException("Unit time must be positive, but found: " + unitTime);
        }

        return new BarrelRecipe(recipeId, ingredients, liquid, carrier, result, unitTime);
    }

    @Override
    public BarrelRecipe fromNetwork(ResourceLocation recipeId, FriendlyByteBuf buffer) {
        int size = Math.min(MAX_INGREDIENTS, buffer.readVarInt());
        NonNullList<Ingredient> ingredients = NonNullList.withSize(size, Ingredient.EMPTY);
        for (int i = 0; i < size; i++) {
            ingredients.set(i, Ingredient.fromNetwork(buffer));
        }
        ResourceLocation liquid = buffer.readResourceLocation();
        Ingredient carrier = Ingredient.fromNetwork(buffer);
        ItemStack result = buffer.readItem();
        int unitTime = buffer.readVarInt();
        return new BarrelRecipe(recipeId, ingredients, liquid, carrier, result, unitTime);
    }

    @Override
    public void toNetwork(FriendlyByteBuf buffer, BarrelRecipe recipe) {
        buffer.writeVarInt(recipe.ingredients().size());
        for (Ingredient ingredient : recipe.ingredients()) {
            ingredient.toNetwork(buffer);
        }
        buffer.writeResourceLocation(recipe.liquid());
        recipe.carrier().toNetwork(buffer);
        buffer.writeItem(recipe.result());
        buffer.writeVarInt(recipe.unitTime());
    }
}
