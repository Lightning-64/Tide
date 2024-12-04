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

public record FishingStatsPredicate(Optional<Integer> luck) implements EntitySubPredicate {
    public static FishingStatsPredicate luckOf(int luck) {
        return new FishingStatsPredicate(Optional.of(luck));
    }

    public boolean matches(@NotNull Entity entity, @NotNull ServerLevel level, @Nullable Vec3 position) {
        if (entity instanceof TideFishingHook hook) {
            if (this.luck.isPresent()) return this.luck.get() <= hook.getLuck();
        }
        return false;
    }

    public static FishingStatsPredicate fromJson(JsonObject object) {
        return new FishingStatsPredicate(
                Optional.ofNullable(object.get("luck"))
                        .map(element -> GsonHelper.convertToInt(element, "luck"))
        );
    }

    public @NotNull JsonObject serializeCustomData() {
        JsonObject object = new JsonObject();
        if (luck().isPresent()) object.add("luck", new JsonPrimitive(luck().get()));
        return object;
    }

    @Override
    public @NotNull JsonElement serialize() {
        if (this.type() == EntitySubPredicate.Types.ANY) {
            return JsonNull.INSTANCE;
        } else {
            JsonObject customData = this.serializeCustomData();
            customData.addProperty("type", "fishing_stats");
            return customData;
        }
    }

    public EntitySubPredicate.@NotNull Type type() {
        return TideFishingPredicate::fromJson;
    }

}
