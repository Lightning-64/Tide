package com.li64.tide.data.loot;

import java.util.List;
import java.util.Random;

public record TornNoteData(String id, boolean unlocked) {
    public static List<String> INSTANCES = List.of(
            "empty", "lucky", "the_end",
            "stars_over_ocean"
    );
    public static TornNoteData EMPTY = new TornNoteData(INSTANCES.get(0));

    public TornNoteData(String id) { this(id, false); }

    public TornNoteData(int id) {
        this(INSTANCES.get(id));
    }

    public static TornNoteData random() {
        return new TornNoteData(new Random().nextInt(1, INSTANCES.size()));
    }
}
