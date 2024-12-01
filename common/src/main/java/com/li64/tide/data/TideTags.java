package com.li64.tide.data;

import com.li64.tide.Tide;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.material.Fluid;

import java.util.ArrayList;

@SuppressWarnings("unused")
public class TideTags {
    public static class Items {
        public static final TagKey<Item> VANILLA_FISH = TagKey.create(Registries.ITEM, Tide.resource("vanilla_fish"));
        public static final TagKey<Item> BIOME_FISH = TagKey.create(Registries.ITEM, Tide.resource("biome_fish"));
        public static final TagKey<Item> LEGENDARY_FISH = TagKey.create(Registries.ITEM, Tide.resource("legendary_fish"));
        public static final TagKey<Item> COOKED_FISH = TagKey.create(Registries.ITEM, Tide.resource("cooked_fish"));
        public static final TagKey<Item> COOKABLE_FISH = TagKey.create(Registries.ITEM, Tide.resource("cookable_fish"));
        public static final TagKey<Item> JOURNAL_FISH = TagKey.create(Registries.ITEM, Tide.resource("journal_fish"));

        public static final TagKey<Item> TWILIGHT_ANGLER_EATABLE = TagKey.create(Registries.ITEM, Tide.resource("twilight_angler_eatable"));
        public static final TagKey<Item> BAIT_PLANTS = TagKey.create(Registries.ITEM, Tide.resource("bait_plants"));
        public static final TagKey<Item> CRATES = TagKey.create(Registries.ITEM, Tide.resource("crates"));

        public static final TagKey<Item> CUSTOMIZABLE_RODS = TagKey.create(Registries.ITEM, Tide.resource("customizable_rods"));
        public static final TagKey<Item> BOBBERS = TagKey.create(Registries.ITEM, Tide.resource("bobbers"));
        public static final TagKey<Item> HOOKS = TagKey.create(Registries.ITEM, Tide.resource("hooks"));
        public static final TagKey<Item> LINES = TagKey.create(Registries.ITEM, Tide.resource("lines"));
    }

    public static class Fluids {
        public static final TagKey<Fluid> CAN_FISH_IN = TagKey.create(Registries.FLUID, Tide.resource("can_fish_in"));
        public static final TagKey<Fluid> WATER_FISHING = TagKey.create(Registries.FLUID, Tide.resource("types/water_fishing"));
        public static final TagKey<Fluid> LAVA_FISHING = TagKey.create(Registries.FLUID, Tide.resource("types/lava_fishing"));
    }

    public static class Climate {
        public static final TagKey<Biome> IS_SALTWATER =
                TagKey.create(Registries.BIOME, Tide.resource("is_saltwater"));
        public static final TagKey<Biome> IS_COLD =
                TagKey.create(Registries.BIOME, Tide.resource("is_cold"));
        public static final TagKey<Biome> IS_WARM =
                TagKey.create(Registries.BIOME, Tide.resource("is_warm"));
    }

    public static class Entities {
        public static final TagKey<EntityType<?>> FRESHWATER_FISH = TagKey.create(
                Registries.ENTITY_TYPE, Tide.resource("freshwater_fish"));
        public static final TagKey<EntityType<?>> SALTWATER_FISH = TagKey.create(
                Registries.ENTITY_TYPE, Tide.resource("saltwater_fish"));
    }

    public static class Biomes {
        public static final TagKey<Biome> HAS_FISHING_BOAT = TagKey.create(
                Registries.BIOME, Tide.resource("has_structure/fishing_boat"));
        public static final TagKey<Biome> HAS_END_OASIS = TagKey.create(
                Registries.BIOME, Tide.resource("has_end_oasis"));
        public static final TagKey<Biome> GROWS_ALGAE = TagKey.create(
                Registries.BIOME, Tide.resource("grows_algae"));

        public static final TagKey<Biome> CAN_CATCH_STARFISH = TagKey.create(
                Registries.BIOME, Tide.resource("can_catch_starfish"));

        public static ArrayList<TagKey<Biome>> fishingBiomes;
        public static final TagKey<Biome> BADLANDS = biomeFishTag("badlands");
        public static final TagKey<Biome> BIRCH = biomeFishTag("birch");
        public static final TagKey<Biome> CHERRY = biomeFishTag("cherry");
        public static final TagKey<Biome> DESERT = biomeFishTag("desert");
        public static final TagKey<Biome> FOREST = biomeFishTag("forest");
        public static final TagKey<Biome> FROZEN = biomeFishTag("frozen");
        public static final TagKey<Biome> JUNGLE = biomeFishTag("jungle");
        public static final TagKey<Biome> MOUNTAIN = biomeFishTag("mountain");
        public static final TagKey<Biome> MUSHROOM = biomeFishTag("mushroom");
        public static final TagKey<Biome> PLAINS = biomeFishTag("plains");
        public static final TagKey<Biome> SAVANNA = biomeFishTag("savanna");
        public static final TagKey<Biome> SWAMP = biomeFishTag("swamp");
        public static final TagKey<Biome> TAIGA = biomeFishTag("taiga");
        public static final TagKey<Biome> DEEP_DARK = biomeFishTag("deep_dark");
        public static final TagKey<Biome> LUSH_CAVES = biomeFishTag("lush_caves");
        public static final TagKey<Biome> DRIPSTONE = biomeFishTag("dripstone");

        public static TagKey<Biome> biomeFishTag(String path) {
            if (fishingBiomes == null) fishingBiomes = new ArrayList<>();
            TagKey<Biome> tag = TagKey.create(Registries.BIOME, Tide.resource( path));
            fishingBiomes.add(tag);
            return tag;
        }
    }
}
