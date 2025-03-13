package com.li64.tide.client.gui.screens;

import com.li64.tide.Tide;
import com.li64.tide.data.loot.TornNoteData;
import com.li64.tide.registries.TideSoundEvents;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

public class TornNoteScreen extends Screen {
    private static final int WIDTH = 160;
    private static final int HEIGHT = 160;
    private static final int Y_OFFSET = 15;

    private final ResourceLocation sprite;

    public TornNoteScreen(TornNoteData variant) {
        super(Component.translatable("item.tide.torn_note"));
        this.sprite = Tide.resource("torn_note/" + variant.id());

        LocalPlayer player = Minecraft.getInstance().player;
        if (player != null) {
            player.playSound(TideSoundEvents.PAGE_FLIP, 1.0f, 1.0f + new Random().nextFloat() * 0.2f);
        }
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
        graphics.blitSprite(sprite, WIDTH, HEIGHT, 0, 0, x, y, WIDTH, HEIGHT);
    }
}
