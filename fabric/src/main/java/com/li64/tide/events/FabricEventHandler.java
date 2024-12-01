package com.li64.tide.events;

import com.li64.tide.Tide;
import com.li64.tide.data.commands.JournalCommand;
import com.li64.tide.data.TideEntity;
import com.li64.tide.registries.TideItems;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.entity.event.v1.ServerEntityWorldChangeEvents;
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.loot.v2.LootTableEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.functions.SetNbtFunction;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

import java.util.ArrayList;
import java.util.List;

public class FabricEventHandler {
    public static void init() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) ->
                new JournalCommand(dispatcher, registryAccess));

        ServerPlayerEvents.COPY_FROM.register((oldPlayer, newPlayer, alive) -> {
            CompoundTag data = ((TideEntity) oldPlayer).getTidePlayerData();
            ((TideEntity) newPlayer).setTidePlayerData(data);
        });

        ServerTickEvents.END_SERVER_TICK.register((server -> server.getPlayerList()
                .getPlayers().forEach((TideEventHandler::updateFishingJournal))));

        ServerEntityWorldChangeEvents.AFTER_PLAYER_CHANGE_WORLD.register((player, origin, destination) -> {
            if (destination.dimension() == Level.NETHER) TideEventHandler.onEnterNether(player);
        });

        ServerPlayConnectionEvents.JOIN.register((handler, sender, server) ->
                TideEventHandler.onPlayerJoinWorld(handler.getPlayer()));

        LootTableEvents.MODIFY.register((resourceManager, lootManager, id, tableBuilder, source) -> {
            if (id.toString().matches(BuiltInLootTables.FISHING.toString())) {
                tableBuilder.modifyPools(builder -> builder.add(Tide.getCrateFishingEntry()));
            }

            if (id.toString().matches(BuiltInLootTables.FISHING_JUNK.toString())) {
                tableBuilder.modifyPools(builder -> builder
                        .add(LootItem.lootTableItem(TideItems.FISH_BONE).setWeight(8)));
            }

            if (id.toString().matches(BuiltInLootTables.UNDERWATER_RUIN_BIG.toString()) || id.toString().matches(BuiltInLootTables.UNDERWATER_RUIN_SMALL.toString())) {
                tableBuilder.pool(new LootPool.Builder().setRolls(UniformGenerator.between(1, 2))
                        .add(LootItem.lootTableItem(TideItems.BAIT).setWeight(20)
                                .apply(SetItemCountFunction.setCount(ConstantValue.exactly(4))))
                        .build()
                );
            }

            if (id.toString().matches(BuiltInLootTables.SHIPWRECK_SUPPLY.toString())) {
                tableBuilder.pool(new LootPool.Builder().setRolls(UniformGenerator.between(1, 2))
                        .add(LootItem.lootTableItem(TideItems.BAIT).setWeight(10)
                                .apply(SetItemCountFunction.setCount(ConstantValue.exactly(4))))
                        .add(LootItem.lootTableItem(TideItems.LUCKY_BAIT).setWeight(10)
                                .apply(SetItemCountFunction.setCount(ConstantValue.exactly(4))))
                        .build()
                );
            }

            if (id.toString().matches(BuiltInLootTables.BURIED_TREASURE.toString())) {
                tableBuilder.pool(new LootPool.Builder().setRolls(ConstantValue.exactly(2))
                        .add(LootItem.lootTableItem(TideItems.LUCKY_BAIT).setWeight(5)
                                .apply(SetItemCountFunction.setCount(ConstantValue.exactly(4))))
                        .add(LootItem.lootTableItem(TideItems.MAGNETIC_BAIT).setWeight(5)
                                .apply(SetItemCountFunction.setCount(ConstantValue.exactly(6))))
                        .build()
                );
            }

            if (id.toString().contains("crates/overworld/water_ocean")
                    || id.toString().contains("crates/overworld/water_river")) {

                List<CompoundTag> tags = new ArrayList<>();
                tags.add(new CompoundTag());
                tags.add(new CompoundTag());
                tags.add(new CompoundTag());

                ListTag pages1 = new ListTag();
                pages1.add(StringTag.valueOf(Component.Serializer.toJson(Component.translatable("note.tide.midas_fish.contents"))));

                ListTag pages2 = new ListTag();
                pages2.add(StringTag.valueOf(Component.Serializer.toJson(Component.translatable("note.tide.voidseeker.contents"))));

                ListTag pages3 = new ListTag();
                pages3.add(StringTag.valueOf(Component.Serializer.toJson(Component.translatable("note.tide.shooting_starfish.contents"))));

                tags.get(0).put("pages", pages1);
                tags.get(1).put("pages", pages2);
                tags.get(2).put("pages", pages3);

                tags.forEach(tag -> tag.putString("title", Component.translatable("note.tide.title").getString()));
                tags.forEach(tag -> tag.putString("author", Component.translatable("note.tide.author").getString()));

                tableBuilder.pool(new LootPool.Builder().setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(Items.WRITTEN_BOOK)
                                .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1)))
                                .apply(SetNbtFunction.setTag(tags.get(0)))
                        ).add(LootItem.lootTableItem(Items.WRITTEN_BOOK)
                                .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1)))
                                .apply(SetNbtFunction.setTag(tags.get(1)))
                        ).add(LootItem.lootTableItem(Items.WRITTEN_BOOK)
                                .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1)))
                                .apply(SetNbtFunction.setTag(tags.get(2)))
                        ).build()
                );
            }
        });
    }
}
