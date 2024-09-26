package com.li64.tide.client.gui;

import com.li64.tide.Tide;
import com.li64.tide.data.journal.JournalLayout;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public record JournalPage(String idName, String title, String content, ItemStack icon, boolean unlockedByDefault) {
    public JournalPage(JournalLayout.Page pageConfig) {
        this(pageConfig.id(), pageConfig.name(), pageConfig.content(), BuiltInRegistries.ITEM.get(new ResourceLocation(pageConfig.icon())).getDefaultInstance(), pageConfig.unlockedByDefault());
    }

    public int id() {
        return idName.hashCode();
    }

    public List<JournalLayout.Profile> getAllProfiles() {
        List<JournalLayout.Profile> profiles = new ArrayList<>();

        // add the relevant profiles to the list
        Tide.JOURNAL.getProfileConfigs().forEach(config -> {
            if (config.journalPage().hashCode() == this.id()) profiles.add(config);
        });

        return profiles;
    }
}
