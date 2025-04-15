package com.li64.tide;

import com.li64.tide.client.gui.TideMenuTypes;
import com.li64.tide.config.TideConfig;
import com.li64.tide.data.TideCriteriaTriggers;
import com.li64.tide.data.TideDataComponents;
import com.li64.tide.registries.TideEntitySubPredicates;
import com.li64.tide.loot.TideLootModifiers;
import com.li64.tide.network.TideMessages;
import com.li64.tide.registries.*;
import me.shedaniel.autoconfig.AutoConfig;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraftforge.client.ConfigScreenHandler;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegisterEvent;

@Mod(Tide.MOD_ID)
public class TideForge {
    public TideForge(FMLJavaModLoadingContext context) {
        IEventBus modEventBus = context.getModEventBus();
        modEventBus.register(this);

        Tide.init();

        TideForgeNetworking.init();
        TideMessages.init(Tide.NETWORK);
        TideLootModifiers.register(modEventBus);

        context.registerExtensionPoint(
                ConfigScreenHandler.ConfigScreenFactory.class,
                () -> new ConfigScreenHandler.ConfigScreenFactory(
                        (mc, screen) -> AutoConfig.getConfigScreen(TideConfig.class, screen).get())
        );
    }

    @SubscribeEvent
    public void onRegister(RegisterEvent event) {
        event.register(ForgeRegistries.Keys.ITEMS, helper -> TideItems.init());
        event.register(ForgeRegistries.Keys.BLOCKS, helper -> TideBlocks.init());
        event.register(ForgeRegistries.Keys.BLOCK_ENTITY_TYPES, helper -> TideBlockEntities.init());
        event.register(ForgeRegistries.Keys.ENTITY_TYPES, helper -> TideEntityTypes.init());
        event.register(ForgeRegistries.Keys.MENU_TYPES, helper -> TideMenuTypes.init());
        event.register(ForgeRegistries.Keys.SOUND_EVENTS, helper -> TideSoundEvents.init());
        event.register(ForgeRegistries.Keys.FEATURES, helper -> TideFeatures.init());
        event.register(Registries.TRIGGER_TYPE, helper -> TideCriteriaTriggers.init());
        event.register(Registries.DATA_COMPONENT_TYPE, helper -> TideDataComponents.init());
        event.register(Registries.ENTITY_SUB_PREDICATE_TYPE, helper -> TideEntitySubPredicates.init());
        event.register(Registries.LOOT_CONDITION_TYPE, helper -> TideLootConditions.init());

        event.register(Registries.CREATIVE_MODE_TAB, helper -> Registry.register(
                BuiltInRegistries.CREATIVE_MODE_TAB, Tide.MOD_ID,
                Tide.getCreativeTab(CreativeModeTab.builder()).build()));
    }
}