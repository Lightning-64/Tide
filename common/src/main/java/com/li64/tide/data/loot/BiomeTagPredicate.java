package com.li64.tide.data.loot;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.li64.tide.registries.TideLootConditions;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.util.GsonHelper;
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

    public static class Serializer implements net.minecraft.world.level.storage.loot.Serializer<BiomeTagPredicate> {
        public void serialize(JsonObject jsonObject, BiomeTagPredicate tagCheck, @NotNull JsonSerializationContext context) {
            jsonObject.addProperty("tag", tagCheck.biomeTag().location().toString());
        }

        public @NotNull BiomeTagPredicate deserialize(@NotNull JsonObject jsonObject, @NotNull JsonDeserializationContext context) {
            ResourceLocation location = new ResourceLocation(GsonHelper.getAsString(jsonObject, "tag"));
            return new BiomeTagPredicate(TagKey.create(Registries.BIOME, location));
        }
    }
}
