package com.li64.tide.data.rods;

import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class ClientFishingRodTooltip implements ClientTooltipComponent {
    private static final ResourceLocation SLOT_BACKGROUND_SPRITE = ResourceLocation.withDefaultNamespace("container/bundle/slot_background");
    private static final ResourceLocation SLOT_HIGHLIGHT_FRONT_SPRITE = ResourceLocation.withDefaultNamespace("container/bundle/slot_highlight_front");
    private static final Component MESSAGE = Component.translatable("text.tide.rod_tooltip.bait_desc");
    private static final int OFFSET_Y = 10;
    private static final int MARGIN_Y = 4;
    private static final int BG_BORDER = 1;
    private static final int SLOT_SIZE_X = 20;
    private static final int SLOT_SIZE_Y = 20;
    private final BaitContents contents;

    public ClientFishingRodTooltip(BaitContents pContents) {
        this.contents = pContents;
    }

    @Override
    public int getHeight(@NotNull Font font) { return this.backgroundHeight() + MARGIN_Y + OFFSET_Y; }

    public int getWidth(@NotNull Font font) {
        return Math.max(this.backgroundWidth(), font.width(MESSAGE));
    }

    private int backgroundWidth() {
        return this.gridWidth() * SLOT_SIZE_X + BG_BORDER * 2;
    }

    private int backgroundHeight() {
        return SLOT_SIZE_Y + BG_BORDER * 2;
    }

    @Override
    public void renderImage(@NotNull Font font, int x, int y, int width, int height, @NotNull GuiGraphics graphics) {
        int gridWidth = this.gridWidth();

        graphics.drawString(font, MESSAGE, x, y, DyeColor.LIGHT_GRAY.getTextColor());

        for (int i = 0; i < gridWidth; i++) {
            int dspX = x + i * SLOT_SIZE_X + BG_BORDER;
            int dspY = y + BG_BORDER + OFFSET_Y;

            this.renderSlot(dspX, dspY, i, graphics, font);
        }
    }

    private void renderSlot(int x, int y, int index, GuiGraphics graphics, Font font) {
        graphics.blitSprite(RenderType::guiTextured, SLOT_BACKGROUND_SPRITE,
                x - 4 + BG_BORDER, y - 4 + BG_BORDER, 24, 24);

        if (index >= this.contents.size()) return;
        ItemStack stack = this.contents.items().get(index);

        graphics.renderItem(stack, x + BG_BORDER, y + BG_BORDER, index);
        graphics.renderItemDecorations(font, stack, x + BG_BORDER, y + BG_BORDER);

        if (index == 0) graphics.blitSprite(RenderType::guiTexturedOverlay, SLOT_HIGHLIGHT_FRONT_SPRITE,
                x - 4 + BG_BORDER, y - 4 + BG_BORDER, 24, 24);
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
