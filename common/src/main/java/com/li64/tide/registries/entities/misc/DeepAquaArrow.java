package com.li64.tide.registries.entities.misc;

import com.li64.tide.registries.TideEntityTypes;
import com.li64.tide.registries.TideItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public class DeepAquaArrow extends AbstractArrow {
    public int lifetime = 50;

    public DeepAquaArrow(EntityType<? extends AbstractArrow> entityType, Level level) {
        super(entityType, level);
    }

    public DeepAquaArrow(Level level, LivingEntity entity, ItemStack itemStack, ItemStack itemStack1) {
        super(TideEntityTypes.DEEP_AQUA_ARROW, entity, level, itemStack, itemStack1);
    }

    @Override
    public void tick() {
        super.tick();
        if (!onGround()) {
            Vec3 vel = this.getDeltaMovement();
            double d5 = vel.x;
            double d6 = vel.y;
            double d1 = vel.z;
            for (int i = 0; i < 4; ++i) {
                this.level().addParticle(ParticleTypes.SPLASH, this.getX() + d5 * (double) i / 4.0D, this.getY() + d6 * (double) i / 4.0D, this.getZ() + d1 * (double) i / 4.0D, -d5, -d6 + 0.2D, -d1);
            }
            if (!level().isClientSide()) {
                lifetime--;
                if (lifetime <= 0) {
                    playSplashParticles(blockPosition());
                    kill((ServerLevel) level());
                }
            }
        }
    }

    @Override
    protected void onHit(HitResult hitResult) {
        HitResult.Type hitType = hitResult.getType();
        if (hitType == HitResult.Type.ENTITY) {
            this.onHitEntity((EntityHitResult)hitResult);
            this.level().gameEvent(GameEvent.PROJECTILE_LAND, hitResult.getLocation(), GameEvent.Context.of(this, null));
            playSplashParticles(blockPosition());
        } else if (hitType == HitResult.Type.BLOCK) {
            BlockHitResult blockhitresult = (BlockHitResult)hitResult;
            this.onHitBlock(blockhitresult);
            BlockPos blockpos = blockhitresult.getBlockPos();
            playSplashParticles(blockpos);
            this.level().gameEvent(GameEvent.PROJECTILE_LAND, blockpos, GameEvent.Context.of(this, this.level().getBlockState(blockpos)));
            if (level() instanceof ServerLevel level) kill(level);
        }
    }

    private void playSplashParticles(BlockPos pos) {
        for (int i = 0; i < 15; i++) {
            this.level().addParticle(ParticleTypes.SPLASH, pos.getX() + 0.5, pos.getY() + 0.7, pos.getZ() + 0.5, random.nextFloat() * 1.5f, 0.2D, random.nextFloat() * 1.5f);
        }
    }

    @Override
    public @NotNull Vec3 getDeltaMovement() {
        if (isCritArrow()) {
            return super.getDeltaMovement().normalize().scale(2.5f);
        } else {
            return super.getDeltaMovement().normalize().scale(1.8f);
        }
    }

    @Override
    public boolean isNoGravity() {
        return true;
    }

    @Override
    public void setNoGravity(boolean value) {
        super.setNoGravity(false);
    }

    protected @NotNull ItemStack getDefaultPickupItem() {
        return new ItemStack(TideItems.DEEP_AQUA_ARROW);
    }
}