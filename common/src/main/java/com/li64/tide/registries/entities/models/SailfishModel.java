package com.li64.tide.registries.entities.models;

import com.li64.tide.Tide;
import com.li64.tide.registries.entities.fish.Sailfish;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.NotNull;

public class SailfishModel extends EntityModel<LivingEntityRenderState> {
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(Tide.resource("sailfish"), "main");
    private final ModelPart tail;

	public SailfishModel(ModelPart root) {
		super(root);
        ModelPart body = root.getChild("body");
		this.tail = body.getChild("tail");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, -5.0F, -6.0F, 2.0F, 4.0F, 3.0F, new CubeDeformation(0.0F))
				.texOffs(15, 6).addBox(-1.0F, -4.0F, -7.0F, 2.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(15, 0).addBox(-0.5F, -4.0F, -12.0F, 1.0F, 1.0F, 5.0F, new CubeDeformation(0.0F))
				.texOffs(0, 0).addBox(-1.0F, -5.0F, -3.0F, 2.0F, 4.0F, 11.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

		body.addOrReplaceChild("fin_right", CubeListBuilder.create().texOffs(0, 11).addBox(0.0F, -2.0F, -4.0F, 0.0F, 2.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, -2.0F, 1.0F, 3.1416F, 0.0F, 0.6981F));
		body.addOrReplaceChild("fin_left", CubeListBuilder.create().texOffs(0, 13).addBox(0.0F, -2.0F, -4.0F, 0.0F, 2.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0F, -2.0F, 1.0F, 0.0F, 0.0F, 2.4435F));
		body.addOrReplaceChild("fin_top", CubeListBuilder.create().texOffs(0, 3).addBox(0.0F, -4.0F, -6.0F, 0.0F, 4.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -5.0F, 1.0F, 0.0F, 3.1416F, 0.0F));

		PartDefinition tail = body.addOrReplaceChild("tail", CubeListBuilder.create(), PartPose.offset(0.0F, -3.0F, 8.0F));
		tail.addOrReplaceChild("tail_1", CubeListBuilder.create().texOffs(0, 3).addBox(0.0F, -2.0F, -4.0F, 0.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 3.1416F, 0.0F));

		return LayerDefinition.create(meshdefinition, 32, 32);
	}

	public void setupAnim(@NotNull LivingEntityRenderState renderState) {
		super.setupAnim(renderState);
		float f = renderState.isInWater ? 1.0F : 1.5F;
		this.tail.yRot = -f * 0.45F * Mth.sin(0.6F * renderState.ageInTicks);
	}
}