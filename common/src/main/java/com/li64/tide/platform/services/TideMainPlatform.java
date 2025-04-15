package com.li64.tide.platform.services;

import com.li64.tide.registries.entities.misc.fishing.HookAccessor;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.projectile.FishingHook;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.ItemStack;

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

    default Optional<ArrayList<ItemStack>> stardewGetRewards(HookAccessor hook) { return Optional.empty(); }

    default boolean stardewStart(ServerPlayer player, HookAccessor hook, ItemStack item, List<ItemStack> items) {
        return false;
    }

    default boolean isFabric() {
        return false;
    }

    default double getBiteTimeMultiplier() { return 1.0; }
}
