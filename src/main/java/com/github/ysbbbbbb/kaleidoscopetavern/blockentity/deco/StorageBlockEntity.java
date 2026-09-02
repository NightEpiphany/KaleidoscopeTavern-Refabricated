package com.github.ysbbbbbb.kaleidoscopetavern.blockentity.deco;

import com.github.ysbbbbbb.kaleidoscopetavern.blockentity.BaseBlockEntity;
import com.github.ysbbbbbb.kaleidoscopetavern.util.neo.ItemStackHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import org.jspecify.annotations.NonNull;

public abstract class StorageBlockEntity extends BaseBlockEntity {
    private static final String ITEMS = "items";
    private final ItemStackHandler items;

    protected StorageBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state, int size) {
        super(type, pos, state);
        this.items = new ItemStackHandler(size) {
            @Override
            public int getSlotLimit(int slot) {
                return 1;
            }
        };
    }

    @Override
    protected void loadAdditional(@NonNull ValueInput valueInput) {
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

    public boolean hasAnyItem() {
        for (int i = 0; i < this.items.getSlots(); i++) {
            if (!this.items.getStackInSlot(i).isEmpty()) {
                return true;
            }
        }
        return false;
    }
}