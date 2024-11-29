package com.li64.tide.registries;

import com.li64.tide.Tide;
import com.li64.tide.data.loot.BiomeTagCheck;
import com.li64.tide.data.loot.TideFishingPredicate;
import com.mojang.serialization.MapCodec;
import net.minecraft.advancements.critereon.EntitySubPredicate;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditions;

import java.util.HashMap;

public class TideLootConditions {
    public static final HashMap<String, LootItemConditionType> LOOT_CONDITIONS = new HashMap<>();

    public static final LootItemConditionType BIOME_TAG_CHECK = register(
            "check_biome_tag", new LootItemConditionType(new BiomeTagCheck.Serializer()));

    public static LootItemConditionType register(String key, LootItemConditionType type) {
        LOOT_CONDITIONS.put(key, type);
        return type;
    }

    public static void init() {
        LOOT_CONDITIONS.forEach(Tide.PLATFORM::registerLootCondition);
    }
}
