package com.github.ysbbbbbb.kaleidoscopetavern.item;

import com.github.ysbbbbbb.kaleidoscopetavern.api.event.DrinkCocktailEvent;
import com.github.ysbbbbbb.kaleidoscopetavern.datamap.data.DrinkEffectData;
import com.github.ysbbbbbb.kaleidoscopetavern.datamap.resources.DrinkEffectDataReloadListener;
import com.github.ysbbbbbb.kaleidoscopetavern.init.ModItems;
import com.google.common.collect.Lists;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.alchemy.PotionContents;
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
        super(block, properties);
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
    public @NotNull InteractionResult useOn(UseOnContext context) {
        Player player = context.getPlayer();

        // 只有潜行时才放置
        if (player == null || player.isShiftKeyDown()) {
            return this.place(new BlockPlaceContext(context));
        }

        // 否则尝试喝下去
        Level level = context.getLevel();
        InteractionResult result = this.use(level, player, context.getHand()).getResult();
        return result == InteractionResult.CONSUME ? InteractionResult.CONSUME_PARTIAL : result;
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        DrinkCocktailEvent.Pre event = new DrinkCocktailEvent.Pre(player.getItemInHand(hand), level, player, hand);
        event.post();
        if (event.isCanceled()) {
            return InteractionResultHolder.fail(player.getItemInHand(hand));
        }
        return ItemUtils.startUsingInstantly(level, player, hand);
    }

    @Override
    public @NotNull ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity entity) {
        DrinkCocktailEvent.Post event = new DrinkCocktailEvent.Post(stack, level, entity);
        event.post();
        if (event.isCanceled()) {
            return stack;
        }

        if (entity instanceof ServerPlayer serverPlayer) {
            CriteriaTriggers.CONSUME_ITEM.trigger(serverPlayer, stack);
            serverPlayer.awardStat(Stats.ITEM_USED.get(this));
        }
        this.addDrinkEffect(stack, level, entity);
        stack.consume(1, entity);
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
            if (!level.isClientSide && level.random.nextFloat() < entry.probability()) {
                MobEffect effect = entry.effect().value();
                int amplifier = entry.amplifier();
                if (effect.isInstantenous()) {
                    // 瞬时效果直接触发，不通过 addEffect
                    effect.applyInstantenousEffect(entity, entity, entity, amplifier, 1.0);
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
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
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
            tooltip.add(CommonComponents.space());
            PotionContents.addPotionTooltip(effectsShow, tooltip::add, 1.0F, context.tickRate());
        }
    }

    @Override
    public Item getContainerItem() {
        return ModItems.EMPTY_GLASSWARE;
    }
}
