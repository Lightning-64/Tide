package com.li64.tide.datagen;

import com.li64.tide.datagen.providers.advancements.TideAdvancementProvider;
import com.li64.tide.datagen.providers.loot.TideBlockLootProvider;
import com.li64.tide.datagen.providers.loot.TideChestLootProvider;
import com.li64.tide.datagen.providers.loot.TideEntityLootProvider;
import com.li64.tide.datagen.providers.loot.TideFishingLootProvider;
import com.li64.tide.datagen.providers.recipes.TideRecipeProvider;
import com.li64.tide.datagen.providers.tags.*;
import com.li64.tide.registries.TideItems;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

public class TideDataGenerator implements DataGeneratorEntrypoint {
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator generator) {
        FabricDataGenerator.Pack pack = generator.createPack();
        TideItems.assignTags();

        pack.addProvider(TideBlockTagsProvider::new);
        pack.addProvider(TideItemTagsProvider::new);
        pack.addProvider(TideBiomeTagsProvider::new);
        pack.addProvider(TideFluidTagsProvider::new);
        pack.addProvider(TideEntityTypeTagsProvider::new);

        pack.addProvider(TideFishingLootProvider::new);
        pack.addProvider(TideChestLootProvider::new);
        pack.addProvider(TideBlockLootProvider::new);
        pack.addProvider(TideEntityLootProvider::new);

        pack.addProvider(TideAdvancementProvider::new);
        pack.addProvider(TideRecipeProvider::new);
    }
}
