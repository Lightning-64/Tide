package com.li64.tide.registries;

import com.li64.tide.Tide;
import com.li64.tide.registries.worldgen.TideLakeFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;

import java.util.HashMap;

public class TideFeatures {
    public static final HashMap<String, Feature<?>> FEATURES = new HashMap<>();

    public static final Feature<TideLakeFeature.Configuration> TIDE_LAKE = register("tide_lake", new TideLakeFeature(TideLakeFeature.Configuration.CODEC));

    public static <C extends FeatureConfiguration, F extends Feature<C>> F register(String key, F feature) {
        FEATURES.put(key, feature);
        return feature;
    }

    public static void init() {
        FEATURES.forEach(Tide.PLATFORM::registerFeature);
    }
}
