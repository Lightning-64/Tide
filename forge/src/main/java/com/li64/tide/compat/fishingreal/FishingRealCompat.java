package com.li64.tide.compat.fishingreal;

import koala.fishingreal.FishingReal;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;

public class FishingRealCompat {
    public static Entity convertItemEntity(Entity itemEntity, Player player) {
        return FishingReal.convertItemEntity(itemEntity, player);
    }
}
