package com.github.ysbbbbbb.kaleidoscopetavern.init.registery;

import com.github.ysbbbbbb.kaleidoscopetavern.KaleidoscopeTavern;
import com.github.ysbbbbbb.kaleidoscopetavern.api.event.LivingChangeTargetEvent;
import com.github.ysbbbbbb.kaleidoscopetavern.api.event.PlantGrapeEvent;
import com.github.ysbbbbbb.kaleidoscopetavern.blockentity.brew.PressingTubBlockEntity;
import com.github.ysbbbbbb.kaleidoscopetavern.datamap.resources.DrinkEffectDataReloadListener;
import com.github.ysbbbbbb.kaleidoscopetavern.event.*;
import com.github.ysbbbbbb.kaleidoscopetavern.game.grape.GoldGrapePlant;
import com.github.ysbbbbbb.kaleidoscopetavern.game.grape.IceGrapePlant;
import com.github.ysbbbbbb.kaleidoscopetavern.game.grape.NormalGrapePlant;
import com.github.ysbbbbbb.kaleidoscopetavern.game.tap.TapBehaviorManager;
import com.github.ysbbbbbb.kaleidoscopetavern.game.tap.impl.*;
import com.github.ysbbbbbb.kaleidoscopetavern.init.ModBlocks;
import com.github.ysbbbbbb.kaleidoscopetavern.init.ModDispenseBehaviors;
import com.github.ysbbbbbb.kaleidoscopetavern.network.NetworkHandler;
import com.github.ysbbbbbb.kaleidoscopetavern.util.event.IKTEvent;
import net.fabricmc.fabric.api.resource.v1.ResourceLoader;
import net.fabricmc.fabric.api.transfer.v1.item.ItemStorage;
import net.minecraft.resources.Identifier;
import net.minecraft.server.packs.PackType;
import net.minecraft.world.level.block.Blocks;

public final class CommonRegistry {
    public static void init() {
        NetworkHandler.init();
        ResourceLoader.get(PackType.SERVER_DATA).registerReloadListener(Identifier.fromNamespaceAndPath(KaleidoscopeTavern.MOD_ID, "drink_effect"), new DrinkEffectDataReloadListener());
        storageRegister();
        events();
        addTapBehavior();
        modCompat();
        ModDispenseBehaviors.registerDispenseBehaviors();
    }

    private static void addTapBehavior() {
        TapBehaviorManager.register(ModBlocks.BARREL, new BarrelTapBehavior());
        TapBehaviorManager.register(Blocks.WATER_CAULDRON, new WaterCauldronTapBehavior());
        TapBehaviorManager.register(Blocks.LAVA_CAULDRON, new LavaCauldronTapBehavior());
        TapBehaviorManager.register(Blocks.BEE_NEST, new BeehiveTapBehavior());
        TapBehaviorManager.register(Blocks.BEEHIVE, new BeehiveTapBehavior());
        TapBehaviorManager.register(Blocks.DRAGON_HEAD, new DragonHeadTapBehavior());
        TapBehaviorManager.register(Blocks.DRAGON_WALL_HEAD, new DragonHeadTapBehavior());
        TapBehaviorManager.register(Blocks.MELON, new WatermelonTapBehavior());
    }

    public static void events() {
        AddFeaturesEvent.addFeatures();
        PlayerSitEvent.register();
        EffectEvent.register();
        VanillaBottlePlaceEvent.register();

        IKTEvent.CALLBACK.register(event -> {
            if (event instanceof PlantGrapeEvent plantGrapeEvent) {
                GoldGrapePlant.onPlant(plantGrapeEvent);
                IceGrapePlant.onPlant(plantGrapeEvent);
                NormalGrapePlant.onPlant(plantGrapeEvent);
            }
            if (event instanceof LivingChangeTargetEvent livingChangeTargetEvent) {
                ChangeTargetEvent.onTarget(livingChangeTargetEvent);
            }
        });
    }

    public static void modCompat() {

    }

    public static void storageRegister() {
        ItemStorage.SIDED.registerForBlockEntity(
                PressingTubBlockEntity::getItemStorage,
                ModBlocks.PRESSING_TUB_BE
        );
    }
}
