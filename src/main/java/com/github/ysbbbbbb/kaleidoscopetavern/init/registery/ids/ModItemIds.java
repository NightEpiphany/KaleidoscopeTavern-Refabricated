package com.github.ysbbbbbb.kaleidoscopetavern.init.registery.ids;

import com.github.ysbbbbbb.kaleidoscopetavern.KaleidoscopeTavern;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;

@SuppressWarnings("unused")
public final class ModItemIds {

    public static final ResourceKey<Item> GREEN_GRAPE = create("green_grape");
    public static final ResourceKey<Item> GRAPE_BUCKET = create("grape_bucket");
    public static final ResourceKey<Item> ICE_GRAPE_BUCKET = create("ice_grape_bucket");
    public static final ResourceKey<Item> GOLD_GRAPE_BUCKET = create("gold_grape_bucket");
    public static final ResourceKey<Item> GREEN_GRAPE_BUCKET = create("green_grape_bucket");
    public static final ResourceKey<Item> SWEET_BERRIES_BUCKET = create("sweet_berries_bucket");
    public static final ResourceKey<Item> GLOW_BERRIES_BUCKET = create("glow_berries_bucket");

    private static ResourceKey<Item> create(String name) {
        return ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(KaleidoscopeTavern.MOD_ID, name));
    }
}
