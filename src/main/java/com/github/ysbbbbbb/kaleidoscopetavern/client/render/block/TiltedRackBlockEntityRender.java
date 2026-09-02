package com.github.ysbbbbbb.kaleidoscopetavern.client.render.block;

import com.github.ysbbbbbb.kaleidoscopetavern.block.deco.TiltedRackBlock;
import com.github.ysbbbbbb.kaleidoscopetavern.blockentity.deco.TiltedRackBlockEntity;
import com.github.ysbbbbbb.kaleidoscopetavern.client.render.renderstate.StorageBlockEntityRenderState;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.block.model.BlockStateModel;
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
public class TiltedRackBlockEntityRender extends StorageBlockEntityRender<TiltedRackBlockEntity, StorageBlockEntityRenderState> {
    public TiltedRackBlockEntityRender(BlockEntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public void extractRenderState(@NonNull TiltedRackBlockEntity blockEntity, @NonNull StorageBlockEntityRenderState blockEntityRenderState, float f, @NonNull Vec3 vec3, ModelFeatureRenderer.@Nullable CrumblingOverlay crumblingOverlay) {
        super.extractRenderState(blockEntity, blockEntityRenderState, f, vec3, crumblingOverlay);
        blockEntityRenderState.facing = blockEntity.getBlockState().getValue(TiltedRackBlock.FACING);
    }

    @Override
    public @NonNull StorageBlockEntityRenderState createRenderState() {
        return new StorageBlockEntityRenderState();
    }

    @Override
    public void submit(StorageBlockEntityRenderState state, @NonNull PoseStack poseStack, @NonNull SubmitNodeCollector submitNodeCollector, @NonNull CameraRenderState cameraRenderState) {
        float scale = 0.9f;
        float angle = 180 - state.facing.get2DDataValue() * 90f;

        poseStack.pushPose();
        poseStack.translate(0.5, 0, 0.5);
        poseStack.mulPose(Axis.YP.rotationDegrees(angle));
        poseStack.translate(-0.5, 0, -0.5);

        for (int i = 0; i < state.items.size(); i++) {
            ItemStack stack = state.items.get(i);
            if (stack.isEmpty() || (!(stack.getItem() instanceof BlockItem blockItem))) {
                continue;
            }

            poseStack.pushPose();
            BlockState state2 = blockItem.getBlock().defaultBlockState();
            poseStack.scale(scale, scale, scale);
            poseStack.translate(0.425 - 0.375 * i, 0.3125, 0.02 + (i - 1) * 0.005);
            poseStack.mulPose(Axis.XP.rotationDegrees(22.5f));
            BlockStateModel blockStateModel = this.blockRender.getBlockModel(state2);
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

        poseStack.popPose();
    }
}
