package com.li64.tide.datagen.providers.worldgen;

import com.li64.tide.Tide;
import com.li64.tide.registries.TideFeatures;
import com.li64.tide.registries.worldgen.TideLakeFeature;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

public class TideConfiguredFeatures {
    public static final ResourceKey<ConfiguredFeature<?, ?>> END_OASIS = key("end_oasis");

    private static ResourceKey<ConfiguredFeature<?, ?>> key(String path) {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, Tide.resource(path));
    }

    public static void bootstrap(BootstapContext<ConfiguredFeature<?, ?>> context) {
        FeatureUtils.register(context, END_OASIS, TideFeatures.TIDE_LAKE,
                new TideLakeFeature.Configuration(
                        BlockStateProvider.simple(Blocks.WATER),
                        BlockStateProvider.simple(Blocks.END_STONE)
                )
        );
    }
}