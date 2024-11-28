package com.li64.tide.data.rods;

import com.mojang.serialization.Codec;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class BaitContents implements TooltipComponent {
    public static final int MAX_STACKS = 3;
    public static final BaitContents EMPTY = new BaitContents();
    public static final Codec<BaitContents> CODEC;
    public static final StreamCodec<RegistryFriendlyByteBuf, BaitContents> STREAM_CODEC;
    private final List<ItemStack> items;

    public BaitContents() {
        this(new ArrayList<>());
    }

    public BaitContents(BaitContents contents) {
        this(contents == null ? new ArrayList<>() : new ArrayList<>(contents.items));
    }

    public BaitContents(List<ItemStack> items) {
        this.items = new ArrayList<>(items);
    }

    public List<ItemStack> items() {
        return items;
    }

    public ItemStack get(int index) {
        return items().get(index);
    }

    public int size() {
        return this.items.size();
    }

    public boolean isEmpty() {
        return this.items.isEmpty();
    }

    private int findStackIndex(ItemStack stack) {
        if (stack.isStackable()) {
            for (int i = 0; i < this.items.size(); ++i) {
                ItemStack storedItem = this.items.get(i);
                if (ItemStack.isSameItemSameComponents(storedItem, stack)
                    && storedItem.getCount() < storedItem.getMaxStackSize()) return i;
            }
        }
        return -1;
    }

    // inserts stack to the contents
    public void tryInsert(ItemStack stack) {
        if (!stack.isEmpty() && stack.getItem().canFitInsideContainerItems()) {
            int index = this.findStackIndex(stack);
            int count = stack.getCount();

            if (index != -1 && stack.isStackable()) {
                ItemStack current = this.items.get(index);

                int stackSize = stack.getMaxStackSize();
                int amountToAdd = Math.min(stackSize - current.getCount(), stack.getCount());

                ItemStack added = current.copyWithCount(current.getCount() + amountToAdd);
                stack.shrink(amountToAdd);

                this.items.set(index, added);
            } else {
                if (size() < MAX_STACKS) this.items.addFirst(stack.split(count));
            }
        }
    }

    // transfer items from slot to contents
    public void tryTransfer(Slot slot, Player pPlayer) {
        ItemStack slotStack = slot.safeTake(slot.getItem().getCount(), slot.getMaxStackSize(), pPlayer);
        this.tryInsert(slotStack);
        if (!slotStack.isEmpty()) slot.safeInsert(slotStack);
    }

    @Nullable
    public ItemStack removeStack() {
        if (this.items.isEmpty()) {
            return null;
        } else {
            return this.items.removeFirst().copy();
        }
    }

    public String toString() {
        return "BaitContents" + this.items;
    }

    static {
        CODEC = ItemStack.CODEC.listOf().xmap(BaitContents::new, contents -> contents.items);
        STREAM_CODEC = ItemStack.STREAM_CODEC.apply(ByteBufCodecs.list()).map(BaitContents::new, contents -> contents.items);
    }
}
