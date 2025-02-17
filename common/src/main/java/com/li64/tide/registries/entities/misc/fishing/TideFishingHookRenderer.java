package com.li64.tide.registries.entities.misc.fishing;

import com.li64.tide.registries.items.FishingHookItem;
import com.li64.tide.registries.items.FishingLineItem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.FishingHookRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.ARGB;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.phys.Vec3;
import com.li64.tide.Tide;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

public class TideFishingHookRenderer extends EntityRenderer<TideFishingHook, TideFishingHookRenderState> implements RenderLayerParent<TideFishingHookRenderState, TideFishingHookModel> {
    private final TideFishingHookModel model;
    private final TideFishingBobberLayer bobberLayer;
    private static final ResourceLocation HOOK_TEX_LOCATION = Tide.resource("textures/entity/fishing_hook/fishing_hook.png");

    public TideFishingHookRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.model = new TideFishingHookModel(context.bakeLayer(TideFishingHookModel.LAYER_LOCATION));
        this.bobberLayer = new TideFishingBobberLayer(this, context.getModelSet(), Minecraft.getInstance().getItemRenderer());
        this.shadowRadius = 0.1f;
    }

    @Override
    public void render(@NotNull TideFishingHookRenderState renderState, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        poseStack.pushPose();
        poseStack.pushPose();

        poseStack.translate((1f / 16f) / 2f, 0, (1f / 16f) / 2f);
        poseStack.mulPose(Axis.YP.rotationDegrees(180f - renderState.initialYaw));
        poseStack.scale(-1.0F, -1.0F, 1.0F);

        model.setupAnim(renderState);
        VertexConsumer vertexConsumer = buffer.getBuffer(RenderType.entityCutoutNoCull(getTextureLocation(renderState)));

        model.renderToBuffer(poseStack, vertexConsumer, packedLight, OverlayTexture.NO_OVERLAY, ARGB.color(255, 255, 255, 255));
        bobberLayer.render(poseStack, buffer, packedLight, renderState, 0, 0);
        poseStack.popPose();

        renderConnectingString(renderState, poseStack, buffer, renderState.partialTick);
        poseStack.popPose();

        super.render(renderState, poseStack, buffer, packedLight);
    }

    private void renderConnectingString(TideFishingHookRenderState renderState, PoseStack poseStack, MultiBufferSource buffer, float partialTick) {
        float x = (float) renderState.lineOriginOffset.x;
        float y = (float) renderState.lineOriginOffset.y;
        float z = (float) renderState.lineOriginOffset.z;

        VertexConsumer vertexConsumer = buffer.getBuffer(RenderType.lineStrip());
        PoseStack.Pose pose = poseStack.last();

        for (int k = 0; k <= 16; ++k) {
            stringVertex(x, y, z, vertexConsumer, pose, fraction(k), fraction(k + 1),
                    FishingLineItem.getColor(renderState.line), partialTick, renderState.ownerPos);
        }
    }

    private static float fraction(int a) {
        return (float) a / (float) 16;
    }

    private static void stringVertex(float x, float y, float z, VertexConsumer vertexConsumer, PoseStack.Pose pose, float frac1, float frac2, String colorHex, float partialTick, BlockPos ownerPos) {
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
        
        ClientLevel level = Minecraft.getInstance().level;
        if (level == null) return;

        float skyDarken = (1 - level.getSkyDarken(partialTick)) * 15;
        float blockBrightness = level.getBrightness(LightLayer.BLOCK, ownerPos);
        float skyBrightness = level.getBrightness(LightLayer.SKY, ownerPos) - skyDarken + 1;

        float colorBrightness = Tide.CONFIG.general.defaultLineColor ? 0.0f : Mth.clamp(
                Math.max(blockBrightness, skyBrightness) / 15f,
                level.dimensionType().ambientLight(), 1f);

        int r = (int) (color.getRed() * colorBrightness);
        int g = (int) (color.getGreen() * colorBrightness);
        int b = (int) (color.getBlue() * colorBrightness);

        vertexConsumer.addVertex(pose.pose(), f, f1, f2)
                .setColor(ARGB.color(255, r, g, b))
                .setNormal(pose, f3, f4, f5);
    }

    private Vec3 getPlayerHandPos(Player player, float handAngle, float partialTick) {
        int i = FishingHookRenderer.getHoldingArm(player) == HumanoidArm.RIGHT ? 1 : -1;
        if (this.entityRenderDispatcher.options.getCameraType().isFirstPerson() && player == Minecraft.getInstance().player) {
            double d4 = 960.0 / (double) this.entityRenderDispatcher.options.fov().get();
            Vec3 vec3 = this.entityRenderDispatcher
                    .camera
                    .getNearPlane()
                    .getPointOnPlane((float)i * 0.525F, -0.1F)
                    .scale(d4)
                    .yRot(handAngle * 0.5F)
                    .xRot(-handAngle * 0.7F);
            return player.getEyePosition(partialTick).add(vec3);
        } else {
            float f = Mth.lerp(partialTick, player.yBodyRotO, player.yBodyRot) * (float) (Math.PI / 180.0);
            double d0 = Mth.sin(f);
            double d1 = Mth.cos(f);
            float f1 = player.getScale();
            double d2 = (double)i * 0.35 * (double)f1;
            double d3 = 0.8 * (double)f1;
            float f2 = player.isCrouching() ? -0.1875F : 0.0F;
            return player.getEyePosition(partialTick).add(-d1 * d2 - d0 * d3, (double)f2 - 0.45 * (double)f1, -d0 * d2 + d1 * d3);
        }
    }

    @Override
    public @NotNull TideFishingHookModel getModel() {
        return this.model;
    }

    @Override
    public @NotNull TideFishingHookRenderState createRenderState() {
        return new TideFishingHookRenderState();
    }

    @Override
    public void extractRenderState(@NotNull TideFishingHook hook, @NotNull TideFishingHookRenderState state, float partialTick) {
        super.extractRenderState(hook, state, partialTick);
        state.bobber = hook.getBobber();
        state.line = hook.getLine();
        state.hook = hook.getHook();
        state.initialYaw = hook.getInitialYaw();
        state.partialTick = partialTick;

        Player player = hook.getPlayerOwner();
        if (player == null) {
            state.lineOriginOffset = Vec3.ZERO;
            state.ownerPos = BlockPos.ZERO;
        } else {
            float f = player.getAttackAnim(partialTick);
            float f1 = Mth.sin(Mth.sqrt(f) * (float) Math.PI);
            Vec3 vec3 = this.getPlayerHandPos(player, f1, partialTick);
            Vec3 vec31 = hook.getPosition(partialTick).add(0.0, 0.25, 0.0);

            state.lineOriginOffset = vec3.subtract(vec31);
            state.ownerPos = player.blockPosition();
        }
    }

    public @NotNull ResourceLocation getTextureLocation(@NotNull TideFishingHookRenderState renderState) {
        if (!allowModifiers()) return HOOK_TEX_LOCATION;
        return FishingHookItem.getTexture(renderState.hook);
    }

    /** Override this to disable bobber, hook, and line modifiers */
    protected boolean allowModifiers() { return true; }
}
