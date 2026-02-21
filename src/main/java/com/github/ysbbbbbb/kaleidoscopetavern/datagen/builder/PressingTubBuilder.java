package com.github.ysbbbbbb.kaleidoscopetavern.datagen.builder;

import com.github.ysbbbbbb.kaleidoscopetavern.KaleidoscopeTavern;
import com.github.ysbbbbbb.kaleidoscopetavern.crafting.serializer.PressingTubRecipeSerializer;
import com.github.ysbbbbbb.kaleidoscopetavern.init.ModRecipes;
import com.google.gson.JsonObject;
import net.minecraft.advancements.CriterionTriggerInstance;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.function.Consumer;

public class PressingTubBuilder implements RecipeBuilder {
    private static final String NAME = "pressing_tub";

    private Ingredient ingredient = Ingredient.EMPTY;
    private ItemStack result = ItemStack.EMPTY;
    private Ingredient carrier = PressingTubRecipeSerializer.DEFAULT_CARRIER;
    private ResourceLocation liquidTexture = PressingTubRecipeSerializer.DEFAULT_LIQUID_TEXTURE;

    public static PressingTubBuilder builder() {
        return new PressingTubBuilder();
    }

    public PressingTubBuilder setIngredient(ItemLike itemLike) {
        this.ingredient = Ingredient.of(itemLike);
        return this;
    }

    public PressingTubBuilder setIngredient(TagKey<Item> itemLike) {
        this.ingredient = Ingredient.of(itemLike);
        return this;
    }

    public PressingTubBuilder setResult(ItemLike itemLike) {
        this.result = new ItemStack(itemLike);
        return this;
    }

    public PressingTubBuilder setCarrier(ItemLike itemLike) {
        this.carrier = Ingredient.of(itemLike);
        return this;
    }

    public PressingTubBuilder setCarrier(TagKey<Item> itemLike) {
        this.carrier = Ingredient.of(itemLike);
        return this;
    }

    public PressingTubBuilder setLiquidTexture(ResourceLocation liquidTexture) {
        this.liquidTexture = liquidTexture;
        return this;
    }

    @Override
    public RecipeBuilder unlockedBy(String name, CriterionTriggerInstance trigger) {
        return this;
    }

    @Override
    public RecipeBuilder group(@Nullable String groupName) {
        return this;
    }

    @Override
    public Item getResult() {
        return this.result.getItem();
    }

    @Override
    public void save(Consumer<FinishedRecipe> output) {
        String path = RecipeBuilder.getDefaultRecipeId(this.getResult()).getPath();
        ResourceLocation filePath = KaleidoscopeTavern.modLoc(NAME + "/" + path);
        this.save(output, filePath);
    }

    @Override
    public void save(Consumer<FinishedRecipe> output, String recipeId) {
        ResourceLocation filePath = KaleidoscopeTavern.modLoc(NAME + "/" + recipeId);
        this.save(output, filePath);
    }

    @Override
    public void save(Consumer<FinishedRecipe> recipeOutput, ResourceLocation id) {
        recipeOutput.accept(new PressingTubFinishedRecipe(id, this.ingredient, this.result, this.carrier, this.liquidTexture));
    }

    public static class PressingTubFinishedRecipe implements FinishedRecipe {
        private final ResourceLocation id;
        private final Ingredient ingredient;
        private final ItemStack result;
        private final Ingredient carrier;
        private final ResourceLocation liquidTexture;

        public PressingTubFinishedRecipe(ResourceLocation id, Ingredient ingredient, ItemStack result,
                                         Ingredient carrier, ResourceLocation liquidTexture) {
            this.id = id;
            this.ingredient = ingredient;
            this.result = result;
            this.carrier = carrier;
            this.liquidTexture = liquidTexture;
        }

        @Override
        public void serializeRecipeData(JsonObject json) {
            json.add("ingredient", this.ingredient.toJson());
            JsonObject itemJson = new JsonObject();
            itemJson.addProperty("item", Objects.requireNonNull(ForgeRegistries.ITEMS.getKey(this.result.getItem())).toString());
            json.add("result", itemJson);
            json.add("carrier", this.carrier.toJson());
            json.addProperty("liquid_texture", this.liquidTexture.toString());
        }

        @Override
        public ResourceLocation getId() {
            return this.id;
        }

        @Override
        public RecipeSerializer<?> getType() {
            return ModRecipes.PRESSING_TUB_SERIALIZER.get();
        }

        @Override
        @Nullable
        public JsonObject serializeAdvancement() {
            return null;
        }

        @Override
        @Nullable
        public ResourceLocation getAdvancementId() {
            return null;
        }
    }
}
