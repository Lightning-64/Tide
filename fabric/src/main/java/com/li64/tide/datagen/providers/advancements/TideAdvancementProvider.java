package com.li64.tide.datagen.providers.advancements;

import com.li64.tide.Tide;
import com.li64.tide.data.TideCriteriaTriggers;
import com.li64.tide.data.TideTags;
import com.li64.tide.data.triggers.TideSimpleTrigger;
import com.li64.tide.registries.TideItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricAdvancementProvider;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.FrameType;
import net.minecraft.advancements.critereon.ContextAwarePredicate;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

import java.util.function.Consumer;

public class TideAdvancementProvider extends FabricAdvancementProvider {
    public TideAdvancementProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateAdvancement(Consumer<Advancement> output) {
        Advancement root = Advancement.Builder.advancement()
                .display(
                        TideItems.ANGELFISH,
                        Component.translatable("advancements.tide.root.title"),
                        Component.translatable("advancements.tide.root.description"),
                        Tide.resource("textures/gui/advancements/backgrounds/prismarine.png"),
                        FrameType.TASK,
                        false,
                        false,
                        false
                )
                .addCriterion("rod", InventoryChangeTrigger.TriggerInstance.hasItems(
                        ItemPredicate.Builder.item().of(conventionTag("tools/fishing_rod")).build()))
                .save(output, Tide.resource("root").toString());

        Advancement bait = Advancement.Builder.advancement()
                .parent(root)
                .display(
                        TideItems.BAIT,
                        Component.translatable("advancements.tide.get_bait.title"),
                        Component.translatable("advancements.tide.get_bait.description"),
                        null,
                        FrameType.TASK,
                        true,
                        true,
                        false
                )
                .addCriterion("bait_items", InventoryChangeTrigger.TriggerInstance.hasItems(
                        ItemPredicate.Builder.item().of(
                                TideItems.BAIT, TideItems.MAGNETIC_BAIT, TideItems.LUCKY_BAIT
                        ).build()
                ))
                .save(output, Tide.resource("get_bait").toString());

        Advancement page = Advancement.Builder.advancement()
                .parent(root)
                .display(
                        Items.FILLED_MAP,
                        Component.translatable("advancements.tide.finish_journal_page.title"),
                        Component.translatable("advancements.tide.finish_journal_page.description"),
                        null,
                        FrameType.TASK,
                        true,
                        true,
                        false
                )
                .addCriterion("finish_page", new TideSimpleTrigger.TriggerInstance(
                        TideCriteriaTriggers.FINISH_PAGE.getId(), ContextAwarePredicate.ANY))
                .save(output, Tide.resource("finish_journal_page").toString());

        Advancement.Builder.advancement()
                .parent(root)
                .display(
                        TideItems.EMBER_KOI,
                        Component.translatable("advancements.tide.fish_in_lava.title"),
                        Component.translatable("advancements.tide.fish_in_lava.description"),
                        null,
                        FrameType.TASK,
                        true,
                        true,
                        false
                )
                .addCriterion("lava_fish", new TideSimpleTrigger.TriggerInstance(
                        TideCriteriaTriggers.FISHED_IN_LAVA.getId(), ContextAwarePredicate.ANY))
                .save(output, Tide.resource("fish_in_lava").toString());

        Advancement.Builder.advancement()
                .parent(root)
                .display(
                        TideItems.SURFACE_LOOT_CRATE,
                        Component.translatable("advancements.tide.fish_crate.title"),
                        Component.translatable("advancements.tide.fish_crate.description"),
                        null,
                        FrameType.TASK,
                        true,
                        true,
                        false
                )
                .addCriterion("pull_crate", new TideSimpleTrigger.TriggerInstance(
                        TideCriteriaTriggers.FISHED_CRATE.getId(), ContextAwarePredicate.ANY))
                .save(output, Tide.resource("fish_crate").toString());

        Advancement.Builder.advancement()
                .parent(root)
                .display(
                        TideItems.CRYSTAL_FISHING_ROD,
                        Component.translatable("advancements.tide.all_fishing_rods.title"),
                        Component.translatable("advancements.tide.all_fishing_rods.description"),
                        null,
                        FrameType.GOAL,
                        true,
                        true,
                        false
                )
                .addCriterion("wood", InventoryChangeTrigger.TriggerInstance.hasItems(Items.FISHING_ROD))
                .addCriterion("stone", InventoryChangeTrigger.TriggerInstance.hasItems(TideItems.STONE_FISHING_ROD))
                .addCriterion("iron", InventoryChangeTrigger.TriggerInstance.hasItems(TideItems.IRON_FISHING_ROD))
                .addCriterion("golden", InventoryChangeTrigger.TriggerInstance.hasItems(TideItems.GOLDEN_FISHING_ROD))
                .addCriterion("crystal", InventoryChangeTrigger.TriggerInstance.hasItems(TideItems.CRYSTAL_FISHING_ROD))
                .addCriterion("diamond", InventoryChangeTrigger.TriggerInstance.hasItems(TideItems.DIAMOND_FISHING_ROD))
                .addCriterion("netherite", InventoryChangeTrigger.TriggerInstance.hasItems(TideItems.NETHERITE_FISHING_ROD))
                .save(output, Tide.resource("all_fishing_rods").toString());

        Advancement.Builder.advancement()
                .parent(root)
                .display(
                        TideItems.SPORESTALKER,
                        Component.translatable("advancements.tide.all_biome_fish.title"),
                        Component.translatable("advancements.tide.all_biome_fish.description"),
                        null,
                        FrameType.CHALLENGE,
                        true,
                        true,
                        false
                )
                .addCriterion("plains", InventoryChangeTrigger.TriggerInstance.hasItems(TideItems.PRAIRIE_PIKE))
                .addCriterion("desert", InventoryChangeTrigger.TriggerInstance.hasItems(TideItems.SANDSKIPPER))
                .addCriterion("cherry", InventoryChangeTrigger.TriggerInstance.hasItems(TideItems.BLOSSOM_BASS))
                .addCriterion("forest", InventoryChangeTrigger.TriggerInstance.hasItems(TideItems.OAKFISH))
                .addCriterion("badlands", InventoryChangeTrigger.TriggerInstance.hasItems(TideItems.MIRAGE_CATFISH))
                .addCriterion("birch", InventoryChangeTrigger.TriggerInstance.hasItems(TideItems.BIRCH_TROUT))
                .addCriterion("deep_dark", InventoryChangeTrigger.TriggerInstance.hasItems(TideItems.ECHOFIN_SNAPPER))
                .addCriterion("dripstone", InventoryChangeTrigger.TriggerInstance.hasItems(TideItems.DRIPSTONE_DARTER))
                .addCriterion("swamp", InventoryChangeTrigger.TriggerInstance.hasItems(TideItems.SLIMEFIN_SNAPPER))
                .addCriterion("frozen", InventoryChangeTrigger.TriggerInstance.hasItems(TideItems.FROSTBITE_FLOUNDER))
                .addCriterion("mountain", InventoryChangeTrigger.TriggerInstance.hasItems(TideItems.STONEFISH))
                .addCriterion("mushroom", InventoryChangeTrigger.TriggerInstance.hasItems(TideItems.SPORESTALKER))
                .addCriterion("savanna", InventoryChangeTrigger.TriggerInstance.hasItems(TideItems.SUNSPIKE_GOBY))
                .addCriterion("taiga", InventoryChangeTrigger.TriggerInstance.hasItems(TideItems.PINE_PERCH))
                .addCriterion("lush_caves", InventoryChangeTrigger.TriggerInstance.hasItems(TideItems.FLUTTERGILL))
                .addCriterion("jungle", InventoryChangeTrigger.TriggerInstance.hasItems(TideItems.LEAFBACK))
                .save(output, Tide.resource("all_biome_fish").toString());

        Advancement.Builder.advancement()
                .parent(bait)
                .display(
                        TideItems.MIDAS_FISH,
                        Component.translatable("advancements.tide.catch_legendary.title"),
                        Component.translatable("advancements.tide.catch_legendary.description"),
                        null,
                        FrameType.CHALLENGE,
                        true,
                        true,
                        true
                )
                .addCriterion("catch", InventoryChangeTrigger.TriggerInstance.hasItems(
                        ItemPredicate.Builder.item().of(TideTags.Items.LEGENDARY_FISH).build()))
                .save(output, Tide.resource("catch_legendary").toString());

        Advancement.Builder.advancement()
                .parent(page)
                .display(
                        TideItems.FISHING_JOURNAL,
                        Component.translatable("advancements.tide.journal_completed.title"),
                        Component.translatable("advancements.tide.journal_completed.description"),
                        null,
                        FrameType.CHALLENGE,
                        true,
                        true,
                        false
                )
                .addCriterion("completed", new TideSimpleTrigger.TriggerInstance(
                        TideCriteriaTriggers.FINISH_JOURNAL.getId(), ContextAwarePredicate.ANY))
                .save(output, Tide.resource("journal_completed").toString());
    }

    public TagKey<Item> conventionTag(String name) {
        return TagKey.create(Registries.ITEM, new ResourceLocation("c", name));
    }
}
