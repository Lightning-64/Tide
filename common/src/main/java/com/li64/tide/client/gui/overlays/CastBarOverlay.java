package com.li64.tide.client.gui.overlays;

import com.li64.tide.Tide;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class CastBarOverlay {
    private static final ResourceLocation BAR_EMPTY_TEX = Tide.resource("textures/gui/fishing/cast_bar_empty.png");
    private static final ResourceLocation BAR_FILLED_TEX = Tide.resource("textures/gui/fishing/cast_bar_filled.png");

    private static float rodChargePercent = 0f;
    private static float timer = 20f;

    public static void render(GuiGraphics graphics, float partialTick) {
        if (timer >= 20f) return;
        timer += Minecraft.getInstance().getDeltaFrameTime();

        float alpha = -Mth.clamp((timer - 10f) / 10f, 0f, 1f) + 1f;

        int texWidth = 24;
        int texHeight = 16;

        int x = (graphics.guiWidth() - texWidth) / 2;
        int y = graphics.guiHeight() / 2 - texHeight - 6;

        int fillWidth = (int) Math.ceil(rodChargePercent * texWidth);

        graphics.setColor(1f, 1f, 1f, alpha);
        graphics.blit(BAR_EMPTY_TEX, x, y, 0, 0, texWidth, texHeight, texWidth, texHeight);
        graphics.blit(BAR_FILLED_TEX, x, y, 0, 0, fillWidth, texHeight, texWidth, texHeight);

        graphics.setColor(1f, 1f, 1f, 1f);
    }

    public static void rodChargeTick(float percent) {
        rodChargePercent = percent;
        timer = 0;
    }
}
