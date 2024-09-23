package com.li64.tide.compat.jei;

import com.li64.tide.Tide;
import com.li64.tide.client.gui.screens.AnglerWorkshopScreen;
import com.li64.tide.compat.jei.recipe.RodUpgradingRecipe;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.IGuiHandlerRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeManager;

import java.util.List;

@JeiPlugin @SuppressWarnings("unused")
public class JEITidePlugin implements IModPlugin {
    @Override
    public ResourceLocation getPluginUid() {
        return Tide.resource("jei_plugin");
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        registration.addRecipeCategories(new RodUpgradingCategory(registration.getJeiHelpers().getGuiHelper()));
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        RecipeManager recipeManager = Minecraft.getInstance().level.getRecipeManager();

        List<RodUpgradingRecipe> recipes = recipeManager.getAllRecipesFor(RodUpgradingRecipe.Type.INSTANCE);
        registration.addRecipes(RodUpgradingCategory.RECIPE_TYPE, recipes);
    }

    @Override
    public void registerGuiHandlers(IGuiHandlerRegistration registration) {
        registration.addRecipeClickArea(AnglerWorkshopScreen.class, 27, 30, 16, 18,
                RodUpgradingCategory.RECIPE_TYPE);
    }
}
