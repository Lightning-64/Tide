package com.li64.tide.registries.entities.misc.fishing;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import com.li64.tide.Tide;

public class TideFishingHookModel<T extends TideFishingHook> extends EntityModel<T> {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(Tide.resource("fishing_hook"), "main");
    private final ModelPart bobber;
    private final ModelPart top;
    private final ModelPart top2;
    private final ModelPart hook;

    public TideFishingHookModel(ModelPart root) {
        this.bobber = root.getChild("bobber");
        this.top = root.getChild("top");
        this.top2 = root.getChild("top2");
        this.hook = root.getChild("hook");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshDefinition = new MeshDefinition();
        PartDefinition partDefinition = meshDefinition.getRoot();

        PartDefinition base = partDefinition.addOrReplaceChild("bobber", CubeListBuilder.create()
                .texOffs(0, 10).addBox(-1.0F, -3.0F, -1.0F, 3.0F, 3.0F, 3.0F,
                        new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition top = partDefinition.addOrReplaceChild("top", CubeListBuilder.create()
                .texOffs(5, 7).addBox(0.0F, -4.0F, 0.5F, 1.0F, 1.0F, 0.0F,
                        new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition top2 = partDefinition.addOrReplaceChild("top2", CubeListBuilder.create()
                .texOffs(5, 7).addBox(-0.5F, -4.0F, 0.0F, 1.0F, 1.0F, 0.0F,
                        new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.5F, 0.0F, 0.5F, 0.0F, -1.5708F, 0.0F));

        PartDefinition hook = partDefinition.addOrReplaceChild("hook", CubeListBuilder.create()
                .texOffs(3, 7).addBox(-2.0F, 0.0F, 0.4F, 3.0F, 3.0F, 0.0F,
                        new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        return LayerDefinition.create(meshDefinition, 16, 16);
    }

    @Override
    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {}

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, int color) {
        bobber.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
        top.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
        top2.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
        hook.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
    }
}