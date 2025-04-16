package com.li64.tide.compat.hybrid_aquatic;

import com.li64.tide.registries.entities.misc.fishing.TideFishingHook;
import com.li64.tide.registries.items.TideFishingRodItem;
import dev.hybridlabs.aquatic.enchantment.HybridAquaticEnchantments;
import dev.hybridlabs.aquatic.enchantment.LiveCatchEnchantment;
import dev.hybridlabs.aquatic.entity.HybridAquaticEntityTypes;
import dev.hybridlabs.aquatic.item.HybridAquaticItems;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.animal.WaterAnimal;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.phys.Vec3;

import java.util.HashMap;

public class HybridAquaticCompat {
    public static Entity convertEntity(ItemEntity itemEntity, Player player, TideFishingHook hook) {
        Entity newEntity = null;
        ItemStack hookItem = hook.getHook();

        if (hookItem.is(HybridAquaticItems.INSTANCE.getOMINOUS_HOOK())) {
            var karkinosType = HybridAquaticEntityTypes.INSTANCE.getKARKINOS();
            newEntity = createAndLaunchEntityAtPlayer(karkinosType, player, hook.blockPosition());
            if (newEntity != null) hook.clearHookItem();
        }
        if (hookItem.is(HybridAquaticItems.INSTANCE.getCREEPERMAGNET_HOOK())) {
            var creeperType = EntityType.CREEPER;
            newEntity = createAndLaunchEntityAtPlayer(creeperType, player, hook.blockPosition());
            if (newEntity != null) hook.clearHookItem();
        }
        if (hook.getRod() != null && !hook.getRod().isEmpty()) {
            if (EnchantmentHelper.getItemEnchantmentLevel(HybridAquaticEnchantments.INSTANCE.getLIVECATCH(), hook.getRod()) > 0) {
                HashMap<Item, EntityType<? extends WaterAnimal>> ITEM_TO_ENTITY = LiveCatchEnchantment.Companion.getITEM_TO_ENTITYTYPE();
                var entityType = ITEM_TO_ENTITY.get(itemEntity.getItem().getItem());
                if (entityType != null) newEntity = createAndLaunchEntityAtPlayer(entityType, player, hook.blockPosition());
            }
        }

        return newEntity == null ? itemEntity : newEntity;
    }

    private static Entity createAndLaunchEntityAtPlayer(EntityType<?> entityType, Player player, BlockPos pos) {
        if (player.level() instanceof ServerLevel serverLevel) {
            Entity entity = entityType.spawn(serverLevel, pos, MobSpawnType.MOB_SUMMONED);
            if (entity == null) return null;

            double modifier = 0.15;
            Vec3 vecBetween = player.position().subtract(Vec3.atCenterOf(pos));
            Vec3 vecBetweenMod = vecBetween.multiply(modifier, modifier, modifier);
            var yOffset = Math.sqrt(Math.sqrt(Math.pow(vecBetween.x, 2)
                    + Math.pow(vecBetween.y, 2)
                    + Math.pow(vecBetween.z, 2))) * 0.08;
            entity.setDeltaMovement(
                    vecBetweenMod.x,
                    vecBetweenMod.y + yOffset,
                    vecBetweenMod.z
            );
            return entity;
        }
        return null;
    }
}
