package com.li64.tide.registries.items;

import com.li64.tide.data.TideDataComponents;
import com.li64.tide.data.loot.TornNoteData;
import com.li64.tide.registries.TideItems;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class TornNoteItem extends Item {
    public TornNoteItem(Properties properties) {
        super(properties);
    }

    public static ItemStack create(String id) {
        ItemStack note = new ItemStack(TideItems.TORN_NOTE);
        note.set(TideDataComponents.TORN_NOTE_VARIANT, new TornNoteData(id));
        return note;
    }

    public static TornNoteData getData(ItemStack note) {
        return note.getOrDefault(TideDataComponents.TORN_NOTE_VARIANT, TornNoteData.EMPTY);
    }

    @Override
    public @NotNull InteractionResult use(@NotNull Level level, Player player, @NotNull InteractionHand hand) {
        ItemStack note = player.getItemInHand(hand);
        if (!level.isClientSide()) note.set(TideDataComponents.TORN_NOTE_VARIANT, new TornNoteData(getData(note).id(), true));
        player.openItemGui(note, hand);
        return InteractionResult.SUCCESS;
    }

    @Override
    public void appendHoverText(@NotNull ItemStack stack, @NotNull TooltipContext context, @NotNull List<Component> components, @NotNull TooltipFlag flag) {
        TornNoteData data = getData(stack);
        if (data.unlocked() || flag.isCreative()) components.add(Component.translatable(
                "item.tide.torn_note.variant." + getData(stack).id()).withStyle(ChatFormatting.GRAY));
        else components.add(Component.translatable(
                "item.tide.torn_note.variant.unknown").withStyle(ChatFormatting.GRAY));
        super.appendHoverText(stack, context, components, flag);
    }
}
