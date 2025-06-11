package com.li64.tide.compat.jobsaddon;

import net.jobsaddon.jobs.JobHelper;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class JobsAddonCompat {
    public static void dropJobXp(Player player, List<ItemStack> catches) {
        if (player == null || catches.isEmpty()) return;
        JobHelper.addFisherXp(player, catches.get(0));
    }
}
