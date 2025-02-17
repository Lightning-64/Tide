package com.li64.tide.registries.entities.misc;

import com.li64.tide.data.TideLootTables;
import com.li64.tide.registries.TideEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.protocol.game.ClientboundBlockUpdatePacket;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.DirectionalPlaceContext;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import com.li64.tide.registries.blocks.entities.LootCrateBlockEntity;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

public class LootCrateEntity extends FallingBlockEntity {
    private static final ResourceKey<LootTable> CRATE_LOOT_TABLE = TideLootTables.Fishing.CRATES;
    private LootParams lootParams;

    public LootCrateEntity(EntityType<FallingBlockEntity> entityType, Level level) {
        super(entityType, level);
    }

    public static void create(Level level, BlockPos pos, BlockState blockState, double dx, double dy, double dz, LootParams lootParams) {
        LootCrateEntity entity = new LootCrateEntity(level, pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5,
                blockState.hasProperty(BlockStateProperties.WATERLOGGED)
                        ? blockState.setValue(BlockStateProperties.WATERLOGGED, false)
                        : blockState, lootParams);
        entity.setDeltaMovement(dx, dy, dz);
        level.setBlock(pos, blockState.getFluidState().createLegacyBlock(), 3);
        level.addFreshEntity(entity);
    }

    private LootCrateEntity(Level level, double x, double y, double z, BlockState state, LootParams lootParams) {
        this(TideEntityTypes.LOOT_CRATE, level);
        this.blockState = state;
        this.blocksBuilding = true;
        this.setPos(x, y, z);
        this.setDeltaMovement(Vec3.ZERO);
        this.xo = x;
        this.yo = y;
        this.zo = z;
        this.lootParams = lootParams;
        this.setStartPos(this.blockPosition());
    }

    @Override
    public void tick() {
        if (this.blockState.isAir()) {
            this.discard();
        } else {
            Block block = this.blockState.getBlock();
            ++this.time;
            this.applyGravity();
            this.move(MoverType.SELF, this.getDeltaMovement());
            this.applyEffectsFromBlocks();
            this.handlePortal();
            Level var3 = this.level();
            if (var3 instanceof ServerLevel serverLevel) {
                if (this.isAlive() || this.forceTickAfterTeleportToDuplicate) {
                    BlockPos blockpos = this.blockPosition();
                    boolean flag = this.blockState.getBlock() instanceof ConcretePowderBlock;
                    boolean flag1 = flag && this.level().getFluidState(blockpos).is(FluidTags.WATER);
                    double d0 = this.getDeltaMovement().lengthSqr();
                    if (flag && d0 > 1.0) {
                        BlockHitResult blockhitresult = this.level().clip(new ClipContext(new Vec3(this.xo, this.yo, this.zo), this.position(), net.minecraft.world.level.ClipContext.Block.COLLIDER, ClipContext.Fluid.SOURCE_ONLY, this));
                        if (blockhitresult.getType() != HitResult.Type.MISS && this.level().getFluidState(blockhitresult.getBlockPos()).is(FluidTags.WATER)) {
                            blockpos = blockhitresult.getBlockPos();
                            flag1 = true;
                        }
                    }

                    if (!this.onGround() && !flag1) {
                        if (this.time > 100 && (blockpos.getY() <= this.level().getMinY() || blockpos.getY() > this.level().getMaxY()) || this.time > 600) {
                            if (this.dropItem && serverLevel.getGameRules().getBoolean(GameRules.RULE_DOENTITYDROPS)) {
                                this.spawnAtLocation(serverLevel, block);
                            }

                            this.discard();
                        }
                    } else {
                        BlockState blockstate = this.level().getBlockState(blockpos);
                        this.setDeltaMovement(this.getDeltaMovement().multiply(0.7, -0.5, 0.7));
                        if (!blockstate.is(Blocks.MOVING_PISTON)) {
                            boolean flag2 = blockstate.canBeReplaced(new DirectionalPlaceContext(this.level(), blockpos, Direction.DOWN, ItemStack.EMPTY, Direction.UP));
                            boolean flag3 = FallingBlock.isFree(this.level().getBlockState(blockpos.below())) && (!flag || !flag1);
                            boolean flag4 = this.blockState.canSurvive(this.level(), blockpos) && !flag3;
                            if (flag2 && flag4) {
                                if (this.blockState.hasProperty(BlockStateProperties.WATERLOGGED) && this.level().getFluidState(blockpos).getType() == Fluids.WATER) {
                                    this.blockState = this.blockState.setValue(BlockStateProperties.WATERLOGGED, true);
                                }

                                if (!this.level().setBlock(blockpos, this.blockState, 3)) {
                                    if (this.dropItem && serverLevel.getGameRules().getBoolean(GameRules.RULE_DOENTITYDROPS)) {
                                        this.discard();
                                        this.callOnBrokenAfterFall(block, blockpos);
                                        this.spawnAtLocation(serverLevel, block);
                                    }
                                } else {
                                    ((ServerLevel) this.level()).getChunkSource().chunkMap.broadcast(this, new ClientboundBlockUpdatePacket(blockpos, this.level().getBlockState(blockpos)));
                                    this.discard();
                                    if (level().getBlockEntity(blockpos) instanceof LootCrateBlockEntity lootCrate) {
                                        this.addCrateLoot(lootCrate);
                                    }
                                }
                            } else {
                                this.discard();
                                if (this.dropItem && serverLevel.getGameRules().getBoolean(GameRules.RULE_DOENTITYDROPS)) {
                                    this.dropCrateLoot();
                                    this.callOnBrokenAfterFall(block, blockpos);
                                    this.spawnAtLocation(serverLevel, block);
                                }
                            }
                        }
                    }
                }
            }

            this.setDeltaMovement(this.getDeltaMovement().scale(0.98));
        }
    }

    public void dropCrateLoot() {
        if (level() instanceof ServerLevel serverLevel) {
            LootTable lootTable = serverLevel.getServer().reloadableRegistries().getLootTable(CRATE_LOOT_TABLE);
            lootTable.getRandomItems(lootParams).forEach(item -> this.spawnAtLocation(serverLevel, item));
        }
    }

    public void addCrateLoot(LootCrateBlockEntity block) {
        if (level() instanceof ServerLevel serverLevel) {
            LootTable lootTable = serverLevel.getServer().reloadableRegistries().getLootTable(CRATE_LOOT_TABLE);
            lootTable.fill(block, lootParams, random.nextLong());
        }
    }
}
