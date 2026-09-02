package com.github.ysbbbbbb.kaleidoscopetavern.client.event;

import com.github.ysbbbbbb.kaleidoscopetavern.api.event.PlayerLeftClickEvent;
import com.github.ysbbbbbb.kaleidoscopetavern.init.ModItems;
import com.github.ysbbbbbb.kaleidoscopetavern.item.ShakerItem;
import com.github.ysbbbbbb.kaleidoscopetavern.network.message.ClearShakerC2SMessage;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;

public class LeftClickEvent {

    public static void onLeftClickItem(PlayerLeftClickEvent event) {
        if (event.getPlayer() == null) return;
        if (event.getPlayer().isSecondaryUseActive() && event.getHand() == InteractionHand.MAIN_HAND) {
            ItemStack stack = event.getPlayer().getMainHandItem();
            if (stack.is(ModItems.SHAKER) && ShakerItem.hasStorage(stack)) {
                ClientPlayNetworking.send(new ClearShakerC2SMessage());
            }
        }
    }
}
