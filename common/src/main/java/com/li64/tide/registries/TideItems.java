package com.li64.tide.registries;

import com.li64.tide.Tide;
import com.li64.tide.registries.blocks.JellyTorchBlockItem;
import com.li64.tide.registries.items.*;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.food.Foods;
import net.minecraft.world.item.*;
import net.minecraft.world.level.material.Fluids;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@SuppressWarnings("unused")
public class TideItems {
    public static final HashMap<String, Item> ITEMS = new HashMap<>();
    private static final ArrayList<Item> ORDERED_ITEMS = new ArrayList<>();

    public static List<ResourceKey<Item>> JOURNAL_FISH_LIST = new ArrayList<>();
    public static List<ResourceKey<Item>> COOKABLE_FISH_LIST = new ArrayList<>();

    public static final Item STONE_FISHING_ROD = register("stone_fishing_rod",
            new TideFishingRodItem(32, new Item.Properties()));
    public static final Item IRON_FISHING_ROD = register("iron_fishing_rod",
            new TideFishingRodItem(64, new Item.Properties()));
    public static final Item GOLDEN_FISHING_ROD = register("golden_fishing_rod",
            new TideFishingRodItem(32, new Item.Properties()));
    public static final Item CRYSTAL_FISHING_ROD = register("crystal_fishing_rod",
            new TideFishingRodItem(80, new Item.Properties()));
    public static final Item DIAMOND_FISHING_ROD = register("diamond_fishing_rod",
            new TideFishingRodItem(128, new Item.Properties()));
    public static final Item NETHERITE_FISHING_ROD = register("netherite_fishing_rod",
            new TideFishingRodItem(512, new Item.Properties().fireResistant()));

    public static final Item BAIT = register("bait", new Item(new Item.Properties()));
    public static final Item LUCKY_BAIT = register("lucky_bait", new Item(new Item.Properties()));
    public static final Item MAGNETIC_BAIT = register("magnetic_bait", new Item(new Item.Properties()));

