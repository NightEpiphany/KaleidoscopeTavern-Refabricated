package com.github.ysbbbbbb.kaleidoscopetavern.client.render.block;

import com.github.ysbbbbbb.kaleidoscopetavern.blockentity.deco.StorageBlockEntity;
import com.github.ysbbbbbb.kaleidoscopetavern.client.render.renderstate.StorageBlockEntityRenderState;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
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

import java.util.ArrayList;
import java.util.List;

@Environment(EnvType.CLIENT)
public abstract class StorageBlockEntityRender<T extends StorageBlockEntity, M extends StorageBlockEntityRenderState> implements BlockEntityRenderer<T, M> {
    protected final BlockRenderDispatcher blockRender;

    protected StorageBlockEntityRender(BlockEntityRendererProvider.Context context) {
        this.blockRender = context.blockRenderDispatcher();
    }

    @Override
    public void extractRenderState(@NonNull T blockEntity, @NonNull M blockEntityRenderState, float f, @NonNull Vec3 vec3, ModelFeatureRenderer.@Nullable CrumblingOverlay crumblingOverlay) {
        BlockEntityRenderer.super.extractRenderState(blockEntity, blockEntityRenderState, f, vec3, crumblingOverlay);
        List<ItemStack> stacks = new ArrayList<>();
        for (int i = 0; i < blockEntity.getItems().getSlots(); i++) {
            stacks.add(blockEntity.getItems().getStackInSlot(i));
        }
        blockEntityRenderState.items = stacks;
    }

    protected void applyFacingRotation(Direction direction, PoseStack poseStack) {
        float angle = 180 - direction.get2DDataValue() * 90f;
        poseStack.translate(0.5, 0, 0.5);
        poseStack.mulPose(Axis.YP.rotationDegrees(angle));
        poseStack.translate(-0.5, 0, -0.5);
    }

    protected void renderStack(ItemStack stack, PoseStack poseStack, SubmitNodeCollector submitNodeCollector, int packedLight,
                               double x, double y, double z, float scale, double yRot, double xRot) {
        if (stack.isEmpty() || !(stack.getItem() instanceof BlockItem blockItem)) {
            return;
        }

        poseStack.pushPose();
        BlockState state = blockItem.getBlock().defaultBlockState();
        poseStack.translate(x, y, z);
        poseStack.mulPose(Axis.YP.rotationDegrees((float) yRot));
        poseStack.mulPose(Axis.XP.rotationDegrees((float) xRot));
        poseStack.scale(scale, scale, scale);
        poseStack.translate(-0.5, 0, -0.5);
        BlockStateModel blockStateModel = this.blockRender.getBlockModel(state);
        submitNodeCollector.submitBlockModel(
                poseStack,
                Sheets.cutoutBlockSheet(),
                blockStateModel,
                1.0F,
                1.0F,
                1.0F,
                packedLight,
                OverlayTexture.NO_OVERLAY,
                0
        );
        poseStack.popPose();
    }

    @Override
    public abstract void submit(M blockEntityRenderState, @NonNull PoseStack poseStack, @NonNull SubmitNodeCollector submitNodeCollector, @NonNull CameraRenderState cameraRenderState);
}