package com.li64.tide.registries.blocks;

import com.li64.tide.registries.TideBlockEntities;
import com.li64.tide.registries.blocks.entities.LootCrateBlockEntity;
import com.li64.tide.registries.blocks.entities.SurfaceLootCrateBlockEntity;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class SurfaceLootCrateBlock extends AbstractLootCrateBlock<LootCrateBlockEntity> {
    public static final MapCodec<SurfaceLootCrateBlock> CODEC = simpleCodec(SurfaceLootCrateBlock::new);

    public SurfaceLootCrateBlock(Properties properties) {
        super(properties, () -> TideBlockEntities.SURFACE_LOOT_CRATE);
    }

    @Override
    protected @NotNull MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new SurfaceLootCrateBlockEntity(pos, state);
    }
}
