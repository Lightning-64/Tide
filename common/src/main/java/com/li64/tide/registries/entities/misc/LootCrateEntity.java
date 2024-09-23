package com.li64.tide.registries.entities.misc;

import net.minecraft.CrashReportCategory;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.network.protocol.game.ClientboundBlockUpdatePacket;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.DirectionalPlaceContext;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import com.li64.tide.Tide;
import com.li64.tide.registries.TideEntityTypes;
import com.li64.tide.registries.TideBlocks;
import com.li64.tide.registries.blocks.entities.LootCrateBlockEntity;

import javax.annotation.Nullable;
import java.util.function.Predicate;

public class LootCrateEntity extends Entity {
    private BlockState blockState = TideBlocks.SURFACE_LOOT_CRATE.defaultBlockState();
    private ResourceLocation lootTable;
    private LootParams lootParams;
    public int time;
    public boolean dropItem = true;
    private boolean cancelDrop;
    private boolean hurtEntities;
    private int fallDamageMax = 40;
    private float fallDamagePerDistance;
    @Nullable
    public CompoundTag blockData;
    protected static final EntityDataAccessor<BlockPos> DATA_START_POS = SynchedEntityData.defineId(LootCrateEntity.class, EntityDataSerializers.BLOCK_POS);

    public LootCrateEntity(EntityType<? extends LootCrateEntity> entityType, Level level) {
        super(entityType, level);
    }

    private LootCrateEntity(BlockState blockState, Level level, double px, double py, double pz, ResourceLocation lootTable, LootParams lootParams, double dx, double dy, double dz) {
        this(TideEntityTypes.LOOT_CRATE, level);
        this.blockState = blockState;
        this.blocksBuilding = true;
        this.setPos(px, py, pz);
        this.setDeltaMovement(dx, dy, dz);
        this.xo = px;
        this.yo = py;
        this.zo = pz;
        this.lootTable = lootTable;
        this.lootParams = lootParams;
        this.setStartPos(this.blockPosition());
    }

    public static LootCrateEntity fall(Level level, BlockPos pos, BlockState blockState, ResourceLocation lootTable, LootParams lootparams, double dx, double dy, double dz) {
        LootCrateEntity entity = new LootCrateEntity(blockState, level, (double)pos.getX() + 0.5D, pos.getY(), (double)pos.getZ() + 0.5D, lootTable, lootparams, dx, dy, dz);
        level.setBlock(pos, blockState.getFluidState().createLegacyBlock(), 3);
        level.addFreshEntity(entity);
        return entity;
    }

    public boolean isAttackable() {
        return false;
    }

    public void setStartPos(BlockPos p_31960_) {
        this.entityData.set(DATA_START_POS, p_31960_);
    }

    public BlockPos getStartPos() {
        return this.entityData.get(DATA_START_POS);
    }

    protected Entity.MovementEmission getMovementEmission() {
        return Entity.MovementEmission.NONE;
    }

    protected void defineSynchedData() {
        this.entityData.define(DATA_START_POS, BlockPos.ZERO);
    }

    public boolean isPickable() {
        return !this.isRemoved();
    }

    public void tick() {
        if (blockState.isAir()) {
            this.discard();
        } else {
            Block block = blockState.getBlock();
            ++this.time;
            if (!this.isNoGravity()) {
                this.setDeltaMovement(this.getDeltaMovement().add(0.0D, -0.04D, 0.0D));
            }

            this.move(MoverType.SELF, this.getDeltaMovement());
            if (!this.level().isClientSide) {
                BlockPos blockpos = this.blockPosition();

                if (!this.onGround()) {
                    if (!this.level().isClientSide && (this.time > 100 && (blockpos.getY() <= this.level().getMinBuildHeight() || blockpos.getY() > this.level().getMaxBuildHeight()) || this.time > 600)) {
                        if (this.dropItem && this.level().getGameRules().getBoolean(GameRules.RULE_DOENTITYDROPS)) {
                            this.spawnAtLocation(block);
                        }

                        this.discard();
                    }
                } else {
                    BlockState blockstate = this.level().getBlockState(blockpos);
                    this.setDeltaMovement(this.getDeltaMovement().multiply(0.7D, -0.5D, 0.7D));
                    if (!blockstate.is(Blocks.MOVING_PISTON)) {
                        if (!this.cancelDrop) {
                            boolean flag2 = blockstate.canBeReplaced(new DirectionalPlaceContext(this.level(), blockpos, Direction.DOWN, ItemStack.EMPTY, Direction.UP));
                            if (flag2) {
                                if (blockState.hasProperty(BlockStateProperties.WATERLOGGED) && this.level().getFluidState(blockpos).getType() == Fluids.WATER) {
                                    blockState = blockState.setValue(BlockStateProperties.WATERLOGGED, Boolean.valueOf(true));
                                }


                                if (this.level().setBlock(blockpos, blockState, 3)) {
                                    ((ServerLevel)this.level()).getChunkSource().chunkMap.broadcast(this, new ClientboundBlockUpdatePacket(blockpos, this.level().getBlockState(blockpos)));
                                    this.discard();

                                    if (this.level().getBlockEntity(blockpos) instanceof LootCrateBlockEntity entity) {
                                        if (this.blockData != null) {
                                            CompoundTag compoundtag = entity.saveWithoutMetadata();
                                            for (String s : this.blockData.getAllKeys()) {
                                                compoundtag.put(s, this.blockData.get(s).copy());
                                            }
                                            try {
                                                entity.load(compoundtag);
                                            } catch (Exception exception) {
                                                Tide.LOG.error("Failed to load block entity from loot crate", exception);
                                            }
                                        }
                                        setCrateLoot(entity);
                                        entity.setChanged();
                                    }
                                } else if (this.dropItem && this.level().getGameRules().getBoolean(GameRules.RULE_DOENTITYDROPS)) {
                                    this.discard();
                                    this.callOnBrokenAfterFall(block, blockpos);
                                    this.spawnAtLocation(block);
                                }
                            } else {
                                this.discard();
                                if (this.dropItem && this.level().getGameRules().getBoolean(GameRules.RULE_DOENTITYDROPS)) {
                                    this.callOnBrokenAfterFall(block, blockpos);
                                    this.spawnAtLocation(block);
                                }
                            }
                        } else {
                            this.discard();
                            this.callOnBrokenAfterFall(block, blockpos);
                        }
                    }
                }
            }

            this.setDeltaMovement(this.getDeltaMovement().scale(0.98D));
        }
    }

