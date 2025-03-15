package com.li64.tide.registries;

import com.li64.tide.Tide;
import com.li64.tide.data.loot.BiomeTagPredicate;
import com.li64.tide.data.loot.IsNightPredicate;
import com.li64.tide.data.loot.MoonPhasePredicate;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import com.li64.tide.data.loot.BlockNearbyPredicate;
import com.li64.tide.data.loot.*;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;

import java.util.HashMap;

public class TideLootConditions {
    public static final HashMap<String, LootItemConditionType> LOOT_CONDITIONS = new HashMap<>();

    public static final LootItemConditionType BIOME_TAG_CHECK = register(
            "check_biome_tag", new LootItemConditionType(BiomeTagPredicate.CODEC));
    public static final LootItemConditionType MOON_PHASE = register(
            "check_moon_phase", new LootItemConditionType(MoonPhasePredicate.CODEC));
    public static final LootItemConditionType IS_NIGHT = register(
            "is_night", new LootItemConditionType(IsNightPredicate.CODEC));
    public static final LootItemConditionType BLOCK_NEARBY = register(
            "block_nearby", new LootItemConditionType(BlockNearbyPredicate.CODEC));
    public static final LootItemConditionType LOOT_CRATE_BLOCK = register(
            "loot_crate_block", new LootItemConditionType(LootCrateBlockPredicate.CODEC));

    public static LootItemConditionType register(String key, LootItemConditionType type) {
        LOOT_CONDITIONS.put(key, type);
        return Registry.register(BuiltInRegistries.LOOT_CONDITION_TYPE, Tide.resource(key), type);
    }

    public static void init() {
        Tide.LOG.info("Registering Tide Loot Conditions");
    }
}
