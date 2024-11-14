package com.li64.tide.data;

import com.li64.tide.Tide;
import com.li64.tide.data.rods.CustomRodManager;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.codec.ByteBufCodecs;

import java.util.HashMap;

public class TideDataComponents {
    public static final HashMap<String, DataComponentType<?>> DATA_COMPONENT_TYPES = new HashMap<>();

    public static final DataComponentType<CompoundTag> FISHING_LINE = register(
            "fishing_line", DataComponentType.<CompoundTag>builder()
                    .persistent(CompoundTag.CODEC)
                    .networkSynchronized(ByteBufCodecs.COMPOUND_TAG).build());

    public static final DataComponentType<CompoundTag> FISHING_BOBBER = register(
            "fishing_bobber", DataComponentType.<CompoundTag>builder()
                    .persistent(CompoundTag.CODEC)
                    .networkSynchronized(ByteBufCodecs.COMPOUND_TAG).build());

    public static final DataComponentType<CompoundTag> FISHING_HOOK = register(
            "fishing_hook", DataComponentType.<CompoundTag>builder()
                    .persistent(CompoundTag.CODEC)
                    .networkSynchronized(ByteBufCodecs.COMPOUND_TAG).build());

    public static <T> DataComponentType<T> register(String key, DataComponentType<T> component) {
        DATA_COMPONENT_TYPES.put(key, component);
        return component;
    }

    public static void init() {
        DATA_COMPONENT_TYPES.forEach(Tide.PLATFORM::registerComponentType);
    }
}
