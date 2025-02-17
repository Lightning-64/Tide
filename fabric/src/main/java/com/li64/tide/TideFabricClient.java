package com.li64.tide;

import com.li64.tide.client.gui.TideMenuTypes;
import com.li64.tide.client.gui.screens.AnglerWorkshopScreen;
import com.li64.tide.data.TideTags;
import com.li64.tide.registries.*;
import com.li64.tide.registries.items.TideFishingRodItem;
import com.li64.tide.util.BaitUtils;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.entity.Entity;

public class TideFabricClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        Tide.NETWORK.registerHandlers();

        MenuScreens.register(TideMenuTypes.ANGLER_WORKSHOP, AnglerWorkshopScreen::new);

        ItemTooltipCallback.EVENT.register((stack, context, type, lines) -> {
            if (BaitUtils.isBait(stack)) lines.addAll(BaitUtils.getDescriptionLines(stack));
            if (stack.is(TideTags.Items.CUSTOMIZABLE_RODS)) lines.addAll(TideFishingRodItem.getDescriptionLines(stack, context.registries()));
        });

        BlockRenderLayerMap.INSTANCE.putBlock(TideBlocks.ALGAE, RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(TideBlocks.JELLY_TORCH, RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(TideBlocks.JELLY_WALL_TORCH, RenderType.cutout());

        TideEntityRenderers.init();
        TideEntityRenderers.REGISTRY.forEach(TideFabricClient::registerRenderer);

        TideLayerDefinitions.init();
        TideLayerDefinitions.REGISTRY.forEach(TideFabricClient::registerLayer);
    }

    public static <T extends Entity> void registerRenderer(TideEntityRenderers.Registration<T> reg) {
        EntityRendererRegistry.register(reg.entityType(), reg.renderer());
    }

    public static void registerLayer(TideLayerDefinitions.Registration reg) {
        EntityModelLayerRegistry.registerModelLayer(reg.location(), () -> reg.layerDefinition().get());
    }
}