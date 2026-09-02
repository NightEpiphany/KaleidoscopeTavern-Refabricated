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
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.state.CameraRenderState;
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
    public void extractRenderState(@NonNull ShakerBlockEntity blockEntity, @NonNull ShakerBlockEntityRenderState blockEntityRenderState, float f, @NonNull Vec3 vec3, ModelFeatureRenderer.@Nullable CrumblingOverlay crumblingOverlay) {
        BlockEntityRenderer.super.extractRenderState(blockEntity, blockEntityRenderState, f, vec3, crumblingOverlay);
        if (blockEntity.getLevel() != null) {
            blockEntityRenderState.time = blockEntity.getLevel().getGameTime() + f;
        }
        blockEntityRenderState.lastPutTick = blockEntity.lastPutTick;
    }

    @Override
    public @NonNull ShakerBlockEntityRenderState createRenderState() {
        return new ShakerBlockEntityRenderState();
    }

    @Override
    public void submit(@NonNull ShakerBlockEntityRenderState shaker, @NonNull PoseStack poseStack, @NonNull SubmitNodeCollector submitNodeCollector, @NonNull CameraRenderState cameraRenderState) {
        // 动画数据（ageInTicks + lastPutTick）随 State 传入 submitModel，
        // 渲染管线会在 model.setupAnim(state) 阶段应用加酒动画。

        poseStack.pushPose();
        poseStack.translate(0.5, 1.5, 0.5);
        poseStack.mulPose(Axis.ZN.rotationDegrees(180));
        poseStack.mulPose(Axis.YN.rotationDegrees(180));
        submitNodeCollector.submitModel(
                model,
                new ShakerModel.State(shaker.time, shaker.lastPutTick),
                poseStack,
                RenderTypes.entityCutoutNoCull(TEXTURE),
                shaker.lightCoords,
                OverlayTexture.NO_OVERLAY,
                0,
                null
        );
        poseStack.popPose();
    }
}