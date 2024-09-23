package com.li64.tide.datagen.providers.tags;

import com.li64.tide.data.TideTags;
import com.li64.tide.registries.TideEntityTypes;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EntityType;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

public class TideEntityTypeTagsProvider extends FabricTagProvider<EntityType<?>> {
    public TideEntityTypeTagsProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, Registries.ENTITY_TYPE, registries);
    }

    @Override
    public @NotNull String getName() {
        return "Tide Entity Type Tags";
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        getOrCreateTagBuilder(TideTags.Entities.FRESHWATER_FISH)
                .add(TideEntityTypes.TROUT)
                .add(EntityType.SALMON)
                .add(TideEntityTypes.BASS)
                .add(TideEntityTypes.YELLOW_PERCH)
                .add(TideEntityTypes.BLUEGILL)
                .add(TideEntityTypes.MINT_CARP)
                .add(TideEntityTypes.PIKE)
                .add(TideEntityTypes.GUPPY)
                .add(TideEntityTypes.CATFISH)
                .add(TideEntityTypes.CLAYFISH);

        getOrCreateTagBuilder(TideTags.Entities.SALTWATER_FISH)
                .add(EntityType.TROPICAL_FISH)
                .add(EntityType.COD)
                .add(EntityType.PUFFERFISH)
                .add(TideEntityTypes.TUNA)
                .add(TideEntityTypes.OCEAN_PERCH)
                .add(TideEntityTypes.MACKEREL)
                .add(TideEntityTypes.ANGELFISH)
                .add(TideEntityTypes.BARRACUDA)
                .add(TideEntityTypes.SAILFISH);
    }
}
