package com.github.ysbbbbbb.kaleidoscopetavern.client.render.block;

import com.github.ysbbbbbb.kaleidoscopetavern.KaleidoscopeTavern;
import com.github.ysbbbbbb.kaleidoscopetavern.block.deco.ChalkboardBlock;
import com.github.ysbbbbbb.kaleidoscopetavern.blockentity.deco.ChalkboardBlockEntity;
import com.github.ysbbbbbb.kaleidoscopetavern.client.model.deco.LargeChalkboardModel;
import com.github.ysbbbbbb.kaleidoscopetavern.client.model.deco.SmallChalkboardModel;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FastColor;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.phys.Vec3;
import org.apache.commons.lang3.StringUtils;
import org.joml.Matrix4f;

import java.util.List;

public class ChalkboardBlockEntityRender implements BlockEntityRenderer<ChalkboardBlockEntity> {
    private static final ResourceLocation SMALL_TEXTURE = KaleidoscopeTavern.modLoc("textures/entity/deco/small_chalkboard.png");
    private static final ResourceLocation LARGE_TEXTURE = KaleidoscopeTavern.modLoc("textures/entity/deco/large_chalkboard.png");

    private static final int OUTLINE_RENDER_DISTANCE = Mth.square(16);
    private static final float TEXT_SCALE = 0.012f;
    private static final int LINE_HEIGHT = 12;
    private static final int MAX_LINES = 11;

    private final SmallChalkboardModel small;
    private final SmallChalkboardModel large;
    private final Font font;

    public ChalkboardBlockEntityRender(BlockEntityRendererProvider.Context context) {
        this.small = new SmallChalkboardModel(context.bakeLayer(SmallChalkboardModel.LAYER_LOCATION));
        this.large = new SmallChalkboardModel(context.bakeLayer(LargeChalkboardModel.LAYER_LOCATION));
        this.font = context.getFont();
    }

    @Override
    public void render(ChalkboardBlockEntity chalkboard, float partialTick, PoseStack poseStack,
                       MultiBufferSource buffer, int packedLight, int packedOverlay) {
        Direction facing = chalkboard.getBlockState().getValue(ChalkboardBlock.FACING);

        // 渲染模型本体
        this.renderModel(poseStack, buffer, packedLight, packedOverlay, facing, chalkboard.isLarge());

        // 渲染文本
        // 玩家视角超过黑板 64 格就不渲染文字了，节省性能
        Camera camera = Minecraft.getInstance().gameRenderer.getMainCamera();
        if (camera.getPosition().distanceTo(Vec3.atCenterOf(chalkboard.getBlockPos())) <= 48) {
            String text = chalkboard.getText();
            if (StringUtils.isNotBlank(text)) {
                this.renderText(chalkboard, poseStack, buffer, packedLight, facing, text);
            }
        }
    }

    private void renderModel(PoseStack poseStack, MultiBufferSource buffer, int packedLight, int packedOverlay, Direction facing, boolean isLarge) {
        poseStack.pushPose();

        poseStack.translate(0.5, 1.5, 0.5);
        poseStack.mulPose(Axis.ZN.rotationDegrees(180));
        poseStack.mulPose(Axis.YN.rotationDegrees(180 - facing.get2DDataValue() * 90));

        if (isLarge) {
            VertexConsumer consumer = buffer.getBuffer(RenderType.entityCutoutNoCull(LARGE_TEXTURE));
            large.renderToBuffer(poseStack, consumer, packedLight, packedOverlay, 1.0F, 1.0F, 1.0F, 1.0F);
        } else {
            VertexConsumer consumer = buffer.getBuffer(RenderType.entityCutoutNoCull(SMALL_TEXTURE));
            small.renderToBuffer(poseStack, consumer, packedLight, packedOverlay, 1.0F, 1.0F, 1.0F, 1.0F);
        }

        poseStack.popPose();
    }

    private void renderText(ChalkboardBlockEntity chalkboard, PoseStack poseStack, MultiBufferSource buffer,
                            int packedLight, Direction facing, String text) {
        int maxWidth = chalkboard.isLarge() ? 232 : 63;

        poseStack.pushPose();

        if (facing == Direction.EAST) {
            poseStack.translate(0.0625, 1.535, 0.5);
        } else if (facing == Direction.WEST) {
            poseStack.translate(0.9375, 1.535, 0.5);
        } else if (facing == Direction.SOUTH) {
            poseStack.translate(0.5, 1.535, 0.0625);
        } else if (facing == Direction.NORTH) {
            poseStack.translate(0.5, 1.535, 0.9375);
        }

        poseStack.mulPose(Axis.YN.rotationDegrees(facing.get2DDataValue() * 90));
        poseStack.scale(TEXT_SCALE, -TEXT_SCALE, TEXT_SCALE);

        int darkColor = getDarkColor(chalkboard.getColor(), chalkboard.isGlowing());
        int textColor;
        boolean hasOutline;
        int light;

        if (chalkboard.isGlowing()) {
            textColor = chalkboard.getColor().getTextColor();
            hasOutline = isOutlineVisible(chalkboard.getBlockPos(), textColor);
            light = 0xf000f0;
        } else {
            textColor = darkColor;
            hasOutline = false;
            light = packedLight;
        }

        List<FormattedCharSequence> splitLines = font.split(Component.literal(text), maxWidth);
        int totalLines = Math.min(splitLines.size(), MAX_LINES);

        for (int i = 0; i < totalLines; i++) {
            FormattedCharSequence line = splitLines.get(i);
            float posX = this.getPosX(chalkboard, maxWidth, font.width(line));
            float posY = i * LINE_HEIGHT - 19;
            Matrix4f pose = poseStack.last().pose();

            if (hasOutline) {
                font.drawInBatch8xOutline(line, posX, posY, textColor, darkColor, pose, buffer, light);
            } else {
                font.drawInBatch(line, posX, posY, textColor, false, pose,
                        buffer, Font.DisplayMode.POLYGON_OFFSET, 0, light);
            }
        }

        poseStack.popPose();
    }

    private float getPosX(ChalkboardBlockEntity chalkboard, int maxWidth, int lineWidth) {
        ChalkboardBlockEntity.TextAlignment alignment = chalkboard.getTextAlignment();
        if (alignment == ChalkboardBlockEntity.TextAlignment.LEFT) {
            return -maxWidth / 2f;
        } else if (alignment == ChalkboardBlockEntity.TextAlignment.RIGHT) {
            return maxWidth / 2f - lineWidth;
        } else {
            return -lineWidth / 2f;
        }
    }

    private int getDarkColor(DyeColor color, boolean isGlowing) {
        int textColor = color.getTextColor();
        if (textColor == DyeColor.BLACK.getTextColor() && isGlowing) {
            return 0xff_f0ebcc;
        }
        double darknessFactor = 0.6;
        int red = (int) (FastColor.ARGB32.red(textColor) * darknessFactor);
        int green = (int) (FastColor.ARGB32.green(textColor) * darknessFactor);
        int blue = (int) (FastColor.ARGB32.blue(textColor) * darknessFactor);
        return FastColor.ARGB32.color(0, red, green, blue);
    }

    private boolean isOutlineVisible(BlockPos blockPos, int textColor) {
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
