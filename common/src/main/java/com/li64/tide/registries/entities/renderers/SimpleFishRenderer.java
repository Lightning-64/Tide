package com.li64.tide.registries.entities.renderers;

import com.li64.tide.Tide;
import com.li64.tide.registries.entities.models.SimpleFishModel;
import com.li64.tide.registries.entities.util.AbstractTideFish;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.NotNull;

public class SimpleFishRenderer<T extends AbstractTideFish> extends MobRenderer<T, LivingEntityRenderState, SimpleFishModel> {
    private final ResourceLocation texLocation;

    public SimpleFishRenderer(String name, SimpleFishModel.Variant variant, EntityRendererProvider.Context context) {
        super(context, new SimpleFishModel(context.bakeLayer(variant.modelLocation())), 0.3F);
        texLocation = Tide.resource("textures/entity/fish/" + name + ".png");
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull LivingEntityRenderState renderState) {
        return texLocation;
    }

    @Override
    public @NotNull LivingEntityRenderState createRenderState() {
        return new LivingEntityRenderState();
    }

    @Override
    protected void setupRotations(@NotNull LivingEntityRenderState renderState, @NotNull PoseStack poseStack, float bodyRot, float scale) {
        super.setupRotations(renderState, poseStack, bodyRot, scale);
        float f = 4.3F * Mth.sin(0.6F * renderState.ageInTicks);
        poseStack.mulPose(Axis.YP.rotationDegrees(f));
        if (!renderState.isInWater) {
            poseStack.translate(0.1F, 0.1F, -0.1F);
            poseStack.mulPose(Axis.ZP.rotationDegrees(90.0F));
        }
    }
}
