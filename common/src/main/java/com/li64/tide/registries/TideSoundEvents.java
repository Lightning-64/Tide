package com.li64.tide.registries;

import com.li64.tide.Tide;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.sounds.SoundEvent;

import java.util.HashMap;

public class TideSoundEvents {
    public static final HashMap<String, SoundEvent> SOUND_EVENTS = new HashMap<>();

    public static final SoundEvent JOURNAL_OPEN = register("journal_open");
    public static final SoundEvent JOURNAL_CLOSE = register("journal_close");
    public static final SoundEvent PAGE_FLIP = register("page_flip");

    public static SoundEvent register(String key) {
        SoundEvent soundEvent = SoundEvent.createVariableRangeEvent(Tide.resource(key));
        SOUND_EVENTS.put(key, soundEvent);
        return Registry.register(BuiltInRegistries.SOUND_EVENT, Tide.resource(key), soundEvent);
    }

    public static void init() {
    }
}
