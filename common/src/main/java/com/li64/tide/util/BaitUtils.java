package com.li64.tide.util;

import com.li64.tide.data.TideDataComponents;
import com.li64.tide.data.rods.BaitContents;
import com.li64.tide.registries.items.BaitItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

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


    public static void consumeBait(ItemStack rod) {
        BaitContents contents = rod.get(TideDataComponents.BAIT_CONTENTS);
        if (contents == null || contents.items() == null) return;
        for (int i = 0; i < contents.size(); i++) {
            if (BaitUtils.isBait(contents.get(i))) {
                contents.get(i).shrink(1);
            }
        }
    }

    public static boolean isBait(ItemStack stack) {
        if (stack.getDescriptionId().equals("item.fishofthieves.earthworms")) return true;
        if (stack.getDescriptionId().equals("item.fishofthieves.grubs")) return true;
        if (stack.getDescriptionId().equals("item.fishofthieves.leeches")) return true;
        return stack.getItem() instanceof BaitItem;
    }

    public static int getBaitSpeed(ItemStack stack) {
        if (stack.getItem() instanceof BaitItem bait) return bait.getSpeedBonus();
        if (stack.getDescriptionId().equals("item.fishofthieves.earthworms")) return 2;
        if (stack.getDescriptionId().equals("item.fishofthieves.grubs")) return 2;
        if (stack.getDescriptionId().equals("item.fishofthieves.leeches")) return 2;
        else return 0;
    }

    public static int getBaitLuck(ItemStack stack) {
        if (stack.getItem() instanceof BaitItem bait) return bait.getLuckBonus();
        else return 0;
    }
}
