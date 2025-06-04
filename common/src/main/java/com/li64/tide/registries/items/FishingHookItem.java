package com.li64.tide.registries.items;

import com.li64.tide.Tide;
import net.minecraft.ChatFormatting;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.function.Consumer;

public class FishingHookItem extends Item {
    private final String description;

    public FishingHookItem(Properties properties) {
        this(properties, "");
    }

    public FishingHookItem(Properties properties, String description) {
        super(properties);
        this.description = description;
    }

    @Override @SuppressWarnings("deprecation")
    public void appendHoverText(@NotNull ItemStack stack,
                                @NotNull TooltipContext context,
                                @NotNull TooltipDisplay tooltipDisplay,
                                @NotNull Consumer<Component> tooltipAdder,
                                @NotNull TooltipFlag flag) {

        super.appendHoverText(stack, context, tooltipDisplay, tooltipAdder, flag);

        if (description.isEmpty()) return;
        Style style = Component.empty().getStyle().withColor(ChatFormatting.GRAY).withItalic(true);
        tooltipAdder.accept(Component.translatable(description).setStyle(style));
    }

    public static ResourceLocation getTexture(ItemStack stack) {
        return Tide.resource("textures/entity/fishing_hook/" + BuiltInRegistries.ITEM
                .getKey(stack.getItem()).getPath() + ".png");
    }
}
