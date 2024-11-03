package com.li64.tide.datagen.providers.tags;

import com.li64.tide.data.TideTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BiomeTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

public class TideBiomeTagsProvider extends FabricTagProvider<Biome> {
    public TideBiomeTagsProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, Registries.BIOME, registries);
    }

    @Override
    public @NotNull String getName() {
        return "Tide Biome Tags";
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        getOrCreateTagBuilder(TideTags.Biomes.HAS_FISHING_BOAT)
                .forceAddTag(BiomeTags.IS_OCEAN);

        getOrCreateTagBuilder(TideTags.Biomes.HAS_END_OASIS)
                .add(Biomes.END_MIDLANDS)
                .add(Biomes.END_HIGHLANDS);

        getOrCreateTagBuilder(TideTags.Biomes.GROWS_ALGAE)
                .forceAddTag(BiomeTags.IS_OCEAN)
                .forceAddTag(BiomeTags.IS_BEACH)
                .add(Biomes.RIVER)
                .add(Biomes.SWAMP)
                .add(Biomes.MANGROVE_SWAMP);

        getOrCreateTagBuilder(TideTags.Biomes.CAN_CATCH_STARFISH)
                .forceAddTag(BiomeTags.IS_DEEP_OCEAN);

        getOrCreateTagBuilder(TideTags.Climate.IS_COLD)
                .forceAddTag(conventionTag("is_cold/overworld"));

        getOrCreateTagBuilder(TideTags.Climate.IS_WARM)
                .forceAddTag(conventionTag("is_hot/overworld"))
                .add(Biomes.DEEP_LUKEWARM_OCEAN)
                .add(Biomes.LUKEWARM_OCEAN);

        getOrCreateTagBuilder(TideTags.Climate.IS_SALTWATER)
                .forceAddTag(BiomeTags.IS_OCEAN)
                .forceAddTag(BiomeTags.IS_BEACH)
                .add(Biomes.MUSHROOM_FIELDS);

        getOrCreateTagBuilder(TideTags.Biomes.BADLANDS)
                .forceAddTag(conventionTag("is_badlands"));

        getOrCreateTagBuilder(TideTags.Biomes.BIRCH)
                .forceAddTag(conventionTag("is_birch_forest"))
                .addOptional(new ResourceLocation(
                        "biomesoplenty", "aspen_glade"));

        getOrCreateTagBuilder(TideTags.Biomes.CHERRY)
                .add(Biomes.CHERRY_GROVE)
                .addOptional(new ResourceLocation(
                        "biomesoplenty", "snowblossom_grove"));

        getOrCreateTagBuilder(TideTags.Biomes.DEEP_DARK)
                .add(Biomes.DEEP_DARK);

        getOrCreateTagBuilder(TideTags.Biomes.DESERT)
                .forceAddTag(conventionTag("is_desert"))
                .addOptional(new ResourceLocation(
                        "biomesoplenty", "dune_beach"))
                .addOptional(new ResourceLocation(
                        "biomesoplenty", "lush_desert"));

        getOrCreateTagBuilder(TideTags.Biomes.DRIPSTONE)
                .add(Biomes.DRIPSTONE_CAVES);

        getOrCreateTagBuilder(TideTags.Biomes.FOREST)
                .forceAddTag(conventionTag("is_forest"))
                .addOptional(new ResourceLocation(
                        "biomesoplenty", "mediterranean_forest"))
                .addOptional(new ResourceLocation(
                        "biomesoplenty", "woodland"))
                .addOptional(new ResourceLocation(
                        "biomesoplenty", "old_growth_woodland"))
                .addOptional(new ResourceLocation(
                        "biomesoplenty", "orchard"))
                .addOptional(new ResourceLocation(
                        "biomesoplenty", "origin_valley"))
                .addOptional(new ResourceLocation(
                        "biomesoplenty", "seasonal_forest"));

        getOrCreateTagBuilder(TideTags.Biomes.FROZEN)
                .forceAddTag(conventionTag("is_icy"))
                .add(Biomes.FROZEN_RIVER)
                .add(Biomes.FROZEN_OCEAN)
                .add(Biomes.SNOWY_BEACH)
                .add(Biomes.SNOWY_PLAINS)
                .add(Biomes.ICE_SPIKES)
                .addOptional(new ResourceLocation(
                        "biomesoplenty", "auroral_garden"))
                .addOptional(new ResourceLocation(
                        "biomesoplenty", "cold_desert"))
                .addOptional(new ResourceLocation(
                        "biomesoplenty", "tundra"))
                .addOptional(new ResourceLocation(
                        "biomesoplenty", "wintry_origin_valley"))
                .addOptional(new ResourceLocation(
                        "biomesoplenty", "snowy_coniferous_forest"))
                .addOptional(new ResourceLocation(
                        "biomesoplenty", "snowy_fir_clearing"))
                .addOptional(new ResourceLocation(
                        "biomesoplenty", "snowy_maple_woods"));

        getOrCreateTagBuilder(TideTags.Biomes.JUNGLE)
                .forceAddTag(conventionTag("is_jungle"))
                .addOptional(new ResourceLocation(
                        "biomesoplenty", "floodplain"))
                .addOptional(new ResourceLocation(
                        "biomesoplenty", "rainforest"))
                .addOptional(new ResourceLocation(
                        "biomesoplenty", "rocky_rainforest"))
                .addOptional(new ResourceLocation(
                        "biomesoplenty", "tropics"));

        getOrCreateTagBuilder(TideTags.Biomes.LUSH_CAVES)
                .add(Biomes.LUSH_CAVES);

        getOrCreateTagBuilder(TideTags.Biomes.MOUNTAIN)
                .forceAddTag(conventionTag("is_mountain"))
                .addOptional(new ResourceLocation(
                        "biomesoplenty", "crag"))
                .addOptional(new ResourceLocation(
                        "biomesoplenty", "highland"))
                .addOptional(new ResourceLocation(
                        "biomesoplenty", "jade_cliffs"))
                .addOptional(new ResourceLocation(
                        "biomesoplenty", "rocky_shrubland"));

        getOrCreateTagBuilder(TideTags.Biomes.MUSHROOM)
                .forceAddTag(conventionTag("is_mushroom"))
                .addOptional(new ResourceLocation(
                        "biomesoplenty", "fungal_jungle"));

        getOrCreateTagBuilder(TideTags.Biomes.PLAINS)
                .forceAddTag(conventionTag("is_plains"))
                .add(Biomes.MEADOW)
                .addOptional(new ResourceLocation(
                        "biomesoplenty", "field"))
                .addOptional(new ResourceLocation(
                        "biomesoplenty", "grassland"))
                .addOptional(new ResourceLocation(
                        "biomesoplenty", "lavender_field"))
                .addOptional(new ResourceLocation(
                        "biomesoplenty", "moor"))
                .addOptional(new ResourceLocation(
                        "biomesoplenty", "pasture"))
                .addOptional(new ResourceLocation(
                        "biomesoplenty", "prairie"))
                .addOptional(new ResourceLocation(
                        "biomesoplenty", "scrubland"))
                .addOptional(new ResourceLocation(
                        "biomesoplenty", "shrubland"));

        getOrCreateTagBuilder(TideTags.Biomes.SAVANNA)
                .forceAddTag(conventionTag("is_savanna"))
                .addOptional(new ResourceLocation(
                        "biomesoplenty", "lush_savanna"));

        getOrCreateTagBuilder(TideTags.Biomes.SWAMP)
                .forceAddTag(conventionTag("is_swamp"))
                .addOptional(new ResourceLocation(
                        "biomesoplenty", "bayou"))
                .addOptional(new ResourceLocation(
                        "biomesoplenty", "bog"))
                .addOptional(new ResourceLocation(
                    "biomesoplenty", "marsh"));

        getOrCreateTagBuilder(TideTags.Biomes.TAIGA)
                .forceAddTag(conventionTag("is_taiga"))
                .add(Biomes.GROVE)
                .addOptional(new ResourceLocation(
                        "biomesoplenty", "maple_woods"))
                .addOptional(new ResourceLocation(
                        "biomesoplenty", "coniferous_forest"))
                .addOptional(new ResourceLocation(
                        "biomesoplenty", "forested_field"))
                .addOptional(new ResourceLocation(
                        "biomesoplenty", "wetland"));
    }

    public TagKey<Biome> conventionTag(String name) {
        return TagKey.create(Registries.BIOME, new ResourceLocation("c", name));
    }
}
