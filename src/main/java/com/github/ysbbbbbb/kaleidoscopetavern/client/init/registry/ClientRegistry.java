package com.github.ysbbbbbb.kaleidoscopetavern.client.init.registry;

import com.github.ysbbbbbb.kaleidoscopetavern.client.animation.ShakerAnimation;
import com.github.ysbbbbbb.kaleidoscopetavern.client.gui.overlay.ShakerOverlay;
import com.github.ysbbbbbb.kaleidoscopetavern.client.init.ClientSetupEvent;
import com.github.ysbbbbbb.kaleidoscopetavern.client.init.CommonModelLoading;
import com.github.ysbbbbbb.kaleidoscopetavern.client.init.ModEntitiesRender;
import com.github.ysbbbbbb.kaleidoscopetavern.client.init.ModParticleFactoryRegistry;
import com.github.ysbbbbbb.kaleidoscopetavern.client.render.misc.PotionBottleColor;
import com.github.ysbbbbbb.kaleidoscopetavern.client.render.misc.SignatureCocktailColor;
import com.github.ysbbbbbb.kaleidoscopetavern.compat.create.ponder.init.PonderCompat;
import com.github.ysbbbbbb.kaleidoscopetavern.init.ModFluids;
import com.github.ysbbbbbb.kaleidoscopetavern.init.ModItems;
import com.github.ysbbbbbb.kaleidoscopetavern.network.NetworkHandler;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.minecraft.client.renderer.chunk.ChunkSectionLayer;

import static com.github.ysbbbbbb.kaleidoscopetavern.init.ModBlocks.*;

@Environment(EnvType.CLIENT)
public final class ClientRegistry {
    public static void init() {
        NetworkHandler.Clientside.init();
        CommonModelLoading.init();
        renderType();
        color();
        ClientSetupEvent.init();
        ModEntitiesRender.init();
        ModFluids.registerFluidRenderers();
        ModParticleFactoryRegistry.init();
        ShakerAnimation.trigger();
        ShakerOverlay.register();
        modCompatClient();
    }

    private static void color() {
        ColorProviderRegistry.BLOCK.register(new SignatureCocktailColor.Block(), SIGNATURE_COCKTAIL);
        ColorProviderRegistry.BLOCK.register(new PotionBottleColor(), POTION_BOTTLE);
    }

    public static void renderType() {
        BlockRenderLayerMap.putBlocks(ChunkSectionLayer.TRANSLUCENT,
                SIGNATURE_COCKTAIL,
                MYSTERY_COCKTAIL,
                WHITE_LADY,
                EMERALD,
                BRASS_HEART,
                GODFATHER,
                GRASSHOPPER,
                SCREWDRIVER,
                MOJITO,
                ALLIUM_GARDEN,
                DEPTH_CHARGE,
                NETHER_SPECIAL,
                BLOODY_MARY,
                SCULK_SPECIAL,
                BELL_PENDANT_LAMP,
                YELLOW_PENDANT_LAMP,
                BLUE_PENDANT_LAMP,
                POTION_BOTTLE,
                XP_BOTTLE
        );
        BlockRenderLayerMap.putBlocks(ChunkSectionLayer.CUTOUT,
                TILTED_RACK,
                HOLDER,
                PRESSING_TUB,
                BARREL,
                CHALKBOARD,
                BASE_SANDWICH_BOARD,
                GRASS_SANDWICH_BOARD,
                ALLIUM_SANDWICH_BOARD,
                AZURE_BLUET_SANDWICH_BOARD,
                CORNFLOWER_SANDWICH_BOARD,
                ORCHID_SANDWICH_BOARD,
                PEONY_SANDWICH_BOARD,
                PINK_PETALS_SANDWICH_BOARD,
                PITCHER_PLANT_SANDWICH_BOARD,
                POPPY_SANDWICH_BOARD,
                SUNFLOWER_SANDWICH_BOARD,
                TORCHFLOWER_SANDWICH_BOARD,
                TULIP_SANDWICH_BOARD,
                WITHER_ROSE_SANDWICH_BOARD,
                EYEBLOSSOM_SANDWICH_BOARD,
                STRING_LIGHTS_COLORLESS,
                STRING_LIGHTS_WHITE,
                STRING_LIGHTS_LIGHT_BLUE,
                STRING_LIGHTS_GRAY,
                STRING_LIGHTS_BLACK,
                STRING_LIGHTS_BROWN,
                STRING_LIGHTS_RED,
                STRING_LIGHTS_ORANGE,
                STRING_LIGHTS_YELLOW,
                STRING_LIGHTS_LIME,
                STRING_LIGHTS_GREEN,
                STRING_LIGHTS_CYAN,
                STRING_LIGHTS_LIGHT_GRAY,
                STRING_LIGHTS_BLUE,
                STRING_LIGHTS_PURPLE,
                STRING_LIGHTS_MAGENTA,
                STRING_LIGHTS_PINK,
                EMPTY_BOTTLE,
                MOLOTOV,
                VODKA,
                GRAPE_CROP,
                GRAPEVINE_TRELLIS,
                ICE_GRAPE_CROP,
                ICE_GRAPEVINE_TRELLIS,
                GOLD_GRAPE_CROP,
                GOLD_GRAPEVINE_TRELLIS,
                WILD_GRAPEVINE,
                WILD_GRAPEVINE_PLANT,
                BAR_CABINET,
                GLASS_BAR_CABINET,
                RED_BAR_STOOL,
                GREEN_BAR_STOOL,
                BLUE_BAR_STOOL,
                YELLOW_BAR_STOOL,
                ORANGE_BAR_STOOL,
                PINK_BAR_STOOL,
                PURPLE_BAR_STOOL,
                WHITE_BAR_STOOL,
                BLACK_BAR_STOOL,
                LIGHT_BLUE_BAR_STOOL,
                LIME_BAR_STOOL,
                BROWN_BAR_STOOL,
                CYAN_BAR_STOOL,
                MAGENTA_BAR_STOOL,
                GRAY_BAR_STOOL,
                LIGHT_GRAY_BAR_STOOL,
                SAKURA_INCENSE,
                PINE_INCENSE,
                GINKGO_INCENSE,
                SPORE_INCENSE,
                CATNIP_INCENSE,
                SNOW_INCENSE,
                BUTTERFLY_INCENSE,
                FIREFLY_INCENSE
        );
    }

    private static void modCompatClient() {
        PonderCompat.init();
    }
}
