package com.github.ysbbbbbb.kaleidoscopetavern.api.event;

import com.github.ysbbbbbb.kaleidoscopetavern.util.event.IKTEvent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;

public record CocktailCraftedEvent(ServerPlayer player, ItemStack result) implements IKTEvent {
}
