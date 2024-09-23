package com.li64.tide.registries.entities.renderers;

import com.li64.tide.Tide;
import com.li64.tide.registries.entities.fish.Angelfish;
import com.li64.tide.registries.entities.fish.Sailfish;
import com.li64.tide.registries.entities.models.AngelfishModel;
import com.li64.tide.registries.entities.models.SailfishModel;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class SailfishRenderer<T extends Sailfish> extends MobRenderer<T, SailfishModel<T>> {
    private final ResourceLocation texLocation;

    public SailfishRenderer(EntityRendererProvider.Context context) {
        super(context, new SailfishModel<>(context.bakeLayer(SailfishModel.LAYER_LOCATION)), 0.35F);
        texLocation = Tide.resource("textures/entity/fish/sailfish.png");
    }

    @Override
    public ResourceLocation getTextureLocation(T fish) {
        return texLocation;
    }

    @Override
    protected void setupRotations(T fish, PoseStack poseStack, float rx, float ry, float rz) {
        super.setupRotations(fish, poseStack, rx, ry, rz);
        float f = 4.3F * Mth.sin(0.6F * rx);
        poseStack.mulPose(Axis.YP.rotationDegrees(f));
        if (!fish.isInWater()) {
            poseStack.translate(0.1F, 0.1F, -0.1F);
            poseStack.mulPose(Axis.ZP.rotationDegrees(90.0F));
        }
    }
}
