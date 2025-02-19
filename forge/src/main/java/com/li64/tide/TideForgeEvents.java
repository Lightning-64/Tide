package com.li64.tide;

import com.google.common.collect.ImmutableList;
import com.li64.tide.data.TideTags;
import com.li64.tide.data.commands.JournalCommand;
import com.li64.tide.data.player.TidePlayerData;
import com.li64.tide.events.TideEventHandler;
import com.li64.tide.loot.LootTableAccessor;
import com.li64.tide.registries.*;
import com.li64.tide.registries.entities.util.AbstractTideFish;
import com.li64.tide.registries.items.TideFishingRodItem;
import com.li64.tide.util.BaitUtils;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.minecraft.client.Minecraft;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.SpawnPlacementTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.ItemCost;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntryContainer;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.entity.SpawnPlacementRegisterEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.village.VillagerTradesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import java.util.List;

@SuppressWarnings("unused")
public class TideForgeEvents {
    @EventBusSubscriber(modid = Tide.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
    public static class Mod {
        @SubscribeEvent
        public static void entitySpawnRestrictions(SpawnPlacementRegisterEvent event) {
            tideFishSpawnRules(TideEntityTypes.TROUT, event);
            tideFishSpawnRules(TideEntityTypes.BASS, event);
            tideFishSpawnRules(TideEntityTypes.YELLOW_PERCH, event);
            tideFishSpawnRules(TideEntityTypes.BLUEGILL, event);
            tideFishSpawnRules(TideEntityTypes.MINT_CARP, event);
            tideFishSpawnRules(TideEntityTypes.PIKE, event);
            tideFishSpawnRules(TideEntityTypes.GUPPY, event);
            tideFishSpawnRules(TideEntityTypes.CATFISH, event);
            tideFishSpawnRules(TideEntityTypes.CLAYFISH, event);

            tideFishSpawnRules(TideEntityTypes.TUNA, event);
            tideFishSpawnRules(TideEntityTypes.OCEAN_PERCH, event);
            tideFishSpawnRules(TideEntityTypes.MACKEREL, event);
            tideFishSpawnRules(TideEntityTypes.ANGELFISH, event);
            tideFishSpawnRules(TideEntityTypes.BARRACUDA, event);
            tideFishSpawnRules(TideEntityTypes.SAILFISH, event);
        }

        public static <T extends AbstractTideFish> void tideFishSpawnRules(EntityType<T> entityType, SpawnPlacementRegisterEvent event) {
            event.register(entityType, SpawnPlacementTypes.IN_WATER,
                    Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, AbstractTideFish::canSpawn,
                    SpawnPlacementRegisterEvent.Operation.REPLACE);
        }

        @SubscribeEvent
        public static void entityAttributeEvent(EntityAttributeCreationEvent event) {
            TideEntityAttributes.init();
            TideEntityAttributes.REGISTRY.forEach(reg -> registerAttributes(reg, event));
        }

        @SubscribeEvent
        public static void registerLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
            TideLayerDefinitions.init();
            TideLayerDefinitions.REGISTRY.forEach(reg -> registerLayer(reg, event));
        }

        @SubscribeEvent
        public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
            TideEntityRenderers.init();
            TideEntityRenderers.REGISTRY.forEach(reg -> registerRenderer(reg, event));
        }

        public static <T extends Entity> void registerRenderer(TideEntityRenderers.Registration<T> reg, EntityRenderersEvent.RegisterRenderers event) {
            event.registerEntityRenderer(reg.entityType(), reg.renderer());
        }

        public static <T extends LivingEntity> void registerAttributes(TideEntityAttributes.Registration<T> reg, EntityAttributeCreationEvent event) {
            event.put(reg.entityType(), reg.attributes());
        }

