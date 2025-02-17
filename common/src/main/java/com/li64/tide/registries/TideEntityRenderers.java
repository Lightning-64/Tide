package com.li64.tide.registries;

import com.li64.tide.registries.entities.misc.fishing.TideFishingHookRenderer;
import com.li64.tide.registries.entities.misc.DeepAquaArrowRenderer;
import com.li64.tide.registries.entities.models.SimpleFishModel.Variant;
import com.li64.tide.registries.entities.renderers.*;
import com.li64.tide.registries.entities.util.AbstractTideFish;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.FallingBlockRenderer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.item.FallingBlockEntity;

import java.util.ArrayList;

public class TideEntityRenderers {
    public static final ArrayList<Registration<?>> REGISTRY = new ArrayList<>();

    public record Registration<T extends Entity>(EntityType<T> entityType, EntityRendererProvider<T> renderer) {}

    public static void init() {
        registerEntityRenderer(TideEntityTypes.FISHING_BOBBER, TideFishingHookRenderer::new);
        registerEntityRenderer(TideEntityTypes.DEEP_AQUA_ARROW, DeepAquaArrowRenderer::new);
        registerEntityRenderer(TideEntityTypes.LOOT_CRATE, FallingBlockRenderer::new);

        registerSimpleFishRenderer(TideEntityTypes.TROUT, "trout", Variant.NOSE_NORMAL);
        registerSimpleFishRenderer(TideEntityTypes.BASS, "bass", Variant.NOSE_FULL);
        registerSimpleFishRenderer(TideEntityTypes.YELLOW_PERCH, "yellow_perch", Variant.NOSE_NORMAL);
        registerSimpleFishRenderer(TideEntityTypes.BLUEGILL, "bluegill", Variant.NOSE_LOWER);
        registerSimpleFishRenderer(TideEntityTypes.MINT_CARP, "mint_carp", Variant.NOSE_UPPER);
        registerSimpleFishRenderer(TideEntityTypes.PIKE, "pike", Variant.NOSE_NORMAL);
        registerEntityRenderer(TideEntityTypes.GUPPY, GuppyRenderer::new);
        registerEntityRenderer(TideEntityTypes.CATFISH, CatfishRenderer::new);
        registerSimpleFishRenderer(TideEntityTypes.CLAYFISH, "clayfish", Variant.NOSE_NORMAL);

        registerSimpleFishRenderer(TideEntityTypes.TUNA, "tuna", Variant.NOSE_NORMAL);
        registerSimpleFishRenderer(TideEntityTypes.OCEAN_PERCH, "ocean_perch", Variant.NOSE_UPPER);
        registerSimpleFishRenderer(TideEntityTypes.MACKEREL, "mackerel", Variant.NOSE_NORMAL);
        registerEntityRenderer(TideEntityTypes.ANGELFISH, AngelfishRenderer::new);
        registerEntityRenderer(TideEntityTypes.BARRACUDA, BarracudaRenderer::new);
        registerEntityRenderer(TideEntityTypes.SAILFISH, SailfishRenderer::new);
    }

    public static <T extends AbstractTideFish> void registerSimpleFishRenderer(EntityType<T> entityType, String name, Variant variant) {
        registerEntityRenderer(entityType, (context) -> new SimpleFishRenderer<>(name, variant, context));
    }

    public static <T extends Entity> void registerEntityRenderer(EntityType<T> entityType, EntityRendererProvider<T> renderer) {
        REGISTRY.add(new Registration<>(entityType, renderer));
    }
}
