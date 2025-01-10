package com.li64.tide.datagen.providers.recipes;

import com.li64.tide.data.TideTags;
import com.li64.tide.registries.TideItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.advancements.CriterionTriggerInstance;
import net.minecraft.advancements.critereon.ImpossibleTrigger;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.*;

import java.util.List;
import java.util.function.Consumer;

public class TideRecipeProvider extends FabricRecipeProvider {
    static final List<Item> BOBBERS = List.of(
            TideItems.WHITE_FISHING_BOBBER,
            TideItems.ORANGE_FISHING_BOBBER,
            TideItems.MAGENTA_FISHING_BOBBER,
            TideItems.LIGHT_BLUE_FISHING_BOBBER,
            TideItems.YELLOW_FISHING_BOBBER,
            TideItems.LIME_FISHING_BOBBER,
            TideItems.PINK_FISHING_BOBBER,
            TideItems.GRAY_FISHING_BOBBER,
            TideItems.LIGHT_GRAY_FISHING_BOBBER,
            TideItems.CYAN_FISHING_BOBBER,
            TideItems.PURPLE_FISHING_BOBBER,
            TideItems.BLUE_FISHING_BOBBER,
            TideItems.BROWN_FISHING_BOBBER,
            TideItems.GREEN_FISHING_BOBBER,
            TideItems.RED_FISHING_BOBBER,
            TideItems.BLACK_FISHING_BOBBER
    );

