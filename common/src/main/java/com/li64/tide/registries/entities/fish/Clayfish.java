package com.li64.tide.registries.entities.fish;

import com.li64.tide.registries.TideItems;
import com.li64.tide.registries.entities.util.AbstractTideFish;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class Clayfish extends AbstractTideFish {
    public Clayfish(EntityType<? extends AbstractTideFish> entityType, Level level) {
        super(entityType, level);
    }

    public static AttributeSupplier.@NotNull Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 3.0)
                .add(Attributes.MOVEMENT_SPEED, 1.0f);
    }

    @Override
    public @NotNull ItemStack getBucketItemStack() {
        return TideItems.CLAYFISH_BUCKET.getDefaultInstance();
    }

    @Override
    public Item getFishItem() {
        return TideItems.CLAYFISH;
    }
}
