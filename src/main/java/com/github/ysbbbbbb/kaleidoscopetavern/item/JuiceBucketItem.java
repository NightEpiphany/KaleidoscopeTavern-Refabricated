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

import java.util.List;

public class JuiceBucketItem extends BucketItem implements IHasContainer {
    public JuiceBucketItem(Fluid fluid) {
        super(fluid, new Properties()
                .stacksTo(16)
                .craftRemainder(Items.BUCKET));
    }

    @Override
    public @NotNull ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity entity) {
        if (!level.isClientSide) {
            entity.removeAllEffects();
        }
        if (entity instanceof ServerPlayer serverPlayer) {
            CriteriaTriggers.CONSUME_ITEM.trigger(serverPlayer, stack);
            serverPlayer.awardStat(Stats.ITEM_USED.get(this));
        }

        stack.consume(1, entity);

        return returnContainerToEntity(stack, level, entity);
    }

    @Override
    public int getUseDuration(ItemStack stack, LivingEntity entity) {
        return 32;
    }

    @Override
    public @NotNull UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.DRINK;
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        if (player.isShiftKeyDown())
            return super.use(level, player, hand);
        return ItemUtils.startUsingInstantly(level, player, hand);
    }

    @Override
    public Item getContainerItem() {
        return Items.BUCKET;
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> components, TooltipFlag isAdvanced) {
        String tooltipKey = Util.makeDescriptionId("tooltip", BuiltInRegistries.ITEM.getKey(this));
        components.add(Component.translatable(tooltipKey).withStyle(ChatFormatting.GRAY));
    }
}
