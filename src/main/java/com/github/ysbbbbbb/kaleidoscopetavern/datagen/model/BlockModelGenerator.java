package com.github.ysbbbbbb.kaleidoscopetavern.datagen.model;

import com.github.ysbbbbbb.kaleidoscopetavern.KaleidoscopeTavern;
import com.github.ysbbbbbb.kaleidoscopetavern.block.properties.ConnectionType;
import com.github.ysbbbbbb.kaleidoscopetavern.init.ModBlocks;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.BlockModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;

public class BlockModelGenerator extends BlockModelProvider {
    public BlockModelGenerator(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, KaleidoscopeTavern.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        // 沙发
        sofa(ModBlocks.WHITE_SOFA, "white");
        sofa(ModBlocks.LIGHT_GRAY_SOFA, "light_gray");
        sofa(ModBlocks.GRAY_SOFA, "gray");
        sofa(ModBlocks.BLACK_SOFA, "black");
        sofa(ModBlocks.BROWN_SOFA, "brown");
        sofa(ModBlocks.RED_SOFA, "red");
        sofa(ModBlocks.ORANGE_SOFA, "orange");
        sofa(ModBlocks.YELLOW_SOFA, "yellow");
        sofa(ModBlocks.LIME_SOFA, "lime");
        sofa(ModBlocks.GREEN_SOFA, "green");
        sofa(ModBlocks.CYAN_SOFA, "cyan");
        sofa(ModBlocks.LIGHT_BLUE_SOFA, "light_blue");
        sofa(ModBlocks.BLUE_SOFA, "blue");
        sofa(ModBlocks.PURPLE_SOFA, "purple");
        sofa(ModBlocks.MAGENTA_SOFA, "magenta");
        sofa(ModBlocks.PINK_SOFA, "pink");
    }

    private void sofa(RegistryObject<Block> block, String color) {
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
}
