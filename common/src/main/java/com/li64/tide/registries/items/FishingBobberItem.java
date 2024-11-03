package com.li64.tide.registries.items;

import com.li64.tide.data.rods.TideAccessoryData;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class FishingBobberItem extends Item {
    private final TideAccessoryData data;

    public FishingBobberItem(String textureLocation, String translation, Properties properties) {
        super(properties);
        this.data = new TideAccessoryData(translation, textureLocation);
    }

    // IMPORTANT: Accessory data MUST be applied here!
    @Override
    public @NotNull ItemStack getDefaultInstance() {
        ItemStack instance = super.getDefaultInstance();
        TideAccessoryData.set(instance, data);
        return instance;
    }
}
