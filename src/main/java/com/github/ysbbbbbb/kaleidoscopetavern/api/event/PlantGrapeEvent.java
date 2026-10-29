package com.github.ysbbbbbb.kaleidoscopetavern.api.event;

import com.github.ysbbbbbb.kaleidoscopetavern.util.event.CancellableKTEvent;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

public class PlantGrapeEvent extends CancellableKTEvent {
    private final BlockState state;
    private final Level level;
    private final BlockPos pos;
    private final Player player;
    private final InteractionHand hand;
    private final BlockHitResult hitResult;

    public PlantGrapeEvent(BlockState state, Level level, BlockPos pos, Player player,
                           InteractionHand hand, BlockHitResult hitResult) {
        this.state = state;
        this.level = level;
        this.pos = pos;
        this.player = player;
        this.hand = hand;
        this.hitResult = hitResult;
    }

    public BlockState state() {
        return state;
    }

    public Level level() {
        return level;
    }

    public BlockPos pos() {
        return pos;
    }

    public Player player() {
        return player;
    }

    public InteractionHand hand() {
        return hand;
    }

    public BlockHitResult hitResult() {
        return hitResult;
    }
}
