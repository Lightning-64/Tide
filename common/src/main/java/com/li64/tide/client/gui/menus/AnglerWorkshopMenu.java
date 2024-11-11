package com.li64.tide.client.gui.menus;

import com.li64.tide.client.gui.TideMenuTypes;
import com.li64.tide.data.TideTags;
import com.li64.tide.data.rods.CustomRodManager;
import com.li64.tide.registries.TideBlocks;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.ContainerListener;
import net.minecraft.world.inventory.ItemCombinerMenu;
import net.minecraft.world.inventory.ItemCombinerMenuSlotDefinition;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public class AnglerWorkshopMenu extends ItemCombinerMenu {
    protected Level level;

    public AnglerWorkshopMenu(int menuId, Inventory inventory) {
        this(menuId, inventory, ContainerLevelAccess.NULL);
    }

    public AnglerWorkshopMenu(int menuId, Inventory inventory, ContainerLevelAccess levelAccess) {
        super(TideMenuTypes.ANGLER_WORKSHOP, menuId, inventory, levelAccess);
        this.level = inventory.player.level();
    }

    @Override
    protected boolean mayPickup(Player player, boolean hasItem) {
        return true;
    }

    @Override
    protected void onTake(Player player, ItemStack stack) {
        level.playLocalSound(player.blockPosition(), SoundEvents.FISHING_BOBBER_RETRIEVE, SoundSource.BLOCKS, 1.5F, this.level.random.nextFloat() * 0.1F + 0.9F, false);
        this.access.execute((level, pos) -> level.levelEvent(1044, pos, 0));

        stack.onCraftedBy(player.level(), player, stack.getCount());
        inputSlots.clearContent();
    }

    @Override
    public void removed(Player player) {
        super.removed(player);
    }

    @Override
    protected boolean isValidBlock(BlockState blockState) {
        return blockState.is(TideBlocks.ANGLER_WORKSHOP);
    }

    @Override
    public void createResult() {
        if (!inputSlots.getItem(0).isEmpty()) {
            ItemStack rod = inputSlots.getItem(0);
            if (CustomRodManager.hasLine(rod, player.registryAccess())) {
                if (!inputSlots.getItem(1).isEmpty()) player.drop(inputSlots.getItem(1), true);
                ItemStack line = CustomRodManager.getLine(rod, player.registryAccess());
                CustomRodManager.setLine(rod, null, player.registryAccess());
                inputSlots.setItem(1, line);
            }
            if (CustomRodManager.hasBobber(rod, player.registryAccess())) {
                if (!inputSlots.getItem(2).isEmpty()) player.drop(inputSlots.getItem(2), true);
                ItemStack bobber = CustomRodManager.getBobber(rod, player.registryAccess());
                CustomRodManager.setBobber(rod, null, player.registryAccess());
                inputSlots.setItem(2, bobber);
            }
            if (CustomRodManager.hasHook(rod, player.registryAccess())) {
                if (!inputSlots.getItem(3).isEmpty()) player.drop(inputSlots.getItem(3), true);
                ItemStack hook = CustomRodManager.getHook(rod, player.registryAccess());
                CustomRodManager.setHook(rod, null, player.registryAccess());
                inputSlots.setItem(3, hook);
            }
        }

        if (inputSlots.getItem(0).isEmpty() ||
                (inputSlots.getItem(1).isEmpty()
                && inputSlots.getItem(2).isEmpty()
                && inputSlots.getItem(3).isEmpty()))
            this.resultSlots.setItem(0, ItemStack.EMPTY);
        else {
            ItemStack rod = slots.get(0).getItem();
            ItemStack line = slots.get(1).getItem();
            ItemStack bobber = slots.get(2).getItem();
            ItemStack hook = slots.get(3).getItem();

            this.resultSlots.setItem(0, getOutputRodFor(rod, bobber, hook, line));
        }
    }

    public ItemStack getOutputRodFor(ItemStack input, ItemStack bobberItem, ItemStack hookItem, ItemStack lineItem) {
        ItemStack newRod = input.copy();
        if (!lineItem.isEmpty()) CustomRodManager.setLine(newRod, lineItem, player.registryAccess());
        if (!bobberItem.isEmpty()) CustomRodManager.setBobber(newRod, bobberItem, player.registryAccess());
        if (!hookItem.isEmpty()) CustomRodManager.setHook(newRod, hookItem, player.registryAccess());
        return newRod;
    }

    @Override
    protected @NotNull ItemCombinerMenuSlotDefinition createInputSlotDefinitions() {
        return ItemCombinerMenuSlotDefinition.create()
            .withSlot(0, 26, 11,
                    stack -> stack.is(TideTags.Items.CUSTOMIZABLE_RODS))
            .withSlot(1, 134, 8,
                    stack -> stack.is(TideTags.Items.LINES))
            .withSlot(2, 134, 32,
                    stack -> stack.is(TideTags.Items.BOBBERS))
            .withSlot(3, 134, 56,
                    stack -> stack.is(TideTags.Items.HOOKS))
            .withResultSlot(4, 26, 49).build();
    }
}
