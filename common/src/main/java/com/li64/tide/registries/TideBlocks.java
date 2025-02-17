package com.li64.tide.registries;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import com.li64.tide.Tide;
import com.li64.tide.registries.blocks.*;

import java.util.HashMap;
import java.util.function.Function;

public class TideBlocks {
    public static final HashMap<ResourceKey<Block>, Block> BLOCKS = new HashMap<>();

    public static Block SURFACE_LOOT_CRATE = register("surface_loot_crate",
            SurfaceLootCrateBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.BARREL));
    public static Block OBSIDIAN_LOOT_CRATE = register("obsidian_loot_crate",
            ObsidianLootCrateBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.OBSIDIAN));
    public static Block END_LOOT_CRATE = register("end_loot_crate",
            EndLootCrateBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.PURPUR_PILLAR));

    public static Block ANGLER_WORKSHOP = register("angler_workshop",
            AnglerWorkshopBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.CRAFTING_TABLE));

    public static Block ALGAE = register("algae",
            AlgaeBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.LILY_PAD).sound(SoundType.CROP).noCollission());
    public static Block JELLY_TORCH = register("jelly_torch",
            JellyTorchBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.TORCH).sound(SoundType.SLIME_BLOCK));
    public static Block JELLY_WALL_TORCH = register("jelly_wall_torch",
            JellyWallTorchBlock::new, BlockBehaviour.Properties.ofFullCopy(JELLY_TORCH));

    public static <T extends Block> T register(String name, Function<BlockBehaviour.Properties, T> factory, BlockBehaviour.Properties properties) {
        ResourceKey<Block> key = ResourceKey.create(Registries.BLOCK, Tide.resource(name));
        T block = factory.apply(properties.setId(key));
        BLOCKS.put(key, block);
        return block;
    }

    public static void init() {
        BLOCKS.forEach(Tide.PLATFORM::registerBlock);
    }
}
