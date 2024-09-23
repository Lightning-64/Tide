package com.li64.tide.compat.jei;

import com.li64.tide.Tide;
import com.li64.tide.client.gui.screens.AnglerWorkshopScreen;
import com.li64.tide.compat.jei.recipe.RodUpgradingRecipe;
import com.li64.tide.registries.TideBlocks;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public class RodUpgradingCategory implements IRecipeCategory<RodUpgradingRecipe> {
    public static final ResourceLocation ID = Tide.resource("rod_upgrading");
    public static final ResourceLocation GUI_LOCATION = AnglerWorkshopScreen.GUI_LOCATION;

    public static final RecipeType<RodUpgradingRecipe> RECIPE_TYPE =
            new RecipeType<>(ID, RodUpgradingRecipe.class);

    private final IDrawable guiBg;
    private final IDrawable icon;

    public RodUpgradingCategory(IGuiHelper guiHelper) {
        this.guiBg = guiHelper.createDrawable(GUI_LOCATION, 0, 0, 176, 166);
        this.icon = guiHelper.createDrawableIngredient(VanillaTypes.ITEM_STACK,
                TideBlocks.ANGLER_WORKSHOP.asItem().getDefaultInstance());
    }

    @Override
    public RecipeType<RodUpgradingRecipe> getRecipeType() {
        return RECIPE_TYPE;
    }

    @Override
    public Component getTitle() {
        return Component.translatable("category.tide.rod_upgrading");
    }

    @Override
    public IDrawable getBackground() {
        return guiBg;
    }

    @Override
    public IDrawable getIcon() {
        return icon;
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, RodUpgradingRecipe recipe, IFocusGroup focusGroup) {
        builder.addSlot(RecipeIngredientRole.INPUT, 26, 11).addItemStack(recipe.getInput());
        builder.addSlot(RecipeIngredientRole.INPUT, 134, 8).addIngredients(recipe.getModifier(0));
        builder.addSlot(RecipeIngredientRole.INPUT, 134, 32).addIngredients(recipe.getModifier(1));
        builder.addSlot(RecipeIngredientRole.INPUT, 134, 56).addIngredients(recipe.getModifier(2));
        builder.addSlot(RecipeIngredientRole.OUTPUT, 26, 49).addItemStack(recipe.getResultItem(null));
    }
}
