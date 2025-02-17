package com.li64.tide.registries.entities.misc.fishing;

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import com.li64.tide.Tide;
import org.jetbrains.annotations.NotNull;

public class TideFishingHookModel extends EntityModel<TideFishingHookRenderState> {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(Tide.resource("fishing_hook"), "main");

    public TideFishingHookModel(ModelPart root) {
        super(root);
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshDefinition = new MeshDefinition();
        PartDefinition partDefinition = meshDefinition.getRoot();

        partDefinition.addOrReplaceChild("bobber", CubeListBuilder.create()
                .texOffs(0, 10).addBox(-1.0F, -3.0F, -1.0F, 3.0F, 3.0F, 3.0F,
                        new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        partDefinition.addOrReplaceChild("top", CubeListBuilder.create()
                .texOffs(5, 7).addBox(0.0F, -4.0F, 0.5F, 1.0F, 1.0F, 0.0F,
                        new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        partDefinition.addOrReplaceChild("top2", CubeListBuilder.create()
                .texOffs(5, 7).addBox(-0.5F, -4.0F, 0.0F, 1.0F, 1.0F, 0.0F,
                        new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.5F, 0.0F, 0.5F, 0.0F, -1.5708F, 0.0F));

        partDefinition.addOrReplaceChild("hook", CubeListBuilder.create()
                .texOffs(3, 7).addBox(-2.0F, 0.0F, 0.4F, 3.0F, 3.0F, 0.0F,
                        new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        return LayerDefinition.create(meshDefinition, 16, 16);
    }

    @Override
    public void setupAnim(@NotNull TideFishingHookRenderState renderState) { super.setupAnim(renderState); }
}