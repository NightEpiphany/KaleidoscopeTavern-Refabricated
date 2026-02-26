package com.github.ysbbbbbb.kaleidoscopetavern.network.message;

import com.github.ysbbbbbb.kaleidoscopetavern.blockentity.deco.TextBlockEntity;
import com.github.ysbbbbbb.kaleidoscopetavern.network.NetworkHandler;
import com.github.ysbbbbbb.kaleidoscopetavern.util.TextAlignment;
import net.fabricmc.fabric.api.networking.v1.FabricPacket;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.PacketType;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;

public record TextUpdateC2SMessage(BlockPos pos, String text,
                                   TextAlignment textAlignment) implements FabricPacket {
    public static final PacketType<TextUpdateC2SMessage> TYPE = PacketType.create(NetworkHandler.TEXT_UPDATE_C2S_PACKET, TextUpdateC2SMessage::new);

    public TextUpdateC2SMessage(FriendlyByteBuf buf) {
        this(buf.readBlockPos(), buf.readUtf(2048), buf.readEnum(TextAlignment.class));
    }

    @Override
    public void write(FriendlyByteBuf friendlyByteBuf) {
        friendlyByteBuf.writeBlockPos(this.pos);
        friendlyByteBuf.writeUtf(this.text, 2048);
        friendlyByteBuf.writeEnum(this.textAlignment);
    }

    @Override
    public PacketType<?> getType() {
        return TYPE;
    }

    public static void receive(TextUpdateC2SMessage message, ServerPlayer sender, PacketSender packetSender) {
        if (sender == null) {
            return;
        }
        Level level = sender.level();
        BlockPos pos = message.pos();
        if (!level.isLoaded(pos)) {
            return;
        }
        if (level.getBlockEntity(pos) instanceof TextBlockEntity textBlock) {
            if (textBlock.isWaxed()) {
                return;
            }
            if (textBlock.playerIsTooFarAwayToEdit(sender.getUUID())) {
                return;
            }
            textBlock.setText(message.text());
            textBlock.setTextAlignment(message.textAlignment());
            textBlock.refresh();
        }
    }
}
