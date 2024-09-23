package com.li64.tide;

import com.li64.tide.client.gui.TideMenuTypes;
import com.li64.tide.compat.jei.TideRecipeSerializers;
import com.li64.tide.config.TideConfig;
import com.li64.tide.data.TideCriteriaTriggers;
import com.li64.tide.data.TideDataComponents;
import com.li64.tide.loot.TideLootModifiers;
import com.li64.tide.network.TideMessages;
import com.li64.tide.registries.*;
import me.shedaniel.autoconfig.AutoConfig;
import net.minecraft.advancements.CriterionTrigger;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.client.ConfigScreenHandler;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegisterEvent;

@Mod(Tide.MOD_ID)
public class TideForge {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Tide.MOD_ID);
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Tide.MOD_ID);
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, Tide.MOD_ID);
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, Tide.MOD_ID);
    public static final DeferredRegister<CriterionTrigger<?>> TRIGGER_TYPES = DeferredRegister.create(Registries.TRIGGER_TYPE, Tide.MOD_ID);
    public static final DeferredRegister<DataComponentType<?>> DATA_COMPONENT_TYPES = DeferredRegister.create(Registries.DATA_COMPONENT_TYPE, Tide.MOD_ID);
    public static final DeferredRegister<MenuType<?>> MENU_TYPES = DeferredRegister.create(ForgeRegistries.MENU_TYPES, Tide.MOD_ID);
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, Tide.MOD_ID);

    public TideForge() {
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
        eventBus.addListener(this::onRegister);

        Tide.init();

        TideForgeNetworking.init();
        TideMessages.init(Tide.NETWORK);

        BLOCKS.register(eventBus);
        BLOCK_ENTITIES.register(eventBus);
        ITEMS.register(eventBus);
        ENTITY_TYPES.register(eventBus);
        TRIGGER_TYPES.register(eventBus);
        DATA_COMPONENT_TYPES.register(eventBus);
        MENU_TYPES.register(eventBus);
        SOUND_EVENTS.register(eventBus);

        TideLootModifiers.register(eventBus);
        TideRecipeSerializers.register(eventBus);

        ModLoadingContext.get().registerExtensionPoint(
                ConfigScreenHandler.ConfigScreenFactory.class,
                () -> new ConfigScreenHandler.ConfigScreenFactory(
                        (mc, screen) -> AutoConfig.getConfigScreen(TideConfig.class, screen).get())
        );
    }

    @SubscribeEvent @SuppressWarnings("unused")
    public void onRegister(RegisterEvent event) {
        event.register(ForgeRegistries.Keys.ITEMS, helper -> TideItems.init());
        event.register(ForgeRegistries.Keys.BLOCKS, helper -> TideBlocks.init());
        event.register(ForgeRegistries.Keys.BLOCK_ENTITY_TYPES, helper -> TideBlockEntities.init());
        event.register(ForgeRegistries.Keys.ENTITY_TYPES, helper -> TideEntityTypes.init());
        event.register(ForgeRegistries.Keys.MENU_TYPES, helper -> TideMenuTypes.init());
        event.register(ForgeRegistries.Keys.SOUND_EVENTS, helper -> TideSoundEvents.init());
        event.register(Registries.TRIGGER_TYPE, helper -> TideCriteriaTriggers.init());
        event.register(Registries.DATA_COMPONENT_TYPE, helper -> TideDataComponents.init());

        event.register(Registries.CREATIVE_MODE_TAB, helper -> Registry.register(
                BuiltInRegistries.CREATIVE_MODE_TAB, Tide.MOD_ID,
                Tide.getCreativeTab(CreativeModeTab.builder()).build()));
    }
}