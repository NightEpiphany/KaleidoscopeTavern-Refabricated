package com.github.ysbbbbbb.kaleidoscopetavern.client.model.baked;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.block.dispatch.BlockStateModelPart;
import net.minecraft.client.resources.model.geometry.BakedQuad;
import net.minecraft.client.resources.model.sprite.Material;
import net.minecraft.core.Direction;
import org.joml.Matrix4fc;
import org.joml.Vector3f;
import org.joml.Vector3fc;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

@Environment(EnvType.CLIENT)
final class RotatedBlockStateModelPart implements BlockStateModelPart {
    private static final Direction[] DIRECTIONS = Direction.values();
    private final BlockStateModelPart wrapped;
    private final double sin;
    private final double cos;
    private final Matrix4fc normalTransform;

    RotatedBlockStateModelPart(BlockStateModelPart wrapped, double sin, double cos, Matrix4fc normalTransform) {
        this.wrapped = wrapped;
        this.sin = sin;
        this.cos = cos;
        this.normalTransform = normalTransform;
    }

    @Override
    public @NonNull List<BakedQuad> getQuads(@Nullable Direction direction) {
        if (direction != null) {
            return List.of();
        }

        List<BakedQuad> unculled = this.wrapped.getQuads(null);
        ArrayList<BakedQuad> rotated = new ArrayList<>(unculled.size() + DIRECTIONS.length);
        addRotated(rotated, unculled);
        for (Direction sourceDirection : DIRECTIONS) {
            addRotated(rotated, this.wrapped.getQuads(sourceDirection));
        }
        return List.copyOf(rotated);
    }

    private void addRotated(ArrayList<BakedQuad> output, List<BakedQuad> source) {
        output.ensureCapacity(output.size() + source.size());
        for (BakedQuad quad : source) {
            output.add(rotateQuad(quad));
        }
    }

    private BakedQuad rotateQuad(BakedQuad quad) {
        Direction rotatedDirection = Direction.rotate(this.normalTransform, quad.direction());
        return new BakedQuad(
                rotatePosition(quad.position0()),
                rotatePosition(quad.position1()),
                rotatePosition(quad.position2()),
                rotatePosition(quad.position3()),
                quad.packedUV0(),
                quad.packedUV1(),
                quad.packedUV2(),
                quad.packedUV3(),
                rotatedDirection,
                quad.materialInfo()
        );
    }

    private Vector3fc rotatePosition(Vector3fc source) {
        double centeredX = source.x() - 0.5D;
        double centeredZ = source.z() - 0.5D;
        return new Vector3f(
                (float) (centeredX * this.cos - centeredZ * this.sin + 0.5D),
                source.y(),
                (float) (centeredX * this.sin + centeredZ * this.cos + 0.5D)
        );
    }

    @Override
    public boolean useAmbientOcclusion() {
        return this.wrapped.useAmbientOcclusion();
    }

    @Override
    public Material.@NonNull Baked particleMaterial() {
        return this.wrapped.particleMaterial();
    }

    @Override
    public int materialFlags() {
        return this.wrapped.materialFlags();
    }
}
