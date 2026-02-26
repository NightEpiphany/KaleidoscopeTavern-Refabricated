package com.github.ysbbbbbb.kaleidoscopetavern.blockentity.deco;

import com.github.ysbbbbbb.kaleidoscopetavern.init.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class SandwichBoardBlockEntity extends TextBlockEntity {
    public SandwichBoardBlockEntity(BlockPos pos, BlockState blockState) {
        super(ModBlocks.SANDWICH_BOARD_BE, pos, blockState);
    }

    @Override
    public int getMaxTextLength() {
        return 320;
    }
}
