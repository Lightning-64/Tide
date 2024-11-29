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

    @ConfigEntry.Category("worldgen")
    @ConfigEntry.Gui.TransitiveObject
    public Worldgen worldgen;

    public TideConfig() {
        this.general = new General();
        this.worldgen = new Worldgen();
    }

    public static class General {
        @ConfigEntry.Gui.Tooltip
        public boolean giveJournal = true;

        @ConfigEntry.Gui.Tooltip
        public boolean holdToCast = true;

        @ConfigEntry.Gui.Tooltip
        public boolean doMinigame = true;

        @ConfigEntry.Gui.Tooltip
        public boolean showUnread = true;

        @ConfigEntry.Gui.Tooltip
        public boolean showToasts = true;

        @ConfigEntry.Gui.Tooltip
        public double minigameDifficulty = 1.0;

        @ConfigEntry.Gui.Tooltip
        @ConfigEntry.Gui.RequiresRestart
        public double rodDurabilityMultiplier = 1.0;

        @ConfigEntry.Gui.Tooltip
        public boolean defaultLineColor = false;

        @ConfigEntry.Gui.Tooltip
        public boolean hideUnknownFishNames = true;
    }

    public static class Worldgen {
        @ConfigEntry.Gui.Tooltip
        public boolean disableFishingBoat = false;
    }
}