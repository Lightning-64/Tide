package com.li64.tide.registries;

import com.li64.tide.Tide;
import com.li64.tide.registries.blocks.JellyTorchBlockItem;
import com.li64.tide.registries.items.*;
import net.minecraft.core.Direction;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.food.Foods;
import net.minecraft.world.item.*;
import net.minecraft.world.item.component.Consumables;
import net.minecraft.world.level.material.Fluids;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

@SuppressWarnings("unused")
public class TideItems {
    public static final HashMap<ResourceKey<Item>, Item> ITEMS = new HashMap<>();
    private static final ArrayList<Item> ORDERED_ITEMS = new ArrayList<>();

    public static List<ResourceKey<Item>> JOURNAL_FISH_LIST = new ArrayList<>();
    public static List<ResourceKey<Item>> COOKABLE_FISH_LIST = new ArrayList<>();

    public static final Map<ResourceLocation, Function<Item.Properties, Item>> ITEM_OVERRIDE_MAP = Map.of(
            ResourceLocation.withDefaultNamespace("fishing_rod"),
            (properties) -> new TideFishingRodItem(32, properties)
    );

    public static final Item STONE_FISHING_ROD = register("stone_fishing_rod",
            properties -> new TideFishingRodItem(48, properties));
    public static final Item IRON_FISHING_ROD = register("iron_fishing_rod",
            properties -> new TideFishingRodItem(64, properties));
    public static final Item GOLDEN_FISHING_ROD = register("golden_fishing_rod",
            properties -> new TideFishingRodItem(32, properties));
    public static final Item CRYSTAL_FISHING_ROD = register("crystal_fishing_rod",
            properties -> new TideFishingRodItem(80, properties));
    public static final Item DIAMOND_FISHING_ROD = register("diamond_fishing_rod",
            properties -> new TideFishingRodItem(128, properties));
    public static final Item NETHERITE_FISHING_ROD = register("netherite_fishing_rod",
            properties -> new TideFishingRodItem(512, properties));

    public static final Item BAIT = register("bait", Item::new);
    public static final Item LUCKY_BAIT = register("lucky_bait", Item::new);
    public static final Item MAGNETIC_BAIT = register("magnetic_bait", Item::new);

