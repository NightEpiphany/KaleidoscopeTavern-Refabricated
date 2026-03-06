package com.github.ysbbbbbb.kaleidoscopetavern.client.render.entity;

import com.github.ysbbbbbb.kaleidoscopetavern.client.render.renderstate.ThrownMolotovEntityRenderState;
import com.github.ysbbbbbb.kaleidoscopetavern.entity.ThrownMolotovEntity;
import com.github.ysbbbbbb.kaleidoscopetavern.init.ModBlocks;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.block.model.BlockStateModel;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.state.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.level.block.state.BlockState;
import org.jspecify.annotations.NonNull;

@Environment(EnvType.CLIENT)
public class ThrownMolotovRenderer extends EntityRenderer<ThrownMolotovEntity, ThrownMolotovEntityRenderState> {
    private final BlockRenderDispatcher renderer;

    public ThrownMolotovRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.renderer = context.getBlockRenderDispatcher();
    }

    @Override
    public ThrownMolotovEntityRenderState createRenderState() {
        return new ThrownMolotovEntityRenderState();
    }

    @Override
    public void extractRenderState(ThrownMolotovEntity entity, ThrownMolotovEntityRenderState entityRenderState, float f) {
        super.extractRenderState(entity, entityRenderState, f);
        entityRenderState.partialTicks = f;
        entityRenderState.tickCount = entity.tickCount;
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
        poseStack.mulPose(Axis.YP.rotationDegrees(rotation));
        poseStack.mulPose(Axis.XP.rotationDegrees(rotation * 0.7F));
        poseStack.translate(-0.5, -0.5, -0.5);

        BlockState blockState = ModBlocks.MOLOTOV.defaultBlockState();
        BlockStateModel blockStateModel = renderer.getBlockModel(blockState);
        submitNodeCollector.submitBlockModel(
                poseStack,
                Sheets.cutoutBlockSheet(),
                blockStateModel,
                1.0F,
                1.0F,
                1.0F,
                entityRenderState.lightCoords,
                OverlayTexture.NO_OVERLAY,
                0
        );
        poseStack.popPose();
        super.submit(entityRenderState, poseStack, submitNodeCollector, cameraRenderState);
    }
}
