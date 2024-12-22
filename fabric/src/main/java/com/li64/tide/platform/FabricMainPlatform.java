package com.li64.tide.platform;

import com.li64.tide.Tide;
import com.li64.tide.data.TidePlayer;
import com.li64.tide.platform.services.TideMainPlatform;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.advancements.CriterionTrigger;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;

public class FabricMainPlatform implements TideMainPlatform {
    @Override
    public String getPlatformName() {
        return "Fabric";
    }

    @Override
    public boolean isModLoaded(String modId) {
        return FabricLoader.getInstance().isModLoaded(modId);
    }

    @Override
    public boolean isDevelopmentEnvironment() {
        return FabricLoader.getInstance().isDevelopmentEnvironment();
    }

    @Override
    public <T extends AbstractContainerMenu> MenuType<T> createMenuType(MenuType.MenuSupplier<T> menuSupplier, FeatureFlagSet flags) {
        return new MenuType<>(menuSupplier, flags);
    }

    @Override
    public CompoundTag getPlayerData(ServerPlayer player) {
        return ((TidePlayer) player).tide$getTidePlayerData();
    }

    @Override
    public void registerItem(String key, Item item) {
        Registry.register(BuiltInRegistries.ITEM, Tide.resource(key), item);
    }

    @Override
    public void registerBlock(String key, Block block) {
        Registry.register(BuiltInRegistries.BLOCK, Tide.resource(key), block);
    }

    @Override
    public void registerBlockEntity(String key, BlockEntityType<?> blockEntity) {
        Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, Tide.resource(key), blockEntity);
    }

    @Override
    public void registerEntityType(String key, EntityType<?> entityType) {
        Registry.register(BuiltInRegistries.ENTITY_TYPE, Tide.resource(key), entityType);
    }

    @Override
    public void registerCriteriaTrigger(String key, CriterionTrigger<?> trigger) {
        CriteriaTriggers.register(trigger);
    }

    @Override
    public void registerLootCondition(String key, LootItemConditionType type) {
        Registry.register(BuiltInRegistries.LOOT_CONDITION_TYPE, Tide.resource(key), type);
    }

    @Override
    public void registerMenuType(String key, MenuType<?> menuType) {
        Registry.register(BuiltInRegistries.MENU, Tide.resource(key), menuType);
    }

    @Override
    public void registerSoundEvent(String key, SoundEvent soundEvent) {
        Registry.register(BuiltInRegistries.SOUND_EVENT, Tide.resource(key), soundEvent);
    }

    @Override
    public boolean isFabric() {
        return true;
    }
}
