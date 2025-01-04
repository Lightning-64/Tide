package com.li64.tide.data.journal.config;

import com.google.gson.JsonElement;
import com.li64.tide.Tide;
import com.li64.tide.data.TideDataLoader;
import com.li64.tide.data.journal.JournalLayout;
import com.li64.tide.util.TideUtils;
import com.mojang.serialization.Codec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.profiling.ProfilerFiller;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

public class CustomProfileLoader extends TideDataLoader<JournalLayout.Profile> {
    public CustomProfileLoader() {
        super("journal/profiles");
    }

    @Override
    protected void apply(@NotNull Map<ResourceLocation, JsonElement> map, @NotNull ResourceManager manager, @NotNull ProfilerFiller profiler) {
        super.apply(map, manager, profiler);
        Tide.JOURNAL.addProfileConfigs(get());
        TideUtils.PROFILE_ITEMS = null;
    }

    @Override
    protected Codec<JournalLayout.Profile> entryCodec() {
        return JournalLayout.Profile.CODEC;
    }

    @Override
    protected String getDataTypeMessage() {
        return "custom journal profiles";
    }
}