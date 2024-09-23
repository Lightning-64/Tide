package com.li64.tide.datagen.providers.tags;

import com.li64.tide.data.TideTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.level.material.Fluid;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

public class TideFluidTagsProvider extends FabricTagProvider<Fluid> {
    public TideFluidTagsProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, Registries.FLUID, registries);
    }

    @Override
    public @NotNull String getName() {
        return "Tide Fluid Tags";
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        getOrCreateTagBuilder(TideTags.Fluids.WATER_FISHING)
                .forceAddTag(FluidTags.WATER);

        getOrCreateTagBuilder(TideTags.Fluids.LAVA_FISHING)
                .forceAddTag(FluidTags.LAVA);

        getOrCreateTagBuilder(TideTags.Fluids.CAN_FISH_IN)
                .forceAddTag(TideTags.Fluids.WATER_FISHING)
                .forceAddTag(TideTags.Fluids.LAVA_FISHING);
    }
}
