package com.github.ysbbbbbb.kaleidoscopetavern.client.render.block;

import com.github.ysbbbbbb.kaleidoscopetavern.block.deco.ChalkboardBlock;
import com.github.ysbbbbbb.kaleidoscopetavern.blockentity.deco.TextBlockEntity;
import com.github.ysbbbbbb.kaleidoscopetavern.client.render.renderstate.TextBlockEntityRenderState;
import com.github.ysbbbbbb.kaleidoscopetavern.util.TextAlignment;
import com.mojang.blaze3d.vertex.PoseStack;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.state.CameraRenderState;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.util.ARGB;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.phys.Vec3;
import org.apache.commons.lang3.StringUtils;
import org.joml.Matrix4f;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.util.List;

@Environment(EnvType.CLIENT)
public abstract class TextBlockEntityRender<T extends TextBlockEntity, M extends TextBlockEntityRenderState> implements BlockEntityRenderer<T, M> {
    protected static final int OUTLINE_RENDER_DISTANCE = Mth.square(16);
    protected final Font font;
    private final Minecraft minecraft = Minecraft.getInstance();

    public TextBlockEntityRender(BlockEntityRendererProvider.Context context) {
        this.font = context.font();
    }

    @Override
    public void extractRenderState(T blockEntity, M blockEntityRenderState, float f, @NonNull Vec3 vec3, ModelFeatureRenderer.@Nullable CrumblingOverlay crumblingOverlay) {
        BlockEntityRenderer.super.extractRenderState(blockEntity, blockEntityRenderState, f, vec3, crumblingOverlay);
        blockEntityRenderState.facing = blockEntity.getBlockState().getValue(ChalkboardBlock.FACING);
        blockEntityRenderState.text = blockEntity.getText();
        blockEntityRenderState.color = blockEntity.getColor();
        blockEntityRenderState.glowing = blockEntity.isGlowing();
        blockEntityRenderState.textAlignment = blockEntity.getTextAlignment();
    }

    @Override
    public final void submit(M blockEntityRenderState, @NonNull PoseStack poseStack, @NonNull SubmitNodeCollector submitNodeCollector, @NonNull CameraRenderState cameraRenderState) {
        // 渲染模型本体
        this.renderModel(blockEntityRenderState, poseStack, submitNodeCollector, blockEntityRenderState.facing);

        // 渲染文本
        if (cameraRenderState.pos.distanceTo(Vec3.atCenterOf(blockEntityRenderState.blockPos)) <= 48) {
            if (StringUtils.isNotBlank(blockEntityRenderState.text)) {
                this.renderText(blockEntityRenderState, poseStack, submitNodeCollector, blockEntityRenderState.facing);
            }
        }
    }

    /**
     * 渲染模型本体，子类需要实现这个方法来渲染具体的模型
     */
    protected abstract void renderModel(M textBlockRenderState, PoseStack poseStack, SubmitNodeCollector submitNodeCollector, Direction facing);

    /**
     * 渲染文字，子类需要实现这个方法来渲染具体的文字
     */
    protected abstract void renderText(M textBlockRenderState, PoseStack poseStack, SubmitNodeCollector submitNodeCollector, Direction facing);

    /**
     * 渲染文字的辅助方法，涉及到文字的旋转、缩放、颜色计算和分行等逻辑，子类可以调用这个方法来简化文字渲染的实现
     * <p>
     * 需要注意的是这个方法没有进行矩阵的 push 和 pop 操作 <br>
     * 调用这个方法之前需要做好矩阵状态的保存和恢复
     */
    protected void doTextRender(M textBlockRenderState, PoseStack poseStack, String text, int maxWidth, float scale, int maxLines, int lineHeight) {
        poseStack.scale(scale, -scale, scale);
        MultiBufferSource.BufferSource buffer = minecraft.renderBuffers().bufferSource();
        int darkColor = this.getDarkColor(textBlockRenderState.color, textBlockRenderState.glowing);
        int textColor;
        boolean hasOutline;
        int light;

        if (textBlockRenderState.glowing) {
            textColor = textBlockRenderState.color.getTextColor();
            hasOutline = this.isOutlineVisible(textBlockRenderState.blockPos, textColor);
            light = 0xf000f0;
        } else {
            textColor = darkColor;
            hasOutline = false;
            light = textBlockRenderState.lightCoords;
        }

        Component renderText = getRenderText(text);
        List<FormattedCharSequence> splitLines = font.split(renderText, maxWidth);
        int totalLines = Math.min(splitLines.size(), maxLines);

        for (int i = 0; i < totalLines; i++) {
            FormattedCharSequence line = splitLines.get(i);
            float posX = this.getPosX(textBlockRenderState, maxWidth, font.width(line));
            float posY = i * lineHeight - 19;
            Matrix4f pose = poseStack.last().pose();

            if (hasOutline) {
                font.drawInBatch8xOutline(line, posX, posY, textColor, darkColor, pose, buffer, light);
            } else {
                font.drawInBatch(line, posX, posY, textColor, false, pose,
                        buffer, Font.DisplayMode.POLYGON_OFFSET, 0, light);
            }
        }
    }

    protected Component getRenderText(String text) {
        return Component.literal(text);
    }

    protected float getPosX(M textBlock, int maxWidth, int lineWidth) {
        TextAlignment alignment = textBlock.textAlignment;
        if (alignment == TextAlignment.LEFT) {
            return -maxWidth / 2f;
        } else if (alignment == TextAlignment.RIGHT) {
            return maxWidth / 2f - lineWidth;
        } else {
            return -lineWidth / 2f;
        }
    }

    protected int getDarkColor(DyeColor color, boolean isGlowing) {
        int textColor = color.getTextColor();
        if (textColor == DyeColor.BLACK.getTextColor() && isGlowing) {
            return 0xff_f0ebcc;
        }
        double darknessFactor = 0.6;
        int red = (int) (ARGB.red(textColor) * darknessFactor);
        int green = (int) (ARGB.green(textColor) * darknessFactor);
        int blue = (int) (ARGB.blue(textColor) * darknessFactor);
        return ARGB.color(0, red, green, blue);
    }

    protected boolean isOutlineVisible(BlockPos blockPos, int textColor) {
        if (textColor == DyeColor.BLACK.getTextColor()) {
            return true;
        }
        Minecraft mc = Minecraft.getInstance();
        LocalPlayer player = mc.player;
        if (player != null && mc.options.getCameraType().isFirstPerson() && player.isScoping()) {
            return true;
        }
        Entity entity = mc.getCameraEntity();
        return entity != null && entity.distanceToSqr(Vec3.atCenterOf(blockPos)) < OUTLINE_RENDER_DISTANCE;
    }
}
