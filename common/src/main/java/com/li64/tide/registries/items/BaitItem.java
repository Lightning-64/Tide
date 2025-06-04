package com.li64.tide.registries.items;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.function.Consumer;

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

    @Override @SuppressWarnings("deprecation")
    public void appendHoverText(@NotNull ItemStack stack,
                                @NotNull TooltipContext context,
                                @NotNull TooltipDisplay tooltipDisplay,
                                @NotNull Consumer<Component> tooltipAdder,
                                @NotNull TooltipFlag flag) {

        super.appendHoverText(stack, context, tooltipDisplay, tooltipAdder, flag);

        Style style = Component.empty().getStyle().withColor(ChatFormatting.GRAY).withItalic(true);
        tooltipAdder.accept(Component.translatable(description).setStyle(style));
    }
}