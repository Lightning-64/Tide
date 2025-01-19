package com.li64.tide.mixin;

import com.li64.tide.registries.items.TideFishingRodItem;
import net.minecraft.world.item.FishingRodItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;

@Mixin(Items.class)
public abstract class ItemsMixin {
    @Redirect(slice = @Slice(from = @At(value = "CONSTANT", args= "stringValue=fishing_rod", ordinal = 0)),
            at = @At(value = "NEW", target = "(Lnet/minecraft/world/item/Item$Properties;)Lnet/minecraft/world/item/FishingRodItem;", ordinal = 0),
            method = "<clinit>")
    private static FishingRodItem fishingRod(Item.Properties properties) {
        return new TideFishingRodItem(32, properties);
    }
}
