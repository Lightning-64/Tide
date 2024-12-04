package com.li64.tide.data.loot;

import com.google.gson.*;
import com.li64.tide.registries.TideLootConditions;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParam;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public record IsNightPredicate() implements LootItemCondition {
    public static Builder isNight() {
        return IsNightPredicate::new;
    }

    @Override
    public @NotNull LootItemConditionType getType() {
        return TideLootConditions.IS_NIGHT;
    }

    @Override
    public boolean test(LootContext lootContext) {
        return lootContext.getLevel().isNight();
    }

    public @NotNull Set<LootContextParam<?>> getReferencedContextParams() {
        return Set.of();
    }

    public static class Serializer implements net.minecraft.world.level.storage.loot.Serializer<IsNightPredicate> {
        public void serialize(@NotNull JsonObject jsonObject, @NotNull IsNightPredicate predicate, @NotNull JsonSerializationContext context) {}

        public @NotNull IsNightPredicate deserialize(@NotNull JsonObject jsonObject, @NotNull JsonDeserializationContext context) {
            return new IsNightPredicate();
        }
    }
}
