package com.li64.tide.data.loot;

import com.google.gson.*;
import com.li64.tide.registries.TideLootConditions;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParam;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public record BlockNearbyPredicate(TagKey<Block> blocks, int distance) implements LootItemCondition {
    public static Builder withinDistance(TagKey<Block> blocks, int distance) {
        return () -> new BlockNearbyPredicate(blocks, distance);
    }

    @Override
    public @NotNull LootItemConditionType getType() {
        return TideLootConditions.BLOCK_NEARBY;
    }

    public boolean test(LootContext context) {
        Vec3 pos = context.getParamOrNull(LootContextParams.ORIGIN);
        if (pos == null) return false;
        AABB boundingBox = AABB.ofSize(pos, 1.0, 1.0, 1.0).inflate(distance());
        return context.getLevel().getBlockStates(boundingBox).anyMatch(state -> state.is(blocks));
    }

    public @NotNull Set<LootContextParam<?>> getReferencedContextParams() {
        return Set.of(LootContextParams.ORIGIN);
    }

    public static class Serializer implements net.minecraft.world.level.storage.loot.Serializer<BlockNearbyPredicate> {
        public void serialize(JsonObject jsonObject, BlockNearbyPredicate predicate, @NotNull JsonSerializationContext context) {
            jsonObject.addProperty("blocks", predicate.blocks().location().toString());
            jsonObject.addProperty("distance", predicate.distance());
        }

        public @NotNull BlockNearbyPredicate deserialize(@NotNull JsonObject jsonObject, @NotNull JsonDeserializationContext context) {
            ResourceLocation location = new ResourceLocation(GsonHelper.getAsString(jsonObject, "blocks"));
            int distance = jsonObject.get("distance").getAsInt();
            return new BlockNearbyPredicate(TagKey.create(Registries.BLOCK, location), distance);
        }
    }
}
