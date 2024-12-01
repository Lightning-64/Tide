package com.li64.tide.data;

import com.li64.tide.Tide;
import net.minecraft.resources.ResourceLocation;

public class TideLootTables {

    public static class Fishing {
        public static final ResourceLocation CRATES = create("gameplay/fishing/crates");

        public static final ResourceLocation FRESHWATER_NORMAL = create("gameplay/fishing/climates/freshwater_normal");
        public static final ResourceLocation FRESHWATER_COLD = create("gameplay/fishing/climates/freshwater_cold");
        public static final ResourceLocation FRESHWATER_WARM = create("gameplay/fishing/climates/freshwater_warm");

        public static final ResourceLocation SALTWATER_NORMAL = create("gameplay/fishing/climates/saltwater_normal");
        public static final ResourceLocation SALTWATER_COLD = create("gameplay/fishing/climates/saltwater_cold");
        public static final ResourceLocation SALTWATER_WARM = create("gameplay/fishing/climates/saltwater_warm");

        public static final ResourceLocation UNDERGROUND = create("gameplay/fishing/climates/underground");
        public static final ResourceLocation DEPTHS = create("gameplay/fishing/climates/depths");

        public static final ResourceLocation LAVA_SURFACE = create("gameplay/fishing/climates/lava_surface");
        public static final ResourceLocation LAVA_UNDERGROUND = create("gameplay/fishing/climates/lava_underground");
        public static final ResourceLocation LAVA_DEPTHS = create("gameplay/fishing/climates/lava_depths");

        public static final ResourceLocation NETHER = create("gameplay/fishing/climates/nether");

        public static final ResourceLocation END_WATER = create("gameplay/fishing/climates/end_water");
        public static final ResourceLocation END_LAVA = create("gameplay/fishing/climates/end_lava");
    }
    
    public static class Chests {
        public static final ResourceLocation FISHING_BOAT = create("chests/fishing_boat");
    }

    public static class Crates {
        public static final ResourceLocation OVERWORLD_LAVA_SURFACE = create("gameplay/fishing/crates/overworld/lava_surface");
        public static final ResourceLocation OVERWORLD_LAVA_UNDERGROUND = create("gameplay/fishing/crates/overworld/lava_underground");
        public static final ResourceLocation OVERWORLD_LAVA_DEEP = create("gameplay/fishing/crates/overworld/lava_deep");
        public static final ResourceLocation OVERWORLD_WATER_OCEAN = create("gameplay/fishing/crates/overworld/water_ocean");
        public static final ResourceLocation OVERWORLD_WATER_RIVER = create("gameplay/fishing/crates/overworld/water_river");
        public static final ResourceLocation OVERWORLD_WATER_UNDERGROUND = create("gameplay/fishing/crates/overworld/water_underground");
        public static final ResourceLocation OVERWORLD_WATER_DEEP = create("gameplay/fishing/crates/overworld/water_deep");
        public static final ResourceLocation NETHER_LAVA = create("gameplay/fishing/crates/nether/lava");
        public static final ResourceLocation END_WATER = create("gameplay/fishing/crates/end/water");
        public static final ResourceLocation END_LAVA = create("gameplay/fishing/crates/end/lava");
    }

    public static class Biomes {
        public static final ResourceLocation BADLANDS = biome("badlands");
        public static final ResourceLocation BIRCH = biome("birch");
        public static final ResourceLocation CHERRY = biome("cherry");
        public static final ResourceLocation DEEP_DARK = biome("deep_dark");
        public static final ResourceLocation DESERT = biome("desert");
        public static final ResourceLocation DRIPSTONE = biome("dripstone");
        public static final ResourceLocation FOREST = biome("forest");
        public static final ResourceLocation FROZEN = biome("frozen");
        public static final ResourceLocation JUNGLE = biome("jungle");
        public static final ResourceLocation LUSH_CAVES = biome("lush_caves");
        public static final ResourceLocation MOUNTAIN = biome("mountain");
        public static final ResourceLocation MUSHROOM = biome("mushroom");
        public static final ResourceLocation PLAINS = biome("plains");
        public static final ResourceLocation SAVANNA = biome("savanna");
        public static final ResourceLocation SWAMP = biome("swamp");
        public static final ResourceLocation TAIGA = biome("taiga");
    }

    public static class Entities {
        public static final ResourceLocation TROUT = create("entities/trout");
        public static final ResourceLocation BASS = create("entities/bass");
        public static final ResourceLocation YELLOW_PERCH = create("entities/yellow_perch");
        public static final ResourceLocation BLUEGILL = create("entities/bluegill");
        public static final ResourceLocation MINT_CARP = create("entities/mint_carp");
        public static final ResourceLocation PIKE = create("entities/pike");
        public static final ResourceLocation GUPPY = create("entities/guppy");
        public static final ResourceLocation CATFISH = create("entities/catfish");
        public static final ResourceLocation CLAYFISH = create("entities/clayfish");

        public static final ResourceLocation TUNA = create("entities/tuna");
        public static final ResourceLocation OCEAN_PERCH = create("entities/ocean_perch");
        public static final ResourceLocation MACKEREL = create("entities/mackerel");
        public static final ResourceLocation ANGELFISH = create("entities/angelfish");
        public static final ResourceLocation BARRACUDA = create("entities/barracuda");
        public static final ResourceLocation SAILFISH = create("entities/sailfish");
    }

    private static ResourceLocation biome(String biome) {
        return create("gameplay/fishing/biomes/" + biome);
    }

    private static ResourceLocation create(String path) {
        return Tide.resource(path);
    }
}