    public static final Item RED_FISHING_BOBBER = register("red_fishing_bobber", FishingBobberItem::new);
    public static final Item ORANGE_FISHING_BOBBER = register("orange_fishing_bobber", FishingBobberItem::new);
    public static final Item YELLOW_FISHING_BOBBER = register("yellow_fishing_bobber", FishingBobberItem::new);
    public static final Item LIME_FISHING_BOBBER = register("lime_fishing_bobber", FishingBobberItem::new);
    public static final Item GREEN_FISHING_BOBBER = register("green_fishing_bobber", FishingBobberItem::new);
    public static final Item CYAN_FISHING_BOBBER = register("cyan_fishing_bobber", FishingBobberItem::new);
    public static final Item LIGHT_BLUE_FISHING_BOBBER = register("light_blue_fishing_bobber", FishingBobberItem::new);
    public static final Item BLUE_FISHING_BOBBER = register("blue_fishing_bobber", FishingBobberItem::new);
    public static final Item PURPLE_FISHING_BOBBER = register("purple_fishing_bobber", FishingBobberItem::new);
    public static final Item MAGENTA_FISHING_BOBBER = register("magenta_fishing_bobber", FishingBobberItem::new);
    public static final Item PINK_FISHING_BOBBER = register("pink_fishing_bobber", FishingBobberItem::new);
    public static final Item WHITE_FISHING_BOBBER = register("white_fishing_bobber", FishingBobberItem::new);
    public static final Item LIGHT_GRAY_FISHING_BOBBER = register("light_gray_fishing_bobber", FishingBobberItem::new);
    public static final Item GRAY_FISHING_BOBBER = register("gray_fishing_bobber", FishingBobberItem::new);
    public static final Item BLACK_FISHING_BOBBER = register("black_fishing_bobber", FishingBobberItem::new);
    public static final Item BROWN_FISHING_BOBBER = register("brown_fishing_bobber", FishingBobberItem::new);
    public static final Item APPLE_FISHING_BOBBER = register("apple_fishing_bobber", FishingBobberItem::new, new Item.Properties().food(Foods.APPLE));
    public static final Item GOLDEN_APPLE_FISHING_BOBBER = register("golden_apple_fishing_bobber", FishingBobberItem::new, new Item.Properties().food(Foods.GOLDEN_APPLE, Consumables.GOLDEN_APPLE));
    public static final Item ENCHANTED_GOLDEN_APPLE_FISHING_BOBBER = register("enchanted_golden_apple_fishing_bobber", FishingBobberItem::new, new Item.Properties().food(Foods.ENCHANTED_GOLDEN_APPLE, Consumables.ENCHANTED_GOLDEN_APPLE).component(DataComponents.ENCHANTMENT_GLINT_OVERRIDE, true));
    public static final Item IRON_FISHING_BOBBER = register("iron_fishing_bobber", FishingBobberItem::new);
    public static final Item GOLDEN_FISHING_BOBBER = register("golden_fishing_bobber", FishingBobberItem::new);
    public static final Item DIAMOND_FISHING_BOBBER = register("diamond_fishing_bobber", FishingBobberItem::new);
    public static final Item NETHERITE_FISHING_BOBBER = register("netherite_fishing_bobber", FishingBobberItem::new);
    public static final Item AMETHYST_FISHING_BOBBER = register("amethyst_fishing_bobber", FishingBobberItem::new);
    public static final Item ECHO_FISHING_BOBBER = register("echo_fishing_bobber", FishingBobberItem::new);
    public static final Item CHORUS_FISHING_BOBBER = register("chorus_fishing_bobber", FishingBobberItem::new);
    public static final Item FEATHER_FISHING_BOBBER = register("feather_fishing_bobber", FishingBobberItem::new);
    public static final Item LICHEN_FISHING_BOBBER = register("lichen_fishing_bobber", FishingBobberItem::new);
    public static final Item NAUTILUS_FISHING_BOBBER = register("nautilus_fishing_bobber", FishingBobberItem::new);
    public static final Item PEARL_FISHING_BOBBER = register("pearl_fishing_bobber", FishingBobberItem::new);
    public static final Item HEART_FISHING_BOBBER = register("heart_fishing_bobber", FishingBobberItem::new);
    public static final Item GRASSY_FISHING_BOBBER = register("grassy_fishing_bobber", FishingBobberItem::new);

    public static final Item FISHING_HOOK = register("fishing_hook", FishingHookItem::new);
    public static final Item IRON_FISHING_HOOK = register("iron_fishing_hook", properties -> new FishingHookItem(properties, "item.tide.iron_hook.desc"));
    public static final Item LAVAPROOF_FISHING_HOOK = register("lavaproof_fishing_hook", properties -> new FishingHookItem(properties, "item.tide.lavaproof_hook.desc"));

    public static final Item FISHING_LINE = register("fishing_line", FishingLineItem::new);
    public static final Item BRAIDED_LINE = register("braided_line", properties -> new FishingLineItem(properties, "item.tide.braided_line.desc"));
    public static final Item REINFORCED_LINE = register("reinforced_line", properties -> new FishingLineItem(properties, "item.tide.reinforced_line.desc"));
    public static final Item FORTUNE_LINE = register("fortune_line", properties -> new FishingLineItem(properties, "item.tide.fortune_line.desc"));

    public static final Item FISHING_JOURNAL = register("fishing_journal", FishingJournalItem::new);

    public static final Item ANGLER_WORKSHOP = register("angler_workshop", properties ->
            new BlockItem(TideBlocks.ANGLER_WORKSHOP, properties), new Item.Properties().useBlockDescriptionPrefix());

