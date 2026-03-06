package com.github.ysbbbbbb.kaleidoscopetavern.client.render.block;

import com.github.ysbbbbbb.kaleidoscopetavern.api.blockentity.IPressingTub;
import com.github.ysbbbbbb.kaleidoscopetavern.block.brew.PressingTubBlock;
import com.github.ysbbbbbb.kaleidoscopetavern.blockentity.brew.PressingTubBlockEntity;
import com.github.ysbbbbbb.kaleidoscopetavern.client.render.renderstate.PressingTubBlockEntityRenderState;
import com.github.ysbbbbbb.kaleidoscopetavern.util.RenderUtils;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.item.ItemModelResolver;
import net.minecraft.client.renderer.state.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import static com.github.ysbbbbbb.kaleidoscopetavern.util.RenderUtils.stableRandom;

@Environment(EnvType.CLIENT)
public class PressingTubBlockEntityRender implements BlockEntityRenderer<PressingTubBlockEntity, PressingTubBlockEntityRenderState> {
    private final ItemModelResolver itemModelResolver;

    public PressingTubBlockEntityRender(BlockEntityRendererProvider.Context context) {
        this.itemModelResolver = context.itemModelResolver();
    }

    @Override
    public void extractRenderState(PressingTubBlockEntity blockEntity, PressingTubBlockEntityRenderState blockEntityRenderState, float f, @NonNull Vec3 vec3, ModelFeatureRenderer.@Nullable CrumblingOverlay crumblingOverlay) {
        BlockEntityRenderer.super.extractRenderState(blockEntity, blockEntityRenderState, f, vec3, crumblingOverlay);
        blockEntityRenderState.items = blockEntity.getItems();
        blockEntityRenderState.facing = blockEntity.getBlockState().getValue(PressingTubBlock.FACING);
        blockEntityRenderState.tilt = blockEntity.getBlockState().getValue(PressingTubBlock.TILT);
        blockEntityRenderState.seed = blockEntity.getBlockPos().asLong();
        ItemStack stack = blockEntity.getItems().getStackInSlot(0);
        int count = stack.getCount();
        if (count > 0)
            this.itemModelResolver.updateForTopItem(blockEntityRenderState.itemState, stack, ItemDisplayContext.FIXED, null, null, 0);
        blockEntityRenderState.fluid = blockEntity.getFluid().getFluid();
        blockEntityRenderState.fluidAmount = blockEntity.getFluidAmount();
    }



    @Override
    public PressingTubBlockEntityRenderState createRenderState() {
        return new PressingTubBlockEntityRenderState();
    }

    @Override
    public void submit(PressingTubBlockEntityRenderState blockEntityRenderState, @NonNull PoseStack poseStack, @NonNull SubmitNodeCollector submitNodeCollector, @NonNull CameraRenderState cameraRenderState) {
        // 渲染四个位置的物品，多余的朝上堆叠
        ItemStack stack = blockEntityRenderState.items.getStackInSlot(0);
        int count = stack.getCount();

        if (count > 0) {

            if (blockEntityRenderState.tilt) {
                poseStack.pushPose();
                poseStack.translate(0.5, 0, 0.5);
                poseStack.mulPose(Axis.YN.rotationDegrees(180 - blockEntityRenderState.facing.get2DDataValue() * 90));
                poseStack.translate(-0.5, 0, -0.5);

                if (blockEntityRenderState.facing.getAxis() == Direction.Axis.X) {
                    poseStack.mulPose(Axis.XP.rotationDegrees(45));
                    poseStack.translate(0, 0.5f, -0.5);
                } else {
                    poseStack.mulPose(Axis.XN.rotationDegrees(45));
                    poseStack.translate(0, -0.25f, 0.25);
                }
            }

            for (int i = 0; i < count; i++) {
                poseStack.pushPose();

                float x = ((i % 4) % 2 == 0) ? -0.15f : 0.15f + stableRandom(blockEntityRenderState.seed, i, 1) * 0.0625f;
                float z = ((i % 4) / 2 == 0) ? -0.15f : 0.15f + stableRandom(blockEntityRenderState.seed, i, 2) * 0.0625f;
                float y = (float) (i / 4) * 0.03125f + stableRandom(blockEntityRenderState.seed, i, 3) * 0.05f;

                float yRot = stableRandom(blockEntityRenderState.seed, i, 4) * count / 10f;
                float zRot = stableRandom(blockEntityRenderState.seed, i, 5) * 360f;

                poseStack.translate(0.5f + x, 0.2f + y, 0.5f + z);
                poseStack.scale(0.5f, 0.5f, 0.5f);
                poseStack.mulPose(Axis.XN.rotationDegrees(90));

                poseStack.mulPose(Axis.YN.rotationDegrees(yRot));
                poseStack.mulPose(Axis.ZN.rotationDegrees(zRot));

                blockEntityRenderState.itemState.submit(poseStack, submitNodeCollector, blockEntityRenderState.lightCoords, OverlayTexture.NO_OVERLAY, 0);
                poseStack.popPose();
            }

            if (blockEntityRenderState.tilt) {
                poseStack.popPose();
            }
        }
        MultiBufferSource.BufferSource buffer = Minecraft.getInstance().renderBuffers().bufferSource();
        // 如果有流体，渲染流体
        int fluidAmount = blockEntityRenderState.fluidAmount;
        if (fluidAmount > 0) {
            float percent = fluidAmount / (float) IPressingTub.MAX_FLUID_AMOUNT;
            float y = 0.125f + percent * 0.25f;
            Fluid fluid = blockEntityRenderState.fluid;
            RenderUtils.renderFluid(fluid, poseStack, buffer, blockEntityRenderState.lightCoords, 12, y);
        }
    }
}