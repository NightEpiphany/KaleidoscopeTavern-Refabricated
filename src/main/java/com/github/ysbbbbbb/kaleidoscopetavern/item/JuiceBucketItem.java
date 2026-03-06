package com.github.ysbbbbbb.kaleidoscopetavern.item;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.Fluid;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.NonNull;

public class JuiceBucketItem extends BucketItem implements IHasContainer {
    public JuiceBucketItem(Fluid fluid, Properties properties) {
        super(fluid, properties
                .stacksTo(16)
                .craftRemainder(Items.BUCKET));
    }

    @Override
    public @NotNull ItemStack finishUsingItem(@NonNull ItemStack stack, Level level, @NonNull LivingEntity entity) {
        if (!level.isClientSide()) {
            entity.removeAllEffects();
        }
        if (entity instanceof ServerPlayer serverPlayer) {
            CriteriaTriggers.CONSUME_ITEM.trigger(serverPlayer, stack);
            serverPlayer.awardStat(Stats.ITEM_USED.get(this));
        }

        if (entity instanceof Player player && !player.isCreative()) {
            stack.shrink(1);
        }

        return returnContainerToEntity(stack, level, entity);
    }

    @Override
    public int getUseDuration(@NonNull ItemStack stack, @NonNull LivingEntity entity) {
        return 32;
    }

    @Override
    public @NonNull ItemUseAnimation getUseAnimation(@NonNull ItemStack stack) {
        return ItemUseAnimation.DRINK;
    }

    @Override
    public @NonNull InteractionResult use(@NonNull Level level, Player player, @NonNull InteractionHand hand) {
        if (player.isShiftKeyDown())
            return super.use(level, player, hand);
        return ItemUtils.startUsingInstantly(level, player, hand);
    }

    @Override
    public Item getContainerItem() {
        return Items.BUCKET;
    }
}