    public static final Item SURFACE_LOOT_CRATE = register("surface_loot_crate", properties ->
            new BlockItem(TideBlocks.SURFACE_LOOT_CRATE, properties), new Item.Properties().useBlockDescriptionPrefix());
    public static final Item OBSIDIAN_LOOT_CRATE = register("obsidian_loot_crate", properties ->
            new BlockItem(TideBlocks.OBSIDIAN_LOOT_CRATE, properties), new Item.Properties().useBlockDescriptionPrefix());
    public static final Item END_LOOT_CRATE = register("end_loot_crate", properties ->
            new BlockItem(TideBlocks.END_LOOT_CRATE, properties), new Item.Properties().useBlockDescriptionPrefix());

    public static final Item FISH_BONE = register("fish_bone",
            Item::new, new Item.Properties().fireResistant());
    public static final Item OBSIDIAN_FRAGMENT = register("obsidian_fragment",
            Item::new, new Item.Properties().fireResistant());

    public static final Item ALGAE = register("algae", properties ->
            new PlaceOnWaterBlockItem(TideBlocks.ALGAE, properties), new Item.Properties().useBlockDescriptionPrefix());
    public static final Item JELLY_TORCH = register("jelly_torch",  properties ->
            new JellyTorchBlockItem(TideBlocks.JELLY_TORCH, TideBlocks.JELLY_WALL_TORCH, properties, Direction.DOWN), new Item.Properties().useBlockDescriptionPrefix());

    public static final Item SPECTRAL_SCALE = register("spectral_scale", Item::new);
    public static final Item TWILIGHT_SCALE = register("twilight_scale", Item::new);

    public static final Item DEEP_AQUA_CRYSTAL = register("deep_aqua_crystal", Item::new);
    public static final Item DEEP_AQUA_ARROW = register("deep_aqua_arrow", DeepAquaArrowItem::new);

    public static final Item TROUT_BUCKET = register("trout_bucket", properties -> 
            new MobBucketItem(TideEntityTypes.TROUT, Fluids.WATER, SoundEvents.BUCKET_EMPTY_FISH, properties), new Item.Properties().stacksTo(1));
    public static final Item BASS_BUCKET = register("bass_bucket", properties ->
            new MobBucketItem(TideEntityTypes.BASS, Fluids.WATER, SoundEvents.BUCKET_EMPTY_FISH, properties), new Item.Properties().stacksTo(1));
    public static final Item YELLOW_PERCH_BUCKET = register("yellow_perch_bucket", properties ->
            new MobBucketItem(TideEntityTypes.YELLOW_PERCH, Fluids.WATER, SoundEvents.BUCKET_EMPTY_FISH, properties), new Item.Properties().stacksTo(1));
    public static final Item BLUEGILL_BUCKET = register("bluegill_bucket", properties ->
            new MobBucketItem(TideEntityTypes.BLUEGILL, Fluids.WATER, SoundEvents.BUCKET_EMPTY_FISH, properties), new Item.Properties().stacksTo(1));
    public static final Item MINT_CARP_BUCKET = register("mint_carp_bucket", properties ->
            new MobBucketItem(TideEntityTypes.MINT_CARP, Fluids.WATER, SoundEvents.BUCKET_EMPTY_FISH, properties), new Item.Properties().stacksTo(1));
    public static final Item PIKE_BUCKET = register("pike_bucket", properties ->
            new MobBucketItem(TideEntityTypes.PIKE, Fluids.WATER, SoundEvents.BUCKET_EMPTY_FISH, properties), new Item.Properties().stacksTo(1));
    public static final Item GUPPY_BUCKET = register("guppy_bucket", properties ->
            new MobBucketItem(TideEntityTypes.GUPPY, Fluids.WATER, SoundEvents.BUCKET_EMPTY_FISH, properties), new Item.Properties().stacksTo(1));
    public static final Item CATFISH_BUCKET = register("catfish_bucket", properties ->
            new MobBucketItem(TideEntityTypes.CATFISH, Fluids.WATER, SoundEvents.BUCKET_EMPTY_FISH, properties), new Item.Properties().stacksTo(1));
    public static final Item CLAYFISH_BUCKET = register("clayfish_bucket", properties ->
            new MobBucketItem(TideEntityTypes.CLAYFISH, Fluids.WATER, SoundEvents.BUCKET_EMPTY_FISH, properties), new Item.Properties().stacksTo(1));

