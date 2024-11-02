package com.li64.tide.data.rods;

import com.li64.tide.Tide;
import com.li64.tide.data.TideDataComponents;
import com.li64.tide.registries.TideItems;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.Arrays;

public class CustomRodManager {
    public static void setBobber(ItemStack stack, ItemStack bobber) {
        if (bobber == null || bobber.isEmpty()) return;
        stack.set(TideDataComponents.FISHING_BOBBER, BuiltInRegistries.ITEM.getKey(bobber.getItem()).toString());
    }

    public static void setHook(ItemStack stack, ItemStack hook) {
        if (hook == null || hook.isEmpty()) return;
        stack.set(TideDataComponents.FISHING_HOOK, BuiltInRegistries.ITEM.getKey(hook.getItem()).toString());
    }

    public static void setLine(ItemStack stack, ItemStack line) {
        if (line == null || line.isEmpty()) return;
        stack.set(TideDataComponents.FISHING_LINE, BuiltInRegistries.ITEM.getKey(line.getItem()).toString());
    }

    public static ItemStack getBobber(ItemStack stack) {
        ItemStack defaultInstance = TideItems.RED_FISHING_BOBBER.getDefaultInstance();
        String bobberKey = stack.get(TideDataComponents.FISHING_BOBBER);

        if (bobberKey == null || bobberKey.isEmpty() ||
            !BuiltInRegistries.ITEM.containsKey(ResourceLocation.parse(bobberKey))) return defaultInstance;

        return BuiltInRegistries.ITEM.get(ResourceLocation.parse(bobberKey)).getDefaultInstance();
    }

    public static ItemStack getHook(ItemStack stack) {
        ItemStack defaultInstance = TideItems.FISHING_HOOK.getDefaultInstance();
        String hookKey = stack.get(TideDataComponents.FISHING_HOOK);

        if (hookKey == null || hookKey.isEmpty() ||
                !BuiltInRegistries.ITEM.containsKey(ResourceLocation.parse(hookKey))) return defaultInstance;

        return BuiltInRegistries.ITEM.get(ResourceLocation.parse(hookKey)).getDefaultInstance();
    }

    public static ItemStack getLine(ItemStack stack) {
        ItemStack defaultInstance = TideItems.FISHING_LINE.getDefaultInstance();
        String lineKey = stack.get(TideDataComponents.FISHING_LINE);

        if (lineKey == null || lineKey.isEmpty() ||
                !BuiltInRegistries.ITEM.containsKey(ResourceLocation.parse(lineKey))) return defaultInstance;

        return BuiltInRegistries.ITEM.get(ResourceLocation.parse(lineKey)).getDefaultInstance();
    }

    public static String getLineColor(ItemStack line) {
        TideAccessoryData data = line.get(TideDataComponents.TIDE_ACCESSORY_DATA);
        if (data == null) return "#d6d6d6";
        return data.entityModifier();
    }

    public static ResourceLocation getTextureLocation(ItemStack hook) {
        TideAccessoryData data = hook.get(TideDataComponents.TIDE_ACCESSORY_DATA);
        if (data == null) return TideItems.RED_FISHING_BOBBER.getDefaultInstance().get(TideDataComponents.TIDE_ACCESSORY_DATA).getTextureLocation();
        return data.getTextureLocation();
    }
}
