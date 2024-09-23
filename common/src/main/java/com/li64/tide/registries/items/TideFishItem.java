package com.li64.tide.registries.items;

import net.minecraft.world.item.Item;

public class TideFishItem extends Item implements StrengthFish {
    private final float strength;
    private final boolean isCookable;

    public TideFishItem(Properties properties, float strength) {
        this(properties, strength, false);
    }

    public TideFishItem(Properties properties, float strength, boolean isCookable) {
        super(properties);
        this.strength = strength;
        this.isCookable = isCookable;
    }

    public float getStrength() {
        return this.strength;
    }

    public boolean isCookable() {
        return this.isCookable;
    }
}