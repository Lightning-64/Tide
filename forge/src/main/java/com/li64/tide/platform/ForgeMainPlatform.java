package com.li64.tide.platform;

import com.li64.tide.compat.stardewfishing.StardewFishingCompat;
import com.li64.tide.platform.services.TideMainPlatform;
import com.li64.tide.registries.entities.misc.fishing.HookAccessor;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.projectile.FishingHook;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.event.entity.player.ItemFishedEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.loading.FMLLoader;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ForgeMainPlatform implements TideMainPlatform {
    @Override
    public String getPlatformName() { return "Forge"; }

    @Override
    public boolean isModLoaded(String modId) { return ModList.get().isLoaded(modId); }

    @Override
    public boolean isDevelopmentEnvironment() { return !FMLLoader.isProduction(); }

    @Override
    public <T extends AbstractContainerMenu> MenuType<T> createMenuType(MenuType.MenuSupplier<T> menuSupplier, FeatureFlagSet flags) {
        return IForgeMenuType.create((windowId, playerInv, extraData) -> menuSupplier.create(windowId, playerInv));
    }

    @Override
    public CompoundTag getPlayerData(ServerPlayer player) {
        return player.getPersistentData();
    }

    @Override
    public boolean forgeItemFishedEvent(List<ItemStack> itemList, int i, FishingHook fishing) {
        ItemFishedEvent event = new ItemFishedEvent(itemList, i, fishing);
        MinecraftForge.EVENT_BUS.post(event);
        return event.isCanceled();
    }

    @Override
    public boolean stardewStart(ServerPlayer player, HookAccessor hook, ItemStack item, List<ItemStack> items) {
        return StardewFishingCompat.start(player, hook, item, items);
    }

    @Override
    public Optional<ArrayList<ItemStack>> stardewGetRewards(HookAccessor hook) {
        return StardewFishingCompat.getRewards(hook);
    }

    @Override
    public double getBiteTimeMultiplier() {
        if (isModLoaded("stardew_fishing")) return StardewFishingCompat.getBiteTimeMultiplier();
        return TideMainPlatform.super.getBiteTimeMultiplier();
    }
}