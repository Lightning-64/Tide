package com.li64.tide.events;

import com.li64.tide.Tide;
import com.li64.tide.client.gui.JournalPage;
import com.li64.tide.data.journal.JournalLayout;
import com.li64.tide.data.player.TidePlayerData;
import com.li64.tide.network.messages.ShowToastMsg;
import com.li64.tide.network.messages.UpdateJournalMsg;
import com.li64.tide.registries.TideItems;
import com.li64.tide.util.TideUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class TideEventHandler {
    public static void onEnterNether(ServerPlayer player) {
        TidePlayerData data = TidePlayerData.getOrCreate(player);
        JournalPage netherPage = TideUtils.getPageByName("nether");
        if (netherPage == null) return;

        if (!data.hasPageUnlocked(netherPage)) {
            data.unlockPage(netherPage);
            Tide.NETWORK.sendToPlayer(new ShowToastMsg(
                            Component.translatable("newpage.toast.title"),
                            TideUtils.getPageToastDesc(netherPage),
                            TideUtils.getPageToastIcon(netherPage)),
                    player
            );
            data.syncTo(player);
        }
    }

    public static void onPlayerJoinWorld(ServerPlayer player) {
        if (!Tide.CONFIG.general.giveJournal) return;

        Tide.NETWORK.sendToPlayer(new UpdateJournalMsg(), player);

        TidePlayerData playerData = TidePlayerData.getOrCreate(player);
        playerData.syncTo(player);

        if (!playerData.gotJournal) {
            if (player.addItem(TideItems.FISHING_JOURNAL.getDefaultInstance())) {
                playerData.gotJournal = true;
                playerData.syncTo(player);
            }
        }
    }

    public static void updateFishingJournal(ServerPlayer player) {
        Inventory inventory = player.getInventory();
        for (int slot = 0; slot < inventory.getContainerSize(); slot++) {
            ItemStack stack = inventory.getItem(slot);

            if (TideUtils.isJournalFish(stack)) { // If the item exists in the journal
                TidePlayerData data = TidePlayerData.getOrCreate(player);
                if (!data.hasFishUnlocked(stack)) {
                    data.unlockFish(stack);
                    Tide.NETWORK.sendToPlayer(new ShowToastMsg(
                            Component.translatable("newfish.toast.title"),
                            TideUtils.removeRawTextInName(stack.getHoverName()),
                            stack.getItem().getDefaultInstance()), player);
                    data.syncTo(player);
                }

                List<JournalLayout.Page> pages = Tide.JOURNAL.getPageConfigs();
                pages.forEach(pageConfig -> {
                    // Go through each page and check for matching fish
                    JournalPage page = new JournalPage(pageConfig);
                    if (TideUtils.isInPage(page.idName(), stack)) {
                        TideUtils.unlockPage(player, page);
                        TideUtils.checkPageCompletion(data, page, player);
                    }
                });
            }
        }
    }
}
