package com.github.ysbbbbbb.kaleidoscopetavern.mixin;

import com.github.ysbbbbbb.kaleidoscopetavern.api.event.LivingChangeTargetEvent;
import com.github.ysbbbbbb.kaleidoscopetavern.util.event.EventHooks;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Mob.class)
public abstract class MobMixin extends LivingEntity {

    protected MobMixin(EntityType<? extends LivingEntity> entityType, Level level) {
        super(entityType, level);
    }

    @Inject(method = "setTarget", at = @At("HEAD"), cancellable = true)
    public void kaleidoscopeTavern$setTarget(LivingEntity target, CallbackInfo ci) {
        LivingChangeTargetEvent livingChangeTargetEvent = EventHooks.onLivingChangeTarget(this, target, LivingChangeTargetEvent.LivingTargetType.MOB_TARGET);
        if (livingChangeTargetEvent.isCanceled()) ci.cancel();
    }
}
