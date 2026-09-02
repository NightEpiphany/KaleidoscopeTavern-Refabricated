package com.github.ysbbbbbb.kaleidoscopetavern.client.model.baked;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.BlockModelPart;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.Direction;
import org.joml.Vector3f;
import org.joml.Vector3fc;
import org.jspecify.annotations.NonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Environment(EnvType.CLIENT)
public final class RotatedBlockModelPart implements BlockModelPart {
    private static final double SEGMENT_RADIANS = Math.PI * 2.0D / 16.0D;

    private final BlockModelPart delegate;
    private final float sin;
    private final float cos;

    public RotatedBlockModelPart(BlockModelPart delegate, int rotation) {
        this.delegate = delegate;
        double radians = rotation * SEGMENT_RADIANS;
        this.sin = (float) Math.sin(radians);
        this.cos = (float) Math.cos(radians);
    }

    @Override
    public @NonNull List<BakedQuad> getQuads(Direction direction) {
        List<BakedQuad> original = delegate.getQuads(direction);
        if (original.isEmpty()) {
            return original;
        }
        List<BakedQuad> rotated = new ArrayList<>(original.size());
        for (BakedQuad quad : original) {
            rotated.add(rotateQuad(quad));
        }
        return Collections.unmodifiableList(rotated);
    }

    @Override
    public boolean useAmbientOcclusion() {
        return delegate.useAmbientOcclusion();
    }

    @Override
    public @NonNull TextureAtlasSprite particleIcon() {
        return delegate.particleIcon();
    }

    private @NonNull BakedQuad rotateQuad(BakedQuad quad) {
        // 与官方 1.21.1 RotatedBakedModel 相同的绕 Y 轴旋转公式（中心 0.5）
        // x' = x*cos - z*sin, z' = x*sin + z*cos
        Vector3f p0 = rotateAroundY(quad.position0());
        Vector3f p1 = rotateAroundY(quad.position1());
        Vector3f p2 = rotateAroundY(quad.position2());
        Vector3f p3 = rotateAroundY(quad.position3());

        Direction rotatedDir = calculateDirection(p0, p1, p2, p3);

        return new BakedQuad(p0, p1, p2, p3,
                quad.packedUV0(), quad.packedUV1(), quad.packedUV2(), quad.packedUV3(),
                quad.tintIndex(), rotatedDir, quad.sprite(), quad.shade(), quad.lightEmission());
    }

    private @NonNull Vector3f rotateAroundY(Vector3fc pos) {
        float centeredX = pos.x() - 0.5f;
        float centeredZ = pos.z() - 0.5f;
        float newX = centeredX * this.cos - centeredZ * this.sin + 0.5f;
        float newZ = centeredX * this.sin + centeredZ * this.cos + 0.5f;
        return new Vector3f(newX, pos.y(), newZ);
    }

    private static Direction calculateDirection(Vector3fc p0, Vector3fc p1, Vector3fc p2, Vector3fc p3) {
        Vector3f v1 = new Vector3f(p1).sub(p0);
        Vector3f v2 = new Vector3f(p2).sub(p0);
        Vector3f normal = v1.cross(v2);
        return Direction.getApproximateNearest(normal.x(), normal.y(), normal.z());
    }
}