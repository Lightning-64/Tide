package com.li64.tide.data.rods;

import com.li64.tide.Tide;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class ClientFishingRodTooltip implements ClientTooltipComponent {
    private static final ResourceLocation SLOT_BACKGROUND_SPRITE = Tide.resource("textures/gui/sprites/container/bait/slot_background.png");
    private static final ResourceLocation SLOT_HIGHLIGHT_FRONT_SPRITE = Tide.resource("textures/gui/sprites/container/bait/slot_highlight_front.png");
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

    @Override
    public void renderImage(@NotNull Font font, int x, int y, @NotNull GuiGraphics graphics) {
        int gridWidth = this.gridWidth();

        graphics.drawString(font, MESSAGE, x, y, DyeColor.LIGHT_GRAY.getTextColor());

        for (int i = 0; i < gridWidth; i++) {
            int dspX = x + i * SLOT_SIZE_X + BG_BORDER;
            int dspY = y + BG_BORDER + OFFSET_Y;

            this.renderSlot(dspX, dspY, i, graphics, font);
        }
    }

    private void renderSlot(int x, int y, int index, GuiGraphics graphics, Font font) {
        graphics.blit(SLOT_BACKGROUND_SPRITE, x - 4 + BG_BORDER, y - 4 + BG_BORDER,
                0, 0, 24 ,24, 24, 24);

        if (index >= this.contents.size()) return;
        ItemStack stack = this.contents.items().get(index);

        graphics.renderItem(stack, x + BG_BORDER, y + BG_BORDER, index);
        graphics.renderItemDecorations(font, stack, x + BG_BORDER, y + BG_BORDER);

        RenderSystem.enableBlend();
        if (index == 0) graphics.blit(SLOT_HIGHLIGHT_FRONT_SPRITE, x - 4 + BG_BORDER,
                y - 4 + BG_BORDER, 0, 0, 24, 24, 24, 24);
        RenderSystem.disableBlend();
    }

    private int gridWidth() {
        return Math.max(3, (int)Math.ceil(Math.sqrt((double)this.contents.size() + 1.0)));
    }
}