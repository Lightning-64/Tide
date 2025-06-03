package com.li64.tide.data.rods;

import com.google.common.collect.ImmutableList;
import com.li64.tide.data.TideDataComponents;
import com.li64.tide.registries.TideItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;

import java.util.List;
import java.util.Optional;

public class CustomRodManager {
    private static final ItemStack DEFAULT_BOBBER = TideItems.RED_FISHING_BOBBER.getDefaultInstance();
    private static final ItemStack DEFAULT_HOOK = TideItems.FISHING_HOOK.getDefaultInstance();
    private static final ItemStack DEFAULT_LINE = TideItems.FISHING_LINE.getDefaultInstance();

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

    private static ItemStack getAccessory(DataComponentType<CompoundTag> componentType, ItemStack rod, HolderLookup.Provider registryAccess, ItemStack defaultItem) {
        CompoundTag data = rod.get(componentType);
        if (data == null || data.isEmpty()) return defaultItem;
        Optional<ItemStack> hook = ItemStack.parse(registryAccess, data);
        return hook.orElse(defaultItem);
    }

    private static ItemStack getAccessory(DataComponentType<CompoundTag> componentType, ItemStack rod, HolderLookup.Provider registryAccess) {
        return getAccessory(componentType, rod, registryAccess, null);
    }

    public static List<ItemStack> getAccessoryList(ItemStack stack, HolderLookup.Provider registryAccess) {
        ImmutableList.Builder<ItemStack> builder = ImmutableList.builder();
        if (hasBobber(stack, registryAccess)) builder.add(getBobber(stack, registryAccess));
        if (hasHook(stack, registryAccess)) builder.add(getHook(stack, registryAccess));
        if (hasLine(stack, registryAccess)) builder.add(getLine(stack, registryAccess));
        return builder.build();
    }
}
