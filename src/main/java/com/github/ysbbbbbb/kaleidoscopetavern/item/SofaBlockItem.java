package com.github.ysbbbbbb.kaleidoscopetavern.item;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.RegistryObject;

public class SofaBlockItem extends BlockItem {
    public SofaBlockItem(RegistryObject<Block> block) {
        super(block.get(), new Item.Properties());
    }
}
