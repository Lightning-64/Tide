package com.li64.tide.client.gui;

import com.li64.tide.Tide;
import com.li64.tide.client.gui.menus.AnglerWorkshopMenu;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;

import java.util.HashMap;

public class TideMenuTypes {
    public static final HashMap<String, MenuType<?>> MENU_TYPES = new HashMap<>();

    public static final MenuType<AnglerWorkshopMenu> ANGLER_WORKSHOP = register("angler_workshop",
            Tide.PLATFORM.createMenuType(AnglerWorkshopMenu::new, FeatureFlags.DEFAULT_FLAGS));

    public static <T extends AbstractContainerMenu> MenuType<T> register(String key, MenuType<T> menuType) {
        MENU_TYPES.put(key, menuType);
        return menuType;
    }

    public static void init() {
        MENU_TYPES.forEach(Tide.PLATFORM::registerMenuType);
    }
}
