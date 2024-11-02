//package com.li64.tide.data.rods;
//
//import com.li64.tide.registries.TideItems;
//import net.minecraft.world.item.Item;
//
//public enum LineModifier {
//    NORMAL(TideItems.FISHING_LINE, "line.tide.normal", "#d6d6d6"),
//    BRAIDED(TideItems.BRAIDED_LINE, "line.tide.braided", "#362c1e"),
//    REINFORCED(TideItems.REINFORCED_LINE, "line.tide.reinforced", "#6b6b68"),
//    FORTUNE(TideItems.FORTUNE_LINE, "line.tide.fortune", "#e9b115");
//
//    public static final LineModifier DEFAULT = NORMAL;
//
//    final Item item;
//    final String translationKey;
//    final String color;
//
//    LineModifier(Item item, String translationKey, String color) {
//        this.item = item;
//        this.translationKey = translationKey;
//        this.color = color;
//    }
//
//    public Item getItem() {
//        return this.item;
//    }
//
//    public String getTranslationKey() {
//        return this.translationKey;
//    }
//
//    public String getLineColor() {
//        return color;
//    }
//}
