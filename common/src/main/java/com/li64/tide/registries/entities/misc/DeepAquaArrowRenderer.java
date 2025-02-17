package com.li64.tide.registries.entities.misc;

import com.li64.tide.Tide;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.state.ArrowRenderState;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class DeepAquaArrowRenderer extends ArrowRenderer<DeepAquaArrow, ArrowRenderState> {
    public static final ResourceLocation DEEP_AQUA_ARROW_LOCATION = Tide.resource("textures/entity/projectiles/deep_aqua_arrow.png");

    public DeepAquaArrowRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public @NotNull ArrowRenderState createRenderState() {
        return new ArrowRenderState();
    }

    @Override
    protected @NotNull ResourceLocation getTextureLocation(@NotNull ArrowRenderState arrowRenderState) {
        return DEEP_AQUA_ARROW_LOCATION;
    }
}