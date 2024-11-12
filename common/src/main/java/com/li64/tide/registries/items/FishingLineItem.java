package com.li64.tide.registries.items;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import java.util.List;

public class FishingLineItem extends Item {
    private final String color;
    private final MutableComponent translation;
    private final String description;

    public FishingLineItem(String color, MutableComponent translation, Properties properties) {
        this(color, translation, properties, "");
    }

    public FishingLineItem(String color, MutableComponent translation, Properties properties, String description) {
        super(properties);
        this.color = color;
        this.translation = translation;
        this.description = description;
    }

    @Override
    public void appendHoverText(ItemStack stack, Level level, List<Component> components, TooltipFlag flag) {
        super.appendHoverText(stack, level, components, flag);
        Style style = Component.empty().getStyle().withColor(ChatFormatting.GRAY).withItalic(true);
        components.add(Component.translatable(description).setStyle(style));
    }

    public String getColor() {
        return color;
    }

    public MutableComponent getTranslation() {
        return translation;
    }
}
