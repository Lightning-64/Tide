package com.li64.tide.datagen.providers.assets;

import com.li64.tide.registries.TideItems;
import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.model.ItemModelUtils;
import net.minecraft.client.data.models.model.ModelLocationUtils;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.Item;

public class TideModelProvider extends FabricModelProvider {
    public TideModelProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockModelGenerators generator) {

    }

    @Override
    public void generateItemModels(ItemModelGenerators generator) {
        generator.generateFishingRod(TideItems.STONE_FISHING_ROD);
        generator.generateFishingRod(TideItems.IRON_FISHING_ROD);
        generator.generateFishingRod(TideItems.GOLDEN_FISHING_ROD);
        generator.generateFishingRod(TideItems.CRYSTAL_FISHING_ROD);
        generator.generateFishingRod(TideItems.DIAMOND_FISHING_ROD);
        generator.generateFishingRod(TideItems.NETHERITE_FISHING_ROD);

        generator.generateFlatItem(TideItems.BAIT, ModelTemplates.FLAT_ITEM);
        generator.generateFlatItem(TideItems.LUCKY_BAIT, ModelTemplates.FLAT_ITEM);
        generator.generateFlatItem(TideItems.MAGNETIC_BAIT, ModelTemplates.FLAT_ITEM);

        generateFishingBobber(generator, TideItems.RED_FISHING_BOBBER);
        generateFishingBobber(generator, TideItems.ORANGE_FISHING_BOBBER);
        generateFishingBobber(generator, TideItems.YELLOW_FISHING_BOBBER);
        generateFishingBobber(generator, TideItems.LIME_FISHING_BOBBER);
        generateFishingBobber(generator, TideItems.GREEN_FISHING_BOBBER);
        generateFishingBobber(generator, TideItems.CYAN_FISHING_BOBBER);
        generateFishingBobber(generator, TideItems.LIGHT_BLUE_FISHING_BOBBER);
        generateFishingBobber(generator, TideItems.BLUE_FISHING_BOBBER);
        generateFishingBobber(generator, TideItems.PURPLE_FISHING_BOBBER);
        generateFishingBobber(generator, TideItems.MAGENTA_FISHING_BOBBER);
        generateFishingBobber(generator, TideItems.PINK_FISHING_BOBBER);
        generateFishingBobber(generator, TideItems.WHITE_FISHING_BOBBER);
        generateFishingBobber(generator, TideItems.LIGHT_GRAY_FISHING_BOBBER);
        generateFishingBobber(generator, TideItems.GRAY_FISHING_BOBBER);
        generateFishingBobber(generator, TideItems.BLACK_FISHING_BOBBER);
        generateFishingBobber(generator, TideItems.BROWN_FISHING_BOBBER);
        generateFishingBobber(generator, TideItems.APPLE_FISHING_BOBBER);
        generateFishingBobber(generator, TideItems.GOLDEN_APPLE_FISHING_BOBBER);
        generateFishingBobber(generator, TideItems.ENCHANTED_GOLDEN_APPLE_FISHING_BOBBER);
        generateFishingBobber(generator, TideItems.IRON_FISHING_BOBBER);
        generateFishingBobber(generator, TideItems.GOLDEN_FISHING_BOBBER);
        generateFishingBobber(generator, TideItems.DIAMOND_FISHING_BOBBER);
        generateFishingBobber(generator, TideItems.NETHERITE_FISHING_BOBBER);
        generateFishingBobber(generator, TideItems.AMETHYST_FISHING_BOBBER);
        generateFishingBobber(generator, TideItems.ECHO_FISHING_BOBBER);
        generateFishingBobber(generator, TideItems.CHORUS_FISHING_BOBBER);
        generateFishingBobber(generator, TideItems.FEATHER_FISHING_BOBBER);
        generateFishingBobber(generator, TideItems.LICHEN_FISHING_BOBBER);
        generateFishingBobber(generator, TideItems.NAUTILUS_FISHING_BOBBER);
        generateFishingBobber(generator, TideItems.PEARL_FISHING_BOBBER);
        generateFishingBobber(generator, TideItems.HEART_FISHING_BOBBER);
        generateFishingBobber(generator, TideItems.GRASSY_FISHING_BOBBER);

        generator.generateFlatItem(TideItems.FISHING_HOOK, ModelTemplates.FLAT_ITEM);
        generator.generateFlatItem(TideItems.IRON_FISHING_HOOK, ModelTemplates.FLAT_ITEM);
        generator.generateFlatItem(TideItems.LAVAPROOF_FISHING_HOOK, ModelTemplates.FLAT_ITEM);

        generator.generateFlatItem(TideItems.FISHING_LINE, ModelTemplates.FLAT_ITEM);
        generator.generateFlatItem(TideItems.BRAIDED_LINE, ModelTemplates.FLAT_ITEM);
        generator.generateFlatItem(TideItems.REINFORCED_LINE, ModelTemplates.FLAT_ITEM);
        generator.generateFlatItem(TideItems.FORTUNE_LINE, ModelTemplates.FLAT_ITEM);

        generator.generateFlatItem(TideItems.FISHING_JOURNAL, ModelTemplates.FLAT_ITEM);

        generator.generateFlatItem(TideItems.FISH_BONE, ModelTemplates.FLAT_ITEM);
        generator.generateFlatItem(TideItems.OBSIDIAN_FRAGMENT, ModelTemplates.FLAT_ITEM);

        generator.generateFlatItem(TideItems.ALGAE, ModelTemplates.FLAT_ITEM);
        generator.generateFlatItem(TideItems.JELLY_TORCH, ModelTemplates.FLAT_ITEM);

        generator.generateFlatItem(TideItems.SPECTRAL_SCALE, ModelTemplates.FLAT_ITEM);
        generator.generateFlatItem(TideItems.TWILIGHT_SCALE, ModelTemplates.FLAT_ITEM);

        generator.generateFlatItem(TideItems.DEEP_AQUA_CRYSTAL, ModelTemplates.FLAT_ITEM);
        generator.generateFlatItem(TideItems.DEEP_AQUA_ARROW, ModelTemplates.FLAT_ITEM);

        generator.generateFlatItem(TideItems.COOKED_FISH, ModelTemplates.FLAT_ITEM);

        generator.generateFlatItem(TideItems.FISH_SLICE, ModelTemplates.FLAT_ITEM);
        generator.generateFlatItem(TideItems.COOKED_FISH_SLICE, ModelTemplates.FLAT_ITEM);

        generator.generateFlatItem(TideItems.TROUT_BUCKET, ModelTemplates.FLAT_ITEM);
        generator.generateFlatItem(TideItems.BASS_BUCKET, ModelTemplates.FLAT_ITEM);
        generator.generateFlatItem(TideItems.YELLOW_PERCH_BUCKET, ModelTemplates.FLAT_ITEM);
        generator.generateFlatItem(TideItems.BLUEGILL_BUCKET, ModelTemplates.FLAT_ITEM);
        generator.generateFlatItem(TideItems.MINT_CARP_BUCKET, ModelTemplates.FLAT_ITEM);
        generator.generateFlatItem(TideItems.PIKE_BUCKET, ModelTemplates.FLAT_ITEM);
        generator.generateFlatItem(TideItems.GUPPY_BUCKET, ModelTemplates.FLAT_ITEM);
        generator.generateFlatItem(TideItems.CATFISH_BUCKET, ModelTemplates.FLAT_ITEM);
        generator.generateFlatItem(TideItems.CLAYFISH_BUCKET, ModelTemplates.FLAT_ITEM);

        generator.generateFlatItem(TideItems.TUNA_BUCKET, ModelTemplates.FLAT_ITEM);
        generator.generateFlatItem(TideItems.OCEAN_PERCH_BUCKET, ModelTemplates.FLAT_ITEM);
        generator.generateFlatItem(TideItems.MACKEREL_BUCKET, ModelTemplates.FLAT_ITEM);
        generator.generateFlatItem(TideItems.ANGELFISH_BUCKET, ModelTemplates.FLAT_ITEM);
        generator.generateFlatItem(TideItems.BARRACUDA_BUCKET, ModelTemplates.FLAT_ITEM);
        generator.generateFlatItem(TideItems.SAILFISH_BUCKET, ModelTemplates.FLAT_ITEM);

        generator.generateSpawnEgg(TideItems.TROUT_SPAWN_EGG, 2580837, 7197881);
        generator.generateSpawnEgg(TideItems.BASS_SPAWN_EGG, 11250290, 4802877);
        generator.generateSpawnEgg(TideItems.YELLOW_PERCH_SPAWN_EGG, 5056290, 12489757);
        generator.generateSpawnEgg(TideItems.BLUEGILL_SPAWN_EGG, 3241569, 8747597);
        generator.generateSpawnEgg(TideItems.MINT_CARP_SPAWN_EGG, 9492648, 4095594);
        generator.generateSpawnEgg(TideItems.PIKE_SPAWN_EGG, 7889995, 16048549);
        generator.generateSpawnEgg(TideItems.GUPPY_SPAWN_EGG, 1207235, 15955968);
        generator.generateSpawnEgg(TideItems.CATFISH_SPAWN_EGG, 13402689, 5254425);
        generator.generateSpawnEgg(TideItems.CLAYFISH_SPAWN_EGG, 11316925, 5332864);

        generator.generateSpawnEgg(TideItems.TUNA_SPAWN_EGG, 0xa8b5af, 0x303336);
        generator.generateSpawnEgg(TideItems.OCEAN_PERCH_SPAWN_EGG, 0xa46250, 0x51211a);
        generator.generateSpawnEgg(TideItems.MACKEREL_SPAWN_EGG, 0x8e9480, 0x003d4d);
        generator.generateSpawnEgg(TideItems.ANGELFISH_SPAWN_EGG, 0x2e5c6b, 0x0e1f2e);
        generator.generateSpawnEgg(TideItems.BARRACUDA_SPAWN_EGG, 0x5c5943, 0x16313d);
        generator.generateSpawnEgg(TideItems.SAILFISH_SPAWN_EGG, 0x698c8f, 0x466c82);

        TideItems.JOURNAL_FISH_LIST.forEach(key -> {
            Item item = BuiltInRegistries.ITEM.get(key).orElseThrow().value();
            if (item == TideItems.BLAZING_SWORDFISH) generator.generateFlatItem(item, ModelTemplates.FLAT_HANDHELD_ITEM);
            else generator.generateFlatItem(item, ModelTemplates.FLAT_ITEM);
        });
    }

    public void generateFishingBobber(ItemModelGenerators generator, Item item) {
        generator.itemModelOutput.accept(item, ItemModelUtils.plainModel(
                ModelLocationUtils.getModelLocation(item)));
    }
}