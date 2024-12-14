package com.li64.tide;

import com.li64.tide.client.gui.TideMenuTypes;
import com.li64.tide.client.gui.overlays.CastBarOverlay;
import com.li64.tide.client.gui.overlays.CatchMinigameOverlay;
import com.li64.tide.client.gui.screens.AnglerWorkshopScreen;
import com.li64.tide.data.rods.ClientFishingRodTooltip;
import com.li64.tide.data.rods.FishingRodTooltip;
import com.li64.tide.registries.TideItems;
import com.li64.tide.registries.items.TideFishingRodItem;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.item.ClampedItemPropertyFunction;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.FishingRodItem;
import net.minecraft.world.item.Items;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterGuiOverlaysEvent;
import net.minecraftforge.client.gui.overlay.VanillaGuiOverlay;
import net.minecraftforge.client.event.RegisterClientTooltipComponentFactoriesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@SuppressWarnings("unused")
@Mod.EventBusSubscriber(modid = Tide.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class TideForgeClient {
    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        ClampedItemPropertyFunction rodCastFunction = (itemStack, level, player, p_174588_) -> {
            if (player == null) {
                return 0.0F;
            } else {
                boolean flag = player.getMainHandItem() == itemStack;
                boolean flag1 = player.getOffhandItem() == itemStack;
                if (player.getMainHandItem().getItem() instanceof FishingRodItem) {
                    flag1 = false;
                }
                return (flag || flag1) && player instanceof Player && ((Player)player).fishing != null ? 1.0F : 0.0F;
            }
        };

        event.enqueueWork(() -> {
            ItemProperties.register(Items.FISHING_ROD, TideFishingRodItem.CAST_PROPERTY, rodCastFunction);
            ItemProperties.register(TideItems.STONE_FISHING_ROD, TideFishingRodItem.CAST_PROPERTY, rodCastFunction);
            ItemProperties.register(TideItems.IRON_FISHING_ROD, TideFishingRodItem.CAST_PROPERTY, rodCastFunction);
            ItemProperties.register(TideItems.GOLDEN_FISHING_ROD, TideFishingRodItem.CAST_PROPERTY, rodCastFunction);
            ItemProperties.register(TideItems.CRYSTAL_FISHING_ROD, TideFishingRodItem.CAST_PROPERTY, rodCastFunction);
            ItemProperties.register(TideItems.DIAMOND_FISHING_ROD, TideFishingRodItem.CAST_PROPERTY, rodCastFunction);
            ItemProperties.register(TideItems.NETHERITE_FISHING_ROD, TideFishingRodItem.CAST_PROPERTY, rodCastFunction);

            MenuScreens.register(TideMenuTypes.ANGLER_WORKSHOP, AnglerWorkshopScreen::new);
        });
    }

    @SubscribeEvent
    public static void registerOverlays(RegisterGuiOverlaysEvent event) {
        Tide.LOG.info("Registering tide gui overlays");
        event.registerAbove(VanillaGuiOverlay.HOTBAR.id(), "cast_overlay",
                (forgeGui, gui, partialTick, width, height) -> CastBarOverlay.render(gui, partialTick));
        event.registerAbove(VanillaGuiOverlay.HOTBAR.id(), "minigame_overlay",
                (forgeGui, gui, partialTick, width, height) -> CatchMinigameOverlay.render(gui, partialTick));
    }

    @SubscribeEvent
    public static void registerClientTooltipComponents(RegisterClientTooltipComponentFactoriesEvent event) {
        event.register(FishingRodTooltip.class, (tooltip -> new ClientFishingRodTooltip(tooltip.contents())));
    }
}
