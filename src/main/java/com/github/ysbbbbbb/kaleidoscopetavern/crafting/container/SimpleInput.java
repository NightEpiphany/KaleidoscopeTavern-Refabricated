package com.github.ysbbbbbb.kaleidoscopetavern.crafting.container;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeInput;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public record SimpleInput(List<ItemStack> inputs) implements RecipeInput {

    @Override
    public @NotNull ItemStack getItem(int index) {
        return this.inputs.get(index);
    }

    @Override
    public int size() {
        return this.inputs.size();
    }
}
