package com.li64.tide.datagen.providers.loot;

import com.li64.tide.data.TideLootTables;
import com.li64.tide.data.TideTags;
import com.li64.tide.data.loot.*;
import com.li64.tide.registries.TideItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.SimpleFabricLootTableProvider;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.EntitySubPredicate;
import net.minecraft.advancements.critereon.LocationPredicate;
import net.minecraft.core.HolderLookup;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.AlternativesEntry;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.entries.NestedLootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.predicates.LocationCheck;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemEntityPropertyCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;

import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;

public class TideFishingLootProvider extends SimpleFabricLootTableProvider {
    public TideFishingLootProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registryLookup) {
        super(output, registryLookup, LootContextParamSets.FISHING);
    }

    @Override
    public void generate(BiConsumer<ResourceKey<LootTable>, LootTable.Builder> output) {
        output.accept(
                TideLootTables.Fishing.CRATES,
                LootTable.lootTable().withPool(LootPool.lootPool().add(AlternativesEntry.alternatives(
                        // crate loot logic for lava fishing
                        AlternativesEntry.alternatives(
                                NestedLootTable.lootTableReference(TideLootTables.Crates.NETHER_LAVA).when(
                                        LocationCheck.checkLocation(LocationPredicate.Builder.inDimension(Level.NETHER))),
                                NestedLootTable.lootTableReference(TideLootTables.Crates.END_LAVA).when(
                                        LocationCheck.checkLocation(LocationPredicate.Builder.inDimension(Level.END))),
                                NestedLootTable.lootTableReference(TideLootTables.Crates.OVERWORLD_LAVA_DEEP).when(
                                        entityPredicate(TideFishingPredicate.depthLayer(DepthLayer.DEPTHS))),
                                NestedLootTable.lootTableReference(TideLootTables.Crates.OVERWORLD_LAVA_UNDERGROUND).when(
                                        entityPredicate(TideFishingPredicate.depthLayer(DepthLayer.UNDERGROUND))),
                                NestedLootTable.lootTableReference(TideLootTables.Crates.OVERWORLD_LAVA_SURFACE)
                        ).when(entityPredicate(TideFishingPredicate.isLavaFishing(true))),
                        // crate loot logic for water fishing
                        AlternativesEntry.alternatives(
                                NestedLootTable.lootTableReference(TideLootTables.Crates.END_WATER).when(
                                        LocationCheck.checkLocation(LocationPredicate.Builder.inDimension(Level.END))),
                                NestedLootTable.lootTableReference(TideLootTables.Crates.OVERWORLD_WATER_DEEP)
                                        .when(entityPredicate(TideFishingPredicate.depthLayer(DepthLayer.DEPTHS))),
                                NestedLootTable.lootTableReference(TideLootTables.Crates.OVERWORLD_WATER_UNDERGROUND)
                                        .when(entityPredicate(TideFishingPredicate.depthLayer(DepthLayer.UNDERGROUND))),
                                AlternativesEntry.alternatives(
                                        NestedLootTable.lootTableReference(TideLootTables.Crates.OVERWORLD_WATER_OCEAN).when(
                                                BiomeTagPredicate.checkTag(TideTags.Climate.IS_SALTWATER)),
                                        NestedLootTable.lootTableReference(TideLootTables.Crates.OVERWORLD_WATER_RIVER)
                                )
                        )
                )))
        );

        output.accept(
                TideLootTables.Fishing.CRATES_BLOCK,
                LootTable.lootTable().withPool(LootPool.lootPool().add(AlternativesEntry.alternatives(
                        LootItem.lootTableItem(TideItems.END_LOOT_CRATE).when(
                                LocationCheck.checkLocation(LocationPredicate.Builder.inDimension(Level.END))),
                        LootItem.lootTableItem(TideItems.OBSIDIAN_LOOT_CRATE).when(
                                LocationCheck.checkLocation(LocationPredicate.Builder.inDimension(Level.NETHER))
                                        .or(entityPredicate(TideFishingPredicate.isLavaFishing(true)))),
                        LootItem.lootTableItem(TideItems.SURFACE_LOOT_CRATE)
                )))
        );

        output.accept(
                TideLootTables.Fishing.SPECIAL_FISH,
                LootTable.lootTable().withPool(LootPool.lootPool()
                        .add(
                            // biome fish (1/20 chance in overworld water)
                            AlternativesEntry.alternatives(
                                LootItem.lootTableItem(TideItems.BLOSSOM_BASS).when(BiomeTagPredicate.checkTag(TideTags.Biomes.CHERRY)),
                                LootItem.lootTableItem(TideItems.ECHOFIN_SNAPPER).when(BiomeTagPredicate.checkTag(TideTags.Biomes.DEEP_DARK)),
                                LootItem.lootTableItem(TideItems.DRIPSTONE_DARTER).when(BiomeTagPredicate.checkTag(TideTags.Biomes.DRIPSTONE)),
                                LootItem.lootTableItem(TideItems.FLUTTERGILL).when(BiomeTagPredicate.checkTag(TideTags.Biomes.LUSH_CAVES)),
                                LootItem.lootTableItem(TideItems.SUNSPIKE_GOBY).when(BiomeTagPredicate.checkTag(TideTags.Biomes.SAVANNA)),
                                LootItem.lootTableItem(TideItems.BIRCH_TROUT).when(BiomeTagPredicate.checkTag(TideTags.Biomes.BIRCH)),
                                LootItem.lootTableItem(TideItems.MIRAGE_CATFISH).when(BiomeTagPredicate.checkTag(TideTags.Biomes.BADLANDS)),
                                LootItem.lootTableItem(TideItems.SLIMEFIN_SNAPPER).when(BiomeTagPredicate.checkTag(TideTags.Biomes.SWAMP)),
                                LootItem.lootTableItem(TideItems.SPORESTALKER).when(BiomeTagPredicate.checkTag(TideTags.Biomes.MUSHROOM)),
                                LootItem.lootTableItem(TideItems.LEAFBACK).when(BiomeTagPredicate.checkTag(TideTags.Biomes.JUNGLE)),
                                LootItem.lootTableItem(TideItems.PINE_PERCH).when(BiomeTagPredicate.checkTag(TideTags.Biomes.TAIGA)),
                                LootItem.lootTableItem(TideItems.SANDSKIPPER).when(BiomeTagPredicate.checkTag(TideTags.Biomes.DESERT)),
                                LootItem.lootTableItem(TideItems.STONEFISH).when(BiomeTagPredicate.checkTag(TideTags.Biomes.MOUNTAIN)),
                                LootItem.lootTableItem(TideItems.FROSTBITE_FLOUNDER).when(BiomeTagPredicate.checkTag(TideTags.Biomes.FROZEN)),
                                LootItem.lootTableItem(TideItems.OAKFISH).when(BiomeTagPredicate.checkTag(TideTags.Biomes.FOREST)),
                                LootItem.lootTableItem(TideItems.PRAIRIE_PIKE).when(BiomeTagPredicate.checkTag(TideTags.Biomes.PLAINS))
                            ).when(LocationCheck.checkLocation(LocationPredicate.Builder.inDimension(Level.OVERWORLD))
                                    .and(entityPredicate(TideFishingPredicate.isLavaFishing(false)))
                                    .and(LootItemRandomChanceCondition.randomChance(ConstantValue.exactly(0.05f)))))

                        .add(LootItem.lootTableItem(TideItems.MIDAS_FISH).setWeight(10).when(
                                entityPredicate(FishingStatsPredicate.luckOf(5))
                                        .and(LootItemRandomChanceCondition.randomChance(ConstantValue.exactly(0.05f)))))
                        .add(LootItem.lootTableItem(TideItems.VOIDSEEKER).setWeight(10).when(
                                LocationCheck.checkLocation(LocationPredicate.Builder.inDimension(Level.END))
                                        .and(MoonPhasePredicate.anyOf(0, 4))
                                        .and(LootItemRandomChanceCondition.randomChance(ConstantValue.exactly(0.05f)))))
                        .add(LootItem.lootTableItem(TideItems.SHOOTING_STARFISH).setWeight(10).when(
                                MoonPhasePredicate.anyOf(0).and(IsNightPredicate.isNight())
                                        .and(BiomeTagPredicate.checkTag(TideTags.Biomes.CAN_CATCH_STARFISH))
                                        .and(LootItemRandomChanceCondition.randomChance(ConstantValue.exactly(0.05f)))))
                )
        );

        output.accept(
                TideLootTables.Fishing.FRESHWATER_NORMAL,
                LootTable.lootTable().withPool(LootPool.lootPool()
                        .add(LootItem.lootTableItem(TideItems.BASS).setWeight(95))
                        .add(LootItem.lootTableItem(TideItems.BLUEGILL).setWeight(80))
                        .add(LootItem.lootTableItem(TideItems.TROUT).setWeight(80))
                        .add(LootItem.lootTableItem(TideItems.YELLOW_PERCH).setWeight(80))
                        .add(LootItem.lootTableItem(Items.SALMON).setWeight(75))
                        .add(LootItem.lootTableItem(TideItems.MINT_CARP).setWeight(50).setQuality(1))
                        .add(LootItem.lootTableItem(TideItems.GUPPY).setWeight(20).setQuality(1))
                        .add(LootItem.lootTableItem(TideItems.PIKE).setWeight(20).setQuality(2))
                        .add(LootItem.lootTableItem(TideItems.CATFISH).setWeight(15).setQuality(3))
                        .add(LootItem.lootTableItem(TideItems.CLAYFISH).setWeight(12).setQuality(4))
                )
        );

        output.accept(
                TideLootTables.Fishing.FRESHWATER_COLD,
                LootTable.lootTable().withPool(LootPool.lootPool()
                        .add(LootItem.lootTableItem(TideItems.TROUT).setWeight(100))
                        .add(LootItem.lootTableItem(Items.SALMON).setWeight(90))
                        .add(LootItem.lootTableItem(TideItems.YELLOW_PERCH).setWeight(85))
                        .add(LootItem.lootTableItem(TideItems.BLUEGILL).setWeight(80))
                        .add(LootItem.lootTableItem(TideItems.PIKE).setWeight(50).setQuality(1))
                        .add(LootItem.lootTableItem(TideItems.CATFISH).setWeight(25).setQuality(2))
                        .add(LootItem.lootTableItem(TideItems.CLAYFISH).setWeight(18).setQuality(3))
                        .add(LootItem.lootTableItem(TideItems.BASS).setWeight(15))
                        .add(LootItem.lootTableItem(TideItems.MINT_CARP).setWeight(10).setQuality(2))
                )
        );

        output.accept(
                TideLootTables.Fishing.FRESHWATER_WARM,
                LootTable.lootTable().withPool(LootPool.lootPool()
                        .add(LootItem.lootTableItem(TideItems.BASS).setWeight(95))
                        .add(LootItem.lootTableItem(TideItems.MINT_CARP).setWeight(90).setQuality(1))
                        .add(LootItem.lootTableItem(TideItems.GUPPY).setWeight(80).setQuality(2))
                        .add(LootItem.lootTableItem(Items.SALMON).setWeight(65))
                        .add(LootItem.lootTableItem(TideItems.YELLOW_PERCH).setWeight(50))
                        .add(LootItem.lootTableItem(TideItems.BLUEGILL).setWeight(40))
                        .add(LootItem.lootTableItem(TideItems.GUPPY).setWeight(40).setQuality(3))
                        .add(LootItem.lootTableItem(TideItems.TROUT).setWeight(35))
                        .add(LootItem.lootTableItem(TideItems.PIKE).setWeight(20).setQuality(2))
                        .add(LootItem.lootTableItem(TideItems.CLAYFISH).setWeight(8).setQuality(4))
                )
        );

        output.accept(
                TideLootTables.Fishing.SALTWATER_NORMAL,
                LootTable.lootTable().withPool(LootPool.lootPool()
                        .add(LootItem.lootTableItem(Items.COD).setWeight(100))
                        .add(LootItem.lootTableItem(TideItems.MACKEREL).setWeight(70))
                        .add(LootItem.lootTableItem(Items.SALMON).setWeight(60))
                        .add(LootItem.lootTableItem(TideItems.OCEAN_PERCH).setWeight(50))
                        .add(LootItem.lootTableItem(TideItems.TROUT).setWeight(25))
                        .add(LootItem.lootTableItem(TideItems.TUNA).setWeight(20))
                        .add(LootItem.lootTableItem(Items.PUFFERFISH).setWeight(15).setQuality(2))
                        .add(LootItem.lootTableItem(TideItems.BARRACUDA).setWeight(8).setQuality(2))
                        .add(LootItem.lootTableItem(TideItems.SAILFISH).setWeight(5).setQuality(4))
                )
        );

        output.accept(
                TideLootTables.Fishing.SALTWATER_COLD,
                LootTable.lootTable().withPool(LootPool.lootPool()
                        .add(LootItem.lootTableItem(Items.COD).setWeight(100))
                        .add(LootItem.lootTableItem(TideItems.OCEAN_PERCH).setWeight(80))
                        .add(LootItem.lootTableItem(Items.SALMON).setWeight(60))
                        .add(LootItem.lootTableItem(TideItems.TROUT).setWeight(45))
                        .add(LootItem.lootTableItem(TideItems.TUNA).setWeight(25))
                )
        );

        output.accept(
                TideLootTables.Fishing.SALTWATER_WARM,
                LootTable.lootTable().withPool(LootPool.lootPool()
                        .add(LootItem.lootTableItem(Items.TROPICAL_FISH).setWeight(70))
                        .add(LootItem.lootTableItem(TideItems.TUNA).setWeight(45))
                        .add(LootItem.lootTableItem(TideItems.MACKEREL).setWeight(40))
                        .add(LootItem.lootTableItem(Items.PUFFERFISH).setWeight(35))
                        .add(LootItem.lootTableItem(TideItems.ANGELFISH).setWeight(30))
                        .add(LootItem.lootTableItem(Items.COD).setWeight(25))
                        .add(LootItem.lootTableItem(TideItems.SAILFISH).setWeight(15))
                        .add(LootItem.lootTableItem(TideItems.BARRACUDA).setWeight(15))
                )
        );

        output.accept(
                TideLootTables.Fishing.UNDERGROUND,
                LootTable.lootTable().withPool(LootPool.lootPool()
                        .add(LootItem.lootTableItem(TideItems.CAVE_EEL).setWeight(90))
                        .add(LootItem.lootTableItem(TideItems.CAVE_CRAWLER).setWeight(90))
                        .add(LootItem.lootTableItem(TideItems.GLOWFISH).setWeight(70))
                        .add(LootItem.lootTableItem(TideItems.ANGLER_FISH).setWeight(70))
                        .add(LootItem.lootTableItem(TideItems.IRON_TETRA).setWeight(50).setQuality(1))
                        .add(LootItem.lootTableItem(TideItems.CRYSTAL_SHRIMP).setWeight(30).setQuality(2))
                        .add(LootItem.lootTableItem(TideItems.GILDED_MINNOW).setWeight(10).setQuality(3))
                        .add(LootItem.lootTableItem(Items.COBBLESTONE).setWeight(25))
                        .add(LootItem.lootTableItem(Items.RAW_IRON).setWeight(25).setQuality(2))
                        .add(LootItem.lootTableItem(Items.STICK).setWeight(25))
                )
        );

        output.accept(
                TideLootTables.Fishing.DEPTHS,
                LootTable.lootTable().withPool(LootPool.lootPool()
                        .add(LootItem.lootTableItem(TideItems.DEEP_GROUPER).setWeight(90))
                        .add(LootItem.lootTableItem(TideItems.SHADOW_SNAPPER).setWeight(90))
                        .add(LootItem.lootTableItem(TideItems.LUMINESCENT_JELLYFISH).setWeight(70))
                        .add(LootItem.lootTableItem(TideItems.ABYSS_ANGLER).setWeight(70))
                        .add(LootItem.lootTableItem(TideItems.LAPIS_LANTERNFISH).setWeight(50).setQuality(1))
                        .add(LootItem.lootTableItem(TideItems.CRYSTALLINE_CARP).setWeight(40).setQuality(1))
                        .add(LootItem.lootTableItem(TideItems.BEDROCK_TETRA).setWeight(30).setQuality(1))
                        .add(LootItem.lootTableItem(Items.COBBLED_DEEPSLATE).setWeight(20))
                        .add(LootItem.lootTableItem(Items.GOLD_INGOT).setWeight(15).setQuality(1))
                        .add(LootItem.lootTableItem(Items.DIAMOND).setWeight(10).setQuality(2))
                )
        );

        output.accept(
                TideLootTables.Fishing.LAVA_SURFACE,
                LootTable.lootTable().withPool(LootPool.lootPool()
                        .add(LootItem.lootTableItem(TideItems.OBSIDIAN_FRAGMENT).setWeight(60))
                        .add(LootItem.lootTableItem(TideItems.FISH_BONE).setWeight(40))
                        .add(LootItem.lootTableItem(TideItems.EMBER_KOI).setWeight(40))
                        .add(LootItem.lootTableItem(TideItems.INFERNO_GUPPY).setWeight(30).setQuality(1))
                        .add(LootItem.lootTableItem(TideItems.OBSIDIAN_PIKE).setWeight(25).setQuality(1))
                        .add(LootItem.lootTableItem(TideItems.VOLCANO_TUNA).setWeight(8).setQuality(2))
                )
        );

        output.accept(
                TideLootTables.Fishing.LAVA_UNDERGROUND,
                LootTable.lootTable().withPool(LootPool.lootPool()
                        .add(LootItem.lootTableItem(TideItems.OBSIDIAN_FRAGMENT).setWeight(60))
                        .add(LootItem.lootTableItem(TideItems.FISH_BONE).setWeight(30))
                        .add(LootItem.lootTableItem(TideItems.EMBER_KOI).setWeight(40))
                        .add(LootItem.lootTableItem(TideItems.INFERNO_GUPPY).setWeight(30).setQuality(1))
                        .add(LootItem.lootTableItem(TideItems.OBSIDIAN_PIKE).setWeight(25).setQuality(1))
                        .add(LootItem.lootTableItem(TideItems.VOLCANO_TUNA).setWeight(10).setQuality(2))
                )
        );

        output.accept(
                TideLootTables.Fishing.LAVA_DEPTHS,
                LootTable.lootTable().withPool(LootPool.lootPool()
                        .add(LootItem.lootTableItem(Items.OBSIDIAN).setWeight(40))
                        .add(LootItem.lootTableItem(TideItems.FISH_BONE).setWeight(20))
                        .add(LootItem.lootTableItem(TideItems.EMBER_KOI).setWeight(40))
                        .add(LootItem.lootTableItem(TideItems.INFERNO_GUPPY).setWeight(30).setQuality(1))
                        .add(LootItem.lootTableItem(TideItems.OBSIDIAN_PIKE).setWeight(35).setQuality(1))
                        .add(LootItem.lootTableItem(TideItems.VOLCANO_TUNA).setWeight(20).setQuality(2))
                )
        );

        output.accept(
                TideLootTables.Fishing.NETHER,
                LootTable.lootTable().withPool(LootPool.lootPool()
                        .add(LootItem.lootTableItem(TideItems.MAGMA_MACKEREL).setWeight(80))
                        .add(LootItem.lootTableItem(TideItems.ASHEN_PERCH).setWeight(70))
                        .add(LootItem.lootTableItem(TideItems.WARPED_GUPPY).setWeight(20).setQuality(1))
                        .add(LootItem.lootTableItem(TideItems.CRIMSON_FANGJAW).setWeight(20).setQuality(1))
                        .add(LootItem.lootTableItem(TideItems.SOULSCALER).setWeight(20).setQuality(1))
                        .add(LootItem.lootTableItem(TideItems.WITHERFIN).setWeight(10).setQuality(2))
                        .add(LootItem.lootTableItem(TideItems.BLAZING_SWORDFISH).setWeight(4).setQuality(3))
                        .add(LootItem.lootTableItem(Items.NETHERITE_SCRAP).setWeight(2).setQuality(3))
                )
        );

        output.accept(
                TideLootTables.Fishing.END_LAVA,
                LootTable.lootTable().withPool(LootPool.lootPool()
                        .add(LootItem.lootTableItem(TideItems.OBSIDIAN_FRAGMENT).setWeight(100))
                        .add(LootItem.lootTableItem(Items.OBSIDIAN).setWeight(40))
                )
        );

        output.accept(
                TideLootTables.Fishing.END_WATER,
                LootTable.lootTable().withPool(LootPool.lootPool()
                        .add(LootItem.lootTableItem(TideItems.END_STONE_PERCH).setWeight(100))
                        .add(LootItem.lootTableItem(TideItems.ENDERFIN).setWeight(95))
                        .add(LootItem.lootTableItem(TideItems.ENDERGAZER).setWeight(80))
                        .add(LootItem.lootTableItem(TideItems.PURPUR_PIKE).setWeight(80))
                        .add(LootItem.lootTableItem(TideItems.CHORUS_COD).setWeight(70))
                        .add(LootItem.lootTableItem(TideItems.ELYTROUT).setWeight(20).setQuality(2))
                )
        );
    }

    private LootItemCondition.Builder entityPredicate(EntitySubPredicate predicate) {
        return LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS, EntityPredicate.Builder.entity().subPredicate(predicate));
    }
}