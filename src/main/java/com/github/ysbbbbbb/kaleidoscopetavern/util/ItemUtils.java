package com.github.ysbbbbbb.kaleidoscopetavern.util;

import net.minecraft.core.NonNullList;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.apache.commons.lang3.tuple.Pair;
import org.jetbrains.annotations.Nullable;

public class ItemUtils {
    public static void getItemToLivingEntity(LivingEntity entity, ItemStack stack) {
        getItemToLivingEntity(entity, stack, -1);
    }

    public static void getItemToLivingEntity(LivingEntity entity, ItemStack stack, int preferredSlot) {
        if (stack.isEmpty()) {
            return;
        }
        if (entity.getMainHandItem().isEmpty()) {
            RandomSource random = entity.level().random;
            entity.setItemInHand(InteractionHand.MAIN_HAND, stack);
            entity.playSound(SoundEvents.ITEM_PICKUP, 0.2F, ((random.nextFloat() - random.nextFloat()) * 0.7F + 1.0F) * 2.0F);
        } else if (entity instanceof Player player) {
            player.getInventory().placeItemBackInInventory(stack, true);
//            player.getInventory().placeItemBackInInventory(stack, preferredSlot);
        } else {
            // 否则直接在实体所处位置生成物品
            ItemEntity dropItem = entity.spawnAtLocation(stack);
            if (dropItem != null) {
                dropItem.setPickUpDelay(0);
            }
        }
    }

    /** 1.20.1 没有 ItemStack.consume，创造玩家保留物品，自动化和其他实体照常消耗。 */
    public static void consume(ItemStack stack, @Nullable LivingEntity user, int count) {
        if (!(user instanceof Player player && player.getAbilities().instabuild)) {
            stack.shrink(count);
        }
    }

    /** 放入创造玩家物品的副本，避免与手中物品共享 NBT 或修改原堆叠。 */
    public static ItemStack separate(ItemStack stack, @Nullable LivingEntity user, int count) {
        if (user instanceof Player player && player.getAbilities().instabuild) {
            return stack.copyWithCount(Math.min(count, stack.getCount()));
        }
        return stack.split(count);
    }

    public static Pair<Integer, ItemStack> getLastStack(NonNullList<ItemStack> itemList) {
        for (int i = itemList.size(); i > 0; i--) {
            int index = i - 1;
            ItemStack stack = itemList.get(index);
            if (!stack.isEmpty()) {
                return Pair.of(index, stack);
            }
        }
        return Pair.of(0, ItemStack.EMPTY);
    }
}
