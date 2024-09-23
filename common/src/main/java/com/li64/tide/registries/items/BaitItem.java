package com.li64.tide.registries.items;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;

public class BaitItem extends Item {
    private final int luckBonus;
    private final int speedBonus;
    private final String description;

    public BaitItem(Properties properties, int luckBonus, int speedBonus, String description) {
        super(properties);
        this.luckBonus = luckBonus;
        this.speedBonus = speedBonus;
        this.description = description;
    }

    public int getSpeedBonus() {
        return speedBonus;
    }

    public int getLuckBonus() {
        return luckBonus;
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> components, TooltipFlag flag) {
        super.appendHoverText(stack, context, components, flag);
        Style style = Component.empty().getStyle().withColor(ChatFormatting.GRAY).withItalic(true);
        components.add(Component.translatable(description).setStyle(style));
    }
}