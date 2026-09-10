package com.github.ysbbbbbb.kaleidoscopetavern.client.render.block;

import com.github.ysbbbbbb.kaleidoscopetavern.block.brew.BarCabinetBlock;
import com.github.ysbbbbbb.kaleidoscopetavern.blockentity.brew.BarCabinetBlockEntity;
import com.github.ysbbbbbb.kaleidoscopetavern.client.render.renderstate.BarCabinetBlockEntityRenderState;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.block.BlockModelResolver;
import net.minecraft.client.renderer.block.model.BlockDisplayContext;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.Direction;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

@Environment(EnvType.CLIENT)
public class BarCabinetBlockEntityRender implements BlockEntityRenderer<BarCabinetBlockEntity, BarCabinetBlockEntityRenderState> {
    private final BlockModelResolver resolver;
    public static final BlockDisplayContext BLOCK_DISPLAY_CONTEXT = BlockDisplayContext.create();

    public BarCabinetBlockEntityRender(BlockEntityRendererProvider.Context context) {
        this.resolver = context.blockModelResolver();
    }

    @Override
    public void extractRenderState(@NonNull BarCabinetBlockEntity blockEntity, @NonNull BarCabinetBlockEntityRenderState blockEntityRenderState, float f, @NonNull Vec3 vec3, ModelFeatureRenderer.@Nullable CrumblingOverlay crumblingOverlay) {
        BlockEntityRenderer.super.extractRenderState(blockEntity, blockEntityRenderState, f, vec3, crumblingOverlay);
        blockEntityRenderState.facing = blockEntity.getBlockState().getValue(BarCabinetBlock.FACING);
        blockEntityRenderState.isSingle = blockEntity.isSingle();
        if (blockEntityRenderState.isSingle && !blockEntity.getLeftItem().isEmpty()) {
            if (blockEntity.getLeftItem().getItem() instanceof BlockItem blockItem)
                this.resolver.update(blockEntityRenderState.leftModel, blockItem.getBlock().defaultBlockState(), BLOCK_DISPLAY_CONTEXT);
        } else {
            if (!blockEntity.getLeftItem().isEmpty() && blockEntity.getLeftItem().getItem() instanceof BlockItem blockItem)
                this.resolver.update(blockEntityRenderState.leftModel, blockItem.getBlock().defaultBlockState(), BLOCK_DISPLAY_CONTEXT);
            if (!blockEntity.getRightItem().isEmpty() && blockEntity.getRightItem().getItem() instanceof BlockItem blockItem)
                this.resolver.update(blockEntityRenderState.rightModel, blockItem.getBlock().defaultBlockState(), BLOCK_DISPLAY_CONTEXT);
        }
    }

    @Override
    public @NonNull BarCabinetBlockEntityRenderState createRenderState() {
        return new BarCabinetBlockEntityRenderState();
    }

    @Override
    public void submit(BarCabinetBlockEntityRenderState blockEntityRenderState, @NonNull PoseStack poseStack, @NonNull SubmitNodeCollector submitNodeCollector, @NonNull CameraRenderState cameraRenderState) {

        float scale = 0.9f;
        float angle = 180 - blockEntityRenderState.facing.get2DDataValue() * 90f;

        if (blockEntityRenderState.isSingle) {
            if (!blockEntityRenderState.leftModel.isEmpty()) {
                poseStack.pushPose();
                poseStack.translate(0.5, 0, 0.5);
                poseStack.rotateDegrees(Axis.YP, angle);
                poseStack.translate(0, 0.0625, 0);
                poseStack.scale(scale, scale, scale);
                poseStack.translate(-0.5, 0, -0.5);
                blockEntityRenderState.leftModel.submit(
                        poseStack,
                        submitNodeCollector,
                        blockEntityRenderState.lightCoords,
                        OverlayTexture.NO_OVERLAY,
                        0
                );
                poseStack.popPose();
            }
        } else {
            if (!blockEntityRenderState.leftModel.isEmpty()) {
                poseStack.pushPose();
                poseStack.translate(0.5, 0, 0.5);
                poseStack.rotateDegrees(Axis.YP, angle);
                poseStack.translate(blockEntityRenderState.facing.getAxis() == Direction.Axis.Z ? 0.25 : -0.25, 0.0625, 0);
                poseStack.scale(scale, scale, scale);
                poseStack.translate(-0.5, 0, -0.5);
                blockEntityRenderState.leftModel.submit(
                        poseStack,
                        submitNodeCollector,
                        blockEntityRenderState.lightCoords,
                        OverlayTexture.NO_OVERLAY,
                        0
                );
                poseStack.popPose();
            }

            if (!blockEntityRenderState.rightModel.isEmpty()) {
                poseStack.pushPose();
                poseStack.translate(0.5, 0, 0.5);
                poseStack.rotateDegrees(Axis.YP, angle);
                poseStack.translate(blockEntityRenderState.facing.getAxis() == Direction.Axis.Z ? -0.25 : 0.25, 0.0625, 0);
                poseStack.scale(scale, scale, scale);
                poseStack.translate(-0.5, 0, -0.5);
                blockEntityRenderState.rightModel.submit(
                        poseStack,
                        submitNodeCollector,
                        blockEntityRenderState.lightCoords,
                        OverlayTexture.NO_OVERLAY,
                        0
                );
                poseStack.popPose();
            }
        }
    }
}
