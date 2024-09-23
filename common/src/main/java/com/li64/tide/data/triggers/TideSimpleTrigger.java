package com.li64.tide.data.triggers;

import com.google.gson.JsonObject;
import com.li64.tide.Tide;
import net.minecraft.advancements.critereon.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;

public class TideSimpleTrigger extends SimpleCriterionTrigger<TideSimpleTrigger.TriggerInstance> {
    final ResourceLocation id;

    public TideSimpleTrigger(String idName) {
        id = Tide.resource(idName);
    }

    public ResourceLocation getId() {
        return id;
    }

    public void trigger(ServerPlayer player) {
        this.trigger(player, instance -> true);
    }

    @Override
    protected TriggerInstance createInstance(JsonObject json, ContextAwarePredicate predicate, DeserializationContext context) {
        return new TriggerInstance(id, predicate);
    }

    public static class TriggerInstance extends AbstractCriterionTriggerInstance {
        final ResourceLocation id;

        public TriggerInstance(ResourceLocation id, ContextAwarePredicate context) {
            super(id, context);
            this.id = id;
        }

        public ResourceLocation getCriterion() {
            return id;
        }

        public JsonObject serializeToJson(SerializationContext context) {
            return new JsonObject();
        }
    }
}