package com.github.ysbbbbbb.kaleidoscopetavern.compat.jade;

import com.github.ysbbbbbb.kaleidoscopetavern.block.brew.BarrelBlock;
import com.github.ysbbbbbb.kaleidoscopetavern.compat.jade.block.BarrelComponentProvider;
import snownee.jade.api.IWailaClientRegistration;
import snownee.jade.api.IWailaPlugin;
import snownee.jade.api.WailaPlugin;

@WailaPlugin
public class ModPlugin implements IWailaPlugin {

    @Override
    public void registerClient(IWailaClientRegistration registration) {
        registration.registerBlockComponent(BarrelComponentProvider.INSTANCE, BarrelBlock.class);
    }
}