    public static final Item TUNA_BUCKET = register("tuna_bucket", properties ->
            new MobBucketItem(TideEntityTypes.TUNA, Fluids.WATER, SoundEvents.BUCKET_EMPTY_FISH, properties), new Item.Properties().stacksTo(1));
    public static final Item OCEAN_PERCH_BUCKET = register("ocean_perch_bucket", properties ->
            new MobBucketItem(TideEntityTypes.OCEAN_PERCH, Fluids.WATER, SoundEvents.BUCKET_EMPTY_FISH, properties), new Item.Properties().stacksTo(1));
    public static final Item MACKEREL_BUCKET = register("mackerel_bucket", properties ->
            new MobBucketItem(TideEntityTypes.MACKEREL, Fluids.WATER, SoundEvents.BUCKET_EMPTY_FISH, properties), new Item.Properties().stacksTo(1));
    public static final Item ANGELFISH_BUCKET = register("angelfish_bucket", properties ->
            new MobBucketItem(TideEntityTypes.ANGELFISH, Fluids.WATER, SoundEvents.BUCKET_EMPTY_FISH, properties), new Item.Properties().stacksTo(1));
    public static final Item BARRACUDA_BUCKET = register("barracuda_bucket", properties ->
            new MobBucketItem(TideEntityTypes.BARRACUDA, Fluids.WATER, SoundEvents.BUCKET_EMPTY_FISH, properties), new Item.Properties().stacksTo(1));
    public static final Item SAILFISH_BUCKET = register("sailfish_bucket", properties ->
            new MobBucketItem(TideEntityTypes.SAILFISH, Fluids.WATER, SoundEvents.BUCKET_EMPTY_FISH, properties), new Item.Properties().stacksTo(1));

    public static final Item MINT_CARP = register("mint_carp", properties -> 
            new TideFishItem(properties, 0.1f, true), new Item.Properties().food(TideFoods.RAW_FISH));
    public static final Item CLAYFISH = register("clayfish", properties ->
            new TideFishItem(properties, 1.2f), new Item.Properties().food(TideFoods.UNHEALTHY_RAW_FISH, TideFoods.UNHEALTHY_RAW_FISH_CONSUMABLE));
    public static final Item HARDENED_CLAYFISH = register("hardened_clayfish", properties ->
            new TideFishItem(properties, 0), new Item.Properties());
    public static final Item TROUT = register("trout", properties ->
            new TideFishItem(properties, 0f, true), new Item.Properties().food(TideFoods.RAW_FISH));
    public static final Item ANGELFISH = register("angelfish", properties ->
            new TideFishItem(properties, 0.4f, true), new Item.Properties().food(TideFoods.RAW_FISH));
    public static final Item BARRACUDA = register("barracuda", properties ->
            new TideFishItem(properties, 0.5f, true), new Item.Properties().food(TideFoods.RAW_FISH));
    public static final Item SAILFISH = register("sailfish", properties ->
            new TideFishItem(properties, 0.8f, true), new Item.Properties().food(TideFoods.RAW_FISH));
    public static final Item CATFISH = register("catfish", properties ->
            new TideFishItem(properties, 0.9f, true), new Item.Properties().food(TideFoods.RAW_FISH));
    public static final Item PIKE = register("pike", properties ->
            new TideFishItem(properties, 0.6f, true), new Item.Properties().food(TideFoods.RAW_FISH));
    public static final Item BASS = register("bass", properties ->
            new TideFishItem(properties, 0.5f, true), new Item.Properties().food(TideFoods.RAW_FISH));
    public static final Item TUNA = register("tuna", properties ->
            new TideFishItem(properties, 0.3f, true), new Item.Properties().food(TideFoods.RAW_FISH));
    public static final Item YELLOW_PERCH = register("yellow_perch", properties ->
            new TideFishItem(properties, 0.5f, true), new Item.Properties().food(TideFoods.RAW_FISH));
    public static final Item OCEAN_PERCH = register("ocean_perch", properties ->
            new TideFishItem(properties, 0.6f, true), new Item.Properties().food(TideFoods.RAW_FISH));
    public static final Item GUPPY = register("guppy", properties ->
            new TideFishItem(properties, 0.3f), new Item.Properties().food(TideFoods.UNHEALTHY_RAW_FISH, TideFoods.UNHEALTHY_RAW_FISH_CONSUMABLE));
    public static final Item BLUEGILL = register("bluegill", properties ->
            new TideFishItem(properties, 0f, true), new Item.Properties().food(TideFoods.RAW_FISH));
    public static final Item MACKEREL = register("mackerel", properties ->
            new TideFishItem(properties, 0.9f, true), new Item.Properties().food(TideFoods.RAW_FISH));

