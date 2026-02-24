package com.github.ysbbbbbb.kaleidoscopetavern.datagen.builder;

import com.github.ysbbbbbb.kaleidoscopetavern.KaleidoscopeTavern;
import com.github.ysbbbbbb.kaleidoscopetavern.crafting.serializer.BarrelRecipeSerializer;
import com.github.ysbbbbbb.kaleidoscopetavern.init.ModRecipes;
import com.google.common.collect.Lists;
import com.google.gson.JsonArray;
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

import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

public class BarrelBuilder implements RecipeBuilder {
    private static final String NAME = "barrel";

    private final List<Ingredient> ingredients = Lists.newArrayList();

    private ResourceLocation liquid;
    private Ingredient carrier = Ingredient.EMPTY;
    private ItemStack result = ItemStack.EMPTY;
    private int unitTime = BarrelRecipeSerializer.DEFAULT_UNIT_TIME;

    public static BarrelBuilder builder() {
        return new BarrelBuilder();
    }

    public BarrelBuilder addIngredient(ItemLike itemLike) {
        this.ingredients.add(Ingredient.of(itemLike));
        return this;
    }

    public BarrelBuilder addIngredient(TagKey<Item> tag) {
        this.ingredients.add(Ingredient.of(tag));
        return this;
    }

    public BarrelBuilder addIngredient(Ingredient ingredient) {
        this.ingredients.add(ingredient);
        return this;
    }

    public BarrelBuilder setLiquid(ResourceLocation liquid) {
        this.liquid = liquid;
        return this;
    }

    public BarrelBuilder setCarrier(ItemLike itemLike) {
        this.carrier = Ingredient.of(itemLike);
        return this;
    }

    public BarrelBuilder setCarrier(TagKey<Item> tag) {
        this.carrier = Ingredient.of(tag);
        return this;
    }

    public BarrelBuilder setResult(ItemLike itemLike) {
        this.result = new ItemStack(itemLike);
        return this;
    }

    public BarrelBuilder setUnitTime(int unitTime) {
        this.unitTime = unitTime;
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
        recipeOutput.accept(new BarrelFinishedRecipe(id, this.ingredients, this.liquid, this.carrier, this.result, this.unitTime));
    }

    public static class BarrelFinishedRecipe implements FinishedRecipe {
        private final ResourceLocation id;
        private final List<Ingredient> ingredients;
        private final ResourceLocation liquid;
        private final Ingredient carrier;
        private final ItemStack result;
        private final int unitTime;

        public BarrelFinishedRecipe(ResourceLocation id, List<Ingredient> ingredients, ResourceLocation liquid,
                                    Ingredient carrier, ItemStack result, int unitTime) {
            this.id = id;
            this.ingredients = ingredients;
            this.liquid = liquid;
            this.carrier = carrier;
            this.result = result;
            this.unitTime = unitTime;
        }

        @Override
        public void serializeRecipeData(JsonObject json) {
            JsonArray ingredientsArray = new JsonArray();
            for (Ingredient ingredient : this.ingredients) {
                ingredientsArray.add(ingredient.toJson());
            }
            json.add("ingredients", ingredientsArray);

            json.addProperty("liquid", this.liquid.toString());

            json.add("carrier", this.carrier.toJson());

            JsonObject resultJson = new JsonObject();
            resultJson.addProperty("item", Objects.requireNonNull(ForgeRegistries.ITEMS.getKey(this.result.getItem())).toString());
            json.add("result", resultJson);

            json.addProperty("unit_time", this.unitTime);
        }

        @Override
        public ResourceLocation getId() {
            return this.id;
        }

        @Override
        public RecipeSerializer<?> getType() {
            return ModRecipes.BARREL_SERIALIZER.get();
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