    public static final Item WHITE_FISHING_BOBBER = register("white_fishing_bobber", new FishingBobberItem(
            Tide.resource("textures/item/white_fishing_bobber.png"), Component.translatable("bobber.tide.white"), new Item.Properties()));
    public static final Item ORANGE_FISHING_BOBBER = register("orange_fishing_bobber",new FishingBobberItem(
            Tide.resource("textures/item/orange_fishing_bobber.png"), Component.translatable("bobber.tide.orange"), new Item.Properties()));
    public static final Item MAGENTA_FISHING_BOBBER = register("magenta_fishing_bobber",new FishingBobberItem(
            Tide.resource("textures/item/magenta_fishing_bobber.png"), Component.translatable("bobber.tide.magenta"), new Item.Properties()));
    public static final Item LIGHT_BLUE_FISHING_BOBBER = register("light_blue_fishing_bobber",new FishingBobberItem(
            Tide.resource("textures/item/light_blue_fishing_bobber.png"), Component.translatable("bobber.tide.light_blue"), new Item.Properties()));
    public static final Item YELLOW_FISHING_BOBBER = register("yellow_fishing_bobber",new FishingBobberItem(
            Tide.resource("textures/item/yellow_fishing_bobber.png"), Component.translatable("bobber.tide.yellow"), new Item.Properties()));
    public static final Item LIME_FISHING_BOBBER = register("lime_fishing_bobber",new FishingBobberItem(
            Tide.resource("textures/item/lime_fishing_bobber.png"), Component.translatable("bobber.tide.lime"), new Item.Properties()));
    public static final Item PINK_FISHING_BOBBER = register("pink_fishing_bobber",new FishingBobberItem(
            Tide.resource("textures/item/pink_fishing_bobber.png"), Component.translatable("bobber.tide.pink"), new Item.Properties()));
    public static final Item GRAY_FISHING_BOBBER = register("gray_fishing_bobber",new FishingBobberItem(
            Tide.resource("textures/item/gray_fishing_bobber.png"), Component.translatable("bobber.tide.gray"), new Item.Properties()));
    public static final Item LIGHT_GRAY_FISHING_BOBBER = register("light_gray_fishing_bobber",new FishingBobberItem(
            Tide.resource("textures/item/light_gray_fishing_bobber.png"), Component.translatable("bobber.tide.light_gray"), new Item.Properties()));
    public static final Item CYAN_FISHING_BOBBER = register("cyan_fishing_bobber",new FishingBobberItem(
            Tide.resource("textures/item/cyan_fishing_bobber.png"), Component.translatable("bobber.tide.cyan"), new Item.Properties()));
    public static final Item PURPLE_FISHING_BOBBER = register("purple_fishing_bobber",new FishingBobberItem(
            Tide.resource("textures/item/purple_fishing_bobber.png"), Component.translatable("bobber.tide.purple"), new Item.Properties()));
    public static final Item BLUE_FISHING_BOBBER = register("blue_fishing_bobber",new FishingBobberItem(
            Tide.resource("textures/item/blue_fishing_bobber.png"), Component.translatable("bobber.tide.blue"), new Item.Properties()));
    public static final Item BROWN_FISHING_BOBBER = register("brown_fishing_bobber",new FishingBobberItem(
            Tide.resource("textures/item/brown_fishing_bobber.png"), Component.translatable("bobber.tide.brown"), new Item.Properties()));
    public static final Item GREEN_FISHING_BOBBER = register("green_fishing_bobber",new FishingBobberItem(
            Tide.resource("textures/item/green_fishing_bobber.png"), Component.translatable("bobber.tide.green"), new Item.Properties()));
    public static final Item RED_FISHING_BOBBER = register("red_fishing_bobber",new FishingBobberItem(
            Tide.resource("textures/item/red_fishing_bobber.png"), Component.translatable("bobber.tide.red"), new Item.Properties()));
    public static final Item BLACK_FISHING_BOBBER = register("black_fishing_bobber",new FishingBobberItem(
            Tide.resource("textures/item/black_fishing_bobber.png"), Component.translatable("bobber.tide.black"), new Item.Properties()));

    public static final Item FISHING_HOOK = register("fishing_hook", new FishingHookItem(
            Tide.resource("textures/entity/fishing_hook/fishing_hook.png"), Component.translatable("hook.tide.normal"),new Item.Properties()));
    public static final Item IRON_FISHING_HOOK = register("iron_fishing_hook", new FishingHookItem(
            Tide.resource("textures/entity/fishing_hook/iron_fishing_hook.png"), Component.translatable("hook.tide.iron"), new Item.Properties(), "item.tide.iron_hook.desc"));
    public static final Item LAVAPROOF_FISHING_HOOK = register("lavaproof_fishing_hook", new FishingHookItem(
            Tide.resource("textures/entity/fishing_hook/lavaproof_fishing_hook.png"), Component.translatable("hook.tide.lavaproof"), new Item.Properties(), "item.tide.lavaproof_hook.desc"));

    public static final Item FISHING_LINE = register("fishing_line", new FishingLineItem(
            "#d6d6d6", Component.translatable("line.tide.normal"), new Item.Properties()));
    public static final Item BRAIDED_LINE = register("braided_line", new FishingLineItem(
            "#362c1e", Component.translatable("line.tide.braided"), new Item.Properties(), "item.tide.braided_line.desc"));
    public static final Item REINFORCED_LINE = register("reinforced_line", new FishingLineItem(
            "#6b6b68", Component.translatable("line.tide.reinforced"), new Item.Properties(), "item.tide.reinforced_line.desc"));
    public static final Item FORTUNE_LINE = register("fortune_line", new FishingLineItem(
            "#e9b115", Component.translatable("line.tide.fortune"),  new Item.Properties(), "item.tide.fortune_line.desc"));

