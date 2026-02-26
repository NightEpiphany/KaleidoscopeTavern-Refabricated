package com.github.ysbbbbbb.kaleidoscopetavern.network.message;

import com.github.ysbbbbbb.kaleidoscopetavern.blockentity.deco.TextBlockEntity;
import com.github.ysbbbbbb.kaleidoscopetavern.client.gui.block.TextScreen;
import com.github.ysbbbbbb.kaleidoscopetavern.network.NetworkHandler;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.networking.v1.FabricPacket;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.PacketType;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public record TextOpenS2CMessage(BlockPos pos) implements FabricPacket {
    public static final PacketType<TextOpenS2CMessage> TYPE = PacketType.create(NetworkHandler.TEXT_OPEN_S2C_PACKET, TextOpenS2CMessage::new);

    public TextOpenS2CMessage(FriendlyByteBuf buf) {
        this(buf.readBlockPos());
    }

    @Override
    public void write(FriendlyByteBuf friendlyByteBuf) {
        friendlyByteBuf.writeBlockPos(pos);
    }

    @Override
    public PacketType<?> getType() {
        return TYPE;
    }

    @Environment(EnvType.CLIENT)
    public static void receive(TextOpenS2CMessage message, LocalPlayer localPlayer, PacketSender packetSender) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.level == null) {
            return;
        }
        Player player = mc.player;
        if (player == null) {
            return;
        }
        if (!mc.level.isLoaded(message.pos)) {
            return;
        }
        Level level = mc.level;
        BlockPos pos = message.pos();
        if (level.getBlockEntity(pos) instanceof TextBlockEntity textBlock) {
            if (textBlock.isWaxed()) {
                return;
            }
            if (textBlock.playerIsTooFarAwayToEdit(player.getUUID())) {
                return;
            }
            mc.setScreen(new TextScreen(textBlock));
        }
    }
}
