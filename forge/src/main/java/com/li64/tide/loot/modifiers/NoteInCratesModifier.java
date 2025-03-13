package com.li64.tide.loot.modifiers;

import com.li64.tide.registries.TideItems;
import com.mojang.serialization.MapCodec;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.server.network.Filterable;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.WrittenBookContent;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.common.loot.LootModifier;

import java.util.List;
import java.util.Random;

public class NoteInCratesModifier extends LootModifier {
    public static final MapCodec<NoteInCratesModifier> CODEC = newCodec();

    public NoteInCratesModifier() {
        super(new LootItemCondition[0]);
    }

    public NoteInCratesModifier(LootItemCondition[] conditionsIn) {
        super(conditionsIn);
    }

    @Override
    protected ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot, LootContext context) {
        if (generatedLoot.isEmpty()) return generatedLoot;
        if (!(context.getQueriedLootTableId().toString().contains("crates/overworld/water_ocean")
            || context.getQueriedLootTableId().toString().contains("crates/overworld/water_river"))) return generatedLoot;

        generatedLoot.add(new ItemStack(TideItems.TORN_NOTE));
        return generatedLoot;
    }

    public static MapCodec<NoteInCratesModifier> newCodec() {
        return MapCodec.unit(NoteInCratesModifier::new);
    }

    @Override
    public MapCodec<? extends IGlobalLootModifier> codec() {
        return CODEC;
    }
}