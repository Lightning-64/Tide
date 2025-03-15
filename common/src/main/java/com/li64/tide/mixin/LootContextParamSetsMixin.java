package com.li64.tide.mixin;

import com.google.common.collect.BiMap;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.context.ContextKeySet;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.function.Consumer;

@Mixin(LootContextParamSets.class)
public class LootContextParamSetsMixin {
    @Shadow @Final private static BiMap<ResourceLocation, ContextKeySet> REGISTRY;

    @Inject(at = @At(value = "HEAD"), method = "register", cancellable = true)
    private static void openItemGui(String name, Consumer<ContextKeySet.Builder> constructor, CallbackInfoReturnable<ContextKeySet> cir) {
        if (name.matches("fishing")) {
            constructor = builder -> {
                builder.required(LootContextParams.ORIGIN)
                        .required(LootContextParams.TOOL)
                        .optional(LootContextParams.THIS_ENTITY)
                        .optional(LootContextParams.BLOCK_STATE); // This entire mixin is just to add this line :(
            };
            ContextKeySet.Builder builder = new ContextKeySet.Builder();
            constructor.accept(builder);
            ContextKeySet keySet = builder.build();
            ResourceLocation registry = ResourceLocation.withDefaultNamespace(name);
            ContextKeySet newKeySet = REGISTRY.put(registry, keySet);
            if (newKeySet != null) {
                throw new IllegalStateException("Loot table parameter set " + registry + " is already registered");
            } else {
                cir.setReturnValue(keySet);
            }
        }
    }
}
