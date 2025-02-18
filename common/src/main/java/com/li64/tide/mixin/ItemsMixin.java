package com.li64.tide.mixin;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.function.Function;

import static com.li64.tide.registries.TideItems.ITEM_OVERRIDE_MAP;

@Mixin(Items.class)
public abstract class ItemsMixin {
    @Inject(at = @At("HEAD"), method = "registerItem(Lnet/minecraft/resources/ResourceKey;Ljava/util/function/Function;Lnet/minecraft/world/item/Item$Properties;)Lnet/minecraft/world/item/Item;", cancellable = true)
    private static void registerItem(ResourceKey<Item> key, Function<Item.Properties, Item> factory, Item.Properties properties, CallbackInfoReturnable<Item> cir) {
        if (ITEM_OVERRIDE_MAP.containsKey(key.location())) {
            Item item = ITEM_OVERRIDE_MAP.get(key.location()).apply(properties.setId(key));
            if (item instanceof BlockItem blockItem) blockItem.registerBlocks(Item.BY_BLOCK, item);
            cir.setReturnValue(Registry.register(BuiltInRegistries.ITEM, key, item));
        }
    }
}
