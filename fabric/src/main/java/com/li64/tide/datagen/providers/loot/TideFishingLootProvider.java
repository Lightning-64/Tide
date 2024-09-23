package com.li64.tide.datagen.providers.loot;

import com.li64.tide.data.TideLootTables;
import com.li64.tide.registries.TideItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.SimpleFabricLootTableProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;

import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;

public class TideFishingLootProvider extends SimpleFabricLootTableProvider {
    public TideFishingLootProvider(FabricDataOutput output) {
        super(output, LootContextParamSets.FISHING);
    }

    @Override
    public void generate(BiConsumer<ResourceLocation, LootTable.Builder> output) {
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
                        .add(LootItem.lootTableItem(TideItems.ENDSTONE_PERCH).setWeight(90))
                        .add(LootItem.lootTableItem(TideItems.ENDERFIN).setWeight(95))
                        .add(LootItem.lootTableItem(TideItems.ENDERGAZER).setWeight(80))
                        .add(LootItem.lootTableItem(TideItems.PURPUR_PIKE).setWeight(80))
                        .add(LootItem.lootTableItem(TideItems.CHORUS_COD).setWeight(70))
                        .add(LootItem.lootTableItem(TideItems.ELYTROUT).setWeight(20).setQuality(2))
                        .add(LootItem.lootTableItem(TideItems.VOIDSEEKER).setWeight(25).setQuality(1))
                )
        );

        output.accept(TideLootTables.Biomes.BADLANDS, LootTable.lootTable().withPool(
                LootPool.lootPool().add(LootItem.lootTableItem(TideItems.MIRAGE_CATFISH))));
        output.accept(TideLootTables.Biomes.BIRCH, LootTable.lootTable().withPool(
                LootPool.lootPool().add(LootItem.lootTableItem(TideItems.BIRCH_TROUT))));
        output.accept(TideLootTables.Biomes.CHERRY, LootTable.lootTable().withPool(
                LootPool.lootPool().add(LootItem.lootTableItem(TideItems.BLOSSOM_BASS))));
        output.accept(TideLootTables.Biomes.DEEP_DARK, LootTable.lootTable().withPool(
                LootPool.lootPool().add(LootItem.lootTableItem(TideItems.ECHOFIN_SNAPPER))));
        output.accept(TideLootTables.Biomes.DESERT, LootTable.lootTable().withPool(
                LootPool.lootPool().add(LootItem.lootTableItem(TideItems.SANDSKIPPER))));
        output.accept(TideLootTables.Biomes.DRIPSTONE, LootTable.lootTable().withPool(
                LootPool.lootPool().add(LootItem.lootTableItem(TideItems.DRIPSTONE_DARTER))));
        output.accept(TideLootTables.Biomes.FOREST, LootTable.lootTable().withPool(
                LootPool.lootPool().add(LootItem.lootTableItem(TideItems.OAKFISH))));
        output.accept(TideLootTables.Biomes.FROZEN, LootTable.lootTable().withPool(
                LootPool.lootPool().add(LootItem.lootTableItem(TideItems.FROSTBITE_FLOUNDER))));
        output.accept(TideLootTables.Biomes.JUNGLE, LootTable.lootTable().withPool(
                LootPool.lootPool().add(LootItem.lootTableItem(TideItems.LEAFBACK))));
        output.accept(TideLootTables.Biomes.LUSH_CAVES, LootTable.lootTable().withPool(
                LootPool.lootPool().add(LootItem.lootTableItem(TideItems.FLUTTERGILL))));
        output.accept(TideLootTables.Biomes.MOUNTAIN, LootTable.lootTable().withPool(
                LootPool.lootPool().add(LootItem.lootTableItem(TideItems.STONEFISH))));
        output.accept(TideLootTables.Biomes.MUSHROOM, LootTable.lootTable().withPool(
                LootPool.lootPool().add(LootItem.lootTableItem(TideItems.SPORESTALKER))));
        output.accept(TideLootTables.Biomes.PLAINS, LootTable.lootTable().withPool(
                LootPool.lootPool().add(LootItem.lootTableItem(TideItems.PRAIRIE_PIKE))));
        output.accept(TideLootTables.Biomes.SAVANNA, LootTable.lootTable().withPool(
                LootPool.lootPool().add(LootItem.lootTableItem(TideItems.SUNSPIKE_GOBY))));
        output.accept(TideLootTables.Biomes.SWAMP, LootTable.lootTable().withPool(
                LootPool.lootPool().add(LootItem.lootTableItem(TideItems.SLIMEFIN_SNAPPER))));
        output.accept(TideLootTables.Biomes.TAIGA, LootTable.lootTable().withPool(
                LootPool.lootPool().add(LootItem.lootTableItem(TideItems.PINE_PERCH))));
    }
}