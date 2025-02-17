package com.li64.tide.mixin;

import com.li64.tide.registries.items.TideFishingRodItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;

import java.util.function.Function;

@Mixin(Items.class)
public abstract class ItemsMixin {
//    @Redirect(
//            slice = @Slice(from = @At(value = "CONSTANT",args= "stringValue=fishing_rod", ordinal = 0)),
//            at = @At(value = "CONSTANT", target = "Ljava/util/function/Function;", ordinal = 0, args = ""),
//            method = "<clinit>"
//    )
//    private static Function<Item.Properties, Item> fishingRod() {
//        return properties -> new TideFishingRodItem(32, properties);
//    }
}
