package com.github.ysbbbbbb.kaleidoscopetavern.crafting.container;

import com.github.ysbbbbbb.kaleidoscopetavern.util.fluids.CustomFluidTank;
import com.github.ysbbbbbb.kaleidoscopetavern.util.neo.IItemHandler;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeInput;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class BarrelRecipeContainer implements RecipeInput {
    private final NonNullList<ItemStack> items;
    private final Fluid fluid;

    public BarrelRecipeContainer(IItemHandler itemHandler, CustomFluidTank fluid) {
        this.items = NonNullList.withSize(itemHandler.getSlots(), ItemStack.EMPTY);
        for (int i = 0; i < itemHandler.getSlots(); i++) {
            this.items.set(i, itemHandler.getStackInSlot(i));
        }
        this.fluid = fluid.getFluid();
    }

    @Override
    public @NotNull ItemStack getItem(int index) {
        return items.get(index);
    }

    @Override
    public int size() {
        return items.size();
    }

    /**
     * 需要重写这个方法，把流体算在内，才不会导致流体不为空但物品为空时被误判为输入空了
     */
    @Override
    public boolean isEmpty() {
        return this.fluid.isSame(Fluids.EMPTY);
    }

    public Fluid getFluid() {
        return fluid;
    }

    public NonNullList<ItemStack> getItems() {
        return items;
    }

    public List<ItemStack> getNonEmptyItems() {
        List<ItemStack> nonEmpty = new ArrayList<>(this.items.size());
        for (ItemStack stack : this.items) {
            if (!stack.isEmpty()) {
                nonEmpty.add(stack);
            }
        }
        return nonEmpty;
    }

    public boolean hasUnitCount(int requiredCount) {
        for (ItemStack stack : this.items) {
            if (!stack.isEmpty() && stack.getCount() < requiredCount) {
                return false;
            }
        }
        return true;
    }

    public boolean itemsIsEmpty() {
        return items.stream().allMatch(ItemStack::isEmpty);
    }
}
