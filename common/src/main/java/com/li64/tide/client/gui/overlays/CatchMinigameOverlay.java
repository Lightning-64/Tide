package com.li64.tide.client.gui.overlays;

import com.li64.tide.Tide;
import com.li64.tide.network.messages.MinigameServerMsg;
import com.li64.tide.registries.entities.misc.fishing.HookAccessor;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

import java.util.Random;

public class CatchMinigameOverlay {
    private static final int INIT_DELAY_MILLIS = 400;
    private static final ResourceLocation BAR_BG_TEX = Tide.resource("textures/gui/fishing/catch_bar_bg.png");
    private static final ResourceLocation MARKER_TEX = Tide.resource("textures/gui/fishing/catch_marker.png");
    private static final ResourceLocation SELECT_TEX = Tide.resource("textures/gui/fishing/catch_marker_select.png");

    private static float timeLeft = 80f;
    private static float animProgress = 0f;
    private static float timer = 20f;
    private static boolean isActive = false;
    private static MutableComponent accuracyText = null;
    private static ChatFormatting textColor = ChatFormatting.WHITE;
    private static float fishStrength;
    private static long delayTime;

    private static final float DEFAULT_SPEED = 0.12f;

    public static void start(float strength) {
        if (isActive) return;

        fishStrength = strength;

        accuracyText = null;
        animProgress = new Random().nextFloat() * 100f;

        timeLeft = 0;
        timer = 0;

        isActive = true;
        delayTime = System.currentTimeMillis() + INIT_DELAY_MILLIS;
    }

    public static void interact() {
        if (!isActive || System.currentTimeMillis() < delayTime) return;

        // Calculate accuracy
        float accuracy = Math.abs(Mth.sin(animProgress * getSpeed()));

        String accuracyKey = "minigame.rating.trash";
        float catchChance = 0.0f;
        textColor = ChatFormatting.BLACK;
        if (accuracy < 0.05f) {
            accuracyKey = "minigame.rating.perfect";
            catchChance = 1.0f;
            textColor = ChatFormatting.GOLD;
        }
        else if (accuracy < 0.15f) {
            accuracyKey = "minigame.rating.great";
            catchChance = 1.0f;
            textColor = ChatFormatting.GREEN;
        }
        else if (accuracy < 0.3f) {
            accuracyKey = "minigame.rating.good";
            catchChance = 0.95f;
            textColor = ChatFormatting.DARK_GREEN;
        }
        else if (accuracy < 0.45f) {
            accuracyKey = "minigame.rating.ok";
            catchChance = 0.5f;
            textColor = ChatFormatting.YELLOW;
        }
        else if (accuracy < 0.6f) {
            accuracyKey = "minigame.rating.bad";
            catchChance = 0.4f;
            textColor = ChatFormatting.RED;
        }
        else if (accuracy < 0.78f) {
            accuracyKey = "minigame.rating.terrible";
            catchChance = 0.1f;
            textColor = ChatFormatting.DARK_RED;
        }

        accuracyText = Component.translatable(accuracyKey);

        if (new Random().nextFloat() < catchChance) {
            // Success
            Tide.NETWORK.sendToServer(new MinigameServerMsg(2));
        } else {
            // Failure
            Tide.NETWORK.sendToServer(new MinigameServerMsg(1));
        }
        close();
    }

    public static void close() {
        isActive = false;
        timer = 0f;
    }

    public static float getSpeed() {
        return (fishStrength * 0.048f + DEFAULT_SPEED) * (float) Tide.CONFIG.general.minigameDifficulty;
    }

    public static void render(GuiGraphics graphics, float partialTick) {
        float delta = Minecraft.getInstance().getDeltaFrameTime();

        if (timer >= 20f) return;
        if (!isActive) timer += delta;
        else {
            timeLeft += delta;
            animProgress += delta;

            if (timeLeft >= 80f || HookAccessor.bobberRemoved(Minecraft.getInstance().player)) {
                // Timeout
                close();
                Tide.NETWORK.sendToServer(new MinigameServerMsg(0));
            }
        }

        float alpha = -Mth.clamp((timer - 10f) / 10f, 0f, 1f) + 1f;

        int texWidth = 32;
        int texHeight = 16;

        int x = (graphics.guiWidth() - texWidth) / 2;
        int y = graphics.guiHeight() / 2 - texHeight - 6;

        int markerX = (int) (x + Mth.sin(animProgress * getSpeed()) * (texWidth / 2f - 2));

        graphics.setColor(1f, 1f, 1f, alpha);
        graphics.blit(BAR_BG_TEX, x, y, 0, 0, texWidth, texHeight, texWidth, texHeight);
        graphics.blit(MARKER_TEX, markerX, y, 0, 0, texWidth, texHeight, texWidth, texHeight);

        if (accuracyText == null) return;
        Font font = Minecraft.getInstance().font;

        graphics.drawString(font, accuracyText.withStyle(
                accuracyText.getStyle().withColor(textColor)),
                (graphics.guiWidth() - font.width(accuracyText)) / 2, y - 10,
                0, false);

        graphics.setColor(1f, 1f, 1f, 1f);
    }

    public static boolean isActive() {
        return isActive;
    }
}
