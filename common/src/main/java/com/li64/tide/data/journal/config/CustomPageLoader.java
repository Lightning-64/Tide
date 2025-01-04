package com.li64.tide.data.journal.config;

import com.google.gson.JsonElement;
import com.li64.tide.Tide;
import com.li64.tide.data.TideDataLoader;
import com.li64.tide.data.journal.JournalLayout;
import com.mojang.serialization.Codec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.profiling.ProfilerFiller;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

public class CustomPageLoader extends TideDataLoader<JournalLayout.Page> {
    public CustomPageLoader() {
        super("journal/pages");
    }

    @Override
    protected void apply(@NotNull Map<ResourceLocation, JsonElement> map, @NotNull ResourceManager manager, @NotNull ProfilerFiller profiler) {
        super.apply(map, manager, profiler);
        Tide.JOURNAL = new JournalLayout();
        Tide.JOURNAL.addPageConfigs(get());
    }

    @Override
    protected Codec<JournalLayout.Page> entryCodec() {
        return JournalLayout.Page.CODEC;
    }

    @Override
    protected String getDataTypeMessage() {
        return "custom journal pages";
    }
}
