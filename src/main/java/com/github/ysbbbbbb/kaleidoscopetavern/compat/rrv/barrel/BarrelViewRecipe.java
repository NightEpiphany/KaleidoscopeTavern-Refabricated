package com.github.ysbbbbbb.kaleidoscopetavern.compat.rrv.barrel;

import cc.cassian.rrv.api.recipe.ReliableClientRecipe;
import cc.cassian.rrv.api.recipe.ReliableClientRecipeType;
import cc.cassian.rrv.backport.ItemStackTemplate;
import cc.cassian.rrv.common.recipe.inventory.RecipeViewMenu;
import cc.cassian.rrv.common.recipe.inventory.SlotContent;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BarrelViewRecipe implements ReliableClientRecipe {
    private final Identifier id;
    private final ItemStackTemplate result;
    private final List<Ingredient> ingredients;
    private final SlotContent carrier;
    public BarrelViewRecipe(Identifier id, ItemStackTemplate result, List<Ingredient> ingredients, Ingredient carrier) {
        this.id = id;
        this.carrier = SlotContent.of(carrier);
        this.ingredients = ingredients.stream()
                .filter(ingredient -> !ingredient.isEmpty())
                .limit(9)
                .toList();
        this.result = result;
    }

    @Override
    public ReliableClientRecipeType getType() {
        return BarrelViewType.INSTANCE;
    }

    @Override
    public Identifier getId() {
        return this.id;
    }

    @SuppressWarnings("deprecation")
    @Override
    public void bindSlots(RecipeViewMenu.SlotFillContext slotFillContext) {
        var bucket = this.ingredients.getFirst().items().map(s -> s.value().getDefaultInstance().copyWithCount(4)).toList();
        slotFillContext.bindSlot(0, SlotContent.of(bucket));
        for (int i = 1; i < this.ingredients.size(); i++) {
            slotFillContext.bindSlot(i, SlotContent.of(this.ingredients.get(i).items().map(s -> s.value().getDefaultInstance().copyWithCount(16)).toList()));
        }
        slotFillContext.bindSlot(5, this.carrier);
        slotFillContext.bindSlot(6, SlotContent.of(this.result.create().copyWithCount(16)));
    }

    @Override
    public List<SlotContent> getIngredients() {
        List<SlotContent> list = new ArrayList<>(this.ingredients.stream().map(SlotContent::of).toList());
        list.add(this.carrier);
        return list;
    }

    @Override
    public List<SlotContent> getResults() {
        return Collections.singletonList(SlotContent.of(this.result));
    }
}
