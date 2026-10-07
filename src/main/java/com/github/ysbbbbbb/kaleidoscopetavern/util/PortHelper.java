package com.github.ysbbbbbb.kaleidoscopetavern.util;

import com.github.ysbbbbbb.kaleidoscopetavern.KaleidoscopeTavern;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.TextColor;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

// 服了
public class PortHelper {
    public static ResourceKey<Block> createBlockId(String name) {
        return ResourceKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath(KaleidoscopeTavern.MOD_ID, name));
    }

    public static EquipmentSlot getSlotForHand(InteractionHand interactionHand) {
        return interactionHand == InteractionHand.MAIN_HAND ? EquipmentSlot.MAINHAND : EquipmentSlot.OFFHAND;
    }

    public static ResourceKey<Item> createItemId(String name) {
        return ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(KaleidoscopeTavern.MOD_ID, name));
    }

    public static int getTextColor(DyeColor color) {
        int res;
        switch (color) {
            case WHITE -> res = 16777215;
            case ORANGE -> res = 16738335;
            case MAGENTA -> res = 16711935;
            case LIGHT_BLUE -> res = 10141901;
            case YELLOW -> res = 16776960;
            case LIME -> res = 12582656;
            case PINK -> res = 16738740;
            case GRAY -> res = 8421504;
            case LIGHT_GRAY -> res = 13882323;
            case CYAN -> res = 65535;
            case PURPLE -> res = 10494192;
            case BLUE -> res = 255;
            case BROWN -> res = 9127187;
            case RED -> res = 16711680;
            case GREEN -> res = 65280;
            default -> res = 0;
        }

        return res;
    }

}
