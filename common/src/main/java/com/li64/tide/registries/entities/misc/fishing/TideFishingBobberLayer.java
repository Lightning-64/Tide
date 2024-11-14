package com.li64.tide.registries.entities.misc.fishing;

import com.li64.tide.data.rods.CustomRodManager;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.FastColor;
import com.li64.tide.Tide;
import net.minecraft.resources.ResourceLocation;

public class TideFishingBobberLayer extends RenderLayer<TideFishingHook, TideFishingHookModel<TideFishingHook>> {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(Tide.resource("fishing_hook"), "bobber");
    private final TideFishingHookModel<TideFishingHook> model;

    public TideFishingBobberLayer(RenderLayerParent<TideFishingHook, TideFishingHookModel<TideFishingHook>> parent, EntityModelSet modelSet) {
        super(parent);
        this.model = new TideFishingHookModel<>(modelSet.bakeLayer(LAYER_LOCATION));
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource bufferSource, int p_117234_, TideFishingHook hookEntity, float p_117236_, float p_117237_, float p_117238_, float p_117239_, float p_117240_, float p_117241_) {
        this.getParentModel().copyPropertiesTo(this.model);
        this.model.setupAnim(hookEntity, p_117236_, p_117237_, p_117239_, p_117240_, p_117241_);

        ResourceLocation textureLocation = CustomRodManager.getBobberTexture(hookEntity.getBobber());

        VertexConsumer vertexconsumer = bufferSource.getBuffer(RenderType.entityCutoutNoCull(textureLocation));
        this.model.renderToBuffer(poseStack, vertexconsumer, p_117234_, OverlayTexture.NO_OVERLAY, FastColor.ARGB32.color(255, 255, 255 ,255));
    }
}
