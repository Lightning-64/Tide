package com.li64.tide.registries;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.component.Consumable;
import net.minecraft.world.item.component.Consumables;
import net.minecraft.world.item.consume_effects.ApplyStatusEffectsConsumeEffect;

public class TideFoods {
    public static FoodProperties RAW_FISH = new FoodProperties.Builder()
            .nutrition(2).saturationModifier(0.1F)
            .build();

    public static FoodProperties UNHEALTHY_RAW_FISH = new FoodProperties.Builder()
            .nutrition(1).saturationModifier(0.1F)
            .build();
    public static Consumable UNHEALTHY_RAW_FISH_CONSUMABLE = Consumables.defaultFood()
            .onConsume(new ApplyStatusEffectsConsumeEffect(
                    new MobEffectInstance(MobEffects.HUNGER, 400, 0), 0.9F))
            .build();

    public static FoodProperties FIERY_RAW_FISH = new FoodProperties.Builder()
            .nutrition(1).saturationModifier(0.2F)
            .alwaysEdible()
            .build();
    public static Consumable FIERY_RAW_FISH_CONSUMABLE = Consumables.defaultFood()
            .onConsume(new ApplyStatusEffectsConsumeEffect(
                    new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 200, 0), 1.0f))
            .onConsume(new ApplyStatusEffectsConsumeEffect(
                    new MobEffectInstance(MobEffects.GLOWING, 200, 0), 1.0f))
            .build();

    public static FoodProperties COOKED_FISH = new FoodProperties.Builder()
            .nutrition(6).saturationModifier(0.7F)
            .build();

    public static FoodProperties GOLDEN_FISH = new FoodProperties.Builder()
            .nutrition(4).saturationModifier(1.2F)
            .alwaysEdible()
            .build();
    public static Consumable GOLDEN_FISH_CONSUMABLE = Consumables.defaultFood()
            .onConsume(new ApplyStatusEffectsConsumeEffect(
                    new MobEffectInstance(MobEffects.REGENERATION, 90, 2), 1.0f))
            .onConsume(new ApplyStatusEffectsConsumeEffect(
                    new MobEffectInstance(MobEffects.ABSORPTION, 1200, 0), 1.0f))
            .build();

    public static FoodProperties VOIDSEEKER = new FoodProperties.Builder()
            .nutrition(20).saturationModifier(2.0F)
            .alwaysEdible()
            .build();
    public static Consumable VOIDSEEKER_CONSUMABLE = Consumables.defaultFood()
            .onConsume(new ApplyStatusEffectsConsumeEffect(
                    new MobEffectInstance(MobEffects.SATURATION, 1200, 0), 1.0f))
            .onConsume(new ApplyStatusEffectsConsumeEffect(
                    new MobEffectInstance(MobEffects.LUCK, 1600, 0), 1.0f))
            .build();

    public static final FoodProperties FISH_SLICE = new FoodProperties.Builder()
            .nutrition(1)
            .saturationModifier(0.1f)
            .build();
    public static final FoodProperties COOKED_FISH_SLICE = new FoodProperties.Builder()
            .nutrition(3)
            .saturationModifier(0.5f)
            .build();
    public static Consumable FISH_SLICE_CONSUMABLE = Consumables.defaultFood()
            .consumeSeconds(0.8f)
            .build();
}
