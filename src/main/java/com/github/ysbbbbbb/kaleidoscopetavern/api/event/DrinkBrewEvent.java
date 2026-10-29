package com.github.ysbbbbbb.kaleidoscopetavern.api.event;

import com.github.ysbbbbbb.kaleidoscopetavern.util.event.CancellableKTEvent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public abstract class DrinkBrewEvent extends CancellableKTEvent {
    private final ItemStack stack;
    private final Level level;
    private final LivingEntity entity;

    private DrinkBrewEvent(ItemStack stack, Level level, LivingEntity entity) {
        this.stack = stack;
        this.level = level;
        this.entity = entity;
    }

    public ItemStack stack() {
        return stack;
    }

    public Level level() {
        return level;
    }

    public LivingEntity entity() {
        return entity;
    }

    public static final class Pre extends DrinkBrewEvent {
        private final InteractionHand hand;

        public Pre(ItemStack stack, Level level, LivingEntity entity, InteractionHand hand) {
            super(stack, level, entity);
            this.hand = hand;
        }

        public InteractionHand hand() {
            return hand;
        }
    }

    public static final class Post extends DrinkBrewEvent {
        public Post(ItemStack stack, Level level, LivingEntity entity) {
            super(stack, level, entity);
        }
    }
}
