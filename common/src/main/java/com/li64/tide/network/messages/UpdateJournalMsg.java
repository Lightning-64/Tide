package com.li64.tide.network.messages;

import com.google.common.collect.ImmutableList;
import com.li64.tide.Tide;
import com.li64.tide.data.journal.JournalLayout;
import com.li64.tide.data.journal.config.JournalRemovalCustomData;
import com.li64.tide.util.TideUtils;
import com.mojang.serialization.Codec;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class UpdateJournalMsg {
    public static final ResourceLocation ID = Tide.resource("update_journal");

    private final List<JournalLayout.Page> customPages;
    private final List<JournalLayout.Profile> customProfiles;
    private final List<JournalRemovalCustomData.Removal> removals;

    public UpdateJournalMsg() {
        customPages = Tide.JOURNAL_PAGE_CUSTOM_DATA.getPageConfigs();
        customProfiles = Tide.JOURNAL_PROFILE_CUSTOM_DATA.getProfileConfigs();
        removals = Tide.JOURNAL_REMOVAL_CUSTOM_DATA.getRemovalConfigs();
    }

    public UpdateJournalMsg(FriendlyByteBuf buf) {
        customPages = readEntries(JournalLayout.Page.CODEC, buf);
        customProfiles = readEntries(JournalLayout.Profile.CODEC, buf);
        removals = readEntries(JournalRemovalCustomData.Removal.CODEC, buf);
    }

    public static void encode(UpdateJournalMsg message, FriendlyByteBuf buf) {
        writeEntries(message.customPages, JournalLayout.Page.CODEC, buf);
        writeEntries(message.customProfiles, JournalLayout.Profile.CODEC, buf);
        writeEntries(message.removals, JournalRemovalCustomData.Removal.CODEC, buf);
    }

    public static <T> ImmutableList<T> readEntries(Codec<T> codec, FriendlyByteBuf buf) {
        ArrayList<T> tempValues = new ArrayList<>();
        int size = buf.readInt();
        for (int i = 0; i < size; i++) {
            tempValues.add(buf.readJsonWithCodec(codec));
        }
        return ImmutableList.copyOf(tempValues);
    }

    public static <T> void writeEntries(List<T> entries, Codec<T> codec, FriendlyByteBuf buf) {
        buf.writeInt(entries.size());
        entries.forEach(entry -> buf.writeJsonWithCodec(codec, entry));
    }

    public static void handle(UpdateJournalMsg message, Player player) {
        Tide.JOURNAL = new JournalLayout();
        Tide.JOURNAL.addPageConfigs(message.customPages);
        Tide.JOURNAL.addProfileConfigs(message.customProfiles);
        Tide.JOURNAL.removeProfileConfigs(message.removals);
        TideUtils.PROFILE_ITEMS = null;
    }
}