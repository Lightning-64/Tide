package com.li64.tide.data.loot;

import java.util.List;
import java.util.Random;

public record TornNoteData(String id, boolean unlocked) {
    public static List<String> INSTANCES = List.of(
            "empty", "desert_well", "lucky",
            "stars_over_ocean", "the_end"
    );
    public static TornNoteData EMPTY = new TornNoteData(INSTANCES.get(0));

    public TornNoteData(String id) { this(id, false); }

    public static TornNoteData random() {
        return new TornNoteData(INSTANCES.get(new Random().nextInt(1, INSTANCES.size())));
    }
}
