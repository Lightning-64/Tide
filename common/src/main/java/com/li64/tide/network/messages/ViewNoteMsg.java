package com.li64.tide.network.messages;

import com.li64.tide.Tide;
import com.li64.tide.client.TideClientHelper;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;

public class ViewNoteMsg {
    public static final ResourceLocation ID = Tide.resource("view_note");

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
}