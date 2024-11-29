package com.li64.tide.data.loot;

import java.util.Arrays;
import java.util.Optional;
import java.util.function.Function;

public enum DepthLayer {
    SURFACE("surface", y -> y >= 50),
    UNDERGROUND("underground", y -> (y < 50 && y >= 0)),
    DEPTHS("depths", y -> y < 0);

    public final String key;
    public final Function<Double, Boolean> predicate;

    DepthLayer(String key, Function<Double, Boolean> predicate) {
        this.key = key;
        this.predicate = predicate;
    }

    public static DepthLayer getLayerAt(double y) {
        for (DepthLayer layer : values()) if (layer.predicate.apply(y)) return layer;
        return DepthLayer.SURFACE;
    }

    public static Optional<DepthLayer> fromKey(String key) {
        return Arrays.stream(values()).filter(layer -> layer.key.matches(key)).findFirst();
    }
}
