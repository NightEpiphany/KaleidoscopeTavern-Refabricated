package com.github.ysbbbbbb.kaleidoscopetavern.client.render.block;

import com.github.ysbbbbbb.kaleidoscopetavern.block.deco.HolderBlock;
import com.github.ysbbbbbb.kaleidoscopetavern.blockentity.deco.HolderBlockEntity;
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
public class HolderBlockEntityRender extends StorageBlockEntityRender<HolderBlockEntity, StorageBlockEntityRenderState> {
    public HolderBlockEntityRender(BlockEntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public void extractRenderState(@NonNull HolderBlockEntity blockEntity, @NonNull StorageBlockEntityRenderState blockEntityRenderState, float f, @NonNull Vec3 vec3, ModelFeatureRenderer.@Nullable CrumblingOverlay crumblingOverlay) {
        super.extractRenderState(blockEntity, blockEntityRenderState, f, vec3, crumblingOverlay);
        blockEntityRenderState.facing = blockEntity.getBlockState().getValue(HolderBlock.FACING);
    }

    @Override
    public @NonNull StorageBlockEntityRenderState createRenderState() {
        return new StorageBlockEntityRenderState();
    }

    @Override
    public void submit(StorageBlockEntityRenderState state, @NonNull PoseStack poseStack, @NonNull SubmitNodeCollector submitNodeCollector, @NonNull CameraRenderState cameraRenderState) {
        if (state.items.isEmpty()) {
            return;
        }
        ItemStack stack = state.items.get(0);
        if (stack.isEmpty()) {
            return;
        }

        poseStack.pushPose();
        this.applyFacingRotation(state.facing, poseStack);
        this.renderStack(stack, poseStack, submitNodeCollector, state.lightCoords,
                0.5, 0.125, 0.75, 0.95f, 0, -45);
        poseStack.popPose();
    }
}
