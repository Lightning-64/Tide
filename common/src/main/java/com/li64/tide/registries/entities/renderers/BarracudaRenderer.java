package com.li64.tide.registries.entities.renderers;

import com.li64.tide.Tide;
import com.li64.tide.registries.entities.fish.Angelfish;
import com.li64.tide.registries.entities.fish.Barracuda;
import com.li64.tide.registries.entities.models.AngelfishModel;
import com.li64.tide.registries.entities.models.BarracudaModel;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class BarracudaRenderer<T extends Barracuda> extends MobRenderer<T, BarracudaModel<T>> {
    private final ResourceLocation texLocation;

    public BarracudaRenderer(EntityRendererProvider.Context context) {
        super(context, new BarracudaModel<>(context.bakeLayer(BarracudaModel.LAYER_LOCATION)), 0.45F);
        texLocation = Tide.resource("textures/entity/fish/barracuda.png");
    }

    @Override
    public ResourceLocation getTextureLocation(T fish) {
        return texLocation;
    }

    @Override
    protected void setupRotations(T fish, PoseStack poseStack, float bob, float yBodyRot, float partialTick) {
        super.setupRotations(fish, poseStack, bob, yBodyRot, partialTick);
        float f = 1.0F;
        float f1 = 1.0F;
        if (!fish.isInWater()) {
            f = 1.3F;
            f1 = 1.7F;
        }

        float f2 = f * 4.3F * Mth.sin(f1 * 0.6F * bob);
        poseStack.mulPose(Axis.YP.rotationDegrees(f2));
        poseStack.translate(0.0F, 0.0F, -0.25F);
        if (!fish.isInWater()) {
            poseStack.translate(0.2F, 0.1F, 0.0F);
            poseStack.mulPose(Axis.ZP.rotationDegrees(90.0F));
        }
    }
}
