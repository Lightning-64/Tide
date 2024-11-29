package com.li64.tide.data;

import com.li64.tide.Tide;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.LootTable;

import java.util.HashSet;
import java.util.Set;

public class TideLootTables {
    private static final Set<ResourceKey<LootTable>> LOCATIONS = new HashSet<>();

    public static class Fishing {
        public static final ResourceKey<LootTable> CRATES = create("gameplay/fishing/crates");
        public static final ResourceKey<LootTable> CRATES_BLOCK = create("gameplay/fishing/crates/block");
        public static final ResourceKey<LootTable> SPECIAL_FISH = create("gameplay/fishing/special");

        public static final ResourceKey<LootTable> FRESHWATER_NORMAL = create("gameplay/fishing/climates/freshwater_normal");
        public static final ResourceKey<LootTable> FRESHWATER_COLD = create("gameplay/fishing/climates/freshwater_cold");
        public static final ResourceKey<LootTable> FRESHWATER_WARM = create("gameplay/fishing/climates/freshwater_warm");

        public static final ResourceKey<LootTable> SALTWATER_NORMAL = create("gameplay/fishing/climates/saltwater_normal");
        public static final ResourceKey<LootTable> SALTWATER_COLD = create("gameplay/fishing/climates/saltwater_cold");
        public static final ResourceKey<LootTable> SALTWATER_WARM = create("gameplay/fishing/climates/saltwater_warm");

        public static final ResourceKey<LootTable> UNDERGROUND = create("gameplay/fishing/climates/underground");
        public static final ResourceKey<LootTable> DEPTHS = create("gameplay/fishing/climates/depths");

        public static final ResourceKey<LootTable> LAVA_SURFACE = create("gameplay/fishing/climates/lava_surface");
        public static final ResourceKey<LootTable> LAVA_UNDERGROUND = create("gameplay/fishing/climates/lava_underground");
        public static final ResourceKey<LootTable> LAVA_DEPTHS = create("gameplay/fishing/climates/lava_depths");

        public static final ResourceKey<LootTable> NETHER = create("gameplay/fishing/climates/nether");

        public static final ResourceKey<LootTable> END_WATER = create("gameplay/fishing/climates/end_water");
        public static final ResourceKey<LootTable> END_LAVA = create("gameplay/fishing/climates/end_lava");
    }

    public static class Crates {
        public static final ResourceKey<LootTable> OVERWORLD_LAVA_SURFACE = create("gameplay/fishing/crates/overworld/lava_surface");
        public static final ResourceKey<LootTable> OVERWORLD_LAVA_UNDERGROUND = create("gameplay/fishing/crates/overworld/lava_underground");
        public static final ResourceKey<LootTable> OVERWORLD_LAVA_DEEP = create("gameplay/fishing/crates/overworld/lava_deep");
        public static final ResourceKey<LootTable> OVERWORLD_WATER_OCEAN = create("gameplay/fishing/crates/overworld/water_ocean");
        public static final ResourceKey<LootTable> OVERWORLD_WATER_RIVER = create("gameplay/fishing/crates/overworld/water_river");
        public static final ResourceKey<LootTable> OVERWORLD_WATER_UNDERGROUND = create("gameplay/fishing/crates/overworld/water_underground");
        public static final ResourceKey<LootTable> OVERWORLD_WATER_DEEP = create("gameplay/fishing/crates/overworld/water_deep");
        public static final ResourceKey<LootTable> NETHER_LAVA = create("gameplay/fishing/crates/nether/lava");
        public static final ResourceKey<LootTable> END_WATER = create("gameplay/fishing/crates/end/water");
        public static final ResourceKey<LootTable> END_LAVA = create("gameplay/fishing/crates/end/lava");
    }

    public static class Chests {
        public static final ResourceKey<LootTable> FISHING_BOAT = create("chests/fishing_boat");
    }

    public static class Entities {
        public static final ResourceKey<LootTable> TROUT = create("entities/trout");
        public static final ResourceKey<LootTable> BASS = create("entities/bass");
        public static final ResourceKey<LootTable> YELLOW_PERCH = create("entities/yellow_perch");
        public static final ResourceKey<LootTable> BLUEGILL = create("entities/bluegill");
        public static final ResourceKey<LootTable> MINT_CARP = create("entities/mint_carp");
        public static final ResourceKey<LootTable> PIKE = create("entities/pike");
        public static final ResourceKey<LootTable> GUPPY = create("entities/guppy");
        public static final ResourceKey<LootTable> CATFISH = create("entities/catfish");
        public static final ResourceKey<LootTable> CLAYFISH = create("entities/clayfish");

        public static final ResourceKey<LootTable> TUNA = create("entities/tuna");
        public static final ResourceKey<LootTable> OCEAN_PERCH = create("entities/ocean_perch");
        public static final ResourceKey<LootTable> MACKEREL = create("entities/mackerel");
        public static final ResourceKey<LootTable> ANGELFISH = create("entities/angelfish");
        public static final ResourceKey<LootTable> BARRACUDA = create("entities/barracuda");
        public static final ResourceKey<LootTable> SAILFISH = create("entities/sailfish");
    }

    public static class Blocks {
        public static final ResourceKey<LootTable> ALGAE = create("blocks/algae");
        public static final ResourceKey<LootTable> ANGLER_WORKSHOP = create("blocks/angler_workshop");
        public static final ResourceKey<LootTable> JELLY_TORCH = create("blocks/jelly_torch");
        public static final ResourceKey<LootTable> SURFACE_LOOT_CRATE = create("blocks/surface_loot_crate");
        public static final ResourceKey<LootTable> OBSIDIAN_LOOT_CRATE = create("blocks/obsidian_loot_crate");
        public static final ResourceKey<LootTable> END_LOOT_CRATE = create("blocks/end_loot_crate");
    }

    public static class Modded {
        public static final ResourceKey<LootTable> EVERBRIGHT = create("gameplay/fishing/climates/blue_skies/everbright");
        public static final ResourceKey<LootTable> EVERDAWN = create("gameplay/fishing/climates/blue_skies/everdawn");
    }

    private static ResourceKey<LootTable> biome(String biome) {
        return create("gameplay/fishing/biomes/" + biome);
    }

    private static ResourceKey<LootTable> create(String path) {
        return register(ResourceKey.create(Registries.LOOT_TABLE, Tide.resource(path)));
    }

    private static ResourceKey<LootTable> register(ResourceKey<LootTable> lootTableKey) {
        LOCATIONS.add(lootTableKey);
        return lootTableKey;
    }

    public static ResourceKey<LootTable> getByLocation(ResourceLocation location) {
        return LOCATIONS.stream().filter(resourceKey -> resourceKey.location().equals(location))
                .findFirst().orElse(null);
    }
}
