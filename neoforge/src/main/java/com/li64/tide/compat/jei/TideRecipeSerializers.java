package com.li64.tide.compat.jei;

import com.li64.tide.Tide;
import com.li64.tide.compat.jei.recipe.RodUpgradingRecipe;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class TideRecipeSerializers {
    public static final DeferredRegister<RecipeSerializer<?>> SERIALIZERS =
            DeferredRegister.create(Registries.RECIPE_SERIALIZER, Tide.MOD_ID);

    public static final Supplier<RecipeSerializer<RodUpgradingRecipe>> ROD_UPGRADING_SERIALIZER =
            SERIALIZERS.register("rod_upgrading", () -> RodUpgradingRecipe.Serializer.INSTANCE);

    public static void register(IEventBus eventBus) {
        SERIALIZERS.register(eventBus);
    }
}
