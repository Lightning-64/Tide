package com.li64.tide.network.messages;

import com.li64.tide.Tide;
import com.li64.tide.client.TideClientHelper;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;

public class OpenJournalMsg {
    public static final ResourceLocation ID = Tide.resource("open_journal");

    public OpenJournalMsg() {}

    public OpenJournalMsg(FriendlyByteBuf buf) {}

    public static void encode(OpenJournalMsg message, FriendlyByteBuf buf) {}

    public static void handle(OpenJournalMsg message, Player player) {
        TideClientHelper.openJournalScreen();
    }
}