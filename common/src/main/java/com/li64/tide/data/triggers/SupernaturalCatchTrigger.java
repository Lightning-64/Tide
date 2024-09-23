package com.li64.tide.data.triggers;

import com.li64.tide.data.TideTags;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.advancements.critereon.ContextAwarePredicate;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EntityType;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class SupernaturalCatchTrigger extends SimpleCriterionTrigger<SupernaturalCatchTrigger.TriggerInstance> {
    public @NotNull Codec<TriggerInstance> codec() {
        return TriggerInstance.CODEC;
    }

    public void trigger(ServerPlayer player, EntityType<?> fish) {
        this.trigger(player, trigger -> trigger.isCorrectFish(fish));
    }

    public record TriggerInstance(Optional<ContextAwarePredicate> player) implements SimpleInstance {
        public static final Codec<TriggerInstance> CODEC = RecordCodecBuilder.create(
                builder -> builder.group(EntityPredicate.ADVANCEMENT_CODEC.optionalFieldOf("player")
                        .forGetter(TriggerInstance::player)).apply(builder, TriggerInstance::new));

        public boolean isCorrectFish(EntityType<?> fish) {
            return fish.is(TideTags.Entities.FRESHWATER_FISH);
        }
    }
}