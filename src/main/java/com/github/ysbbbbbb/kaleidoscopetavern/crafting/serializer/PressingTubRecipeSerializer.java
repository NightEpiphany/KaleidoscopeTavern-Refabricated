package com.github.ysbbbbbb.kaleidoscopetavern.crafting.serializer;

import com.github.ysbbbbbb.kaleidoscopetavern.KaleidoscopeTavern;
import com.github.ysbbbbbb.kaleidoscopetavern.crafting.recipe.PressingTubRecipe;
import com.google.gson.JsonObject;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.common.crafting.CraftingHelper;

import static net.minecraft.util.GsonHelper.getAsJsonArray;
import static net.minecraft.util.GsonHelper.getAsJsonObject;

public class PressingTubRecipeSerializer implements RecipeSerializer<PressingTubRecipe> {
    public static final Ingredient DEFAULT_CARRIER = Ingredient.of(Items.BUCKET);
    public static final ResourceLocation DEFAULT_LIQUID_TEXTURE = KaleidoscopeTavern.modLoc("block/pressing_tub/water");

    @Override
    public PressingTubRecipe fromJson(ResourceLocation recipeId, JsonObject json) {
        Ingredient ingredient;
        if (GsonHelper.isArrayNode(json, "ingredient")) {
            ingredient = Ingredient.fromJson(getAsJsonArray(json, "ingredient"), false);
        } else {
            ingredient = Ingredient.fromJson(getAsJsonObject(json, "ingredient"), false);
        }

        ItemStack result = CraftingHelper.getItemStack(getAsJsonObject(json, "result"), true, true);

        Ingredient carrier;
        if (json.has("carrier")) {
            carrier = Ingredient.fromJson(getAsJsonObject(json, "carrier"));
        } else {
            carrier = DEFAULT_CARRIER;
        }

        ResourceLocation liquidTexture;
        if (json.has("liquid_texture")) {
            liquidTexture = new ResourceLocation(GsonHelper.getAsString(json, "liquid_texture"));
        } else {
            liquidTexture = DEFAULT_LIQUID_TEXTURE;
        }

        return new PressingTubRecipe(recipeId, ingredient, result, carrier, liquidTexture);
    }

    @Override
    public PressingTubRecipe fromNetwork(ResourceLocation recipeId, FriendlyByteBuf buffer) {
        Ingredient ingredient = Ingredient.fromNetwork(buffer);
        ItemStack result = buffer.readItem();
        Ingredient carrier = Ingredient.fromNetwork(buffer);
        ResourceLocation liquidTexture = buffer.readResourceLocation();
        return new PressingTubRecipe(recipeId, ingredient, result, carrier, liquidTexture);
    }

    @Override
    public void toNetwork(FriendlyByteBuf buffer, PressingTubRecipe recipe) {
        recipe.getIngredient().toNetwork(buffer);
        buffer.writeItem(recipe.getResult());
        recipe.getCarrier().toNetwork(buffer);
        buffer.writeResourceLocation(recipe.getLiquidTexture());
    }
}
