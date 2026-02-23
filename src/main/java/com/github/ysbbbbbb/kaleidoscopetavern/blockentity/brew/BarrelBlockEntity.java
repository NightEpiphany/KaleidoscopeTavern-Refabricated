package com.github.ysbbbbbb.kaleidoscopetavern.blockentity.brew;

import com.github.ysbbbbbb.kaleidoscopetavern.blockentity.BaseBlockEntity;
import com.github.ysbbbbbb.kaleidoscopetavern.init.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.items.ItemStackHandler;

public class BarrelBlockEntity extends BaseBlockEntity {
    /**
     * 酒桶的物品槽，大小固定为 4 组
     */
    private final ItemStackHandler items = new ItemStackHandler(4) {
        @Override
        protected void onContentsChanged(int slot) {
            // 物品槽内容改变时，需要强制刷新状态，以便客户端同步
            refresh();
        }
    };

    public BarrelBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlocks.BARREL_BE.get(), pos, state);
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        this.items.deserializeNBT(tag.getCompound("items"));
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.put("items", this.items.serializeNBT());
    }

    @Override
    public AABB getRenderBoundingBox() {
        return new AABB(worldPosition.offset(-2, 0, -2), worldPosition.offset(2, 3, 2));
    }

    public ItemStackHandler getItems() {
        return items;
    }
}
