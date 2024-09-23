package com.li64.tide;

import com.li64.tide.data.journal.JournalLayout;
import com.li64.tide.config.TideConfig;
import com.li64.tide.platform.Services;
import com.li64.tide.platform.services.TideMainPlatform;
import com.li64.tide.platform.services.TideNetworkPlatform;
import com.li64.tide.registries.TideItems;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.Toml4jConfigSerializer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Tide {
    public static final String MOD_ID = "tide";
    public static final String MOD_NAME = "Tide";
    public static final Logger LOG = LoggerFactory.getLogger(MOD_NAME);

    public static final TideMainPlatform PLATFORM = Services.load(TideMainPlatform.class);
    public static final TideNetworkPlatform NETWORK = Services.load(TideNetworkPlatform.class);

    public static TideConfig CONFIG;
    public static JournalLayout JOURNAL;

    public static void init() {
        CONFIG = AutoConfig.register(TideConfig.class, Toml4jConfigSerializer::new).getConfig();
        JOURNAL = new JournalLayout();

        LOG.info("Initialized Tide mod on {}", PLATFORM.getPlatformName());
    }

    public static ResourceLocation resource(String path) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, path);
    }

    public static CreativeModeTab.Builder getCreativeTab(CreativeModeTab.Builder builder) {
        return builder.title(Component.translatable("itemGroup.tide.main"))
                .icon(() -> new ItemStack(TideItems.ANGELFISH))
                .displayItems(Tide::displayItems);
    }

    public static void displayItems(CreativeModeTab.ItemDisplayParameters itemDisplayParameters, CreativeModeTab.Output output) {
        TideItems.getItems().forEach(output::accept);
    }
}