    public static final Item CAVE_EEL = register("cave_eel", properties ->
            new TideFishItem(properties, 1.2f), new Item.Properties().food(TideFoods.RAW_FISH));
    public static final Item CRYSTAL_SHRIMP = register("crystal_shrimp", properties ->
            new TideFishItem(properties, 2.1f), new Item.Properties().food(TideFoods.RAW_FISH));
    public static final Item IRON_TETRA = register("iron_tetra", properties ->
            new TideFishItem(properties, 1.6f), new Item.Properties().food(TideFoods.UNHEALTHY_RAW_FISH, TideFoods.UNHEALTHY_RAW_FISH_CONSUMABLE));
    public static final Item GLOWFISH = register("glowfish", properties ->
            new TideFishItem(properties, 0.7f, true), new Item.Properties().food(TideFoods.RAW_FISH));
    public static final Item ANGLER_FISH = register("anglerfish", properties ->
            new TideFishItem(properties, 1.0f, true), new Item.Properties().food(TideFoods.RAW_FISH));
    public static final Item CAVE_CRAWLER = register("cave_crawler", properties ->
            new TideFishItem(properties, 0.8f, true), new Item.Properties().food(TideFoods.RAW_FISH));
    public static final Item GILDED_MINNOW = register("gilded_minnow", properties ->
            new TideFishItem(properties, 3.2f), new Item.Properties().food(TideFoods.GOLDEN_FISH, TideFoods.GOLDEN_FISH_CONSUMABLE));

    public static final Item ABYSS_ANGLER = register("abyss_angler", properties ->
            new TideFishItem(properties, 3.6f, true), new Item.Properties().food(TideFoods.RAW_FISH));
    public static final Item CRYSTALLINE_CARP = register("crystalline_carp", properties ->
            new TideFishItem(properties, 4.1f), new Item.Properties().food(TideFoods.UNHEALTHY_RAW_FISH, TideFoods.UNHEALTHY_RAW_FISH_CONSUMABLE));
    public static final Item LAPIS_LANTERNFISH = register("lapis_lanternfish", properties ->
            new TideFishItem(properties, 2.4f), new Item.Properties().food(TideFoods.UNHEALTHY_RAW_FISH, TideFoods.UNHEALTHY_RAW_FISH_CONSUMABLE));
    public static final Item LUMINESCENT_JELLYFISH = register("luminescent_jellyfish", properties ->
            new TideFishItem(properties, 2.2f), new Item.Properties().food(TideFoods.UNHEALTHY_RAW_FISH, TideFoods.UNHEALTHY_RAW_FISH_CONSUMABLE));
    public static final Item SHADOW_SNAPPER = register("shadow_snapper", properties ->
            new TideFishItem(properties, 2.0f, true), new Item.Properties().food(TideFoods.RAW_FISH));
    public static final Item DEEP_GROUPER = register("deep_grouper", properties ->
            new TideFishItem(properties, 1.8f, true), new Item.Properties().food(TideFoods.RAW_FISH));
    public static final Item BEDROCK_TETRA = register("bedrock_tetra", properties ->
            new TideFishItem(properties, 5.6f), new Item.Properties());

