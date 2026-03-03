package com.github.ysbbbbbb.kaleidoscopetavern.mixin.client;

import com.github.ysbbbbbb.kaleidoscopetavern.client.render.entity.StringLightsLayer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.resources.PlayerSkin;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
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
    private Map<PlayerSkin.Model, EntityRenderer<? extends Player>> playerRenderers;

    @Shadow
    private Map<EntityType<?>, EntityRenderer<?>> renderers;

    @Inject(method = "onResourceManagerReload", at = @At("TAIL"))
    private void onResourceManagerReload(ResourceManager resourceManager, CallbackInfo ci) {
        for (EntityRenderer<? extends Player> renderer : this.playerRenderers.values()) {
            if (renderer instanceof LivingEntityRenderer<?, ?> livingRenderer) {
                livingRenderer.addLayer(new StringLightsLayer(livingRenderer));
            }
        }

        EntityRenderer<?> armorStandRenderer = renderers.get(EntityType.ARMOR_STAND);
        if (armorStandRenderer instanceof LivingEntityRenderer<?, ?> livingRenderer) {
            livingRenderer.addLayer(new StringLightsLayer(livingRenderer));
        }
    }
}
