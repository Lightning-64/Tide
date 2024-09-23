package com.li64.tide.registries.entities.renderers;

import com.li64.tide.Tide;
import com.li64.tide.registries.entities.fish.Catfish;
import com.li64.tide.registries.entities.fish.Guppy;
import com.li64.tide.registries.entities.models.CatfishModel;
import com.li64.tide.registries.entities.models.GuppyModel;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class CatfishRenderer<T extends Catfish> extends MobRenderer<T, CatfishModel<T>> {
    private final ResourceLocation texLocation;

    public CatfishRenderer(EntityRendererProvider.Context context) {
        super(context, new CatfishModel<>(context.bakeLayer(CatfishModel.LAYER_LOCATION)), 0.3F);
        texLocation = Tide.resource("textures/entity/fish/catfish.png");
    }

    @Override
    public ResourceLocation getTextureLocation(T fish) {
        return texLocation;
    }

    @Override
    protected void setupRotations(T fish, PoseStack poseStack, float rx, float ry, float rz, float idk) {
        super.setupRotations(fish, poseStack, rx, ry, rz, idk);
        float f = 4.3F * Mth.sin(0.6F * rx);
        poseStack.mulPose(Axis.YP.rotationDegrees(f));
        if (!fish.isInWater()) {
            poseStack.translate(0.1F, 0.1F, -0.1F);
            poseStack.mulPose(Axis.ZP.rotationDegrees(90.0F));
        }
    }
}
