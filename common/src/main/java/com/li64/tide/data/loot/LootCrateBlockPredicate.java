package com.li64.tide.data.loot;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.li64.tide.registries.TideLootConditions;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParam;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

public record LootCrateBlockPredicate(Block block) implements LootItemCondition {
    public static final MapCodec<LootCrateBlockPredicate> CODEC = RecordCodecBuilder.mapCodec((instance) -> instance.group(
            BuiltInRegistries.BLOCK.byNameCodec().fieldOf("block").forGetter(LootCrateBlockPredicate::block)
    ).apply(instance, LootCrateBlockPredicate::new));

    public static Builder matches(Block block) {
        return () -> new LootCrateBlockPredicate(block);
    }

    @Override
    public @NotNull LootItemConditionType getType() {
        return TideLootConditions.LOOT_CRATE_BLOCK;
    }

    public boolean test(LootContext context) {
        BlockState blockState = context.getParamOrNull(LootContextParams.BLOCK_STATE);
        return blockState != null && blockState.is(block);
    }

    public @NotNull Set<LootContextParam<?>> getReferencedContextParams() {
        return Set.of(LootContextParams.BLOCK_STATE);
    }

    public static class Serializer implements net.minecraft.world.level.storage.loot.Serializer<LootCrateBlockPredicate> {
        public void serialize(JsonObject jsonObject, LootCrateBlockPredicate predicate, @NotNull JsonSerializationContext context) {
            jsonObject.addProperty("block", BuiltInRegistries.BLOCK.getKey(predicate.block()).toString());
        }

        public @NotNull LootCrateBlockPredicate deserialize(@NotNull JsonObject jsonObject, @NotNull JsonDeserializationContext context) {
            ResourceLocation location = new ResourceLocation(GsonHelper.getAsString(jsonObject, "block"));
            return new LootCrateBlockPredicate(BuiltInRegistries.BLOCK.get(location));
        }
    }
}
