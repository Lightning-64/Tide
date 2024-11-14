package com.li64.tide.data.rods;

import com.li64.tide.data.TideDataComponents;
import com.li64.tide.registries.TideItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.Optional;

public class CustomRodManager {
    public static final Item DEFAULT_HOOK = TideItems.FISHING_HOOK;
    public static final Item DEFAULT_BOBBER = TideItems.RED_FISHING_BOBBER;
    public static final Item DEFAULT_LINE = TideItems.FISHING_LINE;

    public static void setBobber(ItemStack rod, ItemStack bobber, HolderLookup.Provider registries) {
        setAccessory(TideDataComponents.FISHING_BOBBER, rod, bobber, registries);
    }

    public static void setHook(ItemStack rod, ItemStack hook, HolderLookup.Provider registries) {
        setAccessory(TideDataComponents.FISHING_HOOK, rod, hook, registries);
    }

    public static void setLine(ItemStack rod, ItemStack line, HolderLookup.Provider registries) {
        setAccessory(TideDataComponents.FISHING_LINE, rod, line, registries);
    }

    private static void setAccessory(DataComponentType<CompoundTag> componentType, ItemStack rod, ItemStack accessory, HolderLookup.Provider registries) {
        if (accessory == null || accessory.isEmpty()) {
            rod.set(componentType, new CompoundTag());
            return;
        }
        CompoundTag tag = (CompoundTag) accessory.save(registries, new CompoundTag());
        rod.set(componentType, tag);
    }

    public static ItemStack getBobber(ItemStack rod, HolderLookup.Provider registryAccess) {
        return getAccessory(TideDataComponents.FISHING_BOBBER, rod, registryAccess, DEFAULT_BOBBER);
    }

    public static ItemStack getHook(ItemStack rod, HolderLookup.Provider registryAccess) {
        return getAccessory(TideDataComponents.FISHING_HOOK, rod, registryAccess, DEFAULT_HOOK);
    }

    public static ItemStack getLine(ItemStack rod, HolderLookup.Provider registryAccess) {
        return getAccessory(TideDataComponents.FISHING_LINE, rod, registryAccess, DEFAULT_LINE);
    }

    public static boolean hasBobber(ItemStack rod, HolderLookup.Provider registryAccess) {
        return getAccessory(TideDataComponents.FISHING_BOBBER, rod, registryAccess) != null;
    }

    public static boolean hasHook(ItemStack rod, HolderLookup.Provider registryAccess) {
        return getAccessory(TideDataComponents.FISHING_HOOK, rod, registryAccess) != null;
    }

    public static boolean hasLine(ItemStack rod, HolderLookup.Provider registryAccess) {
        return getAccessory(TideDataComponents.FISHING_LINE, rod, registryAccess) != null;
    }

    private static ItemStack getAccessory(DataComponentType<CompoundTag> componentType, ItemStack rod, HolderLookup.Provider registryAccess, Item defaultItem) {
        CompoundTag data = rod.get(componentType);
        if (data == null) return defaultItem.getDefaultInstance();
        Optional<ItemStack> hook = ItemStack.parse(registryAccess, data);
        return hook.orElse(defaultItem.getDefaultInstance());
    }

    private static ItemStack getAccessory(DataComponentType<CompoundTag> componentType, ItemStack rod, HolderLookup.Provider registryAccess) {
        CompoundTag data = rod.get(componentType);
        if (data == null) return null;
        Optional<ItemStack> hook = ItemStack.parse(registryAccess, data);
        return hook.orElse(null);
    }
}
