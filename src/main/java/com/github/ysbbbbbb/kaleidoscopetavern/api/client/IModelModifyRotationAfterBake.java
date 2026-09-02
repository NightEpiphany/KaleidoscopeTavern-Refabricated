package com.github.ysbbbbbb.kaleidoscopetavern.api.client;

import net.minecraft.world.level.block.state.properties.Property;

public interface IModelModifyRotationAfterBake<T extends Property<Integer>> {
    T getRotationProperty();
}
