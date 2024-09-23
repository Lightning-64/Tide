package com.li64.tide.registries.entities.misc;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;

public class LootCrateRenderer extends EntityRenderer<LootCrateEntity> {
    private final BlockRenderDispatcher dispatcher;

    public LootCrateRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.shadowRadius = 0.5F;
        this.dispatcher = context.getBlockRenderDispatcher();
    }

    public void render(LootCrateEntity entity, float entityYaw, float partialTick, PoseStack stack, MultiBufferSource buffer, int packedLight) {
        BlockState blockstate = entity.getBlockState();
        if (blockstate.getRenderShape() == RenderShape.MODEL) {
            Level level = entity.level();
            if (blockstate != level.getBlockState(entity.blockPosition()) && blockstate.getRenderShape() != RenderShape.INVISIBLE) {
                stack.pushPose();
                BlockPos blockpos = BlockPos.containing(entity.getX(), entity.getBoundingBox().maxY, entity.getZ());
                stack.translate(-0.5, 0.0, -0.5);
                this.dispatcher
                        .getModelRenderer()
                        .tesselateBlock(
                                level,
                                this.dispatcher.getBlockModel(blockstate),
                                blockstate,
                                blockpos,
                                stack,
                                buffer.getBuffer(ItemBlockRenderTypes.getMovingBlockRenderType(blockstate)),
                                false,
                                RandomSource.create(),
                                blockstate.getSeed(entity.getStartPos()),
                                OverlayTexture.NO_OVERLAY
                        );
                stack.popPose();
                super.render(entity, entityYaw, partialTick, stack, buffer, packedLight);
            }
        }
    }

    public ResourceLocation getTextureLocation(LootCrateEntity p_114632_) {
        return InventoryMenu.BLOCK_ATLAS;
    }
}
