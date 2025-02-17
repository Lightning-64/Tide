package com.li64.tide.registries.entities.models;

import com.li64.tide.Tide;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.NotNull;

public class AngelfishModel extends EntityModel<LivingEntityRenderState> {
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(Tide.resource("angelfish"), "main");
    private final ModelPart tail;

	public AngelfishModel(ModelPart root) {
		super(root);
        ModelPart body = root.getChild("body");
		this.tail = body.getChild("tail");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(11, 15).addBox(-1.0F, -8.0F, -4.0F, 2.0F, 5.0F, 3.0F, new CubeDeformation(0.0F))
				.texOffs(0, 0).addBox(-1.0F, -7.0F, -5.0F, 2.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(9, 5).addBox(-1.0F, -8.0F, -1.0F, 2.0F, 5.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, -2.0F));

		body.addOrReplaceChild("fin_bottom", CubeListBuilder.create().texOffs(0, 0).addBox(0.0F, -1.0F, -4.0F, 0.0F, 3.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -2.0F, 0.0F, 0.0F, 3.1416F, 0.0F));
		body.addOrReplaceChild("fin_top", CubeListBuilder.create().texOffs(0, 8).addBox(0.0F, -2.0F, -3.0F, 0.0F, 3.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -9.0F, 1.0F, 0.0F, 3.1416F, 0.0F));
		PartDefinition tail = body.addOrReplaceChild("tail", CubeListBuilder.create(), PartPose.offset(0.0F, -5.0F, 4.0F));
		tail.addOrReplaceChild("tail_1", CubeListBuilder.create().texOffs(0, 13).addBox(0.0F, -3.0F, -2.0F, 0.0F, 5.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 3.0F, 0.0F, 3.1416F, 0.0F));

		return LayerDefinition.create(meshdefinition, 32, 32);
	}

	public void setupAnim(@NotNull LivingEntityRenderState renderState) {
		super.setupAnim(renderState);
		float f = renderState.isInWater ? 1.0F : 1.5F;
		this.tail.yRot = -f * 0.35F * Mth.sin(0.6F * renderState.ageInTicks);
	}
}