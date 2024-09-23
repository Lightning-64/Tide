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
import org.jetbrains.annotations.NotNull;

public class FishingJunkModifier extends LootModifier {
    public static final MapCodec<FishingJunkModifier> CODEC = newCodec();

    public FishingJunkModifier() {
        super(new LootItemCondition[0]);
    }

    @Override
    protected @NotNull ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot, LootContext context) {
        if (generatedLoot.isEmpty()) return generatedLoot;
        if (!(context.getQueriedLootTableId().toString().matches(BuiltInLootTables.FISHING_JUNK.location().toString()))) return generatedLoot;

        if (context.getRandom().nextInt(10) == 0) {
            generatedLoot.set(0, TideItems.FISH_BONE.getDefaultInstance());
        }
        return generatedLoot;
    }

    public static MapCodec<FishingJunkModifier> newCodec() {
        return MapCodec.unit(FishingJunkModifier::new);
    }

    @Override
    public MapCodec<? extends IGlobalLootModifier> codec() {
        return CODEC;
    }
}