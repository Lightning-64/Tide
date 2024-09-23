package com.li64.tide.loot.modifiers;

import com.li64.tide.registries.TideItems;
import com.mojang.serialization.MapCodec;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.neoforged.neoforge.common.loot.IGlobalLootModifier;
import net.neoforged.neoforge.common.loot.LootModifier;

public class CommonBaitModifier extends LootModifier {
    public static final MapCodec<CommonBaitModifier> CODEC = newCodec();

    public CommonBaitModifier() {
        super(new LootItemCondition[0]);
    }

    public CommonBaitModifier(LootItemCondition[] conditionsIn) {
        super(conditionsIn);
    }

    @Override
    protected ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot, LootContext context) {
        if (generatedLoot.isEmpty()) return generatedLoot;

        if (!(context.getQueriedLootTableId().toString().matches(BuiltInLootTables.UNDERWATER_RUIN_BIG.location().toString())
            && !(context.getQueriedLootTableId().toString().matches(BuiltInLootTables.UNDERWATER_RUIN_SMALL.location().toString())))) return generatedLoot;

        for (int i = 0; i < context.getRandom().nextInt(0, 3); i++) {
            ItemStack stack = TideItems.BAIT.getDefaultInstance();
            stack.setCount(context.getRandom().nextInt(1, 4));
            generatedLoot.add(stack);
        }
        return generatedLoot;
    }

    public static MapCodec<CommonBaitModifier> newCodec() {
        return MapCodec.unit(CommonBaitModifier::new);
    }

    @Override
    public MapCodec<? extends IGlobalLootModifier> codec() {
        return CODEC;
    }
}