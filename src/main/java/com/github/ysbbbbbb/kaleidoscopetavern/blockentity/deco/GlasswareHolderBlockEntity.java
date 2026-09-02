package com.github.ysbbbbbb.kaleidoscopetavern.blockentity.deco;

import com.github.ysbbbbbb.kaleidoscopetavern.blockentity.BaseBlockEntity;
import com.github.ysbbbbbb.kaleidoscopetavern.init.ModBlocks;
import com.github.ysbbbbbb.kaleidoscopetavern.util.neo.ItemStackHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.phys.AABB;
import org.jspecify.annotations.NonNull;

public class GlasswareHolderBlockEntity extends BaseBlockEntity {
    private static final String ITEMS = "items";

    private final ItemStackHandler items = new ItemStackHandler(4) {
        @Override
        public int getSlotLimit(int slot) {
            return 1;
        }
    };

    public GlasswareHolderBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlocks.GLASSWARE_HOLDER_BE, pos, state);
    }

    @Override
    public void loadAdditional(@NonNull ValueInput valueInput) {
        super.loadAdditional(valueInput);
        this.items.deserializeNBT(valueInput, ITEMS, ITEMS + "_size");
    }

    @Override
    protected void saveAdditional(@NonNull ValueOutput valueOutput) {
        super.saveAdditional(valueOutput);
        this.items.serialize(valueOutput, ITEMS, ITEMS + "_size");
    }

    public ItemStackHandler getItems() {
        return this.items;
    }

    public AABB getRenderBoundingBox() {
        BlockPos pos = this.getBlockPos();
        return new AABB(pos);
    }
}