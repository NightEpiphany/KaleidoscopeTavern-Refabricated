package com.github.ysbbbbbb.kaleidoscopetavern.client.render.block;

import com.github.ysbbbbbb.kaleidoscopetavern.KaleidoscopeTavern;
import com.github.ysbbbbbb.kaleidoscopetavern.block.brew.BarrelBlock;
import com.github.ysbbbbbb.kaleidoscopetavern.blockentity.brew.BarrelBlockEntity;
import com.github.ysbbbbbb.kaleidoscopetavern.client.model.brew.BarrelModel;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;

public class BarrelBlockEntityRender implements BlockEntityRenderer<BarrelBlockEntity> {
    private static final ResourceLocation LARGE_TEXTURE = KaleidoscopeTavern.modLoc("textures/entity/brew/barrel.png");

    private final ItemRenderer itemRenderer;
    private final BarrelModel model;

    public BarrelBlockEntityRender(BlockEntityRendererProvider.Context context) {
        this.itemRenderer = context.getItemRenderer();
        this.model = new BarrelModel(context.bakeLayer(BarrelModel.LAYER_LOCATION));
    }

    @Override
    public void render(BarrelBlockEntity barrel, float partialTick, PoseStack poseStack,
                       MultiBufferSource buffer, int packedLight, int packedOverlay) {
        Direction facing = barrel.getBlockState().getValue(BarrelBlock.FACING);

        poseStack.pushPose();
        poseStack.translate(0.5, 1.5, 0.5);
        poseStack.mulPose(Axis.ZN.rotationDegrees(180));
        poseStack.mulPose(Axis.YN.rotationDegrees(180 - facing.get2DDataValue() * 90));

        // 根据酒桶的开盖状态切换模型显示
        boolean open = barrel.isOpen();
        this.model.getOpen().visible = open;
        this.model.getClose().visible = !open;

        VertexConsumer consumer = buffer.getBuffer(RenderType.entityCutoutNoCull(LARGE_TEXTURE));
        model.renderToBuffer(poseStack, consumer, packedLight, packedOverlay, 1.0F, 1.0F, 1.0F, 1.0F);
        poseStack.popPose();
    }
}
