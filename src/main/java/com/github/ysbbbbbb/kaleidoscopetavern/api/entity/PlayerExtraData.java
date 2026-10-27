package com.github.ysbbbbbb.kaleidoscopetavern.api.entity;

public interface PlayerExtraData {
    default int kaleidoscopeTavern$getPersistentData() {
        throw new RuntimeException("This should be overridden via mixin...");
    };

    default void kaleidoscopeTavern$setPersistentData(int compoundTag) {
        throw new RuntimeException("This should be overridden via mixin...");
    };
}
