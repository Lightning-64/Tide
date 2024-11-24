package com.li64.tide.data.loot;

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

public record TideFishingPredicate(Optional<Boolean> isLavaFishing, Optional<Boolean> usingMagneticBait) implements EntitySubPredicate {
    public static final MapCodec<TideFishingPredicate> CODEC = RecordCodecBuilder.mapCodec((instance) -> instance.group(
            Codec.BOOL.optionalFieldOf("is_lava_fishing").forGetter(TideFishingPredicate::isLavaFishing),
            Codec.BOOL.optionalFieldOf("using_magnetic_bait").forGetter(TideFishingPredicate::usingMagneticBait)
    ).apply(instance, TideFishingPredicate::new));

    public static TideFishingPredicate isLavaFishing(boolean lavaFishing) {
        return new TideFishingPredicate(Optional.of(lavaFishing), Optional.of(false));
    }

    public static TideFishingPredicate usingMagneticBait(boolean magneticBait) {
        return new TideFishingPredicate(Optional.of(false), Optional.of(magneticBait));
    }

    public @NotNull MapCodec<TideFishingPredicate> codec() {
        return TideEntitySubPredicates.TIDE_FISHING_HOOK;
    }

    public boolean matches(@NotNull Entity entity, @NotNull ServerLevel level, @Nullable Vec3 position) {
        if (this.isLavaFishing.isEmpty() || this.usingMagneticBait.isEmpty()) return false;
        else if (entity instanceof TideFishingHook hook) {
            return (this.isLavaFishing.get() && hook.isInLava()) || (this.usingMagneticBait.get() && hook.usingMagneticBait());
        } else return false;
    }

    public Optional<Boolean> isLavaFishing() {
        return this.isLavaFishing;
    }
}
