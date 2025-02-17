package com.li64.tide.registries.blocks;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.WallTorchBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import org.jetbrains.annotations.NotNull;

public class JellyWallTorchBlock extends WallTorchBlock implements SimpleWaterloggedBlock {
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    public JellyWallTorchBlock(Properties properties) {
        super(ParticleTypes.END_ROD, properties);
    }

    protected void createBlockStateDefinition(StateDefinition.@NotNull Builder<Block, BlockState> stateBuilder) {
        super.createBlockStateDefinition(stateBuilder);
        stateBuilder.add(WATERLOGGED);
    }

    @Override
    public BlockState getStateForPlacement(@NotNull BlockPlaceContext context) {
        BlockState placementState = super.getStateForPlacement(context);
        if (placementState == null) return null;
        FluidState fluidstate = context.getLevel().getFluidState(context.getClickedPos());
        if (fluidstate.is(Fluids.WATER)) return placementState.setValue(WATERLOGGED, true);
        else return placementState.setValue(WATERLOGGED, false);
    }

    public @NotNull FluidState getFluidState(BlockState blockState) {
        return blockState.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(blockState);
    }
}
