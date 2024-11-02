package com.li64.tide.registries.items;

import com.li64.tide.data.TideDataComponents;
import com.li64.tide.data.rods.TideAccessoryData;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;

public class FishingLineItem extends Item {
    private final String description;

    public FishingLineItem(String color, Component translation, Properties properties) {
        this(color, translation, properties, "");
    }

    public FishingLineItem(String color, Component translation, Properties properties, String description) {
        super(properties.component(TideDataComponents.TIDE_ACCESSORY_DATA,
                new TideAccessoryData(translation, color)));
        this.description = description;
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> components, TooltipFlag flag) {
        super.appendHoverText(stack, context, components, flag);
        Style style = Component.empty().getStyle().withColor(ChatFormatting.GRAY).withItalic(true);
        components.add(Component.translatable(description).setStyle(style));
    }
}
