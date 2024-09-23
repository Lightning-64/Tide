package com.li64.tide.data.rods;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.Arrays;

public class CustomRodManager {

    private static final BobberModifier DEFAULT_BOBBER = BobberModifier.RED;
    private static final HookModifier DEFAULT_HOOK = HookModifier.NORMAL;
    private static final LineModifier DEFAULT_LINE = LineModifier.NORMAL;

    private static void createModifierTag(ItemStack stack) {
        CompoundTag modifierTag = new CompoundTag();

        modifierTag.putInt("bobber", DEFAULT_BOBBER.ordinal());
        modifierTag.putInt("hook", DEFAULT_HOOK.ordinal());
        modifierTag.putInt("line", DEFAULT_LINE.ordinal());

        if (!stack.hasTag()) stack.setTag(new CompoundTag());
        stack.getTag().put("modifier", modifierTag);
    }

    private static void updateModifiers(ItemStack stack) {
        if (!hasModifierTag(stack)) createModifierTag(stack);
    }

    public static void setBobber(ItemStack stack, Item bobber) {
        setModifier(stack, ModifierType.BOBBER, Arrays.stream(BobberModifier.values())
                .filter(modifier -> bobber == modifier.getItem())
                .findFirst().orElse(DEFAULT_BOBBER).ordinal());
    }

    public static void setHook(ItemStack stack, Item hook) {
        setModifier(stack, ModifierType.HOOK, Arrays.stream(HookModifier.values())
                .filter(modifier -> hook == modifier.getItem())
                .findFirst().orElse(DEFAULT_HOOK).ordinal());
    }

    public static void setLine(ItemStack stack, Item line) {
        setModifier(stack, ModifierType.LINE, Arrays.stream(LineModifier.values())
                .filter(modifier -> line == modifier.getItem())
                .findFirst().orElse(DEFAULT_LINE).ordinal());
    }

    public static void setModifier(ItemStack stack, ModifierType modifier, int value) {
        updateModifiers(stack);
        getModifierTag(stack).putInt(modifier.getID(), value);
    }

    public static boolean hasModifierTag(ItemStack stack) {
        return stack.hasTag() && stack.getTag().contains("modifier");
    }

    public static CompoundTag getModifierTag(ItemStack stack) {
        updateModifiers(stack);
        return (CompoundTag) stack.getTag().get("modifier");
    }

    public static BobberModifier getBobber(ItemStack stack) {
        updateModifiers(stack);
        return BobberModifier.values()[getModifierTag(stack).getInt("bobber")];
    }

    public static HookModifier getHook(ItemStack stack) {
        updateModifiers(stack);
        return HookModifier.values()[getModifierTag(stack).getInt("hook")];
    }

    public static LineModifier getLine(ItemStack stack) {
        updateModifiers(stack);
        return LineModifier.values()[getModifierTag(stack).getInt("line")];
    }
}
