package com.github.ysbbbbbb.kaleidoscopetavern.util;

import com.mojang.blaze3d.vertex.PoseStack;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.transfer.v1.client.fluid.FluidVariantRenderHandler;
import net.fabricmc.fabric.api.transfer.v1.client.fluid.FluidVariantRendering;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.impl.client.rendering.fluid.FluidRenderingRegistryImpl;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.block.BlockAndTintGetter;
import net.minecraft.client.renderer.block.FluidModel;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.texture.MissingTextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.sprite.SpriteId;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;

@Environment(EnvType.CLIENT)
public class RenderUtils {
    private static final int ALPHA_MASK = 0xFF000000;

    /**
     * 渲染流体的工具方法
     *
     * @param fluid     要渲染的流体
     * @param poseStack PoseStack
     * @param collector SubmitNodeCollector
     * @param light     PackedLight
     * @param size      流体平面贴图的大小（0-16），可以根据实际需要调整
     * @param y         流体平面贴图的高度，根据实际流体显示高度调整
     */
    public static void renderFluid(Fluid fluid, PoseStack poseStack, SubmitNodeCollector collector, int light, int size, float y) {
        renderFluid(fluid, null, null, poseStack, collector, light, size, y);
    }

    public static void renderFluid(Fluid fluid, BlockAndTintGetter level, BlockPos pos, PoseStack poseStack, SubmitNodeCollector collector, int light, int size, float y) {
        TextureAtlasSprite sprite = getStillFluidSprite(fluid);
        int color = getFluidColor(level, pos, fluid);
        renderSurface(poseStack, collector, sprite, color, light, Math.clamp(size, 1, 16), y);
    }

    public static void renderWaterFluid(BlockAndTintGetter level, BlockPos pos, Fluid fluid, PoseStack poseStack, SubmitNodeCollector collector, int light, int size, float y) {
        renderFluid(fluid, level, pos, poseStack, collector, light, size, y);
    }

    /**
     * 工具方法，用于渲染流体贴图
     *
     * @param poseStack PoseStack
     * @param collector SubmitNodeCollector
     * @param sprite    TextureAtlasSprite
     * @param color     流体附加着色
     * @param light     PackedLight
     * @param size      流体平面贴图的大小（0-16）
     * @param y         流体平面贴图的高度
     */
    public static void renderSurface(PoseStack poseStack, SubmitNodeCollector collector, TextureAtlasSprite sprite,
                                     int color, int light, int size, float y)
    {
        int tintedColor = ensureAlpha(color);
        collector.submitCustomGeometry(poseStack, RenderTypes.cutoutMovingBlock(), (pose, vertexConsumer) -> {
            var matrix = pose.pose();
            // 贴图的位置和大小
            int margin = (16 - size) / 2;
            float min = margin / 16f, max = 1 - margin / 16f;
            float spriteSize = size / 16f;

            // 渲染一个平面
            vertexConsumer.addVertex(matrix, min, y, min)
                    .setColor(tintedColor)
                    .setUv(sprite.getU0(), sprite.getV0())
                    .setOverlay(0)
                    .setLight(light)
                    .setNormal(pose, 0, 1, 0);
            vertexConsumer.addVertex(matrix, min, y, max)
                    .setColor(tintedColor)
                    .setUv(sprite.getU0(), sprite.getV(spriteSize))
                    .setOverlay(0)
                    .setLight(light)
                    .setNormal(pose, 0, 1, 0);
            vertexConsumer.addVertex(matrix, max, y, max)
                    .setColor(tintedColor)
                    .setUv(sprite.getU(spriteSize), sprite.getV(spriteSize))
                    .setOverlay(0)
                    .setLight(light)
                    .setNormal(pose, 0, 1, 0);
            vertexConsumer.addVertex(matrix, max, y, min)
                    .setColor(tintedColor)
                    .setUv(sprite.getU(spriteSize), sprite.getV0())
                    .setOverlay(0)
                    .setLight(light)
                    .setNormal(pose, 0, 1, 0);
        });
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
    @SuppressWarnings("all")
    private static TextureAtlasSprite getStillFluidSprite(Fluid fluid) {
        FluidModel.Unbaked unbaked = FluidRenderingRegistryImpl.getUnbakedModels().get(fluid);
        if (unbaked != null) {
            Identifier sprite = unbaked.stillMaterial().sprite();
            return Minecraft.getInstance().getModelManager().atlasManager.get(new SpriteId(TextureAtlas.LOCATION_BLOCKS, sprite));
        }
        if (fluid == Fluids.WATER || fluid == Fluids.FLOWING_WATER) {
            return Minecraft.getInstance().getModelManager().atlasManager.get(new SpriteId(TextureAtlas.LOCATION_BLOCKS, Identifier.withDefaultNamespace("block/water_still")));
        }
        if (fluid == Fluids.LAVA || fluid == Fluids.FLOWING_LAVA) {
            return Minecraft.getInstance().getModelManager().atlasManager.get(new SpriteId(TextureAtlas.LOCATION_BLOCKS, Identifier.withDefaultNamespace("block/lava_still")));
        }
        Identifier missing = MissingTextureAtlasSprite.getLocation();
        return Minecraft.getInstance().getModelManager().atlasManager.get(new SpriteId(TextureAtlas.LOCATION_BLOCKS, missing));
    }

    private static int getFluidColor(BlockAndTintGetter level, BlockPos pos, Fluid fluid) {
        if (fluid == Fluids.WATER) return -12618012;
        FluidVariantRenderHandler handler = FluidVariantRendering.getHandler(fluid);
        if (handler == null) {
            return 0xFFFFFFFF;
        }
        return handler.getColor(FluidVariant.of(fluid), level, pos);
    }

    private static int ensureAlpha(int color) {
        return (color & ALPHA_MASK) == 0 ? color | ALPHA_MASK : color;
    }
}
