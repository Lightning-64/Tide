package com.li64.tide.mixin;

import com.li64.tide.Tide;
import net.minecraft.core.*;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.PlacementContext;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlacedFeature.class)
public abstract class PlacedFeatureMixin {
    @Shadow public abstract Holder<ConfiguredFeature<?, ?>> feature();

    @Inject(method = "placeWithContext", at = @At("HEAD"), cancellable = true)
    public void placeWithContextOverride(PlacementContext context, RandomSource source, BlockPos pos, CallbackInfoReturnable<Boolean> cir) {
        if (feature().is(Tide.resource("end_oasis")) && Tide.CONFIG.worldgen.disableEndOases) cir.setReturnValue(false);
    }
}
