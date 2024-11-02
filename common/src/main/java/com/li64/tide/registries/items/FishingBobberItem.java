package com.li64.tide.registries.items;

import com.li64.tide.data.TideDataComponents;
import com.li64.tide.data.rods.TideAccessoryData;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;

public class FishingBobberItem extends Item {
    public FishingBobberItem(String textureLocation, Component translation, Properties properties) {
        super(properties.component(TideDataComponents.TIDE_ACCESSORY_DATA,
                new TideAccessoryData(translation, textureLocation)));
    }
}
