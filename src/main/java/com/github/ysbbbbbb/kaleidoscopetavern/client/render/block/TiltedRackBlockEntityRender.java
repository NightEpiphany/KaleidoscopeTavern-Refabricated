package com.github.ysbbbbbb.kaleidoscopetavern.client.render.block;

import com.github.ysbbbbbb.kaleidoscopetavern.block.deco.TiltedRackBlock;
import com.github.ysbbbbbb.kaleidoscopetavern.blockentity.deco.TiltedRackBlockEntity;
import com.github.ysbbbbbb.kaleidoscopetavern.client.render.renderstate.StorageBlockEntityRenderState;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.Direction;

@Environment(EnvType.CLIENT)
public class TiltedRackBlockEntityRender extends StorageBlockEntityRender<TiltedRackBlockEntity> {
    public TiltedRackBlockEntityRender(BlockEntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    protected Direction getFacing(TiltedRackBlockEntity blockEntity) {
        return blockEntity.getBlockState().getValue(TiltedRackBlock.FACING);
    }

    @Override
    protected StorageBlockEntityRenderState.Entry createEntry(int slot) {
        StorageBlockEntityRenderState.Entry entry = new StorageBlockEntityRenderState.Entry();
        entry.x = 0.425 - 0.375 * slot;
        entry.y = 0.3125;
        entry.z = 0.02 + (slot - 1) * 0.005;
        entry.scale = 0.9F;
        entry.xRot = 22.5F;
        return entry;
    }

    @Override
    protected void renderEntry(StorageBlockEntityRenderState.Entry entry, StorageBlockEntityRenderState renderState,
                               PoseStack poseStack, SubmitNodeCollector submitNodeCollector) {
        if (entry.model.isEmpty()) {
            return;
        }

        poseStack.pushPose();
        poseStack.scale(entry.scale, entry.scale, entry.scale);
        poseStack.translate(entry.x, entry.y, entry.z);
        poseStack.rotateDegrees(Axis.XP, entry.xRot);
        entry.model.submit(poseStack, submitNodeCollector, renderState.lightCoords, OverlayTexture.NO_OVERLAY, 0);
        poseStack.popPose();
    }
}
