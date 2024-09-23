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

    public ItemStack finishUsingItem(ItemStack $$0, Level $$1, LivingEntity $$2) {
        ItemStack $$3 = super.finishUsingItem($$0, $$1, $$2);
        if (!$$1.isClientSide) {
            double $$4 = $$2.getX();
            double $$5 = $$2.getY();
            double $$6 = $$2.getZ();

            for(int $$7 = 0; $$7 < 16; ++$$7) {
                double $$8 = $$2.getX() + ($$2.getRandom().nextDouble() - 0.5) * 16.0;
                double $$9 = Mth.clamp($$2.getY() + (double)($$2.getRandom().nextInt(16) - 8), (double)$$1.getMinBuildHeight(), (double)($$1.getMinBuildHeight() + ((ServerLevel)$$1).getLogicalHeight() - 1));
                double $$10 = $$2.getZ() + ($$2.getRandom().nextDouble() - 0.5) * 16.0;
                if ($$2.isPassenger()) {
                    $$2.stopRiding();
                }

                Vec3 $$11 = $$2.position();
                if ($$2.randomTeleport($$8, $$9, $$10, true)) {
                    $$1.gameEvent(GameEvent.TELEPORT, $$11, GameEvent.Context.of($$2));
                    SoundEvent $$12 = $$2 instanceof Fox ? SoundEvents.FOX_TELEPORT : SoundEvents.CHORUS_FRUIT_TELEPORT;
                    $$1.playSound(null, $$4, $$5, $$6, $$12, SoundSource.PLAYERS, 1.0F, 1.0F);
                    $$2.playSound($$12, 1.0F, 1.0F);
                    break;
                }
            }

            if ($$2 instanceof Player) {
                ((Player)$$2).getCooldowns().addCooldown(this, 20);
            }
        }

        return $$3;
    }
}
