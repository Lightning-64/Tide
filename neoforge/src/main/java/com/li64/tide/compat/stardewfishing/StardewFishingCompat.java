package com.li64.tide.compat.stardewfishing;

import com.bonker.stardewfishing.SFConfig;
import com.bonker.stardewfishing.StardewFishing;
import com.bonker.stardewfishing.common.FishingHookLogic;
import com.bonker.stardewfishing.common.init.SFAttachmentTypes;
import com.bonker.stardewfishing.common.init.SFSoundEvents;
import com.li64.tide.registries.entities.misc.fishing.HookAccessor;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class StardewFishingCompat {
    public static boolean start(ServerPlayer player, HookAccessor hook, ItemStack rod, List<ItemStack> items) {
        if (items.stream().anyMatch(stack -> stack.is(StardewFishing.STARTS_MINIGAME))) {
            hook.getData(SFAttachmentTypes.HOOK).getRewards().addAll(items);
            return FishingHookLogic.startStardewMinigame(player);
        } else {
            FishingHookLogic.modifyRewards(items, 0, 0);
            player.level().playSound(null, player, SFSoundEvents.PULL_ITEM.get(), SoundSource.PLAYERS, 1.0F, 1.0F);
            return false;
        }
    }

    public static List<ItemStack> getRewards(HookAccessor hook) {
        return hook.getData(SFAttachmentTypes.HOOK).getRewards();
    }

    public static double getBiteTimeMultiplier() {
        return SFConfig.getBiteTimeMultiplier();
    }
}