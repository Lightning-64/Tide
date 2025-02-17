package com.li64.tide.datagen.providers.recipes;

import com.li64.tide.data.rods.RodUpgradingRecipe;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRequirements;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.LinkedHashMap;
import java.util.Map;

public class RodUpgradingRecipeBuilder implements RecipeBuilder {
    private final ItemStack rod;
    private final Map<String, Criterion<?>> criteria = new LinkedHashMap<>();

    public RodUpgradingRecipeBuilder(Item rod) {
        this.rod = rod.getDefaultInstance();
    }

    @Override
    public @NotNull RecipeBuilder unlockedBy(String name, Criterion<?> criterion) {
        this.criteria.put(name, criterion);
        return this;
    }

    @Override
    public @NotNull RecipeBuilder group(@Nullable String groupName) {
        return this;
    }

    @Override
    public @NotNull Item getResult() {
        return rod.getItem();
    }

    @Override
    public void save(RecipeOutput output, ResourceKey<Recipe<?>> key) {
        Advancement.Builder builder = output.advancement()
                .addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(key))
                .rewards(AdvancementRewards.Builder.recipe(key))
                .requirements(AdvancementRequirements.Strategy.OR);

        this.criteria.forEach(builder::addCriterion);
        RodUpgradingRecipe recipe = new RodUpgradingRecipe(rod.copy(), rod.copy());
        output.accept(key, recipe, builder.build(key.location().withPrefix("recipes/" + RecipeCategory.TOOLS.getFolderName() + "/")));
    }
}