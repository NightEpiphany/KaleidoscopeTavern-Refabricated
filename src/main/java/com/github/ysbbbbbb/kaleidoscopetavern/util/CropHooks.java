package com.github.ysbbbbbb.kaleidoscopetavern.util;

import com.github.ysbbbbbb.kaleidoscopetavern.api.event.CropStateChangeEvent;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;

public interface CropHooks {
    static boolean onCropsGrowPre(LevelAccessor level, BlockPos pos, BlockState state, boolean shouldGrow) {
        if (!shouldGrow) {
            return false;
        }
        CropStateChangeEvent.Pre event = new CropStateChangeEvent.Pre(level, pos, state);
        event.post();
        return !event.isCanceled();
    }

    static void onCropsGrowPost(Level level, BlockPos pos, BlockState originalState) {
        BlockState currentState = level.getBlockState(pos);
        CropStateChangeEvent.Post event = new CropStateChangeEvent.Post(level, pos, originalState, currentState);
        event.post();
    }
}
