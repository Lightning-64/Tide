package com.li64.tide.client.gui;

import com.li64.tide.Tide;
import com.li64.tide.data.journal.JournalLayout;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class JournalPage {
    private final int id;
    private final String idName;
    private final String title;
    private final String content;
    private final ItemStack icon;
    private final boolean unlockedByDefault;

    public JournalPage(String id, String title, String content, String icon, boolean unlockedByDefault) {
        this.id = id.hashCode(); // return unique integer for the id name
        this.idName = id;
        this.title = title;
        this.content = content;
        this.unlockedByDefault = unlockedByDefault;
        this.icon = BuiltInRegistries.ITEM.get(new ResourceLocation(icon)).getDefaultInstance();
    }

    public JournalPage(JournalLayout.Page pageConfig) {
        this(pageConfig.id(), pageConfig.name(), pageConfig.content(), pageConfig.icon(), pageConfig.unlockedByDefault());
    }

    public int getID() {
        return id;
    }

    public String getIDName() {
        return idName;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public ItemStack getIcon() {
        return icon;
    }

    public boolean isUnlockedByDefault() {
        return unlockedByDefault;
    }

    public List<JournalLayout.Profile> getAllProfiles() {
        List<JournalLayout.Profile> profiles = new ArrayList<>();

        // add the relevant profiles to the list
        Tide.JOURNAL.getProfileConfigs().forEach(config -> {
            if (config.journalPage().hashCode() == this.id) profiles.add(config);
        });

        return profiles;
    }
}
