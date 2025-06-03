package com.li64.tide.network.messages;

import com.li64.tide.Tide;
import com.li64.tide.client.TideClientHelper;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.NotNull;

public class ViewNoteMsg implements CustomPacketPayload{
    public static final CustomPacketPayload.Type<ViewNoteMsg> TYPE = new CustomPacketPayload.Type<>(Tide.resource("view_note"));

    private final String id;

    public ViewNoteMsg(String id) {
        this.id = id;
    }

    public ViewNoteMsg(FriendlyByteBuf buf) {
        this.id = buf.readUtf();
    }

    public static void encode(ViewNoteMsg message, FriendlyByteBuf buf) {
        buf.writeUtf(message.id);
    }

    public static void handle(ViewNoteMsg message, Player player) {
        TideClientHelper.openNoteScreen(message.id);
    }

    @Override
    public @NotNull Type<? extends CustomPacketPayload> type() { return TYPE; }
}