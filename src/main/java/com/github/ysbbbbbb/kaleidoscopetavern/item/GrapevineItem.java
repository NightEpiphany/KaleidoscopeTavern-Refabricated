package com.github.ysbbbbbb.kaleidoscopetavern.item;

import com.github.ysbbbbbb.kaleidoscopetavern.init.ModBlocks;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import java.util.function.Consumer;

import java.util.List;

public class GrapevineItem extends BlockItem {
    public GrapevineItem() {
        super(ModBlocks.WILD_GRAPEVINE, new Properties());
    }

    public GrapevineItem(Properties properties) {
        super(ModBlocks.WILD_GRAPEVINE, properties);
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, TooltipDisplay tooltipDisplay, Consumer<Component> consumer, TooltipFlag flag) {
        consumer.accept(Component.translatable("tooltip.kaleidoscope_tavern.grapevine.1").withStyle(ChatFormatting.GRAY));
        consumer.accept(Component.translatable("tooltip.kaleidoscope_tavern.grapevine.2").withStyle(ChatFormatting.GRAY));
        consumer.accept(Component.translatable("tooltip.kaleidoscope_tavern.grapevine.3").withStyle(ChatFormatting.GRAY));
    }
}
