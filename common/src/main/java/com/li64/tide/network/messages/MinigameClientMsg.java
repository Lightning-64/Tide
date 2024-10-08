package com.li64.tide.network.messages;

import com.li64.tide.Tide;
import com.li64.tide.client.gui.overlays.CatchMinigameOverlay;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.NotNull;

public class MinigameClientMsg implements CustomPacketPayload {
    public static final Type<MinigameClientMsg> TYPE = new Type<>(Tide.resource("minigame_client"));

    public final byte event;
    public final float data;

    public MinigameClientMsg(int event) {
        this(event, 0);
    }

    public MinigameClientMsg(int event, float data) {
        this.event = (byte) event;
        this.data = data;
    }

    public MinigameClientMsg(FriendlyByteBuf buf) {
        this.event = buf.readByte();
        this.data = buf.readFloat();
    }

    public static void encode(MinigameClientMsg message, FriendlyByteBuf buf) {
        buf.writeByte(message.event);
        buf.writeFloat(message.data);
    }

    public static void handle(MinigameClientMsg message, Player player) {
        switch (message.event) {
            case 0 -> CatchMinigameOverlay.start(message.data);
            case 1 -> CatchMinigameOverlay.interact();
            case 2 -> CatchMinigameOverlay.close();
        }
    }

    @Override
    public @NotNull Type<? extends CustomPacketPayload> type() { return TYPE; }
}