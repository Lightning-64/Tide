package com.li64.tide.registries.items;

public interface StrengthFish {
    float strength = 0;

    /** Range: 0-8 */
    default float getStrength() {
        return strength;
    }
}
