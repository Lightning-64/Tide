package com.li64.tide.registries.items;

import com.li64.tide.data.rods.TideAccessoryData;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class FishingHookItem extends Item {
    private final TideAccessoryData data;
    private final String description;

    public FishingHookItem(String textureLocation, String translation, Properties properties) {
        this(textureLocation, translation, properties, "");
    }

    public FishingHookItem(String textureLocation, String translation, Properties properties, String description) {
        super(properties);
        this.data = new TideAccessoryData(translation, textureLocation);
        this.description = description;
    }

    // IMPORTANT: Accessory data MUST be applied here!
    @Override
    public @NotNull ItemStack getDefaultInstance() {
        ItemStack instance = super.getDefaultInstance();
        TideAccessoryData.set(instance, data);
        return instance;
    }

    @Override
    public void appendHoverText(ItemStack stack, Level level, List<Component> components, TooltipFlag flag) {
        super.appendHoverText(stack, level, components, flag);
        Style style = Component.empty().getStyle().withColor(ChatFormatting.GRAY).withItalic(true);
        components.add(Component.translatable(description).setStyle(style));
    }
}
