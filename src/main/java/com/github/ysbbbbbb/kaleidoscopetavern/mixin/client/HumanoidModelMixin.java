package com.github.ysbbbbbb.kaleidoscopetavern.mixin.client;

import com.github.ysbbbbbb.kaleidoscopetavern.item.ShakerItem;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * 第三人称手臂摇晃动画：玩家使用雪克杯时，持杯手臂做摇晃姿势。
 * 1.21.1 原版通过 poseRightArm/poseLeftArm 注入 LivingEntity 实现；
 * 1.21.11 渲染改为 RenderState 管线（模型泛型是 HumanoidRenderState），改为从渲染状态读取。
 */
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
    private static ItemStack kaleidoscope_tavern$getShakingStack(HumanoidRenderState state) {
        if (!state.isUsingItem) {
            return ItemStack.EMPTY;
        }
        return state.useItemHand == InteractionHand.MAIN_HAND
                ? state.getMainHandItemStack()
                : state.leftHandItemStack;
    }

    @Unique
    private static float kaleidoscope_tavern$shakeRot(HumanoidRenderState state) {
        // 与 1.21.1 原版一致：sin(tick * 1.5)，幅度从 0.25 调小到 0.15
        return Mth.sin(state.ageInTicks * 1.5F) * 0.15F;
    }

    @Inject(method = "poseRightArm", at = @At("HEAD"), cancellable = true)
    private void poseRightArmShaking(HumanoidRenderState state, CallbackInfo ci) {
        ItemStack shakingStack = kaleidoscope_tavern$getShakingStack(state);
        if (shakingStack.isEmpty() || !(shakingStack.getItem() instanceof ShakerItem)) {
            return;
        }
        boolean rightHandIsShaking = state.useItemHand == InteractionHand.MAIN_HAND
                ? state.mainArm == HumanoidArm.RIGHT
                : state.mainArm == HumanoidArm.LEFT;
        if (!rightHandIsShaking) {
            return;
        }
        float rot = kaleidoscope_tavern$shakeRot(state);
        if (rot != 0F) {
            this.rightArm.xRot = 1.375F * Mth.PI - Mth.PI * rot;
            this.rightArm.zRot = -Mth.PI * 0.05F;
            ci.cancel();
        }
    }

    @Inject(method = "poseLeftArm", at = @At("HEAD"), cancellable = true)
    private void poseLeftArmShaking(HumanoidRenderState state, CallbackInfo ci) {
        ItemStack shakingStack = kaleidoscope_tavern$getShakingStack(state);
        if (shakingStack.isEmpty() || !(shakingStack.getItem() instanceof ShakerItem)) {
            return;
        }
        boolean leftHandIsShaking = state.useItemHand == InteractionHand.MAIN_HAND
                ? state.mainArm == HumanoidArm.LEFT
                : state.mainArm == HumanoidArm.RIGHT;
        if (!leftHandIsShaking) {
            return;
        }
        float rot = kaleidoscope_tavern$shakeRot(state);
        if (rot != 0F) {
            this.leftArm.xRot = 1.375F * Mth.PI + Mth.PI * rot;
            this.leftArm.zRot = Mth.PI * 0.05F;
            ci.cancel();
        }
    }
}
