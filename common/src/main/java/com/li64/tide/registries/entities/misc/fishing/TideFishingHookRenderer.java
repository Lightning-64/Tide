package com.li64.tide.registries.entities.misc.fishing;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FastColor;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.FishingRodItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import com.li64.tide.Tide;

import java.awt.*;

public class TideFishingHookRenderer extends EntityRenderer<TideFishingHook> implements RenderLayerParent<TideFishingHook, TideFishingHookModel<TideFishingHook>> {
    private final TideFishingHookModel<TideFishingHook> model;
    private final TideFishingBobberLayer bobberLayer;
    private static final ResourceLocation HOOK_TEX_LOCATION = Tide.resource("textures/entity/fishing_hook/fishing_hook.png");

    public TideFishingHookRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.model = new TideFishingHookModel<>(context.bakeLayer(TideFishingHookModel.LAYER_LOCATION));
        this.bobberLayer = new TideFishingBobberLayer(this, context.getModelSet());
        this.shadowRadius = 0.1f;
    }

    @Override
    public void render(TideFishingHook hookEntity, float entityYaw, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
        Player player = hookEntity.getPlayerOwner();
        if (player == null) return;

        poseStack.pushPose();
        poseStack.pushPose();

        poseStack.translate((1f / 16f) / 2f, 0, (1f / 16f) / 2f);
        poseStack.mulPose(Axis.YP.rotationDegrees(180f - hookEntity.getInitialYaw()));

        poseStack.scale(-1.0F, -1.0F, 1.0F);

        model.setupAnim(hookEntity, partialTick, 0.0F, -0.1F, 0.0F, 0.0F);
        VertexConsumer vertexConsumer = bufferSource.getBuffer(RenderType.entityCutoutNoCull(getTextureLocation(hookEntity)));

        model.renderToBuffer(poseStack, vertexConsumer, packedLight, OverlayTexture.NO_OVERLAY, FastColor.ARGB32.color(255, 255, 255, 255));
        bobberLayer.render(poseStack, bufferSource, packedLight, hookEntity, 0, 0, 0, 0, 0, 0);

        poseStack.popPose();

        renderConnectingString(hookEntity, partialTick, poseStack, bufferSource, player);

        poseStack.popPose();

        super.render(hookEntity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
    }

    private void renderConnectingString(TideFishingHook hookEntity, float partialTick, PoseStack poseStack, MultiBufferSource multiBufferSource, Player player) {
        int i = player.getMainArm() == HumanoidArm.RIGHT ? 1 : -1;
        ItemStack itemstack = player.getMainHandItem();
        if (!(itemstack.getItem() instanceof FishingRodItem)) {
            i = -i;
        }

        float f = player.getAttackAnim(partialTick);
        float f1 = Mth.sin(Mth.sqrt(f) * (float)Math.PI);
        float f2 = Mth.lerp(partialTick, player.yBodyRotO, player.yBodyRot) * ((float)Math.PI / 180F);
        double d0 = Mth.sin(f2);
        double d1 = Mth.cos(f2);
        double d2 = (double)i * 0.35D;
        double d3 = 0.8D;
        double d4;
        double d5;
        double d6;
        float f3;

        if (this.entityRenderDispatcher.options.getCameraType().isFirstPerson() && player == Minecraft.getInstance().player) {
            double d7 = 960.0D / (double) this.entityRenderDispatcher.options.fov().get();
            Vec3 vec3 = this.entityRenderDispatcher.camera.getNearPlane().getPointOnPlane((float)i * 0.525F, -0.1F);
            vec3 = vec3.scale(d7);
            vec3 = vec3.yRot(f1 * 0.5F);
            vec3 = vec3.xRot(-f1 * 0.7F);
            d4 = Mth.lerp(partialTick, player.xo, player.getX()) + vec3.x;
            d5 = Mth.lerp(partialTick, player.yo, player.getY()) + vec3.y;
            d6 = Mth.lerp(partialTick, player.zo, player.getZ()) + vec3.z;
            f3 = player.getEyeHeight();
        } else {
            d4 = Mth.lerp(partialTick, player.xo, player.getX()) - d1 * d2 - d0 * 0.8D;
            d5 = player.yo + (double)player.getEyeHeight() + (player.getY() - player.yo) * (double)partialTick - 0.45D;
            d6 = Mth.lerp(partialTick, player.zo, player.getZ()) - d0 * d2 + d1 * 0.8D;
            f3 = player.isCrouching() ? -0.1875F : 0.0F;
        }

        double d9 = Mth.lerp(partialTick, hookEntity.xo, hookEntity.getX());
        double d10 = Mth.lerp(partialTick, hookEntity.yo, hookEntity.getY()) + 0.25D;
        double d8 = Mth.lerp(partialTick, hookEntity.zo, hookEntity.getZ());

        float f4 = (float)(d4 - d9);
        float f5 = (float)(d5 - d10) + f3;
        float f6 = (float)(d6 - d8);

        VertexConsumer vertexconsumer1 = multiBufferSource.getBuffer(RenderType.lineStrip());
        PoseStack.Pose posestack$pose1 = poseStack.last();

        for (int k = 0; k <= 16; ++k) {
            BlockPos vertexPos = new BlockPos((int) d9, (int) d10, (int) d8);

            stringVertex(f4, f5, f6, vertexconsumer1, posestack$pose1, fraction(k, 16),
                    fraction(k + 1, 16), vertexPos, hookEntity.level(), hookEntity.getLineModifier().getLineColor());
        }
    }

    private static float fraction(int a, int b) {
        return (float)a / (float)b;
    }

    private static void stringVertex(float x, float y, float z, VertexConsumer vertexConsumer, PoseStack.Pose pose, float frac1, float frac2, BlockPos hookPos, Level level, String colorHex) {
        float f = x * frac1;
        float f1 = y * (frac1 * frac1 + frac1) * 0.5F + 0.25F;
        float f2 = z * frac1;
        float f3 = x * frac2 - f;
        float f4 = y * (frac2 * frac2 + frac2) * 0.5F + 0.25F - f1;
        float f5 = z * frac2 - f2;
        float f6 = Mth.sqrt(f3 * f3 + f4 * f4 + f5 * f5);

        f3 /= f6;
        f4 /= f6;
        f5 /= f6;

        Color color = Color.decode(colorHex);

//        BlockPos vertexPos = new BlockPos(Mth.floor(hookPos.getX() + f),
//                Mth.floor(hookPos.getY() + f1), Mth.floor(hookPos.getZ() + f2));
//
//        int vertexBrightness = (int) Mth.lerp(level.dimensionType().ambientLight(),
//                level.getRawBrightness(vertexPos, level.getSkyDarken()), 15f);
//        float colorBrightness = Mth.clamp(vertexBrightness / 15f, 0.05f, 1f);

        float colorBrightness = 1.0f;

        int r = (int) (color.getRed() * colorBrightness);
        int g = (int) (color.getGreen() * colorBrightness);
        int b = (int) (color.getBlue() * colorBrightness);

        vertexConsumer.addVertex(pose.pose(), f, f1, f2)
                .setColor(FastColor.ARGB32.color(255, r, g, b))
                .setNormal(pose, f3, f4, f5);
    }

    @Override
    public TideFishingHookModel<TideFishingHook> getModel() {
        return this.model;
    }

    @Override
    public ResourceLocation getTextureLocation(TideFishingHook hookEntity) {
        if (!allowModifiers()) return HOOK_TEX_LOCATION;
        return hookEntity.getHookModifier().getTextureLocation();
    }

    /** Override this to disable bobber, hook, and line modifiers */
    protected boolean allowModifiers() { return true; }
}
