package com.li64.tide;

import com.li64.tide.data.TideDataLoader;
import com.li64.tide.data.TideLootTables;
import com.li64.tide.data.journal.JournalLayout;
import com.li64.tide.config.TideConfig;
import com.li64.tide.data.journal.config.CustomPageLoader;
import com.li64.tide.data.journal.config.CustomProfileLoader;
import com.li64.tide.data.journal.config.CustomRemovalLoader;
import com.li64.tide.data.loot.TideFishingPredicate;
import com.li64.tide.data.rods.AccessoryData;
import com.li64.tide.data.rods.AccessoryDataLoader;
import com.li64.tide.data.rods.BaitData;
import com.li64.tide.data.rods.BaitDataLoader;
import com.li64.tide.platform.Services;
import com.li64.tide.platform.services.TideMainPlatform;
import com.li64.tide.platform.services.TideNetworkPlatform;
import com.li64.tide.registries.TideEntityTypes;
import com.li64.tide.registries.TideItems;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.Toml4jConfigSerializer;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.EntityTypePredicate;
import net.minecraft.advancements.critereon.FishingHookPredicate;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.PreparableReloadListener;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntryContainer;
import net.minecraft.world.level.storage.loot.entries.NestedLootTable;
import net.minecraft.world.level.storage.loot.predicates.LootItemEntityPropertyCondition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.function.BiConsumer;

public class Tide {
    public static final String MOD_ID = "tide";
    public static final String MOD_NAME = "Tide";
    public static final Logger LOG = LoggerFactory.getLogger(MOD_NAME);

    public static final TideMainPlatform PLATFORM = Services.load(TideMainPlatform.class);
    public static final TideNetworkPlatform NETWORK = Services.load(TideNetworkPlatform.class);

    private static final ArrayList<TideDataLoader<?>> LOADERS = new ArrayList<>();

    public static TideConfig CONFIG;
    public static JournalLayout JOURNAL;

    public static TideDataLoader<BaitData> BAIT_DATA = registerDataLoader(new BaitDataLoader());
    public static TideDataLoader<AccessoryData> ACCESSORY_DATA = registerDataLoader(new AccessoryDataLoader());
    public static TideDataLoader<JournalLayout.Page> PAGE_DATA = registerDataLoader(new CustomPageLoader());
    public static TideDataLoader<JournalLayout.Profile> PROFILE_DATA = registerDataLoader(new CustomProfileLoader());
    public static TideDataLoader<CustomRemovalLoader.Removal> REMOVAL_DATA = registerDataLoader(new CustomRemovalLoader());

    private static <T> TideDataLoader<T> registerDataLoader(TideDataLoader<T> loader) {
        LOADERS.add(loader);
        return loader;
    }

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

    public static void onRegisterReloadListeners(BiConsumer<ResourceLocation, PreparableReloadListener> registry) {
        LOADERS.forEach(loader -> registry.accept(loader.getDirectory(), loader));
    }

    public static LootPoolEntryContainer.Builder<?> getCrateFishingEntry() {
        return NestedLootTable.lootTableReference(TideLootTables.Fishing.CRATES_BLOCK)
                .setWeight(CONFIG.general.crateWeight).setQuality(CONFIG.general.crateQuality)
                // crates can only be caught in open water (or any lava)
                .when(LootItemEntityPropertyCondition.hasProperties(
                        LootContext.EntityTarget.THIS, EntityPredicate.Builder.entity()
                                .subPredicate(FishingHookPredicate.inOpenWater(true)))
                        .or(LootItemEntityPropertyCondition.hasProperties(
                                LootContext.EntityTarget.THIS, EntityPredicate.Builder.entity()
                                        .subPredicate(TideFishingPredicate.isLavaFishing(true))))
                        .and(LootItemEntityPropertyCondition.hasProperties(
                                LootContext.EntityTarget.THIS, EntityPredicate.Builder.entity()
                                        .entityType(EntityTypePredicate.of(null, TideEntityTypes.FISHING_BOBBER)))));
    }
}