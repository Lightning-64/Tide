package com.li64.tide.data.loot;

import com.li64.tide.registries.TideLootConditions;
import com.li64.tide.registries.entities.misc.fishing.TideFishingHook;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public record MoonPhasePredicate(Optional<List<Integer>> phases) implements LootItemCondition {
    public static final MapCodec<MoonPhasePredicate> CODEC = RecordCodecBuilder.mapCodec((instance) -> instance.group(
            Codec.INT.listOf().optionalFieldOf("any_of").forGetter(MoonPhasePredicate::phases)
    ).apply(instance, MoonPhasePredicate::new));

    public static LootItemCondition.Builder anyOf(int... phases) {
        return () -> new MoonPhasePredicate(Optional.of(Arrays.stream(phases).boxed().toList()));
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
}
