package com.github.ysbbbbbb.kaleidoscopetavern.item;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.Block;

public class GlasswareBlockItem extends BlockItem {
    public GlasswareBlockItem(Block block) {
        this(block, new Properties().stacksTo(16));
    }

    public GlasswareBlockItem(Block block, Properties properties) {
        // 与原版一致：上限 16，在构造内强制，避免注册路径绕过
        super(block, properties.stacksTo(16));
    }
}
