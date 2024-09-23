package com.li64.tide.registries.entities.misc;

import net.minecraft.CrashReportCategory;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.network.protocol.game.ClientboundBlockUpdatePacket;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerEntity;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.DirectionalPlaceContext;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.portal.DimensionTransition;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import com.li64.tide.Tide;
import com.li64.tide.registries.TideEntityTypes;
import com.li64.tide.registries.TideBlocks;
import com.li64.tide.registries.blocks.entities.LootCrateBlockEntity;

import java.util.function.Predicate;

public class LootCrateEntity extends Entity {
    private ResourceKey<LootTable> lootTable;
    private LootParams lootParams;
    private BlockState blockState = TideBlocks.SURFACE_LOOT_CRATE.defaultBlockState();
    public int time;
    public boolean dropItem = true;
    private boolean cancelDrop;
    private boolean hurtEntities;
    private int fallDamageMax = 40;
    private float fallDamagePerDistance;
    public CompoundTag blockData;
    public boolean forceTickAfterTeleportToDuplicate;
    protected static final EntityDataAccessor<BlockPos> DATA_START_POS = SynchedEntityData.defineId(LootCrateEntity.class, EntityDataSerializers.BLOCK_POS);

    public LootCrateEntity(EntityType<? extends Entity> p_31950_, Level p_31951_) {
        super(p_31950_, p_31951_);
    }

    private LootCrateEntity(Level level, double x, double y, double z, BlockState blockState, ResourceKey<LootTable> lootTable, LootParams lootParams) {
        this(TideEntityTypes.LOOT_CRATE, level);
        this.blockState = blockState;
        this.blocksBuilding = true;
        this.setPos(x, y, z);
        this.setDeltaMovement(Vec3.ZERO);
        this.xo = x;
        this.yo = y;
        this.zo = z;
        this.setStartPos(this.blockPosition());
        this.lootTable = lootTable;
        this.lootParams = lootParams;
    }

    public static LootCrateEntity fall(Level level, BlockPos pos, BlockState state, double dx, double dy, double dz, ResourceKey<LootTable> lootTable, LootParams lootParams) {
        LootCrateEntity entity = new LootCrateEntity(
                level,
                (double)pos.getX() + 0.5,
                pos.getY(),
                (double)pos.getZ() + 0.5,
                state,
                lootTable,
                lootParams
        );
        entity.setDeltaMovement(dx, dy, dz);
        level.setBlock(pos, state.getFluidState().createLegacyBlock(), 3);
        level.addFreshEntity(entity);
        return entity;
    }

    @Override
    public boolean isAttackable() {
        return false;
    }

    public void setStartPos(BlockPos p_31960_) {
        this.entityData.set(DATA_START_POS, p_31960_);
    }

    public BlockPos getStartPos() {
        return this.entityData.get(DATA_START_POS);
    }

    @Override
    protected MovementEmission getMovementEmission() {
        return MovementEmission.NONE;
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder p_329911_) {
        p_329911_.define(DATA_START_POS, BlockPos.ZERO);
    }

    @Override
    public boolean isPickable() {
        return !this.isRemoved();
    }

    @Override
    protected double getDefaultGravity() {
        return 0.04;
    }