    public static final Item FISHING_JOURNAL = register("fishing_journal",
            new FishingJournalItem(new Item.Properties()));

    public static final Item ANGLER_WORKSHOP = register("angler_workshop",
            new BlockItem(TideBlocks.ANGLER_WORKSHOP, new Item.Properties()));

    public static final Item SURFACE_LOOT_CRATE = register("surface_loot_crate",
            new BlockItem(TideBlocks.SURFACE_LOOT_CRATE, new Item.Properties()));
    public static final Item OBSIDIAN_LOOT_CRATE = register("obsidian_loot_crate",
            new BlockItem(TideBlocks.OBSIDIAN_LOOT_CRATE, new Item.Properties()));
    public static final Item END_LOOT_CRATE = register("end_loot_crate",
            new BlockItem(TideBlocks.END_LOOT_CRATE, new Item.Properties()));

    public static final Item FISH_BONE = register("fish_bone",
            new Item(new Item.Properties().fireResistant()));
    public static final Item OBSIDIAN_FRAGMENT = register("obsidian_fragment",
            new Item(new Item.Properties().fireResistant()));

    public static final Item ALGAE = register("algae", new PlaceOnWaterBlockItem(
            TideBlocks.ALGAE, new Item.Properties()));
    public static final Item JELLY_TORCH = register("jelly_torch", new JellyTorchBlockItem(
            TideBlocks.JELLY_TORCH, TideBlocks.JELLY_WALL_TORCH, new Item.Properties(), Direction.DOWN));

    public static final Item SPECTRAL_SCALE = register("spectral_scale",
            new Item(new Item.Properties()));
    public static final Item TWILIGHT_SCALE = register("twilight_scale",
            new Item(new Item.Properties()));

    public static final Item DEEP_AQUA_CRYSTAL = register("deep_aqua_crystal",
            new Item(new Item.Properties()));
    public static final Item DEEP_AQUA_ARROW = register("deep_aqua_arrow",
            new DeepAquaArrowItem(new Item.Properties()));

    public static final Item TROUT_BUCKET = register("trout_bucket",
            new MobBucketItem(TideEntityTypes.TROUT, Fluids.WATER, SoundEvents.BUCKET_EMPTY_FISH, new Item.Properties().stacksTo(1)));
    public static final Item BASS_BUCKET = register("bass_bucket",
            new MobBucketItem(TideEntityTypes.BASS, Fluids.WATER, SoundEvents.BUCKET_EMPTY_FISH, new Item.Properties().stacksTo(1)));
    public static final Item YELLOW_PERCH_BUCKET = register("yellow_perch_bucket",
            new MobBucketItem(TideEntityTypes.YELLOW_PERCH, Fluids.WATER, SoundEvents.BUCKET_EMPTY_FISH, new Item.Properties().stacksTo(1)));
    public static final Item BLUEGILL_BUCKET = register("bluegill_bucket",
            new MobBucketItem(TideEntityTypes.BLUEGILL, Fluids.WATER, SoundEvents.BUCKET_EMPTY_FISH, new Item.Properties().stacksTo(1)));
    public static final Item MINT_CARP_BUCKET = register("mint_carp_bucket",
            new MobBucketItem(TideEntityTypes.MINT_CARP, Fluids.WATER, SoundEvents.BUCKET_EMPTY_FISH, new Item.Properties().stacksTo(1)));
    public static final Item PIKE_BUCKET = register("pike_bucket",
            new MobBucketItem(TideEntityTypes.PIKE, Fluids.WATER, SoundEvents.BUCKET_EMPTY_FISH, new Item.Properties().stacksTo(1)));
    public static final Item GUPPY_BUCKET = register("guppy_bucket",
            new MobBucketItem(TideEntityTypes.GUPPY, Fluids.WATER, SoundEvents.BUCKET_EMPTY_FISH, new Item.Properties().stacksTo(1)));
    public static final Item CATFISH_BUCKET = register("catfish_bucket",
            new MobBucketItem(TideEntityTypes.CATFISH, Fluids.WATER, SoundEvents.BUCKET_EMPTY_FISH, new Item.Properties().stacksTo(1)));
    public static final Item CLAYFISH_BUCKET = register("clayfish_bucket",
            new MobBucketItem(TideEntityTypes.CLAYFISH, Fluids.WATER, SoundEvents.BUCKET_EMPTY_FISH, new Item.Properties().stacksTo(1)));

