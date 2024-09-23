package com.li64.tide.registries.entities.models;

import com.li64.tide.Tide;
import com.li64.tide.registries.entities.fish.Angelfish;
import com.li64.tide.registries.entities.fish.Barracuda;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;

public class BarracudaModel<T extends Barracuda> extends EntityModel<T> {
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(Tide.resource("barracuda"), "main");
	private final ModelPart body;
    private final ModelPart back;
	private final ModelPart tail;

	public BarracudaModel(ModelPart root) {
		this.body = root.getChild("body");
		this.back = body.getChild("back");
		this.tail = back.getChild("tail");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(0.0F, 21.0F, 1.0F));
		PartDefinition front = body.addOrReplaceChild("front", CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, -2.0F, -11.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(0, 13).addBox(-1.0F, -1.0F, -13.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(13, 0).addBox(-1.0F, -2.0F, -12.0F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(0, 6).addBox(-1.0F, 1.0F, -13.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(0, 13).addBox(-1.0F, -2.0F, -9.0F, 2.0F, 4.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -3.0F, 3.0F));

		front.addOrReplaceChild("fin_top", CubeListBuilder.create().texOffs(0, 17).addBox(0.0F, -2.0F, -1.0F, 0.0F, 2.0F, 8.0F, new CubeDeformation(0.0F))
				.texOffs(12, 12).addBox(0.0F, 4.0F, -1.0F, 0.0F, 1.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -2.0F, -2.0F, 0.0F, 3.1416F, 0.0F));
		front.addOrReplaceChild("fin_right", CubeListBuilder.create().texOffs(12, 8).addBox(0.0F, -2.0F, -4.0F, 0.0F, 2.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, 0.0F, -5.0F, 3.1416F, 0.0F, 0.6981F));
		front.addOrReplaceChild("fin_left", CubeListBuilder.create().texOffs(12, 10).addBox(0.0F, -2.0F, -4.0F, 0.0F, 2.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0F, 0.0F, -5.0F, 0.0F, 0.0F, 2.4435F));

		PartDefinition back = body.addOrReplaceChild("back", CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, -2.0F, -1.0F, 2.0F, 4.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -3.0F, 3.0F));
		back.addOrReplaceChild("fin_bottom", CubeListBuilder.create().texOffs(12, 6).addBox(0.0F, -2.0F, -1.0F, 0.0F, 1.0F, 9.0F, new CubeDeformation(0.0F))
				.texOffs(12, 4).addBox(0.0F, -8.0F, -1.0F, 0.0F, 2.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 4.0F, 7.0F, 0.0F, 3.1416F, 0.0F));

		PartDefinition tail = back.addOrReplaceChild("tail", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 8.0F));
		tail.addOrReplaceChild("tail_1", CubeListBuilder.create().texOffs(16, 19).addBox(0.0F, -3.0F, -6.0F, 0.0F, 6.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 3.1416F, 0.0F));

		return LayerDefinition.create(meshdefinition, 32, 32);
	}

	@Override
	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		float f = 1.0f;
		float f1 = 1.0f;
		if (!entity.isInWater()) {
			f = 1.3f;
			f1 = 1.7f;
		}

		this.back.yRot = -f * 0.20f * Mth.sin(f1 * 0.6f * ageInTicks);
		this.tail.yRot = -f * 0.15f * Mth.sin(f1 * 0.6f * ageInTicks);
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float r, float g, float b, float a) {
		body.render(poseStack, vertexConsumer, packedLight, packedOverlay, r, g, b, a);
	}
}