package com.li64.tide.registries.items;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

public class FishingBobberItem extends Item {
    private final ResourceLocation texture;
    private final MutableComponent translation;

    public FishingBobberItem(ResourceLocation textureLocation, MutableComponent translation, Properties properties) {
        super(properties);
        this.texture = textureLocation;
        this.translation = translation;
    }

    public ResourceLocation getTextureLocation() {
        return texture;
    }

    public MutableComponent getTranslation() {
        return translation;
    }
}
