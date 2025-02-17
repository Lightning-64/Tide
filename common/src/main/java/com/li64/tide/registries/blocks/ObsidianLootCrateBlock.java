package com.li64.tide.registries.blocks;

import com.li64.tide.registries.TideBlockEntities;
import com.li64.tide.registries.blocks.entities.LootCrateBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ObsidianLootCrateBlock extends AbstractLootCrateBlock<LootCrateBlockEntity> {
    public ObsidianLootCrateBlock(Properties properties) {
        super(properties, () -> TideBlockEntities.OBSIDIAN_LOOT_CRATE);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(@NotNull BlockPos pos, @NotNull BlockState state) {
        return new LootCrateBlockEntity(TideBlockEntities.OBSIDIAN_LOOT_CRATE, pos, state);
    }
}
