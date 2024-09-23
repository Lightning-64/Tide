package com.li64.tide.data;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntryContainer;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntryType;
import net.minecraft.world.level.storage.loot.entries.LootPoolSingletonContainer;
import net.minecraft.world.level.storage.loot.functions.LootItemFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;

import java.util.function.Consumer;

public class TideLootItem extends LootPoolSingletonContainer {
    private final Item item;

    protected TideLootItem(Item item, int weight, int quality, LootItemCondition[] lootItemConditions, LootItemFunction[] lootItemFunctions) {
        super(weight, quality, lootItemConditions, lootItemFunctions);
        this.item = item;
    }

    public static LootPoolEntryContainer.Builder<?> entry(ItemLike item) {
        return simpleBuilder((weight, quality, conditions, functions) ->
                new TideLootItem(item.asItem(), weight, quality, conditions, functions));
    }

    @Override
    protected void createItemStack(Consumer<ItemStack> consumer, LootContext lootContext) {
        consumer.accept(item.getDefaultInstance());
    }

    @Override
    public LootPoolEntryType getType() {
        return null;
    }
}