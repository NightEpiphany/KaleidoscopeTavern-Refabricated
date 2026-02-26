package com.github.ysbbbbbb.kaleidoscopetavern.crafting.container;

import com.github.ysbbbbbb.kaleidoscopetavern.util.fluids.CustomFluidTank;
import com.github.ysbbbbbb.kaleidoscopetavern.util.forge.IItemHandler;
import net.minecraft.core.NonNullList;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.material.Fluid;

public class BarrelRecipeContainer extends SimpleContainer {
    private final Fluid fluid;

    public BarrelRecipeContainer(IItemHandler items, CustomFluidTank fluid) {
        super(items.getSlots());
        for (int i = 0; i < items.getSlots(); i++) {
            this.setItem(i, items.getStackInSlot(i));
        }
        this.fluid = fluid.getFluid();
    }

    public Fluid getFluid() {
        return fluid;
    }

    public NonNullList<ItemStack> getItems() {
        return items;
    }

    public boolean itemsIsEmpty() {
        return items.stream().allMatch(ItemStack::isEmpty);
    }
}
