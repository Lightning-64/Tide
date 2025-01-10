package com.li64.tide.registries.items;

import com.li64.tide.Tide;
import com.li64.tide.data.rods.AccessoryData;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class FishingBobberItem extends Item {
    public FishingBobberItem(Properties properties) {
        super(properties.stacksTo(1));
    }

    public static ResourceLocation getTexture(ItemStack stack) {
        return Tide.resource("textures/item/" + BuiltInRegistries.ITEM.getKey(stack.getItem()).getPath() + ".png");
    }

    public static boolean renderItemModel(ItemStack stack) {
        AccessoryData data = AccessoryData.get(stack);
        return (data == null || data.renderItem().isEmpty() || data.renderItem().get());
    }
}
