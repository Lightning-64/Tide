package com.li64.tide.registries.items;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.ToolMaterial;
import org.jetbrains.annotations.NotNull;

public class BlazingSwordfishItem extends SwordItem implements StrengthFish {
    public BlazingSwordfishItem(Properties properties) {
        super(ToolMaterial.IRON, 3.0f, -2.4f, properties);
    }

    @Override
    public boolean hurtEnemy(@NotNull ItemStack stack, @NotNull LivingEntity target, @NotNull LivingEntity attacker) {
        boolean result = super.hurtEnemy(stack, target, attacker);
        if (result) target.igniteForTicks(80);
        return result;
    }

    public float getStrength() {
        return 6.4f;
    }
}
