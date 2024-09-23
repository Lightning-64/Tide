package com.li64.tide.network.messages;

import com.li64.tide.Tide;
import com.li64.tide.data.minigame.FishCatchMinigame;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;

public class MinigameServerMsg {
    public static final ResourceLocation ID = Tide.resource("minigame_server");

    public final byte event;

    public MinigameServerMsg(int event) {
        this.event = (byte) event;
    }

    public MinigameServerMsg(FriendlyByteBuf buf) {
        this.event = buf.readByte();
    }

    public static void encode(MinigameServerMsg message, FriendlyByteBuf buf) {
        buf.writeByte(message.event);
    }

    public static void handle(MinigameServerMsg message, Player player) {
        FishCatchMinigame minigame = FishCatchMinigame.create(player);
        minigame.handleClientEvent(message.event);
    }
}