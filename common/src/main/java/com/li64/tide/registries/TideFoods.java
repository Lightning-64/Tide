package com.li64.tide.registries;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Items;

public class TideFoods {
    public static FoodProperties AVERAGE_RAW_FISH = new FoodProperties.Builder()
            .nutrition(2).saturationMod(0.1F)
            .build();
    public static FoodProperties UNHEALTHY_RAW_FISH = new FoodProperties.Builder()
            .nutrition(1).saturationMod(0.1F)
            .effect(new MobEffectInstance(MobEffects.HUNGER, 400, 0), 0.9F)
            .build();
    public static FoodProperties FIERY_RAW_FISH = new FoodProperties.Builder()
            .nutrition(1).saturationMod(0.2F)
            .effect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 200, 0), 1.0F)
            .effect(new MobEffectInstance(MobEffects.GLOWING, 200, 0), 1.0F)
            .alwaysEat()
            .build();
    public static FoodProperties COOKED_FISH = new FoodProperties.Builder()
            .nutrition(6).saturationMod(0.7F)
            .build();
    public static FoodProperties FISH_STEW = new FoodProperties.Builder()
            .nutrition(20).saturationMod(2.0F)
            .build();
    public static FoodProperties GOLDEN_FISH = new FoodProperties.Builder()
            .nutrition(4).saturationMod(1.2F)
            .effect(new MobEffectInstance(MobEffects.REGENERATION, 90, 2), 1.0F)
            .effect(new MobEffectInstance(MobEffects.ABSORPTION, 1200, 0), 1.0F)
            .alwaysEat()
            .build();
    public static FoodProperties VOIDSEEKER = new FoodProperties.Builder()
            .nutrition(20).saturationMod(2.0F)
            .effect(new MobEffectInstance(MobEffects.SATURATION, 1200, 0), 1.0F)
            .effect(new MobEffectInstance(MobEffects.LUCK, 1600, 0), 1.0F)
            .alwaysEat()
            .build();
}
