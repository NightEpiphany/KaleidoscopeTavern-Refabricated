package com.github.ysbbbbbb.kaleidoscopetavern.api.event;

import com.github.ysbbbbbb.kaleidoscopetavern.util.event.IKTEvent;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class BarrelTapEvent implements IKTEvent {
    private final Level level;
    private final BlockPos barrelPos;
    private final BlockPos tapPos;
    private final BlockState barrelState;
    private boolean freeConsumption;

    public BarrelTapEvent(Level level, BlockPos barrelPos, BlockPos tapPos, BlockState barrelState) {
        this.level = level;
        this.barrelPos = barrelPos;
        this.tapPos = tapPos;
        this.barrelState = barrelState;
    }

    public Level level() {
        return level;
    }

    public BlockPos barrelPos() {
        return barrelPos;
    }

    public BlockPos tapPos() {
        return tapPos;
    }

    public BlockState barrelState() {
        return barrelState;
    }

    public boolean isFreeConsumption() {
        return freeConsumption;
    }

    public void markFreeConsumption() {
        freeConsumption = true;
    }
}
