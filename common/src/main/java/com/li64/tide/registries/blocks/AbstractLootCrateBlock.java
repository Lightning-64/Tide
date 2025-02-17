package com.li64.tide.registries.blocks;

import com.li64.tide.Tide;
import com.li64.tide.data.TideLootTables;
import com.li64.tide.registries.blocks.entities.LootCrateBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Container;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BarrelBlock;
import net.minecraft.world.level.block.Fallable;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.function.Supplier;

public abstract class AbstractLootCrateBlock<E extends BlockEntity> extends BarrelBlock implements Fallable {
    protected final Supplier<BlockEntityType<? extends E>> blockEntityType;
    public static final BooleanProperty OPEN = BlockStateProperties.OPEN;

    protected AbstractLootCrateBlock(Properties properties, Supplier<BlockEntityType<? extends E>> blockEntityType) {
        super(properties);
        this.blockEntityType = blockEntityType;
    }

    @Override
    protected @NotNull InteractionResult useWithoutItem(@NotNull BlockState state, Level level, @NotNull BlockPos pos, @NotNull Player player, @NotNull BlockHitResult result) {
        if (level.isClientSide) {
            return InteractionResult.SUCCESS;
        } else {
            BlockEntity blockentity = level.getBlockEntity(pos);
            if (blockentity instanceof LootCrateBlockEntity block) {
                player.openMenu(block);
            }
            return InteractionResult.CONSUME;
        }
    }

    public void onRemove(BlockState state, @NotNull Level level, @NotNull BlockPos pos, BlockState replacer, boolean p_49080_) {
        if (!state.is(replacer.getBlock())) {
            BlockEntity blockentity = level.getBlockEntity(pos);
            if (blockentity instanceof Container) {
                Containers.dropContents(level, pos, (Container) blockentity);
                level.updateNeighbourForOutputSignal(pos, this);
            }

            super.onRemove(state, level, pos, replacer, p_49080_);
        }
    }

    public void tick(@NotNull BlockState state, ServerLevel level, @NotNull BlockPos pos, @NotNull RandomSource random) {
        BlockEntity blockentity = level.getBlockEntity(pos);
        if (blockentity instanceof LootCrateBlockEntity) {
            ((LootCrateBlockEntity) blockentity).recheckOpen();
        }
    }

    @Override
    public void onLand(@NotNull Level level, @NotNull BlockPos pos, @NotNull BlockState state, @NotNull BlockState replaceableState, @NotNull FallingBlockEntity fallingBlock) {
        Tide.LOG.info("Crate onLand called");
        if (level.getBlockEntity(pos) instanceof LootCrateBlockEntity lootCrate && level instanceof ServerLevel serverLevel) {
            if (!lootCrate.isFresh()) return; else lootCrate.markNotFresh();

            Tide.LOG.info("Attempting to fill crate loot");

            LootTable loottable = level.getServer().reloadableRegistries().getLootTable(TideLootTables.Fishing.CRATES);
            LootParams.Builder lootParams = getCrateParams(serverLevel, lootCrate);

            loottable.fill(lootCrate, lootParams.create(LootContextParamSets.CHEST), lootCrate.getLootTableSeed());
        }
    }

    private LootParams.Builder getCrateParams(ServerLevel serverLevel, LootCrateBlockEntity lootCrate) {
        LootParams.Builder builder = new LootParams.Builder(serverLevel)
                .withParameter(LootContextParams.ORIGIN, lootCrate.getPullOrigin())
                .withParameter(LootContextParams.TOOL, lootCrate.getFishingRod())
                .withLuck(lootCrate.getFishingLuck());

        if (lootCrate.getFishingHook(serverLevel) != null) builder
                .withParameter(LootContextParams.THIS_ENTITY, Objects.requireNonNull(lootCrate.getFishingHook(serverLevel)));

        // Only forge and neoforge can use this parameter here
        if (lootCrate.getPlayer(serverLevel) != null && !Tide.PLATFORM.isFabric()) builder
                .withParameter(LootContextParams.ATTACKING_ENTITY, Objects.requireNonNull(lootCrate.getPlayer(serverLevel)));

        return builder;
    }

    @Override
    public void onBrokenAfterFall(@NotNull Level level, @NotNull BlockPos pos, @NotNull FallingBlockEntity fallingBlock) {

    }
}