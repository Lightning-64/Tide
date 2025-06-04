package com.li64.tide.registries.items;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class BlazingSwordfishItem extends Item implements StrengthFish {
    public BlazingSwordfishItem(Item.Properties properties) {
        super(properties);
    }

    @Override
    public void postHurtEnemy(@NotNull ItemStack stack, LivingEntity target, @NotNull LivingEntity attacker) {
        target.igniteForTicks(80);
    }

    public float getStrength() {
        return 6.4f;
    }
}
