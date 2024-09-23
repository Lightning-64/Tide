package com.li64.tide.datagen.providers.recipes;

import com.li64.tide.compat.jei.recipe.RodUpgradingRecipe;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRequirements;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.LinkedHashMap;
import java.util.Map;

public class RodUpgradingRecipeBuilder {
    private final ItemStack rod;
    private final Map<String, Criterion<?>> criteria = new LinkedHashMap<>();

    public RodUpgradingRecipeBuilder(Item rod) {
        this.rod = rod.getDefaultInstance();
    }

    public RodUpgradingRecipeBuilder unlocks(String key, Criterion<?> criterion) {
        this.criteria.put(key, criterion);
        return this;
    }

    public void save(RecipeOutput output, String id) {
        this.save(output, ResourceLocation.parse(id));
    }

    public void save(RecipeOutput output, ResourceLocation id) {
        this.ensureValid(id);
        Advancement.Builder builder = output.advancement()
                .addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(id))
                .rewards(AdvancementRewards.Builder.recipe(id))
                .requirements(AdvancementRequirements.Strategy.OR);

        this.criteria.forEach(builder::addCriterion);
        RodUpgradingRecipe recipe = new RodUpgradingRecipe(rod.copy(), rod.copy());
        output.accept(id, recipe, builder.build(id.withPrefix("recipes/" + RecipeCategory.TOOLS.getFolderName() + "/")));
    }

    private void ensureValid(ResourceLocation pLocation) {
        if (this.criteria.isEmpty()) {
            throw new IllegalStateException("No way of obtaining recipe " + pLocation);
        }
    }
}