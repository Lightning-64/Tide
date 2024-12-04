package com.li64.tide.data.loot;

import com.li64.tide.registries.TideEntitySubPredicates;
import com.li64.tide.registries.entities.misc.fishing.TideFishingHook;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.advancements.critereon.EntitySubPredicate;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public record FishingStatsPredicate(Optional<Integer> luck) implements EntitySubPredicate {
    public static final MapCodec<FishingStatsPredicate> CODEC = RecordCodecBuilder.mapCodec((instance) -> instance.group(
            Codec.INT.optionalFieldOf("luck").forGetter(FishingStatsPredicate::luck)
    ).apply(instance, FishingStatsPredicate::new));

    public static FishingStatsPredicate luckOf(int luck) {
        return new FishingStatsPredicate(Optional.of(luck));
    }

    public @NotNull MapCodec<FishingStatsPredicate> codec() {
        return TideEntitySubPredicates.FISHING_STATS;
    }

    public boolean matches(@NotNull Entity entity, @NotNull ServerLevel level, @Nullable Vec3 position) {
        if (entity instanceof TideFishingHook hook) {
            if (this.luck.isPresent()) return this.luck.get() <= hook.getLuck();
        }
        return false;
    }
}
