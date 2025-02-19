package com.li64.tide.client.gui.screens;

import com.li64.tide.Tide;
import com.li64.tide.client.gui.JournalPage;
import com.li64.tide.data.journal.JournalLayout;
import com.li64.tide.data.player.TidePlayerData;
import com.li64.tide.network.messages.ReadProfileMsg;
import com.li64.tide.registries.TideSoundEvents;
import com.li64.tide.util.TideUtils;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.GameNarrator;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractButton;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class FishingJournalScreen extends Screen {
    private static final int SLOT_SIZE = 22;
    private static final int SLOT_MARGIN = 4;
    private static final ResourceLocation PROFILE_BG = Tide.resource("journal_profile");
    private int numRows;
    private int numCols;
    private int totalSlots;
    private ArrayList<JournalPage> pages;
    public ItemStack profileFish = null;
    public JournalLayout.Profile profileConfig = null;
    public int page;
    public Player player;

    public FishingJournalScreen(Player player) {
        super(GameNarrator.NO_TITLE);
        this.player = player;
        loadJournalPages();
        init();

        player.playSound(TideSoundEvents.JOURNAL_OPEN, 0.9f, 1.0f + new Random().nextFloat() * 0.2f);
    }

    private void loadJournalPages() {
        try {
            pages = new ArrayList<>();
            Tide.JOURNAL.getPageConfigs().stream()
                    .map(JournalPage::new).forEach(page -> pages.add(page));
        } catch (Exception e) {
            Tide.LOG.error("Exception in loading journal pages: {}, {}", e.getMessage(), e.getCause());
        }

        pages.removeIf(journalPage -> !TidePlayerData.CLIENT_DATA.hasPageUnlocked(journalPage));

        // Remove welcome page if other pages are unlocked
        if (pages.size() > 1) pages.removeFirst();
    }

    private void nextPage(int page) {
        clearWidgets();
        this.page = page;
        init();
        player.playSound(TideSoundEvents.PAGE_FLIP, 1.0f, 1.0f + new Random().nextFloat() * 0.2f);
    }

    @Override
    protected void init() {
        this.totalSlots = getPage().getAllProfiles().size();
        this.numCols = calculateNumCols(totalSlots);
        this.numRows = calculateNumRows(totalSlots);

        clearWidgets();
        if (profileFish != null) {
            this.createProfileControls();
        } else {
            this.createMenuControls();
            this.createFishIcons();
        }
    }

    @Override
    public void onClose() {
        super.onClose();
        player.playSound(TideSoundEvents.JOURNAL_CLOSE, 0.9f, 1.0f + new Random().nextFloat() * 0.2f);
    }

    private void createFishIcons() {
        int startX = (this.width - getTotalWidth()) / 2;
        int startY = (this.height - getTotalHeight()) / 2;

        List<JournalLayout.Profile> profiles = getPage().getAllProfiles();

        for (int i = 0; i < totalSlots && i < profiles.size(); i++) {
            int row = i / numCols;
            int col = i % numCols;
            int x = startX + col * (SLOT_SIZE + SLOT_MARGIN);
            int y = startY + row * (SLOT_SIZE + SLOT_MARGIN) - 26;

            JournalLayout.Profile profile = profiles.get(i);
            if (!profile.getFish().isEmpty()) {
                this.addRenderableWidget(new FishButton(x, y, SLOT_SIZE, SLOT_SIZE, profile, this));
            }
        }
    }

    private JournalPage getPage() {
        return pages.get(page);
    }

    private int calculateNumRows(int slots) {
        return Mth.ceil((float) slots / numCols);
    }

    private int calculateNumCols(int slots) {
        return Math.min(12, slots);
    }

    private int getTotalWidth() {
        return numCols * (SLOT_SIZE + SLOT_MARGIN) - SLOT_MARGIN;
    }

    private int getTotalHeight() {
        return numRows * (SLOT_SIZE + SLOT_MARGIN) - SLOT_MARGIN;
    }

    private void createMenuControls() {
        this.addRenderableWidget(Button.builder(CommonComponents.GUI_DONE, button -> onClose())
                .bounds(this.width / 2 - 80, this.height - 40, 160, 18)
                .build());

        Button rightButton = Button.builder(Component.literal("<<"), button -> this.pageLeft())
                .bounds(this.width / 2 - 110, this.height - 40, 26, 18)
                .build();
        rightButton.active = page > 0;
        this.addRenderableWidget(rightButton);

        Button leftButton = Button.builder(Component.literal(">>"), button -> this.pageRight())
                .bounds(this.width / 2 + 84, this.height - 40, 26, 18)
                .build();
        leftButton.active = page < pages.size() - 1;
        this.addRenderableWidget(leftButton);
    }

    private void createProfileControls() {
        this.addRenderableWidget(Button.builder(CommonComponents.GUI_BACK, button -> closeProfile())
                .bounds(this.width / 2 - 80, this.height - 40, 160, 18)
                .build());
    }

    private void closeProfile() {
        this.profileConfig = null;
        this.profileFish = null;
        init();
        player.playSound(TideSoundEvents.PAGE_FLIP, 1.0f, 1.0f + new Random().nextFloat() * 0.2f);
    }

    public void pageLeft() {
        if (page > 0) {
            nextPage(page - 1);
        }
    }

    public void pageRight() {
        if (page < pages.size() - 1) {
            nextPage(page + 1);
        }
    }

    @Override
    public void render(@NotNull GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        RenderSystem.setShaderColor(1f, 1f, 1f, 1f);
        super.render(graphics, mouseX, mouseY, partialTick);

        if (profileFish == null) {
            Component title = Component.translatable(getPage().title());
            Component content = Component.translatable(getPage().content());
            List<FormattedCharSequence> formattedContent = font.split(content, 300);

            int titleX = (this.width - font.width(title)) / 2;
            int titleY = 30;
            if (page == 0) titleY = 70;
            int contentX = (this.width - 300) / 2;
            int contentY = (this.height) - 90;
            if (page == 0) contentY = (this.height) - 125;

            graphics.drawString(font, title.getString(), titleX, titleY, 16777215);
            for (int i = 0; i < formattedContent.size(); i++) {
                graphics.drawString(font, formattedContent.get(i), contentX, contentY + (10 * i), 16777215);
            }
        } else {
            renderProfile(graphics);
        }
    }

    public void openProfile(JournalLayout.Profile profile) {
        profileConfig = profile;
        profileFish = BuiltInRegistries.ITEM.get(ResourceLocation.read(profile.fishItem()).getOrThrow()).orElseThrow().value().getDefaultInstance();
        player.playSound(TideSoundEvents.PAGE_FLIP, 1.0f, 1.0f + new Random().nextFloat() * 0.2f);
        Tide.NETWORK.sendToServer(new ReadProfileMsg(profileFish));
        init();
    }

    public static class FishButton extends AbstractButton {
        private static final ResourceLocation slotTex = Tide.resource("textures/gui/journal/fish_border.png");
        private static final ResourceLocation slotHoverTex = Tide.resource("textures/gui/journal/fish_border_select.png");
        private static final ResourceLocation unreadTex = Tide.resource("textures/gui/journal/unread_icon.png");
        private static final ResourceLocation unreadHoverTex = Tide.resource("textures/gui/journal/unread_icon_select.png");
        private final int x;
        private final int y;
        private final int w;
        private final int h;
        private final JournalLayout.Profile profile;
        private final FishingJournalScreen parent;

        public FishButton(int x, int y, int w, int h, JournalLayout.Profile profile, FishingJournalScreen parent) {
            super(x, y, w, h, GameNarrator.NO_TITLE);
            this.x = x;
            this.y = y;
            this.w = w;
            this.h = h;
            this.profile = profile;
            this.parent = parent;
        }

        @Override
        public void onPress() {
            if (TidePlayerData.CLIENT_DATA.hasFishUnlocked(profile.getFish())) parent.openProfile(profile);
        }

        @Override
        public void renderWidget(GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
            ResourceLocation texture = isMouseOver(mouseX, mouseY) ? slotHoverTex : slotTex;
            graphics.blit(RenderType::guiTextured, texture, x, y, 0, 0, w, h, w, h);

            if (!profile.getFish().isEmpty()) {
                if (!TidePlayerData.CLIENT_DATA.hasFishUnlocked(profile.getFish())) {
                    // Render item silhouette
                    graphics.flush();
                    RenderSystem.setShaderColor(0, 0, 0, 1.0f);

                    graphics.renderItem(profile.getFish(), x + 3, y + 3);

                    graphics.flush();
                    RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
                } else {
                    graphics.renderItem(profile.getFish(), x + 3, y + 3);
                }
            }

            if (!Tide.CONFIG.general.showUnread) return;
            ResourceLocation unreadTexture = isMouseOver(mouseX, mouseY) ? unreadHoverTex : unreadTex;
            if (TidePlayerData.CLIENT_DATA.isUnread(profile))
                graphics.blit(RenderType::guiTextured, unreadTexture, x - 1, y - 1, 0, 0, 7, 7, 7, 7);
        }

        @Override
        protected void updateWidgetNarration(@NotNull NarrationElementOutput output) {}
    }

    public void renderProfile(GuiGraphics graphics) {
        int bgWidth = 291;
        int bgHeight = 202;
        int offsetY = 20;

        graphics.blitSprite(RenderType::guiTextured, PROFILE_BG, (this.width - bgWidth) / 2, (this.height - bgHeight) / 2 - offsetY, bgWidth, bgHeight);

        Style underlined = Component.empty().getStyle().withUnderlined(true);

        Component descriptionRaw = Component.translatable(profileConfig.description());
        Component locationTitleRaw = Component.translatable("profile.info.location").withStyle(underlined);
        Component locationRaw = Component.translatable("profile.info.location." + profileConfig.location());
        Component climateTitleRaw = Component.translatable("profile.info.climate").withStyle(underlined);
        Component climateRaw = Component.translatable("profile.info.climate." + profileConfig.climate());

        List<FormattedCharSequence> description = font.split(descriptionRaw, 114);
        boolean isDescriptionEmpty = descriptionRaw.getString().isEmpty()
                || descriptionRaw.getString().contains("profile.item.");

        List<FormattedCharSequence> location = new ArrayList<>();
        location.addAll(font.split(locationTitleRaw, 114));
        location.addAll(font.split(locationRaw, 114));

        List<FormattedCharSequence> climate = new ArrayList<>();
        climate.addAll(font.split(climateTitleRaw, 114));
        climate.addAll(font.split(climateRaw, 114));

        // Remove the "raw" text in the name of the fish
        Component fishName = TideUtils.removeRawTextInName(profileFish.getHoverName());

        float scaleAmount = 2f;
        int titleX = (this.width - font.width(fishName) - (bgWidth / 2)) / 2;
        int imageX = (int) ((this.width - 16 * scaleAmount - (float) (bgWidth / 2)) / 2);

        int leftContentX = (this.width - (bgWidth / 2)) / 2;
        int rightContentX = (this.width + (bgWidth / 2)) / 2;

        // Roughly center the content if there's no description
        int rightTop = (this.height - 204) / 2 - 6;
        int leftTop = rightTop + (isDescriptionEmpty ? 55 : 0);

        graphics.drawString(font, fishName, titleX, leftTop, 0, false);

        if (!isDescriptionEmpty) {
            for (int i = 0; i < description.size(); i++) {
                graphics.drawString(font, description.get(i),
                        leftContentX - 56, leftTop + 46 + (11 * i),
                        0, false);
            }
        }

        for (int i = 0; i < location.size(); i++) {
            FormattedCharSequence charSequence = location.get(i);
            graphics.drawString(font, charSequence,
                    rightContentX - font.width(charSequence) / 2, rightTop + 46 + (11 * i),
                    0, false);
        }

        for (int i = 0; i < climate.size(); i++) {
            FormattedCharSequence charSequence = climate.get(i);
            graphics.drawString(font, charSequence,
                    rightContentX - font.width(charSequence) / 2, rightTop + 86 + (11 * i),
                    0, false);
        }

        graphics.pose().scale(scaleAmount, scaleAmount, scaleAmount);
        graphics.renderItem(profileFish, (int)(imageX / scaleAmount), (int)((leftTop + 11) / scaleAmount));
        graphics.pose().scale(1 / scaleAmount, 1 / scaleAmount, 1 / scaleAmount);
    }
}