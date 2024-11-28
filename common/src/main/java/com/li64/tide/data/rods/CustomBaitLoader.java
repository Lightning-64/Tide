package com.li64.tide.data.rods;

import com.google.common.collect.ImmutableList;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.li64.tide.Tide;
import com.li64.tide.registries.TideItems;
import com.mojang.serialization.JsonOps;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.item.Items;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CustomBaitLoader extends SimpleJsonResourceReloadListener {
    public static final String DATA_PATH = "bait";
    private static final Gson GSON = new Gson();

    private List<BaitData> baits;

    public CustomBaitLoader() {
        super(GSON, DATA_PATH);
    }

    @Override
    public @NotNull String getName() {
        return "Tide Custom Bait Data Loader";
    }

    @Override
    protected void apply(@NotNull Map<ResourceLocation, JsonElement> map, @NotNull ResourceManager manager, @NotNull ProfilerFiller profiler) {
        List<BaitData> output = new ArrayList<>();

        for (Map.Entry<ResourceLocation, JsonElement> entry : map.entrySet()) {
            ResourceLocation entryKey = entry.getKey();

            try {
                BaitData.CODEC.parse(JsonOps.INSTANCE, entry.getValue()).result()
                        .ifPresentOrElse(data -> { if (data.getItem() != Items.AIR) output.add(data); },
                                () -> Tide.LOG.warn("Did not load invalid custom bait entry {}", entryKey));
            } catch (IllegalArgumentException | JsonParseException parseException) {
                Tide.LOG.error("Parsing error loading custom bait item {}", entryKey, parseException);
            }
        }

        baits = ImmutableList.copyOf(output);
        Tide.LOG.info("Loaded {} custom bait items", baits.size());
    }

    public List<BaitData> getBaitData() {
        return baits;
    }
}
