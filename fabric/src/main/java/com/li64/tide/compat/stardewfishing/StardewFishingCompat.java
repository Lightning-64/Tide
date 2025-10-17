package com.li64.tide.compat.stardewfishing;

import com.kltyton.stardewfishingFabric.StardewfishingFabric;
import com.kltyton.stardewfishingFabric.common.FishingDataStorage;
import com.kltyton.stardewfishingFabric.common.FishingHookLogic;
import com.li64.tide.Tide;
import com.li64.tide.registries.entities.misc.fishing.HookAccessor;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.item.ItemStack;

import java.util.List;
import java.util.Optional;

public class StardewFishingCompat {
    public static boolean start(ServerPlayer player, HookAccessor hook, ItemStack rod, List<ItemStack> items) {
        Optional<ItemStack> minigameStack = items.stream()
                .filter(stack -> stack.is(StardewfishingFabric.STARTS_MINIGAME))
                .findAny();
        if (minigameStack.isPresent()) {
            player.level().playSound(null, player, StardewfishingFabric.FISH_BITE, SoundSource.PLAYERS, 1.0f, 1.0f);
            FishingDataStorage.storeData(player, hook, minigameStack.get());
            FishingHookLogic.startMinigame(player, minigameStack.get());
            return true;
        }
        player.level().playSound(null, player, StardewfishingFabric.PULL_ITEM, SoundSource.PLAYERS, 1.0f, 1.0f);
        return false;
    }
}