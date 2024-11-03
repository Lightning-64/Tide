package com.li64.tide.data.rods;

import net.minecraft.nbt.CompoundTag;
import com.li64.tide.registries.TideItems;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class CustomRodManager {
    private static final ItemStack DEFAULT_BOBBER = TideItems.RED_FISHING_BOBBER.getDefaultInstance();
    private static final ItemStack DEFAULT_HOOK = TideItems.FISHING_HOOK.getDefaultInstance();
    private static final ItemStack DEFAULT_LINE = TideItems.FISHING_LINE.getDefaultInstance();

    private static void createModifierTag(ItemStack stack) {
        CompoundTag modifierTag = new CompoundTag();

        modifierTag.putString("bobber", BuiltInRegistries.ITEM.getKey(DEFAULT_BOBBER.getItem()).toString());
        modifierTag.putString("hook", BuiltInRegistries.ITEM.getKey(DEFAULT_HOOK.getItem()).toString());
        modifierTag.putString("line", BuiltInRegistries.ITEM.getKey(DEFAULT_LINE.getItem()).toString());

        if (!stack.hasTag()) stack.setTag(new CompoundTag());
        stack.getTag().put("modifier", modifierTag);
    }

    private static void updateModifiers(ItemStack stack) {
        if (!hasModifierTag(stack)) createModifierTag(stack);
    }

    public static void setBobber(ItemStack stack, ItemStack bobber) {
        setModifier(stack, ModifierType.BOBBER, bobber.getItem());
    }

    public static void setHook(ItemStack stack, ItemStack hook) {
        setModifier(stack, ModifierType.HOOK, hook.getItem());
    }

    public static void setLine(ItemStack stack, ItemStack line) {
        setModifier(stack, ModifierType.LINE, line.getItem());
    }

    public static void setModifier(ItemStack stack, ModifierType modifier, Item value) {
        updateModifiers(stack);
        getModifierTag(stack).putString(modifier.getID(), BuiltInRegistries.ITEM.getKey(value).toString());
    }

    public static boolean hasModifierTag(ItemStack stack) {
        return stack.hasTag() && stack.getTag().contains("modifier");
    }

    public static CompoundTag getModifierTag(ItemStack stack) {
        updateModifiers(stack);
        return (CompoundTag) stack.getTag().get("modifier");
    }

    public static ItemStack getBobber(ItemStack stack) {
        updateModifiers(stack);
        String bobberKey = getModifierTag(stack).getString("bobber");

        if (bobberKey.isEmpty() || !BuiltInRegistries.ITEM.containsKey(new ResourceLocation(bobberKey))) return DEFAULT_BOBBER;
        return BuiltInRegistries.ITEM.get(new ResourceLocation(bobberKey)).getDefaultInstance();
    }

    public static ItemStack getHook(ItemStack stack) {
        updateModifiers(stack);
        String hookKey = getModifierTag(stack).getString("hook");

        if (hookKey.isEmpty() || !BuiltInRegistries.ITEM.containsKey(new ResourceLocation(hookKey))) return DEFAULT_HOOK;
        return BuiltInRegistries.ITEM.get(new ResourceLocation(hookKey)).getDefaultInstance();
    }

    public static ItemStack getLine(ItemStack stack) {
        updateModifiers(stack);
        String lineKey = getModifierTag(stack).getString("line");

        if (lineKey.isEmpty() || !BuiltInRegistries.ITEM.containsKey(new ResourceLocation(lineKey))) return DEFAULT_LINE;
        return BuiltInRegistries.ITEM.get(new ResourceLocation(lineKey)).getDefaultInstance();
    }

    public static String getLineColor(ItemStack line) {
        TideAccessoryData data = TideAccessoryData.get(line);
        return data.entityModifier();
    }

    public static ResourceLocation getTextureLocation(ItemStack hook) {
        TideAccessoryData data = TideAccessoryData.get(hook);
        return data.getTextureLocation();
    }
}
