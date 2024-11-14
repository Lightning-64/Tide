package com.li64.tide.data.rods;

import com.li64.tide.Tide;
import com.li64.tide.data.TideDataComponents;
import com.li64.tide.registries.TideItems;
import com.li64.tide.registries.items.FishingBobberItem;
import com.li64.tide.registries.items.FishingHookItem;
import com.li64.tide.registries.items.FishingLineItem;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.lang.reflect.Method;
import java.util.Optional;

public class CustomRodManager {
    private static final FishingBobberItem DEFAULT_BOBBER = (FishingBobberItem) TideItems.RED_FISHING_BOBBER;
    private static final FishingHookItem DEFAULT_HOOK = (FishingHookItem) TideItems.FISHING_HOOK;
    private static final FishingLineItem DEFAULT_LINE = (FishingLineItem) TideItems.FISHING_LINE;

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

    public static ResourceLocation getBobberTexture(ItemStack bobber) {
        return getAccessoryAttribute(bobber, "getTextureLocation", DEFAULT_BOBBER.getTextureLocation());
    }

    public static ResourceLocation getHookTexture(ItemStack hook) {
        return getAccessoryAttribute(hook, "getTextureLocation", DEFAULT_HOOK.getTextureLocation());
    }

    public static String getLineColor(ItemStack line) {
        return getAccessoryAttribute(line, "getColor", DEFAULT_LINE.getColor());
    }

    public static MutableComponent getTranslation(ItemStack accessory) {
        return getAccessoryAttribute(accessory, "getTranslation", Component.literal(""));
    }

    @SuppressWarnings("unchecked")
    private static <T> T getAccessoryAttribute(ItemStack accessory, String getterName, T defaultValue) {
        try {
            Method method = accessory.getItem().getClass().getMethod(getterName);
            return (T) method.invoke(accessory.getItem());
        } catch (Exception e) {
            Tide.LOG.error("Fishing rod accessory class \"{}\" does not have a \"{}\" method defined!", accessory.getItem().getClass(), getterName);
            return defaultValue;
        }
    }
}
