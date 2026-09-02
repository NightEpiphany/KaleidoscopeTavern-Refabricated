package com.github.ysbbbbbb.kaleidoscopetavern.client.init;

import com.github.ysbbbbbb.kaleidoscopetavern.client.model.brew.BarrelModel;
import com.github.ysbbbbbb.kaleidoscopetavern.client.model.deco.BarStoolBodyModel;
import com.github.ysbbbbbb.kaleidoscopetavern.client.model.deco.LargeChalkboardModel;
import com.github.ysbbbbbb.kaleidoscopetavern.client.model.deco.SmallChalkboardModel;
import com.github.ysbbbbbb.kaleidoscopetavern.client.model.mixology.ShakerModel;
import com.github.ysbbbbbb.kaleidoscopetavern.init.ModEntities;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.client.renderer.entity.NoopRenderer;

@Environment(EnvType.CLIENT)
public final class ModEntitiesRender {
    public static void init() {
        EntityRenderers.register(ModEntities.SIT, NoopRenderer::new);
        EntityModelLayerRegistry.registerModelLayer(SmallChalkboardModel.LAYER_LOCATION, SmallChalkboardModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(LargeChalkboardModel.LAYER_LOCATION, LargeChalkboardModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(BarrelModel.LAYER_LOCATION, BarrelModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(BarStoolBodyModel.LAYER_LOCATION, BarStoolBodyModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(ShakerModel.LAYER_LOCATION, ShakerModel::createBodyLayer);
    }
}
