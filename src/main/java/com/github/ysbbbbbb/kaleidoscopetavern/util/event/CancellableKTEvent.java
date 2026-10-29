package com.github.ysbbbbbb.kaleidoscopetavern.util.event;

import org.jetbrains.annotations.ApiStatus;

//森罗酒馆相关的各类可取消事件专用的类
public abstract class CancellableKTEvent implements IKTEvent {

    private boolean canceled;

    public final void setCanceled(boolean canceled) {
        this.canceled = canceled;
    }

    @ApiStatus.NonExtendable
    public boolean isCanceled() {
        return this.canceled;
    }
}
