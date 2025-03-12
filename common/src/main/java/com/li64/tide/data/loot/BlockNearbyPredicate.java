package com.li64.tide.data.loot;

import com.li64.tide.registries.TideLootConditions;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParam;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

public record BlockNearbyPredicate(TagKey<Block> blocks, int distance) implements LootItemCondition {
    public static final MapCodec<BlockNearbyPredicate> CODEC = RecordCodecBuilder.mapCodec((instance) -> instance.group(
            TagKey.codec(Registries.BLOCK).fieldOf("blocks").forGetter(BlockNearbyPredicate::blocks),
            ExtraCodecs.intRange(0, 50).fieldOf("distance").forGetter(BlockNearbyPredicate::distance)
    ).apply(instance, BlockNearbyPredicate::new));

    public static Builder withinDistance(TagKey<Block> blocks, int distance) {
        return () -> new BlockNearbyPredicate(blocks, distance);
    }

    @Override
    public @NotNull LootItemConditionType getType() {
        return TideLootConditions.BIOME_TAG_CHECK;
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
}
