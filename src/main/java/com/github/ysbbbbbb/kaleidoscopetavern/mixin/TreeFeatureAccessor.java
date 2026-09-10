package com.github.ysbbbbbb.kaleidoscopetavern.mixin;

import net.minecraft.world.level.levelgen.feature.TreeFeature;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.List;

@Mixin(TreeFeature.class)
public interface TreeFeatureAccessor {
    @Mutable
    @Accessor("decorators")
    void kaleidoscopeTavern$setDecorators(List<TreeDecorator> decorators);
}
