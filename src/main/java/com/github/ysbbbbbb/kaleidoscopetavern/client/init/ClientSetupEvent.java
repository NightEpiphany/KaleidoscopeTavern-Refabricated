package com.github.ysbbbbbb.kaleidoscopetavern.client.init;

import com.github.ysbbbbbb.kaleidoscopetavern.client.render.block.*;
import com.github.ysbbbbbb.kaleidoscopetavern.client.render.entity.ThrownMolotovRenderer;
import com.github.ysbbbbbb.kaleidoscopetavern.entity.ThrownMolotovEntity;
import com.github.ysbbbbbb.kaleidoscopetavern.init.ModBlocks;
import com.github.ysbbbbbb.kaleidoscopetavern.init.ModEntities;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.client.renderer.entity.EntityRenderers;

@Environment(EnvType.CLIENT)
public final class ClientSetupEvent {

    public static void init() {
        BlockEntityRenderers.register(ModBlocks.CHALKBOARD_BE, ChalkboardBlockEntityRender::new);
        BlockEntityRenderers.register(ModBlocks.SANDWICH_BOARD_BE, SandwichBlockEntityRender::new);
        BlockEntityRenderers.register(ModBlocks.PRESSING_TUB_BE, PressingTubBlockEntityRender::new);
        BlockEntityRenderers.register(ModBlocks.BARREL_BE, BarrelBlockEntityRender::new);
        BlockEntityRenderers.register(ModBlocks.BAR_CABINET_BE, BarCabinetBlockEntityRender::new);
        BlockEntityRenderers.register(ModBlocks.BAR_STOOL_BE, BarStoolBlockEntityRender::new);
        BlockEntityRenderers.register(ModBlocks.TILTED_RACK_BE, TiltedRackBlockEntityRender::new);
        BlockEntityRenderers.register(ModBlocks.CIRCULAR_RACK_BE, CircularRackBlockEntityRender::new);
        BlockEntityRenderers.register(ModBlocks.CELLAR_CABINET_BE, CellarCabinetBlockEntityRender::new);
        BlockEntityRenderers.register(ModBlocks.HOLDER_BE, HolderBlockEntityRender::new);
        BlockEntityRenderers.register(ModBlocks.SHAKER_BE, ShakerBlockEntityRender::new);
        BlockEntityRenderers.register(ModBlocks.GLASSWARE_HOLDER_BE, GlasswareHolderBlockEntityRender::new);
        EntityRenderers.register(ModEntities.THROWN_MOLOTOV, ThrownMolotovRenderer::new);
    }
}
