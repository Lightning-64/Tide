package com.li64.tide.registries.entities.renderers;

import com.li64.tide.Tide;
import com.li64.tide.registries.entities.fish.Barracuda;
import com.li64.tide.registries.entities.models.BarracudaModel;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.NotNull;

public class BarracudaRenderer<T extends Barracuda> extends MobRenderer<T, LivingEntityRenderState, BarracudaModel> {
    private final ResourceLocation texLocation;

    public BarracudaRenderer(EntityRendererProvider.Context context) {
        super(context, new BarracudaModel(context.bakeLayer(BarracudaModel.LAYER_LOCATION)), 0.45F);
        texLocation = Tide.resource("textures/entity/fish/barracuda.png");
    }

    @Override
    public @NotNull LivingEntityRenderState createRenderState() {
        return new LivingEntityRenderState();
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull LivingEntityRenderState renderState) {
        return texLocation;
    }

    @Override
    protected void setupRotations(@NotNull LivingEntityRenderState renderState, @NotNull PoseStack poseStack, float bodyRot, float scale) {
        super.setupRotations(renderState, poseStack, bodyRot, scale);
        float f = 4.3F * Mth.sin(0.6F * renderState.ageInTicks);
        poseStack.mulPose(Axis.YP.rotationDegrees(f));
        poseStack.translate(0.0F, 0.0F, -0.25F);
        if (!renderState.isInWater) {
            poseStack.translate(0.2F, 0.1F, 0.0F);
            poseStack.mulPose(Axis.ZP.rotationDegrees(90.0F));
        }
    }
}
