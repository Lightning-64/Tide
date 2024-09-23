package com.li64.tide.registries.blocks;

import com.li64.tide.registries.TideBlockEntities;
import com.li64.tide.registries.blocks.entities.EndLootCrateBlockEntity;
import com.li64.tide.registries.blocks.entities.LootCrateBlockEntity;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class EndLootCrateBlock extends AbstractLootCrateBlock<LootCrateBlockEntity> {
    public static final MapCodec<EndLootCrateBlock> CODEC = simpleCodec(EndLootCrateBlock::new);

    public EndLootCrateBlock(Properties properties) {
        super(properties, () -> TideBlockEntities.END_LOOT_CRATE);
    }

    @Override
    protected @NotNull MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new EndLootCrateBlockEntity(pos, state);
    }
}
