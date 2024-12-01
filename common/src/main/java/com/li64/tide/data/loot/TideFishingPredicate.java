package com.li64.tide.data.loot;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.li64.tide.registries.entities.misc.fishing.TideFishingHook;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.advancements.critereon.EntitySubPredicate;
import net.minecraft.advancements.critereon.FishingHookPredicate;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public record TideFishingPredicate(Optional<Boolean> isLavaFishing, Optional<Boolean> usingMagneticBait) implements EntitySubPredicate {
    public static TideFishingPredicate isLavaFishing(boolean lavaFishing) {
        return new TideFishingPredicate(Optional.of(lavaFishing), Optional.of(false));
    }

    public static TideFishingPredicate usingMagneticBait(boolean magneticBait) {
        return new TideFishingPredicate(Optional.of(false), Optional.of(magneticBait));
    }

    public boolean matches(@NotNull Entity entity, @NotNull ServerLevel level, @Nullable Vec3 position) {
        if (this.isLavaFishing.isEmpty() || this.usingMagneticBait.isEmpty()) return false;
        else if (entity instanceof TideFishingHook hook) {
            return (this.isLavaFishing.get() && hook.isInLava()) || (this.usingMagneticBait.get() && hook.usingMagneticBait());
        } else return false;
    }

    public static TideFishingPredicate fromJson(JsonObject object) {
        return new TideFishingPredicate(
                Optional.ofNullable(object.get("is_lava_fishing"))
                        .map(element -> GsonHelper.convertToBoolean(element, "is_lava_fishing")),
                Optional.ofNullable(object.get("using_magnetic_bait"))
                        .map(element -> GsonHelper.convertToBoolean(element, "using_magnetic_bait"))
        );
    }

    public @NotNull JsonObject serializeCustomData() {
        JsonObject object = new JsonObject();
        if (isLavaFishing().isPresent()) object.add("is_lava_fishing", new JsonPrimitive(isLavaFishing().get()));
        if (usingMagneticBait().isPresent()) object.add("using_magnetic_bait", new JsonPrimitive(usingMagneticBait().get()));
        return object;
    }

    public EntitySubPredicate.@NotNull Type type() {
        return TideFishingPredicate::fromJson;
    }

    public Optional<Boolean> isLavaFishing() {
        return this.isLavaFishing;
    }
}
