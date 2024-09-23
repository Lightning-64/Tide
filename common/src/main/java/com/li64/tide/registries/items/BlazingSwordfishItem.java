package com.li64.tide.registries.items;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;

public class BlazingSwordfishItem extends SwordItem implements StrengthFish {
    public BlazingSwordfishItem(Tier tier, Properties properties) {
        super(tier, properties);
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        boolean result = super.hurtEnemy(stack, target, attacker);
        if (result) target.igniteForTicks(80);
        return result;
    }

    public float getStrength() {
        return 6.4f;
    }
}