    @Override
    public void tick() {
        if (this.blockState.isAir()) {
            this.discard();
        } else {
            Block block = this.blockState.getBlock();
            this.time++;
            this.applyGravity();
            this.move(MoverType.SELF, this.getDeltaMovement());
            this.handlePortal();
            if (!this.level().isClientSide && (this.isAlive() || this.forceTickAfterTeleportToDuplicate)) {
                BlockPos blockpos = this.blockPosition();
                boolean flag = this.blockState.getBlock() instanceof ConcretePowderBlock;
                boolean flag1 = flag && this.level().getFluidState(blockpos).is(FluidTags.WATER);
                double d0 = this.getDeltaMovement().lengthSqr();
                if (flag && d0 > 1.0) {
                    BlockHitResult blockhitresult = this.level()
                            .clip(
                                    new ClipContext(
                                            new Vec3(this.xo, this.yo, this.zo),
                                            this.position(),
                                            ClipContext.Block.COLLIDER,
                                            ClipContext.Fluid.SOURCE_ONLY,
                                            this
                                    )
                            );
                    if (blockhitresult.getType() != HitResult.Type.MISS && this.level().getFluidState(blockpos).is(FluidTags.WATER)) {
                        blockpos = blockhitresult.getBlockPos();
                        flag1 = true;
                    }
                }

                if (this.onGround() || flag1) {
                    BlockState placeBlockState = this.level().getBlockState(blockpos);
                    this.setDeltaMovement(this.getDeltaMovement().multiply(0.7, -0.5, 0.7));
                    if (!placeBlockState.is(Blocks.MOVING_PISTON)) {
                        if (!this.cancelDrop) {
                            boolean flag2 = placeBlockState.canBeReplaced(
                                    new DirectionalPlaceContext(this.level(), blockpos, Direction.DOWN, ItemStack.EMPTY, Direction.UP)
                            );
                            boolean flag3 = FallingBlock.isFree(this.level().getBlockState(blockpos.below())) && (!flag || !flag1);
                            boolean flag4 = this.blockState.canSurvive(this.level(), blockpos) && !flag3;
                            if (flag2 && flag4) {
                                if (this.blockState.hasProperty(BlockStateProperties.WATERLOGGED) && this.level().getFluidState(blockpos).getType() == Fluids.WATER) {
                                    this.blockState = this.blockState.setValue(BlockStateProperties.WATERLOGGED, Boolean.valueOf(true));
                                }

                                if (this.level().setBlock(blockpos, this.blockState, 3)) {
                                    ((ServerLevel)this.level())
                                            .getChunkSource()
                                            .chunkMap
                                            .broadcast(this, new ClientboundBlockUpdatePacket(blockpos, this.level().getBlockState(blockpos)));
                                    this.discard();

                                    Tide.LOG.debug("Blockdata == null: {}", blockData == null);
                                    Tide.LOG.debug("HasBlockEntity: {}", blockState.hasBlockEntity());
                                    if (this.blockState.hasBlockEntity()) {
                                        Tide.LOG.debug("Loading block entity");
                                        BlockEntity blockEntity = this.level().getBlockEntity(blockpos);
                                        if (blockEntity != null) {
                                            if (blockEntity instanceof LootCrateBlockEntity lootCrateBlockEntity) {
                                                Tide.LOG.debug("Adding crate loot to entity");
                                                addCrateLoot(lootCrateBlockEntity);
                                            }
                                        }
                                    }
                                } else if (this.dropItem && this.level().getGameRules().getBoolean(GameRules.RULE_DOENTITYDROPS)) {
                                    this.discard();
                                    this.spawnAtLocation(block);
                                    this.dropCrateLoot();
                                }
                            } else {
                                this.discard();
                                if (this.dropItem && this.level().getGameRules().getBoolean(GameRules.RULE_DOENTITYDROPS)) {
                                    this.spawnAtLocation(block);
                                    this.dropCrateLoot();
                                }
                            }
                        } else {
                            this.discard();
                        }
                    }
                } else if (!this.level().isClientSide
                        && (
                        this.time > 100 && (blockpos.getY() <= this.level().getMinBuildHeight() || blockpos.getY() > this.level().getMaxBuildHeight())
                                || this.time > 600
                )) {
                    if (this.dropItem && this.level().getGameRules().getBoolean(GameRules.RULE_DOENTITYDROPS)) {
                        this.spawnAtLocation(block);
                        this.dropCrateLoot();
                    }

                    this.discard();
                }
            }

            this.setDeltaMovement(this.getDeltaMovement().scale(0.98));
        }
    }

