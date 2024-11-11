package com.li64.tide.compat.jei.recipe;

import com.li64.tide.Tide;
import com.li64.tide.data.TideTags;
import com.li64.tide.data.rods.CustomRodManager;
import com.li64.tide.registries.TideItems;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;

import java.util.Random;

/**
 * The only reason I have this class is so I can get JEI to display the
 * rod upgrading stuff. This isn't actually used for any recipes.
 */
public class RodUpgradingRecipe implements Recipe<RecipeInput> {
    private final ItemStack input;
    private final ItemStack output;

    public RodUpgradingRecipe(ItemStack input, ItemStack output) {
        this.input = input;
        this.output = output;
    }

    @Override
    public boolean matches(RecipeInput input, Level level) {
        return !level.isClientSide;
    }

    @Override
    public ItemStack assemble(RecipeInput input, HolderLookup.Provider provider) {
        return output.copy();
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return true;
    }

    @Override
    public ItemStack getResultItem(HolderLookup.Provider provider) {
        return new ItemStack(output.getItem());
    }

    @Override
    public RecipeSerializer<RodUpgradingRecipe> getSerializer() {
        return Serializer.INSTANCE;
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
        public static final MapCodec<RodUpgradingRecipe> CODEC = RecordCodecBuilder.mapCodec(
            builder -> builder.group(
                ItemStack.STRICT_CODEC.fieldOf("input").forGetter(recipe -> recipe.input),
                ItemStack.STRICT_CODEC.fieldOf("output").forGetter(recipe -> recipe.output)
            ).apply(builder, RodUpgradingRecipe::new)
        );
        public static final StreamCodec<RegistryFriendlyByteBuf, RodUpgradingRecipe> STREAM_CODEC = StreamCodec.of(
                Serializer::toNetwork, Serializer::fromNetwork
        );

        public static RodUpgradingRecipe fromNetwork(RegistryFriendlyByteBuf buffer) {
            ItemStack input = ItemStack.STREAM_CODEC.decode(buffer);
            ItemStack output = ItemStack.STREAM_CODEC.decode(buffer);
            return new RodUpgradingRecipe(input, output);
        }

        public static void toNetwork(RegistryFriendlyByteBuf buffer, RodUpgradingRecipe recipe) {
            ItemStack.STREAM_CODEC.encode(buffer, recipe.input);
            ItemStack.STREAM_CODEC.encode(buffer, recipe.getResultItem(null));
        }

        @Override
        public MapCodec<RodUpgradingRecipe> codec() {
            return CODEC;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, RodUpgradingRecipe> streamCodec() {
            return STREAM_CODEC;
        }
    }
}