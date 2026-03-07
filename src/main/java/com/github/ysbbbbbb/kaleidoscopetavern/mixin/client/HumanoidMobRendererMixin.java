package com.github.ysbbbbbb.kaleidoscopetavern.mixin.client;

import com.github.ysbbbbbb.kaleidoscopetavern.item.StringLightsBlockItem;
import com.llamalad7.mixinextras.sugar.Local;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Environment(EnvType.CLIENT)
@Mixin(HumanoidMobRenderer.class)
public abstract class HumanoidMobRendererMixin {

    @Inject(method = "getEquipmentIfRenderable", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/entity/layers/HumanoidArmorLayer;shouldRender(Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/entity/EquipmentSlot;)Z", shift = At.Shift.BY, by = -1), cancellable = true)
    private static void getEquipmentIfRenderable(LivingEntity livingEntity, EquipmentSlot equipmentSlot, CallbackInfoReturnable<ItemStack> cir, @Local(ordinal = 0) ItemStack itemStack) {
        if (itemStack.getItem() instanceof StringLightsBlockItem)
            cir.setReturnValue(itemStack.copy());
    }
}
