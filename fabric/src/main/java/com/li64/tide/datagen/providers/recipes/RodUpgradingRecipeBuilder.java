package com.li64.tide.datagen.providers.recipes;

import com.google.gson.JsonObject;
import com.li64.tide.compat.jei.recipe.RodUpgradingRecipe;
import net.minecraft.advancements.*;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.RecipeSerializer;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

public class RodUpgradingRecipeBuilder {
    private final Advancement.Builder advancement = Advancement.Builder.recipeAdvancement();
    private final Item rod;

    public RodUpgradingRecipeBuilder(Item item) {
        this.rod = item;
    }

    public RodUpgradingRecipeBuilder unlocks(String string, CriterionTriggerInstance criterionTriggerInstance) {
        this.advancement.addCriterion(string, criterionTriggerInstance);
        return this;
    }

    public void save(Consumer<FinishedRecipe> consumer, String string) {
        this.save(consumer, new ResourceLocation(string));
    }

    public void save(Consumer<FinishedRecipe> consumer, ResourceLocation id) {
        this.ensureValid(id);
        this.advancement.parent(RecipeBuilder.ROOT_RECIPE_ADVANCEMENT).addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(id)).rewards(net.minecraft.advancements.AdvancementRewards.Builder.recipe(id)).requirements(RequirementsStrategy.OR);
        consumer.accept(new Result(id, this.rod, this.advancement, id.withPrefix("recipes/" + RecipeCategory.TOOLS.getFolderName() + "/")));
    }

    private void ensureValid(ResourceLocation resourceLocation) {
        if (this.advancement.getCriteria().isEmpty()) {
            throw new IllegalStateException("No way of obtaining recipe " + resourceLocation);
        }
    }

    public record Result(ResourceLocation id, Item rod, Advancement.Builder advancement, ResourceLocation advancementId) implements FinishedRecipe {
        public void serializeRecipeData(JsonObject main) {
            JsonObject input = new JsonObject();
            input.addProperty("item", BuiltInRegistries.ITEM.getKey(this.rod).toString());
            input.addProperty("count", 1);

            JsonObject output = new JsonObject();
            output.addProperty("item", BuiltInRegistries.ITEM.getKey(this.rod).toString());
            output.addProperty("count", 1);

            main.add("input", input);
            main.add("output", output);
        }

        public ResourceLocation getId() {
            return this.id;
        }

        public RecipeSerializer<?> getType() {
            return RodUpgradingRecipe.Serializer.INSTANCE;
        }

        public JsonObject serializeAdvancement() {
            return this.advancement.serializeToJson();
        }

        @Nullable
        public ResourceLocation getAdvancementId() {
            return this.advancementId;
        }
    }
}