package com.li64.tide;

import com.li64.tide.client.gui.TideMenuTypes;
import com.li64.tide.data.TideCriteriaTriggers;
import com.li64.tide.data.TideDataComponents;
import com.li64.tide.loot.TideLootModifiers;
import com.li64.tide.network.TideMessages;
import com.li64.tide.registries.*;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.CreativeModeTab;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;
import net.neoforged.neoforge.registries.RegisterEvent;

@Mod(Tide.MOD_ID)
public class TideNeoForge {
    public static final PayloadRegistrar REGISTRAR = new PayloadRegistrar("1");
    public static ModContainer CONTAINER;

    public TideNeoForge(IEventBus eventBus, ModContainer container) {
        eventBus.register(this);

        Tide.init();
        TideLootModifiers.register(eventBus);

        CONTAINER = container;
    }

    @SubscribeEvent @SuppressWarnings("unused")
    public void onRegister(RegisterEvent event) {
        event.register(Registries.ITEM, helper -> TideItems.init());
        event.register(Registries.BLOCK, helper -> TideBlocks.init());
        event.register(Registries.BLOCK_ENTITY_TYPE, helper -> TideBlockEntities.init());
        event.register(Registries.ENTITY_TYPE, helper -> TideEntityTypes.init());
        event.register(Registries.MENU, helper -> TideMenuTypes.init());
        event.register(Registries.SOUND_EVENT, helper -> TideSoundEvents.init());
        event.register(Registries.TRIGGER_TYPE, helper -> TideCriteriaTriggers.init());
        event.register(Registries.DATA_COMPONENT_TYPE, helper -> TideDataComponents.init());
        event.register(Registries.ENTITY_SUB_PREDICATE_TYPE, helper -> TideEntitySubPredicates.init());
        event.register(Registries.LOOT_CONDITION_TYPE, helper -> TideLootConditions.init());

        event.register(Registries.CREATIVE_MODE_TAB, helper -> Registry.register(
                BuiltInRegistries.CREATIVE_MODE_TAB, Tide.MOD_ID,
                Tide.getCreativeTab(CreativeModeTab.builder()).build()));
    }

    @SubscribeEvent @SuppressWarnings("unused")
    public void registerPayloads(final RegisterPayloadHandlersEvent event) {
        TideMessages.init(Tide.NETWORK);
    }
}