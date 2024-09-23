package com.li64.tide.registries.blocks;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.IceBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

public class AlgaeBlock extends BushBlock {
    protected static final VoxelShape AABB = Block.box(1.0D, 0.0D, 1.0D, 15.0D, 1.5D, 15.0D);

    public AlgaeBlock(Properties p_58162_) {
        super(p_58162_);
    }

    public void entityInside(BlockState blockState, Level level, BlockPos pos, Entity entity) {
        super.entityInside(blockState, level, pos, entity);
        if (level instanceof ServerLevel && entity instanceof Boat) {
            level.destroyBlock(new BlockPos(pos), true, entity);
        }
    }

    @NotNull
    public VoxelShape getShape(BlockState blockState, BlockGetter blockGetter, BlockPos pos, CollisionContext context) {
        return AABB;
    }

    @Override
    protected boolean mayPlaceOn(@NotNull BlockState blockState, BlockGetter blockGetter, @NotNull BlockPos pos) {
        FluidState fluid = blockGetter.getFluidState(pos);
        FluidState aboveFluid = blockGetter.getFluidState(pos.above());
        return (fluid.getType() == Fluids.WATER || blockState.getBlock() instanceof IceBlock) && aboveFluid.getType() == Fluids.EMPTY;
    }
}
