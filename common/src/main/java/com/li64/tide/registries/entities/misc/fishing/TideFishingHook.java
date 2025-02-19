package com.li64.tide.registries.entities.misc.fishing;

import com.li64.tide.data.TideCriteriaTriggers;
import com.li64.tide.data.TideDataComponents;
import com.li64.tide.data.TideLootTables;
import com.li64.tide.data.TideTags;
import com.li64.tide.data.rods.BaitContents;
import com.li64.tide.data.rods.CustomRodManager;
import com.li64.tide.registries.TideBlocks;
import com.li64.tide.registries.entities.misc.LootCrateEntity;
import com.li64.tide.registries.items.TideFishingRodItem;
import com.li64.tide.util.BaitUtils;
import com.mojang.logging.LogUtils;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.stats.Stats;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import com.li64.tide.Tide;
import com.li64.tide.util.TideUtils;
import com.li64.tide.registries.TideItems;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;

import java.util.*;

public class TideFishingHook extends Projectile {
    private static final Logger LOGGER = LogUtils.getLogger();
    private final RandomSource synchronizedRandom = RandomSource.create();
    private boolean biting;
    private int outOfWaterTime;
    private static final EntityDataAccessor<Integer> DATA_HOOKED_ENTITY = SynchedEntityData.defineId(TideFishingHook.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Boolean> DATA_BITING = SynchedEntityData.defineId(TideFishingHook.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<ItemStack> DATA_ROD_ITEM = SynchedEntityData.defineId(TideFishingHook.class, EntityDataSerializers.ITEM_STACK);
    private static final EntityDataAccessor<Integer> DATA_CATCH_TYPE = SynchedEntityData.defineId(TideFishingHook.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Boolean> DATA_MINIGAME_ACTIVE = SynchedEntityData.defineId(TideFishingHook.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Float> DATA_INITIAL_YAW = SynchedEntityData.defineId(TideFishingHook.class, EntityDataSerializers.FLOAT);
    private int life;
    private int nibble;
    private int timeUntilLured;
    private int timeUntilHooked;
    private float fishAngle;
    private boolean openWater = true;
    private Entity hookedIn;
    private FishHookState currentState = FishHookState.FLYING;
    private FluidState fluid;
    private final int luck;
    private final int lureSpeed;
    private boolean minigameActive = false;
    protected ItemStack rod;
    protected ItemStack hookedItem;
    protected CatchType catchType = CatchType.NOTHING;
    private int particleTimer = 0;

    private TideFishingHook(EntityType<? extends TideFishingHook> entityType, Level level, int luck, int lureSpeed, ItemStack rod) {
        super(entityType, level);
        this.rod = rod;
        this.entityData.set(DATA_ROD_ITEM, rod);
        this.luck = Math.max(0, luck + (getLine().is(TideItems.FORTUNE_LINE) ? 1 : 0));
        this.lureSpeed = Math.max(0, lureSpeed);
    }

    public TideFishingHook(EntityType<? extends TideFishingHook> entityType, Level level) {
        this(entityType, level, 0, 0, Items.FISHING_ROD.getDefaultInstance());
    }

    public TideFishingHook(EntityType<? extends TideFishingHook> hookType, Player player, Level level,
                           int luck, int lureSpeed, float charge, ItemStack rod) {
        this(hookType, level, luck, lureSpeed, rod);
        this.setOwner(player);

        float f = player.getXRot();
        float f1 = player.getYRot();
        float f2 = Mth.cos(-f1 * ((float)Math.PI / 180F) - (float)Math.PI);
        float f3 = Mth.sin(-f1 * ((float)Math.PI / 180F) - (float)Math.PI);
        float f4 = -Mth.cos(-f * ((float)Math.PI / 180F));
        float f5 = Mth.sin(-f * ((float)Math.PI / 180F));

        double d0 = player.getX() - (double)f3 * 0.3D;
        double d1 = player.getEyeY();
        double d2 = player.getZ() - (double)f2 * 0.3D;
        this.moveTo(d0, d1, d2, f1, f);

        Vec3 vec3 = new Vec3(-f3, Mth.clamp(-(f5 / f4), -5.0F, 5.0F), -f2);
        double d3 = vec3.length();
        vec3 = vec3.multiply(charge, charge, charge); // Slow the speed if not fully charged
        vec3 = vec3.multiply(0.6D / d3 + this.random.triangle(0.5D, 0.0103365D), 0.6D / d3 + this.random.triangle(0.5D, 0.0103365D), 0.6D / d3 + this.random.triangle(0.5D, 0.0103365D));
        this.setDeltaMovement(vec3);

        this.setYRot(f1);
        this.setXRot(0);
        this.yRotO = this.getYRot();
        this.xRotO = this.getXRot();

        this.entityData.set(DATA_INITIAL_YAW, f1);
        this.entityData.set(DATA_ROD_ITEM, rod);
    }

    public void invalidateCatch() {
        hookedItem = null;
        catchType = CatchType.NOTHING;
        this.getEntityData().set(DATA_CATCH_TYPE, catchType.ordinal());
    }

    public int getLureSpeed() {
        return lureSpeed;
    }

    public int getLuck() {
        return luck;
    }

    public float getInitialYaw() {
        return this.entityData.get(DATA_INITIAL_YAW);
    }

    public Holder<Biome> getBiome() {
        return level().getBiome(blockPosition());
    }

    public boolean isOpenWaterFishing() {
        return this.openWater;
    }

    public boolean usingMagneticBait() {
        return BaitUtils.getPrimaryBait(rod).is(TideItems.MAGNETIC_BAIT);
    }

    public enum CatchType {
        FISH, CRATE,
        ITEM, NOTHING
    }

    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        builder.define(DATA_HOOKED_ENTITY, 0);
        builder.define(DATA_BITING, false);
        builder.define(DATA_ROD_ITEM, Items.FISHING_ROD.getDefaultInstance());
        builder.define(DATA_CATCH_TYPE, CatchType.NOTHING.ordinal());
        builder.define(DATA_MINIGAME_ACTIVE, false);
        builder.define(DATA_INITIAL_YAW, 0f);
    }

    public void onSyncedDataUpdated(@NotNull EntityDataAccessor<?> data) {
        if (DATA_HOOKED_ENTITY.equals(data)) {
            int i = this.getEntityData().get(DATA_HOOKED_ENTITY);
            this.hookedIn = i > 0 ? this.level().getEntity(i - 1) : null;
        }

        if (DATA_BITING.equals(data)) {
            this.biting = this.getEntityData().get(DATA_BITING);
            if (this.biting) {
                this.setDeltaMovement(this.getDeltaMovement().x, -0.4F * Mth.nextFloat(this.synchronizedRandom, 0.6F, 1.0F), this.getDeltaMovement().z);
            }
        }

        if (DATA_ROD_ITEM.equals(data)) this.rod = this.getEntityData().get(DATA_ROD_ITEM);

        if (DATA_CATCH_TYPE.equals(data) && level().isClientSide()) {
            this.catchType = CatchType.values()[this.getEntityData().get(DATA_CATCH_TYPE)];
        }

        if (DATA_MINIGAME_ACTIVE.equals(data)) {
            minigameActive = this.getEntityData().get(DATA_MINIGAME_ACTIVE);
            if (!level().isClientSide() && !minigameActive) {
                restartFishingSequence();
            }
        }

        super.onSyncedDataUpdated(data);
    }

    public boolean shouldRenderAtSqrDistance(double dst) {
        return dst < 4096.0D;
    }

    public void tick() {
        if (getRodItem() == null) discard();

        this.synchronizedRandom.setSeed(this.getUUID().getLeastSignificantBits() ^ this.level().getGameTime());
        super.tick();

        // Particle effects
        if (BaitUtils.getPrimaryBait(rod).is(TideItems.LUCKY_BAIT) && this.currentState == FishHookState.BOBBING) {
            particleTimer++;
            if (particleTimer >= 5) {
                this.level().addParticle(ParticleTypes.WAX_ON,
                        this.getRandomX(0.8),
                        this.getY((2.0 * this.random.nextDouble() - 1.0) * 0.4) + 0.2,
                        this.getRandomZ(0.8),
                        0.0, 0.0, 0.0);
                particleTimer = 0;
            }
        }

        if (BaitUtils.getPrimaryBait(rod).is(TideItems.MAGNETIC_BAIT) && this.currentState == FishHookState.BOBBING) {
            particleTimer++;
            if (particleTimer >= 3) {
                Vec3 pos = this.position();
                Vec3 randomPos = new Vec3(
                        this.getX() + 0.5 * (this.random.nextGaussian() - this.random.nextGaussian()),
                        this.getY() + 0.5 * (this.random.nextGaussian() - this.random.nextGaussian()),
                        this.getZ() + 0.5 * (this.random.nextGaussian() - this.random.nextGaussian()));
                Vec3 speed = pos.vectorTo(randomPos);
                this.level().addParticle(ParticleTypes.OMINOUS_SPAWNING,
                        pos.x(), pos.y(), pos.z(),
                        speed.x(), speed.y(), speed.z());
                particleTimer = 0;
            }
        }

        Player player = this.getPlayerOwner();
        if (player == null) {
            this.discard();
        } else if (this.level().isClientSide || this.shouldKeepFishing(player)) {
            if (this.onGround()) {
                ++this.life;
                if (this.life >= 1200) {
                    this.discard();
                    return;
                }
            } else {
                this.life = 0;
            }

            float f = 0.0F;
            BlockPos blockpos = this.blockPosition();
            FluidState fluidstate = this.level().getFluidState(blockpos);
            fluid = fluidstate;
            if (canFishIn(fluidstate)) {
                f = fluidstate.getHeight(this.level(), blockpos);
            }

            boolean flag = f > 0.0F;
            if (this.currentState == FishHookState.FLYING) {
                if (this.hookedIn != null) {
                    this.setDeltaMovement(Vec3.ZERO);
                    this.currentState = FishHookState.HOOKED_IN_ENTITY;
                    return;
                }

                if (flag) {
                    this.setDeltaMovement(this.getDeltaMovement().multiply(0.3D, 0.2D, 0.3D));
                    this.currentState = FishHookState.BOBBING;
                    return;
                }

                this.checkCollision();
            } else {
                if (this.currentState == FishHookState.HOOKED_IN_ENTITY) {
                    if (this.hookedIn != null) {
                        if (!this.hookedIn.isRemoved() && this.hookedIn.level().dimension() == this.level().dimension()) {
                            this.setPos(this.hookedIn.getX(), this.hookedIn.getY(0.8D), this.hookedIn.getZ());
                        } else {
                            this.setHookedEntity(null);
                            this.currentState = FishHookState.FLYING;
                        }
                    }

                    return;
                }

                if (this.currentState == FishHookState.BOBBING) {
                    Vec3 vec3 = this.getDeltaMovement();
                    double d0 = this.getY() + vec3.y - (double)blockpos.getY() - (double)f;
                    if (Math.abs(d0) < 0.01D) {
                        d0 += Math.signum(d0) * 0.1D;
                    }

                    this.setDeltaMovement(vec3.x * 0.9D, vec3.y - d0 * (double)this.random.nextFloat() * 0.2D, vec3.z * 0.9D);
                    if (this.nibble <= 0 && this.timeUntilHooked <= 0) {
                        this.openWater = true;
                    } else {
                        this.openWater = this.openWater && this.outOfWaterTime < 10 && this.calculateOpenWater(blockpos);
                    }

                    if (flag) {
                        this.outOfWaterTime = Math.max(0, this.outOfWaterTime - 1);
                        if (this.biting) {
                            this.setDeltaMovement(this.getDeltaMovement().add(0.0D, -0.1D * (double)this.synchronizedRandom.nextFloat() * (double)this.synchronizedRandom.nextFloat(), 0.0D));
                        }

                        if (!this.level().isClientSide) {
                            this.catchingFish(blockpos);
                        }
                    } else {
                        this.outOfWaterTime = Math.min(10, this.outOfWaterTime + 1);
                    }
                }
            }

            if (!canFishIn(fluidstate)) {
                this.setDeltaMovement(this.getDeltaMovement().add(0.0D, -0.03D, 0.0D));
            }

            this.move(MoverType.SELF, this.getDeltaMovement());
            if (this.currentState == FishHookState.FLYING && (this.onGround() || this.horizontalCollision)) {
                this.setDeltaMovement(Vec3.ZERO);
            }

            double d1 = 0.92D;
            this.setDeltaMovement(this.getDeltaMovement().scale(d1));
            this.reapplyPosition();
        }
    }

    private boolean shouldKeepFishing(Player player) {
        boolean flag = player.getMainHandItem().equals(rod);
        boolean flag1 = player.getOffhandItem().equals(rod);
        if (!player.isRemoved() && player.isAlive() && (flag || flag1) && !(this.distanceToSqr(player) > 1224.0D)) {
            return true;
        } else {
            this.discard();
            return false;
        }
    }

    private void checkCollision() {
        HitResult hitresult = ProjectileUtil.getHitResultOnMoveVector(this, this::canHitEntity);
        this.hitTargetOrDeflectSelf(hitresult);
    }

    protected boolean canHitEntity(@NotNull Entity entity) {
        return super.canHitEntity(entity) || entity.isAlive() && entity instanceof ItemEntity;
    }

    protected void onHitEntity(@NotNull EntityHitResult result) {
        super.onHitEntity(result);
        if (!this.level().isClientSide) {
            if (result.getEntity() == this.getOwner()) this.discard();
            this.setHookedEntity(result.getEntity());
        }
    }

    protected void onHitBlock(@NotNull BlockHitResult result) {
        super.onHitBlock(result);
        this.setDeltaMovement(this.getDeltaMovement().normalize().scale(result.distanceTo(this)));
    }

    private void setHookedEntity(Entity p_150158_) {
        this.hookedIn = p_150158_;
        this.getEntityData().set(DATA_HOOKED_ENTITY, p_150158_ == null ? 0 : p_150158_.getId() + 1);
    }

    private void catchingFish(BlockPos pos) {
        if (Tide.PLATFORM.isModLoaded("stardew_fishing")) {
            if (Tide.PLATFORM.stardewGetRewards((HookAccessor) getPlayerOwner().fishing).isEmpty()) return;
        }

        ServerLevel level = (ServerLevel) this.level();
        int i = 1;
        BlockPos abovePos = pos.above();
        if (this.random.nextFloat() < 0.25F && this.level().isRainingAt(abovePos)) {
            ++i;
        }

        if (this.random.nextFloat() < 0.5F && !this.level().canSeeSky(abovePos)) {
            --i;
        }

        if (this.nibble > 0) {
            // When a fish is on the hook, but not reeled in yet

            if (!minigameActive) this.nibble--;

            if (this.nibble <= 0) {
                restartFishingSequence();
            }

        } else if (this.timeUntilHooked > 0) {
            // When a fish is visible, but not yet caught
            this.timeUntilHooked -= i;

            if (this.timeUntilHooked > 0) {

                this.fishAngle += (float)this.random.triangle(0.0D, 9.188D);
                float f = this.fishAngle * ((float)Math.PI / 180F);
                float f1 = Mth.sin(f);
                float f2 = Mth.cos(f);
                double d0 = this.getX() + (double)(f1 * (float)this.timeUntilHooked * 0.1F);
                double d1 = (float)Mth.floor(this.getY()) + 1.0F;
                double d2 = this.getZ() + (double)(f2 * (float)this.timeUntilHooked * 0.1F);
                BlockState blockstate = level.getBlockState(BlockPos.containing(d0, d1 - 1.0D, d2));

                if (blockstate.is(Blocks.WATER)) {
                    fluid = Fluids.WATER.defaultFluidState();
                    if (this.random.nextFloat() < 0.15F) {
                        level.sendParticles(ParticleTypes.BUBBLE, d0, d1 - 0.1, d2, 1, f1, 0.1D, f2, 0.0D);
                    }

                    float f3 = f1 * 0.04F;
                    float f4 = f2 * 0.04F;
                    level.sendParticles(ParticleTypes.FISHING, d0, d1, d2, 0, f4, 0.01D, -f3, 1.0D);
                    level.sendParticles(ParticleTypes.FISHING, d0, d1, d2, 0, -f4, 0.01D, f3, 1.0D);
                } else if (blockstate.is(Blocks.LAVA)) {
                    fluid = Fluids.LAVA.defaultFluidState();
                    if (this.random.nextFloat() < 0.15F) {
                        level.sendParticles(ParticleTypes.FLAME, d0, d1 - (double)0.1F, d2, 1, f1, 0.1D, f2, 0.0D);
                    }

                    float f3 = f1 * 0.04F;
                    float f4 = f2 * 0.04F;
                    level.sendParticles(ParticleTypes.LAVA, d0, d1, d2, 0, f4, 0.01D, -f3, 1.0D);
                    level.sendParticles(ParticleTypes.SMOKE, d0, d1, d2, 0, 0, 0.01D, 0, 1.0D);
                    level.sendParticles(ParticleTypes.LAVA, d0, d1, d2, 0, -f4, 0.01D, f3, 1.0D);
                }
            } else {
                // When a fish first touches the hook

                if (fluid.is(TideTags.Fluids.LAVA_FISHING)) {
                    this.playSound(SoundEvents.BUCKET_EMPTY_LAVA, 0.25F, 1.0F + (this.random.nextFloat() - this.random.nextFloat()) * 0.4F);
                    if (Tide.PLATFORM.isModLoaded("stardew_fishing")) this.playSound(SoundEvents.FISHING_BOBBER_SPLASH, 0.25F, 1.0F + (this.random.nextFloat() - this.random.nextFloat()) * 0.4F);
                    double d3 = this.getY() + 0.5D;
                    level.sendParticles(ParticleTypes.LAVA, this.getX(), d3, this.getZ(), (int) (1.0F + this.getBbWidth() * 20.0F), this.getBbWidth(), 0.0D, this.getBbWidth(), 0.2);
                    level.sendParticles(ParticleTypes.SMOKE, this.getX(), d3, this.getZ(), (int) (1.0F + this.getBbWidth() * 20.0F), this.getBbWidth(), 0.0D, this.getBbWidth(), 0.2);
                } else {
                    this.playSound(SoundEvents.FISHING_BOBBER_SPLASH, 0.25F, 1.0F + (this.random.nextFloat() - this.random.nextFloat()) * 0.4F);
                    double d3 = this.getY() + 0.5D;
                    level.sendParticles(ParticleTypes.BUBBLE, this.getX(), d3, this.getZ(), (int) (1.0F + this.getBbWidth() * 20.0F), this.getBbWidth(), 0.0D, this.getBbWidth(), 0.2);
                    level.sendParticles(ParticleTypes.FISHING, this.getX(), d3, this.getZ(), (int) (1.0F + this.getBbWidth() * 20.0F), this.getBbWidth(), 0.0D, this.getBbWidth(), 0.2);
                }

                // Select a catch
                this.nibble = Mth.nextInt(this.random, 20, 40);
                this.getEntityData().set(DATA_BITING, true);
                if (getPlayerOwner() != null) selectCatch(getPlayerOwner(), rod);
                else {
                    catchType = CatchType.NOTHING;
                    getEntityData().set(DATA_CATCH_TYPE, catchType.ordinal());
                }
            }
        } else if (this.timeUntilLured > 0) {
            // While a fish is waiting to appear

            this.timeUntilLured -= i;
            float f5 = 0.15F;
            if (this.timeUntilLured < 20) {
                f5 += (float)(20 - this.timeUntilLured) * 0.05F;
            } else if (this.timeUntilLured < 40) {
                f5 += (float)(40 - this.timeUntilLured) * 0.02F;
            } else if (this.timeUntilLured < 60) {
                f5 += (float)(60 - this.timeUntilLured) * 0.01F;
            }

            if (this.random.nextFloat() < f5) {
                float f6 = Mth.nextFloat(this.random, 0.0F, 360.0F) * ((float)Math.PI / 180F);
                float f7 = Mth.nextFloat(this.random, 25.0F, 60.0F);
                double d4 = this.getX() + (double)(Mth.sin(f6) * f7) * 0.1D;
                double d5 = (float)Mth.floor(this.getY()) + 1.0F;
                double d6 = this.getZ() + (double)(Mth.cos(f6) * f7) * 0.1D;
                BlockState blockstate1 = level.getBlockState(BlockPos.containing(d4, d5 - 1.0D, d6));
                if (blockstate1.is(Blocks.WATER)) {
                    level.sendParticles(ParticleTypes.SPLASH, d4, d5, d6, 2 + this.random.nextInt(2), 0.1F, 0.0D, 0.1F, 0.0D);
                } else if (blockstate1.is(Blocks.LAVA)) {
                    level.sendParticles(ParticleTypes.LAVA, d4, d5, d6, 2 + this.random.nextInt(2), 0.1F, 0.0D, 0.1F, 0.0D);
                }
            }

            if (this.timeUntilLured <= 0) {
                this.fishAngle = Mth.nextFloat(this.random, 0.0F, 360.0F);
                this.timeUntilHooked = Mth.nextInt(this.random, 20, 80);
            }

        } else {
            this.timeUntilLured = Mth.nextInt(this.random, 200, 600);
            this.timeUntilLured -= (int) (1200.0 / (1.0 + Math.exp(-0.3 * this.lureSpeed)) - 600.0);
            this.timeUntilLured = Math.max(this.timeUntilLured, 10);
        }
    }

    public void restartFishingSequence() {
        catchType = CatchType.NOTHING;
        getEntityData().set(DATA_CATCH_TYPE, catchType.ordinal());
        this.timeUntilLured = 0;
        this.timeUntilHooked = 0;
        this.getEntityData().set(DATA_BITING, false);
    }

    private boolean calculateOpenWater(BlockPos p_37159_) {
        OpenFluidType openWaterType = OpenFluidType.INVALID;

        for(int i = -1; i <= 2; ++i) {
            OpenFluidType openWaterType1 = this.getOpenWaterTypeForArea(p_37159_.offset(-2, i, -2), p_37159_.offset(2, i, 2));
            switch (openWaterType1) {
                case INVALID:
                    return false;
                case ABOVE_WATER:
                    if (openWaterType == OpenFluidType.INVALID) {
                        return false;
                    }
                    break;
                case INSIDE_WATER:
                    if (openWaterType == OpenFluidType.ABOVE_WATER) {
                        return false;
                    }
            }

            openWaterType = openWaterType1;
        }

        return true;
    }

    private OpenFluidType getOpenWaterTypeForArea(BlockPos p_37148_, BlockPos p_37149_) {
        return BlockPos.betweenClosedStream(p_37148_, p_37149_).map(this::getOpenWaterTypeForBlock).reduce((p_37139_, p_37140_) -> p_37139_ == p_37140_ ? p_37139_ : OpenFluidType.INVALID).orElse(OpenFluidType.INVALID);
    }

    private OpenFluidType getOpenWaterTypeForBlock(BlockPos p_37164_) {
        BlockState blockstate = this.level().getBlockState(p_37164_);
        if (!blockstate.isAir() && !blockstate.is(Blocks.LILY_PAD)) {
            FluidState fluidstate = blockstate.getFluidState();
            return canFishIn(fluidstate) && fluidstate.isSource() && blockstate.getCollisionShape(this.level(), p_37164_).isEmpty() ? OpenFluidType.INSIDE_WATER : OpenFluidType.INVALID;
        } else {
            return OpenFluidType.ABOVE_WATER;
        }
    }

    public void addAdditionalSaveData(@NotNull CompoundTag tag) {}

    public void readAdditionalSaveData(@NotNull CompoundTag tag) {}

    public void retrieve() {
        if (getRodItem() == null) discard();
        getRodItem().retrieveHook(rod, getPlayerOwner(), level());
    }

    public int retrieve(ItemStack stack, ServerLevel level, Player player) {
        if (!this.level().isClientSide && player != null && shouldKeepFishing(player)) {

            int i = 0;

            if (this.getHookedIn() != null) {

                this.pullEntity(this.getHookedIn());
                CriteriaTriggers.FISHING_ROD_HOOKED.trigger((ServerPlayer) player, stack, player.fishing, Collections.emptyList());
                this.level().broadcastEntityEvent(this, (byte) 31);
                i = this.getHookedIn() instanceof ItemEntity ? 3 : 5;

            } else if (nibble > 0) {
                List<ItemStack> itemList = new ArrayList<>();

                switch (catchType) {
                    case FISH, ITEM:
                        if (hasHookedItem()) itemList.add(hookedItem);
                        else return 0;

                        // This needs to be awarded even if the event is canceled
                        if (fluid.is(TideTags.Fluids.LAVA_FISHING)) TideCriteriaTriggers.FISHED_IN_LAVA.trigger((ServerPlayer) player);

                        boolean canceled = Tide.PLATFORM.forgeItemFishedEvent(List.copyOf(itemList), this.onGround() ? 2 : 1, player.fishing);

                        if (canceled) {
                            this.discard();
                            return 1;
                        }

                        ItemEntity itemEntity = new ItemEntity(this.level(), this.getX(), this.getY(), this.getZ(), hookedItem);

                        double d0 = player.getX() - this.getX();
                        double d1 = player.getY() - this.getY();
                        double d2 = player.getZ() - this.getZ();

                        itemEntity.setDeltaMovement(d0 * 0.1D, d1 * 0.1D + Math.sqrt(Math.sqrt(d0 * d0 + d1 * d1 + d2 * d2)) * 0.08D, d2 * 0.1D);
                        this.level().addFreshEntity(itemEntity);
                        player.level().addFreshEntity(new ExperienceOrb(player.level(), player.getX(), player.getY() + 0.5D, player.getZ() + 0.5D, this.random.nextInt(6) + 1));

                        if (hookedItem.is(ItemTags.FISHES)) {
                            player.awardStat(Stats.FISH_CAUGHT, 1);
                        }

                        CriteriaTriggers.FISHING_ROD_HOOKED.trigger((ServerPlayer) player, stack, player.fishing, itemList);

                        Tide.LOG.info("Caught fish: {}", hookedItem.getItem().getDescriptionId());
                        break;

                    case CRATE:
                        BlockState lootCrate = getCrateBlock(hookedItem);

                        LootParams.Builder lootParamsBuilder = new LootParams.Builder((ServerLevel) this.level())
                                .withParameter(LootContextParams.ORIGIN, this.position())
                                .withParameter(LootContextParams.TOOL, stack)
                                .withParameter(LootContextParams.THIS_ENTITY, this);

                        // Only forge and neoforge can use this parameter here
                        if (!Tide.PLATFORM.isFabric()) lootParamsBuilder = lootParamsBuilder
                                .withParameter(LootContextParams.ATTACKING_ENTITY, Objects.requireNonNull(this.getOwner()));

                        LootParams params = lootParamsBuilder
                                .withLuck((float)luck + player.getLuck())
                                .create(LootContextParamSets.FISHING);

                        level.setBlockAndUpdate(this.blockPosition(), lootCrate);

                        double dx = player.getX() - this.blockPosition().getX();
                        double dy = player.getY() - this.blockPosition().getY();
                        double dz = player.getZ() - this.blockPosition().getZ();

                        LootCrateEntity.create(level, this.blockPosition(), lootCrate,
                                dx * 0.0666d,
                                dy * 0.0666d + Math.sqrt(Math.sqrt(dx * dx + dy * dy + dz * dz)) * 0.082d + 0.27d,
                                dz * 0.0666d,
                                params
                        );

                        if (fluid.is(TideTags.Fluids.LAVA_FISHING) && fluid.is(Fluids.LAVA)) level.setBlockAndUpdate(this.blockPosition(), Blocks.LAVA.defaultBlockState());
                        if (fluid.is(TideTags.Fluids.WATER_FISHING) && fluid.is(Fluids.WATER)) level.setBlockAndUpdate(this.blockPosition(), Blocks.WATER.defaultBlockState());

                        TideCriteriaTriggers.FISHED_CRATE.trigger((ServerPlayer) player);
                        break;

                    case NOTHING:
                        break;
                }
                i = 1;
            }
            if (this.onGround()) {
                i = 2;
            }
            this.startRetrieving();

            if (getLine().is(TideItems.FISHING_LINE))
                i -= (new Random().nextFloat() > 0.7f ? 1 : 0);

            return i;
        } else {
            return 0;
        }
    }

    public void selectCatch(Player player, ItemStack rod) {
        catchType = CatchType.NOTHING;
        hookedItem = null;

        LootParams.Builder lootParamsBuilder = new LootParams.Builder((ServerLevel) this.level())
                .withParameter(LootContextParams.ORIGIN, this.position())
                .withParameter(LootContextParams.TOOL, rod)
                .withParameter(LootContextParams.THIS_ENTITY, this);

        // Only forge and neoforge can use this parameter here
        if (!Tide.PLATFORM.isFabric()) lootParamsBuilder = lootParamsBuilder
                .withParameter(LootContextParams.ATTACKING_ENTITY, Objects.requireNonNull(this.getOwner()));

        ResourceKey<LootTable> lootKey = BuiltInLootTables.FISHING;
        LootParams params = lootParamsBuilder
                .withLuck((float) luck + player.getLuck())
                .create(LootContextParamSets.FISHING);

        ItemStack selection = select(lootKey, params).orElse(Items.SALMON.getDefaultInstance());

        // Primitive compat with unusual end
        if (Tide.PLATFORM.isModLoaded("unusualend") && getBiome().is(ResourceLocation.parse("unusualend:warped_reef")))
            selection = BuiltInRegistries.ITEM.get(ResourceLocation.parse("unusualend:raw_bluk")).orElseThrow().value().getDefaultInstance();

        // Magnetic bait override
        if (usingMagneticBait() && random.nextInt(0, 4) == 0) { // TODO change 1 back to 4
            // select from crate
            lootKey = TideLootTables.Fishing.CRATES_BLOCK;
            selection = select(lootKey, params).orElse(TideItems.SURFACE_LOOT_CRATE.getDefaultInstance());
        } else if (TideUtils.shouldGrabTideLootTable(selection, fluid)) {
            // check special fish loot table
            lootKey = TideLootTables.Fishing.SPECIAL_FISH;
            selection = select(lootKey, params).orElse(Items.AIR.getDefaultInstance());

            // if no special fish is selected, use regular tide loot table
            if (selection.is(Items.AIR)) {
                lootKey = TideUtils.getTideLootTable(this.getX(), this.getY(), this.getZ(), fluid, level());
                selection = select(lootKey, params).orElse(Items.SALMON.getDefaultInstance());
            }
        }

        Tide.LOG.info("Loot table used: {}", lootKey.location());
        hookedItem = selection;

        catchType = (hookedItem.is(ItemTags.FISHES) || TideUtils.isJournalFish(hookedItem)) ? CatchType.FISH : CatchType.ITEM;
        if (selection.is(TideTags.Items.CRATES)) catchType = CatchType.CRATE;

        if (BaitUtils.isHoldingBait(rod)) {
            if (!player.isCreative()) {
                // consume used bait if necessary
                BaitContents.Mutable contents = new BaitContents.Mutable(rod.get(TideDataComponents.BAIT_CONTENTS));
                contents.shrinkStack(BaitUtils.getPrimaryBait(rod));
                rod.set(TideDataComponents.BAIT_CONTENTS, contents.toImmutable());
            }
        }

        getEntityData().set(DATA_CATCH_TYPE, catchType.ordinal());
    }

    private Optional<ItemStack> select(ResourceKey<LootTable> lootKey, LootParams params) {
        MinecraftServer server = level().getServer();
        if (server == null) return Optional.empty();
        LootTable table = server.reloadableRegistries().getLootTable(lootKey);
        return selectFromCatchList(table.getRandomItems(params));
    }

    private Optional<ItemStack> selectFromCatchList(List<ItemStack> list) {
        if (list == null || list.isEmpty()) return Optional.empty();

        try {
            return Optional.ofNullable(list.get(new Random().nextInt(0, list.size())));
        } catch (Exception e) {
            return Optional.ofNullable(list.getFirst());
        }
    }

    public BlockState getCrateBlock(ItemStack crate) {
        if (crate.getItem() instanceof BlockItem blockItem) {
            return blockItem.getBlock().defaultBlockState();
        } else return TideBlocks.SURFACE_LOOT_CRATE.defaultBlockState();
    }

    public void handleEntityEvent(byte event) {
        if (event == 31 && this.level().isClientSide && this.hookedIn instanceof Player && ((Player)this.hookedIn).isLocalPlayer()) {
            this.pullEntity(this.hookedIn);
        }

        super.handleEntityEvent(event);
    }

    protected void pullEntity(Entity entity) {
        Entity owner = this.getOwner();
        if (owner != null) {
            Vec3 vec3 = (new Vec3(owner.getX() - this.getX(), owner.getY() - this.getY(), owner.getZ() - this.getZ())).scale(0.1D);
            entity.setDeltaMovement(entity.getDeltaMovement().add(vec3));
        }
    }

    protected void startRetrieving() {
        // TODO maybe animate this somehow?
        catchType = CatchType.NOTHING;
        this.discard();
    }

    protected @NotNull MovementEmission getMovementEmission() {
        return MovementEmission.NONE;
    }

    public void remove(@NotNull RemovalReason reason) {
        this.updateOwnerInfo(null);
        super.remove(reason);
    }

    public void onClientRemoval() {
        this.updateOwnerInfo(null);
    }

    public void setOwner(Entity entity) {
        super.setOwner(entity);
        this.updateOwnerInfo(this);
    }

    private void updateOwnerInfo(TideFishingHook hook) {
        Player player = this.getPlayerOwner();
        if (player != null) {
            if (hook == null && player.fishing != null) ((HookAccessor)player.fishing).clearHook(player);
            if (player.fishing == null && hook != null) player.fishing = new HookAccessor(hook, level());
        }
    }

    public Player getPlayerOwner() {
        Entity entity = this.getOwner();
        return entity instanceof Player ? (Player)entity : null;
    }

    public Entity getHookedIn() {
        return this.hookedIn;
    }

    public void recreateFromPacket(@NotNull ClientboundAddEntityPacket packet) {
        super.recreateFromPacket(packet);
        if (this.getPlayerOwner() == null) {
            int i = packet.getData();
            LOGGER.error("Failed to recreate fishing hook on client. {} (id: {}) is not a valid owner.", this.level().getEntity(i), i);
            this.discard();
        }
    }

    @Override
    public boolean fireImmune() {
        if (canFishInLava()) return true;
        else return super.fireImmune();
    }

    @Override
    public boolean isOnFire() {
        if (canFishInLava()) return false;
        else return super.isOnFire();
    }

    public boolean canFishIn(FluidState fluidState) {
        if (fluidState.is(TideTags.Fluids.LAVA_FISHING)) return canFishInLava();
        else return fluidState.is(TideTags.Fluids.CAN_FISH_IN);
    }

    public boolean canFishInLava() {
        if (getRodItem() == null) return false;
        return getRodItem().isLavaproof(registryAccess(), rod);
    }

    public TideFishingRodItem getRodItem() {
        if (rod.getItem() instanceof TideFishingRodItem rodItem) return rodItem;
        return null;
    }

    public ItemStack getBobber() {
        return CustomRodManager.getBobber(rod, registryAccess());
    }
    public ItemStack getHook() {
        return CustomRodManager.getHook(rod, registryAccess());
    }
    public ItemStack getLine() {
        return CustomRodManager.getLine(rod, registryAccess());
    }

    public boolean hasHookedItem() {
        return hookedItem != null && !hookedItem.is(Items.AIR);
    }

    public Item getHookedItem() {
        return hookedItem.getItem();
    }

    public void setMinigameActive(boolean state) {
        this.minigameActive = state;
        if (!minigameActive) nibble = 0;
        this.entityData.set(DATA_MINIGAME_ACTIVE, state);
    }

    public CatchType getCatchType() {
        return CatchType.values()[getEntityData().get(DATA_CATCH_TYPE)];
    }

    enum FishHookState {
        FLYING,
        HOOKED_IN_ENTITY,
        BOBBING
    }

    enum OpenFluidType {
        ABOVE_WATER,
        INSIDE_WATER,
        INVALID
    }
}