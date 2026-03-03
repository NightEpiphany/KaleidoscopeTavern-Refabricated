package com.github.ysbbbbbb.kaleidoscopetavern.mixin;

import com.github.ysbbbbbb.kaleidoscopetavern.item.StringLightsBlockItem;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {

    @Inject(method = "getEquipmentSlotForItem(Lnet/minecraft/world/item/ItemStack;)Lnet/minecraft/world/entity/EquipmentSlot;", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/Equipable;getEquipmentSlot()Lnet/minecraft/world/entity/EquipmentSlot;"), cancellable = true)
    private static void getEquipmentSlotForItem(ItemStack itemStack, CallbackInfoReturnable<EquipmentSlot> cir) {
        if (itemStack.getItem() instanceof StringLightsBlockItem) {
            if (cir == null || itemStack.isEmpty()) {
                return;
            }
            cir.setReturnValue(EquipmentSlot.CHEST);
        }
    }
}
