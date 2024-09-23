package com.li64.tide.compat.jei;

import com.li64.tide.compat.jei.recipe.RodUpgradingRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import com.li64.tide.Tide;

public class TideRecipeSerializers {
    public static final DeferredRegister<RecipeSerializer<?>> SERIALIZERS =
            DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, Tide.MOD_ID);

    public static final RegistryObject<RecipeSerializer<RodUpgradingRecipe>> ROD_UPGRADING_SERIALIZER =
            SERIALIZERS.register("rod_upgrading", () -> RodUpgradingRecipe.Serializer.INSTANCE);

    public static void register(IEventBus eventBus) {
        SERIALIZERS.register(eventBus);
    }
}
