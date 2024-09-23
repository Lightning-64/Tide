package com.li64.tide.client.gui;

import com.li64.tide.Tide;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.toasts.Toast;
import net.minecraft.client.gui.components.toasts.ToastComponent;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class TideToasts {
    private static final ResourceLocation TEXTURE = ResourceLocation.withDefaultNamespace("toast/recipe");

    public static void display(Toast toast) {
        Minecraft.getInstance().getToasts().addToast(toast);
    }

    public static class NewPageToast implements Toast {
        private final Component title;
        private final Component description;
        private final ItemStack displayedItem;

        public NewPageToast(Component title, Component description, ItemStack displayedItem) {
            this.title = title;
            this.description = description;
            this.displayedItem = displayedItem;
            if (description == null) Tide.LOG.error("Component cannot be null");
        }

        @Override
        public @NotNull Visibility render(GuiGraphics graphics, ToastComponent component, long time) {
            graphics.blitSprite(TEXTURE, 0, 0, this.width(), this.height());
            graphics.drawString(component.getMinecraft().font, title, 30, 7, -11534256, false);
            graphics.drawString(component.getMinecraft().font, description, 30, 18, -16777216, false);
            graphics.renderFakeItem(displayedItem, 8, 8);
            return (double)(time) >= 5000.0D * component.getNotificationDisplayTimeMultiplier() ? Visibility.HIDE : Visibility.SHOW;
        }
    }
}