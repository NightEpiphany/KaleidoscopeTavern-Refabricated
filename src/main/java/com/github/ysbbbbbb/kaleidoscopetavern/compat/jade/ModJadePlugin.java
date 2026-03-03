package com.github.ysbbbbbb.kaleidoscopetavern.compat.jade;

import com.github.ysbbbbbb.kaleidoscopetavern.KaleidoscopeTavern;
import com.github.ysbbbbbb.kaleidoscopetavern.block.brew.BarrelBlock;
import com.github.ysbbbbbb.kaleidoscopetavern.block.brew.PressingTubBlock;
import com.github.ysbbbbbb.kaleidoscopetavern.blockentity.brew.PressingTubBlockEntity;
import com.github.ysbbbbbb.kaleidoscopetavern.compat.jade.block.BarrelComponentProvider;
import com.github.ysbbbbbb.kaleidoscopetavern.compat.jade.block.PressingTubComponentProvider;
import net.minecraft.resources.ResourceLocation;
import snownee.jade.api.IWailaClientRegistration;
import snownee.jade.api.IWailaCommonRegistration;
import snownee.jade.api.IWailaPlugin;
import snownee.jade.api.WailaPlugin;

@WailaPlugin
public class ModJadePlugin implements IWailaPlugin {
    public static final ResourceLocation PRESSING_TUB = ResourceLocation.fromNamespaceAndPath(KaleidoscopeTavern.MOD_ID, "pressing_tub");
    public static final ResourceLocation BARREL = ResourceLocation.fromNamespaceAndPath(KaleidoscopeTavern.MOD_ID, "barrel");

    @Override
    public void registerClient(IWailaClientRegistration registration) {
        registration.registerBlockComponent(BarrelComponentProvider.INSTANCE, BarrelBlock.class);
        registration.registerBlockComponent(PressingTubComponentProvider.INSTANCE, PressingTubBlock.class);
    }
    @Override
    public void register(IWailaCommonRegistration registration) {
        registration.registerBlockDataProvider(PressingTubComponentProvider.INSTANCE, PressingTubBlockEntity.class);
    }
}