    public static final Item TUNA_BUCKET = register("tuna_bucket",
            new MobBucketItem(TideEntityTypes.TUNA, Fluids.WATER, SoundEvents.BUCKET_EMPTY_FISH, new Item.Properties().stacksTo(1)));
    public static final Item OCEAN_PERCH_BUCKET = register("ocean_perch_bucket",
            new MobBucketItem(TideEntityTypes.OCEAN_PERCH, Fluids.WATER, SoundEvents.BUCKET_EMPTY_FISH, new Item.Properties().stacksTo(1)));
    public static final Item MACKEREL_BUCKET = register("mackerel_bucket",
            new MobBucketItem(TideEntityTypes.MACKEREL, Fluids.WATER, SoundEvents.BUCKET_EMPTY_FISH, new Item.Properties().stacksTo(1)));
    public static final Item ANGELFISH_BUCKET = register("angelfish_bucket",
            new MobBucketItem(TideEntityTypes.ANGELFISH, Fluids.WATER, SoundEvents.BUCKET_EMPTY_FISH, new Item.Properties().stacksTo(1)));
    public static final Item BARRACUDA_BUCKET = register("barracuda_bucket",
            new MobBucketItem(TideEntityTypes.BARRACUDA, Fluids.WATER, SoundEvents.BUCKET_EMPTY_FISH, new Item.Properties().stacksTo(1)));
    public static final Item SAILFISH_BUCKET = register("sailfish_bucket",
            new MobBucketItem(TideEntityTypes.SAILFISH, Fluids.WATER, SoundEvents.BUCKET_EMPTY_FISH, new Item.Properties().stacksTo(1)));

    public static final Item MINT_CARP = register("mint_carp",
            new TideFishItem(new Item.Properties().food(TideFoods.AVERAGE_RAW_FISH), 0.1f, true));
    public static final Item CLAYFISH = register("clayfish",
            new TideFishItem(new Item.Properties().food(TideFoods.UNHEALTHY_RAW_FISH), 1.2f));
    public static final Item HARDENED_CLAYFISH = register("hardened_clayfish",
            new TideFishItem(new Item.Properties(), 0));
    public static final Item TROUT = register("trout",
            new TideFishItem(new Item.Properties().food(TideFoods.AVERAGE_RAW_FISH), 0f, true));
    public static final Item ANGELFISH = register("angelfish",
            new TideFishItem(new Item.Properties().food(TideFoods.AVERAGE_RAW_FISH), 0.4f, true));
    public static final Item BARRACUDA = register("barracuda",
            new TideFishItem(new Item.Properties().food(TideFoods.AVERAGE_RAW_FISH), 0.5f, true));
    public static final Item SAILFISH = register("sailfish",
            new TideFishItem(new Item.Properties().food(TideFoods.AVERAGE_RAW_FISH), 0.8f, true));
    public static final Item CATFISH = register("catfish",
            new TideFishItem(new Item.Properties().food(TideFoods.AVERAGE_RAW_FISH), 0.9f, true));
    public static final Item PIKE = register("pike",
            new TideFishItem(new Item.Properties().food(TideFoods.AVERAGE_RAW_FISH), 0.6f, true));
    public static final Item BASS = register("bass",
            new TideFishItem(new Item.Properties().food(TideFoods.AVERAGE_RAW_FISH), 0.5f, true));
    public static final Item TUNA = register("tuna",
            new TideFishItem(new Item.Properties().food(TideFoods.AVERAGE_RAW_FISH), 0.3f, true));
    public static final Item YELLOW_PERCH = register("yellow_perch",
            new TideFishItem(new Item.Properties().food(TideFoods.AVERAGE_RAW_FISH), 0.5f, true));
    public static final Item OCEAN_PERCH = register("ocean_perch",
            new TideFishItem(new Item.Properties().food(TideFoods.AVERAGE_RAW_FISH), 0.6f, true));
    public static final Item GUPPY = register("guppy",
            new TideFishItem(new Item.Properties().food(TideFoods.UNHEALTHY_RAW_FISH), 0.3f));
    public static final Item BLUEGILL = register("bluegill",
            new TideFishItem(new Item.Properties().food(TideFoods.AVERAGE_RAW_FISH), 0f, true));
    public static final Item MACKEREL = register("mackerel",
            new TideFishItem(new Item.Properties().food(TideFoods.AVERAGE_RAW_FISH), 0.9f, true));

