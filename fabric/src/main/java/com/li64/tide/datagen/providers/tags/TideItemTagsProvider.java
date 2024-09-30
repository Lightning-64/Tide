package com.li64.tide.datagen.providers.tags;

import com.li64.tide.data.TideTags;
import com.li64.tide.registries.TideItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalItemTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

public class TideItemTagsProvider extends FabricTagProvider<Item> {
    public TideItemTagsProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, Registries.ITEM, registries);
    }

    @Override
    public @NotNull String getName() {
        return "Tide Item Tags";
    }

    @Override
    protected void addTags(HolderLookup.@NotNull Provider provider) {
        /* Mod specific tags */

        getOrCreateTagBuilder(TideTags.Items.CUSTOMIZABLE_RODS)
                .add(Items.FISHING_ROD)
                .add(TideItems.STONE_FISHING_ROD)
                .add(TideItems.IRON_FISHING_ROD)
                .add(TideItems.GOLDEN_FISHING_ROD)
                .add(TideItems.CRYSTAL_FISHING_ROD)
                .add(TideItems.DIAMOND_FISHING_ROD)
                .add(TideItems.NETHERITE_FISHING_ROD);

        getOrCreateTagBuilder(TideTags.Items.LINES)
                .add(TideItems.FISHING_LINE)
                .add(TideItems.BRAIDED_LINE)
                .add(TideItems.REINFORCED_LINE)
                .add(TideItems.FORTUNE_LINE);

        getOrCreateTagBuilder(TideTags.Items.BOBBERS)
                .add(TideItems.RED_FISHING_BOBBER)
                .add(TideItems.ORANGE_FISHING_BOBBER)
                .add(TideItems.YELLOW_FISHING_BOBBER)
                .add(TideItems.LIME_FISHING_BOBBER)
                .add(TideItems.GREEN_FISHING_BOBBER)
                .add(TideItems.LIGHT_BLUE_FISHING_BOBBER)
                .add(TideItems.CYAN_FISHING_BOBBER)
                .add(TideItems.BLUE_FISHING_BOBBER)
                .add(TideItems.PURPLE_FISHING_BOBBER)
                .add(TideItems.MAGENTA_FISHING_BOBBER)
                .add(TideItems.PINK_FISHING_BOBBER)
                .add(TideItems.LIGHT_GRAY_FISHING_BOBBER)
                .add(TideItems.GRAY_FISHING_BOBBER)
                .add(TideItems.BROWN_FISHING_BOBBER)
                .add(TideItems.WHITE_FISHING_BOBBER)
                .add(TideItems.BLACK_FISHING_BOBBER);

        getOrCreateTagBuilder(TideTags.Items.HOOKS)
                .add(TideItems.FISHING_HOOK)
                .add(TideItems.IRON_FISHING_HOOK)
                .add(TideItems.LAVAPROOF_FISHING_HOOK);

        getOrCreateTagBuilder(TideTags.Items.BAIT_PLANTS)
                .forceAddTag(neoForgeConventionTag("crops"))
                .forceAddTag(neoForgeConventionTag("mushrooms"))
                .forceAddTag(ItemTags.FLOWERS);

        getOrCreateTagBuilder(TideTags.Items.VANILLA_FISH)
                .add(Items.COD)
                .add(Items.SALMON)
                .add(Items.TROPICAL_FISH)
                .add(Items.PUFFERFISH);

        getOrCreateTagBuilder(TideTags.Items.BIOME_FISH)
                .add(TideItems.PRAIRIE_PIKE)
                .add(TideItems.SANDSKIPPER)
                .add(TideItems.BLOSSOM_BASS)
                .add(TideItems.OAKFISH)
                .add(TideItems.FROSTBITE_FLOUNDER)
                .add(TideItems.MIRAGE_CATFISH)
                .add(TideItems.ECHOFIN_SNAPPER)
                .add(TideItems.SUNSPIKE_GOBY)
                .add(TideItems.BIRCH_TROUT)
                .add(TideItems.STONEFISH)
                .add(TideItems.DRIPSTONE_DARTER)
                .add(TideItems.SLIMEFIN_SNAPPER)
                .add(TideItems.SPORESTALKER)
                .add(TideItems.LEAFBACK)
                .add(TideItems.FLUTTERGILL)
                .add(TideItems.PINE_PERCH);

        getOrCreateTagBuilder(TideTags.Items.LEGENDARY_FISH)
                .add(TideItems.MIDAS_FISH)
                .add(TideItems.VOIDSEEKER)
                .add(TideItems.SHOOTING_STARFISH);

        getOrCreateTagBuilder(TideTags.Items.JOURNAL_FISH)
                .forceAddTag(TideTags.Items.VANILLA_FISH)
                .addAll(TideItems.JOURNAL_FISH_LIST);

        getOrCreateTagBuilder(TideTags.Items.COOKABLE_FISH)
                .addAll(TideItems.COOKABLE_FISH_LIST);

        getOrCreateTagBuilder(TideTags.Items.COOKED_FISH)
                .add(TideItems.COOKED_FISH)
                .add(Items.COOKED_COD)
                .add(Items.COOKED_SALMON);

        getOrCreateTagBuilder(TideTags.Items.TWILIGHT_ANGLER_EATABLE)
                .forceAddTag(TideTags.Items.COOKABLE_FISH);

        /* Common tags */

        getOrCreateTagBuilder(TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("forge", "tools/fishing_rods")))
                .forceAddTag(TideTags.Items.CUSTOMIZABLE_RODS);

        getOrCreateTagBuilder(ItemTags.ARROWS).add(TideItems.DEEP_AQUA_ARROW);
        getOrCreateTagBuilder(ItemTags.CAT_FOOD).forceAddTag(TideTags.Items.COOKABLE_FISH);
        getOrCreateTagBuilder(ItemTags.FISHES).forceAddTag(TideTags.Items.JOURNAL_FISH);
        getOrCreateTagBuilder(ItemTags.FISHING_ENCHANTABLE).forceAddTag(TideTags.Items.CUSTOMIZABLE_RODS);
        getOrCreateTagBuilder(ItemTags.SWORD_ENCHANTABLE).add(TideItems.BLAZING_SWORDFISH);
        getOrCreateTagBuilder(ItemTags.SWORDS).add(TideItems.BLAZING_SWORDFISH);

        /* Compat tags */

        getOrCreateTagBuilder(TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("stardew_fishing", "starts_minigame")))
                .addTag(TideTags.Items.JOURNAL_FISH);

        getOrCreateTagBuilder(neoForgeConventionTag("safe_raw_fish"))
                .addTag(TideTags.Items.COOKABLE_FISH)
                .add(TideItems.FISH_SLICE);
    }

    public TagKey<Item> neoForgeConventionTag(String name) {
        return TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("c", name));
    }
}