    public TideRecipeProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void buildRecipes(Consumer<FinishedRecipe> output) {
        // -- Shapeless --

        TagKey<Item> seeds = conventionTag("seeds");
        TagKey<Item> slimeBalls = conventionTag("slime_balls");
        TagKey<Item> obsidians = conventionTag("obsidians");
        TagKey<Item> ironNuggets = conventionTag("nuggets/iron");
        TagKey<Item> goldIngots = conventionTag("ingots/gold");
        TagKey<Item> ironIngots = conventionTag("ingots/iron");
        TagKey<Item> copperIngots = conventionTag("ingots/copper");
        TagKey<Item> diamondGems = conventionTag("gems/diamond");
        TagKey<Item> amethystGems = conventionTag("gems/amethyst");
        TagKey<Item> strings = conventionTag("strings");
        TagKey<Item> cobblestones = conventionTag("cobblestones");
        TagKey<Item> craftingTables = conventionTag("player_workstations/crafting_tables");

        ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, Items.OBSIDIAN, 1)
                .requires(TideItems.OBSIDIAN_FRAGMENT)
                .requires(TideItems.OBSIDIAN_FRAGMENT)
                .requires(TideItems.OBSIDIAN_FRAGMENT)
                .requires(TideItems.OBSIDIAN_FRAGMENT)
                .unlockedBy("has_obsidian_fragment", has(TideItems.OBSIDIAN_FRAGMENT))
                .save(output, "obsidian_from_fragments");

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, TideItems.FISHING_JOURNAL, 1)
                .requires(TideTags.Items.JOURNAL_FISH)
                .requires(Items.BOOK)
                .unlockedBy("has_book", has(Items.BOOK))
                .unlockedBy("has_fish", has(TideTags.Items.JOURNAL_FISH))
                .save(output);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, TideItems.BAIT, 4)
                .requires(TideTags.Items.BAIT_PLANTS)
                .requires(Items.ROTTEN_FLESH)
                .requires(seeds)
                .requires(Items.BONE_MEAL)
                .unlockedBy("has_bait_plants", has(TideTags.Items.BAIT_PLANTS))
                .unlockedBy("has_rotten_flesh", has(Items.ROTTEN_FLESH))
                .unlockedBy("has_seeds", has(seeds))
                .unlockedBy("has_bone_meal", has(Items.BONE_MEAL))
                .save(output);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, Items.AMETHYST_SHARD, 4)
                .requires(TideItems.CRYSTAL_SHRIMP)
                .unlockedBy("has_crystal_shrimp", has(TideItems.CRYSTAL_SHRIMP))
                .save(output, "tide:amethyst_from_shrimp");

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, TideItems.DEEP_AQUA_CRYSTAL, 1)
                .requires(TideItems.CRYSTALLINE_CARP)
                .unlockedBy("has_crystalline_carp", has(TideItems.CRYSTALLINE_CARP))
                .save(output, "tide:deep_aqua_crystal_from_carp");

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, Items.END_STONE, 8)
                .requires(TideItems.END_STONE_PERCH)
                .unlockedBy("has_end_stone_perch", has(TideItems.END_STONE_PERCH))
                .save(output, "tide:end_stone_from_perch");

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, Items.ENDER_PEARL, 2)
                .requires(TideItems.ENDERFIN)
                .unlockedBy("has_enderfin", has(TideItems.ENDERFIN))
                .save(output, "tide:ender_pearl_from_enderfin");

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, Items.ENDER_EYE, 1)
                .requires(TideItems.ENDERGAZER)
                .unlockedBy("has_endergazer", has(TideItems.ENDERGAZER))
                .save(output, "tide:eye_from_endergazer");

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, Items.BONE_MEAL, 2)
                .requires(TideItems.FISH_BONE)
                .unlockedBy("has_fish_bone", has(TideItems.FISH_BONE))
                .save(output, "tide:bone_meal_from_fish_bone");

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, Items.BRICK, 8)
                .requires(TideItems.HARDENED_CLAYFISH)
                .unlockedBy("has_hardened_clayfish", has(TideItems.HARDENED_CLAYFISH))
                .save(output, "tide:bricks_from_clayfish");

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, Items.CHORUS_FLOWER)
                .requires(TideItems.CHORUS_COD)
                .unlockedBy("has", has(TideItems.CHORUS_COD))
                .save(output, "tide:chorus_flower_from_cod");

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, Items.GLOW_INK_SAC, 2)
                .requires(TideItems.GLOWFISH)
                .unlockedBy("has", has(TideItems.GLOWFISH))
                .save(output, "tide:glow_ink_from_fish");

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, Items.RAW_GOLD, 2)
                .requires(TideItems.GILDED_MINNOW)
                .unlockedBy("has_gilded_minnow", has(TideItems.GILDED_MINNOW))
                .save(output, "tide:gold_from_minnow");

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, Items.RAW_IRON, 2)
                .requires(TideItems.IRON_TETRA)
                .unlockedBy("has_iron_tetra", has(TideItems.IRON_TETRA))
                .save(output, "tide:iron_from_tetra");

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, Items.LAPIS_LAZULI, 2)
                .requires(TideItems.LAPIS_LANTERNFISH)
                .unlockedBy("has_lapis_lanternfish", has(TideItems.LAPIS_LANTERNFISH))
                .save(output, "tide:lapis_from_lanternfish");

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, Items.PHANTOM_MEMBRANE, 1)
                .requires(TideItems.ELYTROUT)
                .unlockedBy("has_elytrout", has(TideItems.ELYTROUT))
                .save(output, "tide:membrane_from_elytrout");

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, Items.POPPED_CHORUS_FRUIT, 8)
                .requires(TideItems.PURPUR_PIKE)
                .unlockedBy("has_purpur_pike", has(TideItems.PURPUR_PIKE))
                .save(output, "tide:purpur_from_pike");

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, TideItems.JELLY_TORCH, 8)
                .requires(TideItems.LUMINESCENT_JELLYFISH)
                .unlockedBy("has_luminescent_jellyfish", has(TideItems.LUMINESCENT_JELLYFISH))
                .save(output, "tide:jelly_torch_from_jellyfish");

        createBobberRecipes(output);

        // -- Shaped --

        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, TideItems.ANGLER_WORKSHOP)
                .pattern("SS")
                .pattern("CC")
                .pattern("##")
                .define('S', strings)
                .define('C', copperIngots)
                .define('#', ItemTags.PLANKS)
                .unlockedBy("has_string", has(strings))
                .unlockedBy("has_copper_ingot", has(copperIngots))
                .unlockedBy("has_planks", has(ItemTags.PLANKS))
                .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, TideItems.DEEP_AQUA_ARROW, 8)
                .pattern("###")
                .pattern("#C#")
                .pattern("###")
                .define('#', Items.ARROW)
                .define('C', TideItems.DEEP_AQUA_CRYSTAL)
                .unlockedBy("has_aqua_crystal", has(TideItems.DEEP_AQUA_CRYSTAL))
                .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, TideItems.LUCKY_BAIT, 16)
                .pattern("###")
                .pattern("#R#")
                .pattern("###")
                .define('#', TideItems.BAIT)
                .define('R', Items.RABBIT_FOOT)
                .unlockedBy("has_rabbit_foot", has(Items.RABBIT_FOOT))
                .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, TideItems.MAGNETIC_BAIT, 4)
                .pattern("L#R")
                .pattern("I#I")
                .pattern(" P ")
                .define('L', conventionTag("gems/lapis"))
                .define('R', conventionTag("dusts/redstone"))
                .define('P', conventionTag("gems/prismarine"))
                .define('I', Items.IRON_NUGGET)
                .define('#', TideItems.BAIT)
                .unlockedBy("has_lapis", has(conventionTag("gems/lapis")))
                .unlockedBy("has_redstone", has(conventionTag("dusts/redstone")))
                .unlockedBy("has_prismarine", has(conventionTag("gems/prismarine")))
                .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, TideItems.STONE_FISHING_ROD)
                .pattern("  #")
                .pattern(" #S")
                .pattern("# S")
                .define('S', strings)
                .define('#', cobblestones)
                .unlockedBy("has_string", has(strings))
                .unlockedBy("has_cobble", has(cobblestones))
                .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, TideItems.FISHING_LINE)
                .pattern("S")
                .pattern("S")
                .pattern("S")
                .define('S', strings)
                .unlockedBy("has_string", has(strings))
                .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, TideItems.BRAIDED_LINE)
                .pattern("#")
                .pattern("L")
                .pattern("#")
                .define('L', TideItems.FISHING_LINE)
                .define('#', slimeBalls)
                .unlockedBy("has_string", has(strings))
                .unlockedBy("has_slimeball", has(slimeBalls))
                .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, TideItems.REINFORCED_LINE)
                .pattern("#")
                .pattern("L")
                .pattern("#")
                .define('L', TideItems.FISHING_LINE)
                .define('#', ironIngots)
                .unlockedBy("has_fishing_line", has(TideItems.FISHING_LINE))
                .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, TideItems.FORTUNE_LINE)
                .pattern("#")
                .pattern("L")
                .pattern("#")
                .define('L', TideItems.FISHING_LINE)
                .define('#', goldIngots)
                .unlockedBy("has_fishing_line", has(TideItems.FISHING_LINE))
                .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, TideItems.FISHING_HOOK)
                .pattern("#")
                .pattern("C")
                .pattern("#")
                .define('C', cobblestones)
                .define('#', ironNuggets)
                .unlockedBy("has_cobble", has(cobblestones))
                .unlockedBy("has_iron_nugget", has(ironNuggets))
                .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, TideItems.IRON_FISHING_HOOK)
                .pattern("  #")
                .pattern("I I")
                .pattern(" I ")
                .define('I', ironIngots)
                .define('#', ironNuggets)
                .unlockedBy("has_iron_ingot", has(ironIngots))
                .unlockedBy("has_iron_nugget", has(ironNuggets))
                .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, TideItems.LAVAPROOF_FISHING_HOOK)
                .pattern("  #")
                .pattern("O O")
                .pattern(" O ")
                .define('O', obsidians)
                .define('#', copperIngots)
                .unlockedBy("has_obsidian", has(obsidians))
                .unlockedBy("has_copper", has(copperIngots))
                .save(output);

        // -- Smithing --

        SmithingTransformRecipeBuilder.smithing(
                        Ingredient.of(strings),
                        Ingredient.of(Items.FISHING_ROD, TideItems.STONE_FISHING_ROD),
                        Ingredient.of(ironIngots),
                        RecipeCategory.TOOLS,
                        TideItems.IRON_FISHING_ROD)
                .unlocks("has_string", has(strings))
                .unlocks("has_stone_fishing_rod", has(TideItems.STONE_FISHING_ROD))
                .unlocks("has_iron_ingot", has(ironIngots))
                .save(output, "tide:iron_rod_smithing");

        SmithingTransformRecipeBuilder.smithing(
                        Ingredient.of(strings),
                        Ingredient.of(Items.FISHING_ROD, TideItems.STONE_FISHING_ROD,
                                TideItems.IRON_FISHING_ROD),
                        Ingredient.of(goldIngots),
                        RecipeCategory.TOOLS,
                        TideItems.GOLDEN_FISHING_ROD)
                .unlocks("has_string", has(strings))
                .unlocks("has_stone_fishing_rod", has(TideItems.STONE_FISHING_ROD))
                .unlocks("has_gold", has(goldIngots))
                .save(output, "tide:gold_rod_smithing");

        SmithingTransformRecipeBuilder.smithing(
                        Ingredient.of(strings),
                        Ingredient.of(Items.FISHING_ROD, TideItems.STONE_FISHING_ROD,
                                TideItems.IRON_FISHING_ROD, TideItems.GOLDEN_FISHING_ROD),
                        Ingredient.of(amethystGems),
                        RecipeCategory.TOOLS,
                        TideItems.CRYSTAL_FISHING_ROD)
                .unlocks("has_string", has(strings))
                .unlocks("has_iron_fishing_rod", has(TideItems.IRON_FISHING_ROD))
                .unlocks("has_amethyst", has(amethystGems))
                .save(output, "tide:crystal_rod_smithing");

        SmithingTransformRecipeBuilder.smithing(
                        Ingredient.of(strings),
                        Ingredient.of(Items.FISHING_ROD, TideItems.STONE_FISHING_ROD,
                                TideItems.IRON_FISHING_ROD, TideItems.GOLDEN_FISHING_ROD,
                                TideItems.CRYSTAL_FISHING_ROD),
                        Ingredient.of(diamondGems),
                        RecipeCategory.TOOLS,
                        TideItems.DIAMOND_FISHING_ROD)
                .unlocks("has_string", has(strings))
                .unlocks("has_iron_fishing_rod", has(TideItems.IRON_FISHING_ROD))
                .unlocks("has_diamond", has(diamondGems))
                .save(output, "tide:diamond_rod_smithing");

        SmithingTransformRecipeBuilder.smithing(
                        Ingredient.of(strings),
                        Ingredient.of(TideItems.DIAMOND_FISHING_ROD),
                        Ingredient.of(conventionTag("ingots/netherite")),
                        RecipeCategory.TOOLS,
                        TideItems.NETHERITE_FISHING_ROD)
                .unlocks("has_string", has(strings))
                .unlocks("has_diamond_fishing_rod", has(TideItems.DIAMOND_FISHING_ROD))
                .unlocks("has_netherite", has(conventionTag("ingots/netherite")))
                .save(output, "tide:netherite_rod_smithing");

        // -- Cooking --

        SimpleCookingRecipeBuilder.generic(Ingredient.of(TideTags.Items.COOKABLE_FISH),
                        RecipeCategory.FOOD, TideItems.COOKED_FISH,
                        0.35f, 600, RecipeSerializer.CAMPFIRE_COOKING_RECIPE)
                .unlockedBy("has_cookable_fish", has(TideTags.Items.COOKABLE_FISH))
                .save(output, "tide:cooked_fish_campfire_cooking");

        SimpleCookingRecipeBuilder.generic(Ingredient.of(TideTags.Items.COOKABLE_FISH),
                        RecipeCategory.FOOD, TideItems.COOKED_FISH,
                        0.1f, 200, RecipeSerializer.SMELTING_RECIPE)
                .unlockedBy("has_cookable_fish", has(TideTags.Items.COOKABLE_FISH))
                .save(output, "tide:cooked_fish_smelting");

        SimpleCookingRecipeBuilder.generic(Ingredient.of(TideTags.Items.COOKABLE_FISH),
                        RecipeCategory.FOOD, TideItems.COOKED_FISH,
                        0.1f, 100, RecipeSerializer.SMOKING_RECIPE)
                .unlockedBy("has_cookable_fish", has(TideTags.Items.COOKABLE_FISH))
                .save(output, "tide:cooked_fish_smoking");

        SimpleCookingRecipeBuilder.generic(Ingredient.of(TideItems.FISH_SLICE),
                        RecipeCategory.FOOD, TideItems.COOKED_FISH_SLICE,
                        0.35f, 600, RecipeSerializer.CAMPFIRE_COOKING_RECIPE)
                .unlockedBy("has_fish_slice", has(TideItems.FISH_SLICE))
                .save(output, "tide:cooked_fish_slice_campfire_cooking");

        SimpleCookingRecipeBuilder.generic(Ingredient.of(TideItems.FISH_SLICE),
                        RecipeCategory.FOOD, TideItems.COOKED_FISH_SLICE,
                        0.35f, 200, RecipeSerializer.SMELTING_RECIPE)
                .unlockedBy("has_fish_slice", has(TideItems.FISH_SLICE))
                .save(output, "tide:cooked_fish_slice_smelting");

        SimpleCookingRecipeBuilder.generic(Ingredient.of(TideItems.FISH_SLICE),
                        RecipeCategory.FOOD, TideItems.COOKED_FISH_SLICE,
                        0.35f, 100, RecipeSerializer.SMOKING_RECIPE)
                .unlockedBy("has_fish_slice", has(TideItems.FISH_SLICE))
                .save(output, "tide:cooked_fish_slice_smoking");

        SimpleCookingRecipeBuilder.generic(Ingredient.of(TideItems.CLAYFISH),
                        RecipeCategory.FOOD, TideItems.HARDENED_CLAYFISH,
                        1.0f, 150, RecipeSerializer.SMELTING_RECIPE)
                .unlockedBy("has_clayfish", has(TideItems.CLAYFISH))
                .save(output, "tide:clayfish_smelting");

        // -- Rod Upgrading (for JEI) --

        new RodUpgradingRecipeBuilder(Items.FISHING_ROD)
                .unlocks("impossible", impossible())
                .save(output, "tide:rod_upgrading/normal");

        new RodUpgradingRecipeBuilder(TideItems.STONE_FISHING_ROD)
                .unlocks("impossible", impossible())
                .save(output, "tide:rod_upgrading/stone");

        new RodUpgradingRecipeBuilder(TideItems.IRON_FISHING_ROD)
                .unlocks("impossible", impossible())
                .save(output, "tide:rod_upgrading/iron");

        new RodUpgradingRecipeBuilder(TideItems.GOLDEN_FISHING_ROD)
                .unlocks("impossible", impossible())
                .save(output, "tide:rod_upgrading/golden");

        new RodUpgradingRecipeBuilder(TideItems.CRYSTAL_FISHING_ROD)
                .unlocks("impossible", impossible())
                .save(output, "tide:rod_upgrading/crystal");

        new RodUpgradingRecipeBuilder(TideItems.DIAMOND_FISHING_ROD)
                .unlocks("impossible", impossible())
                .save(output, "tide:rod_upgrading/diamond");

        new RodUpgradingRecipeBuilder(TideItems.NETHERITE_FISHING_ROD)
                .unlocks("impossible", impossible())
                .save(output, "tide:rod_upgrading/netherite");
    }

    private void createBobberRecipes(Consumer<FinishedRecipe> output) {
        TagKey<Item> slimeballTag = conventionTag("slime_balls");
        BOBBERS.forEach(bobber -> {
            String dyeId = bobber.toString().split("_fishing_bobber")[0];

            ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, bobber)
                    .requires(slimeballTag)
                    .requires(ItemTags.PLANKS)
                    .requires(TagKey.create(Registries.ITEM, new ResourceLocation(
                            "c", "dyes/" + dyeId)))
                    .unlockedBy("has_slimeball", has(slimeballTag))
                    .save(output, "tide:bobbers/" + dyeId);
        });
    }

    private static CriterionTriggerInstance impossible() {
        return new ImpossibleTrigger.TriggerInstance();
    }

    public TagKey<Item> conventionTag(String name) {
        return TagKey.create(Registries.ITEM, new ResourceLocation("c", name));
    }
}