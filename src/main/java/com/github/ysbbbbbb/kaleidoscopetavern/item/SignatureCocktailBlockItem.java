package com.github.ysbbbbbb.kaleidoscopetavern.item;

import com.github.ysbbbbbb.kaleidoscopetavern.datamap.data.DrinkEffectData;
import com.github.ysbbbbbb.kaleidoscopetavern.init.ModDataComponents;
import com.google.common.collect.Lists;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.CustomModelData;
import net.minecraft.world.item.component.TooltipDisplay;
import java.util.function.Consumer;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;

import java.util.ArrayList;
import java.util.List;

public class SignatureCocktailBlockItem extends CocktailBlockItem {
    public SignatureCocktailBlockItem(Block block) {
        super(block);
    }

    public SignatureCocktailBlockItem(Block block, Properties properties) {
        super(block, properties);
    }

    public static boolean hasEffects(ItemStack stack) {
        return stack.has(ModDataComponents.SIGNATURE_COCKTAIL_EFFECTS);
    }

    public static List<DrinkEffectData.Entry> getEffects(ItemStack stack) {
        return stack.getOrDefault(ModDataComponents.SIGNATURE_COCKTAIL_EFFECTS, List.of());
    }

    public static void setEffects(ItemStack stack, List<DrinkEffectData.Entry> effects) {
        stack.set(ModDataComponents.SIGNATURE_COCKTAIL_EFFECTS, List.copyOf(effects));
    }

    public static int getColor(ItemStack stack) {
        return stack.getOrDefault(ModDataComponents.SIGNATURE_COCKTAIL_COLOR, 0x5555ff);
    }

    public static void setColor(ItemStack stack, int color) {
        stack.set(ModDataComponents.SIGNATURE_COCKTAIL_COLOR, color);
        // 同步写入 CUSTOM_MODEL_DATA，供 items/ 物品模型定义中的 tint 使用
        CustomModelData customModelData = stack.getOrDefault(DataComponents.CUSTOM_MODEL_DATA, CustomModelData.EMPTY);
        List<Integer> colors = new ArrayList<>(customModelData.colors());
        if (colors.isEmpty()) {
            colors.add(0xFFFFFF);
        }
        colors.set(0, color & 0xFFFFFF);
        stack.set(DataComponents.CUSTOM_MODEL_DATA, new CustomModelData(
                customModelData.floats(),
                customModelData.flags(),
                customModelData.strings(),
                List.copyOf(colors)
        ));
    }

    @Override
    protected void addDrinkEffect(ItemStack drink, Level level, LivingEntity entity) {
        List<DrinkEffectData.Entry> effects = getEffects(drink);
        for (DrinkEffectData.Entry entry : effects) {
            if (!level.isClientSide() && level.random.nextFloat() < entry.probability()) {
                MobEffect effect = entry.effect().value();
                int amplifier = entry.amplifier();
                if (effect.isInstantenous()) {
                    effect.applyInstantenousEffect((ServerLevel) level, entity, entity, entity, amplifier, 1.0);
                } else {
                    int duration = entry.duration() * 20;
                    MobEffectInstance instance = new MobEffectInstance(entry.effect(), duration, amplifier);
                    entity.addEffect(instance);
                }
            }
        }
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, TooltipDisplay tooltipDisplay, Consumer<Component> consumer, TooltipFlag flag) {
        List<DrinkEffectData.Entry> effects = getEffects(stack);

        List<MobEffectInstance> effectsShow = Lists.newArrayList();
        for (DrinkEffectData.Entry entry : effects) {
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
}
