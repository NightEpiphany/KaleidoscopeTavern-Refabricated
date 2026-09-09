package com.github.ysbbbbbb.kaleidoscopetavern.item;

import com.github.ysbbbbbb.kaleidoscopetavern.api.blockentity.IBarrel;
import com.github.ysbbbbbb.kaleidoscopetavern.block.brew.DrinkBlock;
import com.github.ysbbbbbb.kaleidoscopetavern.datamap.data.DrinkEffectData;
import com.github.ysbbbbbb.kaleidoscopetavern.datamap.resources.DrinkEffectDataReloadListener;
import com.github.ysbbbbbb.kaleidoscopetavern.init.ModDataComponents;
import com.github.ysbbbbbb.kaleidoscopetavern.util.ColorUtils;
import com.google.common.collect.Lists;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.DispenserBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.NonNull;

import java.util.List;
import java.util.function.Consumer;

public class BottleBlockItem extends BlockItem {
    @SuppressWarnings("unused")
    public static final String BREW_LEVEL_KEY = "BrewLevel";
    public static final int MIN_BREW_LEVEL_FOR_SHAKER = 4;

    public BottleBlockItem(Block block) {
        this(block, new Properties()
                .stacksTo(16));
    }

    public BottleBlockItem(Block block, Properties properties) {
        super(block, properties);
    }

    public static ItemStack getMaxLevelDrink(Item item) {
        ItemStack stack = item.getDefaultInstance();
        setBrewLevel(stack, IBarrel.BREWING_FINISHED);
        return stack;
    }

    public static void setBrewLevel(ItemStack stack, int brewLevel) {
        stack.set(ModDataComponents.BREW_LEVEL, clampBrewLevel(brewLevel));
    }

    public static int getBrewLevel(ItemStack stack) {
        int brewLevel = stack.getOrDefault(ModDataComponents.BREW_LEVEL, 0);
        int clampedBrewLevel = clampBrewLevel(brewLevel);
        if (brewLevel != clampedBrewLevel) {
            stack.set(ModDataComponents.BREW_LEVEL, clampedBrewLevel);
        }
        return clampedBrewLevel;
    }

    public static int clampBrewLevel(int brewLevel) {
        return Mth.clamp(brewLevel, IBarrel.BREWING_NOT_STARTED, IBarrel.BREWING_FINISHED);
    }

    public static boolean isValidForShaker(ItemStack stack) {
        if (!(stack.getItem() instanceof BottleBlockItem)) {
            return true;
        }
        return getBrewLevel(stack) >= MIN_BREW_LEVEL_FOR_SHAKER;
    }

    public @NotNull InteractionResult placeForDispenser(@NotNull BlockPlaceContext placeContext, DispenserBlockEntity entity) {
        BlockState blockState = placeContext.getLevel().getBlockState(placeContext.getClickedPos());
        if (blockState.is(this.getBlock()) && this.getBlock() instanceof DrinkBlock drinkBlock && drinkBlock.getMaxCount() > 1) {
            if (blockState.getValue(drinkBlock.getCountProperty()) < drinkBlock.getMaxCount()) {
                BlockPlaceContext updatedPlaceContext = this.updatePlacementContext(placeContext);
                if (updatedPlaceContext != null)
                    placeContext.getItemInHand().consume(1, updatedPlaceContext.getPlayer());
                entity.applyComponentsFromItemStack(placeContext.getItemInHand());
                entity.setChanged();
                return drinkBlock.tryIncreaseCount(placeContext.getLevel(), placeContext.getClickedPos(), blockState, placeContext.getItemInHand()) ? InteractionResult.SUCCESS : InteractionResult.FAIL;
            }
        }
        return super.place(placeContext);
    }

    public ItemStack getFilledStack(int brewLevel) {
        ItemStack stack = new ItemStack(this);
        setBrewLevel(stack, brewLevel);
        return stack;
    }

    @SuppressWarnings("deprecation")
    @Override
    public void appendHoverText(@NonNull ItemStack stack, @NonNull TooltipContext tooltipContext, @NonNull TooltipDisplay tooltipDisplay, @NonNull Consumer<Component> consumer, @NonNull TooltipFlag tooltipFlag) {
        // 添加颜色说明（1.21.11 同款：配料色 tag → ChatFormatting → "颜色：XX" 行）
        ChatFormatting applied = ColorUtils.ITEM_COLOR_CACHE.apply(stack.getItem());
        if (applied != ChatFormatting.RESET) {
            String key = "color.kaleidoscope_tavern.%s".formatted(applied.getName());
            Component text = Component.translatable("color.kaleidoscope_tavern.prefix")
                    .withStyle(ChatFormatting.GRAY)
                    .append(Component.translatable(key).withStyle(applied));
            consumer.accept(text);
        }
        int brewLevel = getBrewLevel(stack);
        if (0 < brewLevel) {
            Component brewLevelText = Component.translatable("message.kaleidoscope_tavern.barrel.brew_level.%d".formatted(brewLevel));
            consumer.accept(Component.translatable("tooltip.kaleidoscope_tavern.bottle_block.brew_level", brewLevelText).withStyle(ChatFormatting.GRAY));

            DrinkEffectData effectData = DrinkEffectDataReloadListener.INSTANCE.get(stack.getItem());
            if (effectData == null) {
                return;
            }
            var effects = effectData.effects();
            if (effects.isEmpty()) {
                return;
            }

            List<MobEffectInstance> effectsShow = Lists.newArrayList();
            for (DrinkEffectData.Entry entry : effects.get(brewLevel - 1)) {
                if (entry.probability() >= 1.0F) {
                    Holder<MobEffect> effect = entry.effect();
                    int duration = entry.duration() * 20;
                    int amplifier = entry.amplifier();
                    effectsShow.add(new MobEffectInstance(effect, duration, amplifier));
                }
            }

            if (!effectsShow.isEmpty()) {
                PotionContents.addPotionTooltip(effectsShow, consumer, 1.0F, tooltipContext.tickRate());
            }
        }
    }
}
