package com.github.ysbbbbbb.kaleidoscopetavern.compact.jade;

import com.github.ysbbbbbb.kaleidoscopetavern.KaleidoscopeTavern;
import com.github.ysbbbbbb.kaleidoscopetavern.block.brew.BarrelBlock;
import com.github.ysbbbbbb.kaleidoscopetavern.block.brew.PressingTubBlock;
import com.github.ysbbbbbb.kaleidoscopetavern.blockentity.brew.BarrelBlockEntity;
import com.github.ysbbbbbb.kaleidoscopetavern.blockentity.brew.PressingTubBlockEntity;
import com.github.ysbbbbbb.kaleidoscopetavern.compact.jade.components.BarrelComponentProvider;
import com.github.ysbbbbbb.kaleidoscopetavern.compact.jade.components.PressingTubComponentProvider;
import net.minecraft.resources.ResourceLocation;
import snownee.jade.api.IWailaClientRegistration;
import snownee.jade.api.IWailaCommonRegistration;
import snownee.jade.api.IWailaPlugin;
import snownee.jade.api.WailaPlugin;

@WailaPlugin
public class ModJadePlugin implements IWailaPlugin {
    public static final ResourceLocation PRESSING_TUB = new ResourceLocation(KaleidoscopeTavern.MOD_ID, "pressing_tub");
    public static final ResourceLocation BARREL = new ResourceLocation(KaleidoscopeTavern.MOD_ID, "barrel");

    @Override
    public void register(IWailaCommonRegistration registration) {
        registration.registerBlockDataProvider(PressingTubComponentProvider.INSTANCE, PressingTubBlockEntity.class);
        registration.registerBlockDataProvider(BarrelComponentProvider.INSTANCE, BarrelBlockEntity.class);
    }

    @Override
    public void registerClient(IWailaClientRegistration registration) {
        registration.registerBlockComponent(PressingTubComponentProvider.INSTANCE, PressingTubBlock.class);
        registration.registerBlockComponent(BarrelComponentProvider.INSTANCE, BarrelBlock.class);
    }
}
