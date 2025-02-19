package com.li64.tide.client.gui.overlays;

import com.li64.tide.Tide;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.ARGB;
import net.minecraft.util.Mth;

public class CastBarOverlay {
    private static final ResourceLocation BAR_EMPTY_TEX = Tide.resource("textures/gui/fishing/cast_bar_empty.png");
    private static final ResourceLocation BAR_FILLED_TEX = Tide.resource("textures/gui/fishing/cast_bar_filled.png");

    private static float rodChargePercent = 0f;
    private static float timer = 20f;

    public static void render(GuiGraphics graphics, DeltaTracker tracker) {
        if (timer >= 20f) return;
        timer += tracker.getRealtimeDeltaTicks();

        float alpha = -Mth.clamp((timer - 10f) / 10f, 0f, 1f) + 1f;

        int texWidth = 24;
        int texHeight = 16;

        int x = (graphics.guiWidth() - texWidth) / 2;
        int y = graphics.guiHeight() / 2 - texHeight - 6;

        int fillWidth = (int) Math.ceil(rodChargePercent * texWidth);

        graphics.blit(RenderType::guiTextured, BAR_EMPTY_TEX, x, y, 0, 0, texWidth, texHeight, texWidth, texHeight, ARGB.white(alpha));
        graphics.blit(RenderType::guiTextured, BAR_FILLED_TEX, x, y, 0, 0, fillWidth, texHeight, texWidth, texHeight, ARGB.white(alpha));
    }

    public static void rodChargeTick(float percent) {
        rodChargePercent = percent;
        timer = 0;
    }
}
