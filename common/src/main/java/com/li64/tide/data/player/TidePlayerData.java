package com.li64.tide.data.player;

import com.li64.tide.Tide;
import com.li64.tide.client.gui.JournalPage;
import com.li64.tide.data.journal.JournalLayout;
import com.li64.tide.network.messages.SyncDataMsg;
import com.li64.tide.util.TideUtils;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class TidePlayerData {
    public static TidePlayerData CLIENT_DATA = new TidePlayerData();
    private static final String NBT_TAG = "TidePlayerData";

    private final int[] emptyIntArray = new int[0];

    public static TidePlayerData getOrCreate(ServerPlayer player) {
        return getOrCreate(Tide.PLATFORM.getPlayerData(player));
    }

    public static TidePlayerData getOrCreate(CompoundTag tag) {
        if (!tag.contains(NBT_TAG)) tag.put(NBT_TAG, new CompoundTag());
        return new TidePlayerData(tag.getCompoundOrEmpty(NBT_TAG));
    }

    public List<Integer> fishUnlocked = new ArrayList<>();
    public List<Integer> unreadProfiles = new ArrayList<>();
    public List<Integer> pagesUnlocked = new ArrayList<>();
    public List<Integer> pagesCompleted = new ArrayList<>();
    public boolean gotJournal = false;
    public boolean finishedJournal = false;

    public TidePlayerData(CompoundTag tag) {
        deserializeNBT(tag);
    }

    public TidePlayerData() {}

    public void deserializeNBT(CompoundTag tag) {
        fishUnlocked = fromIntArray(tag.getIntArray("fish_unlocked").orElse(emptyIntArray));
        unreadProfiles = fromIntArray(tag.getIntArray("unread_pages").orElse(emptyIntArray));
        pagesUnlocked = fromIntArray(tag.getIntArray("pages_unlocked").orElse(emptyIntArray));
        pagesCompleted = fromIntArray(tag.getIntArray("pages_completed").orElse(emptyIntArray));
        gotJournal = tag.getBoolean("got_journal").orElse(false);
        finishedJournal = tag.getBoolean("finished_journal").orElse(false);
    }

    public CompoundTag serializeNBT() {
        CompoundTag tag = new CompoundTag();
        tag.putIntArray("fish_unlocked", toIntArray(fishUnlocked));
        tag.putIntArray("unread_pages", toIntArray(unreadProfiles));
        tag.putIntArray("pages_unlocked", toIntArray(pagesUnlocked));
        tag.putIntArray("pages_completed", toIntArray(pagesCompleted));
        tag.putBoolean("got_journal", gotJournal);
        tag.putBoolean("finished_journal", finishedJournal);
        return tag;
    }

    public void syncTo(ServerPlayer player) {
        CompoundTag playerData = Tide.PLATFORM.getPlayerData(player);
        playerData.put(NBT_TAG, serializeNBT());
        if (player instanceof ServerPlayer serverPlayer) {
            Tide.NETWORK.sendToPlayer(new SyncDataMsg(this), serverPlayer);
        }
    }

    public static int[] toIntArray(List<Integer> list) {
        return list.stream().mapToInt(n -> n).toArray();
    }

    public static ArrayList<Integer> fromIntArray(int[] array) {
        ArrayList<Integer> newList = new ArrayList<>();
        for (int value : array) {
            newList.add(value);
        }
        return newList;
    }

    public int[] pagesIntArray() {
        int[] intArray = new int[pagesUnlocked.size()];
        for (int i = 0; i < pagesUnlocked.size(); i++) {
            intArray[i] = pagesUnlocked.get(i);
        }
        return intArray;
    }

    public int[] pagesCompletedIntArray() {
        int[] intArray = new int[pagesUnlocked.size()];
        for (int i = 0; i < pagesUnlocked.size(); i++) {
            intArray[i] = pagesUnlocked.get(i);
        }
        return intArray;
    }

    public int[] fishIntArray() {
        int[] intArray = new int[fishUnlocked.size()];
        for (int i = 0; i < fishUnlocked.size(); i++) {
            intArray[i] = fishUnlocked.get(i);
        }
        return intArray;
    }

    public int[] unreadIntArray() {
        int[] intArray = new int[unreadProfiles.size()];
        for (int i = 0; i < unreadProfiles.size(); i++) {
            intArray[i] = unreadProfiles.get(i);
        }
        return intArray;
    }

    public boolean hasPageUnlocked(JournalPage page) {
        return pagesUnlocked.contains(page.id()) || page.unlockedByDefault();
    }

    public boolean unlockPage(JournalPage page) {
        if (!hasPageUnlocked(page)) {
            pagesUnlocked.add(page.id());
            return true;
        } else return false;
    }

    public void lockAllPages() {
        pagesUnlocked.clear();
    }

    public boolean hasFishUnlocked(ItemStack fish) {
        return hasFishUnlocked(fish.getItem());
    }

    public boolean hasFishUnlocked(Item fish) {
        int fishId = Item.getId(fish);
        return fishUnlocked.contains(fishId);
    }

    public void unlockFish(ItemStack fish) {
        if (!hasFishUnlocked(fish)) {
            int fishId = Item.getId(fish.getItem());
            fishUnlocked.add(fishId);
            markAsUnread(fishId);
        }
    }

    public void lockAllFish() {
        pagesCompleted.clear();
        pagesUnlocked.clear();
        fishUnlocked.clear();
        unreadProfiles.clear();
    }

    public boolean hasPageCompleted(JournalPage page) {
        return pagesCompleted.contains(page.id());
    }

    public boolean isUnread(JournalLayout.Profile profile) {
        Item fish = TideUtils.getItemFromName(profile.fishItem());
        int fishId = Item.getId(fish);
        return unreadProfiles.contains(fishId);
    }

    public void markAsRead(int fishId) {
        Tide.LOG.info("Marked id {} as read", fishId);
        unreadProfiles.remove((Integer) fishId);
    }

    public void markAsUnread(int fishId) {
        Tide.LOG.info("Marked id {} as unread", fishId);
        if (!unreadProfiles.contains(fishId)) unreadProfiles.add(fishId);
    }
}
