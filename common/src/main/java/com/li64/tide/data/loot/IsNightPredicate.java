package com.li64.tide.data.loot;

import com.li64.tide.registries.TideLootConditions;
import com.mojang.serialization.MapCodec;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;
import org.jetbrains.annotations.NotNull;

public record IsNightPredicate() implements LootItemCondition {
    public static final MapCodec<IsNightPredicate> CODEC = MapCodec.unit(IsNightPredicate::new);

    public static Builder isNight() {
        return IsNightPredicate::new;
    }

    @Override
    public @NotNull LootItemConditionType getType() {
        return TideLootConditions.IS_NIGHT;
    }

    @Override
    public boolean test(LootContext lootContext) {
        return lootContext.getLevel().isMoonVisible();
    }
}
