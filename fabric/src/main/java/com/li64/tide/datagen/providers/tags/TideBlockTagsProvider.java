package com.li64.tide.datagen.providers.tags;

import com.li64.tide.registries.TideBlocks;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

public class TideBlockTagsProvider extends FabricTagProvider<Block> {
    public TideBlockTagsProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, Registries.BLOCK, registries);
    }

    @Override
    public @NotNull String getName() {
        return "Tide Block Tags";
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        getOrCreateTagBuilder(BlockTags.MINEABLE_WITH_AXE)
                .add(TideBlocks.SURFACE_LOOT_CRATE)
                .add(TideBlocks.ANGLER_WORKSHOP);

        getOrCreateTagBuilder(BlockTags.MINEABLE_WITH_PICKAXE)
                .add(TideBlocks.END_LOOT_CRATE)
                .add(TideBlocks.OBSIDIAN_LOOT_CRATE);

        getOrCreateTagBuilder(BlockTags.NEEDS_DIAMOND_TOOL)
                .add(TideBlocks.OBSIDIAN_LOOT_CRATE);
    }
}
