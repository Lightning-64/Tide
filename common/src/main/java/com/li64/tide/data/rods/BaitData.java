package com.li64.tide.data.rods;

import com.li64.tide.util.TideUtils;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

public record BaitData(String item, int speedBonus, int luckBonus) {
    public static final Codec<BaitData> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.STRING.fieldOf("item").forGetter(BaitData::item),
            Codec.INT.fieldOf("speed_bonus").forGetter(BaitData::speedBonus),
            Codec.INT.fieldOf("luck_bonus").forGetter(BaitData::luckBonus)
    ).apply(instance, BaitData::new));

    public Item getItem() {
        return TideUtils.itemFromLocation(ResourceLocation.parse(item));
    }
}
