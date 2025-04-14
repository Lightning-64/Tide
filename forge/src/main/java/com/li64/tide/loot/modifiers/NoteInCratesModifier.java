package com.li64.tide.loot.modifiers;

import com.mojang.serialization.Codec;
import com.li64.tide.registries.TideItems;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.common.loot.LootModifier;
import org.jetbrains.annotations.NotNull;

public class NoteInCratesModifier extends LootModifier {
    public static final Codec<NoteInCratesModifier> CODEC = newCodec();

    public NoteInCratesModifier() {
        super(new LootItemCondition[0]);
    }

    @Override
    protected @NotNull ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot, LootContext context) {
        if (generatedLoot.isEmpty()) return generatedLoot;
        if (!(context.getQueriedLootTableId().toString().contains("crates/overworld/water_ocean")
            || context.getQueriedLootTableId().toString().contains("crates/overworld/water_river"))) return generatedLoot;

        generatedLoot.add(new ItemStack(TideItems.TORN_NOTE));
        return generatedLoot;
    }

    public static Codec<NoteInCratesModifier> newCodec() {
        return Codec.unit(NoteInCratesModifier::new);
    }

    @Override
    public Codec<? extends IGlobalLootModifier> codec() {
        return CODEC;
    }
}