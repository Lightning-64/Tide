package com.li64.tide.network.messages;

import com.li64.tide.Tide;
import com.li64.tide.data.player.TidePlayerData;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.NotNull;

public class SyncDataMsg implements CustomPacketPayload {
    public static final Type<SyncDataMsg> TYPE = new Type<>(Tide.resource("sync_data"));

    public final int[] pagesUnlocked;
    public final int[] fishUnlocked;
    public final int[] unreadProfiles;
    public final int[] pagesCompleted;
    public final boolean gotJournal;
    public final boolean finishedJournal;

    public SyncDataMsg(TidePlayerData data) {
        this.pagesUnlocked = data.pagesIntArray();
        this.fishUnlocked = data.fishIntArray();
        this.unreadProfiles = data.unreadIntArray();
        this.pagesCompleted = data.pagesCompletedIntArray();
        this.gotJournal = data.gotJournal;
        this.finishedJournal = data.finishedJournal;
    }

    public SyncDataMsg(FriendlyByteBuf buf) {
        this.pagesUnlocked = buf.readVarIntArray();
        this.fishUnlocked = buf.readVarIntArray();
        this.unreadProfiles = buf.readVarIntArray();
        this.pagesCompleted = buf.readVarIntArray();
        this.gotJournal = buf.readBoolean();
        this.finishedJournal = buf.readBoolean();
    }

    public static void encode(SyncDataMsg message, FriendlyByteBuf buf) {
        buf.writeVarIntArray(message.pagesUnlocked);
        buf.writeVarIntArray(message.fishUnlocked);
        buf.writeVarIntArray(message.unreadProfiles);
        buf.writeVarIntArray(message.pagesCompleted);
        buf.writeBoolean(message.gotJournal);
        buf.writeBoolean(message.finishedJournal);
    }

    public static void handle(SyncDataMsg message, Player player) {
        TidePlayerData.CLIENT_DATA.pagesUnlocked = TidePlayerData.fromIntArray(message.pagesUnlocked);
        TidePlayerData.CLIENT_DATA.fishUnlocked = TidePlayerData.fromIntArray(message.fishUnlocked);
        TidePlayerData.CLIENT_DATA.unreadProfiles = TidePlayerData.fromIntArray(message.unreadProfiles);
        TidePlayerData.CLIENT_DATA.pagesCompleted = TidePlayerData.fromIntArray(message.pagesCompleted);
        TidePlayerData.CLIENT_DATA.gotJournal = message.gotJournal;
        TidePlayerData.CLIENT_DATA.finishedJournal = message.finishedJournal;
    }

    @Override
    public @NotNull Type<? extends CustomPacketPayload> type() { return TYPE; }
}