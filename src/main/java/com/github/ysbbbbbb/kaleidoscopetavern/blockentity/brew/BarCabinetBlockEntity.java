package com.github.ysbbbbbb.kaleidoscopetavern.blockentity.brew;

import com.github.ysbbbbbb.kaleidoscopetavern.blockentity.BaseBlockEntity;
import com.github.ysbbbbbb.kaleidoscopetavern.init.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import org.jspecify.annotations.NonNull;

public class BarCabinetBlockEntity extends BaseBlockEntity {
    /**
     * 酒柜左侧酒瓶。如果仅显示单个酒瓶的酒，则仅渲染左侧酒瓶
     */
    private ItemStack leftItem = ItemStack.EMPTY;
    /**
     * 酒柜右侧酒瓶。如果仅显示单个酒瓶的酒，则不渲染，也不能交互右侧酒瓶
     */
    private ItemStack rightItem = ItemStack.EMPTY;
    /**
     * 是否是异形酒瓶，异形酒瓶只能显示单个酒瓶的酒，并且只能交互左侧酒瓶
     */
    private boolean isSingle = false;

    public BarCabinetBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlocks.BAR_CABINET_BE, pos, state);
    }

    @Override
    protected void saveAdditional(@NonNull ValueOutput valueOutput) {
        super.saveAdditional(valueOutput);
        if (!this.leftItem.isEmpty()) {
            valueOutput.store("left_item", ItemStack.CODEC, this.leftItem);
        }

        if (!this.rightItem.isEmpty()) {
            valueOutput.store("right_item", ItemStack.CODEC, this.rightItem);
        }

        valueOutput.putBoolean("is_single", this.isSingle);
    }

    @Override
    protected void loadAdditional(@NonNull ValueInput valueInput) {
        super.loadAdditional(valueInput);

        valueInput.read("left_item", ItemStack.CODEC).ifPresentOrElse(itemStack -> this.leftItem = itemStack, () -> this.leftItem = ItemStack.EMPTY);
        valueInput.read("right_item", ItemStack.CODEC).ifPresentOrElse(itemStack -> this.rightItem = itemStack, () -> this.rightItem = ItemStack.EMPTY);
        this.isSingle = valueInput.getBooleanOr("is_single", false);
    }

    public ItemStack getLeftItem() {
        return leftItem;
    }

    public void setLeftItem(ItemStack leftItem) {
        this.leftItem = leftItem;
    }

    public ItemStack getRightItem() {
        return rightItem;
    }

    public void setRightItem(ItemStack rightItem) {
        this.rightItem = rightItem;
    }

    public void setSingle(boolean single) {
        isSingle = single;
    }

    public boolean isSingle() {
        return isSingle;
    }
}
