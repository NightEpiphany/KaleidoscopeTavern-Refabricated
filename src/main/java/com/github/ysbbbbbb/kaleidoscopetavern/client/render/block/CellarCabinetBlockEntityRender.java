package com.github.ysbbbbbb.kaleidoscopetavern.client.render.block;

import com.github.ysbbbbbb.kaleidoscopetavern.block.brew.CellarCabinetBlock;
import com.github.ysbbbbbb.kaleidoscopetavern.blockentity.brew.CellarCabinetBlockEntity;
import com.github.ysbbbbbb.kaleidoscopetavern.client.render.renderstate.StorageBlockEntityRenderState;
import com.mojang.blaze3d.vertex.PoseStack;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.state.CameraRenderState;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

@Environment(EnvType.CLIENT)
public class CellarCabinetBlockEntityRender extends StorageBlockEntityRender<CellarCabinetBlockEntity, StorageBlockEntityRenderState> {
    public CellarCabinetBlockEntityRender(BlockEntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public void extractRenderState(@NonNull CellarCabinetBlockEntity blockEntity, @NonNull StorageBlockEntityRenderState blockEntityRenderState, float f, @NonNull Vec3 vec3, ModelFeatureRenderer.@Nullable CrumblingOverlay crumblingOverlay) {
        super.extractRenderState(blockEntity, blockEntityRenderState, f, vec3, crumblingOverlay);
        blockEntityRenderState.facing = blockEntity.getBlockState().getValue(CellarCabinetBlock.FACING);
    }

    @Override
    public @NonNull StorageBlockEntityRenderState createRenderState() {
        return new StorageBlockEntityRenderState();
    }

    @Override
    public void submit(StorageBlockEntityRenderState state, @NonNull PoseStack poseStack, @NonNull SubmitNodeCollector submitNodeCollector, @NonNull CameraRenderState cameraRenderState) {
        Direction direction = state.facing;
        poseStack.pushPose();
        this.applyFacingRotation(direction, poseStack);
        for (int i = 0; i < state.items.size(); i++) {
            ItemStack stack = state.items.get(i);
            if (stack.isEmpty()) {
                continue;
            }

            int row = i / 3;
            int column = i % 3;
            double x = 0.825 - column * 0.325;
            double y = 0.78 - row * 0.29;

            this.renderStack(stack, poseStack, submitNodeCollector, state.lightCoords,
                    x, y, 0.875, 1, 0, -90);
        }
        poseStack.popPose();
    }
}
