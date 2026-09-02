package com.github.ysbbbbbb.kaleidoscopetavern.mixin;

import net.minecraft.world.entity.monster.zombie.ZombieVillager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

import java.util.UUID;

@Mixin(ZombieVillager.class)
public interface ZombieVillagerAccessor {
    @Invoker("startConverting")
    void invokeStartConverting(UUID uuid, int time);
}