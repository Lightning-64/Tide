package com.li64.tide.data.rods;

import com.li64.tide.Tide;
import com.li64.tide.registries.items.FishingBobberItem;
import com.li64.tide.registries.items.FishingHookItem;
import com.li64.tide.registries.items.FishingLineItem;
import net.minecraft.nbt.CompoundTag;
import com.li64.tide.registries.TideItems;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

import java.lang.reflect.Method;

public class CustomRodManager {
    private static final FishingBobberItem DEFAULT_BOBBER = (FishingBobberItem) TideItems.RED_FISHING_BOBBER;
    private static final FishingHookItem DEFAULT_HOOK = (FishingHookItem) TideItems.FISHING_HOOK;
    private static final FishingLineItem DEFAULT_LINE = (FishingLineItem) TideItems.FISHING_LINE;

    private static void createModifierTag(ItemStack stack) {
        CompoundTag modifierTag = new CompoundTag();

        modifierTag.put("bobber", new CompoundTag());
        modifierTag.put("hook", new CompoundTag());
        modifierTag.put("line", new CompoundTag());

        if (!stack.hasTag()) stack.setTag(new CompoundTag());
        stack.getOrCreateTag().put("modifier", modifierTag);
    }

    private static void updateModifiers(ItemStack stack) {
        if (!hasModifierTag(stack)) createModifierTag(stack);
    }

    public static void setBobber(ItemStack stack, ItemStack bobber) {
        setModifier(stack, ModifierType.BOBBER, bobber);
    }

    public static void setHook(ItemStack stack, ItemStack hook) {
        setModifier(stack, ModifierType.HOOK, hook);
    }

    public static void setLine(ItemStack stack, ItemStack line) {
        setModifier(stack, ModifierType.LINE, line);
    }

    public static void setModifier(ItemStack stack, ModifierType modifier, ItemStack accessory) {
        updateModifiers(stack);
        if (accessory == null) {
            getModifierTag(stack).put(modifier.getID(), new CompoundTag());
            return;
        }
        CompoundTag tag = accessory.save(new CompoundTag());
        getModifierTag(stack).put(modifier.getID(), tag);
    }

    public static boolean hasModifierTag(ItemStack stack) {
        return stack.hasTag() && stack.getOrCreateTag().contains("modifier");
    }

    public static CompoundTag getModifierTag(ItemStack stack) {
        updateModifiers(stack);
        return (CompoundTag) stack.getOrCreateTag().get("modifier");
    }

    public static ItemStack getBobber(ItemStack rod) {
        return getAccessory(rod, ModifierType.BOBBER, DEFAULT_BOBBER.getDefaultInstance());
    }

    public static ItemStack getHook(ItemStack rod) {
        return getAccessory(rod, ModifierType.HOOK, DEFAULT_HOOK.getDefaultInstance());
    }

    public static ItemStack getLine(ItemStack rod) {
        return getAccessory(rod, ModifierType.LINE, DEFAULT_LINE.getDefaultInstance());
    }

    public static boolean hasBobber(ItemStack rod) {
        return getAccessory(rod, ModifierType.BOBBER) != null;
    }

    public static boolean hasHook(ItemStack rod) {
        return getAccessory(rod, ModifierType.HOOK) != null;
    }

    public static boolean hasLine(ItemStack rod) {
        return getAccessory(rod, ModifierType.LINE) != null;
    }

    private static ItemStack getAccessory(ItemStack rod, ModifierType type) {
        return getAccessory(rod, type, null);
    }

    private static ItemStack getAccessory(ItemStack rod, ModifierType type, ItemStack defaultInstance) {
        updateModifiers(rod);
        CompoundTag accessoryTag = getModifierTag(rod).getCompound(type.getID());

        if (accessoryTag.isEmpty()) return defaultInstance;
        ItemStack accessory = ItemStack.of(accessoryTag);
        return accessory.isEmpty() ? defaultInstance : accessory;
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