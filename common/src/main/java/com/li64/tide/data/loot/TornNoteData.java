package com.li64.tide.data.loot;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import java.util.List;

public record TornNoteData(String id, boolean unlocked) {
    public static List<String> INSTANCES = List.of(
            "empty", "desert_well", "lucky",
            "stars_over_ocean", "the_end"
    );
    public static TornNoteData EMPTY = new TornNoteData(INSTANCES.get(0));

    public static final Codec<TornNoteData> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.STRING.fieldOf("id").forGetter(TornNoteData::id),
            Codec.BOOL.fieldOf("unlocked").forGetter(TornNoteData::unlocked)
    ).apply(instance, TornNoteData::new));

    public TornNoteData(String id) { this(id, false); }
}
