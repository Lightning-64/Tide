package com.li64.tide.registries.items;

import com.li64.tide.data.rods.AccessoryData;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class FishingLineItem extends Item {
    private final String description;

    public FishingLineItem(Properties properties) {
        this(properties, "");
    }

    public FishingLineItem(Properties properties, String description) {
        super(properties);
        this.description = description;
    }

    @Override
    public void appendHoverText(@NotNull ItemStack stack, Level level, @NotNull List<Component> components, @NotNull TooltipFlag flag) {
        super.appendHoverText(stack, level, components, flag);
        if (description.isEmpty()) return;
        Style style = Component.empty().getStyle().withColor(ChatFormatting.GRAY).withItalic(true);
        components.add(Component.translatable(description).setStyle(style));
    }

    public static String getColor(ItemStack stack) {
        AccessoryData data = AccessoryData.get(stack);
        if (data == null || data.color().isEmpty()) return "#d6d6d6";
        return data.color().get();
    }
}
