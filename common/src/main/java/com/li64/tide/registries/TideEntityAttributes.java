package com.li64.tide.registries;

import com.li64.tide.registries.entities.fish.*;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;

import java.util.ArrayList;

public class TideEntityAttributes {
    public static final ArrayList<Registration<?>> REGISTRY = new ArrayList<>();

    public record Registration<T extends LivingEntity>(EntityType<T> entityType, AttributeSupplier attributes) {}

    public static void init() {
        registerAttributes(TideEntityTypes.TROUT, Trout.createAttributes().build());
        registerAttributes(TideEntityTypes.BASS, Bass.createAttributes().build());
        registerAttributes(TideEntityTypes.YELLOW_PERCH, YellowPerch.createAttributes().build());
        registerAttributes(TideEntityTypes.BLUEGILL, Bluegill.createAttributes().build());
        registerAttributes(TideEntityTypes.MINT_CARP, MintCarp.createAttributes().build());
        registerAttributes(TideEntityTypes.PIKE, Pike.createAttributes().build());
        registerAttributes(TideEntityTypes.GUPPY, Guppy.createAttributes().build());
        registerAttributes(TideEntityTypes.CATFISH, Catfish.createAttributes().build());
        registerAttributes(TideEntityTypes.CLAYFISH, Clayfish.createAttributes().build());

        registerAttributes(TideEntityTypes.TUNA, Tuna.createAttributes().build());
        registerAttributes(TideEntityTypes.OCEAN_PERCH, OceanPerch.createAttributes().build());
        registerAttributes(TideEntityTypes.MACKEREL, Mackerel.createAttributes().build());
        registerAttributes(TideEntityTypes.ANGELFISH, Angelfish.createAttributes().build());
        registerAttributes(TideEntityTypes.BARRACUDA, Barracuda.createAttributes().build());
        registerAttributes(TideEntityTypes.SAILFISH, Sailfish.createAttributes().build());
    }

    public static <T extends LivingEntity> void registerAttributes(EntityType<T> entityType, AttributeSupplier attributes) {
        REGISTRY.add(new Registration<>(entityType, attributes));
    }
}
