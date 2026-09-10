package com.github.ysbbbbbb.kaleidoscopetavern.client.render.block;

import com.github.ysbbbbbb.kaleidoscopetavern.blockentity.deco.StorageBlockEntity;
import com.github.ysbbbbbb.kaleidoscopetavern.client.render.renderstate.StorageBlockEntityRenderState;
import com.github.ysbbbbbb.kaleidoscopetavern.util.neo.ItemStackHandler;
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
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

@Environment(EnvType.CLIENT)
public abstract class StorageBlockEntityRender<T extends StorageBlockEntity> implements BlockEntityRenderer<T, StorageBlockEntityRenderState> {
    protected static final BlockDisplayContext BLOCK_DISPLAY_CONTEXT = BlockDisplayContext.create();
    protected final BlockModelResolver resolver;

    protected StorageBlockEntityRender(BlockEntityRendererProvider.Context context) {
        this.resolver = context.blockModelResolver();
    }

    @Override
    public void extractRenderState(@NonNull T blockEntity, @NonNull StorageBlockEntityRenderState renderState, float partialTick,
                                   @NonNull Vec3 cameraPos, ModelFeatureRenderer.@Nullable CrumblingOverlay crumblingOverlay) {
        BlockEntityRenderer.super.extractRenderState(blockEntity, renderState, partialTick, cameraPos, crumblingOverlay);
        renderState.facing = getFacing(blockEntity);

        ItemStackHandler items = blockEntity.getItems();
        List<StorageBlockEntityRenderState.Entry> entries = new ArrayList<>();
        for (int slot = 0; slot < items.getSlots(); slot++) {
            ItemStack stack = items.getStackInSlot(slot);
            if (stack.isEmpty() || !(stack.getItem() instanceof BlockItem blockItem)) {
                continue;
            }

            StorageBlockEntityRenderState.Entry entry = createEntry(slot);
            this.resolver.update(entry.model, blockItem.getBlock().defaultBlockState(), BLOCK_DISPLAY_CONTEXT);
            entries.add(entry);
        }
        renderState.entries = entries;
    }

    @Override
    public @NonNull StorageBlockEntityRenderState createRenderState() {
        return new StorageBlockEntityRenderState();
    }

    @Override
    public void submit(@NonNull StorageBlockEntityRenderState renderState, @NonNull PoseStack poseStack,
                       @NonNull SubmitNodeCollector submitNodeCollector, @NonNull CameraRenderState cameraRenderState) {
        poseStack.pushPose();
        applyFacingRotation(renderState.facing, poseStack);
        for (StorageBlockEntityRenderState.Entry entry : renderState.entries) {
            renderEntry(entry, renderState, poseStack, submitNodeCollector);
        }
        poseStack.popPose();
    }

    protected void applyFacingRotation(Direction direction, PoseStack poseStack) {
        float angle = 180 - direction.get2DDataValue() * 90F;
        poseStack.translate(0.5, 0, 0.5);
        poseStack.rotateDegrees(Axis.YP, angle);
        poseStack.translate(-0.5, 0, -0.5);
    }

    protected void renderEntry(StorageBlockEntityRenderState.Entry entry, StorageBlockEntityRenderState renderState,
                               PoseStack poseStack, SubmitNodeCollector submitNodeCollector) {
        if (entry.model.isEmpty()) {
            return;
        }

        poseStack.pushPose();
        poseStack.translate(entry.x, entry.y, entry.z);
        poseStack.rotateDegrees(Axis.YP, entry.yRot);
        poseStack.rotateDegrees(Axis.XP, entry.xRot);
        poseStack.scale(entry.scale, entry.scale, entry.scale);
        poseStack.translate(-0.5, 0, -0.5);
        entry.model.submit(poseStack, submitNodeCollector, renderState.lightCoords, OverlayTexture.NO_OVERLAY, 0);
        poseStack.popPose();
    }

    protected abstract Direction getFacing(T blockEntity);

    protected abstract StorageBlockEntityRenderState.Entry createEntry(int slot);
}
