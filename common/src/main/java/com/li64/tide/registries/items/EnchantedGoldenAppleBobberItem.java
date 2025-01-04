package com.li64.tide.registries.items;

import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class EnchantedGoldenAppleBobberItem extends FishingBobberItem {
    public EnchantedGoldenAppleBobberItem(Properties properties) {
        super(properties);
    }

    @Override
    public boolean isFoil(@NotNull ItemStack stack) {
        return true;
    }
}
