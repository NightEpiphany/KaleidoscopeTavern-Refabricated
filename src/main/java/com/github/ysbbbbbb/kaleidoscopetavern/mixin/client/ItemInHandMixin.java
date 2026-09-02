package com.github.ysbbbbbb.kaleidoscopetavern.mixin.client;

import com.github.ysbbbbbb.kaleidoscopetavern.client.animation.ShakerAnimation;
import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.blaze3d.vertex.PoseStack;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(EnvType.CLIENT)
@Mixin(ItemInHandRenderer.class)
public abstract class ItemInHandMixin {
    @Shadow
    @Final
    private Minecraft minecraft;

    @Shadow
    public abstract void renderItem(LivingEntity livingEntity, ItemStack itemStack, ItemDisplayContext itemDisplayContext, PoseStack poseStack, SubmitNodeCollector multiBufferSource, int i);

    @Inject(method = "renderArmWithItem", at = @At(
            value = "FIELD",
            target = "Lnet/minecraft/world/entity/HumanoidArm;RIGHT:Lnet/minecraft/world/entity/HumanoidArm;",
            ordinal = 1,
            shift = At.Shift.AFTER,
            opcode = Opcodes.GETSTATIC), cancellable = true)
    public void renderArmWithItem(
            AbstractClientPlayer abstractClientPlayer,
            float f,
            float g,
            InteractionHand interactionHand,
            float h,
            ItemStack itemStack,
            float i,
            PoseStack poseStack,
            SubmitNodeCollector multiBufferSource,
            int j,
            CallbackInfo ci,
            @Local HumanoidArm arm
    ) {
        if (ShakerAnimation.applyHandTransform(
                poseStack,
                minecraft.player,
                arm,
                f,
                itemStack
        )) {
            boolean bool = arm == HumanoidArm.RIGHT;
            this.renderItem(abstractClientPlayer, itemStack, bool ? ItemDisplayContext.THIRD_PERSON_RIGHT_HAND : ItemDisplayContext.THIRD_PERSON_LEFT_HAND, poseStack, multiBufferSource, j);
            poseStack.popPose();
            ci.cancel();
        }
    }
}
