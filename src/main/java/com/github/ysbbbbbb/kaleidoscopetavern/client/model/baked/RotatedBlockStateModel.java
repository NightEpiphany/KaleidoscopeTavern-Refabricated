package com.github.ysbbbbbb.kaleidoscopetavern.client.model.baked;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.block.dispatch.BlockStateModel;
import net.minecraft.client.renderer.block.dispatch.BlockStateModelPart;
import net.minecraft.client.resources.model.sprite.Material;
import net.minecraft.util.RandomSource;
import org.joml.Matrix4f;
import org.joml.Matrix4fc;
import org.jspecify.annotations.NonNull;

import java.util.ArrayList;
import java.util.List;

@Environment(EnvType.CLIENT)
public final class RotatedBlockStateModel implements BlockStateModel {
    private static final double SEGMENT_RADIANS = Math.PI * 2.0D / 16.0D;
    private final BlockStateModel wrapped;
    private final double sin;
    private final double cos;
    private final Matrix4fc normalTransform;

    public RotatedBlockStateModel(BlockStateModel wrapped, int rotation) {
        this.wrapped = wrapped;
        double radians = rotation * SEGMENT_RADIANS;
        this.sin = Math.sin(radians);
        this.cos = Math.cos(radians);
        this.normalTransform = new Matrix4f().rotateY((float) -radians);
    }

    @Override
    public void collectParts(@NonNull RandomSource randomSource, @NonNull List<BlockStateModelPart> parts) {
        List<BlockStateModelPart> wrappedParts = new ArrayList<>(4);
        this.wrapped.collectParts(randomSource, wrappedParts);
        for (BlockStateModelPart part : wrappedParts) {
            parts.add(new RotatedBlockStateModelPart(part, this.sin, this.cos, this.normalTransform));
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

    @Override
    public boolean hasMaterialFlag(int flag) {
        return this.wrapped.hasMaterialFlag(flag);
    }
}
