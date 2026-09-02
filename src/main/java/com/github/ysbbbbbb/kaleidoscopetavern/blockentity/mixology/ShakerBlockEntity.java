package com.github.ysbbbbbb.kaleidoscopetavern.blockentity.mixology;

import com.github.ysbbbbbb.kaleidoscopetavern.api.blockentity.IShaker;
import com.github.ysbbbbbb.kaleidoscopetavern.blockentity.BaseBlockEntity;
import com.github.ysbbbbbb.kaleidoscopetavern.init.ModBlocks;
import com.github.ysbbbbbb.kaleidoscopetavern.item.BottleBlockItem;
import com.github.ysbbbbbb.kaleidoscopetavern.item.IHasContainer;
import com.github.ysbbbbbb.kaleidoscopetavern.util.ItemUtils;
import com.github.ysbbbbbb.kaleidoscopetavern.util.neo.ItemStackHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import org.jspecify.annotations.NonNull;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.PotionItem;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class ShakerBlockEntity extends BaseBlockEntity implements IShaker {
    private final ItemStackHandler storage = new ItemStackHandler(3) {
        @Override
        public int getSlotLimit(int slot) {
            return 1;
        }
    };

    private ItemStack result = ItemStack.EMPTY;
    public int lastPutTick = 0;

    public ShakerBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlocks.SHAKER_BE, pos, state);
    }

    @Override
    public boolean addIngredient(ItemStack stack, @Nullable LivingEntity user) {
        // 先看看满了么
        if (!hasEmptySlots()) {
            return false;
        }

        // 如果是酒，需要首先检查品质，必须是优质以上
        if (!BottleBlockItem.isValidForShaker(stack)) {
            if (user instanceof Player player && !player.level().isClientSide()) {
                player.displayClientMessage(Component.translatable("message.kaleidoscope_tavern.shaker.brew_level_too_low"), false);
            }
            return false;
        }

        ItemStack copy = stack.copyWithCount(1);
        ItemUtils.insertItemStacked(storage, copy, false);
        this.refresh();

        // 如果是容器类物品
        if (stack.getItem() instanceof IHasContainer hasContainer && user != null && level != null) {
            // 返还容器
            ItemStack carried = hasContainer.getContainerItem().getDefaultInstance();
            if (user instanceof Player player) {
                ItemUtils.giveItemToPlayer(player, carried);
            } else {
                ItemEntity itemEntity = new ItemEntity(level, user.getX(), user.getY(), user.getZ(), carried);
                level.addFreshEntity(itemEntity);
            }
            level.playSound(null, worldPosition, SoundEvents.BOTTLE_EMPTY,
                    SoundSource.BLOCKS, 0.75F, 1.0F
            );
        } else if (stack.getItem() instanceof PotionItem && user != null && level != null) {
            // 药水倒入后返还玻璃瓶
            ItemStack bottle = new ItemStack(Items.GLASS_BOTTLE);
            if (user instanceof Player player) {
                ItemUtils.giveItemToPlayer(player, bottle);
            } else {
                ItemEntity itemEntity = new ItemEntity(level, user.getX(), user.getY(), user.getZ(), bottle);
                level.addFreshEntity(itemEntity);
            }
            level.playSound(null, worldPosition, SoundEvents.BOTTLE_EMPTY,
                    SoundSource.BLOCKS, 0.75F, 1.0F
            );
        } else if (level != null) {
            level.playSound(null, worldPosition, SoundEvents.ITEM_FRAME_ADD_ITEM,
                    SoundSource.BLOCKS, 0.75F, 1.0F
            );
        }

        stack.shrink(1);

        // 记录加酒时刻并同步给客户端，用于驱动杯盖动画
        if (level != null) {
            this.lastPutTick = (int) level.getGameTime();
        }
        this.refresh();
        // 粒子
        if (this.level instanceof ServerLevel serverLevel) {
            serverLevel.sendParticles(
                    net.minecraft.core.particles.ParticleTypes.BUBBLE_POP,
                    worldPosition.getX() + 0.5, worldPosition.getY() + 0.75, worldPosition.getZ() + 0.5,
                    8, 0.2, 0.3, 0.2, 0
            );
        }

        return true;
    }

    private boolean hasEmptySlots() {
        for (int i = 0; i < storage.getSlots(); i++) {
            ItemStack slot = storage.getStackInSlot(i);
            if (slot.isEmpty()) {
                return true;
            }
        }
        return false;
    }

    @Override
    protected void saveAdditional(@NonNull ValueOutput valueOutput) {
        super.saveAdditional(valueOutput);
        this.storage.serialize(valueOutput, "Storage", "StorageSize");
        valueOutput.store("result", ItemStack.OPTIONAL_CODEC, this.result);
        valueOutput.putInt("last_put_tick", this.lastPutTick);
    }

    @Override
    public void loadAdditional(@NonNull ValueInput valueInput) {
        super.loadAdditional(valueInput);
        this.storage.deserializeNBT(valueInput, "Storage", "StorageSize");
        this.result = valueInput.read("result", ItemStack.OPTIONAL_CODEC).orElse(ItemStack.EMPTY);
        this.lastPutTick = valueInput.getIntOr("last_put_tick", 0);
    }

    public ItemStackHandler getStorage() {
        return storage;
    }

    public void setStorage(ItemStackHandler storage) {
        for (int i = 0; i < storage.getSlots(); i++) {
            ItemStack slot = storage.getStackInSlot(i);
            this.storage.setStackInSlot(i, slot);
        }
    }

    public ItemStack getResult() {
        return result;
    }

    public void setResult(ItemStack result) {
        this.result = result;
    }
}
