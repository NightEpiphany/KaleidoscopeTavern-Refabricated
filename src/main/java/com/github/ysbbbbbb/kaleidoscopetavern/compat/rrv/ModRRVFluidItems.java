package com.github.ysbbbbbb.kaleidoscopetavern.compat.rrv;

import cc.cassian.rrv.common.recipe.item.FluidItem;
import com.github.ysbbbbbb.kaleidoscopetavern.init.ModFluids;
import com.github.ysbbbbbb.kaleidoscopetavern.init.ModItems;
import com.github.ysbbbbbb.kaleidoscopetavern.init.registery.ids.ModItemIds;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Fluid;

public final class ModRRVFluidItems {
    private ModRRVFluidItems() {
    }

    public static void register() {
        register(ModItemIds.GRAPE_JUICE, ModFluids.GRAPE_JUICE, ModFluids.GRAPE_JUICE_BLOCK);
        register(ModItemIds.ICE_GRAPE_JUICE, ModFluids.ICE_GRAPE_JUICE, ModFluids.ICE_GRAPE_JUICE_BLOCK);
        register(ModItemIds.GOLD_GRAPE_JUICE, ModFluids.GOLD_GRAPE_JUICE, ModFluids.GOLD_GRAPE_JUICE_BLOCK);
        register(ModItemIds.GREEN_GRAPE_JUICE, ModFluids.GREEN_GRAPE_JUICE, ModFluids.GREEN_GRAPE_JUICE_BLOCK);
        register(ModItemIds.SWEET_BERRIES_JUICE, ModFluids.SWEET_BERRIES_JUICE, ModFluids.SWEET_BERRIES_JUICE_BLOCK);
        register(ModItemIds.GLOW_BERRIES_JUICE, ModFluids.GLOW_BERRIES_JUICE, ModFluids.GLOW_BERRIES_JUICE_BLOCK);
    }

    private static void register(ResourceKey<Item> itemKey, Fluid fluid, Block block) {
        FluidItem.FluidItemProperties properties = new FluidItem.FluidItemProperties()
                .fluid(fluid)
                .setItemId(itemKey);
        ModItems.registerItem(itemKey, itemProperties -> new FluidItem(
                block,
                (FluidItem.FluidItemProperties) itemProperties
        ), properties);
    }
}
