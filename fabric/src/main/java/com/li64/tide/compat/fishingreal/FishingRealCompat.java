package com.li64.tide.compat.fishingreal;

import koala.fishingreal.FishingReal;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;

public class FishingRealCompat {
    public static Entity convertItemStack(ItemStack stack, Player player, Vec3 pos) {
        Entity entity = FishingReal.convertItemStack(stack, player, pos);
        if (entity != null && player.fishing != null) FishingReal.fishUpEntity(entity, player.fishing, stack, player);
        return entity;
    }
}
