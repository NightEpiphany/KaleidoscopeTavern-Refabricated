package com.github.ysbbbbbb.kaleidoscopetavern.item;

import com.github.ysbbbbbb.kaleidoscopetavern.block.deco.SandwichBoardBlock;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.level.block.Block;
import org.jspecify.annotations.NonNull;

import java.util.function.Consumer;

public class SandwichBoardBlockItem extends BlockItem {
    public SandwichBoardBlockItem(Block block, Properties properties) {
        super(block, properties.overrideDescription("block.kaleidoscopetavern.sandwich_board"));
    }

    @Override
    public void appendHoverText(@NonNull ItemStack itemStack, @NonNull TooltipContext tooltipContext, @NonNull TooltipDisplay tooltipDisplay, @NonNull Consumer<Component> consumer, @NonNull TooltipFlag tooltipFlag) {
        SandwichBoardBlock sandwichBoardBlock = SandwichBoardBlock.TRANSFORM_MAP.get(itemStack.getItem());
        if (sandwichBoardBlock != null)
            if (sandwichBoardBlock.getTransformItemNames() != null) {
                sandwichBoardBlock.getTransformItemNames().forEach(name -> consumer.accept(Component.translatable(name).withStyle(ChatFormatting.GRAY)));
            }
    }
}
