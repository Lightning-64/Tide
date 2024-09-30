package com.li64.tide;

import com.li64.tide.data.journal.JournalLayout;
import com.li64.tide.config.TideConfig;
import com.li64.tide.network.TideMessages;
import com.li64.tide.data.journal.config.JournalPageCustomData;
import com.li64.tide.data.journal.config.JournalProfileCustomData;
import com.li64.tide.data.journal.config.JournalRemovalCustomData;
import com.li64.tide.platform.Services;
import com.li64.tide.platform.services.TideMainPlatform;
import com.li64.tide.platform.services.TideNetworkPlatform;
import com.li64.tide.registries.TideItems;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.Toml4jConfigSerializer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.PreparableReloadListener;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.BiConsumer;

public class Tide {
    public static final String MOD_ID = "tide";
    public static final String MOD_NAME = "Tide";
    public static final Logger LOG = LoggerFactory.getLogger(MOD_NAME);

    public static final TideMainPlatform PLATFORM = Services.load(TideMainPlatform.class);
    public static final TideNetworkPlatform NETWORK = Services.load(TideNetworkPlatform.class);

    public static TideConfig CONFIG;
    public static JournalLayout JOURNAL;

    public static JournalPageCustomData JOURNAL_PAGE_CUSTOM_DATA = new JournalPageCustomData();
    public static JournalProfileCustomData JOURNAL_PROFILE_CUSTOM_DATA = new JournalProfileCustomData();
    public static JournalRemovalCustomData JOURNAL_REMOVAL_CUSTOM_DATA = new JournalRemovalCustomData();

    public static void init() {
        CONFIG = AutoConfig.register(TideConfig.class, Toml4jConfigSerializer::new).getConfig();
        JOURNAL = new JournalLayout();

        TideMessages.init(NETWORK);

        LOG.info("Initialized Tide mod on {}", PLATFORM.getPlatformName());
    }

    public static ResourceLocation resource(String path) {
        return new ResourceLocation(MOD_ID, path);
    }

    public static CreativeModeTab.Builder getCreativeTab(CreativeModeTab.Builder builder) {
        return builder.title(Component.translatable("itemGroup.tide.main"))
                .icon(() -> new ItemStack(TideItems.ANGELFISH))
                .displayItems(Tide::displayItems);
    }

    public static void displayItems(CreativeModeTab.ItemDisplayParameters itemDisplayParameters, CreativeModeTab.Output output) {
        TideItems.getItems().forEach(output::accept);
    }

    public static void onRegisterReloadListeners(BiConsumer<ResourceLocation, PreparableReloadListener> registry) {
        registry.accept(resource(JournalPageCustomData.DATA_PATH), JOURNAL_PAGE_CUSTOM_DATA);
        registry.accept(resource(JournalProfileCustomData.DATA_PATH), JOURNAL_PROFILE_CUSTOM_DATA);
        registry.accept(resource(JournalRemovalCustomData.DATA_PATH), JOURNAL_REMOVAL_CUSTOM_DATA);
    }
}