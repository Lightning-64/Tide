package com.li64.tide.config;

import com.li64.tide.Tide;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;

@Config(name = Tide.MOD_ID)
public final class TideConfig implements ConfigData {
    @ConfigEntry.Category("general")
    @ConfigEntry.Gui.TransitiveObject
    public General general;

    @ConfigEntry.Category("minigame")
    @ConfigEntry.Gui.TransitiveObject
    public Minigame minigame;

    @ConfigEntry.Category("worldgen")
    @ConfigEntry.Gui.TransitiveObject
    public Worldgen worldgen;

    public TideConfig() {
        this.general = new General();
        this.worldgen = new Worldgen();
        this.minigame = new Minigame();
    }

    public static class General {
        @ConfigEntry.Gui.Tooltip
        public boolean giveJournal = true;

        @ConfigEntry.Gui.Tooltip
        public boolean holdToCast = true;

        @ConfigEntry.Gui.Tooltip
        public boolean showUnread = true;

        @ConfigEntry.Gui.Tooltip
        public boolean showToasts = true;

        @ConfigEntry.Gui.Tooltip
        public boolean defaultLineColor = false;

        @ConfigEntry.Gui.Tooltip
        public boolean hideUnknownFishNames = true;

        @ConfigEntry.Gui.Tooltip
        @ConfigEntry.Gui.RequiresRestart
        public double rodDurabilityMultiplier = 1.0;

        @ConfigEntry.Gui.Tooltip
        @ConfigEntry.BoundedDiscrete(min = 0, max = 15)
        @ConfigEntry.Gui.RequiresRestart
        public int crateWeight = 6;

        @ConfigEntry.Gui.Tooltip
        @ConfigEntry.BoundedDiscrete(min = 0, max = 5)
        @ConfigEntry.Gui.RequiresRestart
        public int crateQuality = 1;
    }

    public static class Minigame {
        @ConfigEntry.Gui.Tooltip
        public boolean doMinigame = true;

        @ConfigEntry.Gui.Tooltip
        public boolean doFeedback = true;

        @ConfigEntry.Gui.Tooltip
        public boolean doSuccessSound = true;

        @ConfigEntry.Gui.Tooltip
        public boolean doFailSound = true;

        @ConfigEntry.Gui.Tooltip
        public double minigameDifficulty = 1.0;
    }

    public static class Worldgen {
        @ConfigEntry.Gui.Tooltip
        public boolean disableFishingBoat = false;
    }
}