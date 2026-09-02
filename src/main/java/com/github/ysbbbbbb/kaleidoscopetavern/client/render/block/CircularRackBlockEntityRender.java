package com.github.ysbbbbbb.kaleidoscopetavern.client.render.block;

import com.github.ysbbbbbb.kaleidoscopetavern.block.deco.CircularRackBlock;
import com.github.ysbbbbbb.kaleidoscopetavern.blockentity.deco.CircularRackBlockEntity;
import com.github.ysbbbbbb.kaleidoscopetavern.client.render.renderstate.StorageBlockEntityRenderState;
import com.mojang.blaze3d.vertex.PoseStack;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.state.CameraRenderState;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

@Environment(EnvType.CLIENT)
public class CircularRackBlockEntityRender extends StorageBlockEntityRender<CircularRackBlockEntity, StorageBlockEntityRenderState> {
    public CircularRackBlockEntityRender(BlockEntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public void extractRenderState(@NonNull CircularRackBlockEntity blockEntity, @NonNull StorageBlockEntityRenderState blockEntityRenderState, float f, @NonNull Vec3 vec3, ModelFeatureRenderer.@Nullable CrumblingOverlay crumblingOverlay) {
        super.extractRenderState(blockEntity, blockEntityRenderState, f, vec3, crumblingOverlay);
        blockEntityRenderState.facing = blockEntity.getBlockState().getValue(CircularRackBlock.FACING);
    }

    @Override
    public @NonNull StorageBlockEntityRenderState createRenderState() {
        return new StorageBlockEntityRenderState();
    }

    @Override
    public void submit(StorageBlockEntityRenderState state, @NonNull PoseStack poseStack, @NonNull SubmitNodeCollector submitNodeCollector, @NonNull CameraRenderState cameraRenderState) {
        poseStack.pushPose();
        this.applyFacingRotation(state.facing, poseStack);

        for (int i = 0; i < state.items.size(); i++) {
            ItemStack stack = state.items.get(i);
            if (stack.isEmpty()) {
                continue;
            }

            double x = 0;
            double z = 0;
            double yRot = 0;

            switch (i) {
                case 0 -> {
                    x = 0.5;
                    z = 0.125;
                    yRot = 0;
                }
                case 1 -> {
                    x = 0.875;
                    z = 0.3125;
                    yRot = 22.5;
                }
                case 2 -> {
                    x = 0.875;
                    z = 0.6875;
                    yRot = -22.5;
                }
                case 3 -> {
                    x = 0.5;
                    z = 0.875;
                    yRot = 180;
                }
                case 4 -> {
                    x = 0.125;
                    z = 0.6875;
                    yRot = 157.5;
                }
                case 5 -> {
                    x = 0.125;
                    z = 0.3125;
                    yRot = -157.5;
                }
            }

            this.renderStack(stack, poseStack, submitNodeCollector, state.lightCoords,
                    x, 0.125, z, 0.82F, yRot, 0);
        }
        poseStack.popPose();
    }
}
