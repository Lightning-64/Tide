package com.li64.tide.registries.worldgen;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

/** Modified copy of net.minecraft.world.level.levelgen.feature.LakeFeature */
@SuppressWarnings("deprecation")
public class TideLakeFeature extends Feature<TideLakeFeature.Configuration> {
    private static final BlockState AIR = Blocks.CAVE_AIR.defaultBlockState();

    public TideLakeFeature(Codec<TideLakeFeature.Configuration> codec) { super(codec); }

    public boolean place(FeaturePlaceContext<TideLakeFeature.Configuration> context) {
        BlockPos origin = context.origin();
        WorldGenLevel worldgenlevel = context.level();
        RandomSource randomsource = context.random();
        TideLakeFeature.Configuration configuration = context.config();
        if (origin.getY() <= worldgenlevel.getMinY() + 4) {
            return false;
        } else {
            origin = origin.below(4);
            boolean[] booleans = new boolean[2048];
            int i = randomsource.nextInt(4) + 4;

            for(int j = 0; j < i; ++j) {
                double d0 = randomsource.nextDouble() * 6.0D + 3.0D;
                double d1 = randomsource.nextDouble() * 4.0D + 2.0D;
                double d2 = randomsource.nextDouble() * 6.0D + 3.0D;
                double d3 = randomsource.nextDouble() * (16.0D - d0 - 2.0D) + 1.0D + d0 / 2.0D;
                double d4 = randomsource.nextDouble() * (8.0D - d1 - 4.0D) + 2.0D + d1 / 2.0D;
                double d5 = randomsource.nextDouble() * (16.0D - d2 - 2.0D) + 1.0D + d2 / 2.0D;

                for(int l = 1; l < 15; ++l) {
                    for(int i1 = 1; i1 < 15; ++i1) {
                        for(int j1 = 1; j1 < 7; ++j1) {
                            double d6 = ((double)l - d3) / (d0 / 2.0D);
                            double d7 = ((double)j1 - d4) / (d1 / 2.0D);
                            double d8 = ((double)i1 - d5) / (d2 / 2.0D);
                            double d9 = d6 * d6 + d7 * d7 + d8 * d8;
                            if (d9 < 1.0D) {
                                booleans[(l * 16 + i1) * 8 + j1] = true;
                            }
                        }
                    }
                }
            }

            BlockState fluid = configuration.fluid().getState(randomsource, origin);

            for(int k1 = 0; k1 < 16; ++k1) {
                for(int k = 0; k < 16; ++k) {
                    for(int l2 = 0; l2 < 8; ++l2) {
                        boolean flag = !booleans[(k1 * 16 + k) * 8 + l2] && (k1 < 15 && booleans[((k1 + 1) * 16 + k) * 8 + l2] || k1 > 0 && booleans[((k1 - 1) * 16 + k) * 8 + l2] || k < 15 && booleans[(k1 * 16 + k + 1) * 8 + l2] || k > 0 && booleans[(k1 * 16 + (k - 1)) * 8 + l2] || l2 < 7 && booleans[(k1 * 16 + k) * 8 + l2 + 1] || l2 > 0 && booleans[(k1 * 16 + k) * 8 + (l2 - 1)]);
                        if (flag) {
                            BlockState state = worldgenlevel.getBlockState(origin.offset(k1, l2, k));
                            if (l2 >= 4 && state.liquid()) {
                                return false;
                            }

                            if (l2 < 4 && !state.isSolid() && worldgenlevel.getBlockState(origin.offset(k1, l2, k)) != fluid) {
                                return false;
                            }
                        }
                    }
                }
            }

            for(int l1 = 0; l1 < 16; ++l1) {
                for(int i2 = 0; i2 < 16; ++i2) {
                    for(int i3 = 0; i3 < 8; ++i3) {
                        if (booleans[(l1 * 16 + i2) * 8 + i3]) {
                            BlockPos pos = origin.offset(l1, i3, i2);
                            if (this.canReplaceBlock(worldgenlevel.getBlockState(pos))) {
                                boolean flag1 = i3 >= 4;
                                worldgenlevel.setBlock(pos, flag1 ? AIR : fluid, 2);
                                if (flag1) {
                                    worldgenlevel.scheduleTick(pos, AIR.getBlock(), 0);
                                    this.markAboveForPostProcessing(worldgenlevel, pos);
                                }
                            }
                        }
                    }
                }
            }

            BlockState state = configuration.barrier().getState(randomsource, origin);
            if (!state.isAir()) {
                for(int j2 = 0; j2 < 16; ++j2) {
                    for(int j3 = 0; j3 < 16; ++j3) {
                        for(int l3 = 0; l3 < 8; ++l3) {
                            boolean flag2 = !booleans[(j2 * 16 + j3) * 8 + l3] && (j2 < 15 && booleans[((j2 + 1) * 16 + j3) * 8 + l3] || j2 > 0 && booleans[((j2 - 1) * 16 + j3) * 8 + l3] || j3 < 15 && booleans[(j2 * 16 + j3 + 1) * 8 + l3] || j3 > 0 && booleans[(j2 * 16 + (j3 - 1)) * 8 + l3] || l3 < 7 && booleans[(j2 * 16 + j3) * 8 + l3 + 1] || l3 > 0 && booleans[(j2 * 16 + j3) * 8 + (l3 - 1)]);
                            if (flag2 && (l3 < 4 || randomsource.nextInt(2) != 0)) {
                                BlockState blockstate = worldgenlevel.getBlockState(origin.offset(j2, l3, j3));
                                if (blockstate.isSolid() && !blockstate.is(BlockTags.LAVA_POOL_STONE_CANNOT_REPLACE)) {
                                    BlockPos pos = origin.offset(j2, l3, j3);
                                    worldgenlevel.setBlock(pos, state, 2);
                                    this.markAboveForPostProcessing(worldgenlevel, pos);
                                }
                            }
                        }
                    }
                }
            }

            return true;
        }
    }

    private boolean canReplaceBlock(BlockState state) {
        return !state.is(BlockTags.FEATURES_CANNOT_REPLACE);
    }

    public record Configuration(BlockStateProvider fluid, BlockStateProvider barrier) implements FeatureConfiguration {
        public static final Codec<TideLakeFeature.Configuration> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                BlockStateProvider.CODEC.fieldOf("fluid").forGetter(Configuration::fluid),
                BlockStateProvider.CODEC.fieldOf("barrier").forGetter(Configuration::barrier)
        ).apply(instance, Configuration::new));
    }
}
