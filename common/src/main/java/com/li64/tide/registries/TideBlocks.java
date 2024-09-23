package com.li64.tide.registries;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import com.li64.tide.Tide;
import com.li64.tide.registries.blocks.*;

import java.util.HashMap;

public class TideBlocks {
    public static final HashMap<String, Block> BLOCKS = new HashMap<>();

    public static Block SURFACE_LOOT_CRATE = register("surface_loot_crate",
            new SurfaceLootCrateBlock(BlockBehaviour.Properties.copy(Blocks.BARREL)));
    public static Block OBSIDIAN_LOOT_CRATE = register("obsidian_loot_crate",
            new ObsidianLootCrateBlock(BlockBehaviour.Properties.copy(Blocks.OBSIDIAN)));
    public static Block END_LOOT_CRATE = register("end_loot_crate",
            new EndLootCrateBlock(BlockBehaviour.Properties.copy(Blocks.PURPUR_PILLAR)));

    public static Block ANGLER_WORKSHOP = register("angler_workshop",
            new AnglerWorkshopBlock(BlockBehaviour.Properties.copy(Blocks.CRAFTING_TABLE)));

    public static Block ALGAE = register("algae",
            new AlgaeBlock(BlockBehaviour.Properties.copy(Blocks.LILY_PAD).sound(SoundType.CROP).noCollission()));
    public static Block JELLY_TORCH = register("jelly_torch",
            new JellyTorchBlock(ParticleTypes.END_ROD, BlockBehaviour.Properties.copy(Blocks.TORCH).sound(SoundType.SLIME_BLOCK)));
    public static Block JELLY_WALL_TORCH = register("jelly_wall_torch",
            new JellyWallTorchBlock(ParticleTypes.END_ROD, BlockBehaviour.Properties.copy(TideBlocks.JELLY_TORCH)));

    public static <T extends Block> T register(String key, T block) {
        BLOCKS.put(key, block);
        return block;
    }

    public static void init() {
        BLOCKS.forEach(Tide.PLATFORM::registerBlock);
    }
}
