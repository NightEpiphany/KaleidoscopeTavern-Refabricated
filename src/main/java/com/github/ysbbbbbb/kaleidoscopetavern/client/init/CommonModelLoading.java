package com.github.ysbbbbbb.kaleidoscopetavern.client.init;

import com.github.ysbbbbbb.kaleidoscopetavern.api.client.IModelModifyRotationAfterBake;
import com.github.ysbbbbbb.kaleidoscopetavern.client.model.baked.RotatedBlockModelPart;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.model.loading.v1.ModelLoadingPlugin;
import net.fabricmc.fabric.api.client.model.loading.v1.ModelModifier;
import net.minecraft.client.renderer.block.model.BlockModelPart;
import net.minecraft.client.renderer.block.model.BlockStateModel;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.Property;

import java.util.List;

@Environment(EnvType.CLIENT)
public final class CommonModelLoading {

    public static void init() {
        ModelLoadingPlugin.register(pluginContext -> pluginContext.modifyBlockModelAfterBake()
                .register(ModelModifier.WRAP_PHASE, (model, context) -> wrapModel(model, context)));
    }

    private static BlockStateModel wrapModel(BlockStateModel model, ModelModifier.AfterBakeBlock.Context context) {
        if (model == null || context == null || context.state() == null) {
            return model;
        }

        if (!(context.state().getBlock() instanceof IModelModifyRotationAfterBake<?> bake)) {
            return model;
        }
        Property<Integer> rotationProperty = bake.getRotationProperty();

        if (rotationProperty != BlockStateProperties.ROTATION_16)
            return model;

        Integer rotation = context.state().getValue(rotationProperty);
        if (rotation == null || rotation <= 0) {
            return model;
        }

        return new RotatedBlockStateModel(model, rotation);
    }

    private CommonModelLoading() {
    }

    private record RotatedBlockStateModel(BlockStateModel delegate, int rotation) implements BlockStateModel {
        @Override
        public void collectParts(RandomSource randomSource, List<BlockModelPart> list) {
            // 先收集原始 parts，再包装成旋转版
            List<BlockModelPart> originalParts = delegate.collectParts(randomSource);
            for (BlockModelPart part : originalParts) {
                list.add(new RotatedBlockModelPart(part, rotation));
            }
        }

        @Override
        public TextureAtlasSprite particleIcon() {
            return delegate.particleIcon();
        }
    }
}