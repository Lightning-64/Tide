package com.li64.tide.data.rods;

import com.li64.tide.Tide;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.Optional;

public record AccessoryData(Holder<Item> item, Optional<String> modelPath, Optional<String> color, Optional<Boolean> renderItem) {
    public static final Codec<AccessoryData> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Item.CODEC.fieldOf("item").forGetter(AccessoryData::item),
            Codec.STRING.optionalFieldOf("model_path").forGetter(AccessoryData::modelPath),
            Codec.STRING.optionalFieldOf("color").forGetter(AccessoryData::color),
            Codec.BOOL.optionalFieldOf("render_item_model").forGetter(AccessoryData::renderItem)
    ).apply(instance, AccessoryData::new));

    public static AccessoryData get(ItemStack stack) {
        return Tide.ACCESSORY_DATA.get().stream()
                .filter(data -> stack.is(data.item()))
                .findFirst().orElse(null);
    }

    public static MutableComponent getTranslation(ItemStack stack) {
        return Component.translatable("accessory." + stack.getItem().getDescriptionId());
    }
}
