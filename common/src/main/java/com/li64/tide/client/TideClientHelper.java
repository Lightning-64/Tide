package com.li64.tide.client;

import com.li64.tide.Tide;
import com.li64.tide.client.gui.TideToasts;
import com.li64.tide.client.gui.screens.FishingJournalScreen;
import com.li64.tide.config.TideConfig;
import com.li64.tide.util.TideUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class TideClientHelper {
    public static void openJournalScreen() {
        openJournalScreen(null);
    }

    public static void openJournalScreen(Item turnTo) {
        if (Minecraft.getInstance().player == null) return;
        FishingJournalScreen screen = new FishingJournalScreen(Minecraft.getInstance().player);
        if (turnTo != null) screen.openProfile(TideUtils.getProfileFromItem(turnTo.getDefaultInstance()));
        Minecraft.getInstance().setScreen(screen);
    }

    public static void showToast(Component title, Component description, ItemStack display) {
        if (Tide.CONFIG.general.showToasts)
            TideToasts.display(new TideToasts.NewPageToast(title, description, display));
    }
}
