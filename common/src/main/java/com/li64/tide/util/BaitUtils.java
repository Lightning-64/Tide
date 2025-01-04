package com.li64.tide.util;

import com.google.common.collect.ImmutableList;
import com.li64.tide.Tide;
import com.li64.tide.data.TideDataComponents;
import com.li64.tide.data.rods.BaitContents;
import com.li64.tide.data.rods.BaitData;
import com.li64.tide.registries.TideItems;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BaitUtils {
    public static boolean isHoldingBait(ItemStack rod) {
        return isBait(getPrimaryBait(rod));
    }

    public static ItemStack getPrimaryBait(ItemStack rod) {
        BaitContents contents = rod.get(TideDataComponents.BAIT_CONTENTS);
        if (contents == null || contents.items() == null) return Items.AIR.getDefaultInstance();
        for (int i = 0; i < contents.size(); i++) {
            ItemStack stack = contents.get(i);
            if (BaitUtils.isBait(stack)) return stack;
        }
        return Items.AIR.getDefaultInstance();
    }

    public static boolean isBait(ItemStack stack) {
        return Tide.BAIT_DATA.get().stream().anyMatch(baitData -> stack.is(baitData.getItem()));
    }

    public static Optional<BaitData> getBaitData(ItemStack stack) {
        return Tide.BAIT_DATA.get().stream()
                .filter(baitData -> stack.is(baitData.getItem()))
                .findFirst();
    }

    public static int getBaitSpeed(ItemStack stack) {
        return getBaitData(stack).map(BaitData::speedBonus).orElse(0);
    }

    public static int getBaitLuck(ItemStack stack) {
        return getBaitData(stack).map(BaitData::luckBonus).orElse(0);
    }

    public static int getCrateChance(ItemStack stack) {
        return stack.is(TideItems.MAGNETIC_BAIT) ? 25 : 0;
    }

    public static List<Component> getDescriptionLines(ItemStack bait) {
        ArrayList<Component> builder = new ArrayList<>();
        if (!isBait(bait)) return ImmutableList.copyOf(builder);

        int speed = getBaitSpeed(bait);
        int luck = getBaitLuck(bait);
        int crateChance = getCrateChance(bait);

        if (speed != 0)
            builder.add(Component.translatable("text.tide.bait_tooltip.speed",
                    (speed < 0 ? "-" : "+") + speed).withStyle(ChatFormatting.BLUE));
        if (luck != 0)
            builder.add(Component.translatable("text.tide.bait_tooltip.lucky",
                    (luck < 0 ? "-" : "+") + luck).withStyle(ChatFormatting.BLUE));
        if (crateChance != 0)
            builder.add(Component.translatable("text.tide.bait_tooltip.crate",
                    (crateChance < 0 ? "-" : "+") + crateChance + "%").withStyle(ChatFormatting.BLUE));

        if (!builder.isEmpty()) builder.addFirst(Component.translatable("text.tide.bait_tooltip.prefix").withStyle(ChatFormatting.GRAY));
        else builder.addFirst(Component.translatable("text.tide.bait_tooltip.unknown_effects").withStyle(ChatFormatting.GRAY));

        // new line should be placed before text
        builder.addFirst(Component.empty());

        return ImmutableList.copyOf(builder);
    }
}
