package com.github.ysbbbbbb.kaleidoscopetavern.mixin.plugin;

import net.fabricmc.loader.api.FabricLoader;
import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

import java.util.List;
import java.util.Set;

public class KaleidoscopeTavernMixinPlugin implements IMixinConfigPlugin {
    private static final String DRAGONLIB_BLOCK_MODEL_COMPAT =
            "com.github.ysbbbbbb.kaleidoscopetavern.mixin.compat.dragonlib.DragonLibBlockModelCompatMixin";


    @Override
    public void onLoad(String mixinPackage) {

    }

    @Override
    public String getRefMapperConfig() {
        return null;
    }

    @Override
    public boolean shouldApplyMixin(String targetClassName, String mixinClassName) {
        if (DRAGONLIB_BLOCK_MODEL_COMPAT.equals(mixinClassName)) {
            FabricLoader loader = FabricLoader.getInstance();
            return loader.isModLoaded("dragonlib");
        }
        return true;
    }

    @Override
    public void acceptTargets(Set<String> myTargets, Set<String> otherTargets) {

    }

    @Override
    public List<String> getMixins() {
        return List.of();
    }

    @Override
    public void preApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {

    }

    @Override
    public void postApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {

    }
}
