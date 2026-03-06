package com.github.ysbbbbbb.kaleidoscopetavern.item;

import net.minecraft.ChatFormatting;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Util;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.level.block.Block;
import org.jspecify.annotations.NonNull;

import java.util.function.Consumer;

public class PaintingBlockItem extends BlockItem {
    public PaintingBlockItem(Block block, Properties properties) {
        super(block, properties.overrideDescription("block.kaleidoscope_tavern.painting"));
    }

    @Override
    public void appendHoverText(@NonNull ItemStack itemStack, @NonNull TooltipContext tooltipContext, @NonNull TooltipDisplay tooltipDisplay, @NonNull Consumer<Component> consumer, @NonNull TooltipFlag tooltipFlag) {
        String desc = Util.makeDescriptionId("tooltip", BuiltInRegistries.ITEM.getKey(this));
        consumer.accept(Component.translatable(desc).withStyle(ChatFormatting.GRAY));
    }
}