    public static final Item CAVE_EEL = register("cave_eel",
            new TideFishItem(new Item.Properties().food(TideFoods.AVERAGE_RAW_FISH), 1.2f));
    public static final Item CRYSTAL_SHRIMP = register("crystal_shrimp",
            new TideFishItem(new Item.Properties().food(TideFoods.AVERAGE_RAW_FISH), 2.1f));
    public static final Item IRON_TETRA = register("iron_tetra",
            new TideFishItem(new Item.Properties().food(TideFoods.UNHEALTHY_RAW_FISH), 1.6f));
    public static final Item GLOWFISH = register("glowfish",
            new TideFishItem(new Item.Properties().food(TideFoods.AVERAGE_RAW_FISH), 0.7f, true));
    public static final Item ANGLER_FISH = register("anglerfish",
            new TideFishItem(new Item.Properties().food(TideFoods.AVERAGE_RAW_FISH), 1.0f, true));
    public static final Item CAVE_CRAWLER = register("cave_crawler",
            new TideFishItem(new Item.Properties().food(TideFoods.AVERAGE_RAW_FISH), 0.8f, true));
    public static final Item GILDED_MINNOW = register("gilded_minnow",
            new TideFishItem(new Item.Properties().food(TideFoods.GOLDEN_FISH), 3.2f));

    public static final Item ABYSS_ANGLER = register("abyss_angler",
            new TideFishItem(new Item.Properties().food(TideFoods.AVERAGE_RAW_FISH), 3.6f, true));
    public static final Item CRYSTALLINE_CARP = register("crystalline_carp",
            new TideFishItem(new Item.Properties().food(TideFoods.UNHEALTHY_RAW_FISH), 4.1f));
    public static final Item LAPIS_LANTERNFISH = register("lapis_lanternfish",
            new TideFishItem(new Item.Properties().food(TideFoods.UNHEALTHY_RAW_FISH), 2.4f));
    public static final Item LUMINESCENT_JELLYFISH = register("luminescent_jellyfish",
            new TideFishItem(new Item.Properties().food(TideFoods.UNHEALTHY_RAW_FISH), 2.2f));
    public static final Item SHADOW_SNAPPER = register("shadow_snapper",
            new TideFishItem(new Item.Properties().food(TideFoods.AVERAGE_RAW_FISH), 2.0f, true));
    public static final Item DEEP_GROUPER = register("deep_grouper",
            new TideFishItem(new Item.Properties().food(TideFoods.AVERAGE_RAW_FISH), 1.8f, true));
    public static final Item BEDROCK_TETRA = register("bedrock_tetra",
            new TideFishItem(new Item.Properties(), 5.6f));

