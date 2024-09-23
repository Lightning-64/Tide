package com.li64.tide.network.messages;

import com.li64.tide.Tide;
import com.li64.tide.client.TideClientHelper;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("unused")
public class OpenJournalMsg implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<OpenJournalMsg> TYPE = new CustomPacketPayload.Type<>(Tide.resource("open_journal"));

    public OpenJournalMsg() {}

    public OpenJournalMsg(FriendlyByteBuf buf) {}

    public static void encode(OpenJournalMsg message, FriendlyByteBuf buf) {}

    public static void handle(OpenJournalMsg message, Player player) {
        TideClientHelper.openJournalScreen();
    }

    @Override
    public @NotNull Type<? extends CustomPacketPayload> type() { return TYPE; }
}