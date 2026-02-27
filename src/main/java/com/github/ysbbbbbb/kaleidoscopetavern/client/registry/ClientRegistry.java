package com.github.ysbbbbbb.kaleidoscopetavern.client.registry;

import com.github.ysbbbbbb.kaleidoscopetavern.network.NetworkHandler;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.client.renderer.RenderType;

import static com.github.ysbbbbbb.kaleidoscopetavern.init.ModBlocks.*;

@Environment(EnvType.CLIENT)
public class ClientRegistry {
    public static void init() {
        NetworkHandler.Clientside.init();
        renderType();
    }

    public static void renderType() {
        BlockRenderLayerMap.INSTANCE.putBlocks(RenderType.cutout(),
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
                WILD_GRAPEVINE,
                WILD_GRAPEVINE_PLANT
        );
    }
}
