package com.li64.tide.platform.services;

import com.li64.tide.registries.entities.misc.fishing.HookAccessor;
import net.minecraft.advancements.CriterionTrigger;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.FishingHook;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public interface TideMainPlatform {
    String getPlatformName();

    boolean isModLoaded(String modId);

    boolean isDevelopmentEnvironment();

    default String getEnvironmentName() {
        return isDevelopmentEnvironment() ? "development" : "production";
    }

    default boolean forgeItemFishedEvent(List<ItemStack> itemList, int i, FishingHook fishing) {
        return false;
    }

    <T extends AbstractContainerMenu> MenuType<T> createMenuType(MenuType.MenuSupplier<T> menuSupplier, FeatureFlagSet flags);

    CompoundTag getPlayerData(ServerPlayer player);

    void registerItem(String key, Item item);

    void registerBlock(String key, Block block);

    void registerBlockEntity(String key, BlockEntityType<?> blockEntity);

    void registerEntityType(String key, EntityType<?> entityType);

    void registerCriteriaTrigger(String key, CriterionTrigger<?> trigger);

    void registerComponentType(String key, DataComponentType<?> componentType);

    void registerMenuType(String key, MenuType<?> menuType);

    void registerSoundEvent(String key, SoundEvent soundEvent);

    default Optional<ArrayList<ItemStack>> stardewGetRewards(HookAccessor hook) { return Optional.empty(); }

    default boolean stardewStart(ServerPlayer player, HookAccessor hook, ItemStack item, List<ItemStack> items) {
        return false;
    }

    default boolean isFabric() {
        return false;
    }
}
