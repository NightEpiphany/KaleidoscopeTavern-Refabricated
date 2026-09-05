package com.github.ysbbbbbb.kaleidoscopetavern.init.registery;

import com.github.ysbbbbbb.kaleidoscopetavern.KaleidoscopeTavern;
import com.github.ysbbbbbb.kaleidoscopetavern.api.event.LivingChangeTargetEvent;
import com.github.ysbbbbbb.kaleidoscopetavern.api.event.PlantGrapeEvent;
import com.github.ysbbbbbb.kaleidoscopetavern.block.dispenser.BottleBlockDispenseBehavior;
import com.github.ysbbbbbb.kaleidoscopetavern.blockentity.brew.PressingTubBlockEntity;
import com.github.ysbbbbbb.kaleidoscopetavern.datamap.resources.DrinkEffectDataReloadListener;
import com.github.ysbbbbbb.kaleidoscopetavern.event.AddFeaturesEvent;
import com.github.ysbbbbbb.kaleidoscopetavern.event.EffectEvent;
import com.github.ysbbbbbb.kaleidoscopetavern.event.VanillaBottlePlaceEvent;
import com.github.ysbbbbbb.kaleidoscopetavern.game.tap.TapBehaviorManager;
import com.github.ysbbbbbb.kaleidoscopetavern.game.tap.impl.*;
import com.github.ysbbbbbb.kaleidoscopetavern.init.ModBlocks;
import com.github.ysbbbbbb.kaleidoscopetavern.init.ModItems;
import com.github.ysbbbbbb.kaleidoscopetavern.network.NetworkHandler;
import net.fabricmc.fabric.api.registry.FuelRegistryEvents;
import net.fabricmc.fabric.api.resource.v1.ResourceLoader;
import net.fabricmc.fabric.api.transfer.v1.item.ItemStorage;
import net.minecraft.resources.Identifier;
import net.minecraft.server.packs.PackType;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

import java.util.function.Predicate;

public final class CommonRegistry {
    public static void init() {
        NetworkHandler.init();
        ResourceLoader.get(PackType.SERVER_DATA).registerReloader(Identifier.fromNamespaceAndPath(KaleidoscopeTavern.MOD_ID, "drink_effect"), new DrinkEffectDataReloadListener());
        dispenseRegister();
        storageRegister();
        events();
        addTapBehavior();
        fuelRegistry();
        modCompat();
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
        // 所有官方含水（waterlogged）方块都可以给龙头供水，接水效果与水炼药锅一致
        TapBehaviorManager.register(
                state -> state.hasProperty(BlockStateProperties.WATERLOGGED) && state.getValue(BlockStateProperties.WATERLOGGED),
                new WaterCauldronTapBehavior()
        );
    }

    public static void events() {
        AddFeaturesEvent.addFeatures();
        EffectEvent.register();
        VanillaBottlePlaceEvent.register();
        PlantGrapeEvent.register();
        LivingChangeTargetEvent.register();
    }

    public static void fuelRegistry() {
        FuelRegistryEvents.BUILD.register((builder, context) -> {
            builder.add(ModItems.GRAPEVINE, context.baseSmeltTime());
        });
    }

    public static void dispenseRegister() {
        DispenserBlock.registerBehavior(ModItems.WHISKEY, new BottleBlockDispenseBehavior());
        DispenserBlock.registerBehavior(ModItems.WINE, new BottleBlockDispenseBehavior());
        DispenserBlock.registerBehavior(ModItems.BRANDY, new BottleBlockDispenseBehavior());
        DispenserBlock.registerBehavior(ModItems.SAKURA_WINE, new BottleBlockDispenseBehavior());
        DispenserBlock.registerBehavior(ModItems.PLUM_WINE, new BottleBlockDispenseBehavior());
        DispenserBlock.registerBehavior(ModItems.CHAMPAGNE, new BottleBlockDispenseBehavior());
        DispenserBlock.registerBehavior(ModItems.VODKA, new BottleBlockDispenseBehavior());
        DispenserBlock.registerBehavior(ModItems.CARIGNAN, new BottleBlockDispenseBehavior());
        DispenserBlock.registerBehavior(ModItems.EMPTY_BOTTLE, new BottleBlockDispenseBehavior());
        DispenserBlock.registerBehavior(ModItems.ICE_WINE, new BottleBlockDispenseBehavior());
        DispenserBlock.registerBehavior(ModItems.VINEGAR, new BottleBlockDispenseBehavior());
        DispenserBlock.registerBehavior(ModItems.MOLOTOV, new BottleBlockDispenseBehavior());
        DispenserBlock.registerBehavior(ModItems.POLARIS_SWEET_WHITE, new BottleBlockDispenseBehavior());
        DispenserBlock.registerBehavior(ModItems.MOTHER_SNOW, new BottleBlockDispenseBehavior());
        DispenserBlock.registerBehavior(ModItems.SHERRY, new BottleBlockDispenseBehavior());
        DispenserBlock.registerBehavior(ModItems.SWEET_BERRY_WINE, new BottleBlockDispenseBehavior());
        DispenserBlock.registerBehavior(ModItems.RED_QUEEN, new BottleBlockDispenseBehavior());
        DispenserBlock.registerBehavior(ModItems.RUM, new BottleBlockDispenseBehavior());
        DispenserBlock.registerBehavior(ModItems.MINERS_STAR, new BottleBlockDispenseBehavior());
        DispenserBlock.registerBehavior(ModItems.HONEY_WINE, new BottleBlockDispenseBehavior());
        DispenserBlock.registerBehavior(ModItems.MADAME_SHEXIANG, new BottleBlockDispenseBehavior());
        DispenserBlock.registerBehavior(ModItems.SUNSET_GLOW, new BottleBlockDispenseBehavior());
        DispenserBlock.registerBehavior(ModItems.SAUVIGNON_BLANC_DRY_WHITE, new BottleBlockDispenseBehavior());
        DispenserBlock.registerBehavior(ModItems.RIESLING_DRY_WHITE, new BottleBlockDispenseBehavior());
        DispenserBlock.registerBehavior(ModItems.LUMINOUS_BRIDE, new BottleBlockDispenseBehavior());
        DispenserBlock.registerBehavior(ModItems.GLOWFLOWER_BREW, new BottleBlockDispenseBehavior());
        DispenserBlock.registerBehavior(ModItems.WATERMELON_JUICE, new BottleBlockDispenseBehavior());
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
