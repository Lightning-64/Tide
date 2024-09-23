package com.li64.tide.loot.modifiers;

import com.mojang.serialization.MapCodec;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.common.loot.LootModifier;
import com.li64.tide.registries.TideItems;

public class UncommonBaitModifier extends LootModifier {
    public static final MapCodec<UncommonBaitModifier> CODEC = newCodec();

    public UncommonBaitModifier() {
        super(new LootItemCondition[0]);
    }

    public UncommonBaitModifier(LootItemCondition[] conditionsIn) {
        super(conditionsIn);
    }

    @Override
    protected ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot, LootContext context) {
        if (generatedLoot.isEmpty()) return generatedLoot;
        if (!(context.getQueriedLootTableId().toString().matches(BuiltInLootTables.SHIPWRECK_SUPPLY.location().toString()))) return generatedLoot;

        for (int i = 0; i < context.getRandom().nextInt(1, 3); i++) {
            if (context.getRandom().nextInt(0, 3) == 0) {
                // Add regular bait
                ItemStack stack = TideItems.BAIT.getDefaultInstance();
                stack.setCount(context.getRandom().nextInt(1, 4));
                generatedLoot.add(stack);
            } else {
                // Add lucky bait
                ItemStack stack = TideItems.LUCKY_BAIT.getDefaultInstance();
                stack.setCount(context.getRandom().nextInt(2, 4));
                generatedLoot.add(stack);
            }
        }
        return generatedLoot;
    }

    public static MapCodec<UncommonBaitModifier> newCodec() {
        return MapCodec.unit(UncommonBaitModifier::new);
    }

    @Override
    public MapCodec<? extends IGlobalLootModifier> codec() {
        return CODEC;
    }
}