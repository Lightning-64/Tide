package com.li64.tide.data.rods;

import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class ClientFishingRodTooltip implements ClientTooltipComponent {
    private static final ResourceLocation BACKGROUND_SPRITE = ResourceLocation.withDefaultNamespace("container/bundle/background");
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
        graphics.blitSprite(BACKGROUND_SPRITE, x, y + OFFSET_Y, this.backgroundWidth(), this.backgroundHeight());

        for (int i = 0; i < gridWidth; i++) {
            int dspX = x + i * 18 + BG_BORDER;
            int dspY = y + BG_BORDER + OFFSET_Y;

            this.renderSlot(dspX, dspY, i, graphics, font);
        }
    }

    private void renderSlot(int x, int y, int index, GuiGraphics graphics, Font font) {
        if (index >= this.contents.size()) {
            this.blit(graphics, x, y);
        } else {
            ItemStack stack = this.contents.items().get(index);
            this.blit(graphics, x, y);
            graphics.renderItem(stack, x + BG_BORDER, y + BG_BORDER, index);
            graphics.renderItemDecorations(font, stack, x + BG_BORDER, y + BG_BORDER);
            if (index == 0) {
                AbstractContainerScreen.renderSlotHighlight(graphics, x + BG_BORDER, y + BG_BORDER, 0);
            }
        }
    }

    private void blit(GuiGraphics pGuiGraphics, int pX, int pY) {
        pGuiGraphics.blitSprite(Texture.SLOT.sprite, pX, pY, 0, Texture.SLOT.w, Texture.SLOT.h);
    }

    private int gridWidth() {
        return Math.max(3, (int)Math.ceil(Math.sqrt((double)this.contents.size() + 1.0)));
    }

    enum Texture {
        SLOT(ResourceLocation.withDefaultNamespace("container/bundle/slot"), 18, 20);

        public final ResourceLocation sprite;
        public final int w;
        public final int h;

        Texture(ResourceLocation pSprite, int pW, int pH) {
            this.sprite = pSprite;
            this.w = pW;
            this.h = pH;
        }
    }
}
