package com.li64.tide;

import com.li64.tide.client.gui.TideMenuTypes;
import com.li64.tide.compat.jei.TideRecipeSerializers;
import com.li64.tide.data.TideCriteriaTriggers;
import com.li64.tide.data.TideDataComponents;
import com.li64.tide.loot.TideLootModifiers;
import com.li64.tide.network.TideMessages;
import com.li64.tide.registries.*;
import com.mojang.serialization.MapCodec;
import net.minecraft.advancements.CriterionTrigger;
import net.minecraft.advancements.critereon.EntitySubPredicate;
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
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.RegisterEvent;

@Mod(Tide.MOD_ID)
public class TideNeoForge {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(BuiltInRegistries.ITEM, Tide.MOD_ID);
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(BuiltInRegistries.BLOCK, Tide.MOD_ID);
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, Tide.MOD_ID);
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(BuiltInRegistries.ENTITY_TYPE, Tide.MOD_ID);
    public static final DeferredRegister<CriterionTrigger<?>> TRIGGER_TYPES = DeferredRegister.create(Registries.TRIGGER_TYPE, Tide.MOD_ID);
    public static final DeferredRegister<DataComponentType<?>> DATA_COMPONENT_TYPES = DeferredRegister.create(BuiltInRegistries.DATA_COMPONENT_TYPE, Tide.MOD_ID);
    public static final DeferredRegister<MapCodec<? extends EntitySubPredicate>> ENTITY_SUB_PREDICATES = DeferredRegister.create(Registries.ENTITY_SUB_PREDICATE_TYPE, Tide.MOD_ID);
    public static final DeferredRegister<LootItemConditionType> LOOT_CONDITION_TYPES = DeferredRegister.create(Registries.LOOT_CONDITION_TYPE, Tide.MOD_ID);
    public static final DeferredRegister<MenuType<?>> MENU_TYPES = DeferredRegister.create(BuiltInRegistries.MENU, Tide.MOD_ID);
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS = DeferredRegister.create(BuiltInRegistries.SOUND_EVENT, Tide.MOD_ID);

    public static final PayloadRegistrar REGISTRAR = new PayloadRegistrar("1");
    public static ModContainer CONTAINER;

    public TideNeoForge(IEventBus eventBus, ModContainer container) {
        eventBus.addListener(this::onRegister);
        eventBus.addListener(this::registerPayloads);

        Tide.init();

        BLOCKS.register(eventBus);
        BLOCK_ENTITIES.register(eventBus);
        ITEMS.register(eventBus);
        ENTITY_TYPES.register(eventBus);
        TRIGGER_TYPES.register(eventBus);
        DATA_COMPONENT_TYPES.register(eventBus);
        ENTITY_SUB_PREDICATES.register(eventBus);
        LOOT_CONDITION_TYPES.register(eventBus);
        MENU_TYPES.register(eventBus);
        SOUND_EVENTS.register(eventBus);

        TideLootModifiers.register(eventBus);
        TideRecipeSerializers.register(eventBus);

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