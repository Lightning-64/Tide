package com.li64.tide.data.rods;

import com.li64.tide.Tide;
import com.li64.tide.registries.TideItems;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

public enum BobberModifier {
    WHITE(TideItems.WHITE_FISHING_BOBBER, "bobber.tide.white"),
    ORANGE(TideItems.ORANGE_FISHING_BOBBER, "bobber.tide.orange"),
    MAGENTA(TideItems.MAGENTA_FISHING_BOBBER, "bobber.tide.magenta"),
    LIGHT_BLUE(TideItems.LIGHT_BLUE_FISHING_BOBBER, "bobber.tide.light_blue"),
    YELLOW(TideItems.YELLOW_FISHING_BOBBER, "bobber.tide.yellow"),
    LIME(TideItems.LIME_FISHING_BOBBER, "bobber.tide.lime"),
    PINK(TideItems.PINK_FISHING_BOBBER, "bobber.tide.pink"),
    GRAY(TideItems.GRAY_FISHING_BOBBER, "bobber.tide.gray"),
    LIGHT_GRAY(TideItems.LIGHT_GRAY_FISHING_BOBBER, "bobber.tide.light_gray"),
    CYAN(TideItems.CYAN_FISHING_BOBBER, "bobber.tide.cyan"),
    PURPLE(TideItems.PURPLE_FISHING_BOBBER, "bobber.tide.purple"),
    BLUE(TideItems.BLUE_FISHING_BOBBER, "bobber.tide.blue"),
    BROWN(TideItems.BROWN_FISHING_BOBBER, "bobber.tide.brown"),
    GREEN(TideItems.GREEN_FISHING_BOBBER, "bobber.tide.green"),
    RED(TideItems.RED_FISHING_BOBBER, "bobber.tide.red"),
    BLACK(TideItems.BLACK_FISHING_BOBBER, "bobber.tide.black");

    public static final BobberModifier DEFAULT = RED;

    final Item item;
    final String translationKey;
    ResourceLocation textureLocation;

    BobberModifier(Item item, String translationKey) {
        this.item = item;
        this.translationKey = translationKey;
    }

    public Item getItem() {
        return this.item;
    }

    public String getTranslationKey() {
        return this.translationKey;
    }

    public ResourceLocation getTextureLocation() {
        if (textureLocation == null) textureLocation = Tide.resource(getTexturePath());
        return textureLocation;
    }

    private String getTexturePath() {
        return switch (this) {
            case WHITE -> "textures/item/white_fishing_bobber.png";
            case ORANGE -> "textures/item/orange_fishing_bobber.png";
            case MAGENTA -> "textures/item/magenta_fishing_bobber.png";
            case LIGHT_BLUE -> "textures/item/light_blue_fishing_bobber.png";
            case YELLOW -> "textures/item/yellow_fishing_bobber.png";
            case LIME -> "textures/item/lime_fishing_bobber.png";
            case PINK -> "textures/item/pink_fishing_bobber.png";
            case GRAY -> "textures/item/gray_fishing_bobber.png";
            case LIGHT_GRAY -> "textures/item/light_gray_fishing_bobber.png";
            case CYAN -> "textures/item/cyan_fishing_bobber.png";
            case PURPLE -> "textures/item/purple_fishing_bobber.png";
            case BLUE -> "textures/item/blue_fishing_bobber.png";
            case BROWN -> "textures/item/brown_fishing_bobber.png";
            case GREEN -> "textures/item/green_fishing_bobber.png";
            case RED -> "textures/item/red_fishing_bobber.png";
            case BLACK -> "textures/item/black_fishing_bobber.png";
        };
    }
}
