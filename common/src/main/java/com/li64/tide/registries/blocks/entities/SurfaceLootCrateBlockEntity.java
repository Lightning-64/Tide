package com.li64.tide.registries.blocks.entities;

import com.li64.tide.registries.TideBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class SurfaceLootCrateBlockEntity extends LootCrateBlockEntity {
    public SurfaceLootCrateBlockEntity(BlockPos pos, BlockState state) {
        super(TideBlockEntities.SURFACE_LOOT_CRATE, pos, state);
    }
}
