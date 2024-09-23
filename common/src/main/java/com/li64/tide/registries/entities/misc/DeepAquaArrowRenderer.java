package com.li64.tide.registries.entities.misc;

import com.li64.tide.Tide;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class DeepAquaArrowRenderer extends ArrowRenderer<DeepAquaArrow> {
    public static final ResourceLocation DEEP_AQUA_ARROW_LOCATION = Tide.resource("textures/entity/projectiles/deep_aqua_arrow.png");

    public DeepAquaArrowRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    public ResourceLocation getTextureLocation(DeepAquaArrow arrow) {
        return DEEP_AQUA_ARROW_LOCATION;
    }
}