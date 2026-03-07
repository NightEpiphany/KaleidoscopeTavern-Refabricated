package com.github.ysbbbbbb.kaleidoscopetavern.mixin.client;

import com.github.ysbbbbbb.kaleidoscopetavern.client.render.entity.StringLightsLayer;
import com.google.common.collect.ImmutableMap;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.player.AvatarRenderer;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.PlayerModelType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;

@SuppressWarnings({"unchecked", "rawtypes"})
@Environment(EnvType.CLIENT)
@Mixin(EntityRenderDispatcher.class)
public class EntityRendererDispatcherMixin {

    @Shadow
    private Map<EntityType<?>, EntityRenderer<?, ?>> renderers = ImmutableMap.of();
    @Shadow
    private Map<PlayerModelType, AvatarRenderer<AbstractClientPlayer>> playerRenderers = Map.of();

    @Inject(method = "onResourceManagerReload", at = @At("TAIL"))
    private void onResourceManagerReload(ResourceManager resourceManager, CallbackInfo ci) {
        for (AvatarRenderer<AbstractClientPlayer> renderer : this.playerRenderers.values()) {
            renderer.addLayer(new StringLightsLayer<>(renderer));
        }

        var armorStandRenderer = this.renderers.get(EntityType.ARMOR_STAND);
        if (armorStandRenderer instanceof LivingEntityRenderer livingEntityRenderer) {
            livingEntityRenderer.addLayer(new StringLightsLayer<>(livingEntityRenderer));
        }
    }
}