    public static final Item END_STONE_PERCH = register("endstone_perch", properties ->
            new TideFishItem(properties, 4.1f), new Item.Properties());
    public static final Item ENDERFIN = register("enderfin", properties ->
            new TideFishItem(properties, 4.5f), new Item.Properties().food(TideFoods.RAW_FISH));
    public static final Item ENDERGAZER = register("endergazer", properties ->
            new TideFishItem(properties, 5.2f), new Item.Properties().food(TideFoods.RAW_FISH));
    public static final Item PURPUR_PIKE = register("purpur_pike", properties ->
            new TideFishItem(properties, 5.0f), new Item.Properties().food(TideFoods.UNHEALTHY_RAW_FISH, TideFoods.UNHEALTHY_RAW_FISH_CONSUMABLE));
    public static final Item CHORUS_COD = register("chorus_cod", properties ->
            new TideFishItem(properties, 4.0f), new Item.Properties().food(Foods.CHORUS_FRUIT, Consumables.CHORUS_FRUIT));
    public static final Item ELYTROUT = register("elytrout", properties ->
            new TideFishItem(properties, 6.8f), new Item.Properties().food(TideFoods.RAW_FISH));

    // I know spelled the item ID wrong, it's just too late to change it at this point
    public static final Item PRAIRIE_PIKE = register("prarie_pike", properties ->
            new TideFishItem(properties, 6.0f), new Item.Properties().food(TideFoods.UNHEALTHY_RAW_FISH, TideFoods.UNHEALTHY_RAW_FISH_CONSUMABLE));
    public static final Item SANDSKIPPER = register("sandskipper", properties ->
            new TideFishItem(properties, 6.0f), new Item.Properties().food(TideFoods.RAW_FISH));
    public static final Item BLOSSOM_BASS = register("blossom_bass", properties ->
            new TideFishItem(properties, 6.0f), new Item.Properties().food(TideFoods.UNHEALTHY_RAW_FISH, TideFoods.UNHEALTHY_RAW_FISH_CONSUMABLE));
    public static final Item OAKFISH = register("oakfish", properties ->
            new TideFishItem(properties, 6.0f), new Item.Properties());
    public static final Item FROSTBITE_FLOUNDER = register("frostbite_flounder", properties ->
            new TideFishItem(properties, 6.0f), new Item.Properties().food(TideFoods.RAW_FISH));
    public static final Item MIRAGE_CATFISH = register("mirage_catfish", properties ->
            new TideFishItem(properties, 6.0f), new Item.Properties().food(TideFoods.RAW_FISH));
    public static final Item ECHOFIN_SNAPPER = register("echofin_snapper", properties ->
            new TideFishItem(properties, 6.0f), new Item.Properties().food(TideFoods.UNHEALTHY_RAW_FISH, TideFoods.UNHEALTHY_RAW_FISH_CONSUMABLE));
    public static final Item SUNSPIKE_GOBY = register("sunspike_goby", properties ->
            new TideFishItem(properties, 6.0f), new Item.Properties().food(TideFoods.UNHEALTHY_RAW_FISH, TideFoods.UNHEALTHY_RAW_FISH_CONSUMABLE));
    public static final Item BIRCH_TROUT = register("birch_trout", properties ->
            new TideFishItem(properties, 6.0f), new Item.Properties());
    public static final Item STONEFISH = register("stonefish", properties ->
            new TideFishItem(properties, 6.0f), new Item.Properties());
    public static final Item DRIPSTONE_DARTER = register("dripstone_darter", properties ->
            new TideFishItem(properties, 6.0f), new Item.Properties());
    public static final Item SLIMEFIN_SNAPPER = register("slimefin_snapper", properties ->
            new TideFishItem(properties, 6.0f), new Item.Properties().food(TideFoods.UNHEALTHY_RAW_FISH, TideFoods.UNHEALTHY_RAW_FISH_CONSUMABLE));
    public static final Item SPORESTALKER = register("sporestalker", properties ->
            new TideFishItem(properties, 6.0f), new Item.Properties().food(TideFoods.UNHEALTHY_RAW_FISH, TideFoods.UNHEALTHY_RAW_FISH_CONSUMABLE));
    public static final Item LEAFBACK = register("leafback", properties ->
            new TideFishItem(properties, 6.0f), new Item.Properties().food(TideFoods.UNHEALTHY_RAW_FISH, TideFoods.UNHEALTHY_RAW_FISH_CONSUMABLE));
    public static final Item FLUTTERGILL = register("fluttergill", properties ->
            new TideFishItem(properties, 6.0f), new Item.Properties().food(TideFoods.RAW_FISH));
    public static final Item PINE_PERCH = register("pine_perch", properties ->
            new TideFishItem(properties, 6.0f), new Item.Properties());

