package com.li64.tide.data.rods;

import com.li64.tide.data.TideDataComponents;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.Arrays;

public class CustomRodManager {
    public static void setBobber(ItemStack stack, Item bobber) {
        stack.set(TideDataComponents.FISHING_BOBBER, Arrays.stream(BobberModifier.values())
                .filter(modifier -> bobber == modifier.item)
                .findFirst().orElse(BobberModifier.DEFAULT).ordinal());
    }

    public static void setBobber(ItemStack stack, int bobber) {
        stack.set(TideDataComponents.FISHING_BOBBER, bobber);
    }

    public static void setHook(ItemStack stack, Item hook) {
        stack.set(TideDataComponents.FISHING_HOOK, Arrays.stream(HookModifier.values())
                .filter(modifier -> hook == modifier.item)
                .findFirst().orElse(HookModifier.DEFAULT).ordinal());
    }

    public static void setHook(ItemStack stack, int hook) {
        stack.set(TideDataComponents.FISHING_HOOK, hook);
    }

    public static void setLine(ItemStack stack, Item line) {
        stack.set(TideDataComponents.FISHING_LINE, Arrays.stream(LineModifier.values())
                .filter(modifier -> line == modifier.item)
                .findFirst().orElse(LineModifier.DEFAULT).ordinal());
    }

    public static void setLine(ItemStack stack, int line) {
        stack.set(TideDataComponents.FISHING_LINE, line);
    }

    public static BobberModifier getBobber(ItemStack stack) {
        Integer value = stack.get(TideDataComponents.FISHING_BOBBER);
        if (value == null) return BobberModifier.DEFAULT;
        return BobberModifier.values()[value];
    }

    public static HookModifier getHook(ItemStack stack) {
        Integer value = stack.get(TideDataComponents.FISHING_HOOK);
        if (value == null) return HookModifier.DEFAULT;
        return HookModifier.values()[value];
    }

    public static LineModifier getLine(ItemStack stack) {
        Integer value = stack.get(TideDataComponents.FISHING_LINE);
        if (value == null) return LineModifier.DEFAULT;
        return LineModifier.values()[value];
    }
}
