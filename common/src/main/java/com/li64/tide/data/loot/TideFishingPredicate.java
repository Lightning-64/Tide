package com.li64.tide.data.loot;

import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.li64.tide.registries.entities.misc.fishing.TideFishingHook;
import net.minecraft.advancements.critereon.EntitySubPredicate;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public record TideFishingPredicate(Optional<Boolean> isLavaFishing, Optional<Boolean> usingMagneticBait, Optional<String> depthLayer) implements EntitySubPredicate {
    public static TideFishingPredicate isLavaFishing(boolean lavaFishing) {
        return new TideFishingPredicate(Optional.of(lavaFishing), Optional.empty(), Optional.empty());
    }

    public static TideFishingPredicate usingMagneticBait(boolean magneticBait) {
        return new TideFishingPredicate(Optional.empty(), Optional.of(magneticBait), Optional.empty());
    }

    public static TideFishingPredicate depthLayer(DepthLayer layer) {
        return new TideFishingPredicate(Optional.empty(), Optional.empty(), Optional.of(layer.key));
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

    public static TideFishingPredicate fromJson(JsonObject object) {
        return new TideFishingPredicate(
                Optional.ofNullable(object.get("is_lava_fishing"))
                        .map(element -> GsonHelper.convertToBoolean(element, "is_lava_fishing")),
                Optional.ofNullable(object.get("using_magnetic_bait"))
                        .map(element -> GsonHelper.convertToBoolean(element, "using_magnetic_bait")),
                Optional.ofNullable(object.get("depth_layer"))
                        .map(element -> GsonHelper.convertToString(element, "depth_layer"))
        );
    }

    public @NotNull JsonObject serializeCustomData() {
        JsonObject object = new JsonObject();
        if (isLavaFishing().isPresent()) object.add("is_lava_fishing", new JsonPrimitive(isLavaFishing().get()));
        if (usingMagneticBait().isPresent()) object.add("using_magnetic_bait", new JsonPrimitive(usingMagneticBait().get()));
        if (depthLayer().isPresent()) object.add("depth_layer", new JsonPrimitive(depthLayer().get()));
        return object;
    }

    @Override
    public @NotNull JsonElement serialize() {
        if (this.type() == EntitySubPredicate.Types.ANY) {
            return JsonNull.INSTANCE;
        } else {
            JsonObject customData = this.serializeCustomData();
            customData.addProperty("type", "tide_fishing");
            return customData;
        }
    }

    public EntitySubPredicate.@NotNull Type type() {
        return TideFishingPredicate::fromJson;
    }

    public Optional<Boolean> isLavaFishing() {
        return this.isLavaFishing;
    }
}