    public static final Item EMBER_KOI = register("ember_koi", properties ->
            new TideFishItem(properties, 2.6f), new Item.Properties().food(TideFoods.FIERY_RAW_FISH, TideFoods.FIERY_RAW_FISH_CONSUMABLE).fireResistant());
    public static final Item INFERNO_GUPPY = register("inferno_guppy", properties ->
            new TideFishItem(properties, 2.8f), new Item.Properties().food(TideFoods.FIERY_RAW_FISH, TideFoods.FIERY_RAW_FISH_CONSUMABLE).fireResistant());
    public static final Item OBSIDIAN_PIKE = register("obsidian_pike", properties ->
            new TideFishItem(properties, 3.3f), new Item.Properties().fireResistant());
    public static final Item VOLCANO_TUNA = register("volcano_tuna", properties ->
            new TideFishItem(properties, 5.0f), new Item.Properties().food(TideFoods.FIERY_RAW_FISH, TideFoods.FIERY_RAW_FISH_CONSUMABLE).fireResistant());

    public static final Item MAGMA_MACKEREL = register("magma_mackerel", properties ->
            new TideFishItem(properties, 3.0f), new Item.Properties().food(TideFoods.FIERY_RAW_FISH,  TideFoods.FIERY_RAW_FISH_CONSUMABLE).fireResistant());
    public static final Item WARPED_GUPPY = register("warped_guppy", properties ->
            new TideFishItem(properties, 3.5f), new Item.Properties().food(TideFoods.RAW_FISH).fireResistant());
    public static final Item CRIMSON_FANGJAW = register("crimson_fangjaw", properties ->
            new TideFishItem(properties, 3.5f), new Item.Properties().food(TideFoods.RAW_FISH).fireResistant());
    public static final Item ASHEN_PERCH = register("ashen_perch", properties ->
            new TideFishItem(properties, 3.2f), new Item.Properties().food(TideFoods.FIERY_RAW_FISH,  TideFoods.FIERY_RAW_FISH_CONSUMABLE).fireResistant());
    public static final Item WITHERFIN = register("witherfin", properties ->
            new TideFishItem(properties, 5.2f), new Item.Properties().food(TideFoods.RAW_FISH).fireResistant());
    public static final Item SOULSCALER = register("soulscaler", properties ->
            new TideFishItem(properties, 3.8f), new Item.Properties().food(TideFoods.RAW_FISH).fireResistant());
    public static final Item BLAZING_SWORDFISH = register("blazing_swordfish",
            BlazingSwordfishItem::new, new Item.Properties().fireResistant());

    public static final Item MIDAS_FISH = register("midas_fish", properties ->
            new TideFishItem(properties, 7.5f), new Item.Properties().fireResistant().rarity(Rarity.EPIC));
    public static final Item AQUATHORN = register("aquathorn", properties ->
            new TideFishItem(properties, 7.5f), new Item.Properties().fireResistant().rarity(Rarity.EPIC));
    public static final Item WINDBASS = register("windbass", properties ->
            new TideFishItem(properties, 7.5f), new Item.Properties().fireResistant().rarity(Rarity.EPIC));
    public static final Item VOIDSEEKER = register("voidseeker", properties ->
            new TideFishItem(properties, 7.5f), new Item.Properties().food(TideFoods.VOIDSEEKER, TideFoods.VOIDSEEKER_CONSUMABLE).fireResistant().rarity(Rarity.EPIC));
    public static final Item SHOOTING_STARFISH = register("shooting_starfish", properties ->
            new TideFishItem(properties, 7.5f), new Item.Properties().fireResistant().rarity(Rarity.EPIC));

