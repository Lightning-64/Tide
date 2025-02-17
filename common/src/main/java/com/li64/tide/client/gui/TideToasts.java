package com.li64.tide.client.gui;

import com.li64.tide.Tide;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.toasts.Toast;
import net.minecraft.client.gui.components.toasts.ToastManager;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class TideToasts {
    private static final ResourceLocation TEXTURE = ResourceLocation.withDefaultNamespace("toast/recipe");

    public static void display(Toast toast) {
        Minecraft.getInstance().getToastManager().addToast(toast);
    }

    public static class NewPageToast implements Toast {
        private final Component title;
        private final Component description;
        private final ItemStack displayedItem;
        private Toast.Visibility wantedVisibility;

        public NewPageToast(Component title, Component description, ItemStack displayedItem) {
            this.title = title;
            this.description = description;
            this.displayedItem = displayedItem;
            this.wantedVisibility = Visibility.HIDE;
            if (description == null) Tide.LOG.error("Component cannot be null");
        }

        public Toast.@NotNull Visibility getWantedVisibility() {
            return this.wantedVisibility;
        }

        public void update(ToastManager manager, long time) {
            this.wantedVisibility = (double)(time) >= 5000.0D * manager.getNotificationDisplayTimeMultiplier() ? Visibility.HIDE : Visibility.SHOW;
        }

        @Override
        public void render(GuiGraphics graphics, @NotNull Font font, long time) {
            graphics.blitSprite(RenderType::guiTextured, TEXTURE, 0, 0, this.width(), this.height());
            graphics.drawString(font, title, 30, 7, -11534256, false);
            graphics.drawString(font, description, 30, 18, -16777216, false);
            graphics.renderFakeItem(displayedItem, 8, 8);
        }
    }
}