package com.github.ysbbbbbb.kaleidoscopetavern.client.init;

import com.github.ysbbbbbb.kaleidoscopetavern.api.client.IModelModifyRotationAfterBake;
import com.github.ysbbbbbb.kaleidoscopetavern.client.model.baked.RotatedBlockStateModel;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.block.dispatch.BlockStateModel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.Property;

@Environment(EnvType.CLIENT)
public final class CommonModelLoading {
    private CommonModelLoading() {
    }

    public static BlockStateModel wrapModel(BlockStateModel model, BlockState state) {
        if (!(state.getBlock() instanceof IModelModifyRotationAfterBake<?> rotationAfterBake)) {
            return model;
        }

        Property<Integer> rotationProperty = rotationAfterBake.getRotationProperty();
        if (rotationProperty != BlockStateProperties.ROTATION_16 || !state.hasProperty(rotationProperty)) {
            return model;
        }

        int rotation = state.getValue(rotationProperty);
        if (rotation <= 0) {
            return model;
        }
        return new RotatedBlockStateModel(model, rotation);
    }
}
