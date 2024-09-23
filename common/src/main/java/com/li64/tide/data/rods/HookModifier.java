package com.li64.tide.data.rods;

import com.li64.tide.registries.TideItems;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import com.li64.tide.Tide;

public enum HookModifier {
    NORMAL(TideItems.FISHING_HOOK, "hook.tide.normal", false),
    IRON(TideItems.IRON_FISHING_HOOK, "hook.tide.iron", false),
    LAVAPROOF(TideItems.LAVAPROOF_FISHING_HOOK, "hook.tide.lavaproof", true);

    public static final HookModifier DEFAULT = NORMAL;

    final Item item;
    final String translationKey;
    final boolean isLavaproof;
    ResourceLocation textureLocation;

    HookModifier(Item item, String translationKey, boolean isLavaproof) {
        this.item = item;
        this.translationKey = translationKey;
        this.isLavaproof = isLavaproof;
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

    public boolean isLavaproof() {
        return isLavaproof;
    }

    private String getTexturePath() {
        return switch (this) {
            case NORMAL -> "textures/entity/fishing_hook/fishing_hook.png";
            case IRON -> "textures/entity/fishing_hook/iron_fishing_hook.png";
            case LAVAPROOF -> "textures/entity/fishing_hook/lavaproof_fishing_hook.png";
        };
    }
}