        public static void registerLayer(TideLayerDefinitions.Registration reg, EntityRenderersEvent.RegisterLayerDefinitions event) {
            event.registerLayerDefinition(reg.location(), reg.layerDefinition());
        }
    }

    @EventBusSubscriber(modid = Tide.MOD_ID, bus = EventBusSubscriber.Bus.FORGE)
    public static class Forge {
        @SubscribeEvent
        public static void onLootTableLoad(LootTableLoadEvent event) {
            if (event.getName().toString().equals("minecraft:gameplay/fishing")) {
                if (Tide.CONFIG.general.crateWeight <= 0) return;
                // Add crate rolls
                LootPool pool = ((LootTableAccessor) event.getTable()).tide$getPool(0);
                ((LootTableAccessor) event.getTable()).tide$getPool(0).entries = new ImmutableList.Builder<LootPoolEntryContainer>()
                        .addAll(pool.entries)
                        .add(Tide.getCrateFishingEntry().build())
                        .build();
            }
        }

        @SubscribeEvent
        public static void onServerReloadListeners(AddReloadListenerEvent event) {
            Tide.onRegisterReloadListeners((id, listener) -> event.addListener(listener));
        }

        @SubscribeEvent
        public static void onDimensionChange(PlayerEvent.PlayerChangedDimensionEvent event) {
            if (event.getTo() == Level.NETHER) {
                TideEventHandler.onEnterNether((ServerPlayer) event.getEntity());
            }
        }

        @SubscribeEvent
        public static void registerCommandsEvent(RegisterCommandsEvent event) {
            new JournalCommand(event.getDispatcher(), event.getBuildContext());
        }

        @SubscribeEvent
        public static void onEntityJoinWorld(PlayerEvent.PlayerLoggedInEvent event) {
            TideEventHandler.onPlayerJoinWorld((ServerPlayer) event.getEntity());
        }

        @SubscribeEvent
        public static void itemTooltipEvent(ItemTooltipEvent event) {
            ItemStack stack = event.getItemStack();
            if (BaitUtils.isBait(stack)) event.getToolTip().addAll(BaitUtils.getDescriptionLines(stack));
            if (stack.is(TideTags.Items.CUSTOMIZABLE_RODS)) event.getToolTip().addAll(TideFishingRodItem.getDescriptionLines(stack, event.getEntity().registryAccess()));
        }

        @SubscribeEvent
        public static void onPlayerCloned(PlayerEvent.Clone event) {
            if (event.getEntity().level().isClientSide()) return;

            Player oldPlayer = event.getOriginal();
            Player newPlayer = event.getEntity();

            TidePlayerData.getOrCreate((ServerPlayer) oldPlayer).syncTo((ServerPlayer) newPlayer);
        }

        @SubscribeEvent
        public static void addCustomTrades(VillagerTradesEvent event) {
            if (event.getType() == VillagerProfession.FISHERMAN) {
                Int2ObjectMap<List<VillagerTrades.ItemListing>> trades = event.getTrades();
                trades.get(1).add((trader, random) -> new MerchantOffer(
                        new ItemCost(TideItems.TROUT, 8),
                        new ItemStack(Items.EMERALD, 1),
                        4,4,0.02F));
                trades.get(1).add((trader, random) -> new MerchantOffer(
                        new ItemCost(TideItems.TUNA, 8),
                        new ItemStack(Items.EMERALD, 1),
                        4,4,0.02F));
                trades.get(1).add((trader, random) -> new MerchantOffer(
                        new ItemCost(TideItems.BASS, 7),
                        new ItemStack(Items.EMERALD, 1),
                        4,4,0.02F));
                trades.get(1).add((trader, random) -> new MerchantOffer(
                        new ItemCost(TideItems.MINT_CARP, 8),
                        new ItemStack(Items.EMERALD, 1),
                        4,4,0.02F));
                trades.get(2).add((trader, random) -> new MerchantOffer(
                        new ItemCost(TideItems.BARRACUDA, 3),
                        new ItemStack(Items.EMERALD, 1),
                        4,4,0.02F));
                trades.get(2).add((trader, random) -> new MerchantOffer(
                        new ItemCost(TideItems.SAILFISH, 3),
                        new ItemStack(Items.EMERALD, 1),
                        4,4,0.02F));
                trades.get(2).add((trader, random) -> new MerchantOffer(
                        new ItemCost(Items.TROPICAL_FISH, 9),
                        new ItemStack(Items.EMERALD, 1),
                        4,4, 0.02F));
            }
        }

        @SubscribeEvent
        public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
            if (event.phase == TickEvent.Phase.END && event.side.isServer()) {
                TideEventHandler.updateFishingJournal((ServerPlayer) event.player);
            }
        }
    }
}
