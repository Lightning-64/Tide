package com.li64.tide;

import com.li64.tide.config.TideConfig;
import com.li64.tide.data.rods.ClientFishingRodTooltip;
import com.li64.tide.data.rods.FishingRodTooltip;
import me.shedaniel.autoconfig.AutoConfig;
import net.neoforged.api.distmarker.Dist;
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
    }

    @SubscribeEvent
    public static void registerClientTooltipComponents(RegisterClientTooltipComponentFactoriesEvent event) {
        event.register(FishingRodTooltip.class, (tooltip -> new ClientFishingRodTooltip(tooltip.contents())));
    }
}
