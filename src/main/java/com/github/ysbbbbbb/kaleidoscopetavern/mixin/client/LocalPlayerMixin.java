package com.github.ysbbbbbb.kaleidoscopetavern.mixin.client;

import com.github.ysbbbbbb.kaleidoscopetavern.init.ModEffects;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.effect.MobEffects;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

@Environment(EnvType.CLIENT)
@Mixin(LocalPlayer.class)
public class LocalPlayerMixin {
    @Unique
    private static final float TIPSY_NAUSEA_FACTOR = 0.18F;

    @Unique
    private static final float TIPSY_SPINNING_SPEED = 2.23F;

    @Shadow
    @Final
    protected Minecraft minecraft;

    @ModifyExpressionValue(method = "tickSpinningEffect", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/player/LocalPlayer;getEffectBlendFactor(Lnet/minecraft/core/Holder;F)F"))
    private float includeTipsyInSpinningTick(float nauseaIntensity) {
        return Math.max(nauseaIntensity, this.getTipsySpinningIntensity(1.0F));
    }

    @ModifyExpressionValue(method = "tickSpinningEffect", at = @At(value = "CONSTANT", args = "floatValue=7.0F"))
    private float slowTipsySpinningSpeed(float nauseaSpinningSpeed) {
        LocalPlayer player = this.minecraft.player;
        if (player == null) {
            return nauseaSpinningSpeed;
        }

        float tipsyIntensity = this.getTipsySpinningIntensity(1.0F);
        float nauseaIntensity = player.getEffectBlendFactor(MobEffects.NAUSEA, 1.0F);
        return tipsyIntensity > nauseaIntensity ? TIPSY_SPINNING_SPEED : nauseaSpinningSpeed;
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
