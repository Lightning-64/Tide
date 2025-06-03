package com.li64.tide.registries.items;

import com.li64.tide.Tide;
import com.li64.tide.data.loot.TornNoteData;
import com.li64.tide.network.messages.ViewNoteMsg;
import com.li64.tide.registries.TideItems;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class TornNoteItem extends Item {
    public TornNoteItem(Properties properties) {
        super(properties);
    }

    public static ItemStack create(String id) {
        ItemStack note = new ItemStack(TideItems.TORN_NOTE);
        TornNoteData data = new TornNoteData(id);

        CompoundTag tag = new CompoundTag();
        tag.putString("id", data.id());
        tag.putBoolean("unlocked", data.unlocked());

        note.getOrCreateTag().put("TornNoteData", tag);
        return note;
    }

    public static void finalizeData(ItemStack note) {
        if (!note.getOrCreateTag().contains("TornNoteData")) setData(note, TornNoteData.random());
    }

    public static TornNoteData getData(ItemStack note) {
        CompoundTag tag = note.getOrCreateTag().getCompound("TornNoteData");
        if (!note.getOrCreateTag().contains("TornNoteData") || !tag.contains("id")
                || !tag.contains("unlocked")) return TornNoteData.EMPTY;
        return new TornNoteData(tag.getString("id"), tag.getBoolean("unlocked"));
    }

    public static void setData(ItemStack note, TornNoteData data) {
        CompoundTag tag = new CompoundTag();
        tag.putString("id", data.id());
        tag.putBoolean("unlocked", data.unlocked());
        note.getOrCreateTag().put("TornNoteData", tag);
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level level, @NotNull Player player, @NotNull InteractionHand hand) {
        ItemStack note = player.getItemInHand(hand);
        if (player instanceof ServerPlayer serverPlayer) {
            finalizeData(note);
            String id = getData(note).id();
            setData(note, new TornNoteData(id, true));
            int slot = player.getInventory().findSlotMatchingItem(note);
            player.getInventory().setItem(slot, note);
            Tide.NETWORK.sendToPlayer(new ViewNoteMsg(id), serverPlayer);
        }
        return InteractionResultHolder.sidedSuccess(note, level.isClientSide());
    }

    @Override
    public void appendHoverText(@NotNull ItemStack stack, @Nullable Level level, @NotNull List<Component> components, @NotNull TooltipFlag flag) {
        TornNoteData data = getData(stack);
        if (data.unlocked() || flag.isCreative()) components.add(Component.translatable(
                "item.tide.torn_note.variant." + data.id()).withStyle(ChatFormatting.GRAY));
        else components.add(Component.translatable(
                "item.tide.torn_note.variant.unknown").withStyle(ChatFormatting.GRAY));
        super.appendHoverText(stack, level, components, flag);
    }
}
