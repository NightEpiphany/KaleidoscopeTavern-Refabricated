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
        super(block, properties.overrideDescription("block.kaleidoscope_tavern.sandwich_board"));
    }

    @SuppressWarnings("deprecation")
    @Override
    public void appendHoverText(@NonNull ItemStack itemStack, @NonNull TooltipContext tooltipContext, @NonNull TooltipDisplay tooltipDisplay, @NonNull Consumer<Component> consumer, @NonNull TooltipFlag tooltipFlag) {
        // 与原版一致：在名字下方列出所有可以转换成此展板的花（花列表在方块上维护）
        if (this.getBlock() instanceof SandwichBoardBlock board) {
            for (Item item : board.getTransformItems()) {
                consumer.accept(Component.translatable(item.getDescriptionId()).withStyle(ChatFormatting.GRAY));
            }
        }
    }
}
