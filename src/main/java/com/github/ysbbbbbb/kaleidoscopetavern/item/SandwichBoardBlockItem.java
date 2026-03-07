package com.github.ysbbbbbb.kaleidoscopetavern.item;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.level.block.Block;
import org.jspecify.annotations.NonNull;

import java.util.List;
import java.util.function.Consumer;

public class SandwichBoardBlockItem extends BlockItem {
    private final List<Item> transformItems;
    public SandwichBoardBlockItem(Block block, Properties properties, Item... transformItems) {
        super(block, properties.overrideDescription("block.kaleidoscope_tavern.sandwich_board"));
        this.transformItems = List.of(transformItems);
    }

    @SuppressWarnings("deprecation")
    @Override
    public void appendHoverText(@NonNull ItemStack itemStack, @NonNull TooltipContext tooltipContext, @NonNull TooltipDisplay tooltipDisplay, @NonNull Consumer<Component> consumer, @NonNull TooltipFlag tooltipFlag) {
        if (!this.transformItems.isEmpty()) {
            consumer.accept(transformItems.getFirst().getName().copy().withStyle(ChatFormatting.GRAY));
        }

    }
}
