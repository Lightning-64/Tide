package com.li64.tide.datagen.providers.tags;

import com.li64.tide.data.TideTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalBiomeTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BiomeTags;
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
                .forceAddTag(ConventionalBiomeTags.IS_COLD);

        getOrCreateTagBuilder(TideTags.Climate.IS_WARM)
                .forceAddTag(ConventionalBiomeTags.IS_HOT)
                .add(Biomes.DEEP_LUKEWARM_OCEAN)
                .add(Biomes.LUKEWARM_OCEAN);

        getOrCreateTagBuilder(TideTags.Climate.IS_SALTWATER)
                .forceAddTag(ConventionalBiomeTags.IS_OCEAN)
                .forceAddTag(ConventionalBiomeTags.IS_BEACH)
                .add(Biomes.MUSHROOM_FIELDS);

        getOrCreateTagBuilder(TideTags.Biomes.BADLANDS)
                .forceAddTag(ConventionalBiomeTags.IS_BADLANDS);

        getOrCreateTagBuilder(TideTags.Biomes.BIRCH)
                .forceAddTag(ConventionalBiomeTags.IS_BIRCH_FOREST)
                .addOptional(ResourceLocation.fromNamespaceAndPath(
                        "biomesoplenty", "aspen_glade"));

        getOrCreateTagBuilder(TideTags.Biomes.CHERRY)
                .add(Biomes.CHERRY_GROVE)
                .addOptional(ResourceLocation.fromNamespaceAndPath(
                        "biomesoplenty", "snowblossom_grove"));

        getOrCreateTagBuilder(TideTags.Biomes.DEEP_DARK)
                .add(Biomes.DEEP_DARK);

        getOrCreateTagBuilder(TideTags.Biomes.DESERT)
                .forceAddTag(ConventionalBiomeTags.IS_DESERT)
                .addOptional(ResourceLocation.fromNamespaceAndPath(
                        "biomesoplenty", "dune_beach"))
                .addOptional(ResourceLocation.fromNamespaceAndPath(
                        "biomesoplenty", "lush_desert"));

        getOrCreateTagBuilder(TideTags.Biomes.DRIPSTONE)
                .add(Biomes.DRIPSTONE_CAVES);

        getOrCreateTagBuilder(TideTags.Biomes.FOREST)
                .forceAddTag(ConventionalBiomeTags.IS_FOREST)
                .addOptional(ResourceLocation.fromNamespaceAndPath(
                        "biomesoplenty", "mediterranean_forest"))
                .addOptional(ResourceLocation.fromNamespaceAndPath(
                        "biomesoplenty", "woodland"))
                .addOptional(ResourceLocation.fromNamespaceAndPath(
                        "biomesoplenty", "old_growth_woodland"))
                .addOptional(ResourceLocation.fromNamespaceAndPath(
                        "biomesoplenty", "orchard"))
                .addOptional(ResourceLocation.fromNamespaceAndPath(
                        "biomesoplenty", "origin_valley"))
                .addOptional(ResourceLocation.fromNamespaceAndPath(
                        "biomesoplenty", "seasonal_forest"));

        getOrCreateTagBuilder(TideTags.Biomes.FROZEN)
                .forceAddTag(ConventionalBiomeTags.IS_ICY)
                .add(Biomes.FROZEN_RIVER)
                .add(Biomes.FROZEN_OCEAN)
                .add(Biomes.SNOWY_BEACH)
                .add(Biomes.SNOWY_PLAINS)
                .add(Biomes.ICE_SPIKES)
                .addOptional(ResourceLocation.fromNamespaceAndPath(
                        "biomesoplenty", "auroral_garden"))
                .addOptional(ResourceLocation.fromNamespaceAndPath(
                        "biomesoplenty", "cold_desert"))
                .addOptional(ResourceLocation.fromNamespaceAndPath(
                        "biomesoplenty", "tundra"))
                .addOptional(ResourceLocation.fromNamespaceAndPath(
                        "biomesoplenty", "wintry_origin_valley"))
                .addOptional(ResourceLocation.fromNamespaceAndPath(
                        "biomesoplenty", "snowy_coniferous_forest"))
                .addOptional(ResourceLocation.fromNamespaceAndPath(
                        "biomesoplenty", "snowy_fir_clearing"))
                .addOptional(ResourceLocation.fromNamespaceAndPath(
                        "biomesoplenty", "snowy_maple_woods"));

        getOrCreateTagBuilder(TideTags.Biomes.JUNGLE)
                .forceAddTag(ConventionalBiomeTags.IS_JUNGLE)
                .addOptional(ResourceLocation.fromNamespaceAndPath(
                        "biomesoplenty", "floodplain"))
                .addOptional(ResourceLocation.fromNamespaceAndPath(
                        "biomesoplenty", "rainforest"))
                .addOptional(ResourceLocation.fromNamespaceAndPath(
                        "biomesoplenty", "rocky_rainforest"))
                .addOptional(ResourceLocation.fromNamespaceAndPath(
                        "biomesoplenty", "tropics"));

        getOrCreateTagBuilder(TideTags.Biomes.LUSH_CAVES)
                .add(Biomes.LUSH_CAVES);

        getOrCreateTagBuilder(TideTags.Biomes.MOUNTAIN)
                .forceAddTag(ConventionalBiomeTags.IS_MOUNTAIN)
                .addOptional(ResourceLocation.fromNamespaceAndPath(
                        "biomesoplenty", "crag"))
                .addOptional(ResourceLocation.fromNamespaceAndPath(
                        "biomesoplenty", "highland"))
                .addOptional(ResourceLocation.fromNamespaceAndPath(
                        "biomesoplenty", "jade_cliffs"))
                .addOptional(ResourceLocation.fromNamespaceAndPath(
                        "biomesoplenty", "rocky_shrubland"));

        getOrCreateTagBuilder(TideTags.Biomes.MUSHROOM)
                .forceAddTag(ConventionalBiomeTags.IS_MUSHROOM)
                .addOptional(ResourceLocation.fromNamespaceAndPath(
                        "biomesoplenty", "fungal_jungle"));

        getOrCreateTagBuilder(TideTags.Biomes.PLAINS)
                .forceAddTag(ConventionalBiomeTags.IS_PLAINS)
                .add(Biomes.MEADOW)
                .addOptional(ResourceLocation.fromNamespaceAndPath(
                        "biomesoplenty", "field"))
                .addOptional(ResourceLocation.fromNamespaceAndPath(
                        "biomesoplenty", "grassland"))
                .addOptional(ResourceLocation.fromNamespaceAndPath(
                        "biomesoplenty", "lavender_field"))
                .addOptional(ResourceLocation.fromNamespaceAndPath(
                        "biomesoplenty", "moor"))
                .addOptional(ResourceLocation.fromNamespaceAndPath(
                        "biomesoplenty", "pasture"))
                .addOptional(ResourceLocation.fromNamespaceAndPath(
                        "biomesoplenty", "prairie"))
                .addOptional(ResourceLocation.fromNamespaceAndPath(
                        "biomesoplenty", "scrubland"))
                .addOptional(ResourceLocation.fromNamespaceAndPath(
                        "biomesoplenty", "shrubland"));

        getOrCreateTagBuilder(TideTags.Biomes.SAVANNA)
                .forceAddTag(ConventionalBiomeTags.IS_SAVANNA)
                .addOptional(ResourceLocation.fromNamespaceAndPath(
                        "biomesoplenty", "lush_savanna"));

        getOrCreateTagBuilder(TideTags.Biomes.SWAMP)
                .forceAddTag(ConventionalBiomeTags.IS_SWAMP)
                .addOptional(ResourceLocation.fromNamespaceAndPath(
                        "biomesoplenty", "bayou"))
                .addOptional(ResourceLocation.fromNamespaceAndPath(
                        "biomesoplenty", "bog"))
                .addOptional(ResourceLocation.fromNamespaceAndPath(
                    "biomesoplenty", "marsh"));

        getOrCreateTagBuilder(TideTags.Biomes.TAIGA)
                .forceAddTag(ConventionalBiomeTags.IS_TAIGA)
                .add(Biomes.GROVE)
                .addOptional(ResourceLocation.fromNamespaceAndPath(
                        "biomesoplenty", "maple_woods"))
                .addOptional(ResourceLocation.fromNamespaceAndPath(
                        "biomesoplenty", "coniferous_forest"))
                .addOptional(ResourceLocation.fromNamespaceAndPath(
                        "biomesoplenty", "forested_field"))
                .addOptional(ResourceLocation.fromNamespaceAndPath(
                        "biomesoplenty", "wetland"));
    }
}
