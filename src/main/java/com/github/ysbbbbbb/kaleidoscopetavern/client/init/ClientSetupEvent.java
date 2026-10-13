package com.github.ysbbbbbb.kaleidoscopetavern.client.init;

import com.github.ysbbbbbb.kaleidoscopetavern.client.model.brew.BarrelModel;
import com.github.ysbbbbbb.kaleidoscopetavern.client.model.deco.BarStoolBodyModel;
import com.github.ysbbbbbb.kaleidoscopetavern.client.model.deco.LargeChalkboardModel;
import com.github.ysbbbbbb.kaleidoscopetavern.client.model.deco.SmallChalkboardModel;
import com.github.ysbbbbbb.kaleidoscopetavern.client.model.mixology.ShakerModel;
import com.github.ysbbbbbb.kaleidoscopetavern.client.render.block.*;
import com.github.ysbbbbbb.kaleidoscopetavern.client.render.entity.ThrownMolotovRenderer;
import com.github.ysbbbbbb.kaleidoscopetavern.entity.ThrownMolotovEntity;
import com.github.ysbbbbbb.kaleidoscopetavern.init.ModBlocks;
import com.github.ysbbbbbb.kaleidoscopetavern.init.ModEntities;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.ModelLayerRegistry;
import net.minecraft.client.core.registries.BuiltInClientRegistries;
import net.minecraft.client.core.registries.ClientRegistries;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.client.renderer.blockentity.state.BlockEntityRenderState;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.client.renderer.entity.NoopRenderer;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import org.jetbrains.annotations.Contract;

import java.util.Objects;

@Environment(EnvType.CLIENT)
public final class ClientSetupEvent {

    @Contract
    public static void init() {
        register(ModBlocks.CHALKBOARD_BE, ChalkboardBlockEntityRender::new);
        register(ModBlocks.SANDWICH_BOARD_BE, SandwichBlockEntityRender::new);
        register(ModBlocks.PRESSING_TUB_BE, PressingTubBlockEntityRender::new);
        register(ModBlocks.BARREL_BE, BarrelBlockEntityRender::new);
        register(ModBlocks.BAR_CABINET_BE, BarCabinetBlockEntityRender::new);
        register(ModBlocks.BAR_STOOL_BE, BarStoolBlockEntityRender::new);
        register(ModBlocks.CELLAR_CABINET_BE, CellarCabinetBlockEntityRender::new);
        register(ModBlocks.TILTED_RACK_BE, TiltedRackBlockEntityRender::new);
        register(ModBlocks.CIRCULAR_RACK_BE, CircularRackBlockEntityRender::new);
        register(ModBlocks.HOLDER_BE, HolderBlockEntityRender::new);
        register(ModBlocks.SHAKER_BE, ShakerBlockEntityRender::new);
        register(ModBlocks.GLASSWARE_HOLDER_BE, GlasswareHolderBlockEntityRender::new);
        register(ModEntities.THROWN_MOLOTOV, ThrownMolotovRenderer::new);
        register(ModEntities.SIT, NoopRenderer::new);
        ModelLayerRegistry.registerModelLayer(SmallChalkboardModel.LAYER_LOCATION, SmallChalkboardModel::createBodyLayer);
        ModelLayerRegistry.registerModelLayer(LargeChalkboardModel.LAYER_LOCATION, LargeChalkboardModel::createBodyLayer);
        ModelLayerRegistry.registerModelLayer(BarrelModel.LAYER_LOCATION, BarrelModel::createBodyLayer);
        ModelLayerRegistry.registerModelLayer(BarStoolBodyModel.LAYER_LOCATION, BarStoolBodyModel::createBodyLayer);
        ModelLayerRegistry.registerModelLayer(ShakerModel.LAYER_LOCATION, ShakerModel::createBodyLayer);
    }

    private static <T extends BlockEntity, S extends BlockEntityRenderState> void register(final BlockEntityType<? extends T> type, final BlockEntityRendererProvider<T, S> renderer) {
        Registry.register(BuiltInClientRegistries.BLOCK_ENTITY_RENDERER,
                ResourceKey.create(ClientRegistries.BLOCK_ENTITY_RENDERER, Objects.requireNonNull(
                        BuiltInRegistries.BLOCK_ENTITY_TYPE.getKey(type),
                        () -> "Unregistered block entity type: " + type)),
                renderer);
    }

    private static <T extends Entity> void register(final EntityType<? extends T> type, final EntityRendererProvider<T> renderer) {
        Registry.register(BuiltInClientRegistries.ENTITY_RENDERER, ResourceKey.create(
                ClientRegistries.ENTITY_RENDERER,
                BuiltInRegistries.ENTITY_TYPE.getKey(type)
        ), renderer);
    }
}
