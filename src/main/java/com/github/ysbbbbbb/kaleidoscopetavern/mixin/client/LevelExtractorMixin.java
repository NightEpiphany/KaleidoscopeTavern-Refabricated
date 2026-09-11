package com.github.ysbbbbbb.kaleidoscopetavern.mixin.client;

import com.github.ysbbbbbb.kaleidoscopetavern.init.ModEffects;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Camera;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.extract.LevelExtractor;
import net.minecraft.client.renderer.state.level.PlayerRenderState;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

@Environment(EnvType.CLIENT)
@Mixin(LevelExtractor.class)
public class LevelExtractorMixin {

    @Unique
    private static final float TIPSY_NAUSEA_FACTOR = 0.18F;

    @Shadow
    @Final
    private Minecraft minecraft;

    @ModifyExpressionValue(
            method = "extractPlayerState",
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/client/player/LocalPlayer;getEffectBlendFactor(Lnet/minecraft/core/Holder;F)F"
            )
    )
    private float includeTipsyInSpinningRender(
            float original,
            final Camera camera,
            final DeltaTracker deltaTracker,
            final float worldPartialTicks,
            final PlayerRenderState state
    ) {
        float partialTicks = deltaTracker.getGameTimeDeltaPartialTick(false);
        return Math.max(original, this.getTipsySpinningIntensity(partialTicks));
    }

    @SuppressWarnings("all")
    @Unique
    private float getTipsySpinningIntensity(float partialTicks) {
        LocalPlayer player = this.minecraft.player;
        if (player == null || ModEffects.SLIGHTLY_TIPSY == null) {
            return 0.0F;
        }

        // 复用原版反胃的投影矩阵算法，只降低输入强度，保证微醺更轻。
        return player.getEffectBlendFactor(ModEffects.SLIGHTLY_TIPSY, partialTicks) * TIPSY_NAUSEA_FACTOR;
    }
}
