package com.li64.tide.data;

import com.li64.tide.Tide;
import com.li64.tide.data.rods.TideAccessoryData;
import com.mojang.serialization.Codec;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.HashMap;

public class TideDataComponents {
    public static final HashMap<String, DataComponentType<?>> DATA_COMPONENT_TYPES = new HashMap<>();

    public static final DataComponentType<String> FISHING_LINE = register(
            "fishing_line", DataComponentType.<String>builder()
                    .persistent(Codec.STRING)
                    .networkSynchronized(ByteBufCodecs.STRING_UTF8).build());

    public static final DataComponentType<String> FISHING_BOBBER = register(
            "fishing_bobber", DataComponentType.<String>builder()
                    .persistent(Codec.STRING)
                    .networkSynchronized(ByteBufCodecs.STRING_UTF8).build());

    public static final DataComponentType<String> FISHING_HOOK = register(
            "fishing_hook", DataComponentType.<String>builder()
                    .persistent(Codec.STRING)
                    .networkSynchronized(ByteBufCodecs.STRING_UTF8).build());

    public static final DataComponentType<TideAccessoryData> TIDE_ACCESSORY_DATA = register(
            "tide_accessory_data", DataComponentType.<TideAccessoryData>builder()
                    .persistent(TideAccessoryData.CODEC)
                    .networkSynchronized(TideAccessoryData.STREAM_CODEC).cacheEncoding().build());

    public static <T> DataComponentType<T> register(String key, DataComponentType<T> component) {
        DATA_COMPONENT_TYPES.put(key, component);
        return component;
    }

    public static void init() {
        DATA_COMPONENT_TYPES.forEach(Tide.PLATFORM::registerComponentType);
    }
}
