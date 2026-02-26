package com.github.ysbbbbbb.kaleidoscopetavern.client.init;

import com.github.ysbbbbbb.kaleidoscopetavern.client.model.brew.BarrelModel;
import com.github.ysbbbbbb.kaleidoscopetavern.client.model.deco.LargeChalkboardModel;
import com.github.ysbbbbbb.kaleidoscopetavern.client.model.deco.SmallChalkboardModel;
import com.github.ysbbbbbb.kaleidoscopetavern.client.render.entity.SitRenderer;
import com.github.ysbbbbbb.kaleidoscopetavern.init.ModEntities;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;

@Environment(EnvType.CLIENT)
public class ModEntitiesRender {
        public static void init() {
            EntityRendererRegistry.register(ModEntities.SIT, SitRenderer::new);
            EntityModelLayerRegistry.registerModelLayer(SmallChalkboardModel.LAYER_LOCATION, SmallChalkboardModel::createBodyLayer);
            EntityModelLayerRegistry.registerModelLayer(LargeChalkboardModel.LAYER_LOCATION, LargeChalkboardModel::createBodyLayer);
            EntityModelLayerRegistry.registerModelLayer(BarrelModel.LAYER_LOCATION, BarrelModel::createBodyLayer);
        }
}
