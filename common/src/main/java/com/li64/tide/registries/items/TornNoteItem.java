package com.li64.tide.registries.items;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
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

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(Level pLevel, Player player, @NotNull InteractionHand hand) {
        ItemStack noteItem = player.getItemInHand(hand);
        player.openItemGui(noteItem, hand);
        return InteractionResultHolder.sidedSuccess(noteItem, pLevel.isClientSide());
    }

    @Override
    public void appendHoverText(@NotNull ItemStack stack, @Nullable Level level, List<Component> components, @NotNull TooltipFlag isAdvanced) {
        components.add(Component.translatable("item.tide.torn_note.desc").withStyle(ChatFormatting.GRAY));
        super.appendHoverText(stack, level, components, isAdvanced);
    }
}
