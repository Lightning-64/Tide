package com.li64.tide.registries.blocks;

import com.li64.tide.registries.TideBlockEntities;
import com.li64.tide.registries.blocks.entities.LootCrateBlockEntity;
import com.li64.tide.registries.blocks.entities.ObsidianLootCrateBlockEntity;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.BaseEntityBlock;
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
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new ObsidianLootCrateBlockEntity(pos, state);
    }
}
