package com.li64.tide.data.triggers;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.advancements.critereon.ContextAwarePredicate;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.server.level.ServerPlayer;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class TideSimpleTrigger extends SimpleCriterionTrigger<TideSimpleTrigger.TriggerInstance> {
    public @NotNull Codec<TriggerInstance> codec() {
        return TriggerInstance.CODEC;
    }

    public void trigger(ServerPlayer player) {
        this.trigger(player, trigger -> true);
    }

    public record TriggerInstance(Optional<ContextAwarePredicate> player) implements SimpleInstance {
        public static final Codec<TriggerInstance> CODEC = RecordCodecBuilder.create(
                builder -> builder.group(EntityPredicate.ADVANCEMENT_CODEC.optionalFieldOf("player")
                        .forGetter(TriggerInstance::player)).apply(builder, TriggerInstance::new));
    }
}