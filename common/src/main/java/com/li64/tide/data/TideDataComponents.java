package com.li64.tide.data;

import com.li64.tide.Tide;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.util.ExtraCodecs;

import java.util.HashMap;

public class TideDataComponents {
    public static final HashMap<String, DataComponentType<?>> DATA_COMPONENT_TYPES = new HashMap<>();

    public static final DataComponentType<Integer> FISHING_LINE = register(
            "fishing_line", DataComponentType.<Integer>builder()
                    .persistent(ExtraCodecs.intRange(0, 4))
                    .networkSynchronized(ByteBufCodecs.VAR_INT).build());

    public static final DataComponentType<Integer> FISHING_BOBBER = register(
            "fishing_bobber", DataComponentType.<Integer>builder()
                    .persistent(ExtraCodecs.intRange(0, 16))
                    .networkSynchronized(ByteBufCodecs.VAR_INT).build());

    public static final DataComponentType<Integer> FISHING_HOOK = register(
            "fishing_hook", DataComponentType.<Integer>builder()
                    .persistent(ExtraCodecs.intRange(0, 3))
                    .networkSynchronized(ByteBufCodecs.VAR_INT).build());

    public static <T> DataComponentType<T> register(String key, DataComponentType<T> component) {
        DATA_COMPONENT_TYPES.put(key, component);
        return component;
    }

    public static void init() {
        DATA_COMPONENT_TYPES.forEach(Tide.PLATFORM::registerComponentType);
    }
}
