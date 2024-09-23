package com.li64.tide.datagen.providers.loot;

import com.li64.tide.data.TideLootTables;
import com.li64.tide.registries.TideItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.SimpleFabricLootTableProvider;
import net.minecraft.advancements.critereon.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.SmeltItemFunction;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.predicates.*;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;

import java.util.function.BiConsumer;

public class TideEntityLootProvider extends SimpleFabricLootTableProvider {
    protected static final EntityPredicate.Builder ENTITY_ON_FIRE = EntityPredicate.Builder.entity()
            .flags(EntityFlagsPredicate.Builder.flags().setOnFire(true).build());

    public TideEntityLootProvider(FabricDataOutput output) {
        super(output, LootContextParamSets.ENTITY);
    }

    @Override
    public void generate(BiConsumer<ResourceLocation, LootTable.Builder> output) {
        output.accept(TideLootTables.Entities.TROUT, simpleFishLootTable(TideItems.TROUT));
        output.accept(TideLootTables.Entities.BASS, simpleFishLootTable(TideItems.BASS));
        output.accept(TideLootTables.Entities.YELLOW_PERCH, simpleFishLootTable(TideItems.YELLOW_PERCH));
        output.accept(TideLootTables.Entities.BLUEGILL, simpleFishLootTable(TideItems.BLUEGILL));
        output.accept(TideLootTables.Entities.MINT_CARP, simpleFishLootTable(TideItems.MINT_CARP));
        output.accept(TideLootTables.Entities.PIKE, simpleFishLootTable(TideItems.PIKE));
        output.accept(TideLootTables.Entities.GUPPY, simpleFishLootTable(TideItems.GUPPY));
        output.accept(TideLootTables.Entities.CATFISH, simpleFishLootTable(TideItems.CATFISH));
        output.accept(TideLootTables.Entities.CLAYFISH, simpleFishLootTable(TideItems.CLAYFISH));

        output.accept(TideLootTables.Entities.TUNA, simpleFishLootTable(TideItems.TUNA));
        output.accept(TideLootTables.Entities.OCEAN_PERCH, simpleFishLootTable(TideItems.OCEAN_PERCH));
        output.accept(TideLootTables.Entities.MACKEREL, simpleFishLootTable(TideItems.MACKEREL));
        output.accept(TideLootTables.Entities.ANGELFISH, simpleFishLootTable(TideItems.ANGELFISH));
        output.accept(TideLootTables.Entities.BARRACUDA, simpleFishLootTable(TideItems.BARRACUDA));
        output.accept(TideLootTables.Entities.SAILFISH, simpleFishLootTable(TideItems.SAILFISH));
    }

    public LootTable.Builder simpleFishLootTable(Item fishItem) {
        return LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1.0f))
                        .add(LootItem.lootTableItem(fishItem)
                                .when(LootItemKilledByPlayerCondition.killedByPlayer())
                                .apply(SmeltItemFunction.smelted().when(LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS, ENTITY_ON_FIRE)))))
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1.0f))
                        .add(LootItem.lootTableItem(Items.BONE_MEAL))
                        .when(LootItemRandomChanceCondition.randomChance(0.1f)));
    }
}