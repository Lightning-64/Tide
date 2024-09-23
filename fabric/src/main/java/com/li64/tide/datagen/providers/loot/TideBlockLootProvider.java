package com.li64.tide.datagen.providers.loot;

import com.li64.tide.registries.TideBlocks;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.minecraft.core.HolderLookup;

import java.util.concurrent.CompletableFuture;

public class TideBlockLootProvider extends FabricBlockLootTableProvider {
    public TideBlockLootProvider(FabricDataOutput dataOutput) {
        super(dataOutput);
    }

    @Override
    public void generate() {
        this.dropSelf(TideBlocks.ALGAE);
        this.dropSelf(TideBlocks.JELLY_TORCH);
        this.dropSelf(TideBlocks.JELLY_WALL_TORCH);
        this.dropSelf(TideBlocks.ANGLER_WORKSHOP);
        this.dropSelf(TideBlocks.SURFACE_LOOT_CRATE);
        this.dropSelf(TideBlocks.OBSIDIAN_LOOT_CRATE);
        this.dropSelf(TideBlocks.END_LOOT_CRATE);
    }
}