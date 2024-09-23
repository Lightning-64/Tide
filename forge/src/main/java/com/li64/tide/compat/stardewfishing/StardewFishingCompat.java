package com.li64.tide.compat.stardewfishing;

import com.bonker.stardewfishing.StardewFishing;
import com.bonker.stardewfishing.common.FishingHookLogic;
import com.li64.tide.Tide;
import com.li64.tide.registries.entities.misc.fishing.HookAccessor;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.AttachCapabilitiesEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class StardewFishingCompat {
    public static boolean start(ServerPlayer player, HookAccessor hook, ItemStack rod, List<ItemStack> items) {
        if (items.stream().anyMatch(stack -> stack.is(StardewFishing.STARTS_MINIGAME))) {
            FishingHookLogic.getStoredRewards(hook).ifPresent(rewards -> rewards.addAll(items));
            FishingHookLogic.startMinigame(player);
            return true;
        } else {
            FishingHookLogic.modifyRewards(items, 0);
            player.level().playSound(null, player, StardewFishing.PULL_ITEM.get(), SoundSource.PLAYERS, 1.0F, 1.0F);
            return false;
        }
    }

    public static Optional<ArrayList<ItemStack>> getRewards(HookAccessor hook) {
        return FishingHookLogic.getStoredRewards(hook);
    }
}
