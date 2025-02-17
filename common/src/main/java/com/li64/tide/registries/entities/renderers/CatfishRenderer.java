package com.li64.tide.registries.entities.renderers;

import com.li64.tide.Tide;
import com.li64.tide.registries.entities.fish.Catfish;
import com.li64.tide.registries.entities.models.CatfishModel;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.NotNull;

public class CatfishRenderer<T extends Catfish> extends MobRenderer<T, LivingEntityRenderState, CatfishModel> {
    private final ResourceLocation texLocation;

    public CatfishRenderer(EntityRendererProvider.Context context) {
        super(context, new CatfishModel(context.bakeLayer(CatfishModel.LAYER_LOCATION)), 0.3F);
        texLocation = Tide.resource("textures/entity/fish/catfish.png");
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
        if (!renderState.isInWater) {
            poseStack.translate(0.1F, 0.1F, -0.1F);
            poseStack.mulPose(Axis.ZP.rotationDegrees(90.0F));
        }
    }
}
