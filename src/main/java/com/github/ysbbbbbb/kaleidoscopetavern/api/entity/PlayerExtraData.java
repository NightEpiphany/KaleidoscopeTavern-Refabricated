package com.github.ysbbbbbb.kaleidoscopetavern.api.entity;

import net.minecraft.nbt.CompoundTag;

public interface PlayerExtraData {
    default CompoundTag kaleidoscope_tavern$getPersistentData() {
        throw new RuntimeException("This should be overridden via mixin...");
    };

    default void kaleidoscope_tavern$setPersistentData(CompoundTag compoundTag) {
        throw new RuntimeException("This should be overridden via mixin...");
    };
}
