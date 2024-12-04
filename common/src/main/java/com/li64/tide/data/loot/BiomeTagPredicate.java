package com.li64.tide.data.loot;

import com.li64.tide.registries.TideLootConditions;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParam;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

public record BiomeTagPredicate(TagKey<Biome> biomeTag) implements LootItemCondition {
    public static final MapCodec<BiomeTagPredicate> CODEC = RecordCodecBuilder.mapCodec((instance) ->
            instance.group(TagKey.codec(Registries.BIOME).fieldOf("tag")
                    .forGetter(BiomeTagPredicate::biomeTag)).apply(instance, BiomeTagPredicate::new));

    public static LootItemCondition.Builder checkTag(TagKey<Biome> biomeTag) {
        return () -> new BiomeTagPredicate(biomeTag);
    }

    @Override
    public @NotNull LootItemConditionType getType() {
        return TideLootConditions.BIOME_TAG_CHECK;
    }

    public boolean test(LootContext context) {
        Vec3 pos = context.getParamOrNull(LootContextParams.ORIGIN);
        return pos != null && context.getLevel().getBiome(new BlockPos(
                (int) pos.x(), (int) pos.y(), (int) pos.z())).is(biomeTag());
    }

    public @NotNull Set<LootContextParam<?>> getReferencedContextParams() {
        return Set.of(LootContextParams.ORIGIN);
    }
}
