package com.github.ysbbbbbb.kaleidoscopetavern.client.render.block;

import com.github.ysbbbbbb.kaleidoscopetavern.blockentity.deco.GlasswareHolderBlockEntity;
import com.github.ysbbbbbb.kaleidoscopetavern.client.render.renderstate.GlasswareHolderBlockEntityRenderState;
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
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

@Environment(EnvType.CLIENT)
public class GlasswareHolderBlockEntityRender implements BlockEntityRenderer<GlasswareHolderBlockEntity, GlasswareHolderBlockEntityRenderState> {
    private static final BlockDisplayContext BLOCK_DISPLAY_CONTEXT = BlockDisplayContext.create();
    private final BlockModelResolver resolver;

    public GlasswareHolderBlockEntityRender(BlockEntityRendererProvider.Context context) {
        this.resolver = context.blockModelResolver();
    }

    @Override
    public void extractRenderState(@NonNull GlasswareHolderBlockEntity blockEntity, @NonNull GlasswareHolderBlockEntityRenderState renderState,
                                   float partialTick, @NonNull Vec3 cameraPos, ModelFeatureRenderer.@Nullable CrumblingOverlay crumblingOverlay) {
        BlockEntityRenderer.super.extractRenderState(blockEntity, renderState, partialTick, cameraPos, crumblingOverlay);
        ItemStackHandler items = blockEntity.getItems();
        List<GlasswareHolderBlockEntityRenderState.Entry> entries = new ArrayList<>();
        for (int slot = 0; slot < items.getSlots(); slot++) {
            ItemStack stack = items.getStackInSlot(slot);
            if (!stack.isEmpty() && stack.getItem() instanceof BlockItem blockItem) {
                GlasswareHolderBlockEntityRenderState.Entry entry = new GlasswareHolderBlockEntityRenderState.Entry();
                entry.slot = slot;
                this.resolver.update(entry.model, blockItem.getBlock().defaultBlockState(), BLOCK_DISPLAY_CONTEXT);
                entries.add(entry);
            }
        }
        renderState.entries = entries;
    }

    @Override
    public @NonNull GlasswareHolderBlockEntityRenderState createRenderState() {
        return new GlasswareHolderBlockEntityRenderState();
    }

    @Override
    public void submit(@NonNull GlasswareHolderBlockEntityRenderState renderState, @NonNull PoseStack poseStack,
                       @NonNull SubmitNodeCollector submitNodeCollector, @NonNull CameraRenderState cameraRenderState) {
        for (GlasswareHolderBlockEntityRenderState.Entry entry : renderState.entries) {
            if (entry.model.isEmpty()) {
                continue;
            }

            poseStack.pushPose();
            poseStack.translate(-0.25, 0.76, 0.75);
            poseStack.translate(0.5 * (entry.slot % 2), 0, 0.5 * (entry.slot / 2));
            poseStack.rotateDegrees(Axis.XN, 180);
            entry.model.submit(poseStack, submitNodeCollector, renderState.lightCoords, OverlayTexture.NO_OVERLAY, 0);
            poseStack.popPose();
        }
    }
}
