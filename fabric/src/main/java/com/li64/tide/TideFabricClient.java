package com.li64.tide;

import com.li64.tide.client.gui.TideMenuTypes;
import com.li64.tide.client.gui.screens.AnglerWorkshopScreen;
import com.li64.tide.registries.*;
import com.li64.tide.registries.items.BaitItem;
import com.li64.tide.registries.items.TideFishingRodItem;
import com.li64.tide.util.BaitUtils;
import com.li64.tide.util.TideUtils;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.item.ClampedItemPropertyFunction;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.FishingRodItem;
import net.minecraft.world.item.Items;

public class TideFabricClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        Tide.NETWORK.registerHandlers();

        MenuScreens.register(TideMenuTypes.ANGLER_WORKSHOP, AnglerWorkshopScreen::new);

        ItemTooltipCallback.EVENT.register((stack, context, type, lines) -> {
            if (BaitUtils.isBait(stack) && !(stack.getItem() instanceof BaitItem)) {
                Style style = Component.empty().getStyle().withColor(ChatFormatting.GRAY).withItalic(true);
                lines.add(Component.translatable("item.tide.bait.desc").setStyle(style));
            }
        });

        ClampedItemPropertyFunction rodCastFunction = (itemStack, level, player, i) -> {
            if (player == null) return 0.0F;
            else {
                boolean flag = player.getMainHandItem() == itemStack;
                boolean flag1 = player.getOffhandItem() == itemStack;
                if (player.getMainHandItem().getItem() instanceof FishingRodItem) flag1 = false;
                return (flag || flag1) && player instanceof Player && ((Player) player).fishing != null ? 1.0F : 0.0F;
            }
        };

        ItemProperties.register(Items.FISHING_ROD, TideFishingRodItem.CAST_PROPERTY, rodCastFunction);
        ItemProperties.register(TideItems.STONE_FISHING_ROD, TideFishingRodItem.CAST_PROPERTY, rodCastFunction);
        ItemProperties.register(TideItems.IRON_FISHING_ROD, TideFishingRodItem.CAST_PROPERTY, rodCastFunction);
        ItemProperties.register(TideItems.GOLDEN_FISHING_ROD, TideFishingRodItem.CAST_PROPERTY, rodCastFunction);
        ItemProperties.register(TideItems.CRYSTAL_FISHING_ROD, TideFishingRodItem.CAST_PROPERTY, rodCastFunction);
        ItemProperties.register(TideItems.DIAMOND_FISHING_ROD, TideFishingRodItem.CAST_PROPERTY, rodCastFunction);
        ItemProperties.register(TideItems.NETHERITE_FISHING_ROD, TideFishingRodItem.CAST_PROPERTY, rodCastFunction);

        BlockRenderLayerMap.INSTANCE.putBlock(TideBlocks.ALGAE, RenderType.cutoutMipped());
        BlockRenderLayerMap.INSTANCE.putBlock(TideBlocks.JELLY_TORCH, RenderType.cutoutMipped());
        BlockRenderLayerMap.INSTANCE.putBlock(TideBlocks.JELLY_WALL_TORCH, RenderType.cutoutMipped());

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