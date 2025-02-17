package com.li64.tide.registries;

import com.li64.tide.Tide;
import com.li64.tide.registries.blocks.entities.LootCrateBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;

import java.util.HashMap;
import java.util.Set;

public class TideBlockEntities {
    public static final HashMap<String, BlockEntityType<?>> BLOCK_ENTITIES = new HashMap<>();

    public static final BlockEntityType<? extends LootCrateBlockEntity> OBSIDIAN_LOOT_CRATE =
            register("obsidian_loot_crate", new BlockEntityType<>((pos, state) ->
                    new LootCrateBlockEntity(TideBlockEntities.OBSIDIAN_LOOT_CRATE, pos, state),
                    Set.of(TideBlocks.OBSIDIAN_LOOT_CRATE)));
    public static final BlockEntityType<? extends LootCrateBlockEntity> SURFACE_LOOT_CRATE =
            register("surface_loot_crate", new BlockEntityType<>((pos, state) ->
                    new LootCrateBlockEntity(TideBlockEntities.SURFACE_LOOT_CRATE, pos, state),
                    Set.of(TideBlocks.SURFACE_LOOT_CRATE)));
    public static final BlockEntityType<? extends LootCrateBlockEntity> END_LOOT_CRATE =
            register("end_loot_crate", new BlockEntityType<>((pos, state) ->
                    new LootCrateBlockEntity(TideBlockEntities.END_LOOT_CRATE, pos, state),
                    Set.of(TideBlocks.END_LOOT_CRATE)));

    public static <T extends BlockEntity> BlockEntityType<T> register(String key, BlockEntityType<T> block) {
        BLOCK_ENTITIES.put(key, block);
        return block;
    }

    public static void init() {
        BLOCK_ENTITIES.forEach(Tide.PLATFORM::registerBlockEntity);
    }
}