    public static final Item END_STONE_PERCH = register("endstone_perch",
            new TideFishItem(new Item.Properties(), 4.1f));
    public static final Item ENDERFIN = register("enderfin",
            new TideFishItem(new Item.Properties().food(TideFoods.AVERAGE_RAW_FISH), 4.5f));
    public static final Item ENDERGAZER = register("endergazer",
            new TideFishItem(new Item.Properties().food(TideFoods.AVERAGE_RAW_FISH), 5.2f));
    public static final Item PURPUR_PIKE = register("purpur_pike",
            new TideFishItem(new Item.Properties().food(TideFoods.UNHEALTHY_RAW_FISH), 5.0f));
    public static final Item CHORUS_COD = register("chorus_cod",
            new ChorusFishItem(new Item.Properties().food(Foods.CHORUS_FRUIT), 4.0f));
    public static final Item ELYTROUT = register("elytrout",
            new TideFishItem(new Item.Properties().food(TideFoods.AVERAGE_RAW_FISH), 6.8f));

    // I know spelled the item ID wrong, it's just too late to change it at this point
    public static final Item PRAIRIE_PIKE = register("prarie_pike",
            new TideFishItem(new Item.Properties().food(TideFoods.UNHEALTHY_RAW_FISH), 6.0f));
    public static final Item SANDSKIPPER = register("sandskipper",
            new TideFishItem(new Item.Properties().food(TideFoods.AVERAGE_RAW_FISH), 6.0f));
    public static final Item BLOSSOM_BASS = register("blossom_bass",
            new TideFishItem(new Item.Properties().food(TideFoods.UNHEALTHY_RAW_FISH), 6.0f));
    public static final Item OAKFISH = register("oakfish",
            new TideFishItem(new Item.Properties(), 6.0f));
    public static final Item FROSTBITE_FLOUNDER = register("frostbite_flounder",
            new TideFishItem(new Item.Properties().food(TideFoods.AVERAGE_RAW_FISH), 6.0f));
    public static final Item MIRAGE_CATFISH = register("mirage_catfish",
            new TideFishItem(new Item.Properties().food(TideFoods.AVERAGE_RAW_FISH), 6.0f));
    public static final Item ECHOFIN_SNAPPER = register("echofin_snapper",
            new TideFishItem(new Item.Properties().food(TideFoods.UNHEALTHY_RAW_FISH), 6.0f));
    public static final Item SUNSPIKE_GOBY = register("sunspike_goby",
            new TideFishItem(new Item.Properties().food(TideFoods.UNHEALTHY_RAW_FISH), 6.0f));
    public static final Item BIRCH_TROUT = register("birch_trout",
            new TideFishItem(new Item.Properties(), 6.0f));
    public static final Item STONEFISH = register("stonefish",
            new TideFishItem(new Item.Properties(), 6.0f));
    public static final Item DRIPSTONE_DARTER = register("dripstone_darter",
            new TideFishItem(new Item.Properties(), 6.0f));
    public static final Item SLIMEFIN_SNAPPER = register("slimefin_snapper",
            new TideFishItem(new Item.Properties().food(TideFoods.UNHEALTHY_RAW_FISH), 6.0f));
    public static final Item SPORESTALKER = register("sporestalker",
            new TideFishItem(new Item.Properties().food(TideFoods.UNHEALTHY_RAW_FISH), 6.0f));
    public static final Item LEAFBACK = register("leafback",
            new TideFishItem(new Item.Properties().food(TideFoods.UNHEALTHY_RAW_FISH), 6.0f));
    public static final Item FLUTTERGILL = register("fluttergill",
            new TideFishItem(new Item.Properties().food(TideFoods.AVERAGE_RAW_FISH), 6.0f));
    public static final Item PINE_PERCH = register("pine_perch",
            new TideFishItem(new Item.Properties(), 6.0f));

