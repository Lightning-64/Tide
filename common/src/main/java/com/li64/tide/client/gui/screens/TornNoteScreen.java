package com.li64.tide.client.gui.screens;

import com.li64.tide.Tide;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class TornNoteScreen extends Screen {
    private static final ResourceLocation NOTE = Tide.resource("torn_note");
    private static final int WIDTH = 160;
    private static final int HEIGHT = 160;
    private static final int Y_OFFSET = 15;

    public TornNoteScreen() {
        super(Component.translatable("item.tide.torn_note"));
    }

    @Override
    protected void init() {
        super.init();
        this.addRenderableWidget(Button.builder(CommonComponents.GUI_DONE, button -> onClose())
                .bounds(this.width / 2 - 50, this.height - 40, 100, 18)
                .build());
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    @Override
    public void render(@NotNull GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        super.render(graphics, mouseX, mouseY, partialTick);
        int x = (graphics.guiWidth() - WIDTH) / 2;
        int y = (graphics.guiHeight() - HEIGHT) / 2 - Y_OFFSET;
        graphics.blitSprite(NOTE, WIDTH, HEIGHT, 0, 0, x, y, WIDTH, HEIGHT);
    }
}
