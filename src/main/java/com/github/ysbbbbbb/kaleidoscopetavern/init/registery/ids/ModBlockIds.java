package com.github.ysbbbbbb.kaleidoscopetavern.init.registery.ids;

import com.github.ysbbbbbb.kaleidoscopetavern.KaleidoscopeTavern;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.Block;

@SuppressWarnings("unused")
public final class ModBlockIds {

    public static final ResourceKey<Block> WATER_BOTTLE = create("water_bottle");
    public static final ResourceKey<Block> HONEY_BOTTLE = create("honey_bottle");
    public static final ResourceKey<Block> DRAGON_BREATH_BOTTLE = create("dragon_breath_bottle");
    public static final ResourceKey<Block> POTION_BOTTLE = create("potion_bottle");
    public static final ResourceKey<Block> XP_BOTTLE = create("xp_bottle");
    public static final ResourceKey<Block> WILD_GRAPEVINE_PLANT = create("wild_grapevine_plant");
    public static final ResourceKey<Block> GRAPEVINE_TRELLIS = create("grapevine_trellis");
    public static final ResourceKey<Block> ICE_GRAPEVINE_TRELLIS = create("ice_grapevine_trellis");
    public static final ResourceKey<Block> GOLD_GRAPEVINE_TRELLIS = create("gold_grapevine_trellis");
    public static final ResourceKey<Block> GRAPE_JUICE = create("grape_juice");
    public static final ResourceKey<Block> ICE_GRAPE_JUICE = create("ice_grape_juice");
    public static final ResourceKey<Block> GOLD_GRAPE_JUICE = create("gold_grape_juice");
    public static final ResourceKey<Block> GREEN_GRAPE_JUICE = create("green_grape_juice");
    public static final ResourceKey<Block> SWEET_BERRIES_JUICE = create("sweet_berries_juice");
    public static final ResourceKey<Block> GLOW_BERRIES_JUICE = create("glow_berries_juice");

    private static ResourceKey<Block> create(String name) {
        return ResourceKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath(KaleidoscopeTavern.MOD_ID, name));
    }
}
