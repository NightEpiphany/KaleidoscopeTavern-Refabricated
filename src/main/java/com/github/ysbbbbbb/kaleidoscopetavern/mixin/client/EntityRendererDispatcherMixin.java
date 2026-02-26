package com.github.ysbbbbbb.kaleidoscopetavern.mixin.client;

import com.github.ysbbbbbb.kaleidoscopetavern.client.render.entity.StringLightsLayer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;

@Environment(EnvType.CLIENT)
@Mixin(EntityRenderDispatcher.class)
public class EntityRendererDispatcherMixin {

    @Shadow
    private Map<String, EntityRenderer<? extends Player>> playerRenderers;

    @Shadow
    private Map<EntityType<?>, EntityRenderer<?>> renderers;

    @Inject(method = "onResourceManagerReload", at = @At("TAIL"))
    private <R extends LivingEntityRenderer<? extends Player, ? extends EntityModel<? extends Player>>> void onResourceManagerReload(ResourceManager resourceManager, CallbackInfo ci) {
        for (var skin : this.playerRenderers.keySet()) {
            LivingEntityRenderer renderer = (R) this.playerRenderers.get(skin);
            if (renderer != null) {
                renderer.addLayer(new StringLightsLayer<>(renderer));
            }
        }

        LivingEntityRenderer renderer = (LivingEntityRenderer) renderers.get(EntityType.ARMOR_STAND);
        if (renderer != null) {
            renderer.addLayer(new StringLightsLayer<>(renderer));
        }
    }
}
