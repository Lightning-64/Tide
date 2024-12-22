package com.li64.tide.data.loot;

import com.google.gson.*;
import com.li64.tide.registries.TideLootConditions;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParam;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public record MoonPhasePredicate(Optional<List<Integer>> phases) implements LootItemCondition {
    public static final MapCodec<MoonPhasePredicate> CODEC = RecordCodecBuilder.mapCodec((instance) -> instance.group(
            Codec.INT.listOf().optionalFieldOf("any_of").forGetter(MoonPhasePredicate::phases)
    ).apply(instance, MoonPhasePredicate::new));

    public static LootItemCondition.Builder anyOf(int... phases) {
        return anyOf(Arrays.stream(phases).boxed().toList());
    }

    public static LootItemCondition.Builder anyOf(List<Integer> phases) {
        return () -> new MoonPhasePredicate(Optional.ofNullable(phases));
    }

    @Override
    public @NotNull LootItemConditionType getType() {
        return TideLootConditions.MOON_PHASE;
    }

    @Override
    public boolean test(LootContext lootContext) {
        return phases.map(phases -> phases.stream()
                .anyMatch(phase -> phase == lootContext.getLevel().getMoonPhase()))
                .orElse(false);
    }

    public @NotNull Set<LootContextParam<?>> getReferencedContextParams() {
        return Set.of();
    }

    public static class Serializer implements net.minecraft.world.level.storage.loot.Serializer<MoonPhasePredicate> {
        public void serialize(JsonObject jsonObject, MoonPhasePredicate predicate, @NotNull JsonSerializationContext context) {
            List<Integer> phases = predicate.phases().orElse(List.of());
            JsonArray array = new JsonArray(phases.size());
            phases.forEach(array::add);
            jsonObject.add("any_of", array);
        }

        public @NotNull MoonPhasePredicate deserialize(@NotNull JsonObject jsonObject, @NotNull JsonDeserializationContext context) {
            Optional<List<Integer>> phases = jsonObject.has("any_of") ?
                    Optional.of(jsonObject.get("any_of").getAsJsonArray()
                        .asList().stream()
                        .map(JsonElement::getAsInt).toList()) : Optional.empty();
            return new MoonPhasePredicate(phases);
        }
    }
}
