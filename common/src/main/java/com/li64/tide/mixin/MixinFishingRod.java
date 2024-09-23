package com.li64.tide.mixin;

import com.li64.tide.Tide;
import com.li64.tide.registries.items.TideFishingRodItem;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Items.class)
public class MixinFishingRod {
    @Inject(
        method="registerItem(Ljava/lang/String;Lnet/minecraft/world/item/Item;)Lnet/minecraft/world/item/Item;",
        at=@At("HEAD"),
        cancellable=true
    )
    private static void registerItemMixin(String pKey, Item pItem, CallbackInfoReturnable<Item> cir) {
        if (!pKey.equals("fishing_rod")) return;
        Items.registerItem(Tide.resource("vanilla_fishing_rod"), pItem);

        Item fishingRod = new TideFishingRodItem(32, (new Item.Properties()).durability(20));
        Items.registerItem(ResourceLocation.withDefaultNamespace(pKey), fishingRod);
        cir.setReturnValue(fishingRod);
    }
}