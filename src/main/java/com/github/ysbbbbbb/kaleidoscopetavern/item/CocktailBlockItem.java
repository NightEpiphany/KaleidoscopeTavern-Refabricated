package com.github.ysbbbbbb.kaleidoscopetavern.item;

import com.github.ysbbbbbb.kaleidoscopetavern.datamap.data.DrinkEffectData;
import com.github.ysbbbbbb.kaleidoscopetavern.datamap.resources.DrinkEffectDataReloadListener;
import com.github.ysbbbbbb.kaleidoscopetavern.init.ModItems;
import com.google.common.collect.Lists;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.component.Consumable;
import net.minecraft.world.item.component.Consumables;
import net.minecraft.world.item.component.TooltipDisplay;
import java.util.function.Consumer;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class CocktailBlockItem extends GlasswareBlockItem implements IHasContainer {
    public CocktailBlockItem(Block block) {
        super(block);
    }

    public CocktailBlockItem(Block block, Properties properties) {
        super(block, properties.component(DataComponents.CONSUMABLE, Consumables.defaultDrink().build()));
    }

    @Override
    public int getUseDuration(ItemStack stack, LivingEntity entity) {
        return 32;
    }

    @Override
    public @NotNull ItemUseAnimation getUseAnimation(ItemStack stack) {
        return ItemUseAnimation.DRINK;
    }

    @Override
    public @NotNull InteractionResult useOn(UseOnContext context) {
        Player player = context.getPlayer();

        // 只有潜行时才放置
        if (player == null || player.isShiftKeyDown()) {
            return this.place(new BlockPlaceContext(context));
        }

        // 否则尝试喝下去
        Level level = context.getLevel();
        InteractionResult result = this.use(level, player, context.getHand());
        return result == InteractionResult.CONSUME ? InteractionResult.SUCCESS : result;
    }

    @Override
    public @NotNull InteractionResult use(Level level, Player player, InteractionHand hand) {
        return ItemUtils.startUsingInstantly(level, player, hand);
    }

    @Override
    public @NotNull ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity entity) {
        if (entity instanceof ServerPlayer serverPlayer) {
            CriteriaTriggers.CONSUME_ITEM.trigger(serverPlayer, stack);
            serverPlayer.awardStat(Stats.ITEM_USED.get(this));
        }
        this.addDrinkEffect(stack, level, entity);
        if (entity instanceof Player player && !player.isCreative()) {
            stack.shrink(1);
        }
        return returnContainerToEntity(stack, level, entity);
    }

    protected void addDrinkEffect(ItemStack drink, Level level, LivingEntity entity) {
        DrinkEffectData effectData = DrinkEffectDataReloadListener.INSTANCE.get(drink.getItem());
        if (effectData == null) {
            return;
        }
        var effects = effectData.effects();
        if (effects.isEmpty()) {
            return;
        }
        // 鸡尾酒没有酿造等级，直接取第一层效果
        for (DrinkEffectData.Entry entry : effects.getFirst()) {
            if (!level.isClientSide() && level.random.nextFloat() < entry.probability()) {
                MobEffect effect = entry.effect().value();
                int amplifier = entry.amplifier();
                if (effect.isInstantenous()) {
                    // 瞬时效果直接触发，不通过 addEffect
                    effect.applyInstantenousEffect((ServerLevel) level, entity, entity, entity, amplifier, 1.0);
                } else {
                    // json 里的持续时间是秒，但是内部游戏是 tick，需要转化
                    int duration = entry.duration() * 20;
                    MobEffectInstance instance = new MobEffectInstance(entry.effect(), duration, amplifier);
                    entity.addEffect(instance);
                }
            }
        }
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, TooltipDisplay tooltipDisplay, Consumer<Component> consumer, TooltipFlag flag) {
        DrinkEffectData effectData = DrinkEffectDataReloadListener.INSTANCE.get(stack.getItem());
        if (effectData == null || effectData.effects().isEmpty()) {
            return;
        }

        List<MobEffectInstance> effectsShow = Lists.newArrayList();
        for (DrinkEffectData.Entry entry : effectData.effects().getFirst()) {
            if (entry.probability() >= 1.0F) {
                int duration = entry.duration() * 20;
                int amplifier = entry.amplifier();
                effectsShow.add(new MobEffectInstance(entry.effect(), duration, amplifier));
            }
        }

        if (!effectsShow.isEmpty()) {
            consumer.accept(CommonComponents.space());
            PotionContents.addPotionTooltip(effectsShow, consumer, 1.0F, context.tickRate());
        }
    }

    @Override
    public Item getContainerItem() {
        return ModItems.EMPTY_GLASSWARE;
    }
}