    public static final Item TROUT_SPAWN_EGG = register("trout_spawn_egg",
            properties -> new SpawnEggItem(TideEntityTypes.TROUT, properties));
    public static final Item BASS_SPAWN_EGG = register("bass_spawn_egg",
            properties -> new SpawnEggItem(TideEntityTypes.BASS, properties));
    public static final Item YELLOW_PERCH_SPAWN_EGG = register("yellow_perch_spawn_egg",
            properties -> new SpawnEggItem(TideEntityTypes.YELLOW_PERCH, properties));
    public static final Item BLUEGILL_SPAWN_EGG = register("bluegill_spawn_egg",
            properties -> new SpawnEggItem(TideEntityTypes.BLUEGILL, properties));
    public static final Item MINT_CARP_SPAWN_EGG = register("mint_carp_spawn_egg",
            properties -> new SpawnEggItem(TideEntityTypes.MINT_CARP, properties));
    public static final Item PIKE_SPAWN_EGG = register("pike_spawn_egg",
            properties -> new SpawnEggItem(TideEntityTypes.PIKE, properties));
    public static final Item GUPPY_SPAWN_EGG = register("guppy_spawn_egg",
            properties -> new SpawnEggItem(TideEntityTypes.GUPPY, properties));
    public static final Item CATFISH_SPAWN_EGG = register("catfish_spawn_egg",
            properties -> new SpawnEggItem(TideEntityTypes.CATFISH, properties));
    public static final Item CLAYFISH_SPAWN_EGG = register("clayfish_spawn_egg",
            properties -> new SpawnEggItem(TideEntityTypes.CLAYFISH, properties));

    public static final Item TUNA_SPAWN_EGG = register("tuna_spawn_egg",
            properties -> new SpawnEggItem(TideEntityTypes.TUNA, properties));
    public static final Item OCEAN_PERCH_SPAWN_EGG = register("ocean_perch_spawn_egg",
            properties -> new SpawnEggItem(TideEntityTypes.OCEAN_PERCH, properties));
    public static final Item MACKEREL_SPAWN_EGG = register("mackerel_spawn_egg",
            properties -> new SpawnEggItem(TideEntityTypes.MACKEREL, properties));
    public static final Item ANGELFISH_SPAWN_EGG = register("angelfish_spawn_egg",
            properties -> new SpawnEggItem(TideEntityTypes.ANGELFISH, properties));
    public static final Item BARRACUDA_SPAWN_EGG = register("barracuda_spawn_egg",
            properties -> new SpawnEggItem(TideEntityTypes.BARRACUDA, properties));
    public static final Item SAILFISH_SPAWN_EGG = register("sailfish_spawn_egg",
            properties -> new SpawnEggItem(TideEntityTypes.SAILFISH, properties));

    public static final Item COOKED_FISH = register("cooked_fish",
            Item::new, new Item.Properties().food(TideFoods.COOKED_FISH));

    // Farmer's delight compat
    public static final Item FISH_SLICE = register("fish_slice", Item::new,
            new Item.Properties().food(TideFoods.FISH_SLICE, TideFoods.FISH_SLICE_CONSUMABLE));
    public static final Item COOKED_FISH_SLICE = register("cooked_fish_slice", Item::new,
            new Item.Properties().food(TideFoods.COOKED_FISH_SLICE, TideFoods.FISH_SLICE_CONSUMABLE));

    public static Item register(String name, Function<Item.Properties, Item> factory) {
        return register(name, factory, new Item.Properties());
    }

    public static Item register(String name, Function<Item.Properties, Item> factory, Item.Properties properties) {
        ResourceKey<Item> key = ResourceKey.create(Registries.ITEM, Tide.resource(name));
        Item item = factory.apply(properties.setId(key));
        return registerItem(key, item);
    }

    private static Item registerItem(ResourceKey<Item> key, Item item) {
        ITEMS.put(key, item);
        ORDERED_ITEMS.add(item);
        return item;
    }

    public static void init() {
        ITEMS.forEach(Tide.PLATFORM::registerItem);
    }

    public static void assignTags() {
        JOURNAL_FISH_LIST = ORDERED_ITEMS.stream().filter(item -> item instanceof TideFishItem || item instanceof BlazingSwordfishItem)
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
