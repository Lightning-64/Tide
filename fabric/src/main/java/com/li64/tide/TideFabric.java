package com.li64.tide;

import com.li64.tide.client.gui.TideMenuTypes;
import com.li64.tide.compat.jei.recipe.RodUpgradingRecipe;
import com.li64.tide.data.TideCriteriaTriggers;
import com.li64.tide.data.TideTags;
import com.li64.tide.events.FabricEventHandler;
import com.li64.tide.registries.*;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.trade.TradeOfferHelper;
import net.fabricmc.fabric.api.resource.IdentifiableResourceReloadListener;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.animal.WaterAnimal;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.Heightmap;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

public class TideFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        Tide.init();

        FabricEventHandler.init();

        TideItems.init();
        TideBlocks.init();
        TideBlockEntities.init();
        TideEntityTypes.init();
        TideMenuTypes.init();
        TideSoundEvents.init();
        TideCriteriaTriggers.init();

        Registry.register(BuiltInRegistries.RECIPE_SERIALIZER, Tide.resource(RodUpgradingRecipe.Type.ID),
                RodUpgradingRecipe.Serializer.INSTANCE);
        Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, Tide.resource("tide"),
                Tide.getCreativeTab(FabricItemGroup.builder()).build());

        BiomeModifications.addFeature(
                BiomeSelectors.tag(TideTags.Biomes.HAS_END_OASIS),
                GenerationStep.Decoration.LAKES,
                ResourceKey.create(Registries.PLACED_FEATURE, Tide.resource("end_oasis")));

        BiomeModifications.addSpawn(
                BiomeSelectors.tag(BiomeTags.IS_OVERWORLD)
                        .and(ctx -> !ctx.hasTag(TideTags.Climate.IS_WARM))
                        .and(ctx -> !ctx.hasTag(TideTags.Climate.IS_SALTWATER)),
                MobCategory.WATER_AMBIENT, TideEntityTypes.TROUT,
                10, 2, 3);
        BiomeModifications.addSpawn(
                BiomeSelectors.tag(BiomeTags.IS_OVERWORLD)
                        .and(ctx -> !ctx.hasTag(TideTags.Climate.IS_COLD))
                        .and(ctx -> !ctx.hasTag(TideTags.Climate.IS_SALTWATER)),
                MobCategory.WATER_AMBIENT, TideEntityTypes.BASS,
                10, 2, 3);
        BiomeModifications.addSpawn(
                BiomeSelectors.tag(BiomeTags.IS_OVERWORLD)
                        .and(ctx -> !ctx.hasTag(TideTags.Climate.IS_SALTWATER)),
                MobCategory.WATER_AMBIENT, TideEntityTypes.YELLOW_PERCH,
                10, 2, 3);
        BiomeModifications.addSpawn(
                BiomeSelectors.tag(BiomeTags.IS_OVERWORLD)
                        .and(ctx -> !ctx.hasTag(TideTags.Climate.IS_SALTWATER)),
                MobCategory.WATER_AMBIENT, TideEntityTypes.BLUEGILL,
                10, 2, 3);
        BiomeModifications.addSpawn(
                BiomeSelectors.tag(BiomeTags.IS_OVERWORLD)
                        .and(ctx -> !ctx.hasTag(TideTags.Climate.IS_COLD))
                        .and(ctx -> !ctx.hasTag(TideTags.Climate.IS_SALTWATER)),
                MobCategory.WATER_AMBIENT, TideEntityTypes.MINT_CARP,
                10, 2, 3);
        BiomeModifications.addSpawn(
                BiomeSelectors.tag(BiomeTags.IS_OVERWORLD)
                        .and(ctx -> !ctx.hasTag(TideTags.Climate.IS_SALTWATER)),
                MobCategory.WATER_AMBIENT, TideEntityTypes.PIKE,
                8, 2, 2);
        BiomeModifications.addSpawn(
                BiomeSelectors.tag(TideTags.Climate.IS_WARM)
                        .and(ctx -> !ctx.hasTag(TideTags.Climate.IS_SALTWATER)),
                MobCategory.WATER_AMBIENT, TideEntityTypes.GUPPY,
                10, 2, 4);
        BiomeModifications.addSpawn(
                BiomeSelectors.tag(BiomeTags.IS_OVERWORLD)
                        .and(ctx -> !ctx.hasTag(TideTags.Climate.IS_WARM))
                        .and(ctx -> !ctx.hasTag(TideTags.Climate.IS_SALTWATER)),
                MobCategory.WATER_AMBIENT, TideEntityTypes.CATFISH,
                8, 2, 2);
        BiomeModifications.addSpawn(
                BiomeSelectors.tag(TideTags.Climate.IS_COLD)
                        .and(ctx -> !ctx.hasTag(TideTags.Climate.IS_SALTWATER)),
                MobCategory.WATER_AMBIENT, TideEntityTypes.CLAYFISH,
                6, 1, 2);

        BiomeModifications.addSpawn(
                BiomeSelectors.tag(TideTags.Climate.IS_SALTWATER),
                MobCategory.WATER_AMBIENT, TideEntityTypes.TUNA,
                10, 2, 3);
        BiomeModifications.addSpawn(
                BiomeSelectors.tag(TideTags.Climate.IS_SALTWATER)
                        .and(ctx -> !ctx.hasTag(TideTags.Climate.IS_WARM)),
                MobCategory.WATER_AMBIENT, TideEntityTypes.OCEAN_PERCH,
                10, 2, 3);
        BiomeModifications.addSpawn(
                BiomeSelectors.tag(TideTags.Climate.IS_SALTWATER)
                        .and(ctx -> !ctx.hasTag(TideTags.Climate.IS_COLD)),
                MobCategory.WATER_AMBIENT, TideEntityTypes.MACKEREL,
                10, 2, 3);
        BiomeModifications.addSpawn(
                BiomeSelectors.includeByKey(Biomes.WARM_OCEAN),
                MobCategory.WATER_AMBIENT, TideEntityTypes.ANGELFISH,
                10, 2, 3);
        BiomeModifications.addSpawn(
                BiomeSelectors.tag(TideTags.Climate.IS_SALTWATER)
                        .and(ctx -> !ctx.hasTag(TideTags.Climate.IS_COLD)),
                MobCategory.WATER_AMBIENT, TideEntityTypes.BARRACUDA,
                8, 2, 2);
        BiomeModifications.addSpawn(
                BiomeSelectors.tag(TideTags.Climate.IS_SALTWATER)
                        .and(BiomeSelectors.tag(TideTags.Climate.IS_WARM)),
                MobCategory.WATER_AMBIENT, TideEntityTypes.SAILFISH,
                8, 2, 3);

        tideFishSpawnRules(TideEntityTypes.TROUT);
        tideFishSpawnRules(TideEntityTypes.BASS);
        tideFishSpawnRules(TideEntityTypes.YELLOW_PERCH);
        tideFishSpawnRules(TideEntityTypes.BLUEGILL);
        tideFishSpawnRules(TideEntityTypes.MINT_CARP);
        tideFishSpawnRules(TideEntityTypes.PIKE);
        tideFishSpawnRules(TideEntityTypes.GUPPY);
        tideFishSpawnRules(TideEntityTypes.CATFISH);
        tideFishSpawnRules(TideEntityTypes.CLAYFISH);

        tideFishSpawnRules(TideEntityTypes.TUNA);
        tideFishSpawnRules(TideEntityTypes.OCEAN_PERCH);
        tideFishSpawnRules(TideEntityTypes.MACKEREL);
        tideFishSpawnRules(TideEntityTypes.ANGELFISH);
        tideFishSpawnRules(TideEntityTypes.BARRACUDA);
        tideFishSpawnRules(TideEntityTypes.SAILFISH);

        TideEntityAttributes.init();
        TideEntityAttributes.REGISTRY.forEach(TideFabric::registerAttributes);

        TradeOfferHelper.registerVillagerOffers(VillagerProfession.FISHERMAN, 1, (trades) -> {
            trades.add((trader, random) -> new MerchantOffer(
                    new ItemStack(TideItems.TROUT, 8),
                    new ItemStack(Items.EMERALD, 1),
                    4, 4, 0.02F));
            trades.add((trader, random) -> new MerchantOffer(
                    new ItemStack(TideItems.TUNA, 8),
                    new ItemStack(Items.EMERALD, 1),
                    4, 4, 0.02F));
            trades.add((trader, random) -> new MerchantOffer(
                    new ItemStack(TideItems.BASS, 7),
                    new ItemStack(Items.EMERALD, 1),
                    4, 4, 0.02F));
            trades.add((trader, random) -> new MerchantOffer(
                    new ItemStack(TideItems.MINT_CARP, 8),
                    new ItemStack(Items.EMERALD, 1),
                    4, 4, 0.02F));
        });
        
        TradeOfferHelper.registerVillagerOffers(VillagerProfession.FISHERMAN, 2, (trades) -> {
            trades.add((trader, random) -> new MerchantOffer(
                    new ItemStack(TideItems.BARRACUDA, 3),
                    new ItemStack(Items.EMERALD, 1),
                    4,4,0.02F));
            trades.add((trader, random) -> new MerchantOffer(
                    new ItemStack(TideItems.SAILFISH, 3),
                    new ItemStack(Items.EMERALD, 1),
                    4,4,0.02F));
            trades.add((trader, random) -> new MerchantOffer(
                    new ItemStack(Items.TROPICAL_FISH, 9),
                    new ItemStack(Items.EMERALD, 1),
                    4,4, 0.02F));
        });

        Tide.onRegisterReloadListeners((id, listener) -> ResourceManagerHelper.get(PackType.SERVER_DATA)
                .registerReloadListener(new IdentifiableResourceReloadListener() {
            @Override
            public ResourceLocation getFabricId() {
                return id;
            }

            @Override
            public @NotNull CompletableFuture<Void> reload(PreparationBarrier synchronizer, ResourceManager manager, ProfilerFiller prepareProfiler, ProfilerFiller applyProfiler, Executor prepareExecutor, Executor applyExecutor) {
                return listener.reload(synchronizer, manager, prepareProfiler, applyProfiler, prepareExecutor, applyExecutor);
            }
        }));
    }

    public static <T extends LivingEntity> void registerAttributes(TideEntityAttributes.Registration<T> reg) {
        FabricDefaultAttributeRegistry.register(reg.entityType(), reg.attributes());
    }

    public static <T extends WaterAnimal> void tideFishSpawnRules(EntityType<T> entityType) {
        SpawnPlacements.register(entityType, SpawnPlacements.Type.IN_WATER,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, WaterAnimal::checkSurfaceWaterAnimalSpawnRules);
    }
}
