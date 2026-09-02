package com.github.ysbbbbbb.kaleidoscopetavern.blockentity.brew;

import com.github.ysbbbbbb.kaleidoscopetavern.blockentity.BaseBlockEntity;
import com.github.ysbbbbbb.kaleidoscopetavern.init.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import org.jspecify.annotations.NonNull;

public class PotionBottleBlockEntity extends BaseBlockEntity {
    private static final String ITEM_KEY = "Item";
    private ItemStack potionStack = ItemStack.EMPTY;

    public PotionBottleBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlocks.POTION_BOTTLE_BE, pos, state);
    }

    @Override
    public void loadAdditional(@NonNull ValueInput valueInput) {
        super.loadAdditional(valueInput);
        this.potionStack = valueInput.read(ITEM_KEY, ItemStack.CODEC).orElse(ItemStack.EMPTY);
        this.refreshClientRendering();
    }

    @Override
    protected void saveAdditional(@NonNull ValueOutput valueOutput) {
        super.saveAdditional(valueOutput);
        valueOutput.store(ITEM_KEY, ItemStack.CODEC, this.potionStack);
    }

    private void refreshClientRendering() {
        if (this.level != null && this.level.isClientSide()) {
            BlockState state = this.getBlockState();
            this.level.sendBlockUpdated(this.worldPosition, state, state, Block.UPDATE_IMMEDIATE);
        }
    }

    public ItemStack getPotionStack() {
        return this.potionStack;
    }

    public void setPotionStack(ItemStack stack) {
        this.potionStack = stack.copyWithCount(1);
        this.refresh();
        this.refreshClientRendering();
    }
}