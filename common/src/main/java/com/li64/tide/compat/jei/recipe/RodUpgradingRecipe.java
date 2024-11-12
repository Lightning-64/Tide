package com.li64.tide.compat.jei.recipe;

import com.google.gson.JsonObject;
import com.li64.tide.Tide;
import com.li64.tide.data.TideTags;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;

/**
 * The only reason I have this class is so I can get JEI to display the
 * rod upgrading stuff. This isn't actually used for any recipes.
 */
public class RodUpgradingRecipe implements Recipe<Container> {
    private final ItemStack input;
    private final ItemStack output;
    private final ResourceLocation id;

    public RodUpgradingRecipe(ItemStack input, ItemStack output, ResourceLocation id) {
        this.input = input;
        this.output = output;
        this.id = id;
    }

    @Override
    public boolean matches(Container input, Level level) {
        return !level.isClientSide;
    }

    @Override
    public ItemStack assemble(Container input, RegistryAccess registryAccess) {
        return output.copy();
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return true;
    }

    @Override
    public ItemStack getResultItem(RegistryAccess registryAccess) {
        return new ItemStack(output.getItem());
    }

    @Override
    public RecipeSerializer<RodUpgradingRecipe> getSerializer() {
        return Serializer.INSTANCE;
    }

    @Override
    public ResourceLocation getId() {
        return id;
    }

    @Override
    public RecipeType<RodUpgradingRecipe> getType() {
        return Type.INSTANCE;
    }

    public ItemStack getInput() {
        return input;
    }

    public Ingredient getModifier(int modifier) {
        return switch (modifier) {
            case 0 -> Ingredient.of(TideTags.Items.LINES);
            case 1 -> Ingredient.of(TideTags.Items.BOBBERS);
            default -> Ingredient.of(TideTags.Items.HOOKS);
        };
    }

    public static class Type implements RecipeType<RodUpgradingRecipe> {
        public static final Type INSTANCE = new Type();
        public static final String ID = "rod_upgrading";
    }

    public static class Serializer implements RecipeSerializer<RodUpgradingRecipe> {
        public static final Serializer INSTANCE = new Serializer();
        public static final ResourceLocation ID = Tide.resource("rod_upgrading");

        @Override
        public RodUpgradingRecipe fromJson(ResourceLocation recipeId, JsonObject serializedRecipe) {
            ItemStack input = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(serializedRecipe, "input"));
            ItemStack output = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(serializedRecipe, "output"));
            return new RodUpgradingRecipe(input, output, recipeId);
        }

        @Override
        public RodUpgradingRecipe fromNetwork(ResourceLocation recipeId, FriendlyByteBuf buffer) {
            ItemStack input = buffer.readItem();
            ItemStack output = buffer.readItem();
            return new RodUpgradingRecipe(input, output, recipeId);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buffer, RodUpgradingRecipe recipe) {
            buffer.writeItem(recipe.input);
            buffer.writeItem(recipe.getResultItem(null));
        }
    }
}