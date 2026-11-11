package com.github.ysbbbbbb.kaleidoscopetavern.client.model.baked;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.block.dispatch.BlockStateModel;
import net.minecraft.client.renderer.block.dispatch.BlockStateModelPart;
import net.minecraft.client.resources.model.geometry.BakedQuad;
import net.minecraft.client.resources.model.sprite.Material;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.joml.Vector3fc;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.util.ArrayList;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;

@Environment(EnvType.CLIENT)
public final class RotatedBlockStateModel implements BlockStateModel {
    private static final double SEGMENT_RADIANS = Math.PI * 2.0D / 16.0D;
    private static final Direction[] DIRECTIONS = Direction.values();
    private final BlockStateModel wrapped;
    private final Map<BlockStateModelPart, BlockStateModelPart> rotatedParts = new IdentityHashMap<>(4);
    private final double sin;
    private final double cos;
    private final Matrix4f normalTransform;

    public RotatedBlockStateModel(BlockStateModel wrapped, int rotation) {
        this.wrapped = wrapped;
        double radians = rotation * SEGMENT_RADIANS;
        this.sin = Math.sin(radians);
        this.cos = Math.cos(radians);
        this.normalTransform = new Matrix4f().rotateY((float) -radians);
    }

    @Override
    public void collectParts(@NonNull RandomSource randomSource, @NonNull List<BlockStateModelPart> parts) {
        int firstPart = parts.size();
        this.wrapped.collectParts(randomSource, parts);
        // Chunk workers share baked models, so publish each rotated part under the same lock.
        synchronized (this.rotatedParts) {
            for (int index = firstPart; index < parts.size(); index++) {
                BlockStateModelPart part = parts.get(index);
                BlockStateModelPart rotatedPart = this.rotatedParts.get(part);
                if (rotatedPart == null) {
                    rotatedPart = new RotatedPart(part);
                    this.rotatedParts.put(part, rotatedPart);
                }
                parts.set(index, rotatedPart);
            }
        }
    }

    @Override
    public Material.@NonNull Baked particleMaterial() {
        return this.wrapped.particleMaterial();
    }

    @Override
    public int materialFlags() {
        return this.wrapped.materialFlags();
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

    private final class RotatedPart implements BlockStateModelPart {
        private final BlockStateModelPart wrappedPart;
        private final List<BakedQuad> quads;

        private RotatedPart(BlockStateModelPart wrappedPart) {
            this.wrappedPart = wrappedPart;
            this.quads = bakeQuads();
        }

        @Override
        public @NonNull List<BakedQuad> getQuads(@Nullable Direction direction) {
            return direction == null ? this.quads : List.of();
        }

        private List<BakedQuad> bakeQuads() {
            List<BakedQuad> unculled = this.wrappedPart.getQuads(null);
            int capacity = unculled.size();
            for (Direction direction : DIRECTIONS) {
                capacity += this.wrappedPart.getQuads(direction).size();
            }

            List<BakedQuad> rotated = new ArrayList<>(capacity);
            // Arbitrary rotations invalidate axis-aligned cull faces.
            addRotated(rotated, unculled);
            for (Direction direction : DIRECTIONS) {
                addRotated(rotated, this.wrappedPart.getQuads(direction));
            }
            return List.copyOf(rotated);
        }

        private void addRotated(List<BakedQuad> output, List<BakedQuad> source) {
            for (BakedQuad quad : source) {
                output.add(rotateQuad(quad));
            }
        }

        @Override
        public boolean useAmbientOcclusion() {
            return this.wrappedPart.useAmbientOcclusion();
        }

        @Override
        public Material.@NonNull Baked particleMaterial() {
            return this.wrappedPart.particleMaterial();
        }

        @Override
        public int materialFlags() {
            return this.wrappedPart.materialFlags();
        }
    }
}
