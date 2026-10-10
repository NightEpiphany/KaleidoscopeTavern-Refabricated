package com.github.ysbbbbbb.kaleidoscopetavern.compat.rrv.shaker;

import cc.cassian.rrv.api.recipe.ReliableClientRecipe;
import cc.cassian.rrv.api.recipe.ReliableClientRecipeType;
import cc.cassian.rrv.backport.ItemStackTemplate;
import cc.cassian.rrv.common.recipe.inventory.RecipeViewMenu;
import cc.cassian.rrv.common.recipe.inventory.SlotContent;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.Collections;
import java.util.List;

public class ShakerViewRecipe implements ReliableClientRecipe {
    private final Identifier id;
    private final ItemStackTemplate result;
    private final List<Ingredient> ingredients;

    public ShakerViewRecipe(Identifier id, ItemStackTemplate result, List<Ingredient> ingredients) {
        this.id = id;
        this.result = result;
        this.ingredients = ingredients.stream()
                .filter(ingredient -> !ingredient.isEmpty())
                .limit(3)
                .toList();
    }

    @Override
    public ReliableClientRecipeType getType() {
        return ShakerViewType.INSTANCE;
    }

    @Override
    public Identifier getId() {
        return this.id;
    }

    @Override
    public void bindSlots(RecipeViewMenu.SlotFillContext slotFillContext) {
        for (int i = 0; i < this.ingredients.size(); i++) {
            slotFillContext.bindSlot(i, SlotContent.of(this.ingredients.get(i)));
        }
        slotFillContext.bindSlot(3, SlotContent.of(this.result));
    }

    @Override
    public List<SlotContent> getIngredients() {
        return this.ingredients.stream().map(SlotContent::of).toList();
    }

    @Override
    public List<SlotContent> getResults() {
        return Collections.singletonList(SlotContent.of(this.result));
    }
}
