package com.li64.tide.registries.entities.misc.fishing;

import com.li64.tide.registries.items.FishingBobberItem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import com.li64.tide.Tide;
import net.minecraft.resources.ResourceLocation;

import net.minecraft.world.item.ItemDisplayContext;
import org.jetbrains.annotations.NotNull;

public class TideFishingBobberLayer extends RenderLayer<TideFishingHook, TideFishingHookModel<TideFishingHook>> {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(Tide.resource("fishing_hook"), "bobber");
    private final TideFishingHookModel<TideFishingHook> model;
    private final ItemRenderer itemRenderer;

    public TideFishingBobberLayer(RenderLayerParent<TideFishingHook, TideFishingHookModel<TideFishingHook>> parent, EntityModelSet modelSet, ItemRenderer itemRenderer) {
        super(parent);
        this.itemRenderer = itemRenderer;
        this.model = new TideFishingHookModel<>(modelSet.bakeLayer(LAYER_LOCATION));
    }

    @Override
    public void render(@NotNull PoseStack poseStack, @NotNull MultiBufferSource buffer, int packedLight, @NotNull TideFishingHook hookEntity, float limbSwing, float limbSwingAmount, float var1, float ageInTicks, float netHeadYaw, float netHeadPitch) {
        this.getParentModel().copyPropertiesTo(this.model);
        this.model.setupAnim(hookEntity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, netHeadPitch);

        // Render bobber
        if (FishingBobberItem.renderItemModel(hookEntity.getBobber())) {
            poseStack.pushPose();
            poseStack.scale(0.5f, 0.5f, 0.5f);
            poseStack.translate(0.03f, -0.22f, 0.0f);
            poseStack.mulPose(Axis.ZP.rotationDegrees(180.0f));
            this.itemRenderer.renderStatic(hookEntity.getBobber(),
                    ItemDisplayContext.FIXED, packedLight, OverlayTexture.NO_OVERLAY,
                    poseStack, buffer, hookEntity.level(), hookEntity.getId());
            poseStack.popPose();
        } else {
            ResourceLocation textureLocation = FishingBobberItem.getTexture(hookEntity.getBobber());
            VertexConsumer consumer = buffer.getBuffer(RenderType.entityCutoutNoCull(textureLocation));
            this.model.renderToBuffer(poseStack, consumer, packedLight, OverlayTexture.NO_OVERLAY, 1.0f, 1.0f, 1.0f, 1.0f);
        }
    }
}
