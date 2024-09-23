package com.li64.tide.registries.items;

import com.li64.tide.registries.entities.misc.DeepAquaArrow;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

public class DeepAquaArrowItem extends ArrowItem {
    public DeepAquaArrowItem(Properties properties) {
        super(properties);
    }

    @Override
    public AbstractArrow createArrow(Level level, ItemStack itemStack, LivingEntity entity) {
        return new DeepAquaArrow(level, entity, itemStack);
    }
}