    public static final Item EMBER_KOI = register("ember_koi",
            new TideFishItem(new Item.Properties().food(TideFoods.FIERY_RAW_FISH).fireResistant(), 2.6f));
    public static final Item INFERNO_GUPPY = register("inferno_guppy",
            new TideFishItem(new Item.Properties().food(TideFoods.FIERY_RAW_FISH).fireResistant(), 2.8f));
    public static final Item OBSIDIAN_PIKE = register("obsidian_pike",
            new TideFishItem(new Item.Properties().fireResistant(), 3.3f));
    public static final Item VOLCANO_TUNA = register("volcano_tuna",
            new TideFishItem(new Item.Properties().food(TideFoods.FIERY_RAW_FISH).fireResistant(), 5.0f));

    public static final Item MAGMA_MACKEREL = register("magma_mackerel",
            new TideFishItem(new Item.Properties().food(TideFoods.FIERY_RAW_FISH).fireResistant(), 3.0f));
    public static final Item WARPED_GUPPY = register("warped_guppy",
            new TideFishItem(new Item.Properties().food(TideFoods.AVERAGE_RAW_FISH).fireResistant(), 3.5f));
    public static final Item CRIMSON_FANGJAW = register("crimson_fangjaw",
            new TideFishItem(new Item.Properties().food(TideFoods.AVERAGE_RAW_FISH).fireResistant(), 3.5f));
    public static final Item ASHEN_PERCH = register("ashen_perch",
            new TideFishItem(new Item.Properties().food(TideFoods.FIERY_RAW_FISH).fireResistant(), 3.2f));
    public static final Item WITHERFIN = register("witherfin",
            new TideFishItem(new Item.Properties().food(TideFoods.AVERAGE_RAW_FISH).fireResistant(), 5.2f));
    public static final Item SOULSCALER = register("soulscaler",
            new TideFishItem(new Item.Properties().food(TideFoods.AVERAGE_RAW_FISH).fireResistant(), 3.8f));
    public static final Item BLAZING_SWORDFISH = register("blazing_swordfish",
            new BlazingSwordfishItem(TideTiers.BLAZING_FISH, new Item.Properties().fireResistant()
                    .attributes(SwordItem.createAttributes(TideTiers.BLAZING_FISH, 3, -2.4F))));

    public static final Item MIDAS_FISH = register("midas_fish",
            new TideFishItem(new Item.Properties().fireResistant().rarity(Rarity.EPIC), 8.0f));
    public static final Item VOIDSEEKER = register("voidseeker",
            new TideFishItem(new Item.Properties().food(TideFoods.VOIDSEEKER).fireResistant().rarity(Rarity.EPIC), 8.0f));
    public static final Item SHOOTING_STARFISH = register("shooting_starfish",
            new TideFishItem(new Item.Properties().fireResistant().rarity(Rarity.EPIC), 8.0f));

    public static final Item TROUT_SPAWN_EGG = register("trout_spawn_egg",
            new SpawnEggItem(TideEntityTypes.TROUT, 2580837, 7197881, new Item.Properties()));
    public static final Item BASS_SPAWN_EGG = register("bass_spawn_egg",
            new SpawnEggItem(TideEntityTypes.BASS, 11250290, 4802877, new Item.Properties()));
    public static final Item YELLOW_PERCH_SPAWN_EGG = register("yellow_perch_spawn_egg",
            new SpawnEggItem(TideEntityTypes.YELLOW_PERCH, 5056290, 12489757, new Item.Properties()));
    public static final Item BLUEGILL_SPAWN_EGG = register("bluegill_spawn_egg",
            new SpawnEggItem(TideEntityTypes.BLUEGILL, 3241569, 8747597, new Item.Properties()));
    public static final Item MINT_CARP_SPAWN_EGG = register("mint_carp_spawn_egg",
            new SpawnEggItem(TideEntityTypes.MINT_CARP, 9492648, 4095594, new Item.Properties()));
    public static final Item PIKE_SPAWN_EGG = register("pike_spawn_egg",
            new SpawnEggItem(TideEntityTypes.PIKE, 7889995, 16048549, new Item.Properties()));
    public static final Item GUPPY_SPAWN_EGG = register("guppy_spawn_egg",
            new SpawnEggItem(TideEntityTypes.GUPPY, 1207235, 15955968, new Item.Properties()));
    public static final Item CATFISH_SPAWN_EGG = register("catfish_spawn_egg",
            new SpawnEggItem(TideEntityTypes.CATFISH, 13402689, 5254425, new Item.Properties()));
    public static final Item CLAYFISH_SPAWN_EGG = register("clayfish_spawn_egg",
            new SpawnEggItem(TideEntityTypes.CLAYFISH, 11316925, 5332864, new Item.Properties()));

