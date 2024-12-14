package com.li64.tide;

import com.li64.tide.config.TideConfig;
import com.li64.tide.data.rods.ClientFishingRodTooltip;
import com.li64.tide.data.rods.FishingRodTooltip;
import com.li64.tide.registries.TideItems;
import com.li64.tide.registries.items.TideFishingRodItem;
import me.shedaniel.autoconfig.AutoConfig;
import net.minecraft.client.renderer.item.ClampedItemPropertyFunction;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.FishingRodItem;
import net.minecraft.world.item.Items;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.RegisterClientTooltipComponentFactoriesEvent;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;

@SuppressWarnings("unused")
@EventBusSubscriber(modid = Tide.MOD_ID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class TideNeoForgeClient {
    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        TideNeoForge.CONTAINER.registerExtensionPoint(
                IConfigScreenFactory.class,
                (mc, screen) -> AutoConfig.getConfigScreen(TideConfig.class, screen).get()
        );

        ClampedItemPropertyFunction rodCastFunction = (itemStack, level, player, p_174588_) -> {
            if (player == null) {
                return 0.0F;
            } else {
                boolean flag = player.getMainHandItem() == itemStack;
                boolean flag1 = player.getOffhandItem() == itemStack;
                if (player.getMainHandItem().getItem() instanceof FishingRodItem) {
                    flag1 = false;
                }
                return (flag || flag1) && player instanceof Player && ((Player) player).fishing != null ? 1.0F : 0.0F;
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
        });
    }

    @SubscribeEvent
    public static void registerClientTooltipComponents(RegisterClientTooltipComponentFactoriesEvent event) {
        event.register(FishingRodTooltip.class, (tooltip -> new ClientFishingRodTooltip(tooltip.contents())));
    }
}
