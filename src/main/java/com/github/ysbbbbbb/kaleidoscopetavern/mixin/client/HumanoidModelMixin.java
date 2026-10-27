package com.github.ysbbbbbb.kaleidoscopetavern.mixin.client;

import com.github.ysbbbbbb.kaleidoscopetavern.init.ModItems;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(EnvType.CLIENT)
@Mixin(HumanoidModel.class)
public abstract class HumanoidModelMixin {
    @Shadow
    @Final
    public ModelPart rightArm;

    @Shadow
    @Final
    public ModelPart leftArm;

    @Unique
    private static float shakeOffset(HumanoidRenderState renderState, HumanoidArm arm) {
        if (!renderState.isUsingItem || renderState.ticksUsingItem(arm) <= 0.0F) {
            return 0.0F;
        }

        ItemStack useStack = renderState.getUseItemStackForArm(arm);
        if (!useStack.is(ModItems.SHAKER)) {
            return 0.0F;
        }

        return Mth.sin(renderState.ageInTicks * 1.5F) * 0.25F;
    }

    @Inject(method = "poseRightArm", at = @At("HEAD"), cancellable = true)
    private void kaleidoscopeTavern$poseRightArmShaking(HumanoidRenderState state, CallbackInfo ci) {
        float rot = shakeOffset(state, HumanoidArm.RIGHT);
        if (rot != 0.0F) {
            this.rightArm.xRot = 1.375F * Mth.PI - Mth.PI * rot;
            this.rightArm.zRot = -Mth.PI * 0.05F;
            ci.cancel();
        }
    }

    @Inject(method = "poseLeftArm", at = @At("HEAD"), cancellable = true)
    private void kaleidoscopeTavern$poseLeftArmShaking(HumanoidRenderState state, CallbackInfo ci) {
        float rot = shakeOffset(state, HumanoidArm.LEFT);
        if (rot != 0.0F) {
            this.leftArm.xRot = 1.375F * Mth.PI + Mth.PI * rot;
            this.leftArm.zRot = Mth.PI * 0.05F;
            ci.cancel();
        }
    }
}
