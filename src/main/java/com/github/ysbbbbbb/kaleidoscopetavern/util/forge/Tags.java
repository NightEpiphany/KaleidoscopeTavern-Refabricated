package com.github.ysbbbbbb.kaleidoscopetavern.util.forge;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class Tags {

    public static class Items
    {
        public static final TagKey<Item> DYES = tag("dyes");
        public static final TagKey<Item> GLASS_PANES = tag("glass_panes");

        private static TagKey<Item> tag(String name)
        {
            return TagKey.create(Registries.ITEM, new ResourceLocation("c", name));
        }
    }
}
