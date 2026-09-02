package com.github.ysbbbbbb.kaleidoscopetavern.item;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import java.util.function.Consumer;
import net.minecraft.world.level.block.Block;

import java.util.List;

public class TooltipBlockItem extends BlockItem {
    private final String[] tooltipKeys;

    public TooltipBlockItem(Block block, Properties properties, String... tooltipKeys) {
        super(block, properties);
        this.tooltipKeys = tooltipKeys;
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, TooltipDisplay tooltipDisplay, Consumer<Component> consumer, TooltipFlag flag) {
        for (String tooltipKey : this.tooltipKeys) {
            consumer.accept(Component.translatable(tooltipKey).withStyle(ChatFormatting.GRAY));
        }
    }
}
