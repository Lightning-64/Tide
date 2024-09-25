package com.li64.tide.mixin;

import com.li64.tide.registries.entities.misc.fishing.TideFishingHook;
import net.minecraft.advancements.critereon.FishingHookPredicate;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;

@Mixin(FishingHookPredicate.class)
public class HookPredicateMixin {
    @Shadow @Final private Optional<Boolean> inOpenWater;

    @Inject(method = "matches", at = @At(value = "HEAD"), cancellable = true)
    private void matches(Entity entity, ServerLevel level, Vec3 pos, CallbackInfoReturnable<Boolean> cir) {
        if (entity instanceof TideFishingHook hook) {
            cir.setReturnValue(this.inOpenWater.orElse(true) == hook.isOpenWaterFishing());
        }
    }
}