package com.github.ysbbbbbb.kaleidoscopetavern.client.render.entity;

import com.github.ysbbbbbb.kaleidoscopetavern.item.StringLightsBlockItem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.client.renderer.item.ItemModelResolver;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.jspecify.annotations.NonNull;

@Environment(EnvType.CLIENT)
public class StringLightsLayer<S extends HumanoidRenderState, M extends HumanoidModel<? super S>> extends RenderLayer<S, M> {
    private final Minecraft minecraft = Minecraft.getInstance();

    public StringLightsLayer(RenderLayerParent<S, M> renderLayerParent) {
        super(renderLayerParent);
    }

    @Override
    public void submit(@NonNull PoseStack poseStack, @NonNull SubmitNodeCollector submitNodeCollector, int i, S entityRenderState, float f, float g) {
        ItemStack stack = entityRenderState.chestEquipment;
        if (stack.getItem() instanceof StringLightsBlockItem && this.getParentModel() instanceof HumanoidModel<?> humanoidModel) {
            ItemModelResolver itemModelResolver = this.minecraft.getItemModelResolver();
            poseStack.pushPose();
            humanoidModel.body.translateAndRotate(poseStack);
            poseStack.translate(0f, -0.1875f, -0.4375f);
            poseStack.mulPose(Axis.YP.rotationDegrees(180f));
            poseStack.scale(-0.625f, -0.625f, 0.625f);
            ItemStackRenderState itemStackRenderState = new ItemStackRenderState();
            itemModelResolver.updateForTopItem(itemStackRenderState, stack, ItemDisplayContext.HEAD, null, null, 0);
            poseStack.popPose();
        }
    }
}
