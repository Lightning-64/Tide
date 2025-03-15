package com.li64.tide.data.loot;

import com.li64.tide.registries.TideLootConditions;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.util.context.ContextKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootContext;
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
        BlockState blockState = context.getOptionalParameter(LootContextParams.BLOCK_STATE);
        return blockState != null && blockState.is(block);
    }

    @Override
    public @NotNull Set<ContextKey<?>> getReferencedContextParams() {
        return Set.of(LootContextParams.BLOCK_STATE);
    }
}
