package com.li64.tide.client.gui;

import com.li64.tide.client.gui.overlays.CastBarOverlay;
import com.li64.tide.client.gui.overlays.CatchMinigameOverlay;
import net.minecraft.client.gui.GuiGraphics;

public class TideGuiOverlays {
    public static void render(GuiGraphics guiGraphics, float partialTick) {
        CatchMinigameOverlay.render(guiGraphics, partialTick);
        CastBarOverlay.render(guiGraphics, partialTick);
    }
}
