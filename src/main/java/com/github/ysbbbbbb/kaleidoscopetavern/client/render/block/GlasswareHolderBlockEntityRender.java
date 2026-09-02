package com.github.ysbbbbbb.kaleidoscopetavern.client.render.block;

import com.github.ysbbbbbb.kaleidoscopetavern.blockentity.deco.GlasswareHolderBlockEntity;
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
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

@Environment(EnvType.CLIENT)
public class GlasswareHolderBlockEntityRender implements BlockEntityRenderer<GlasswareHolderBlockEntity, StorageBlockEntityRenderState> {
    private final BlockRenderDispatcher blockRender;

    public GlasswareHolderBlockEntityRender(BlockEntityRendererProvider.Context context) {
        this.blockRender = context.blockRenderDispatcher();
    }

    @Override
    public void extractRenderState(@NonNull GlasswareHolderBlockEntity blockEntity, @NonNull StorageBlockEntityRenderState blockEntityRenderState, float f, @NonNull Vec3 vec3, ModelFeatureRenderer.@Nullable CrumblingOverlay crumblingOverlay) {
        BlockEntityRenderer.super.extractRenderState(blockEntity, blockEntityRenderState, f, vec3, crumblingOverlay);
        var items = blockEntity.getItems();
        for (int i = 0; i < items.getSlots(); i++) {
            blockEntityRenderState.items.add(items.getStackInSlot(i));
        }
    }

    @Override
    public @NonNull StorageBlockEntityRenderState createRenderState() {
        return new StorageBlockEntityRenderState();
    }

    @Override
    public void submit(StorageBlockEntityRenderState state, @NonNull PoseStack poseStack, @NonNull SubmitNodeCollector submitNodeCollector, @NonNull CameraRenderState cameraRenderState) {
        for (int i = 0; i < state.items.size(); i++) {
            ItemStack stack = state.items.get(i);
            if (!stack.isEmpty() && stack.getItem() instanceof BlockItem blockItem) {
                poseStack.pushPose();
                poseStack.translate(-0.25, 0.76, 0.75);
                poseStack.translate(0.5 * (i % 2), 0, 0.5 * (i / 2));
                poseStack.mulPose(Axis.XN.rotationDegrees(180));
                BlockState blockState = blockItem.getBlock().defaultBlockState();
                BlockStateModel blockStateModel = this.blockRender.getBlockModel(blockState);
                submitNodeCollector.submitBlockModel(
                        poseStack,
                        Sheets.cutoutBlockSheet(),
                        blockStateModel,
                        1.0F,
                        1.0F,
                        1.0F,
                        state.lightCoords,
                        OverlayTexture.NO_OVERLAY,
                        0
                );
                poseStack.popPose();
            }
        }
    }
}