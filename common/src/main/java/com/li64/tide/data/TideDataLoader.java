package com.li64.tide.data;

import com.google.common.collect.ImmutableList;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.li64.tide.Tide;
import com.mojang.serialization.Codec;
import com.mojang.serialization.JsonOps;
import net.minecraft.resources.FileToIdConverter;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.util.profiling.ProfilerFiller;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public abstract class TideDataLoader<T> extends SimpleJsonResourceReloadListener<JsonElement> {
    private List<T> values = new ArrayList<>();
    private final String directory;

    public TideDataLoader(String directory) {
        super(ExtraCodecs.JSON, FileToIdConverter.json(directory));
        this.directory = directory;
    }

    protected abstract Codec<T> entryCodec();
    protected abstract String getDataTypeMessage();

    @Override
    protected void apply(@NotNull Map<ResourceLocation, JsonElement> map, @NotNull ResourceManager manager, @NotNull ProfilerFiller profiler) {
        ArrayList<T> output = new ArrayList<>();

        for (Map.Entry<ResourceLocation, JsonElement> entry : map.entrySet()) {
            ResourceLocation entryKey = entry.getKey();

            try {
                entryCodec().parse(JsonOps.INSTANCE, entry.getValue()).result()
                        .ifPresentOrElse(value -> add(value, output), () -> postInvalidEntryMessage(entryKey));
            } catch (IllegalArgumentException | JsonParseException parseException) {
                Tide.LOG.error("Parsing error during tide data loading for entry " + entryKey, parseException);
            }
        }

        values = ImmutableList.copyOf(output);
        Tide.LOG.info("Loaded {} {}", values.size(), getDataTypeMessage());
    }

    protected void postInvalidEntryMessage(ResourceLocation entryKey) {
        Tide.LOG.warn("Skipped loading invalid data entry {}", entryKey);
    }

    protected void add(T value, ArrayList<T> list) {
        list.add(value);
    }

    public List<T> get() {
        return values;
    }

    public void set(List<T> values) {
        this.values = new ArrayList<>(values);
    }

    public ResourceLocation getDirectory() {
        return Tide.resource(directory);
    }
}