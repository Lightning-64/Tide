package com.li64.tide.registries.items;

import com.li64.tide.registries.entities.misc.DeepAquaArrow;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class DeepAquaArrowItem extends ArrowItem {
    public DeepAquaArrowItem(Properties properties) {
        super(properties);
    }

    @Override
    public @NotNull AbstractArrow createArrow(@NotNull Level level, ItemStack itemStack, @NotNull LivingEntity entity, @Nullable ItemStack itemStack1) {
        return new DeepAquaArrow(level, entity, itemStack.copyWithCount(1), itemStack1);
    }
}
