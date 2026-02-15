package com.github.ysbbbbbb.kaleidoscopetavern.datagen.model;

import com.github.ysbbbbbb.kaleidoscopetavern.KaleidoscopeTavern;
import com.github.ysbbbbbb.kaleidoscopetavern.block.deco.SofaBlock;
import com.github.ysbbbbbb.kaleidoscopetavern.block.properties.ConnectionType;
import com.github.ysbbbbbb.kaleidoscopetavern.init.ModBlocks;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;

public class BlockStateGenerator extends BlockStateProvider {
    public BlockStateGenerator(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, KaleidoscopeTavern.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
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
        horizontalBlock(block.get(), blockState -> {
            ConnectionType connection = blockState.getValue(SofaBlock.CONNECTION);
            String type = connection.getSerializedName();
            ResourceLocation file = modLoc("block/deco/sofa/%s/%s".formatted(color, type));
            return new ModelFile.UncheckedModelFile(file);
        });
    }
}
