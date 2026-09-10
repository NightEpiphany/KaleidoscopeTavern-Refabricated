package com.github.ysbbbbbb.kaleidoscopetavern.client.render.block;

import com.github.ysbbbbbb.kaleidoscopetavern.blockentity.deco.SandwichBoardBlockEntity;
import com.github.ysbbbbbb.kaleidoscopetavern.client.render.renderstate.SandwichBoardBlockEntityRenderState;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.ChatFormatting;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.network.chat.Component;
import org.apache.commons.lang3.StringUtils;
import org.joml.Quaternionf;
import org.jspecify.annotations.NonNull;

@Environment(EnvType.CLIENT)
public class SandwichBlockEntityRender extends TextBlockEntityRender<SandwichBoardBlockEntity, SandwichBoardBlockEntityRenderState> {
    private static final float TEXT_SCALE = 0.01f;
    private static final int LINE_HEIGHT = 10;
    private static final int MAX_LINES = 8;

    public SandwichBlockEntityRender(BlockEntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    protected void renderModel(SandwichBoardBlockEntityRenderState textBlockRenderState, PoseStack poseStack, SubmitNodeCollector submitNodeCollector, int direction) {
        // 展板使用的是方块模型，不需要特殊渲染
    }

    @Override
    protected void renderText(SandwichBoardBlockEntityRenderState textBlockRenderState, PoseStack poseStack, SubmitNodeCollector submitNodeCollector, int direction) {

        float angle = direction * 22.5f + 180;
        float radians = (float) Math.toRadians(angle);
        float xOffset = (float) (-Math.sin(radians) * 0.06f);
        float zOffset = (float) (Math.cos(radians) * 0.06f);
        float tiltAxisX = (float) -Math.cos(radians);
        float tiltAxisZ = (float) -Math.sin(radians);

        poseStack.pushPose();
        poseStack.translate(0.5 + xOffset, 1.06, 0.5 + zOffset);

        poseStack.rotate(new Quaternionf().rotateAxis((float) Math.toRadians(22.5f), tiltAxisX, 0.0f, tiltAxisZ));
        poseStack.rotateDegrees(Axis.YN, angle);

        int maxWidth = 55;
        if (StringUtils.isNotBlank(textBlockRenderState.text))
            doTextRender(textBlockRenderState, poseStack, textBlockRenderState.text, maxWidth, TEXT_SCALE, MAX_LINES, LINE_HEIGHT, submitNodeCollector);

        poseStack.popPose();
    }

    @Override
    protected Component getRenderText(String text) {
        return Component.literal(text).withStyle(ChatFormatting.BOLD);
    }

    @Override
    public @NonNull SandwichBoardBlockEntityRenderState createRenderState() {
        return new SandwichBoardBlockEntityRenderState();
    }
}
