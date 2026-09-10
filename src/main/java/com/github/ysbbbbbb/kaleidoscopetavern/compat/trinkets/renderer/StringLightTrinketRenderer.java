package com.github.ysbbbbbb.kaleidoscopetavern.compat.trinkets.renderer;

import com.github.ysbbbbbb.kaleidoscopetavern.item.StringLightsBlockItem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import eu.pb4.trinkets.api.TrinketSlotAccess;
import eu.pb4.trinkets.api.client.TrinketRenderer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

@Environment(EnvType.CLIENT)
public class StringLightTrinketRenderer implements TrinketRenderer {

    @Override
    public void submit(ItemStack stack, TrinketSlotAccess slotReference, EntityModel<? extends LivingEntityRenderState> contextModel, PoseStack poseStack, SubmitNodeCollector submit, int light, LivingEntityRenderState state, float limbAngle, float limbDistance) {
        if (stack.getItem() instanceof StringLightsBlockItem && state instanceof HumanoidRenderState humanoidRenderState && !(humanoidRenderState.chestEquipment.getItem() instanceof StringLightsBlockItem)) {
            ItemStackRenderState itemStackRenderState = new ItemStackRenderState();
            if (stack.getItem() instanceof StringLightsBlockItem && contextModel instanceof HumanoidModel<?> humanoidModel) {
                poseStack.pushPose();
                humanoidModel.body.translateAndRotate(poseStack);
                poseStack.translate(0f, -0.1875f, -0.4375f);
                poseStack.rotateDegrees(Axis.YP, 180f);
                poseStack.scale(-0.625f, -0.625f, 0.625f);
                Minecraft.getInstance().getItemModelResolver().updateForTopItem(itemStackRenderState, stack, ItemDisplayContext.HEAD, null, null, 0);
                itemStackRenderState.submit(poseStack, submit, light, OverlayTexture.NO_OVERLAY, 0);
                poseStack.popPose();
            }
        }
    }
}
