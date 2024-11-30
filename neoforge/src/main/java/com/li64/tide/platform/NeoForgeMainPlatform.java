package com.li64.tide.platform;

import com.li64.tide.TideNeoForge;
import com.li64.tide.platform.services.TideMainPlatform;
import com.mojang.serialization.MapCodec;
import net.minecraft.advancements.CriterionTrigger;
import net.minecraft.advancements.critereon.EntitySubPredicate;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.FishingHook;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;
import net.neoforged.fml.ModList;
import net.neoforged.fml.loading.FMLLoader;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.player.ItemFishedEvent;

import java.util.List;

public class NeoForgeMainPlatform implements TideMainPlatform {
    @Override
    public String getPlatformName() { return "NeoForge"; }

    @Override
    public boolean isModLoaded(String modId) { return ModList.get().isLoaded(modId); }

    @Override
    public boolean isDevelopmentEnvironment() { return !FMLLoader.isProduction(); }

    @Override
    public void registerItem(String key, Item item) {
        TideNeoForge.ITEMS.register(key, () -> item);
    }

    @Override
    public void registerBlock(String key, Block block) {
        TideNeoForge.BLOCKS.register(key, () -> block);
    }

    @Override
    public void registerBlockEntity(String key, BlockEntityType<?> blockEntity) {
        TideNeoForge.BLOCK_ENTITIES.register(key, () -> blockEntity);
    }

    @Override
    public void registerEntityType(String key, EntityType<?> entityType) {
        TideNeoForge.ENTITY_TYPES.register(key, () -> entityType);
    }

    @Override
    public void registerCriteriaTrigger(String key, CriterionTrigger<?> trigger) {
        TideNeoForge.TRIGGER_TYPES.register(key, () -> trigger);
    }

    @Override
    public void registerComponentType(String key, DataComponentType<?> componentType) {
        TideNeoForge.DATA_COMPONENT_TYPES.register(key, () -> componentType);
    }

    @Override
    public void registerEntitySubPredicate(String key, MapCodec<? extends EntitySubPredicate> codec) {
        TideNeoForge.ENTITY_SUB_PREDICATES.register(key, () -> codec);
    }

    @Override
    public void registerLootCondition(String key, LootItemConditionType type) {
        TideNeoForge.LOOT_CONDITION_TYPES.register(key, () -> type);
    }

    @Override
    public void registerMenuType(String key, MenuType<?> menuType) {
        TideNeoForge.MENU_TYPES.register(key, () -> menuType);
    }

    @Override
    public void registerSoundEvent(String key, SoundEvent soundEvent) {
        TideNeoForge.SOUND_EVENTS.register(key, () -> soundEvent);
    }

    @Override
    public <T extends AbstractContainerMenu> MenuType<T> createMenuType(MenuType.MenuSupplier<T> menuSupplier, FeatureFlagSet flags) {
        return new MenuType<>(menuSupplier, FeatureFlags.DEFAULT_FLAGS);
    }

    @Override
    public CompoundTag getPlayerData(ServerPlayer player) {
        return player.getPersistentData();
    }

    @Override
    public boolean forgeItemFishedEvent(List<ItemStack> itemList, int i, FishingHook fishing) {
        ItemFishedEvent event = new ItemFishedEvent(itemList, i, fishing);
        NeoForge.EVENT_BUS.post(event);
        return event.isCanceled();
    }
}