package com.li64.tide.datagen.providers.recipes;

import com.li64.tide.data.TideTags;
import com.li64.tide.registries.TideItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalItemTags;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.ImpossibleTrigger;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.*;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.concurrent.CompletableFuture;

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

    public TideRecipeProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected RecipeProvider createRecipeProvider(HolderLookup.Provider provider, RecipeOutput recipeOutput) {
        return new TideCraftingRecipesProvider(provider, recipeOutput);
    }

    @Override
    public @NotNull String getName() {
        return "Tide Recipes";
    }

    public static class TideCraftingRecipesProvider extends RecipeProvider {
        private final RecipeOutput output;

        public TideCraftingRecipesProvider(HolderLookup.Provider registries, RecipeOutput output) {
            super(registries, output);
            this.output = output;
        }

        @Override
        public void buildRecipes() {
            // -- Shapeless --

            TagKey<Item> seeds = neoForgeConventionTag("seeds");
            TagKey<Item> slimeBalls = neoForgeConventionTag("slime_balls");
            TagKey<Item> obsidians = neoForgeConventionTag("obsidians");
            TagKey<Item> ironNuggets = neoForgeConventionTag("nuggets/iron");

            shapeless(RecipeCategory.BUILDING_BLOCKS, Items.OBSIDIAN, 1)
                    .requires(TideItems.OBSIDIAN_FRAGMENT)
                    .requires(TideItems.OBSIDIAN_FRAGMENT)
                    .requires(TideItems.OBSIDIAN_FRAGMENT)
                    .requires(TideItems.OBSIDIAN_FRAGMENT)
                    .unlockedBy("has_obsidian_fragment", has(TideItems.OBSIDIAN_FRAGMENT))
                    .save(output, "obsidian_from_fragments");

            shapeless(RecipeCategory.MISC, TideItems.FISHING_JOURNAL, 1)
                    .requires(TideTags.Items.JOURNAL_FISH)
                    .requires(Items.BOOK)
                    .unlockedBy("has_book", has(Items.BOOK))
                    .unlockedBy("has_fish", has(TideTags.Items.JOURNAL_FISH))
                    .save(output);

            shapeless(RecipeCategory.MISC, TideItems.BAIT, 4)
                    .requires(TideTags.Items.BAIT_PLANTS)
                    .requires(Items.ROTTEN_FLESH)
                    .requires(seeds)
                    .requires(Items.BONE_MEAL)
                    .unlockedBy("has_bait_plants", has(TideTags.Items.BAIT_PLANTS))
                    .unlockedBy("has_rotten_flesh", has(Items.ROTTEN_FLESH))
                    .unlockedBy("has_seeds", has(seeds))
                    .unlockedBy("has_bone_meal", has(Items.BONE_MEAL))
                    .save(output);

            shapeless(RecipeCategory.MISC, Items.AMETHYST_SHARD, 4)
                    .requires(TideItems.CRYSTAL_SHRIMP)
                    .unlockedBy("has_crystal_shrimp", has(TideItems.CRYSTAL_SHRIMP))
                    .save(output, "tide:amethyst_from_shrimp");

            shapeless(RecipeCategory.MISC, TideItems.DEEP_AQUA_CRYSTAL, 1)
                    .requires(TideItems.CRYSTALLINE_CARP)
                    .unlockedBy("has_crystalline_carp", has(TideItems.CRYSTALLINE_CARP))
                    .save(output, "tide:deep_aqua_crystal_from_carp");

            shapeless(RecipeCategory.MISC, Items.END_STONE, 8)
                    .requires(TideItems.END_STONE_PERCH)
                    .unlockedBy("has_end_stone_perch", has(TideItems.END_STONE_PERCH))
                    .save(output, "tide:end_stone_from_perch");

            shapeless(RecipeCategory.MISC, Items.ENDER_PEARL, 2)
                    .requires(TideItems.ENDERFIN)
                    .unlockedBy("has_enderfin", has(TideItems.ENDERFIN))
                    .save(output, "tide:ender_pearl_from_enderfin");

            shapeless(RecipeCategory.MISC, Items.ENDER_EYE, 1)
                    .requires(TideItems.ENDERGAZER)
                    .unlockedBy("has_endergazer", has(TideItems.ENDERGAZER))
                    .save(output, "tide:eye_from_endergazer");

            shapeless(RecipeCategory.MISC, Items.BONE_MEAL, 2)
                    .requires(TideItems.FISH_BONE)
                    .unlockedBy("has_fish_bone", has(TideItems.FISH_BONE))
                    .save(output, "tide:bone_meal_from_fish_bone");

            shapeless(RecipeCategory.MISC, Items.BRICK, 8)
                    .requires(TideItems.HARDENED_CLAYFISH)
                    .unlockedBy("has_hardened_clayfish", has(TideItems.HARDENED_CLAYFISH))
                    .save(output, "tide:bricks_from_clayfish");

            shapeless(RecipeCategory.MISC, Items.CHORUS_FLOWER)
                    .requires(TideItems.CHORUS_COD)
                    .unlockedBy("has", has(TideItems.CHORUS_COD))
                    .save(output, "tide:chorus_flower_from_cod");

            shapeless(RecipeCategory.MISC, Items.GLOW_INK_SAC, 2)
                    .requires(TideItems.GLOWFISH)
                    .unlockedBy("has", has(TideItems.GLOWFISH))
                    .save(output, "tide:glow_ink_from_fish");

            shapeless(RecipeCategory.MISC, Items.RAW_GOLD, 2)
                    .requires(TideItems.GILDED_MINNOW)
                    .unlockedBy("has_gilded_minnow", has(TideItems.GILDED_MINNOW))
                    .save(output, "tide:gold_from_minnow");

            shapeless(RecipeCategory.MISC, Items.RAW_IRON, 2)
                    .requires(TideItems.IRON_TETRA)
                    .unlockedBy("has_iron_tetra", has(TideItems.IRON_TETRA))
                    .save(output, "tide:iron_from_tetra");

            shapeless(RecipeCategory.MISC, Items.LAPIS_LAZULI, 2)
                    .requires(TideItems.LAPIS_LANTERNFISH)
                    .unlockedBy("has_lapis_lanternfish", has(TideItems.LAPIS_LANTERNFISH))
                    .save(output, "tide:lapis_from_lanternfish");

            shapeless(RecipeCategory.MISC, Items.PHANTOM_MEMBRANE, 1)
                    .requires(TideItems.ELYTROUT)
                    .unlockedBy("has_elytrout", has(TideItems.ELYTROUT))
                    .save(output, "tide:membrane_from_elytrout");

            shapeless(RecipeCategory.MISC, Items.POPPED_CHORUS_FRUIT, 8)
                    .requires(TideItems.PURPUR_PIKE)
                    .unlockedBy("has_purpur_pike", has(TideItems.PURPUR_PIKE))
                    .save(output, "tide:purpur_from_pike");

            shapeless(RecipeCategory.MISC, TideItems.JELLY_TORCH, 8)
                    .requires(TideItems.LUMINESCENT_JELLYFISH)
                    .unlockedBy("has_luminescent_jellyfish", has(TideItems.LUMINESCENT_JELLYFISH))
                    .save(output, "tide:jelly_torch_from_jellyfish");

            createColoredBobberRecipes(output);
            createSimpleBobberRecipe(output, TideItems.APPLE_FISHING_BOBBER, Items.APPLE);
            createSimpleBobberRecipe(output, TideItems.GOLDEN_APPLE_FISHING_BOBBER, Items.GOLDEN_APPLE);
            createSimpleBobberRecipe(output, TideItems.ENCHANTED_GOLDEN_APPLE_FISHING_BOBBER, Items.ENCHANTED_GOLDEN_APPLE);
            createSimpleBobberRecipe(output, TideItems.IRON_FISHING_BOBBER, Items.IRON_INGOT);
            createSimpleBobberRecipe(output, TideItems.GOLDEN_FISHING_BOBBER, Items.GOLD_INGOT);
            createSimpleBobberRecipe(output, TideItems.DIAMOND_FISHING_BOBBER, Items.DIAMOND);
            createSimpleBobberRecipe(output, TideItems.NETHERITE_FISHING_BOBBER, Items.NETHERITE_INGOT);
            createSimpleBobberRecipe(output, TideItems.AMETHYST_FISHING_BOBBER, Items.AMETHYST_SHARD);
            createSimpleBobberRecipe(output, TideItems.ECHO_FISHING_BOBBER, Items.ECHO_SHARD);
            createSimpleBobberRecipe(output, TideItems.CHORUS_FISHING_BOBBER, Items.CHORUS_FRUIT);
            createSimpleBobberRecipe(output, TideItems.FEATHER_FISHING_BOBBER, Items.FEATHER);
            createSimpleBobberRecipe(output, TideItems.LICHEN_FISHING_BOBBER, Items.GLOW_LICHEN);
            createSimpleBobberRecipe(output, TideItems.NAUTILUS_FISHING_BOBBER, Items.NAUTILUS_SHELL);
            createSimpleBobberRecipe(output, TideItems.PEARL_FISHING_BOBBER, Items.ENDER_PEARL);
            createSimpleBobberRecipe(output, TideItems.HEART_FISHING_BOBBER, Items.HEART_OF_THE_SEA);
            createSimpleBobberRecipe(output, TideItems.GRASSY_FISHING_BOBBER, Items.GRASS_BLOCK);

            // -- Shaped --

            shaped(RecipeCategory.DECORATIONS, TideItems.ANGLER_WORKSHOP)
                    .pattern("SS")
                    .pattern("CC")
                    .pattern("##")
                    .define('S', ConventionalItemTags.STRINGS)
                    .define('C', ConventionalItemTags.COPPER_INGOTS)
                    .define('#', ItemTags.PLANKS)
                    .unlockedBy("has_string", has(ConventionalItemTags.STRINGS))
                    .unlockedBy("has_copper_ingot", has(ConventionalItemTags.COPPER_INGOTS))
                    .unlockedBy("has_planks", has(ItemTags.PLANKS))
                    .save(output);

            shaped(RecipeCategory.MISC, TideItems.SURFACE_LOOT_CRATE)
                    .pattern("SSS")
                    .pattern("S S")
                    .pattern("SSS")
                    .define('S', ItemTags.WOODEN_SLABS)
                    .unlockedBy("has_wooden_slab", has(ItemTags.WOODEN_SLABS))
                    .save(output);

            shaped(RecipeCategory.COMBAT, TideItems.DEEP_AQUA_ARROW, 8)
                    .pattern("###")
                    .pattern("#C#")
                    .pattern("###")
                    .define('#', Items.ARROW)
                    .define('C', TideItems.DEEP_AQUA_CRYSTAL)
                    .unlockedBy("has_aqua_crystal", has(TideItems.DEEP_AQUA_CRYSTAL))
                    .save(output);

            shaped(RecipeCategory.MISC, TideItems.LUCKY_BAIT, 16)
                    .pattern("###")
                    .pattern("#R#")
                    .pattern("###")
                    .define('#', TideItems.BAIT)
                    .define('R', Items.RABBIT_FOOT)
                    .unlockedBy("has_rabbit_foot", has(Items.RABBIT_FOOT))
                    .save(output);

            shaped(RecipeCategory.MISC, TideItems.MAGNETIC_BAIT, 4)
                    .pattern("L#R")
                    .pattern("I#I")
                    .pattern(" P ")
                    .define('L', ConventionalItemTags.LAPIS_GEMS)
                    .define('R', ConventionalItemTags.REDSTONE_DUSTS)
                    .define('P', ConventionalItemTags.PRISMARINE_GEMS)
                    .define('I', Items.IRON_NUGGET)
                    .define('#', TideItems.BAIT)
                    .unlockedBy("has_lapis", has(ConventionalItemTags.LAPIS_GEMS))
                    .unlockedBy("has_redstone", has(ConventionalItemTags.REDSTONE_DUSTS))
                    .unlockedBy("has_prismarine", has(ConventionalItemTags.PRISMARINE_GEMS))
                    .save(output);

            shaped(RecipeCategory.TOOLS, TideItems.STONE_FISHING_ROD)
                    .pattern("  #")
                    .pattern(" #S")
                    .pattern("# S")
                    .define('S', ConventionalItemTags.STRINGS)
                    .define('#', ConventionalItemTags.COBBLESTONES)
                    .unlockedBy("has_string", has(ConventionalItemTags.STRINGS))
                    .unlockedBy("has_cobble", has(ConventionalItemTags.COBBLESTONES))
                    .save(output);

            shaped(RecipeCategory.TOOLS, TideItems.FISHING_LINE)
                    .pattern("S")
                    .pattern("S")
                    .pattern("S")
                    .define('S', ConventionalItemTags.STRINGS)
                    .unlockedBy("has_string", has(ConventionalItemTags.STRINGS))
                    .save(output);

            shaped(RecipeCategory.TOOLS, TideItems.BRAIDED_LINE)
                    .pattern("#")
                    .pattern("L")
                    .pattern("#")
                    .define('L', TideItems.FISHING_LINE)
                    .define('#', slimeBalls)
                    .unlockedBy("has_string", has(ConventionalItemTags.STRINGS))
                    .unlockedBy("has_slimeball", has(slimeBalls))
                    .save(output);

            shaped(RecipeCategory.TOOLS, TideItems.REINFORCED_LINE)
                    .pattern("#")
                    .pattern("L")
                    .pattern("#")
                    .define('L', TideItems.FISHING_LINE)
                    .define('#', ConventionalItemTags.IRON_INGOTS)
                    .unlockedBy("has_fishing_line", has(TideItems.FISHING_LINE))
                    .save(output);

            shaped(RecipeCategory.TOOLS, TideItems.FORTUNE_LINE)
                    .pattern("#")
                    .pattern("L")
                    .pattern("#")
                    .define('L', TideItems.FISHING_LINE)
                    .define('#', ConventionalItemTags.GOLD_INGOTS)
                    .unlockedBy("has_fishing_line", has(TideItems.FISHING_LINE))
                    .save(output);

            shaped(RecipeCategory.TOOLS, TideItems.FISHING_HOOK)
                    .pattern("#")
                    .pattern("C")
                    .pattern("#")
                    .define('C', ConventionalItemTags.COBBLESTONES)
                    .define('#', Items.IRON_NUGGET)
                    .unlockedBy("has_cobble", has(ConventionalItemTags.COBBLESTONES))
                    .unlockedBy("has_iron_nugget", has(Items.IRON_NUGGET))
                    .save(output);

            shaped(RecipeCategory.TOOLS, TideItems.IRON_FISHING_HOOK)
                    .pattern("  #")
                    .pattern("I I")
                    .pattern(" I ")
                    .define('I', ConventionalItemTags.IRON_INGOTS)
                    .define('#', ironNuggets)
                    .unlockedBy("has_iron_ingot", has(ConventionalItemTags.IRON_INGOTS))
                    .unlockedBy("has_iron_nugget", has(Items.IRON_NUGGET))
                    .save(output);

            shaped(RecipeCategory.TOOLS, TideItems.LAVAPROOF_FISHING_HOOK)
                    .pattern("  #")
                    .pattern("O O")
                    .pattern(" O ")
                    .define('O', obsidians)
                    .define('#', ConventionalItemTags.COPPER_INGOTS)
                    .unlockedBy("has_obsidian", has(obsidians))
                    .unlockedBy("has_copper", has(ConventionalItemTags.COPPER_INGOTS))
                    .save(output);

            // -- Smithing --

            SmithingTransformRecipeBuilder.smithing(
                            tag(ConventionalItemTags.STRINGS),
                            Ingredient.of(Items.FISHING_ROD, TideItems.STONE_FISHING_ROD),
                            tag(ConventionalItemTags.IRON_INGOTS),
                            RecipeCategory.TOOLS,
                            TideItems.IRON_FISHING_ROD)
                    .unlocks("has_string", has(ConventionalItemTags.STRINGS))
                    .unlocks("has_stone_fishing_rod", has(TideItems.STONE_FISHING_ROD))
                    .unlocks("has_iron_ingot", has(ConventionalItemTags.IRON_INGOTS))
                    .save(output, "tide:iron_rod_smithing");

            SmithingTransformRecipeBuilder.smithing(
                            tag(ConventionalItemTags.STRINGS),
                            Ingredient.of(Items.FISHING_ROD, TideItems.STONE_FISHING_ROD,
                                    TideItems.IRON_FISHING_ROD),
                            tag(ConventionalItemTags.GOLD_INGOTS),
                            RecipeCategory.TOOLS,
                            TideItems.GOLDEN_FISHING_ROD)
                    .unlocks("has_string", has(ConventionalItemTags.STRINGS))
                    .unlocks("has_stone_fishing_rod", has(TideItems.STONE_FISHING_ROD))
                    .unlocks("has_gold", has(ConventionalItemTags.GOLD_INGOTS))
                    .save(output, "tide:gold_rod_smithing");

            SmithingTransformRecipeBuilder.smithing(
                            tag(ConventionalItemTags.STRINGS),
                            Ingredient.of(Items.FISHING_ROD, TideItems.STONE_FISHING_ROD,
                                    TideItems.IRON_FISHING_ROD, TideItems.GOLDEN_FISHING_ROD),
                            tag(ConventionalItemTags.AMETHYST_GEMS),
                            RecipeCategory.TOOLS,
                            TideItems.CRYSTAL_FISHING_ROD)
                    .unlocks("has_string", has(ConventionalItemTags.STRINGS))
                    .unlocks("has_iron_fishing_rod", has(TideItems.IRON_FISHING_ROD))
                    .unlocks("has_amethyst", has(ConventionalItemTags.AMETHYST_GEMS))
                    .save(output, "tide:crystal_rod_smithing");

            SmithingTransformRecipeBuilder.smithing(
                            tag(ConventionalItemTags.STRINGS),
                            Ingredient.of(Items.FISHING_ROD, TideItems.STONE_FISHING_ROD,
                                    TideItems.IRON_FISHING_ROD, TideItems.GOLDEN_FISHING_ROD,
                                    TideItems.CRYSTAL_FISHING_ROD),
                            tag(ConventionalItemTags.DIAMOND_GEMS),
                            RecipeCategory.TOOLS,
                            TideItems.DIAMOND_FISHING_ROD)
                    .unlocks("has_string", has(ConventionalItemTags.STRINGS))
                    .unlocks("has_iron_fishing_rod", has(TideItems.IRON_FISHING_ROD))
                    .unlocks("has_diamond", has(ConventionalItemTags.DIAMOND_GEMS))
                    .save(output, "tide:diamond_rod_smithing");

            SmithingTransformRecipeBuilder.smithing(
                            tag(ConventionalItemTags.STRINGS),
                            Ingredient.of(TideItems.DIAMOND_FISHING_ROD),
                            tag(ConventionalItemTags.NETHERITE_INGOTS),
                            RecipeCategory.TOOLS,
                            TideItems.NETHERITE_FISHING_ROD)
                    .unlocks("has_string", has(ConventionalItemTags.STRINGS))
                    .unlocks("has_diamond_fishing_rod", has(TideItems.DIAMOND_FISHING_ROD))
                    .unlocks("has_netherite", has(ConventionalItemTags.NETHERITE_INGOTS))
                    .save(output, "tide:netherite_rod_smithing");

            // -- Cooking --

            SimpleCookingRecipeBuilder.generic(tag(TideTags.Items.COOKABLE_FISH),
                            RecipeCategory.FOOD, TideItems.COOKED_FISH,
                            0.35f, 600, RecipeSerializer.CAMPFIRE_COOKING_RECIPE,
                            CampfireCookingRecipe::new)
                    .unlockedBy("has_cookable_fish", has(TideTags.Items.COOKABLE_FISH))
                    .save(output, "tide:cooked_fish_campfire_cooking");

            SimpleCookingRecipeBuilder.generic(tag(TideTags.Items.COOKABLE_FISH),
                            RecipeCategory.FOOD, TideItems.COOKED_FISH,
                            0.1f, 200, RecipeSerializer.SMELTING_RECIPE,
                            SmeltingRecipe::new)
                    .unlockedBy("has_cookable_fish", has(TideTags.Items.COOKABLE_FISH))
                    .save(output, "tide:cooked_fish_smelting");

            SimpleCookingRecipeBuilder.generic(tag(TideTags.Items.COOKABLE_FISH),
                            RecipeCategory.FOOD, TideItems.COOKED_FISH,
                            0.1f, 100, RecipeSerializer.SMOKING_RECIPE,
                            SmokingRecipe::new)
                    .unlockedBy("has_cookable_fish", has(TideTags.Items.COOKABLE_FISH))
                    .save(output, "tide:cooked_fish_smoking");

            SimpleCookingRecipeBuilder.generic(Ingredient.of(TideItems.FISH_SLICE),
                            RecipeCategory.FOOD, TideItems.COOKED_FISH_SLICE,
                            0.35f, 600, RecipeSerializer.CAMPFIRE_COOKING_RECIPE,
                            CampfireCookingRecipe::new)
                    .unlockedBy("has_fish_slice", has(TideItems.FISH_SLICE))
                    .save(output, "tide:cooked_fish_slice_campfire_cooking");

            SimpleCookingRecipeBuilder.generic(Ingredient.of(TideItems.FISH_SLICE),
                            RecipeCategory.FOOD, TideItems.COOKED_FISH_SLICE,
                            0.35f, 200, RecipeSerializer.SMELTING_RECIPE,
                            SmeltingRecipe::new)
                    .unlockedBy("has_fish_slice", has(TideItems.FISH_SLICE))
                    .save(output, "tide:cooked_fish_slice_smelting");

            SimpleCookingRecipeBuilder.generic(Ingredient.of(TideItems.FISH_SLICE),
                            RecipeCategory.FOOD, TideItems.COOKED_FISH_SLICE,
                            0.35f, 100, RecipeSerializer.SMOKING_RECIPE,
                            SmokingRecipe::new)
                    .unlockedBy("has_fish_slice", has(TideItems.FISH_SLICE))
                    .save(output, "tide:cooked_fish_slice_smoking");

            SimpleCookingRecipeBuilder.generic(Ingredient.of(TideItems.CLAYFISH),
                            RecipeCategory.FOOD, TideItems.HARDENED_CLAYFISH,
                            1.0f, 150, RecipeSerializer.SMELTING_RECIPE,
                            SmeltingRecipe::new)
                    .unlockedBy("has_clayfish", has(TideItems.CLAYFISH))
                    .save(output, "tide:clayfish_smelting");

            // -- Rod Upgrading (for JEI) --

            new RodUpgradingRecipeBuilder(Items.FISHING_ROD)
                    .unlockedBy("impossible", impossible())
                    .save(output, "tide:rod_upgrading/normal");

            new RodUpgradingRecipeBuilder(TideItems.STONE_FISHING_ROD)
                    .unlockedBy("impossible", impossible())
                    .save(output, "tide:rod_upgrading/stone");

            new RodUpgradingRecipeBuilder(TideItems.IRON_FISHING_ROD)
                    .unlockedBy("impossible", impossible())
                    .save(output, "tide:rod_upgrading/iron");

            new RodUpgradingRecipeBuilder(TideItems.GOLDEN_FISHING_ROD)
                    .unlockedBy("impossible", impossible())
                    .save(output, "tide:rod_upgrading/golden");

            new RodUpgradingRecipeBuilder(TideItems.CRYSTAL_FISHING_ROD)
                    .unlockedBy("impossible", impossible())
                    .save(output, "tide:rod_upgrading/crystal");

            new RodUpgradingRecipeBuilder(TideItems.DIAMOND_FISHING_ROD)
                    .unlockedBy("impossible", impossible())
                    .save(output, "tide:rod_upgrading/diamond");

            new RodUpgradingRecipeBuilder(TideItems.NETHERITE_FISHING_ROD)
                    .unlockedBy("impossible", impossible())
                    .save(output, "tide:rod_upgrading/netherite");
        }

        private void createSimpleBobberRecipe(RecipeOutput output, Item bobber, Item addition) {
            TagKey<Item> slimeballTag = neoForgeConventionTag("slime_balls");
            String identifier = BuiltInRegistries.ITEM.getKey(bobber).getPath()
                    .replace("_fishing_bobber", "");
            String additionPath = BuiltInRegistries.ITEM.getKey(addition).getPath();
            shapeless(RecipeCategory.MISC, bobber)
                    .requires(slimeballTag)
                    .requires(ItemTags.PLANKS)
                    .requires(addition)
                    .unlockedBy("has_slimeball", has(slimeballTag))
                    .unlockedBy("has_" + additionPath, has(addition))
                    .save(output, "tide:bobbers/" + identifier);
        }

        private void createColoredBobberRecipes(RecipeOutput output) {
            TagKey<Item> slimeballTag = neoForgeConventionTag("slime_balls");
            BOBBERS.forEach(bobber -> {
                String dyeId = BuiltInRegistries.ITEM.getKey(bobber).getPath()
                        .replace("_fishing_bobber", "");
                TagKey<Item> dyeTag = TagKey.create(Registries.ITEM, ResourceLocation
                        .fromNamespaceAndPath("c", "dyes/" + dyeId));

                shapeless(RecipeCategory.MISC, bobber)
                        .requires(slimeballTag)
                        .requires(ItemTags.PLANKS)
                        .requires(dyeTag)
                        .unlockedBy("has_slimeball", has(slimeballTag))
                        .unlockedBy("has_" + dyeId + "_dye", has(dyeTag))
                        .save(output, "tide:bobbers/" + dyeId);
            });
        }

        private static Criterion<ImpossibleTrigger.TriggerInstance> impossible() {
            return CriteriaTriggers.IMPOSSIBLE.createCriterion(new ImpossibleTrigger.TriggerInstance());
        }

        public TagKey<Item> neoForgeConventionTag(String name) {
            return TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("c", name));
        }
    }
}