package com.li64.tide.platform;

import com.li64.tide.TideForge;
import com.li64.tide.compat.stardewfishing.StardewFishingCompat;
import com.li64.tide.platform.services.TideMainPlatform;
import com.mojang.serialization.MapCodec;
import net.minecraft.advancements.CriterionTrigger;
import net.minecraft.advancements.critereon.EntitySubPredicate;
import net.minecraft.core.component.DataComponentType;
import com.li64.tide.registries.entities.misc.fishing.HookAccessor;
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
    public void registerItem(String key, Item item) {
        TideForge.ITEMS.register(key, () -> item);
    }

    @Override
    public void registerBlock(String key, Block block) {
        TideForge.BLOCKS.register(key, () -> block);
    }

    @Override
    public void registerBlockEntity(String key, BlockEntityType<?> blockEntity) {
        TideForge.BLOCK_ENTITIES.register(key, () -> blockEntity);
    }

    @Override
    public void registerEntityType(String key, EntityType<?> entityType) {
        TideForge.ENTITY_TYPES.register(key, () -> entityType);
    }

    @Override
    public void registerCriteriaTrigger(String key, CriterionTrigger<?> trigger) {
        TideForge.TRIGGER_TYPES.register(key, () -> trigger);
    }

    @Override
    public void registerComponentType(String key, DataComponentType<?> componentType) {
        TideForge.DATA_COMPONENT_TYPES.register(key, () -> componentType);
    }

    @Override
    public void registerEntitySubPredicate(String key, MapCodec<? extends EntitySubPredicate> codec) {
        TideForge.ENTITY_SUB_PREDICATES.register(key, () -> codec);
    }

    @Override
    public void registerMenuType(String key, MenuType<?> menuType) {
        TideForge.MENU_TYPES.register(key, () -> menuType);
    }

    @Override
    public void registerSoundEvent(String key, SoundEvent soundEvent) {
        TideForge.SOUND_EVENTS.register(key, () -> soundEvent);
    }

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
}