    public static final Item TUNA_SPAWN_EGG = register("tuna_spawn_egg",
            new SpawnEggItem(TideEntityTypes.TUNA, 0xa8b5af, 0x303336, new Item.Properties()));
    public static final Item OCEAN_PERCH_SPAWN_EGG = register("ocean_perch_spawn_egg",
            new SpawnEggItem(TideEntityTypes.OCEAN_PERCH, 0xa46250, 0x51211a, new Item.Properties()));
    public static final Item MACKEREL_SPAWN_EGG = register("mackerel_spawn_egg",
            new SpawnEggItem(TideEntityTypes.MACKEREL, 0x8e9480, 0x003d4d, new Item.Properties()));
    public static final Item ANGELFISH_SPAWN_EGG = register("angelfish_spawn_egg",
            new SpawnEggItem(TideEntityTypes.ANGELFISH, 0x2e5c6b, 0x0e1f2e, new Item.Properties()));
    public static final Item BARRACUDA_SPAWN_EGG = register("barracuda_spawn_egg",
            new SpawnEggItem(TideEntityTypes.BARRACUDA, 0x5c5943, 0x16313d, new Item.Properties()));
    public static final Item SAILFISH_SPAWN_EGG = register("sailfish_spawn_egg",
            new SpawnEggItem(TideEntityTypes.SAILFISH, 0x698c8f, 0x466c82, new Item.Properties()));

    public static final Item COOKED_FISH = register("cooked_fish",
            new Item(new Item.Properties().food(TideFoods.COOKED_FISH)));

    // Farmer's delight compat

    public static final Item FISH_SLICE = register("fish_slice", new Item(new Item.Properties()
            .food(new FoodProperties.Builder()
                    .nutrition(1)
                    .saturationModifier(0.1f)
                    .fast().build()
            )
    ));
    public static final Item COOKED_FISH_SLICE = register("cooked_fish_slice", new Item(new Item.Properties()
            .food(new FoodProperties.Builder()
                    .nutrition(3)
                    .saturationModifier(0.5f)
                    .fast().build()
            )
    ));

    public static Item register(String key, Item item) {
        ITEMS.put(key, item);
        ORDERED_ITEMS.add(item);
        return item;
    }

    public static void init() {
        ITEMS.forEach(Tide.PLATFORM::registerItem);
    }

    public static void assignTags() {
        JOURNAL_FISH_LIST = ORDERED_ITEMS.stream().filter(item -> item instanceof TideFishItem)
                .map((item) -> BuiltInRegistries.ITEM.getResourceKey(item).orElse(null)).toList();

        COOKABLE_FISH_LIST = ORDERED_ITEMS.stream().filter(item -> {
            if (item instanceof TideFishItem fishItem)
                return fishItem.isCookable();
            else return false;
        }).map((item) -> BuiltInRegistries.ITEM.getResourceKey(item).orElse(null)).toList();
    }

    public static ArrayList<Item> getItems() {
        if (!ORDERED_ITEMS.contains(Items.FISHING_ROD)) ORDERED_ITEMS.addFirst(Items.FISHING_ROD);
        return ORDERED_ITEMS;
    }
}
