package com.github.ysbbbbbb.kaleidoscopetavern.init;

import com.github.ysbbbbbb.kaleidoscopetavern.KaleidoscopeTavern;
import com.github.ysbbbbbb.kaleidoscopetavern.entity.SitEntity;
import com.github.ysbbbbbb.kaleidoscopetavern.entity.ThrownMolotovEntity;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.EntityType;

public final class ModEntities {
    // 注意：不能用 noSave()——1.21.11 的 startRiding 要求 vehicle 的 EntityType.canSerialize() 为 true，否则骑乘直接失败
    // 坐姿实体的不持久化由 SitEntity.readAdditionalSaveData 里自丢弃实现
    public static final EntityType<SitEntity> SIT = EntityType.Builder.<SitEntity>of(SitEntity::new, net.minecraft.world.entity.MobCategory.MISC).sized(0.5f, 0.1f).clientTrackingRange(10).noSummon().build(net.minecraft.resources.ResourceKey.create(net.minecraft.core.registries.Registries.ENTITY_TYPE, Identifier.fromNamespaceAndPath(KaleidoscopeTavern.MOD_ID, "sit")));
    public static final EntityType<ThrownMolotovEntity> THROWN_MOLOTOV = EntityType.Builder.<ThrownMolotovEntity>of(ThrownMolotovEntity::new, net.minecraft.world.entity.MobCategory.MISC).sized(0.25F, 0.25F).clientTrackingRange(4).updateInterval(10).build(net.minecraft.resources.ResourceKey.create(net.minecraft.core.registries.Registries.ENTITY_TYPE, Identifier.fromNamespaceAndPath(KaleidoscopeTavern.MOD_ID, "thrown_molotov")));

    public static void registerEntities() {
        Registry.register(BuiltInRegistries.ENTITY_TYPE, Identifier.fromNamespaceAndPath(KaleidoscopeTavern.MOD_ID, "sit"), SIT);
        Registry.register(BuiltInRegistries.ENTITY_TYPE, Identifier.fromNamespaceAndPath(KaleidoscopeTavern.MOD_ID, "thrown_molotov"), THROWN_MOLOTOV);
    }
}
