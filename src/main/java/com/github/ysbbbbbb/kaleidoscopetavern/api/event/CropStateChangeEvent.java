package com.github.ysbbbbbb.kaleidoscopetavern.api.event;

import com.github.ysbbbbbb.kaleidoscopetavern.util.event.CancellableEvent;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;

public abstract class CropStateChangeEvent extends CancellableEvent {
    private final LevelAccessor level;
    private final BlockPos pos;
    private final BlockState state;

    protected CropStateChangeEvent(LevelAccessor level, BlockPos pos, BlockState state) {
        this.level = level;
        this.pos = pos;
        this.state = state;
    }

    public BlockState getState() {
        return state;
    }

    public BlockPos getPos() {
        return pos;
    }

    public LevelAccessor getLevel() {
        return level;
    }

    @Override
    public void post() {
        CALLBACK.invoker().post(this);
    }

    public static class Pre extends CropStateChangeEvent {
        public Pre(LevelAccessor level, BlockPos pos, BlockState state) {
            super(level, pos, state);
        }
    }

    public static class Post extends CropStateChangeEvent {
        private final BlockState originalState;
        public Post(Level level, BlockPos pos, BlockState original, BlockState state) {
            super(level, pos, state);
            originalState = original;
        }

        public BlockState getOriginalState() {
            return originalState;
        }
    }
}
