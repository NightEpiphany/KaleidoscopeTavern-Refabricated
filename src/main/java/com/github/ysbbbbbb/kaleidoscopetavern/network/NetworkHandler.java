package com.github.ysbbbbbb.kaleidoscopetavern.network;

import com.github.ysbbbbbb.kaleidoscopetavern.network.message.ClearShakerC2SMessage;
import com.github.ysbbbbbb.kaleidoscopetavern.network.message.DrinkEffectSyncS2CMessage;
import com.github.ysbbbbbb.kaleidoscopetavern.network.message.TextOpenS2CMessage;
import com.github.ysbbbbbb.kaleidoscopetavern.network.message.TextUpdateC2SMessage;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;

public class NetworkHandler {

    public static void init() {
        //==================================================Payload==============================================
        PayloadTypeRegistry.playS2C().register(TextOpenS2CMessage.TYPE, TextOpenS2CMessage.STREAM_CODEC);
        PayloadTypeRegistry.playS2C().register(DrinkEffectSyncS2CMessage.TYPE, DrinkEffectSyncS2CMessage.STREAM_CODEC);
        PayloadTypeRegistry.playC2S().register(TextUpdateC2SMessage.TYPE, TextUpdateC2SMessage.STREAM_CODEC);
        PayloadTypeRegistry.playC2S().register(ClearShakerC2SMessage.TYPE, ClearShakerC2SMessage.STREAM_CODEC);

        ServerPlayNetworking.registerGlobalReceiver(TextUpdateC2SMessage.TYPE, TextUpdateC2SMessage::receive);
        ServerPlayNetworking.registerGlobalReceiver(ClearShakerC2SMessage.TYPE, ClearShakerC2SMessage::receive);
        // 玩家进服时把服务端的饮品效果表同步到客户端，供 tooltip 使用
        ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> {
            DrinkEffectSyncS2CMessage message = DrinkEffectSyncS2CMessage.fromServer();
            server.getPlayerList().getPlayers().forEach(serverPlayer -> {
                ServerPlayNetworking.send(serverPlayer, message);
            });
        });
    }

    @Environment(EnvType.CLIENT)
    public static class Clientside {
        public static void init() {
            ClientPlayNetworking.registerGlobalReceiver(TextOpenS2CMessage.TYPE, TextOpenS2CMessage::receive);
            ClientPlayNetworking.registerGlobalReceiver(DrinkEffectSyncS2CMessage.TYPE, DrinkEffectSyncS2CMessage::onHandle);
        }
    }
}
