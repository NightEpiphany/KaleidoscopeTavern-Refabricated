package com.github.ysbbbbbb.kaleidoscopetavern.block.properties;

import net.minecraft.util.StringRepresentable;
import org.jetbrains.annotations.NotNull;

import java.util.Locale;

public enum ConnectionType implements StringRepresentable {
    /**
     * 沙发或者吧台的链接状态
     */
    SINGLE,
    LEFT,
    RIGHT,
    MIDDLE,
    LEFT_CORNER,
    RIGHT_CORNER;

    @Override
    public @NotNull String getSerializedName() {
        return this.name().toLowerCase(Locale.ENGLISH);
    }

    @Override
    public String toString() {
        return this.getSerializedName();
    }
}