    @Override
    public boolean causeFallDamage(float p_149643_, float p_149644_, DamageSource p_149645_) {
        if (!this.hurtEntities) {
            return false;
        } else {
            int i = Mth.ceil(p_149643_ - 1.0F);
            if (i < 0) {
                return false;
            } else {
                Predicate<Entity> predicate = EntitySelector.NO_CREATIVE_OR_SPECTATOR.and(EntitySelector.LIVING_ENTITY_STILL_ALIVE);
                DamageSource damagesource = this.blockState.getBlock() instanceof Fallable fallable ? fallable.getFallDamageSource(this) : this.damageSources().fallingBlock(this);
                float f = (float)Math.min(Mth.floor((float)i * this.fallDamagePerDistance), this.fallDamageMax);
                this.level().getEntities(this, this.getBoundingBox(), predicate).forEach(p_149649_ -> p_149649_.hurt(damagesource, f));
                boolean flag = this.blockState.is(BlockTags.ANVIL);
                if (flag && f > 0.0F && this.random.nextFloat() < 0.05F + (float)i * 0.05F) {
                    BlockState blockstate = AnvilBlock.damage(this.blockState);
                    if (blockstate == null) {
                        this.cancelDrop = true;
                    } else {
                        this.blockState = blockstate;
                    }
                }

                return false;
            }
        }
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag p_31973_) {
        p_31973_.put("BlockState", NbtUtils.writeBlockState(this.blockState));
        p_31973_.putInt("Time", this.time);
        p_31973_.putBoolean("DropItem", this.dropItem);
        p_31973_.putBoolean("HurtEntities", this.hurtEntities);
        p_31973_.putFloat("FallHurtAmount", this.fallDamagePerDistance);
        p_31973_.putInt("FallHurtMax", this.fallDamageMax);
        if (this.blockData != null) {
            p_31973_.put("TileEntityData", this.blockData);
        }

        p_31973_.putBoolean("CancelDrop", this.cancelDrop);
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag p_31964_) {
        this.blockState = NbtUtils.readBlockState(this.level().holderLookup(Registries.BLOCK), p_31964_.getCompound("BlockState"));
        this.time = p_31964_.getInt("Time");
        if (p_31964_.contains("HurtEntities", 99)) {
            this.hurtEntities = p_31964_.getBoolean("HurtEntities");
            this.fallDamagePerDistance = p_31964_.getFloat("FallHurtAmount");
            this.fallDamageMax = p_31964_.getInt("FallHurtMax");
        } else if (this.blockState.is(BlockTags.ANVIL)) {
            this.hurtEntities = true;
        }

        if (p_31964_.contains("DropItem", 99)) {
            this.dropItem = p_31964_.getBoolean("DropItem");
        }

        if (p_31964_.contains("TileEntityData", 10)) {
            this.blockData = p_31964_.getCompound("TileEntityData").copy();
        }

        this.cancelDrop = p_31964_.getBoolean("CancelDrop");
        if (this.blockState.isAir()) {
            this.blockState = Blocks.SAND.defaultBlockState();
        }
    }

    @Override
    public boolean displayFireAnimation() {
        return false;
    }

    @Override
    public void fillCrashReportCategory(CrashReportCategory p_31962_) {
        super.fillCrashReportCategory(p_31962_);
        p_31962_.setDetail("Immitating BlockState", this.blockState.toString());
    }

    public BlockState getBlockState() {
        return this.blockState;
    }

    @Override
    protected Component getTypeName() {
        return Component.translatable("entity.minecraft.falling_block_type", this.blockState.getBlock().getName());
    }

    @Override
    public boolean onlyOpCanSetNbt() {
        return true;
    }

    @Override
    public Packet<ClientGamePacketListener> getAddEntityPacket(ServerEntity p_342166_) {
        return new ClientboundAddEntityPacket(this, p_342166_, Block.getId(this.getBlockState()));
    }

    @Override
    public void recreateFromPacket(ClientboundAddEntityPacket p_149654_) {
        super.recreateFromPacket(p_149654_);
        this.blockState = Block.stateById(p_149654_.getData());
        this.blocksBuilding = true;
        double d0 = p_149654_.getX();
        double d1 = p_149654_.getY();
        double d2 = p_149654_.getZ();
        this.setPos(d0, d1, d2);
        this.setStartPos(this.blockPosition());
    }

    @Override
    public Entity changeDimension(DimensionTransition dimensionTransition) {
        ResourceKey<Level> newDimension = dimensionTransition.newLevel().dimension();
        ResourceKey<Level> oldDimension = this.level().dimension();
        boolean flag = (oldDimension == Level.END || newDimension == Level.END) && oldDimension != newDimension;
        Entity entity = super.changeDimension(dimensionTransition);
        this.forceTickAfterTeleportToDuplicate = entity != null && flag;
        return entity;
    }

    @Override
    public boolean fireImmune() {
        return true;
    }

    @Override
    public boolean isOnFire() {
        return false;
    }

    public void dropCrateLoot() {
        if (this.lootTable != null && level().getServer() != null) {
            LootTable lootTable = level().getServer().reloadableRegistries().getLootTable(this.lootTable);
            lootTable.getRandomItems(lootParams).forEach(this::spawnAtLocation);
        }
    }

    public void addCrateLoot(LootCrateBlockEntity block) {
        if (this.lootTable != null && level().getServer() != null) {
            LootTable lootTable = level().getServer().reloadableRegistries().getLootTable(this.lootTable);
            lootTable.fill(block, lootParams, random.nextLong());
        }
    }
}
