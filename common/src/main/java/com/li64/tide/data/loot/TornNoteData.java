package com.li64.tide.data.loot;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;

import java.util.List;

public record TornNoteData(String id, boolean unlocked) {
    public static List<String> INSTANCES = List.of(
            "empty", "desert_well", "lucky",
            "stars_over_ocean", "the_end"
    );
    public static TornNoteData EMPTY = new TornNoteData(INSTANCES.getFirst());

    public static final Codec<TornNoteData> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.STRING.fieldOf("id").forGetter(TornNoteData::id),
            Codec.BOOL.fieldOf("unlocked").forGetter(TornNoteData::unlocked)
    ).apply(instance, TornNoteData::new));

    public static final StreamCodec<FriendlyByteBuf, TornNoteData> STREAM_CODEC = StreamCodec.of(
            (buf, data) -> { buf.writeUtf(data.id()); buf.writeBoolean(data.unlocked()); },
            (buf) -> new TornNoteData(buf.readUtf(), buf.readBoolean()));

    public TornNoteData(String id) { this(id, false); }
}
