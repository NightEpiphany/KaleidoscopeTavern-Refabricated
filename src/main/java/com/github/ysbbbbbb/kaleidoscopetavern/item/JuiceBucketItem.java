package com.github.ysbbbbbb.kaleidoscopetavern.item;

import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.Fluid;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class JuiceBucketItem extends BucketItem implements IHasContainer {
    public JuiceBucketItem(Fluid fluid) {
        super(fluid, new Properties()
                .stacksTo(16)
                .craftRemainder(Items.BUCKET));
    }

    @Override
    public @NotNull ItemStack finishUsingItem(@NotNull ItemStack stack, Level level, @NotNull LivingEntity entity) {
        if (!level.isClientSide) {
            entity.removeAllEffects();
        }
        if (entity instanceof ServerPlayer serverPlayer) {
            CriteriaTriggers.CONSUME_ITEM.trigger(serverPlayer, stack);
            serverPlayer.awardStat(Stats.ITEM_USED.get(this));
        }

        com.github.ysbbbbbb.kaleidoscopetavern.util.ItemUtils.consume(stack, entity, 1);

        return returnContainerToEntity(stack, level, entity);
    }


    @Override
    public int getUseDuration(@NotNull ItemStack stack) {
        return 32;
    }

    @Override
    public @NotNull UseAnim getUseAnimation(@NotNull ItemStack stack) {
        return UseAnim.DRINK;
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level level, Player player, @NotNull InteractionHand hand) {
        if (player.isShiftKeyDown())
            return super.use(level, player, hand);
        return ItemUtils.startUsingInstantly(level, player, hand);
    }

    @Override
    public void appendHoverText(@NotNull ItemStack stack, @Nullable Level pLevel, List<Component> components, @NotNull TooltipFlag pIsAdvanced) {
        String tooltipKey = Util.makeDescriptionId("tooltip", BuiltInRegistries.ITEM.getKey(this));
        components.add(Component.translatable(tooltipKey).withStyle(ChatFormatting.GRAY));
    }

    @Override
    public Item getContainerItem() {
        return Items.BUCKET;
    }
}
