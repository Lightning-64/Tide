package com.li64.tide.registries.blocks;

import net.minecraft.ChatFormatting;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.StandingAndWallBlockItem;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class JellyTorchBlockItem extends StandingAndWallBlockItem {
    public JellyTorchBlockItem(Block normal, Block wall, Properties properties, Direction direction) {
        super(normal, wall, direction, properties);
    }

    @Override
    public void appendHoverText(@NotNull ItemStack itemStack, @NotNull TooltipContext context, @NotNull List<Component> hoverText, @NotNull TooltipFlag flag) {
        super.appendHoverText(itemStack, context, hoverText, flag);
        Style style = Component.empty().getStyle().withColor(ChatFormatting.GRAY).withItalic(true);
        hoverText.add(Component.translatable("item.tide.jelly_torch.desc").setStyle(style));
    }
}
