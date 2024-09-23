package com.li64.tide.loot.modifiers;

import com.li64.tide.Tide;
import com.mojang.serialization.MapCodec;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.common.loot.LootModifier;
import com.li64.tide.registries.TideItems;

public class RareBaitModifier extends LootModifier {
    public static final MapCodec<RareBaitModifier> CODEC = newCodec();

    public RareBaitModifier() {
        super(new LootItemCondition[0]);
    }

    @Override
    protected ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot, LootContext context) {
        if (generatedLoot.isEmpty()) return generatedLoot;
        if (!(context.getQueriedLootTableId().toString().matches(BuiltInLootTables.BURIED_TREASURE.location().toString()))) return generatedLoot;

        for (int i = 0; i < context.getRandom().nextInt(1, 3); i++) {
            if (context.getRandom().nextInt(0, 4) == 0) {
                // Add lucky bait
                ItemStack stack = TideItems.LUCKY_BAIT.getDefaultInstance();
                stack.setCount(context.getRandom().nextInt(2, 4));
                generatedLoot.add(stack);
            } else {
                // Add magnetic bait
                ItemStack stack = TideItems.MAGNETIC_BAIT.getDefaultInstance();
                stack.setCount(context.getRandom().nextInt(4, 6));
                generatedLoot.add(stack);
            }
        }
        return generatedLoot;
    }

    public static MapCodec<RareBaitModifier> newCodec() {
        return MapCodec.unit(RareBaitModifier::new);
    }

    @Override
    public MapCodec<? extends IGlobalLootModifier> codec() {
        return CODEC;
    }
}