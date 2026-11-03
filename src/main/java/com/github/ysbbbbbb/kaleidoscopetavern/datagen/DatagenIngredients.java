package com.github.ysbbbbbb.kaleidoscopetavern.datagen;

import com.github.ysbbbbbb.kaleidoscopetavern.util.EmptyJsonOps;
import net.minecraft.core.HolderSet;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;

public class DatagenIngredients {

    @SuppressWarnings("deprecation")
    public static Ingredient tag(TagKey<Item> tagKey) {
        return Ingredient.of(HolderSet.emptyNamed(EmptyJsonOps.INSTANCE, tagKey));
    }

}
