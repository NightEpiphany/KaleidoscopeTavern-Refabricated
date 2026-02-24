package com.github.ysbbbbbb.kaleidoscopetavern.crafting.container;

import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class BarrelRecipeContainer extends SimpleContainer {
    private final ResourceLocation liquid;

    public BarrelRecipeContainer(List<ItemStack> items, ResourceLocation liquid) {
        super(items.size());
        for (int i = 0; i < items.size(); i++) {
            this.setItem(i, items.get(i));
        }
        this.liquid = liquid;
    }

    public ResourceLocation getLiquid() {
        return liquid;
    }

    public NonNullList<ItemStack> getItems() {
        return items;
    }
}
