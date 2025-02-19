package com.li64.tide.util;

import com.li64.tide.Tide;
import com.li64.tide.client.gui.JournalPage;
import com.li64.tide.data.journal.JournalLayout;
import com.li64.tide.data.TideCriteriaTriggers;
import com.li64.tide.data.TideLootTables;
import com.li64.tide.data.TideTags;
import com.li64.tide.data.loot.DepthLayer;
import com.li64.tide.data.player.TidePlayerData;
import com.li64.tide.network.messages.ShowToastMsg;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootTable;

import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class TideUtils {

    public static boolean moddedDimension(ResourceKey<Level> dimension) {
        return dimension != Level.OVERWORLD && dimension != Level.NETHER && dimension != Level.END;
    }

    public static boolean shouldGrabTideLootTable(ItemStack item, FluidState fluid) {
        if (item.is(TideTags.Items.CRATES)) return false;
        if (item.is(TideTags.Items.VANILLA_FISH) || new Random().nextInt(0, 4) == 0) return true;
        return fluid.is(TideTags.Fluids.LAVA_FISHING);
    }

    public static ResourceKey<LootTable> getTideLootTable(double x, double y, double z, FluidState fluid, Level level) {
        DepthLayer layer = DepthLayer.getLayerAt(y);
        Holder<Biome> biomeHolder = level.getBiome(new BlockPos((int) x, (int) y, (int) z));

        if (moddedDimension(level.dimension())) {

            // Return the default loot tables for an unknown modded dimension
            if (fluid.is(TideTags.Fluids.LAVA_FISHING)) return TideLootTables.Fishing.LAVA_SURFACE;
            else return BuiltInLootTables.FISHING;

        } if (level.dimension() == Level.NETHER){

            if (Tide.PLATFORM.isModLoaded("netherdepthsupgrade") && new Random().nextFloat() > 0.65f)
                return ResourceKey.create(Registries.LOOT_TABLE, ResourceLocation.fromNamespaceAndPath(
                        "netherdepthsupgrade", "gameplay/nether_fishing"));
            return TideLootTables.Fishing.NETHER;

        } else if (level.dimension() == Level.END) {

            if (fluid.is(TideTags.Fluids.LAVA_FISHING)) return TideLootTables.Fishing.END_LAVA;
            else return TideLootTables.Fishing.END_WATER;

        } else if (layer == DepthLayer.UNDERGROUND && level.dimension() == Level.OVERWORLD) {

            if (fluid.is(TideTags.Fluids.LAVA_FISHING)) return TideLootTables.Fishing.LAVA_UNDERGROUND;
            return TideLootTables.Fishing.UNDERGROUND;

        } else if (layer == DepthLayer.DEPTHS && level.dimension() == Level.OVERWORLD) {

            if (fluid.is(TideTags.Fluids.LAVA_FISHING)) return TideLootTables.Fishing.LAVA_DEPTHS;
            return TideLootTables.Fishing.DEPTHS;

        } else {

            if (fluid.is(TideTags.Fluids.LAVA_FISHING)) return TideLootTables.Fishing.LAVA_SURFACE;

            if (biomeHolder.is(TideTags.Climate.IS_COLD)) {

                if (biomeHolder.is(TideTags.Climate.IS_SALTWATER)) return TideLootTables.Fishing.SALTWATER_COLD;
                else return TideLootTables.Fishing.FRESHWATER_COLD;

            } else if (biomeHolder.is(TideTags.Climate.IS_WARM)) {

                if (biomeHolder.is(TideTags.Climate.IS_SALTWATER)) return TideLootTables.Fishing.SALTWATER_WARM;
                else return TideLootTables.Fishing.FRESHWATER_WARM;

            } else {

                if (biomeHolder.is(TideTags.Climate.IS_SALTWATER)) return TideLootTables.Fishing.SALTWATER_NORMAL;
                else return TideLootTables.Fishing.FRESHWATER_NORMAL;

            }
        }
    }

    public static HashMap<String, Item> PROFILE_ITEMS;

    public static boolean isJournalFish(ItemStack stack) {
        if (PROFILE_ITEMS == null || PROFILE_ITEMS.size() != Tide.JOURNAL.getProfileConfigs().size()) {
            PROFILE_ITEMS = new HashMap<>();
            Tide.JOURNAL.getProfileConfigs().forEach(config ->
                    PROFILE_ITEMS.put(config.fishItem(), getItemFromName(config.fishItem())));
        }
        return PROFILE_ITEMS.containsKey(getNameFromItem(stack.getItem()));
    }

    public static String getNameFromItem(Item item) {
        return BuiltInRegistries.ITEM.getKey(item).toString();
    }

    public static Item getItemFromName(String name) {
        return BuiltInRegistries.ITEM.get(ResourceLocation.read(name).getOrThrow()).orElseThrow().value();
    }

    public static void checkPageCompletion(TidePlayerData data, JournalPage page, ServerPlayer player) {
        if (isCategoryCompleted(data, page)) {
            if (!data.hasPageCompleted(page)) {
                data.pagesCompleted.add(page.id());
                if (data.pagesCompleted.size() <= 1) {
                    TideCriteriaTriggers.FINISH_PAGE.trigger((ServerPlayer) player);
                }
                if (data.pagesCompleted.size() >= Tide.JOURNAL.getPageConfigs().size() - 1) {
                    data.finishedJournal = true;
                    TideCriteriaTriggers.FINISH_JOURNAL.trigger((ServerPlayer) player);
                }
                data.syncTo(player);
                Tide.LOG.debug("Player completed category: {}", page.idName());
                Tide.LOG.debug("Completed {}/{} categories", data.pagesCompleted.size(), Tide.JOURNAL.getPageConfigs().size());
            }
        }
    }

    public static boolean isCategoryCompleted(TidePlayerData data, JournalPage page) {
        List<Item> fishes = getFishFromProfileList(page.getAllProfiles());
        if (fishes == null) return false;

        for (Item fish : fishes) {
            ItemStack fishStack = new ItemStack(fish);
            if (!data.hasFishUnlocked(fishStack)) {
                return false;
            }
        }
        return true;
    }

    public static void unlockPage(ServerPlayer player, JournalPage page) {
        TidePlayerData data = TidePlayerData.getOrCreate(player);

        if (!data.hasPageUnlocked(page)) {
            data.unlockPage(page);
            Tide.NETWORK.sendToPlayer(new ShowToastMsg(
                    Component.translatable("newpage.toast.title"),
                    TideUtils.getPageToastDesc(page),
                    TideUtils.getPageToastIcon(page)), player);
            data.syncTo(player);
        }
    }

    public static void unlockFishForCategory(ServerPlayer player, String name) {
        TidePlayerData data = TidePlayerData.getOrCreate(player);

        // Get journal page
        JournalPage page = getPageByName(name);
        if (page == null) return;
        List<Item> fishes = getFishFromProfileList(page.getAllProfiles());

        // Unlock fish
        for (Item fish : fishes) {
            if (!data.hasFishUnlocked(fish.getDefaultInstance())) {
                data.unlockFish(fish.getDefaultInstance());
            }
        }

        checkPageCompletion(data, page, player);
        data.syncTo(player);
    }

    public static boolean isInPage(String pageName, ItemStack stack) {
        List<Item> fishes = getFishFromPageName(pageName);
        if (fishes == null) return false;
        return fishes.contains(stack.getItem());
    }

    public static Component removeRawTextInName(Component initialName) {
        String[] splitName = Component.translatable(initialName.getString())
                .getString().split("Raw ");

        StringBuilder nameBuilder = new StringBuilder();
        for (String string : splitName) {
            nameBuilder.append(string);
        }

        return Component.literal(nameBuilder.toString());
    }

    public static ItemStack getPageToastIcon(JournalPage page) {
        return page.icon();
    }

    public static Component getPageToastDesc(JournalPage page) {
        return Component.translatable(page.title());
    }

    public static List<Item> getFishFromProfileList(List<JournalLayout.Profile> profiles) {
        return profiles.stream().map(profile -> BuiltInRegistries.ITEM
                .get(ResourceLocation.parse(profile.fishItem())).orElseThrow().value()).toList();
    }

    public static List<Item> getFishFromPageName(String name) {
        JournalPage page = getPageByName(name);
        if (page == null) return null;
        return getFishFromProfileList(page.getAllProfiles());
    }

    public static JournalPage getPageByName(String name) {
        JournalLayout.Page pageConfig = Tide.JOURNAL.getPageConfigs()
                .stream().filter(config -> config.id().matches(name))
                .findFirst().orElse(null);
        if (pageConfig == null) return null;
        return new JournalPage(pageConfig);
    }

    public static JournalLayout.Profile getProfileFromItem(ItemStack item) {
        return Tide.JOURNAL.getProfileConfigs().stream()
                .filter(config -> item.is(BuiltInRegistries.ITEM.get(
                        ResourceLocation.parse(config.fishItem())).orElseThrow().value()))
                .findFirst().orElse(null);
    }

    public static Item itemFromLocation(ResourceLocation location) {
        return BuiltInRegistries.ITEM.get(location).orElse(BuiltInRegistries.ITEM.get(
                BuiltInRegistries.ITEM.getKey(Items.AIR)).orElseThrow()).value();
    }
}
