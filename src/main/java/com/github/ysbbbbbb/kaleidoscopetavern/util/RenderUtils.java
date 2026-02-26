package com.github.ysbbbbbb.kaleidoscopetavern.util;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderHandler;
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderHandlerRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.MissingTextureAtlasSprite;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import org.joml.Matrix4f;

@Environment(EnvType.CLIENT)
public class RenderUtils {
    /**
     * 渲染流体的工具方法
     *
     * @param fluid     要渲染的流体
     * @param poseStack PoseStack
     * @param buffer    MultiBufferSource
     * @param light     PackedLight
     * @param size      流体平面贴图的大小（0-16），可以根据实际需要调整
     * @param y         流体平面贴图的高度，根据实际流体显示高度调整
     */
    public static void renderFluid(Fluid fluid, PoseStack poseStack, MultiBufferSource buffer, int light, int size, float y) {
        TextureAtlasSprite sprite = getStillFluidSprite(null, null, fluid);
        int color = getFluidColor(null, null, fluid);
        renderSurface(poseStack, buffer, sprite, color, light, Mth.clamp(size, 1, 16), y);
    }

    public static void renderFluid(BlockAndTintGetter level, BlockPos pos, Fluid fluid, PoseStack poseStack, MultiBufferSource buffer, int light, int size, float y) {
        TextureAtlasSprite sprite = getStillFluidSprite(level, pos, fluid);
        int color = getFluidColor(level, pos, fluid);
        renderSurface(poseStack, buffer, sprite, color, light, Mth.clamp(size, 1, 16), y);
    }

    /**
     * 工具方法，用于渲染流体贴图
     *
     * @param poseStack PoseStack
     * @param buffer    MultiBufferSource
     * @param sprite    TextureAtlasSprite
     * @param color     流体附加着色
     * @param light     PackedLight
     * @param size      流体平面贴图的大小（0-16）
     * @param y         流体平面贴图的高度
     */
    public static void renderSurface(PoseStack poseStack, MultiBufferSource buffer, TextureAtlasSprite sprite,
                                     int color, int light, int size, float y) {
        VertexConsumer vertexConsumer = buffer.getBuffer(RenderType.translucentNoCrumbling());
        Matrix4f matrix = poseStack.last().pose();

        // 贴图的位置和大小
        int margin = (16 - size) / 2;
        float min = margin / 16f, max = 1 - margin / 16f;

        // 渲染一个平面
        vertexConsumer.vertex(matrix, min, y, min)
                .color(color)
                .uv(sprite.getU0(), sprite.getV0())
                .overlayCoords(OverlayTexture.NO_OVERLAY)
                .uv2(light)
                .normal(0, 1, 0)
                .endVertex();
        vertexConsumer.vertex(matrix, min, y, max)
                .color(color)
                .uv(sprite.getU0(), sprite.getV(size))
                .overlayCoords(OverlayTexture.NO_OVERLAY)
                .uv2(light)
                .normal(0, 1, 0)
                .endVertex();
        vertexConsumer.vertex(matrix, max, y, max)
                .color(color)
                .uv(sprite.getU(size), sprite.getV(size))
                .overlayCoords(OverlayTexture.NO_OVERLAY)
                .uv2(light)
                .normal(0, 1, 0)
                .endVertex();
        vertexConsumer.vertex(matrix, max, y, min)
                .color(color)
                .uv(sprite.getU(size), sprite.getV0())
                .overlayCoords(OverlayTexture.NO_OVERLAY)
                .uv2(light)
                .normal(0, 1, 0)
                .endVertex();
    }

    /**
     * 基于方块坐标、物品索引和通道号生成稳定的伪随机浮点数，范围 [-1, 1]。
     * <p>
     * 使用 64 位位混淆哈希（Splitmix64 变体），无对象分配，适合逐帧调用。
     *
     * @param posSeed 方块坐标的 long 表示，作为基础种子
     * @param index   物品在槽位中的索引，保证每个物品结果不同
     * @param channel 通道编号，保证同一物品的不同旋转轴结果不同
     * @return [-1, 1] 范围内的伪随机浮点数
     */
    public static float stableRandom(long posSeed, int index, int channel) {
        long h = posSeed ^ ((long) index * 0x9e3779b97f4a7c15L) ^ ((long) channel * 0x6c62272e07bb0142L);
        h = (h ^ (h >>> 30)) * 0xbf58476d1ce4e5b9L;
        h = (h ^ (h >>> 27)) * 0x94d049bb133111ebL;
        h ^= (h >>> 31);
        return (float) (int) h / (float) Integer.MAX_VALUE;
    }

    private static TextureAtlasSprite getStillFluidSprite(BlockAndTintGetter level, BlockPos pos, Fluid fluid) {
        FluidState state = fluid.defaultFluidState();
        FluidRenderHandler handler = FluidRenderHandlerRegistry.INSTANCE.get(fluid);
        if (handler != null) {
            TextureAtlasSprite[] sprites = handler.getFluidSprites(level, pos, state);
            if (sprites != null && sprites.length > 0 && sprites[0] != null) {
                return sprites[0];
            }
        }
        ResourceLocation missing = MissingTextureAtlasSprite.getLocation();
        return Minecraft.getInstance().getTextureAtlas(InventoryMenu.BLOCK_ATLAS).apply(missing);
    }

    private static int getFluidColor(BlockAndTintGetter level, BlockPos pos, Fluid fluid) {
        if (level == null || pos == null) {
            return 0xFFFFFFFF;
        }
        FluidState state = fluid.defaultFluidState();
        FluidRenderHandler handler = FluidRenderHandlerRegistry.INSTANCE.get(fluid);
        if (handler == null) {
            return 0xFFFFFFFF;
        }
        return handler.getFluidColor(level, pos, state);
    }
}
