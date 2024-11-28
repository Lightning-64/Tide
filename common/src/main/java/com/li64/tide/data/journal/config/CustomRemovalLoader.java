package com.li64.tide.data.journal.config;

import com.google.common.collect.ImmutableList;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.li64.tide.Tide;
import com.mojang.serialization.Codec;
import com.mojang.serialization.JsonOps;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CustomRemovalLoader extends SimpleJsonResourceReloadListener {
    public static final String DATA_PATH = "journal/removals";
    private static final Gson GSON = new Gson();
    private List<Removal> removals;

    public CustomRemovalLoader() {
        super(GSON, DATA_PATH);
    }

    @Override
    public @NotNull String getName() {
        return "Tide Journal Profile Data Loader";
    }

    @Override
    protected void apply(@NotNull Map<ResourceLocation, JsonElement> map, @NotNull ResourceManager manager, @NotNull ProfilerFiller profiler) {
        List<Removal> output = new ArrayList<>();

        for (Map.Entry<ResourceLocation, JsonElement> entry : map.entrySet()) {
            ResourceLocation entryKey = entry.getKey();

            try {
                Removal.CODEC.parse(JsonOps.INSTANCE, entry.getValue()).result()
                        .ifPresentOrElse(output::add, () -> Tide.LOG.warn("Did not load invalid removal entry {}", entryKey));
            } catch (IllegalArgumentException | JsonParseException parseException) {
                Tide.LOG.error("Parsing error loading journal removal {}", entryKey, parseException);
            }
        }

        removals = ImmutableList.copyOf(output);
        Tide.LOG.info("Loaded {} journal removals", removals.size());

        Tide.JOURNAL.removeProfileConfigs(removals);
    }

    public List<Removal> getRemovalConfigs() {
        return removals;
    }

    public record Removal(String item) {
        public static final Codec<Removal> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                Codec.STRING.fieldOf("item").forGetter(Removal::item)
        ).apply(instance, Removal::new));
    }
}
