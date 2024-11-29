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

public record TideFishingPredicate(Optional<Boolean> isLavaFishing, Optional<Boolean> usingMagneticBait, Optional<String> depthLayer) implements EntitySubPredicate {
    public static final MapCodec<TideFishingPredicate> CODEC = RecordCodecBuilder.mapCodec((instance) -> instance.group(
            Codec.BOOL.optionalFieldOf("is_lava_fishing").forGetter(TideFishingPredicate::isLavaFishing),
            Codec.BOOL.optionalFieldOf("using_magnetic_bait").forGetter(TideFishingPredicate::usingMagneticBait),
            Codec.STRING.optionalFieldOf("loot_layer").forGetter(TideFishingPredicate::depthLayer)
    ).apply(instance, TideFishingPredicate::new));

    public static TideFishingPredicate isLavaFishing(boolean lavaFishing) {
        return new TideFishingPredicate(Optional.of(lavaFishing), Optional.empty(), Optional.empty());
    }

    public static TideFishingPredicate usingMagneticBait(boolean magneticBait) {
        return new TideFishingPredicate(Optional.empty(), Optional.of(magneticBait), Optional.empty());
    }

    public static TideFishingPredicate depthLayer(DepthLayer layer) {
        return new TideFishingPredicate(Optional.empty(), Optional.empty(), Optional.of(layer.key));
    }

    public @NotNull MapCodec<TideFishingPredicate> codec() {
        return TideEntitySubPredicates.TIDE_FISHING_HOOK;
    }

    public boolean matches(@NotNull Entity entity, @NotNull ServerLevel level, @Nullable Vec3 position) {
        if (entity instanceof TideFishingHook hook) {
            if (this.isLavaFishing.isPresent()) return this.isLavaFishing.get() == hook.isInLava();
            if (this.usingMagneticBait.isPresent()) return this.usingMagneticBait.get() == hook.usingMagneticBait();
            if (this.depthLayer.isPresent()) {
                Optional<DepthLayer> layer = DepthLayer.fromKey(this.depthLayer.get());
                if (layer.isPresent()) return layer.get() == DepthLayer.getLayerAt(hook.getY());
            }
        }
        return false;
    }

    public Optional<Boolean> isLavaFishing() {
        return this.isLavaFishing;
    }
}
