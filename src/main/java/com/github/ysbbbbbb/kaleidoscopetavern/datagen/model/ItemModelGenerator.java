package com.github.ysbbbbbb.kaleidoscopetavern.datagen.model;

import com.github.ysbbbbbb.kaleidoscopetavern.KaleidoscopeTavern;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ItemModelGenerator extends ItemModelProvider {
    public ItemModelGenerator(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, KaleidoscopeTavern.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        sofa("white");
        sofa("light_gray");
        sofa("gray");
        sofa("black");
        sofa("brown");
        sofa("red");
        sofa("orange");
        sofa("yellow");
        sofa("lime");
        sofa("green");
        sofa("cyan");
        sofa("light_blue");
        sofa("blue");
        sofa("purple");
        sofa("magenta");
        sofa("pink");
    }

    private void sofa(String color) {
        String name = "item/%s_sofa".formatted(color);
        ResourceLocation parent = modLoc("block/deco/sofa/%s/single".formatted(color));
        withExistingParent(name, parent);
    }
}
