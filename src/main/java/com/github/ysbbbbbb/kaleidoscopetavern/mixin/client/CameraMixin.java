package com.github.ysbbbbbb.kaleidoscopetavern.mixin.client;

import com.github.ysbbbbbb.kaleidoscopetavern.api.event.ViewportEvent;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Camera;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@SuppressWarnings("all")
@Environment(EnvType.CLIENT)
@Mixin(Camera.class)
public class CameraMixin {

    @WrapOperation(method = "alignWithEntity", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/Camera;setRotation(FF)V", ordinal = 0))
    private void kaleidoscopeTavern$setup(Camera instance, float yRot, float xRot, Operation<Void> original, @Local(argsOnly = true, ordinal = 0) float partialTicks) {
        var cameraSetup = new ViewportEvent.ComputeCameraAngles(
                ((Camera) (Object)this), partialTicks,
                yRot,
                xRot,
                0
        );
        cameraSetup.post();
        original.call(instance, cameraSetup.getYaw(), cameraSetup.getPitch());
    }
}
