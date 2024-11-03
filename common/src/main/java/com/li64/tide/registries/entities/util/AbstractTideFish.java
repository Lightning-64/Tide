package com.li64.tide.registries.entities.util;

import com.li64.tide.Tide;
import com.li64.tide.client.TideClientHelper;
import com.li64.tide.data.player.TidePlayerData;
import com.li64.tide.registries.TideItems;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.AbstractSchoolingFish;
import net.minecraft.world.entity.animal.WaterAnimal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.HitResult;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class AbstractTideFish extends AbstractSchoolingFish {
    public AbstractTideFish(EntityType<? extends AbstractSchoolingFish> entityType, Level level) {
        super(entityType, level);
    }

    public static AttributeSupplier.@NotNull Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 3.0)
                .add(Attributes.MOVEMENT_SPEED, 1.0f);
    }

    @Override
    protected @NotNull SoundEvent getFlopSound() {
        return SoundEvents.COD_FLOP;
    }

    @Override
    protected @Nullable SoundEvent getHurtSound(@NotNull DamageSource source) {
        return SoundEvents.COD_HURT;
    }

    @Override
    protected @Nullable SoundEvent getDeathSound() {
        return SoundEvents.COD_DEATH;
    }

    public boolean isUnknown() {
        return !TidePlayerData.CLIENT_DATA.hasFishUnlocked(getFishItem()) && Tide.CONFIG.general.hideUnknownFishNames;
    }

    @Override
    public boolean hasCustomName() {
        return isUnknown();
    }

    @Override
    protected InteractionResult mobInteract(Player player, InteractionHand hand) {
        if (player.getItemInHand(hand).is(TideItems.FISHING_JOURNAL)) {
            if (level().isClientSide()) TideClientHelper.openJournalScreen(getFishItem());
            return InteractionResult.SUCCESS;
        } else return super.mobInteract(player, hand);
    }

    @Nullable
    @Override
    public Component getCustomName() {
        if (!isUnknown()) return super.getCustomName();
        else return getName();
    }

    @Override
    protected Component getTypeName() {
        return getFishItem().getDescription();
    }

    @Override
    public Component getName() {
        if (isUnknown()) return Component.literal("???");
        else return super.getName();
    }

    public abstract Item getFishItem();

    public static boolean canSpawn(EntityType<? extends AbstractTideFish> entityType, LevelAccessor level, MobSpawnType spawnType, BlockPos pos, RandomSource random) {
        return WaterAnimal.checkSurfaceWaterAnimalSpawnRules(entityType, level, spawnType, pos, random);
    }
}