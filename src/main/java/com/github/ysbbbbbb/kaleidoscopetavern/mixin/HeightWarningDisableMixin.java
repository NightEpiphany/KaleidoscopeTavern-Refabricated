package com.github.ysbbbbbb.kaleidoscopetavern.mixin;

import net.minecraft.server.level.ServerPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayer.class)
public abstract class HeightWarningDisableMixin {

    @Inject(method = "sendBuildLimitMessage", at = @At("HEAD"), cancellable = true)
    public void kaleidoscopeTavern$disableHeightWarning(boolean isTooHigh, int limit, CallbackInfo ci) {
        ServerPlayer serverPlayer = (ServerPlayer) (Object) this;
        if (serverPlayer.getY() < 319 && limit == 319)
            ci.cancel();
    }
}
