package com.github.ysbbbbbb.kaleidoscopetavern.datagen.model;

import com.github.ysbbbbbb.kaleidoscopetavern.KaleidoscopeTavern;
import com.github.ysbbbbbb.kaleidoscopetavern.block.properties.ConnectionType;
import com.github.ysbbbbbb.kaleidoscopetavern.util.ColorUtils;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.model.generators.BlockModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

public class BlockModelGenerator extends BlockModelProvider {
    public BlockModelGenerator(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, KaleidoscopeTavern.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        for (String color : ColorUtils.COLORS) {
            sofa(color);
            barStool(color);
        }
    }

    private void sofa(String color) {
        ResourceLocation texture = modLoc("block/deco/sofa/%s".formatted(color));
        ResourceLocation particle = mcLoc("block/%s_wool".formatted(color));

        for (ConnectionType type : ConnectionType.values()) {
            String typeName = type.getSerializedName();
            String name = "block/deco/sofa/%s/%s".formatted(color, typeName);
            ResourceLocation parent = modLoc("block/deco/sofa/base/%s".formatted(typeName));
            withExistingParent(name, parent)
                    .texture("texture", texture)
                    .texture("particle", particle);
        }
    }

    private void barStool(String color) {
        ResourceLocation texture = modLoc("block/deco/bar_stool/%s".formatted(color));
        ResourceLocation particle = mcLoc("block/%s_wool".formatted(color));

        String name = "block/deco/bar_stool/%s".formatted(color);
        ResourceLocation parent = modLoc("block/deco/bar_stool/base");
        withExistingParent(name, parent)
                .texture("texture", texture)
                .texture("particle", particle);
    }
}
