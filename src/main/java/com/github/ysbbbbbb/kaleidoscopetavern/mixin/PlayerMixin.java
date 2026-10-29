package com.github.ysbbbbbb.kaleidoscopetavern.mixin;

import com.github.ysbbbbbb.kaleidoscopetavern.api.entity.PlayerExtraData;
import com.github.ysbbbbbb.kaleidoscopetavern.api.event.special.PlayerTickEvent;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.OptionalInt;

@Mixin(Player.class)
public abstract class PlayerMixin extends LivingEntity implements PlayerExtraData {
    protected PlayerMixin(EntityType<? extends LivingEntity> entityType, Level level) {
        super(entityType, level);
    }

    @SuppressWarnings("all")
    @Unique
    private static final EntityDataAccessor<OptionalInt> PERSISTENT_DATA = SynchedEntityData.defineId(Player.class, EntityDataSerializers.OPTIONAL_UNSIGNED_INT);


    @Inject(method = "defineSynchedData", at = @At("TAIL"))
    public void kaleidoscopeTavern$defineSyncedData(final SynchedEntityData.Builder entityData, CallbackInfo ci) {
        entityData.define(PERSISTENT_DATA, OptionalInt.empty());
    }

    @Inject(method = "tick", at = @At("HEAD"))
    public void kaleidoscopeTavern$tickPre(CallbackInfo ci) {
        PlayerTickEvent.START.invoker().onStartOfPlayerTick((Player) (Object) this);
    }

    @Inject(method = "tick", at = @At("TAIL"))
    public void kaleidoscopeTavern$tickPost(CallbackInfo ci) {
        PlayerTickEvent.END.invoker().onEndOfPlayerTick((Player) (Object) this);
    }

    @Override
    public int kaleidoscopeTavern$getPersistentData() {
        return this.entityData.get(PERSISTENT_DATA).orElse(0);
    }

    @Override
    public void kaleidoscopeTavern$setPersistentData(int compoundTag) {
        this.entityData.set(PERSISTENT_DATA, OptionalInt.of(compoundTag));
    }
}
