package com.li64.tide.data.journal.config;

import com.google.gson.JsonElement;
import com.li64.tide.Tide;
import com.li64.tide.data.TideDataLoader;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.profiling.ProfilerFiller;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

public class CustomRemovalLoader extends TideDataLoader<CustomRemovalLoader.Removal> {
    public CustomRemovalLoader() {
        super("journal/removals");
    }

    @Override
    protected void apply(@NotNull Map<ResourceLocation, JsonElement> map, @NotNull ResourceManager manager, @NotNull ProfilerFiller profiler) {
        super.apply(map, manager, profiler);
        Tide.JOURNAL.removeProfileConfigs(get());
    }

    @Override
    protected Codec<Removal> entryCodec() {
        return Removal.CODEC;
    }

    @Override
    protected String getDataTypeMessage() {
        return "journal removals";
    }

    public record Removal(String item) {
        public static final Codec<Removal> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                Codec.STRING.fieldOf("item").forGetter(Removal::item)
        ).apply(instance, Removal::new));
    }
}
