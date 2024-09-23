package com.li64.tide.data.triggers;

import com.google.gson.JsonObject;
import com.li64.tide.Tide;
import com.li64.tide.data.TideTags;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.advancements.critereon.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EntityType;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class SupernaturalCatchTrigger extends SimpleCriterionTrigger<SupernaturalCatchTrigger.TriggerInstance> {
    static final ResourceLocation ID = Tide.resource("supernatural_catch");

    public ResourceLocation getId() {
        return ID;
    }

    public void trigger(ServerPlayer player, EntityType<?> fish) {
        this.trigger(player, instance -> instance.isCorrectFish(fish));
    }

    @Override
    protected TriggerInstance createInstance(JsonObject json, ContextAwarePredicate predicate, DeserializationContext context) {
        return new TriggerInstance(ID, predicate);
    }

    public static class TriggerInstance extends AbstractCriterionTriggerInstance {

        public TriggerInstance(ResourceLocation location, ContextAwarePredicate context) {
            super(location, context);
        }

        public ResourceLocation getCriterion() {
            return SupernaturalCatchTrigger.ID;
        }

        public boolean isCorrectFish(EntityType<?> fish) {
            return fish.is(TideTags.Entities.FRESHWATER_FISH);
        }

        public JsonObject serializeToJson(SerializationContext context) {
            return new JsonObject();
        }
    }
}