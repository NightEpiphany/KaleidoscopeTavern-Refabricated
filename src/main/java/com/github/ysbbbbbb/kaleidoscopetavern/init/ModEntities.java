package com.github.ysbbbbbb.kaleidoscopetavern.init;

import com.github.ysbbbbbb.kaleidoscopetavern.KaleidoscopeTavern;
import com.github.ysbbbbbb.kaleidoscopetavern.entity.SitEntity;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;

public class ModEntities {
    public static final EntityType<SitEntity> SIT = SitEntity.TYPE;

    public static void registerEntities() {
        Registry.register(BuiltInRegistries.ENTITY_TYPE, new ResourceLocation(KaleidoscopeTavern.MOD_ID, "sit"), SIT);
    }
}
