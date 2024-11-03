package com.li64.tide.data.rods;

import com.li64.tide.Tide;
import com.li64.tide.registries.TideItems;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

/**
 * TideAccessoryData holds component data about a bobber, hook, or line modifier that can
 * be applied to a fishing rod.
 * @param translationKey What the text below the fishing rod should say to describe the
 *                    accessory. For example, putting Component.translatable("bobber.tide.red")
 *                    here would display "Red Bobber."
 * @param entityModifier For bobbers and hooks, this is the path to the texture that will be
 *                       displayed on the fishing bobber entity (such as "textures/item/white_fishing_bobber.png").
 *                       For fishing lines, this is used as the field to put the color hex
 *                       value of the line (such as "#362c1e")
 */
public record TideAccessoryData(String translationKey, String entityModifier) {
    public ResourceLocation getTextureLocation() {
        if (entityModifier.isEmpty()) return get(
                TideItems.RED_FISHING_BOBBER.getDefaultInstance()).getTextureLocation();
        return Tide.resource(entityModifier);
    }

    public static void set(ItemStack accessory, TideAccessoryData data) {
        if (accessory.getTag() == null || !accessory.hasTag()) accessory.setTag(new CompoundTag());

        accessory.getTag().putString("translationKey", data.translationKey);
        accessory.getTag().putString("entityModifier", data.entityModifier);
    }

    public static TideAccessoryData get(ItemStack accessory) {
        if (accessory.getTag() == null || !accessory.hasTag())
            set(accessory, get(TideItems.RED_FISHING_BOBBER.getDefaultInstance()));

        return new TideAccessoryData(accessory.getTag().getString("translationKey"),
                accessory.getTag().getString("entityModifier"));
    }
}
