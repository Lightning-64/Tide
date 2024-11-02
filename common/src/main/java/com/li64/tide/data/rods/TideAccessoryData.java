package com.li64.tide.data.rods;

import com.li64.tide.Tide;
import com.li64.tide.data.TideDataComponents;
import com.li64.tide.registries.TideItems;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentSerialization;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;

/**
 * TideAccessoryData holds component data about a bobber, hook, or line modifier that can
 * be applied to a fishing rod.
 * @param translationKey What the text below the fishing rod should say to describe the
 *                    accessory. For example, putting Component.translatable("bobber.tide.red")
 *                    here would display "Red Bobber."
 * @param entityModifier For bobbers and hooks, this is the path to the texture that will be
 *                       displayed on the fishing bobber entity (such as "textures/item/white_fishing_bobber.png").
 *                       For fishing lines, this is used as the field to put the color hex
 *                       value of the line (such as "#362c1e")
 */
public record TideAccessoryData(Component translationKey, String entityModifier) {
    public static final Codec<TideAccessoryData> CODEC = RecordCodecBuilder.create((instance) -> instance.group(
            ComponentSerialization.FLAT_CODEC.fieldOf("translationKey").forGetter(TideAccessoryData::translationKey),
            Codec.STRING.fieldOf("entityModifier").forGetter(TideAccessoryData::entityModifier)
    ).apply(instance, TideAccessoryData::new));

    public static final StreamCodec<RegistryFriendlyByteBuf, TideAccessoryData> STREAM_CODEC = StreamCodec.composite(
            ComponentSerialization.STREAM_CODEC, TideAccessoryData::translationKey,
            ByteBufCodecs.STRING_UTF8, TideAccessoryData::entityModifier,
            TideAccessoryData::new
    );

    public ResourceLocation getTextureLocation() {
        if (entityModifier.isEmpty())return TideItems.RED_FISHING_BOBBER.getDefaultInstance()
                .get(TideDataComponents.TIDE_ACCESSORY_DATA).getTextureLocation();
        return Tide.resource(entityModifier);
    }
}
