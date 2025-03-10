package com.li64.tide.platform;

import com.li64.tide.platform.services.TideMainPlatform;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.projectile.FishingHook;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.ItemStack;
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