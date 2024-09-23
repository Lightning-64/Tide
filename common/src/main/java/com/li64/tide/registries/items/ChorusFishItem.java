package com.li64.tide.registries.items;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Fox;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.Vec3;

public class ChorusFishItem extends TideFishItem {
    public ChorusFishItem(Properties properties, float strength) {
        super(properties, strength);
    }

    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity entity) {
        ItemStack itemstack = super.finishUsingItem(stack, level, entity);
        if (!level.isClientSide) {
            for (int i = 0; i < 16; i++) {
                double d0 = entity.getX() + (entity.getRandom().nextDouble() - 0.5) * 16.0;
                double d1 = Mth.clamp(
                        entity.getY() + (double)(entity.getRandom().nextInt(16) - 8),
                        (double)level.getMinBuildHeight(),
                        (double)(level.getMinBuildHeight() + ((ServerLevel)level).getLogicalHeight() - 1)
                );
                double d2 = entity.getZ() + (entity.getRandom().nextDouble() - 0.5) * 16.0;
                if (entity.isPassenger()) {
                    entity.stopRiding();
                }

                Vec3 vec3 = entity.position();
                if (entity.randomTeleport(d0, d1, d2, true)) {
                    level.gameEvent(GameEvent.TELEPORT, vec3, GameEvent.Context.of(entity));
                    SoundSource soundsource;
                    SoundEvent soundevent;
                    if (entity instanceof Fox) {
                        soundevent = SoundEvents.FOX_TELEPORT;
                        soundsource = SoundSource.NEUTRAL;
                    } else {
                        soundevent = SoundEvents.CHORUS_FRUIT_TELEPORT;
                        soundsource = SoundSource.PLAYERS;
                    }

                    level.playSound(null, entity.getX(), entity.getY(), entity.getZ(), soundevent, soundsource);
                    entity.resetFallDistance();
                    break;
                }
            }

            if (entity instanceof Player player) {
                player.resetCurrentImpulseContext();
                player.getCooldowns().addCooldown(this, 20);
            }
        }

        return itemstack;
    }
}
