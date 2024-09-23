package com.li64.tide.datagen.providers.loot;

import com.li64.tide.data.TideLootTables;
import com.li64.tide.registries.TideItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.SimpleFabricLootTableProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.CachedOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.EnchantRandomlyFunction;
import net.minecraft.world.level.storage.loot.functions.EnchantWithLevelsFunction;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.functions.SetItemDamageFunction;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;

public class TideChestLootProvider extends SimpleFabricLootTableProvider {
    public TideChestLootProvider(FabricDataOutput output) {
        super(output, LootContextParamSets.CHEST);
    }

    @Override
    public void generate(BiConsumer<ResourceLocation, LootTable.Builder> output) {
        output.accept(
                TideLootTables.Chests.FISHING_BOAT,
                LootTable.lootTable().withPool(LootPool.lootPool()
                        .setRolls(UniformGenerator.between(4, 6))
                        .add(LootItem.lootTableItem(TideItems.COOKED_FISH).setWeight(100))
                        .add(LootItem.lootTableItem(Items.COOKED_SALMON).setWeight(80))
                        .add(LootItem.lootTableItem(Items.COOKED_COD).setWeight(80))
                        .add(LootItem.lootTableItem(Items.STRING).setWeight(45))
                        .add(LootItem.lootTableItem(Items.IRON_NUGGET).setWeight(30).setQuality(1))
                        .add(LootItem.lootTableItem(Items.FISHING_ROD).setWeight(12).setQuality(1)
                                .apply(SetItemDamageFunction.setDamage(
                                        UniformGenerator.between(0.4f, 1.0f)))
                                .apply(EnchantRandomlyFunction.randomApplicableEnchantment()
                                        .when(LootItemRandomChanceCondition.randomChance(0.7f))))
                )
        );

        output.accept(
                TideLootTables.Crates.OVERWORLD_WATER_RIVER,
                LootTable.lootTable().withPool(LootPool.lootPool()
                        .setRolls(UniformGenerator.between(7, 12))
                        .add(LootItem.lootTableItem(Items.SALMON).setWeight(100))
                        .add(LootItem.lootTableItem(TideItems.TROUT).setWeight(100))
                        .add(LootItem.lootTableItem(Items.STICK).setWeight(80)
                                .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(0, 8), true)
                                        .when(LootItemRandomChanceCondition.randomChance(0.5f))))
                        .add(LootItem.lootTableItem(Items.COBBLESTONE).setWeight(90))
                        .add(LootItem.lootTableItem(Items.IRON_NUGGET).setWeight(40).setQuality(3)
                                .apply(SetItemCountFunction.setCount(
                                                UniformGenerator.between(0, 4), true)
                                        .when(LootItemRandomChanceCondition.randomChance(0.5f))))
                        .add(LootItem.lootTableItem(TideItems.BASS).setWeight(20))
                        .add(LootItem.lootTableItem(Items.WHEAT).setWeight(20).setQuality(1))
                        .add(LootItem.lootTableItem(Items.WHEAT_SEEDS).setWeight(15))
                        .add(LootItem.lootTableItem(Items.SAND).setWeight(12)
                                .apply(SetItemCountFunction.setCount(
                                        UniformGenerator.between(5, 20))))
                )
        );

        output.accept(
                TideLootTables.Crates.OVERWORLD_WATER_OCEAN,
                LootTable.lootTable().withPool(LootPool.lootPool()
                        .setRolls(UniformGenerator.between(7, 12))
                        .add(LootItem.lootTableItem(Items.SALMON).setWeight(45))
                        .add(LootItem.lootTableItem(TideItems.TROUT).setWeight(45))
                        .add(LootItem.lootTableItem(TideItems.TUNA).setWeight(45))
                        .add(LootItem.lootTableItem(Items.IRON_NUGGET).setWeight(23).setQuality(1))
                        .add(LootItem.lootTableItem(Items.GOLD_NUGGET).setWeight(15).setQuality(1))
                        .add(LootItem.lootTableItem(Items.IRON_INGOT).setWeight(8).setQuality(2))
                        .add(LootItem.lootTableItem(Items.GOLD_INGOT).setWeight(4).setQuality(2))
                        .add(LootItem.lootTableItem(Items.EMERALD).setWeight(3).setQuality(3))
                        .add(LootItem.lootTableItem(Items.FISHING_ROD).setWeight(4).setQuality(3)
                                .apply(SetItemDamageFunction.setDamage(
                                        UniformGenerator.between(0.4f, 1.0f))))
                )
        );

        output.accept(
                TideLootTables.Crates.OVERWORLD_WATER_UNDERGROUND,
                LootTable.lootTable().withPool(LootPool.lootPool()
                        .setRolls(UniformGenerator.between(7, 12))
                        .add(LootItem.lootTableItem(Items.STICK).setWeight(100)
                                .apply(SetItemCountFunction.setCount(ConstantValue.exactly(2), true)))
                        .add(LootItem.lootTableItem(Items.IRON_NUGGET).setWeight(70)
                                .apply(SetItemCountFunction.setCount(ConstantValue.exactly(3), true)))
                        .add(LootItem.lootTableItem(Items.COBBLESTONE).setWeight(60).setQuality(3)
                                .apply(SetItemCountFunction.setCount(
                                        UniformGenerator.between(1, 5), true)))
                        .add(LootItem.lootTableItem(Items.ANDESITE).setWeight(60))
                        .add(LootItem.lootTableItem(Items.GOLD_INGOT).setWeight(25).setQuality(1))
                        .add(LootItem.lootTableItem(Items.LAPIS_LAZULI).setWeight(20)
                                .apply(SetItemCountFunction.setCount(ConstantValue.exactly(2), true)))
                        .add(LootItem.lootTableItem(Items.STONE_PICKAXE).setWeight(9).setQuality(1)
                                .apply(SetItemDamageFunction.setDamage(
                                        UniformGenerator.between(0.1f, 1.0f)))
                                .apply(EnchantWithLevelsFunction.enchantWithLevels(UniformGenerator.between(8, 10))))
                )
        );

        output.accept(
                TideLootTables.Crates.OVERWORLD_WATER_DEEP,
                LootTable.lootTable().withPool(LootPool.lootPool()
                        .setRolls(UniformGenerator.between(7, 12))
                        .add(LootItem.lootTableItem(Items.IRON_NUGGET).setWeight(60))
                        .add(LootItem.lootTableItem(Items.RAW_COPPER).setWeight(45))
                        .add(LootItem.lootTableItem(Items.RAW_IRON).setWeight(45))
                        .add(LootItem.lootTableItem(Items.GOLD_INGOT).setWeight(25).setQuality(1))
                        .add(LootItem.lootTableItem(Items.LAPIS_LAZULI).setWeight(20)
                                .apply(SetItemCountFunction.setCount(ConstantValue.exactly(3), true)))
                        .add(LootItem.lootTableItem(Items.REDSTONE).setWeight(18))
                        .add(LootItem.lootTableItem(Items.AMETHYST_SHARD).setWeight(16))
                        .add(LootItem.lootTableItem(Items.EMERALD).setWeight(14).setQuality(1))
                        .add(LootItem.lootTableItem(Items.DIAMOND).setWeight(12).setQuality(2))
                        .add(LootItem.lootTableItem(Items.IRON_PICKAXE).setWeight(8).setQuality(2)
                                .apply(SetItemDamageFunction.setDamage(
                                                UniformGenerator.between(0.2f, 0.8f))
                                        .when(LootItemRandomChanceCondition.randomChance(0.8f)))
                                .apply(EnchantWithLevelsFunction.enchantWithLevels(
                                                UniformGenerator.between(20, 30))
                                        .when(LootItemRandomChanceCondition.randomChance(0.8f))))
                )
        );

        output.accept(
                TideLootTables.Crates.OVERWORLD_LAVA_SURFACE,
                LootTable.lootTable().withPool(LootPool.lootPool()
                        .setRolls(UniformGenerator.between(6, 9))
                        .add(LootItem.lootTableItem(Items.GOLD_NUGGET).setWeight(60))
                        .add(LootItem.lootTableItem(Items.FLINT).setWeight(60))
                        .add(LootItem.lootTableItem(Items.BONE).setWeight(60))
                        .add(LootItem.lootTableItem(Items.OBSIDIAN).setWeight(50))
                        .add(LootItem.lootTableItem(TideItems.OBSIDIAN_FRAGMENT).setWeight(50))
                        .add(LootItem.lootTableItem(Items.MAGMA_BLOCK).setWeight(35))

                )
        );

        output.accept(
                TideLootTables.Crates.OVERWORLD_LAVA_UNDERGROUND,
                LootTable.lootTable().withPool(LootPool.lootPool()
                        .setRolls(UniformGenerator.between(6, 9))
                        .add(LootItem.lootTableItem(Items.COAL).setWeight(60))
                        .add(LootItem.lootTableItem(Items.BONE).setWeight(60))
                        .add(LootItem.lootTableItem(Items.OBSIDIAN).setWeight(50))
                        .add(LootItem.lootTableItem(Items.CRYING_OBSIDIAN).setWeight(50))
                        .add(LootItem.lootTableItem(TideItems.OBSIDIAN_FRAGMENT).setWeight(40))
                        .add(LootItem.lootTableItem(Items.MAGMA_BLOCK).setWeight(20))
                        .add(LootItem.lootTableItem(Items.GOLD_INGOT).setWeight(10).setQuality(1))
                        .add(LootItem.lootTableItem(Items.DIAMOND).setWeight(8).setQuality(2))

                )
        );

        output.accept(
                TideLootTables.Crates.OVERWORLD_LAVA_DEEP,
                LootTable.lootTable().withPool(LootPool.lootPool()
                        .setRolls(UniformGenerator.between(8, 10))
                        .add(LootItem.lootTableItem(Items.IRON_INGOT).setWeight(40))
                        .add(LootItem.lootTableItem(Items.BONE).setWeight(40))
                        .add(LootItem.lootTableItem(Items.OBSIDIAN).setWeight(40))
                        .add(LootItem.lootTableItem(Items.CRYING_OBSIDIAN).setWeight(35))
                        .add(LootItem.lootTableItem(Items.COPPER_INGOT).setWeight(35))
                        .add(LootItem.lootTableItem(Items.GOLD_INGOT).setWeight(20).setQuality(1))
                        .add(LootItem.lootTableItem(Items.DIAMOND).setWeight(20).setQuality(2))

                )
        );

        output.accept(
                TideLootTables.Crates.NETHER_LAVA,
                LootTable.lootTable().withPool(LootPool.lootPool()
                        .setRolls(UniformGenerator.between(8, 10))
                        .add(LootItem.lootTableItem(TideItems.MAGMA_MACKEREL).setWeight(45))
                        .add(LootItem.lootTableItem(TideItems.ASHEN_PERCH).setWeight(45))
                        .add(LootItem.lootTableItem(Items.NETHER_BRICK).setWeight(40))
                        .add(LootItem.lootTableItem(Items.NETHER_WART).setWeight(40))
                        .add(LootItem.lootTableItem(Items.BLAZE_POWDER).setWeight(35))
                        .add(LootItem.lootTableItem(Items.MAGMA_CREAM).setWeight(35))
                        .add(LootItem.lootTableItem(Items.GOLD_INGOT).setWeight(20).setQuality(1))
                        .add(LootItem.lootTableItem(Items.GOLD_BLOCK).setWeight(8).setQuality(2))
                        .add(LootItem.lootTableItem(Items.NETHERITE_SCRAP).setWeight(6).setQuality(2))
                )
        );

        output.accept(
                TideLootTables.Crates.END_WATER,
                LootTable.lootTable().withPool(LootPool.lootPool()
                        .setRolls(UniformGenerator.between(6, 8))
                        .add(LootItem.lootTableItem(Items.ENDER_PEARL).setWeight(70))
                        .add(LootItem.lootTableItem(Items.CHORUS_FRUIT).setWeight(70))
                        .add(LootItem.lootTableItem(Items.POPPED_CHORUS_FRUIT).setWeight(55))
                        .add(LootItem.lootTableItem(Items.SHULKER_SHELL).setWeight(35).setQuality(1))
                        .add(LootItem.lootTableItem(Items.ELYTRA).setWeight(5).setQuality(4)
                                .apply(EnchantWithLevelsFunction.enchantWithLevels(ConstantValue.exactly(15))
                                        .when(LootItemRandomChanceCondition.randomChance(0.5f)))
                        )
                )
        );

        output.accept(
                TideLootTables.Crates.END_LAVA,
                LootTable.lootTable().withPool(LootPool.lootPool()
                        .setRolls(UniformGenerator.between(6, 8))
                        .add(LootItem.lootTableItem(Items.ENDER_PEARL).setWeight(70))
                        .add(LootItem.lootTableItem(Items.CHORUS_FRUIT).setWeight(60))
                        .add(LootItem.lootTableItem(Items.POPPED_CHORUS_FRUIT).setWeight(55))
                        .add(LootItem.lootTableItem(Items.SHULKER_SHELL).setWeight(40).setQuality(1))
                        .add(LootItem.lootTableItem(Items.ELYTRA).setWeight(5).setQuality(4)
                                .apply(EnchantWithLevelsFunction.enchantWithLevels(ConstantValue.exactly(30))))
                )
        );
    }
}