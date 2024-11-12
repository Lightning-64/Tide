package com.li64.tide.data.rods;

import com.li64.tide.Tide;
import net.minecraft.nbt.CompoundTag;
import com.li64.tide.registries.TideItems;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class CustomRodManager {
    private static final Item DEFAULT_BOBBER = TideItems.RED_FISHING_BOBBER;
    private static final Item DEFAULT_HOOK = TideItems.FISHING_HOOK;
    private static final Item DEFAULT_LINE = TideItems.FISHING_LINE;

    private static void createModifierTag(ItemStack stack) {
        CompoundTag modifierTag = new CompoundTag();

        modifierTag.put("bobber", new CompoundTag());
        modifierTag.put("hook", new CompoundTag());
        modifierTag.put("line", new CompoundTag());

        if (!stack.hasTag()) stack.setTag(new CompoundTag());
        stack.getTag().put("modifier", modifierTag);
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
        Tide.LOG.info("Saved accessory tag: {}", tag);
        getModifierTag(stack).put(modifier.getID(), tag);
    }

    public static boolean hasModifierTag(ItemStack stack) {
        return stack.hasTag() && stack.getTag().contains("modifier");
    }

    public static CompoundTag getModifierTag(ItemStack stack) {
        updateModifiers(stack);
        return (CompoundTag) stack.getTag().get("modifier");
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
}