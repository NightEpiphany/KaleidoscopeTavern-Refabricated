package com.github.ysbbbbbb.kaleidoscopetavern.client.render.block;

import com.github.ysbbbbbb.kaleidoscopetavern.block.brew.BarCabinetBlock;
import com.github.ysbbbbbb.kaleidoscopetavern.blockentity.brew.BarCabinetBlockEntity;
import com.github.ysbbbbbb.kaleidoscopetavern.client.render.renderstate.BarCabinetBlockEntityRenderState;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.block.model.BlockStateModel;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.state.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.Direction;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

@Environment(EnvType.CLIENT)
public class BarCabinetBlockEntityRender implements BlockEntityRenderer<BarCabinetBlockEntity, BarCabinetBlockEntityRenderState> {
    private final BlockRenderDispatcher blockRender;

    public BarCabinetBlockEntityRender(BlockEntityRendererProvider.Context context) {
        this.blockRender = context.blockRenderDispatcher();
    }

    @Override
    public void extractRenderState(BarCabinetBlockEntity blockEntity, BarCabinetBlockEntityRenderState blockEntityRenderState, float f, @NonNull Vec3 vec3, ModelFeatureRenderer.@Nullable CrumblingOverlay crumblingOverlay) {
        BlockEntityRenderer.super.extractRenderState(blockEntity, blockEntityRenderState, f, vec3, crumblingOverlay);
        blockEntityRenderState.leftItem = blockEntity.getLeftItem();
        blockEntityRenderState.rightItem = blockEntity.getRightItem();
        blockEntityRenderState.facing = blockEntity.getBlockState().getValue(BarCabinetBlock.FACING);
        blockEntityRenderState.isSingle = blockEntity.isSingle();
    }

    @Override
    public BarCabinetBlockEntityRenderState createRenderState() {
        return new BarCabinetBlockEntityRenderState();
    }

    @Override
    public void submit(BarCabinetBlockEntityRenderState blockEntityRenderState, @NonNull PoseStack poseStack, @NonNull SubmitNodeCollector submitNodeCollector, @NonNull CameraRenderState cameraRenderState) {

        var leftStack = blockEntityRenderState.leftItem;
        var rightStack = blockEntityRenderState.rightItem;
        float scale = 0.9f;
        float angle = 180 - blockEntityRenderState.facing.get2DDataValue() * 90f;

        if (blockEntityRenderState.isSingle) {
            if (!leftStack.isEmpty() && leftStack.getItem() instanceof BlockItem blockItem) {
                poseStack.pushPose();
                BlockState state = blockItem.getBlock().defaultBlockState();
                poseStack.translate(0.5, 0, 0.5);
                poseStack.mulPose(Axis.YP.rotationDegrees(angle));
                poseStack.translate(0, 0.0625, 0);
                poseStack.scale(scale, scale, scale);
                poseStack.translate(-0.5, 0, -0.5);
                BlockStateModel blockStateModel = this.blockRender.getBlockModel(state);
                submitNodeCollector.submitBlockModel(
                        poseStack,
                        Sheets.solidBlockSheet(),
                        blockStateModel,
                        1.0F,
                        1.0F,
                        1.0F,
                        blockEntityRenderState.lightCoords,
                        OverlayTexture.NO_OVERLAY,
                        0
                );
                poseStack.popPose();
            }
        } else {
            if (!leftStack.isEmpty() && leftStack.getItem() instanceof BlockItem blockItem) {
                poseStack.pushPose();
                BlockState state = blockItem.getBlock().defaultBlockState();
                poseStack.translate(0.5, 0, 0.5);
                poseStack.mulPose(Axis.YP.rotationDegrees(angle));
                poseStack.translate(blockEntityRenderState.facing.getAxis() == Direction.Axis.Z ? 0.25 : -0.25, 0.0625, 0);
                poseStack.scale(scale, scale, scale);
                poseStack.translate(-0.5, 0, -0.5);
                BlockStateModel blockStateModel = this.blockRender.getBlockModel(state);
                submitNodeCollector.submitBlockModel(
                        poseStack,
                        Sheets.solidBlockSheet(),
                        blockStateModel,
                        1.0F,
                        1.0F,
                        1.0F,
                        blockEntityRenderState.lightCoords,
                        OverlayTexture.NO_OVERLAY,
                        0
                );
                poseStack.popPose();
            }

            if (!rightStack.isEmpty() && rightStack.getItem() instanceof BlockItem blockItem) {
                poseStack.pushPose();
                BlockState state = blockItem.getBlock().defaultBlockState();
                poseStack.translate(0.5, 0, 0.5);
                poseStack.mulPose(Axis.YP.rotationDegrees(angle));
                poseStack.translate(blockEntityRenderState.facing.getAxis() == Direction.Axis.Z ? -0.25 : 0.25, 0.0625, 0);
                poseStack.scale(scale, scale, scale);
                poseStack.translate(-0.5, 0, -0.5);
                BlockStateModel blockStateModel = this.blockRender.getBlockModel(state);
                submitNodeCollector.submitBlockModel(
                        poseStack,
                        Sheets.solidBlockSheet(),
                        blockStateModel,
                        1.0F,
                        1.0F,
                        1.0F,
                        blockEntityRenderState.lightCoords,
                        OverlayTexture.NO_OVERLAY,
                        0
                );
                poseStack.popPose();
            }
        }
    }
}
