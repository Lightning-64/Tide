package com.li64.tide.data.journal.config;

import com.google.common.collect.ImmutableList;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.li64.tide.Tide;
import com.li64.tide.data.journal.JournalLayout;
import com.mojang.serialization.JsonOps;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class JournalPageCustomData extends SimpleJsonResourceReloadListener {
    public static final String DATA_PATH = "journal/pages";
    private static final Gson GSON = new Gson();

    private List<JournalLayout.Page> pages;

    public JournalPageCustomData() {
        super(GSON, DATA_PATH);
    }

    @Override
    public @NotNull String getName() {
        return "Tide Journal Page Data Loader";
    }

    @Override
    protected Map<ResourceLocation, JsonElement> prepare(ResourceManager pResourceManager, ProfilerFiller pProfiler) {
        return super.prepare(pResourceManager, pProfiler);

    }

    @Override
    protected void apply(@NotNull Map<ResourceLocation, JsonElement> map, @NotNull ResourceManager manager, @NotNull ProfilerFiller profiler) {
        List<JournalLayout.Page> output = new ArrayList<>();

        for (Map.Entry<ResourceLocation, JsonElement> entry : map.entrySet()) {
            ResourceLocation entryKey = entry.getKey();

            try {
                JournalLayout.Page.CODEC.parse(JsonOps.INSTANCE, entry.getValue()).result()
                        .ifPresentOrElse(output::add, () -> Tide.LOG.warn("Did not load invalid profile entry {}", entryKey));
            } catch (IllegalArgumentException | JsonParseException parseException) {
                Tide.LOG.error("Parsing error loading custom journal page {}", entryKey, parseException);
            }
        }

        pages = ImmutableList.copyOf(output);
        Tide.LOG.info("Loaded {} custom journal pages", pages.size());

        Tide.JOURNAL = new JournalLayout();
        Tide.JOURNAL.addPageConfigs(pages);
    }

    public List<JournalLayout.Page> getPageConfigs() {
        return pages;
    }
}
