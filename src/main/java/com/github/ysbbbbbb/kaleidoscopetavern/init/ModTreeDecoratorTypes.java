package com.github.ysbbbbbb.kaleidoscopetavern.init;

import com.github.ysbbbbbb.kaleidoscopetavern.worldgen.WildGrapevineDecorator;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;

public interface ModTreeDecoratorTypes {

    TreeDecoratorType<WildGrapevineDecorator> WILD_GRAPEVINE = Registry.register(BuiltInRegistries.TREE_DECORATOR_TYPE, "wild_grapevine", new TreeDecoratorType<>(WildGrapevineDecorator.CODEC));
}
