package com.li64.tide.datagen.providers.recipes;

import com.li64.tide.data.TideTags;
import com.li64.tide.data.rods.BobberModifier;
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

import java.util.Arrays;
import java.util.function.Consumer;

public class TideRecipeProvider extends FabricRecipeProvider {
    public TideRecipeProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void buildRecipes(Consumer<FinishedRecipe> output) {
        // -- Shapeless --

        TagKey<Item> carrot = conventionTag("crops/carrot");
        TagKey<Item> potato = conventionTag("crops/potato");
        TagKey<Item> seeds = conventionTag("seeds");
        TagKey<Item> mushrooms = conventionTag("mushrooms");
        TagKey<Item> slimeBalls = conventionTag("slime_balls");
        TagKey<Item> obsidians = conventionTag("obsidians");
        TagKey<Item> ironNuggets = conventionTag("nuggets/iron");
        TagKey<Item> goldIngots = conventionTag("ingots/gold");
        TagKey<Item> ironIngots = conventionTag("ingots/iron");
        TagKey<Item> strings = conventionTag("strings");
        TagKey<Item> cobblestones = conventionTag("cobblestones");

        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, TideItems.FISH_STEW, 1)
                .requires(carrot)
                .requires(carrot)
                .requires(potato)
                .requires(potato)
                .requires(TideItems.COOKED_FISH)
                .requires(TideItems.COOKED_FISH)
                .requires(Items.BOWL)
                .unlockedBy("impossible", impossible())
                .save(output);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, Items.OBSIDIAN, 1)
                .requires(TideItems.OBSIDIAN_FRAGMENT)
                .requires(TideItems.OBSIDIAN_FRAGMENT)
                .requires(TideItems.OBSIDIAN_FRAGMENT)
                .requires(TideItems.OBSIDIAN_FRAGMENT)
                .unlockedBy("impossible", impossible())
                .save(output, "obsidian_from_fragments");

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, TideItems.FISHING_JOURNAL, 1)
                .requires(TideTags.Items.JOURNAL_FISH)
                .requires(Items.BOOK)
                .unlockedBy("impossible", impossible())
                .save(output);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, TideItems.BAIT, 4)
                .requires(TideTags.Items.BAIT_PLANTS)
                .requires(Items.ROTTEN_FLESH)
                .requires(seeds)
                .requires(Items.BONE_MEAL)
                .unlockedBy("impossible", impossible())
                .save(output);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, Items.AMETHYST_SHARD, 4)
                .requires(TideItems.CRYSTAL_SHRIMP)
                .unlockedBy("impossible", impossible())
                .save(output, "tide:amethyst_from_shrimp");

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, TideItems.DEEP_AQUA_CRYSTAL, 1)
                .requires(TideItems.CRYSTALLINE_CARP)
                .unlockedBy("impossible", impossible())
                .save(output, "tide:deep_aqua_crystal_from_carp");

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, Items.END_STONE, 8)
                .requires(TideItems.ENDSTONE_PERCH)
                .unlockedBy("impossible", impossible())
                .save(output, "tide:end_stone_from_perch");

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, Items.ENDER_PEARL, 2)
                .requires(TideItems.ENDERFIN)
                .unlockedBy("impossible", impossible())
                .save(output, "tide:ender_pearl_from_enderfin");

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, Items.ENDER_EYE, 1)
                .requires(TideItems.ENDERGAZER)
                .unlockedBy("impossible", impossible())
                .save(output, "tide:eye_from_endergazer");

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, Items.BONE_MEAL, 2)
                .requires(TideItems.FISH_BONE)
                .unlockedBy("impossible", impossible())
                .save(output, "tide:bone_meal_from_fish_bone");

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, Items.BRICK, 8)
                .requires(TideItems.HARDENED_CLAYFISH)
                .unlockedBy("impossible", impossible())
                .save(output, "tide:bricks_from_clayfish");

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, Items.CHORUS_FLOWER)
                .requires(TideItems.CHORUS_COD)
                .unlockedBy("impossible", impossible())
                .save(output, "tide:chorus_flower_from_cod");

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, Items.GLOW_INK_SAC, 2)
                .requires(TideItems.GLOWFISH)
                .unlockedBy("impossible", impossible())
                .save(output, "tide:glow_ink_from_fish");

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, Items.RAW_GOLD, 2)
                .requires(TideItems.GILDED_MINNOW)
                .unlockedBy("impossible", impossible())
                .save(output, "tide:gold_from_minnow");

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, Items.RAW_IRON, 2)
                .requires(TideItems.IRON_TETRA)
                .unlockedBy("impossible", impossible())
                .save(output, "tide:iron_from_tetra");

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, Items.LAPIS_LAZULI, 2)
                .requires(TideItems.LAPIS_LANTERNFISH)
                .unlockedBy("impossible", impossible())
                .save(output, "tide:lapis_from_lanternfish");

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, Items.PHANTOM_MEMBRANE, 1)
                .requires(TideItems.ELYTROUT)
                .unlockedBy("impossible", impossible())
                .save(output, "tide:membrane_from_elytrout");

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, Items.POPPED_CHORUS_FRUIT, 8)
                .requires(TideItems.PURPUR_PIKE)
                .unlockedBy("impossible", impossible())
                .save(output, "tide:purpur_from_pike");

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, TideItems.JELLY_TORCH, 8)
                .requires(TideItems.LUMINESCENT_JELLYFISH)
                .unlockedBy("impossible", impossible())
                .save(output);

        createBobberRecipes(output);

        // -- Shaped --

        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, TideItems.ANGLER_WORKSHOP)
                .pattern("#S#")
                .pattern("ICI")
                .pattern("#I#")
                .define('C', conventionTag("player_workstations/crafting_tables"))
                .define('S', strings)
                .define('I', ironNuggets)
                .define('#', ItemTags.PLANKS)
                .unlockedBy("impossible", impossible())
                .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, TideItems.DEEP_AQUA_ARROW, 8)
                .pattern("###")
                .pattern("#C#")
                .pattern("###")
                .define('#', Items.ARROW)
                .define('C', TideItems.DEEP_AQUA_CRYSTAL)
                .unlockedBy("impossible", impossible())
                .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, TideItems.LUCKY_BAIT, 16)
                .pattern("###")
                .pattern("#R#")
                .pattern("###")
                .define('#', TideItems.BAIT)
                .define('R', Items.RABBIT_FOOT)
                .unlockedBy("impossible", impossible())
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
                .unlockedBy("impossible", impossible())
                .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, TideItems.STONE_FISHING_ROD)
                .pattern("  #")
                .pattern(" #S")
                .pattern("# S")
                .define('S', strings)
                .define('#', cobblestones)
                .unlockedBy("impossible", impossible())
                .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, TideItems.FISHING_LINE)
                .pattern("S")
                .pattern("S")
                .pattern("S")
                .define('S', strings)
                .unlockedBy("impossible", impossible())
                .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, TideItems.BRAIDED_LINE)
                .pattern("#")
                .pattern("L")
                .pattern("#")
                .define('L', TideItems.FISHING_LINE)
                .define('#', slimeBalls)
                .unlockedBy("impossible", impossible())
                .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, TideItems.REINFORCED_LINE)
                .pattern("#")
                .pattern("L")
                .pattern("#")
                .define('L', TideItems.FISHING_LINE)
                .define('#', ironIngots)
                .unlockedBy("impossible", impossible())
                .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, TideItems.FORTUNE_LINE)
                .pattern("#")
                .pattern("L")
                .pattern("#")
                .define('L', TideItems.FISHING_LINE)
                .define('#', goldIngots)
                .unlockedBy("impossible", impossible())
                .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, TideItems.FISHING_HOOK)
                .pattern("#")
                .pattern("C")
                .pattern("#")
                .define('C', cobblestones)
                .define('#', ironNuggets)
                .unlockedBy("impossible", impossible())
                .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, TideItems.IRON_FISHING_HOOK)
                .pattern("  #")
                .pattern("I I")
                .pattern(" I ")
                .define('I', ironIngots)
                .define('#', ironNuggets)
                .unlockedBy("impossible", impossible())
                .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, TideItems.LAVAPROOF_FISHING_HOOK)
                .pattern("  #")
                .pattern("O O")
                .pattern(" O ")
                .define('O', obsidians)
                .define('#', conventionTag("ingots/copper"))
                .unlockedBy("impossible", impossible())
                .save(output);

        // -- Smithing --

        SmithingTransformRecipeBuilder.smithing(
                        Ingredient.of(strings),
                        Ingredient.of(Items.FISHING_ROD, TideItems.STONE_FISHING_ROD),
                        Ingredient.of(ironIngots),
                        RecipeCategory.TOOLS,
                        TideItems.IRON_FISHING_ROD)
                .unlocks("impossible", impossible())
                .save(output, "tide:iron_rod_smithing");

        SmithingTransformRecipeBuilder.smithing(
                        Ingredient.of(strings),
                        Ingredient.of(Items.FISHING_ROD, TideItems.STONE_FISHING_ROD,
                                TideItems.IRON_FISHING_ROD),
                        Ingredient.of(goldIngots),
                        RecipeCategory.TOOLS,
                        TideItems.GOLDEN_FISHING_ROD)
                .unlocks("impossible", impossible())
                .save(output, "tide:gold_rod_smithing");

        SmithingTransformRecipeBuilder.smithing(
                        Ingredient.of(strings),
                        Ingredient.of(Items.FISHING_ROD, TideItems.STONE_FISHING_ROD,
                                TideItems.IRON_FISHING_ROD, TideItems.GOLDEN_FISHING_ROD),
                        Ingredient.of(conventionTag("gems/amethyst")),
                        RecipeCategory.TOOLS,
                        TideItems.CRYSTAL_FISHING_ROD)
                .unlocks("impossible", impossible())
                .save(output, "tide:crystal_rod_smithing");

        SmithingTransformRecipeBuilder.smithing(
                        Ingredient.of(strings),
                        Ingredient.of(Items.FISHING_ROD, TideItems.STONE_FISHING_ROD,
                                TideItems.IRON_FISHING_ROD, TideItems.GOLDEN_FISHING_ROD,
                                TideItems.CRYSTAL_FISHING_ROD),
                        Ingredient.of(conventionTag("gems/diamond")),
                        RecipeCategory.TOOLS,
                        TideItems.DIAMOND_FISHING_ROD)
                .unlocks("impossible", impossible())
                .save(output, "tide:diamond_rod_smithing");

        SmithingTransformRecipeBuilder.smithing(
                        Ingredient.of(strings),
                        Ingredient.of(TideItems.DIAMOND_FISHING_ROD),
                        Ingredient.of(conventionTag("ingots/netherite")),
                        RecipeCategory.TOOLS,
                        TideItems.NETHERITE_FISHING_ROD)
                .unlocks("impossible", impossible())
                .save(output, "tide:netherite_rod_smithing");

        // -- Cooking --

        SimpleCookingRecipeBuilder.generic(Ingredient.of(TideTags.Items.COOKABLE_FISH),
                        RecipeCategory.FOOD, TideItems.COOKED_FISH,
                        0.35f, 600, RecipeSerializer.CAMPFIRE_COOKING_RECIPE)
                .unlockedBy("impossible", impossible())
                .save(output, "tide:cooked_fish_campfire_cooking");

        SimpleCookingRecipeBuilder.generic(Ingredient.of(TideTags.Items.COOKABLE_FISH),
                        RecipeCategory.FOOD, TideItems.COOKED_FISH,
                        0.1f, 200, RecipeSerializer.SMELTING_RECIPE)
                .unlockedBy("impossible", impossible())
                .save(output, "tide:cooked_fish_smelting");

        SimpleCookingRecipeBuilder.generic(Ingredient.of(TideTags.Items.COOKABLE_FISH),
                        RecipeCategory.FOOD, TideItems.COOKED_FISH,
                        0.1f, 100, RecipeSerializer.SMOKING_RECIPE)
                .unlockedBy("impossible", impossible())
                .save(output, "tide:cooked_fish_smoking");

        SimpleCookingRecipeBuilder.generic(Ingredient.of(TideItems.CLAYFISH),
                        RecipeCategory.FOOD, TideItems.HARDENED_CLAYFISH,
                        1.0f, 150, RecipeSerializer.SMELTING_RECIPE)
                .unlockedBy("impossible", impossible())
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
        Arrays.stream(BobberModifier.values()).toList().forEach(modifier -> {
            String dyeId = modifier.name().toLowerCase();

            ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, modifier.getItem())
                    .requires(conventionTag("slime_balls"))
                    .requires(ItemTags.PLANKS)
                    .requires(TagKey.create(Registries.ITEM, new ResourceLocation(
                            "c", "dyes/" + dyeId)))
                    .unlockedBy("impossible", impossible())
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