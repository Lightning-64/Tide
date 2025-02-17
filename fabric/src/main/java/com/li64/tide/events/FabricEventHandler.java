package com.li64.tide.events;


import com.li64.tide.Tide;
import com.li64.tide.data.commands.JournalCommand;
import com.li64.tide.data.TidePlayer;
import com.li64.tide.registries.TideItems;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.entity.event.v1.ServerEntityWorldChangeEvents;
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.network.Filterable;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.WrittenBookContent;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.SetComponentsFunction;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

import java.util.List;

public class FabricEventHandler {
    public static void init() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) ->
                new JournalCommand(dispatcher, registryAccess));

        ServerPlayerEvents.COPY_FROM.register((oldPlayer, newPlayer, alive) -> {
            CompoundTag data = ((TidePlayer) oldPlayer).tide$getTidePlayerData();
            ((TidePlayer) newPlayer).tide$setTidePlayerData(data);
        });

        ServerTickEvents.END_SERVER_TICK.register((server -> server.getPlayerList()
                .getPlayers().forEach((TideEventHandler::updateFishingJournal))));

        ServerEntityWorldChangeEvents.AFTER_PLAYER_CHANGE_WORLD.register((player, origin, destination) -> {
            if (destination.dimension() == Level.NETHER) TideEventHandler.onEnterNether(player);
        });

        ServerPlayConnectionEvents.JOIN.register((handler, sender, server) ->
                TideEventHandler.onPlayerJoinWorld(handler.getPlayer()));

        LootTableEvents.MODIFY.register((key, tableBuilder, source, registries) -> {
            if (key == BuiltInLootTables.FISHING) {
                if (Tide.CONFIG.general.crateWeight > 0)
                    tableBuilder.modifyPools(builder -> builder.add(Tide.getCrateFishingEntry()));
            }

            if (key == BuiltInLootTables.FISHING_JUNK) {
                tableBuilder.modifyPools(builder -> builder
                        .add(LootItem.lootTableItem(TideItems.FISH_BONE).setWeight(8)));
            }

            if (key == BuiltInLootTables.UNDERWATER_RUIN_BIG || key == BuiltInLootTables.UNDERWATER_RUIN_SMALL) {
                tableBuilder.pool(new LootPool.Builder().setRolls(UniformGenerator.between(1, 2))
                        .add(LootItem.lootTableItem(TideItems.BAIT).setWeight(20)
                                .apply(SetItemCountFunction.setCount(ConstantValue.exactly(4))))
                        .build()
                );
            }

            if (key == BuiltInLootTables.SHIPWRECK_SUPPLY) {
                tableBuilder.pool(new LootPool.Builder().setRolls(UniformGenerator.between(1, 2))
                        .add(LootItem.lootTableItem(TideItems.BAIT).setWeight(10)
                                .apply(SetItemCountFunction.setCount(ConstantValue.exactly(4))))
                        .add(LootItem.lootTableItem(TideItems.LUCKY_BAIT).setWeight(10)
                                .apply(SetItemCountFunction.setCount(ConstantValue.exactly(4))))
                        .build()
                );
            }

            if (key == BuiltInLootTables.BURIED_TREASURE) {
                tableBuilder.pool(new LootPool.Builder().setRolls(ConstantValue.exactly(2))
                        .add(LootItem.lootTableItem(TideItems.LUCKY_BAIT).setWeight(5)
                                .apply(SetItemCountFunction.setCount(ConstantValue.exactly(4))))
                        .add(LootItem.lootTableItem(TideItems.MAGNETIC_BAIT).setWeight(5)
                                .apply(SetItemCountFunction.setCount(ConstantValue.exactly(6))))
                        .build()
                );
            }

            if (key.location().toString().contains("crates/overworld/water_ocean")
                    || key.location().toString().contains("crates/overworld/water_river")) {

                tableBuilder.pool(new LootPool.Builder().setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(Items.WRITTEN_BOOK)
                                .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1)))
                                .apply(SetComponentsFunction.setComponent(
                                    DataComponents.WRITTEN_BOOK_CONTENT,
                                    new WrittenBookContent(
                                        Filterable.passThrough(Component.translatable("note.tide.title").getString()),
                                        Component.translatable("note.tide.author").getString(),
                                        0,
                                        List.of(Filterable.passThrough(Component.translatable("note.tide.midas_fish.contents"))),
                                        true)
                                ))
                        ).add(LootItem.lootTableItem(Items.WRITTEN_BOOK)
                                .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1)))
                                .apply(SetComponentsFunction.setComponent(
                                            DataComponents.WRITTEN_BOOK_CONTENT,
                                            new WrittenBookContent(
                                                    Filterable.passThrough(Component.translatable("note.tide.title").getString()),
                                                    Component.translatable("note.tide.author").getString(),
                                                    0,
                                                    List.of(Filterable.passThrough(Component.translatable("note.tide.voidseeker.contents"))),
                                                    true
                                            )
                                ))
                        ).add(LootItem.lootTableItem(Items.WRITTEN_BOOK)
                                .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1)))
                                .apply(SetComponentsFunction.setComponent(
                                        DataComponents.WRITTEN_BOOK_CONTENT,
                                        new WrittenBookContent(
                                                Filterable.passThrough(Component.translatable("note.tide.title").getString()),
                                                Component.translatable("note.tide.author").getString(),
                                                0,
                                                List.of(Filterable.passThrough(Component.translatable("note.tide.shooting_starfish.contents"))),
                                                true
                                        )
                                ))
                        ).add(LootItem.lootTableItem(Items.WRITTEN_BOOK)
                                .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1)))
                                .apply(SetComponentsFunction.setComponent(
                                        DataComponents.WRITTEN_BOOK_CONTENT,
                                        new WrittenBookContent(
                                                Filterable.passThrough(Component.translatable("note.tide.title").getString()),
                                                Component.translatable("note.tide.author").getString(),
                                                0,
                                                List.of(Filterable.passThrough(Component.translatable("note.tide.aquathorn.contents"))),
                                                true
                                        )
                                ))
                        ).add(LootItem.lootTableItem(Items.WRITTEN_BOOK)
                                .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1)))
                                .apply(SetComponentsFunction.setComponent(
                                        DataComponents.WRITTEN_BOOK_CONTENT,
                                        new WrittenBookContent(
                                                Filterable.passThrough(Component.translatable("note.tide.title").getString()),
                                                Component.translatable("note.tide.author").getString(),
                                                0,
                                                List.of(Filterable.passThrough(Component.translatable("note.tide.windbass.contents"))),
                                                true
                                        )
                                ))
                        ).build()
                );
            }
        });
    }
}
