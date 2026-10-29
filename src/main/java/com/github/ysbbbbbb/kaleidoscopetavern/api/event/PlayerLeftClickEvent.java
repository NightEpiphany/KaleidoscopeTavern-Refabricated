package com.github.ysbbbbbb.kaleidoscopetavern.api.event;

import com.github.ysbbbbbb.kaleidoscopetavern.client.event.LeftClickEvent;
import com.github.ysbbbbbb.kaleidoscopetavern.util.event.CancellableKTEvent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.Nullable;

public class PlayerLeftClickEvent extends CancellableKTEvent {
    private final @Nullable Player player;
    private final InteractionHand hand;

    public PlayerLeftClickEvent(@Nullable Player player, InteractionHand hand) {
        this.player = player;
        this.hand = hand;
    }

    public @Nullable Player getPlayer() {
        return player;
    }

    public InteractionHand getHand() {
        return hand;
    }

    public static void register() {
        CALLBACK.register(event -> {
            if (event instanceof PlayerLeftClickEvent playerLeftClickEvent) {
                LeftClickEvent.onLeftClickItem(playerLeftClickEvent);
            }
        });
    }
}
