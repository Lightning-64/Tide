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

public class FishingTreasureModifier extends LootModifier {
    public static final MapCodec<FishingTreasureModifier> CODEC = newCodec();

    public FishingTreasureModifier() {
        super(new LootItemCondition[0]);
    }

    public FishingTreasureModifier(LootItemCondition[] conditionsIn) {
        super(conditionsIn);
    }

    @Override
    protected ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot, LootContext context) {
        if (generatedLoot.isEmpty()) return generatedLoot;
        if (!(context.getQueriedLootTableId().toString().matches(BuiltInLootTables.FISHING_TREASURE.location().toString()))) return generatedLoot;

        if (context.getRandom().nextInt(13) == 0) {
            generatedLoot.set(0, TideItems.TWILIGHT_SCALE.getDefaultInstance());
        }
        if (context.getRandom().nextInt(13) == 0) {
            generatedLoot.set(0, TideItems.SPECTRAL_SCALE.getDefaultInstance());
        }
        return generatedLoot;
    }

    public static MapCodec<FishingTreasureModifier> newCodec() {
        return MapCodec.unit(FishingTreasureModifier::new);
    }

    @Override
    public MapCodec<? extends IGlobalLootModifier> codec() {
        return CODEC;
    }
}