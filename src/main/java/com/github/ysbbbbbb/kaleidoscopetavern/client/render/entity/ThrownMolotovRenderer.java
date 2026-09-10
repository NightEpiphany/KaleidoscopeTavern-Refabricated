package com.github.ysbbbbbb.kaleidoscopetavern.client.render.entity;

import com.github.ysbbbbbb.kaleidoscopetavern.client.render.renderstate.ThrownMolotovEntityRenderState;
import com.github.ysbbbbbb.kaleidoscopetavern.entity.ThrownMolotovEntity;
import com.github.ysbbbbbb.kaleidoscopetavern.init.ModBlocks;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.block.BlockModelResolver;
import net.minecraft.client.renderer.block.model.BlockDisplayContext;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import org.jspecify.annotations.NonNull;

@Environment(EnvType.CLIENT)
public class ThrownMolotovRenderer extends EntityRenderer<ThrownMolotovEntity, ThrownMolotovEntityRenderState> {
    private final BlockModelResolver resolver;
    public static final BlockDisplayContext BLOCK_DISPLAY_CONTEXT = BlockDisplayContext.create();

    public ThrownMolotovRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.resolver = context.getBlockModelResolver();
    }

    @Override
    public @NonNull ThrownMolotovEntityRenderState createRenderState() {
        return new ThrownMolotovEntityRenderState();
    }

    @Override
    public void extractRenderState(@NonNull ThrownMolotovEntity entity, @NonNull ThrownMolotovEntityRenderState entityRenderState, float f) {
        super.extractRenderState(entity, entityRenderState, f);
        entityRenderState.partialTicks = f;
        entityRenderState.tickCount = entity.tickCount;
        this.resolver.update(entityRenderState.bottleModel, ModBlocks.MOLOTOV.defaultBlockState(), BLOCK_DISPLAY_CONTEXT);
    }

    @Override
    public void submit(ThrownMolotovEntityRenderState entityRenderState, @NonNull PoseStack poseStack, @NonNull SubmitNodeCollector submitNodeCollector, @NonNull CameraRenderState cameraRenderState) {
        poseStack.pushPose();
        // 缩小方块模型，使其适合投射物大小
        poseStack.scale(0.5F, 0.5F, 0.5F);
        poseStack.translate(-0.5, 0, -0.5);

        // 飞行时旋转
        float rotation = (entityRenderState.tickCount + entityRenderState.partialTicks) * 20.0F;
        poseStack.translate(0.5, 0.5, 0.5);
        poseStack.rotateDegrees(Axis.YP, rotation);
        poseStack.rotateDegrees(Axis.XP, rotation * 0.7F);
        poseStack.translate(-0.5, -0.5, -0.5);

        entityRenderState.bottleModel.submit(
                poseStack,
                submitNodeCollector,
                entityRenderState.lightCoords,
                OverlayTexture.NO_OVERLAY,
                0
        );
        poseStack.popPose();
        super.submit(entityRenderState, poseStack, submitNodeCollector, cameraRenderState);
    }
}
