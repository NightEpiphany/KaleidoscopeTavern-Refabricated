package com.github.ysbbbbbb.kaleidoscopetavern.datagen.model;

import com.github.ysbbbbbb.kaleidoscopetavern.KaleidoscopeTavern;
import com.github.ysbbbbbb.kaleidoscopetavern.util.ColorUtils;
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
        for (String color : ColorUtils.COLORS) {
            sofa(color);
            barStool(color);
        }
    }

    private void sofa(String color) {
        String name = "item/%s_sofa".formatted(color);
        ResourceLocation parent = modLoc("block/deco/sofa/%s/single".formatted(color));
        withExistingParent(name, parent);
    }

    private void barStool(String color) {
        String name = "item/%s_bar_stool".formatted(color);
        ResourceLocation parent = modLoc("block/deco/bar_stool/%s".formatted(color));
        withExistingParent(name, parent);
    }
}