    public void callOnBrokenAfterFall(Block block, BlockPos blockPos) {
        dropCrateLoot();
    }

    public boolean causeFallDamage(float p_149643_, float p_149644_, DamageSource source) {
        if (this.hurtEntities) {
            int i = Mth.ceil(p_149643_ - 1.0F);
            if (i >= 0) {
                Predicate<Entity> predicate = EntitySelector.NO_CREATIVE_OR_SPECTATOR.and(EntitySelector.LIVING_ENTITY_STILL_ALIVE);
                Block block = blockState.getBlock();
                DamageSource damagesource1;
                if (block instanceof Fallable) {
                    Fallable fallable = (Fallable) block;
                    damagesource1 = fallable.getFallDamageSource(this);
                } else {
                    damagesource1 = this.damageSources().fallingBlock(this);
                }

                DamageSource damagesource = damagesource1;
                float f = (float) Math.min(Mth.floor((float) i * this.fallDamagePerDistance), this.fallDamageMax);
                this.level().getEntities(this, this.getBoundingBox(), predicate).forEach((entity) -> entity.hurt(damagesource, f));

            }
        }
        return false;
    }

    protected void addAdditionalSaveData(CompoundTag tag) {
        tag.put("BlockState", NbtUtils.writeBlockState(this.blockState));
        tag.putInt("Time", this.time);
        tag.putBoolean("DropItem", this.dropItem);
        tag.putBoolean("HurtEntities", this.hurtEntities);
        tag.putFloat("FallHurtAmount", this.fallDamagePerDistance);
        tag.putInt("FallHurtMax", this.fallDamageMax);
        if (this.blockData != null) {
            tag.put("TileEntityData", this.blockData);
        }

        tag.putBoolean("CancelDrop", this.cancelDrop);
    }

    protected void readAdditionalSaveData(CompoundTag tag) {
        this.blockState = NbtUtils.readBlockState(this.level().holderLookup(Registries.BLOCK), tag.getCompound("BlockState"));
        this.time = tag.getInt("Time");
        if (tag.contains("HurtEntities", 99)) {
            this.hurtEntities = tag.getBoolean("HurtEntities");
            this.fallDamagePerDistance = tag.getFloat("FallHurtAmount");
            this.fallDamageMax = tag.getInt("FallHurtMax");
        } else if (this.blockState.is(BlockTags.ANVIL)) {
            this.hurtEntities = true;
        }

        if (tag.contains("DropItem", 99)) {
            this.dropItem = tag.getBoolean("DropItem");
        }

        if (tag.contains("TileEntityData", 10)) {
            this.blockData = tag.getCompound("TileEntityData");
        }

        this.cancelDrop = tag.getBoolean("CancelDrop");
        if (this.blockState.isAir()) {
            this.blockState = Blocks.SAND.defaultBlockState();
        }

    }

    public void setHurtsEntities(float p_149657_, int p_149658_) {
        this.hurtEntities = true;
        this.fallDamagePerDistance = p_149657_;
        this.fallDamageMax = p_149658_;
    }

    public void disableDrop() {
        this.cancelDrop = true;
    }

    public boolean displayFireAnimation() {
        return false;
    }

    public void fillCrashReportCategory(CrashReportCategory category) {
        super.fillCrashReportCategory(category);
        category.setDetail("Imitating BlockState", blockState.toString());
    }

    public BlockState getBlockState() {
        return blockState;
    }

    public boolean onlyOpCanSetNbt() {
        return true;
    }

    public Packet<ClientGamePacketListener> getAddEntityPacket() {
        return new ClientboundAddEntityPacket(this, Block.getId(this.getBlockState()));
    }

    @Override
    public boolean fireImmune() {
        return true;
    }

    @Override
    public boolean isOnFire() {
        return false;
    }

    public void recreateFromPacket(ClientboundAddEntityPacket packet) {
        super.recreateFromPacket(packet);
        this.blockState = Block.stateById(packet.getData());
        this.blocksBuilding = true;
        double d0 = packet.getX();
        double d1 = packet.getY();
        double d2 = packet.getZ();
        this.setPos(d0, d1, d2);
        this.setStartPos(this.blockPosition());
    }

    public void dropCrateLoot() {
        if (this.lootTable != null && level().getServer() != null) {
            LootTable lootTable = level().getServer().getLootData().getLootTable(this.lootTable);
            lootTable.getRandomItems(lootParams).forEach(this::spawnAtLocation);
        }
    }

    public void setCrateLoot(LootCrateBlockEntity block) {
        if (this.lootTable != null && level().getServer() != null) {
            LootTable loottable = level().getServer().getLootData().getLootTable(this.lootTable);
            loottable.fill(block, lootParams, random.nextLong());
        }
    }
}
