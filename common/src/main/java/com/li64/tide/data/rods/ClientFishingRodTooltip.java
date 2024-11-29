package com.li64.tide.data.rods;

import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientBundleTooltip;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class ClientFishingRodTooltip implements ClientTooltipComponent {
    public static final ResourceLocation TEXTURE_LOCATION = new ResourceLocation("textures/gui/container/bundle.png");
    private static final Component MESSAGE = Component.translatable("text.tide.rod_tooltip.bait_desc");
    private static final int OFFSET_Y = 10;
    private static final int MARGIN_Y = 4;
    private static final int BG_BORDER = 1;
    private static final int SLOT_SIZE_X = 18;
    private static final int SLOT_SIZE_Y = 20;
    private final BaitContents contents;

    public ClientFishingRodTooltip(BaitContents pContents) {
        this.contents = pContents;
    }

    public int getHeight() {
        return this.backgroundHeight() + MARGIN_Y + OFFSET_Y;
    }

    public int getWidth(@NotNull Font font) {
        return Math.max(this.backgroundWidth(), font.width(MESSAGE));
    }

    private int backgroundWidth() {
        return this.gridWidth() * SLOT_SIZE_X + BG_BORDER * 2;
    }

    private int backgroundHeight() {
        return SLOT_SIZE_Y + BG_BORDER * 2;
    }

    public void renderImage(@NotNull Font font, int x, int y, GuiGraphics graphics) {
        int gridWidth = this.gridWidth();

        graphics.drawString(font, MESSAGE, x, y, DyeColor.LIGHT_GRAY.getTextColor());

        for (int i = 0; i < gridWidth; i++) {
            int dspX = x + i * 18 + BG_BORDER;
            int dspY = y + BG_BORDER + OFFSET_Y;

            this.renderSlot(dspX, dspY, i, graphics, font);
        }

        this.drawBorder(x, y + OFFSET_Y, gridWidth, 1, graphics);
    }

    private void renderSlot(int x, int y, int index, GuiGraphics graphics, Font font) {
        if (index >= this.contents.size()) {
            this.blit(graphics, x, y, Texture.SLOT);
        } else {
            ItemStack stack = this.contents.items().get(index);
            this.blit(graphics, x, y, Texture.SLOT);
            graphics.renderItem(stack, x + BG_BORDER, y + BG_BORDER, index);
            graphics.renderItemDecorations(font, stack, x + BG_BORDER, y + BG_BORDER);
            if (index == 0) {
                AbstractContainerScreen.renderSlotHighlight(graphics, x + BG_BORDER, y + BG_BORDER, 0);
            }
        }
    }

    private void drawBorder(int x, int y, int gw, int gh, GuiGraphics guiGraphics) {
        this.blit(guiGraphics, x, y, Texture.BORDER_CORNER_TOP);
        this.blit(guiGraphics, x + gw * 18 + 1, y, Texture.BORDER_CORNER_TOP);

        int m;
        for(m = 0; m < gw; ++m) {
            this.blit(guiGraphics, x + 1 + m * 18, y, Texture.BORDER_HORIZONTAL_TOP);
            this.blit(guiGraphics, x + 1 + m * 18, y + gh * 20, Texture.BORDER_HORIZONTAL_BOTTOM);
        }

        for(m = 0; m < gh; ++m) {
            this.blit(guiGraphics, x, y + m * 20 + 1, Texture.BORDER_VERTICAL);
            this.blit(guiGraphics, x + gw * 18 + 1, y + m * 20 + 1, Texture.BORDER_VERTICAL);
        }

        this.blit(guiGraphics, x, y + gh * 20, Texture.BORDER_CORNER_BOTTOM);
        this.blit(guiGraphics, x + gw * 18 + 1, y + gh * 20, Texture.BORDER_CORNER_BOTTOM);
    }

    private void blit(GuiGraphics guiGraphics, int x, int y, ClientFishingRodTooltip.Texture texture) {
        guiGraphics.blit(TEXTURE_LOCATION, x, y, 0, (float)texture.x, (float)texture.y, texture.w, texture.h, 128, 128);
    }

    private int gridWidth() {
        return Math.max(3, (int)Math.ceil(Math.sqrt((double)this.contents.size() + 1.0)));
    }

    enum Texture {
        SLOT(0, 0, 18, 20),
        BLOCKED_SLOT(0, 40, 18, 20),
        BORDER_VERTICAL(0, 18, 1, 20),
        BORDER_HORIZONTAL_TOP(0, 20, 18, 1),
        BORDER_HORIZONTAL_BOTTOM(0, 60, 18, 1),
        BORDER_CORNER_TOP(0, 20, 1, 1),
        BORDER_CORNER_BOTTOM(0, 60, 1, 1);

        public final int x;
        public final int y;
        public final int w;
        public final int h;

        private Texture(int j, int k, int l, int m) {
            this.x = j;
            this.y = k;
            this.w = l;
            this.h = m;
        }
    }
}
