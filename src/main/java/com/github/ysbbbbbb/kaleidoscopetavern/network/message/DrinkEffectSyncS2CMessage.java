package com.github.ysbbbbbb.kaleidoscopetavern.network.message;

import com.github.ysbbbbbb.kaleidoscopetavern.KaleidoscopeTavern;
import com.github.ysbbbbbb.kaleidoscopetavern.datamap.data.DrinkEffectData;
import com.github.ysbbbbbb.kaleidoscopetavern.datamap.resources.DrinkEffectDataReloadListener;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * 服务端 -> 客户端同步所有 DrinkEffectData 数据
 */
public record DrinkEffectSyncS2CMessage(List<DrinkEffectData> entries) implements CustomPacketPayload {
    public static final Type<DrinkEffectSyncS2CMessage> TYPE = new Type<>(Identifier.fromNamespaceAndPath(KaleidoscopeTavern.MOD_ID, "drink_effect_sync"));

    public static final StreamCodec<RegistryFriendlyByteBuf, DrinkEffectSyncS2CMessage> STREAM_CODEC =
            DrinkEffectData.STREAM_CODEC
                    .apply(ByteBufCodecs.list())
                    .map(DrinkEffectSyncS2CMessage::new, DrinkEffectSyncS2CMessage::entries);

    /**
     * 从当前服务端 INSTANCE 构建消息
     */
    public static DrinkEffectSyncS2CMessage fromServer() {
        List<DrinkEffectData> copyData = List.copyOf(DrinkEffectDataReloadListener.INSTANCE.values());
        return new DrinkEffectSyncS2CMessage(copyData);
    }

    @Override
    public @NotNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    @SuppressWarnings("unused")
    @Environment(EnvType.CLIENT)
    public static void onHandle(DrinkEffectSyncS2CMessage message, ClientPlayNetworking.Context context) {
        DrinkEffectDataReloadListener.INSTANCE.clear();
        for (DrinkEffectData data : message.entries()) {
            DrinkEffectDataReloadListener.INSTANCE.put(data.item(), data);
        }
    }
}
