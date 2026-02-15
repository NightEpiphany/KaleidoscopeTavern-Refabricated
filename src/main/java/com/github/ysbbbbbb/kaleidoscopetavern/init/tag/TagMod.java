package com.github.ysbbbbbb.kaleidoscopetavern.init.tag;

import com.github.ysbbbbbb.kaleidoscopetavern.KaleidoscopeTavern;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public interface TagMod {
    /**
     * 沙发
     */
    TagKey<Block> SOFA = blockTag("sofa");
    /**
     * 高脚凳
     */
    TagKey<Block> BAR_STOOL = blockTag("bar_stool");
    /**
     * 可以坐在上面的方块
     */
    TagKey<Block> SITTABLE = blockTag("sittable");

    static TagKey<Item> itemTag(String name) {
        return TagKey.create(Registries.ITEM, new ResourceLocation(KaleidoscopeTavern.MOD_ID, name));
    }

    static TagKey<Block> blockTag(String name) {
        return TagKey.create(Registries.BLOCK, new ResourceLocation(KaleidoscopeTavern.MOD_ID, name));
    }

    static TagKey<EntityType<?>> entityTag(String name) {
        return TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation(KaleidoscopeTavern.MOD_ID, name));
    }

    static TagKey<DamageType> damageTypeTag(String name) {
        return TagKey.create(Registries.DAMAGE_TYPE, new ResourceLocation(KaleidoscopeTavern.MOD_ID, name));
    }
}
