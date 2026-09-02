package com.github.ysbbbbbb.kaleidoscopetavern.network.message;

import com.github.ysbbbbbb.kaleidoscopetavern.KaleidoscopeTavern;
import com.github.ysbbbbbb.kaleidoscopetavern.init.ModItems;
import com.github.ysbbbbbb.kaleidoscopetavern.item.ShakerItem;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public record ClearShakerC2SMessage() implements CustomPacketPayload {
    public static final Type<ClearShakerC2SMessage> TYPE = new Type<>(Identifier.fromNamespaceAndPath(KaleidoscopeTavern.MOD_ID, "clear_shaker"));
    public static final StreamCodec<FriendlyByteBuf, ClearShakerC2SMessage> STREAM_CODEC = StreamCodec.unit(new ClearShakerC2SMessage());

    @SuppressWarnings("unused")
    public static void receive(ClearShakerC2SMessage message, ServerPlayNetworking.Context context) {
        if (context.player() == null) {
            return;
        }
        ItemStack stack = context.player().getMainHandItem();
        if (stack.is(ModItems.SHAKER) && ShakerItem.hasStorage(stack)) {
            ShakerItem.removeAll(stack);
            context.player().level().playSound(null, context.player().blockPosition(), SoundEvents.BOTTLE_FILL, SoundSource.PLAYERS);
        }
    }

    @Override
    public @NotNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
