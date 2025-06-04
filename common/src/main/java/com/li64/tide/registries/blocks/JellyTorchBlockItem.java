package com.li64.tide.registries.blocks;

import net.minecraft.core.Direction;
import net.minecraft.world.item.StandingAndWallBlockItem;
import net.minecraft.world.level.block.Block;

public class JellyTorchBlockItem extends StandingAndWallBlockItem {
    public JellyTorchBlockItem(Block normal, Block wall, Properties properties, Direction direction) {
        super(normal, wall, direction, properties);
    }
}
