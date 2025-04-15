package com.li64.tide.loot.modifiers;

import com.mojang.serialization.MapCodec;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.neoforged.neoforge.common.loot.IGlobalLootModifier;
import net.neoforged.neoforge.common.loot.LootModifier;
import org.jetbrains.annotations.NotNull;

public class NoteInCratesModifier extends LootModifier {
    public static final MapCodec<NoteInCratesModifier> CODEC = newCodec();

    public NoteInCratesModifier() {
        super(new LootItemCondition[0]);
    }

    @Override
    protected @NotNull ObjectArrayList<ItemStack> doApply(@NotNull ObjectArrayList<ItemStack> generatedLoot, @NotNull LootContext context) {
        return generatedLoot;
    }

    public static MapCodec<NoteInCratesModifier> newCodec() {
        return MapCodec.unit(NoteInCratesModifier::new);
    }

    @Override
    public @NotNull MapCodec<? extends IGlobalLootModifier> codec() {
        return CODEC;
    }
}