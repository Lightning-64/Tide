package com.li64.tide.compat.modmenu;

import com.li64.tide.config.TideConfig;
import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import me.shedaniel.autoconfig.AutoConfig;
import net.fabricmc.api.Environment;

import static net.fabricmc.api.EnvType.CLIENT;

@Environment(CLIENT)
public class ModMenuCompat implements ModMenuApi {
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return parent -> AutoConfig.getConfigScreen(TideConfig.class, parent).get();
    }
}