package com.github.ysbbbbbb.kaleidoscopetavern.compat.rrv.pressing_tub;

import cc.cassian.rrv.api.recipe.ReliableClientRecipe;
import cc.cassian.rrv.api.recipe.ReliableClientRecipeType;
import cc.cassian.rrv.backport.ItemStackTemplate;
import cc.cassian.rrv.common.recipe.inventory.RecipeViewMenu;
import cc.cassian.rrv.common.recipe.inventory.SlotContent;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.Collections;
import java.util.List;

public class PressingTubViewRecipe implements ReliableClientRecipe {
    private final Identifier id;
    private final SlotContent result;
    private final Ingredient input;
    public PressingTubViewRecipe(Identifier id, ItemStackTemplate result, Ingredient input) {
        this.id = id;
        this.result = SlotContent.of(result);
        this.input = input;
    }

    @Override
    public ReliableClientRecipeType getType() {
        return PressingTubViewType.INSTANCE;
    }

    @Override
    public Identifier getId() {
        return this.id;
    }

    @SuppressWarnings("deprecation")
    @Override
    public void bindSlots(RecipeViewMenu.SlotFillContext slotFillContext) {
        slotFillContext.bindSlot(0, SlotContent.of(this.input.items().map(s -> s.value().getDefaultInstance().copyWithCount(8)).toList()));
        slotFillContext.bindSlot(1, SlotContent.of(Ingredient.of(Items.BUCKET)));
        slotFillContext.bindSlot(2, this.result);
    }

    @Override
    public List<SlotContent> getIngredients() {
        return Collections.singletonList(SlotContent.of(this.input));
    }

    @Override
    public List<SlotContent> getResults() {
        return Collections.singletonList(result);
    }
}
