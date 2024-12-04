package com.li64.tide.registries;

import com.li64.tide.Tide;
import com.li64.tide.data.loot.FishingStatsPredicate;
import com.li64.tide.data.loot.TideFishingPredicate;
import com.mojang.serialization.MapCodec;
import net.minecraft.advancements.critereon.EntitySubPredicate;

import java.util.HashMap;

public class TideEntitySubPredicates {
    public static final HashMap<String, MapCodec<? extends EntitySubPredicate>> ENTITY_SUB_PREDICATES = new HashMap<>();

    public static final MapCodec<TideFishingPredicate> TIDE_FISHING_HOOK = register(
            "tide_fishing_hook", TideFishingPredicate.CODEC);
    public static final MapCodec<FishingStatsPredicate> FISHING_STATS = register(
            "fishing_stats", FishingStatsPredicate.CODEC);

    public static <T extends EntitySubPredicate> MapCodec<T> register(String key, MapCodec<T> codec) {
        ENTITY_SUB_PREDICATES.put(key, codec);
        return codec;
    }

    public static void init() {
        ENTITY_SUB_PREDICATES.forEach(Tide.PLATFORM::registerEntitySubPredicate);
    }
}
