package com.li64.tide.client.gui.screens;

import com.li64.tide.Tide;
import com.li64.tide.client.gui.menus.AnglerWorkshopMenu;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.ItemCombinerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class AnglerWorkshopScreen extends ItemCombinerScreen<AnglerWorkshopMenu> {
    public static final ResourceLocation GUI_LOCATION = Tide.resource("textures/gui/container/angler_workshop.png");

    public AnglerWorkshopScreen(AnglerWorkshopMenu menu, Inventory inventory, Component component) {
        super(menu, inventory, component, GUI_LOCATION);
        this.titleLabelX = 56;
        this.titleLabelY = 8;
    }

    @Override
    protected void renderErrorIcon(GuiGraphics graphics, int p_266822_, int p_267045_) {}
}
