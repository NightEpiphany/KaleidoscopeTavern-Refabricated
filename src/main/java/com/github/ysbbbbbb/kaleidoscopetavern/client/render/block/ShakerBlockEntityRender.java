package com.github.ysbbbbbb.kaleidoscopetavern.client.render.block;

import com.github.ysbbbbbb.kaleidoscopetavern.KaleidoscopeTavern;
import com.github.ysbbbbbb.kaleidoscopetavern.blockentity.mixology.ShakerBlockEntity;
import com.github.ysbbbbbb.kaleidoscopetavern.client.model.mixology.ShakerModel;
import com.github.ysbbbbbb.kaleidoscopetavern.client.render.renderstate.ShakerBlockEntityRenderState;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.Identifier;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

@Environment(EnvType.CLIENT)
public class ShakerBlockEntityRender implements BlockEntityRenderer<ShakerBlockEntity, ShakerBlockEntityRenderState> {
    private static final Identifier TEXTURE = Identifier.fromNamespaceAndPath(KaleidoscopeTavern.MOD_ID, "textures/block/mixology/shaker.png");
    private final ShakerModel model;

    public ShakerBlockEntityRender(BlockEntityRendererProvider.Context context) {
        this.model = new ShakerModel(context.bakeLayer(ShakerModel.LAYER_LOCATION));
    }

    @Override
    public void extractRenderState(@NonNull ShakerBlockEntity blockEntity, @NonNull ShakerBlockEntityRenderState renderState,
                                   float partialTick, @NonNull Vec3 cameraPos, ModelFeatureRenderer.@Nullable CrumblingOverlay crumblingOverlay) {
        BlockEntityRenderer.super.extractRenderState(blockEntity, renderState, partialTick, cameraPos, crumblingOverlay);
        renderState.animationAge = blockEntity.getLevel() == null ? partialTick : blockEntity.getLevel().getGameTime() + partialTick;
        renderState.put.copyFrom(blockEntity.putState);
    }

    @Override
    public @NonNull ShakerBlockEntityRenderState createRenderState() {
        return new ShakerBlockEntityRenderState();
    }

    @Override
    public void submit(@NonNull ShakerBlockEntityRenderState renderState, @NonNull PoseStack poseStack,
                       @NonNull SubmitNodeCollector submitNodeCollector, @NonNull CameraRenderState cameraRenderState) {
        poseStack.pushPose();
        poseStack.translate(0.5, 1.5, 0.5);
        poseStack.rotateDegrees(Axis.ZN, 180);
        poseStack.rotateDegrees(Axis.YN, 180);

        ShakerModel.State state = new ShakerModel.State(renderState.animationAge, renderState.put);
        this.model.resetPose();
        this.model.setupAnim(state);
        RenderType renderType = RenderTypes.entityCutout(TEXTURE);
        submitNodeCollector.submitModel(this.model, state, poseStack, renderType, renderState.lightCoords, OverlayTexture.NO_OVERLAY, 0);
        poseStack.popPose();
    }
}
