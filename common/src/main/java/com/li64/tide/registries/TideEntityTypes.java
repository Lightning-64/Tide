package com.li64.tide.registries;

import com.li64.tide.Tide;
import com.li64.tide.registries.entities.fish.*;
import com.li64.tide.registries.entities.misc.fishing.HookAccessor;
import com.li64.tide.registries.entities.misc.fishing.TideFishingHook;
import com.li64.tide.registries.entities.misc.DeepAquaArrow;
import com.li64.tide.registries.entities.misc.LootCrateEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;

import java.util.HashMap;

public class TideEntityTypes {
    public static final HashMap<String, EntityType<?>> ENTITY_TYPES = new HashMap<>();

    public static final EntityType<TideFishingHook> FISHING_BOBBER = register(
            "fishing_bobber", EntityType.Builder.<TideFishingHook>of(TideFishingHook::new, MobCategory.MISC)
                    .noSave().noSummon().sized(0.25f, 0.25f)
                    .clientTrackingRange(4).updateInterval(5));

    public static final EntityType<HookAccessor> HOOK_ACCESSOR = register(
            "fishing_bobber_accessor", EntityType.Builder.<HookAccessor>of(HookAccessor::new, MobCategory.MISC)
                    .noSave().noSummon().sized(0.25f, 0.25f)
                    .clientTrackingRange(4).updateInterval(5));

    public static final EntityType<LootCrateEntity> LOOT_CRATE = register(
            "loot_crate", EntityType.Builder.of(LootCrateEntity::new, MobCategory.MISC)
                    .sized(0.98f, 0.98f).clientTrackingRange(10)
                    .updateInterval(20));

    public static final EntityType<DeepAquaArrow> DEEP_AQUA_ARROW = register(
            "deep_aqua_arrow", EntityType.Builder.<DeepAquaArrow>of(DeepAquaArrow::new, MobCategory.MISC)
                    .sized(0.5f, 0.5f)
                    .clientTrackingRange(4).updateInterval(20));


    public static final EntityType<Trout> TROUT = register(
            "trout", EntityType.Builder.of(Trout::new, MobCategory.WATER_AMBIENT)
                    .sized(0.5f, 0.4f).eyeHeight(0.195f)
                    .clientTrackingRange(4));

    public static final EntityType<Bass> BASS = register(
            "bass", EntityType.Builder.of(Bass::new, MobCategory.WATER_AMBIENT)
                    .sized(0.5f, 0.4f).eyeHeight(0.195f)
                    .clientTrackingRange(4));

    public static final EntityType<YellowPerch> YELLOW_PERCH = register(
            "yellow_perch", EntityType.Builder.of(YellowPerch::new, MobCategory.WATER_AMBIENT)
                    .sized(0.5f, 0.4f).eyeHeight(0.195f)
                    .clientTrackingRange(4));

    public static final EntityType<Bluegill> BLUEGILL = register(
            "bluegill", EntityType.Builder.of(Bluegill::new, MobCategory.WATER_AMBIENT)
                    .sized(0.5f, 0.4f).eyeHeight(0.195f)
                    .clientTrackingRange(4));

    public static final EntityType<MintCarp> MINT_CARP = register(
            "mint_carp", EntityType.Builder.of(MintCarp::new, MobCategory.WATER_AMBIENT)
                    .sized(0.5f, 0.4f).eyeHeight(0.195f)
                    .clientTrackingRange(4));

    public static final EntityType<Pike> PIKE = register(
            "pike", EntityType.Builder.of(Pike::new, MobCategory.WATER_AMBIENT)
                    .sized(0.5f, 0.4f).eyeHeight(0.195f)
                    .clientTrackingRange(4));

    public static final EntityType<Guppy> GUPPY = register(
            "guppy", EntityType.Builder.of(Guppy::new, MobCategory.WATER_AMBIENT)
                    .sized(0.4f, 0.3f).eyeHeight(0.195f)
                    .clientTrackingRange(4));

    public static final EntityType<Catfish> CATFISH = register(
            "catfish", EntityType.Builder.of(Catfish::new, MobCategory.WATER_AMBIENT)
                    .sized(0.5f, 0.4f).eyeHeight(0.195f)
                    .clientTrackingRange(4));

    public static final EntityType<Clayfish> CLAYFISH = register(
            "clayfish", EntityType.Builder.of(Clayfish::new, MobCategory.WATER_AMBIENT)
                    .sized(0.5f, 0.4f).eyeHeight(0.195f)
                    .clientTrackingRange(4));


    public static final EntityType<Tuna> TUNA = register(
            "tuna", EntityType.Builder.of(Tuna::new, MobCategory.WATER_AMBIENT)
                    .sized(0.5f, 0.4f).eyeHeight(0.195f)
                    .clientTrackingRange(4));

    public static final EntityType<OceanPerch> OCEAN_PERCH = register(
            "ocean_perch", EntityType.Builder.of(OceanPerch::new, MobCategory.WATER_AMBIENT)
                    .sized(0.5f, 0.4f).eyeHeight(0.195f)
                    .clientTrackingRange(4));

    public static final EntityType<Mackerel> MACKEREL = register(
            "mackerel", EntityType.Builder.of(Mackerel::new, MobCategory.WATER_AMBIENT)
                    .sized(0.5f, 0.4f).eyeHeight(0.195f)
                    .clientTrackingRange(4));

    public static final EntityType<Angelfish> ANGELFISH = register(
            "angelfish", EntityType.Builder.of(Angelfish::new, MobCategory.WATER_AMBIENT)
                    .sized(0.45f, 0.55f).eyeHeight(0.195f)
                    .clientTrackingRange(4));

    public static final EntityType<Barracuda> BARRACUDA = register(
            "barracuda", EntityType.Builder.of(Barracuda::new, MobCategory.WATER_AMBIENT)
                    .sized(0.9f, 0.4f).eyeHeight(0.195f)
                    .clientTrackingRange(4));

    public static final EntityType<Sailfish> SAILFISH = register(
            "sailfish", EntityType.Builder.of(Sailfish::new, MobCategory.WATER_AMBIENT)
                    .sized(0.75f, 0.45f).eyeHeight(0.195f)
                    .clientTrackingRange(4));

    public static <T extends Entity> EntityType<T> register(String key, EntityType.Builder<T> builder) {
        EntityType<T> entityType = builder.build(key);
        ENTITY_TYPES.put(key, entityType);
        return entityType;
    }

    public static void init() {
        ENTITY_TYPES.forEach(Tide.PLATFORM::registerEntityType);
    }
}