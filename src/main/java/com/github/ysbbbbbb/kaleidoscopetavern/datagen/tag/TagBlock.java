package com.github.ysbbbbbb.kaleidoscopetavern.datagen.tag;

import com.github.ysbbbbbb.kaleidoscopetavern.KaleidoscopeTavern;
import com.github.ysbbbbbb.kaleidoscopetavern.init.ModBlocks;
import com.github.ysbbbbbb.kaleidoscopetavern.init.tag.TagMod;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class TagBlock extends BlockTagsProvider {
    public TagBlock(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, KaleidoscopeTavern.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        this.tag(TagMod.SOFA).add(
                ModBlocks.WHITE_SOFA.get(),
                ModBlocks.LIGHT_GRAY_SOFA.get(),
                ModBlocks.GRAY_SOFA.get(),
                ModBlocks.BLACK_SOFA.get(),
                ModBlocks.BROWN_SOFA.get(),
                ModBlocks.RED_SOFA.get(),
                ModBlocks.ORANGE_SOFA.get(),
                ModBlocks.YELLOW_SOFA.get(),
                ModBlocks.LIME_SOFA.get(),
                ModBlocks.GREEN_SOFA.get(),
                ModBlocks.CYAN_SOFA.get(),
                ModBlocks.LIGHT_BLUE_SOFA.get(),
                ModBlocks.BLUE_SOFA.get(),
                ModBlocks.PURPLE_SOFA.get(),
                ModBlocks.MAGENTA_SOFA.get(),
                ModBlocks.PINK_SOFA.get()
        );

        this.tag(TagMod.SITTABLE).addTag(TagMod.SOFA);
    }
}
