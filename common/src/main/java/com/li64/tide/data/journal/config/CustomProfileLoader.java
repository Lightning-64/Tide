package com.li64.tide.data.journal.config;

import com.google.common.collect.ImmutableList;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.li64.tide.Tide;
import com.li64.tide.data.journal.JournalLayout;
import com.li64.tide.util.TideUtils;
import com.mojang.serialization.JsonOps;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CustomProfileLoader extends SimpleJsonResourceReloadListener {
    public static final String DATA_PATH = "journal/profiles";
    private static final Gson GSON = new Gson();

    private List<JournalLayout.Profile> profiles;

    public CustomProfileLoader() {
        super(GSON, DATA_PATH);
    }

    @Override
    public @NotNull String getName() {
        return "Tide Journal Profile Data Loader";
    }

    @Override
    protected void apply(@NotNull Map<ResourceLocation, JsonElement> map, @NotNull ResourceManager manager, @NotNull ProfilerFiller profiler) {
        List<JournalLayout.Profile> output = new ArrayList<>();

        for (Map.Entry<ResourceLocation, JsonElement> entry : map.entrySet()) {
            ResourceLocation entryKey = entry.getKey();

            try {
                JournalLayout.Profile.CODEC.parse(JsonOps.INSTANCE, entry.getValue()).result()
                        .ifPresentOrElse(output::add, () -> Tide.LOG.warn("Did not load invalid profile entry {}", entryKey));
            } catch (IllegalArgumentException | JsonParseException parseException) {
                Tide.LOG.error("Parsing error loading custom journal profile {}", entryKey, parseException);
            }
        }

        profiles = ImmutableList.copyOf(output);
        Tide.LOG.info("Loaded {} custom journal profiles", profiles.size());

        Tide.JOURNAL.addProfileConfigs(profiles);
        TideUtils.PROFILE_ITEMS = null;
    }

    public List<JournalLayout.Profile> getProfileConfigs() {
        return profiles;
    }
}
