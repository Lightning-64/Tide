package com.li64.tide.registries.items;

import com.li64.tide.Tide;
import com.li64.tide.data.player.TidePlayerData;
import com.li64.tide.network.messages.OpenJournalMsg;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class FishingJournalItem extends Item {
    public FishingJournalItem(Properties properties) {
        super(properties);
    }

    public @NotNull InteractionResult use(@NotNull Level level, Player player, @NotNull InteractionHand hand) {
        ItemStack itemStack = player.getItemInHand(hand);

        if (player instanceof ServerPlayer serverPlayer) {
            CriteriaTriggers.CONSUME_ITEM.trigger(serverPlayer, itemStack);
            serverPlayer.awardStat(Stats.ITEM_USED.get(this));

            TidePlayerData.getOrCreate(serverPlayer).syncTo(serverPlayer);
            Tide.NETWORK.sendToPlayer(new OpenJournalMsg(), serverPlayer);
        }
        return InteractionResult.SUCCESS;
    }
}
