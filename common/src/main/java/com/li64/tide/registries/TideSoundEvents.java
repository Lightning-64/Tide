package com.li64.tide.registries;

import com.li64.tide.Tide;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;

import java.util.HashMap;

public class TideSoundEvents {
    public static final HashMap<String, SoundEvent> SOUND_EVENTS = new HashMap<>();

    public static final SoundEvent JOURNAL_OPEN = register("journal_open");
    public static final SoundEvent JOURNAL_CLOSE = register("journal_close");
    public static final SoundEvent PAGE_FLIP = register("page_flip");

    public static SoundEvent register(String key) {
        SoundEvent soundEvent = SoundEvent.createVariableRangeEvent(Tide.resource(key));
        SOUND_EVENTS.put(key, soundEvent);
        return soundEvent;
    }

    public static void init() {
        SOUND_EVENTS.forEach(Tide.PLATFORM::registerSoundEvent);
    }
}
