package com.li64.tide.util;

import com.li64.tide.Tide;
import com.li64.tide.client.gui.JournalPage;
import com.li64.tide.data.journal.JournalLayout;
import com.li64.tide.data.TideCriteriaTriggers;
import com.li64.tide.data.TideLootTables;
import com.li64.tide.data.TideTags;
import com.li64.tide.data.player.TidePlayerData;
import com.li64.tide.network.messages.ShowToastMsg;
import com.li64.tide.registries.TideItems;
import com.li64.tide.registries.entities.misc.fishing.TideFishingHook;
import com.li64.tide.registries.items.BaitItem;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.TagKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootTable;

import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class TideUtils {

    public static boolean moddedDimension(ResourceKey<Level> dimension) {
        return dimension != Level.OVERWORLD && dimension != Level.NETHER && dimension != Level.END;
    }

    public enum LootLayer {
        SURFACE, UNDERGROUND, DEPTHS
    }

    public static LootLayer getLayerAt(double y) {
        if (y < 0) return LootLayer.DEPTHS;
        else if (y < 50) return LootLayer.UNDERGROUND;
        else return LootLayer.SURFACE;
    }

    public static List<ItemStack> checkForOverrides(List<ItemStack> list, TideFishingHook hook, ServerLevel level) {
        ItemStack result = list.get(0);
        int luck = hook.getLuck();
        int moonPhase = level.getMoonPhase();

        if (result.is(TideItems.VOIDSEEKER)) {
            // new moon and full moon are the only phases where you can get the voidseeker,
            // otherwise it will just be replaced with an end stone perch.
            if (moonPhase != 0 && moonPhase != 4)
                result = new ItemStack(TideItems.ENDSTONE_PERCH, 1);
        }
        if (hook.getLuck() >= 5) {
            // 1/24 chance to catch the midas fish if the player has max luck (5)
            // Technically luck can go higher through the luck effect but im not doing that
            if (new Random().nextInt(0, 24) == 1)
                result = new ItemStack(TideItems.MIDAS_FISH, 1);
        }
        if (moonPhase == 0 && hook.getBiome().is(TideTags.Biomes.CAN_CATCH_STARFISH) && level.isNight()) {
            // 1/24 (with luck) chance to catch the shooting starfish at night, on a full moon,
            // when fishing in any deep ocean biome
            if (new Random().nextInt(0, 24 - luck) == 1)
                result = new ItemStack(TideItems.SHOOTING_STARFISH, 1);
        }
        return List.of(result);
    }

    public static boolean shouldGrabTideLootTable(List<ItemStack> items, FluidState fluid) {
        if (items.getFirst().is(TideTags.Items.VANILLA_FISH) || new Random().nextInt(0, 4) == 0) return true;
        return fluid.is(TideTags.Fluids.LAVA_FISHING);
    }

    private static boolean isInDimension(String name, Level level) {
        return level.dimension().location().toString().matches(name);
    }

    public static ResourceKey<LootTable> getTideLootTable(double x, double y, double z, FluidState fluid, Level level, RandomSource random) {
        LootLayer layer = TideUtils.getLayerAt(y);
        Holder<Biome> biomeHolder = level.getBiome(new BlockPos((int) x, (int) y, (int) z));
        if (moddedDimension(level.dimension())) {

            // Return the default loot tables for an unknown modded dimension
            if (fluid.is(TideTags.Fluids.LAVA_FISHING)) return TideLootTables.Fishing.LAVA_SURFACE;
            else return BuiltInLootTables.FISHING;

        } if (level.dimension() == Level.NETHER) return TideLootTables.Fishing.NETHER;
        else if (level.dimension() == Level.END) {

            if (fluid.is(TideTags.Fluids.LAVA_FISHING)) return TideLootTables.Fishing.END_LAVA;
            else return TideLootTables.Fishing.END_WATER;

        } else if (layer == LootLayer.UNDERGROUND && level.dimension() == Level.OVERWORLD) {

            if (fluid.is(TideTags.Fluids.LAVA_FISHING)) return TideLootTables.Fishing.LAVA_UNDERGROUND;
            ResourceKey<LootTable> biomeLoot = TideUtils.getBiomeLootTable(biomeHolder);
            if (biomeLoot != null && random.nextInt(0, 21) == 1) return biomeLoot;
            return TideLootTables.Fishing.UNDERGROUND;

        } else if (layer == LootLayer.DEPTHS && level.dimension() == Level.OVERWORLD) {

            if (fluid.is(TideTags.Fluids.LAVA_FISHING)) return TideLootTables.Fishing.LAVA_DEPTHS;
            ResourceKey<LootTable> biomeLoot = TideUtils.getBiomeLootTable(biomeHolder);
            if (biomeLoot != null && random.nextInt(0, 21) == 1) return biomeLoot;
            return TideLootTables.Fishing.DEPTHS;

        } else {

            if (fluid.is(TideTags.Fluids.LAVA_FISHING)) return TideLootTables.Fishing.LAVA_SURFACE;
            ResourceKey<LootTable> biomeLoot = TideUtils.getBiomeLootTable(biomeHolder);
            if (biomeLoot != null && random.nextInt(0, 21) == 1) return biomeLoot;

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

    public static ResourceKey<LootTable> getBiomeLootTable(Holder<Biome> biomeHolder) {
        for (TagKey<Biome> tag : TideTags.Biomes.fishingBiomes) {
            if (biomeHolder.is(tag)) {
                return TideLootTables.getByLocation(
                        Tide.resource("gameplay/fishing/biomes/" + tag.location().getPath()));
            }
        }
        return null;
    }

    public static ResourceKey<LootTable> getCrateLoot(double x, double y, double z, FluidState fluid, Level level) {
        Holder<Biome> biomeHolder = level.getBiome(new BlockPos((int)x, (int)y, (int)z));
        LootLayer layer = TideUtils.getLayerAt(y);

        if (fluid.getType() == Fluids.LAVA) {

            if (level.dimension() == Level.NETHER) return TideLootTables.Crates.NETHER_LAVA;
            else if (level.dimension() == Level.END) return TideLootTables.Crates.END_LAVA;
            else if (layer == LootLayer.SURFACE) return TideLootTables.Crates.OVERWORLD_LAVA_SURFACE;
            else if (layer == LootLayer.UNDERGROUND) return TideLootTables.Crates.OVERWORLD_LAVA_UNDERGROUND;
            else return TideLootTables.Crates.OVERWORLD_LAVA_DEEP;

        } else {

            if (level.dimension() == Level.END) return TideLootTables.Crates.END_WATER;
            else {
                if (layer == LootLayer.SURFACE) {
                    if (biomeHolder.is(TideTags.Climate.IS_SALTWATER)) return TideLootTables.Crates.OVERWORLD_WATER_OCEAN;
                    else return TideLootTables.Crates.OVERWORLD_WATER_RIVER;
                } else if (layer == LootLayer.UNDERGROUND) return TideLootTables.Crates.OVERWORLD_WATER_UNDERGROUND;
                else return TideLootTables.Crates.OVERWORLD_WATER_DEEP;
            }

        }
    }

    private static HashMap<String, Item> profileItems;

    public static boolean isJournalFish(ItemStack stack) {
        if (profileItems == null || profileItems.size() < Tide.JOURNAL.getProfileConfigs().size()) {
            profileItems = new HashMap<>();
            Tide.JOURNAL.getProfileConfigs().forEach(config ->
                    profileItems.put(config.fishItem(), getItemFromName(config.fishItem())));
        }
        return profileItems.containsKey(getNameFromItem(stack.getItem()));
    }

    public static String getNameFromItem(Item item) {
        return BuiltInRegistries.ITEM.getKey(item).toString();
    }

    public static Item getItemFromName(String name) {
        return BuiltInRegistries.ITEM.get(ResourceLocation.read(name).getOrThrow());
    }

    public static void checkPageCompletion(TidePlayerData data, JournalPage page, ServerPlayer player) {
        if (isCategoryCompleted(data, page)) {
            if (!data.hasPageCompleted(page)) {
                data.pagesCompleted.add(page.getID());
                if (data.pagesCompleted.size() <= 1) {
                    TideCriteriaTriggers.FINISH_PAGE.trigger((ServerPlayer) player);
                }
                if (data.pagesCompleted.size() >= Tide.JOURNAL.getPageConfigs().size() - 1) {
                    data.finishedJournal = true;
                    TideCriteriaTriggers.FINISH_JOURNAL.trigger((ServerPlayer) player);
                }
                data.syncTo(player);
                Tide.LOG.debug("Player completed category: {}", page.getIDName());
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
        return page.getIcon();
    }

    public static Component getPageToastDesc(JournalPage page) {
        return Component.translatable(page.getTitle());
    }

    public static List<Item> getFishFromProfileList(List<JournalLayout.Profile> profiles) {
        return profiles.stream().map(profile -> BuiltInRegistries.ITEM
                .get(ResourceLocation.parse(profile.fishItem()))).toList();
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

    public static JournalPage getPageByNumber(int pageNumber) {
        if (pageNumber > Tide.JOURNAL.getPageConfigs().size() - 1) return null;
        return new JournalPage(Tide.JOURNAL.getPageConfigs().get(pageNumber));
    }

    public static JournalLayout.Profile getProfileFromItem(ItemStack item) {
        return Tide.JOURNAL.getProfileConfigs().stream().filter(config ->
                        item.is(BuiltInRegistries.ITEM.get(ResourceLocation.parse(config.fishItem()))))
                .findFirst().orElse(null);
    }

    public static boolean isHoldingBait(Player player) {
        return isBait(player.getOffhandItem()) || isBait(player.getMainHandItem());
    }

    public static ItemStack getHeldBaitItem(Player player) {
        if (!isHoldingBait(player)) return ItemStack.EMPTY;
        return isBait(player.getOffhandItem())
                ? player.getOffhandItem() : player.getMainHandItem();
    }

    public static boolean isBait(ItemStack stack) {
        if (stack.getDescriptionId().equals("item.fishofthieves.earthworms")) return true;
        if (stack.getDescriptionId().equals("item.fishofthieves.grubs")) return true;
        if (stack.getDescriptionId().equals("item.fishofthieves.leeches")) return true;
        return stack.getItem() instanceof BaitItem;
    }

    public static int getBaitSpeed(ItemStack stack) {
        if (stack.getItem() instanceof BaitItem bait) return bait.getSpeedBonus();
        if (stack.getDescriptionId().equals("item.fishofthieves.earthworms")) return 2;
        if (stack.getDescriptionId().equals("item.fishofthieves.grubs")) return 2;
        if (stack.getDescriptionId().equals("item.fishofthieves.leeches")) return 2;
        else return 0;
    }

    public static int getBaitLuck(ItemStack stack) {
        if (stack.getItem() instanceof BaitItem bait) return bait.getLuckBonus();
        else return 0;
    }
}
