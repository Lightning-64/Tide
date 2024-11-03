package com.li64.tide.data.journal;

import com.li64.tide.Tide;
import com.li64.tide.data.journal.config.JournalRemovalCustomData;
import com.li64.tide.util.TideUtils;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class JournalLayout {
    private final List<Profile> profiles = new ArrayList<>(20);
    private final List<Page> pages = new ArrayList<>(6);

    public JournalLayout() {
        // ++ PAGES ++

        addPage("welcome", "journal.title.welcome", "journal.content.welcome", "minecraft:air", true);

        addPage("freshwater", "journal.title.freshwater", "journal.content.freshwater", "minecraft:salmon", false);

        addPage("saltwater", "journal.title.saltwater", "journal.content.saltwater", "minecraft:tropical_fish", false);

        addPage("underground", "journal.title.underground", "journal.content.underground", "minecraft:stone", false);

        addPage("depths", "journal.title.depths", "journal.content.depths", "minecraft:deepslate", false);

        addPage("biome", "journal.title.biome", "journal.content.biome", "tide:sporestalker", false);

        addPage("lava", "journal.title.lava", "journal.content.lava", "tide:fish_bone", false);

        addPage("nether", "journal.title.nether", "journal.content.nether", "tide:magma_mackerel", false);

        if (Tide.PLATFORM.isModLoaded("blue_skies"))
            addPage("blueskies", "journal.title.blueskies", "journal.content.blueskies", "blue_skies:municipal_monkfish", false);

        addPage("end", "journal.title.end", "journal.content.end", "tide:endstone_perch", false);

        addPage("legendary", "journal.title.legendary", "journal.content.legendary", "tide:midas_fish", false);

        // ++ PROFILES ++

        addProfile("tide:trout", "profile.item.tide.trout", "freshwater", "freshwater", "cold");
        addProfile("minecraft:salmon", "profile.item.minecraft.salmon", "freshwater", "freshwater", "cold");
        addProfile("tide:bass", "profile.item.tide.bass", "freshwater", "freshwater", "normal");
        addProfile("tide:yellow_perch", "profile.item.tide.yellow_perch", "freshwater", "freshwater", "normal");
        addProfile("tide:bluegill", "profile.item.tide.bluegill", "freshwater", "freshwater", "normal");
        addProfile("tide:mint_carp", "profile.item.tide.mint_carp", "freshwater", "freshwater", "warm");
        addProfile("tide:pike", "profile.item.tide.pike", "freshwater", "freshwater", "cold");
        addProfile("tide:guppy", "profile.item.tide.guppy", "freshwater", "freshwater", "warm");
        addProfile("tide:catfish", "profile.item.tide.catfish", "freshwater", "freshwater", "cold");
        addProfile("tide:clayfish", "profile.item.tide.clayfish", "freshwater", "freshwater", "cold");

        addProfile("minecraft:cod", "profile.item.minecraft.cod", "saltwater", "saltwater", "cold");
        addProfile("minecraft:tropical_fish", "profile.item.minecraft.tropical_fish", "saltwater", "saltwater", "warm");
        addProfile("minecraft:pufferfish", "profile.item.minecraft.pufferfish", "saltwater", "saltwater", "warm");
        addProfile("tide:tuna", "profile.item.tide.tuna", "saltwater", "saltwater", "normal");
        addProfile("tide:ocean_perch", "profile.item.tide.ocean_perch", "saltwater", "saltwater", "cold");
        addProfile("tide:mackerel", "profile.item.tide.mackerel", "saltwater", "saltwater", "normal");
        addProfile("tide:angelfish", "profile.item.tide.angelfish", "saltwater", "saltwater", "warm");
        addProfile("tide:barracuda", "profile.item.tide.barracuda", "saltwater", "saltwater", "normal");
        addProfile("tide:sailfish", "profile.item.tide.sailfish", "saltwater", "saltwater", "warm");

        addProfile("tide:cave_eel", "profile.item.tide.cave_eel", "underground", "underground", "cold");
        addProfile("tide:crystal_shrimp", "profile.item.tide.crystal_shrimp", "underground", "underground", "any");
        addProfile("tide:iron_tetra", "profile.item.tide.iron_tetra", "underground", "underground", "any");
        addProfile("tide:glowfish", "profile.item.tide.glowfish", "underground", "underground", "cold");
        addProfile("tide:anglerfish", "profile.item.tide.anglerfish", "underground", "underground", "cold");
        addProfile("tide:cave_crawler", "profile.item.tide.cave_crawler", "underground", "underground", "cold");
        addProfile("tide:gilded_minnow", "profile.item.tide.gilded_minnow", "underground", "underground", "any");

        addProfile("tide:deep_grouper", "profile.item.tide.deep_grouper", "depths", "depths", "cold");
        addProfile("tide:shadow_snapper", "profile.item.tide.shadow_snapper", "depths", "depths", "cold");
        addProfile("tide:abyss_angler", "profile.item.tide.abyss_angler", "depths", "depths", "cold");
        addProfile("tide:lapis_lanternfish", "profile.item.tide.lapis_lanternfish", "depths", "depths", "any");
        addProfile("tide:luminescent_jellyfish", "profile.item.tide.luminescent_jellyfish", "depths", "depths", "cold");
        addProfile("tide:crystalline_carp", "profile.item.tide.crystalline_carp", "depths", "depths", "any");
        addProfile("tide:bedrock_tetra", "profile.item.tide.bedrock_tetra", "depths", "depths", "any");

        addProfile("tide:prarie_pike", "profile.item.tide.prarie_pike", "biome", "plains", "normal");
        addProfile("tide:sandskipper", "profile.item.tide.sandskipper", "biome", "desert", "warm");
        addProfile("tide:blossom_bass", "profile.item.tide.blossom_bass", "biome", "cherry", "normal");
        addProfile("tide:oakfish", "profile.item.tide.oakfish", "biome", "forest", "normal");
        addProfile("tide:frostbite_flounder", "profile.item.tide.frostbite_flounder", "biome", "frozen", "cold");
        addProfile("tide:mirage_catfish", "profile.item.tide.mirage_catfish", "biome", "badlands", "warm");
        addProfile("tide:echofin_snapper", "profile.item.tide.echofin_snapper", "biome", "deep_dark", "cold");
        addProfile("tide:sunspike_goby", "profile.item.tide.sunspike_goby", "biome", "savanna", "warm");
        addProfile("tide:birch_trout", "profile.item.tide.birch_trout", "biome", "birch", "normal");
        addProfile("tide:stonefish", "profile.item.tide.stonefish", "biome", "mountain", "cold");
        addProfile("tide:dripstone_darter", "profile.item.tide.dripstone_darter", "biome", "dripstone", "cold");
        addProfile("tide:slimefin_snapper", "profile.item.tide.slimefin_snapper", "biome", "swamp", "normal");
        addProfile("tide:sporestalker", "profile.item.tide.sporestalker", "biome", "mushroom", "normal");
        addProfile("tide:leafback", "profile.item.tide.leafback", "biome", "jungle", "warm");
        addProfile("tide:fluttergill", "profile.item.tide.fluttergill", "biome", "lush_caves", "normal");
        addProfile("tide:pine_perch", "profile.item.tide.pine_perch", "biome", "taiga", "cold");

        addProfile("tide:ember_koi", "profile.item.tide.ember_koi", "lava", "lava", "very_hot");
        addProfile("tide:inferno_guppy", "profile.item.tide.inferno_guppy", "lava", "lava", "very_hot");
        addProfile("tide:obsidian_pike", "profile.item.tide.obsidian_pike", "lava", "lava", "very_hot");
        addProfile("tide:volcano_tuna", "profile.item.tide.volcano_tuna", "lava", "lava", "very_hot");

        addProfile("tide:magma_mackerel", "profile.item.tide.magma_mackerel", "nether", "nether", "very_hot");
        addProfile("tide:ashen_perch", "profile.item.tide.ashen_perch", "nether", "nether", "very_hot");
        addProfile("tide:soulscaler", "profile.item.tide.soulscaler", "nether", "nether", "very_hot");
        addProfile("tide:warped_guppy", "profile.item.tide.warped_guppy", "nether", "nether", "very_hot");
        addProfile("tide:crimson_fangjaw", "profile.item.tide.crimson_fangjaw", "nether", "nether", "very_hot");
        addProfile("tide:witherfin", "profile.item.tide.witherfin", "nether", "nether", "very_hot");
        addProfile("tide:blazing_swordfish", "profile.item.tide.blazing_swordfish", "nether", "nether", "very_hot");

        addProfile("tide:endstone_perch", "profile.item.tide.endstone_perch", "end", "end", "cold");
        addProfile("tide:enderfin", "profile.item.tide.enderfin", "end", "end", "cold");
        addProfile("tide:endergazer", "profile.item.tide.endergazer", "end", "end", "cold");
        addProfile("tide:purpur_pike", "profile.item.tide.purpur_pike", "end", "end", "cold");
        addProfile("tide:chorus_cod", "profile.item.tide.chorus_cod", "end", "end", "cold");
        addProfile("tide:elytrout", "profile.item.tide.elytrout", "end", "end", "cold");

        if (Tide.PLATFORM.isModLoaded("blue_skies")) {
            addProfile("blue_skies:municipal_monkfish", "", "blueskies", "everbright", "cold");
            addProfile("blue_skies:grittle_flatfish", "", "blueskies", "everbright", "cold");
            addProfile("blue_skies:charscale_moki", "", "blueskies", "everdawn", "warm");
            addProfile("blue_skies:horizofin_tunid", "", "blueskies", "everdawn", "warm");
        }

        if (Tide.PLATFORM.isModLoaded("unusualfishmod")) {
            addProfile("unusualfishmod:raw_eyelash", "", "freshwater", "jungle", "warm");
            addProfile("unusualfishmod:raw_spindlefish", "", "saltwater", "saltwater", "warm");
            addProfile("unusualfishmod:raw_snowflake", "", "saltwater", "saltwater", "cold");
            addProfile("unusualfishmod:raw_aero_mono", "", "saltwater", "saltwater", "normal");
            addProfile("unusualfishmod:raw_rhino_tetra", "", "freshwater", "freshwater", "normal");
            addProfile("unusualfishmod:raw_sailor_barb", "", "freshwater", "swamp", "normal");
            addProfile("unusualfishmod:raw_bark_angelfish", "", "freshwater", "swamp", "normal");
            addProfile("unusualfishmod:raw_picklefish", "", "saltwater", "saltwater", "warm");
            addProfile("unusualfishmod:raw_amber_goby", "", "saltwater", "saltwater", "warm");
            addProfile("unusualfishmod:raw_beaked_herring", "", "saltwater", "saltwater", "normal");
            addProfile("unusualfishmod:raw_blind_sailfin", "", "freshwater", "overworld", "any");
            addProfile("unusualfishmod:raw_circus_fish", "", "saltwater", "saltwater", "warm");
            addProfile("unusualfishmod:raw_copperflame_anthias", "", "saltwater", "saltwater", "warm");
            addProfile("unusualfishmod:raw_demon_herring", "", "saltwater", "saltwater", "normal");
            addProfile("unusualfishmod:raw_drooping_gourami", "", "freshwater", "swamp", "normal");
            addProfile("unusualfishmod:raw_duality_damselfish", "", "saltwater", "saltwater", "warm");
            addProfile("unusualfishmod:raw_forkfish", "", "saltwater", "saltwater", "warm");
            addProfile("unusualfishmod:raw_hatchetfish", "", "freshwater", "overworld", "any");
            addProfile("unusualfishmod:raw_sneep_snorp", "", "saltwater", "saltwater", "warm");
            addProfile("unusualfishmod:raw_triple_twirl_pleco", "", "freshwater", "freshwater", "normal");
        }

        if (Tide.PLATFORM.isModLoaded("rainbowreef")) {
            addProfile("rainbowreef:raw_basslet", "", "saltwater", "saltwater", "warm");
            addProfile("rainbowreef:raw_boxfish", "", "saltwater", "saltwater", "warm");
            addProfile("rainbowreef:raw_butterflyfish", "", "saltwater", "saltwater", "warm");
            addProfile("rainbowreef:raw_clownfish", "", "saltwater", "saltwater", "warm");
            addProfile("rainbowreef:raw_dwarf_angelfish", "", "saltwater", "saltwater", "warm");
            addProfile("rainbowreef:raw_goby", "", "saltwater", "saltwater", "warm");
            addProfile("rainbowreef:raw_hogfish", "", "saltwater", "saltwater", "warm");
            addProfile("rainbowreef:raw_parrotfish", "", "saltwater", "saltwater", "warm");
            addProfile("rainbowreef:raw_pipefish", "", "freshwater", "swamp", "warm");
            addProfile("rainbowreef:raw_seahorse", "", "saltwater", "saltwater", "warm");
            addProfile("rainbowreef:raw_tang", "", "saltwater", "saltwater", "warm");
        }

        if (Tide.PLATFORM.isModLoaded("fishofthieves")) {
            addProfile("fishofthieves:splashtail", "", "saltwater", "saltwater", "normal");
            addProfile("fishofthieves:pondie", "", "freshwater", "freshwater", "normal");
            addProfile("fishofthieves:islehopper", "", "saltwater", "saltwater", "normal");
            addProfile("fishofthieves:ancientscale", "", "saltwater", "saltwater", "warm");
            addProfile("fishofthieves:plentifin", "", "saltwater", "saltwater", "warm");
            addProfile("fishofthieves:wildsplash", "", "freshwater", "jungle", "warm");
            addProfile("fishofthieves:devilfish", "", "underground", "dripstone", "cold");
            addProfile("fishofthieves:battlegill", "", "saltwater", "structures", "raids");
            addProfile("fishofthieves:wrecker", "", "saltwater", "shipwrecks", "any");
            addProfile("fishofthieves:stormfish", "", "freshwater", "any", "thunderstorms");
        }

        if (Tide.PLATFORM.isModLoaded("netherdepthsupgrade")) {
            addProfile("netherdepthsupgrade:lava_pufferfish", "", "nether", "nether", "very_hot");
            addProfile("netherdepthsupgrade:obsidianfish", "", "nether", "nether", "very_hot");
            addProfile("netherdepthsupgrade:searing_cod", "", "nether", "nether", "very_hot");
            addProfile("netherdepthsupgrade:bonefish", "", "nether", "nether", "very_hot");
            addProfile("netherdepthsupgrade:wither_bonefish", "", "nether", "nether", "very_hot");
            addProfile("netherdepthsupgrade:blazefish", "", "nether", "nether", "very_hot");
            addProfile("netherdepthsupgrade:magmacubefish", "", "nether", "nether", "very_hot");
            addProfile("netherdepthsupgrade:glowdine", "", "nether", "nether", "very_hot");
            addProfile("netherdepthsupgrade:soulsucker", "", "nether", "nether", "very_hot");
            addProfile("netherdepthsupgrade:fortress_grouper", "", "nether", "nether", "very_hot");
            addProfile("netherdepthsupgrade:eyeball_fish", "", "nether", "nether", "very_hot");
        }

        if (Tide.PLATFORM.isModLoaded("aquamirae")) {
            addProfile("aquamirae:spinefish", "", "saltwater", "saltwater", "cold");
        }

        if (Tide.PLATFORM.isModLoaded("finsandtails")) {
            addProfile("finsandtails:banded_redback_shrimp", "", "saltwater", "saltwater", "warm");
            addProfile("finsandtails:wee_wee", "", "freshwater", "freshwater", "normal");
            addProfile("finsandtails:pea_wee", "", "freshwater", "jungle", "warm");
            addProfile("finsandtails:blu_wee", "", "saltwater", "saltwater", "cold");
            addProfile("finsandtails:vibra_wee", "", "freshwater", "jungle", "warm");
            addProfile("finsandtails:papa_wee", "", "freshwater", "freshwater", "normal");
            addProfile("finsandtails:flatback_sucker", "", "freshwater", "freshwater", "normal");
            addProfile("finsandtails:high_finned_blue", "", "saltwater", "saltwater", "normal");
            addProfile("finsandtails:night_light_squid", "", "saltwater", "saltwater", "cold");
            addProfile("finsandtails:ornate_bugfish", "", "saltwater", "saltwater", "warm");
            addProfile("finsandtails:swamp_mucker", "", "freshwater", "swamp", "warm");
            addProfile("finsandtails:teal_arrowfish", "", "saltwater", "saltwater", "cold");
            addProfile("finsandtails:ruby_spindly_gem_crab", "", "saltwater", "saltwater", "warm");
            addProfile("finsandtails:amber_spindly_gem_crab", "", "saltwater", "saltwater", "warm");
            addProfile("finsandtails:emerald_spindly_gem_crab", "", "saltwater", "saltwater", "warm");
            addProfile("finsandtails:pearl_spindly_gem_crab", "", "saltwater", "saltwater", "warm");
            addProfile("finsandtails:sapphire_spindly_gem_crab", "", "saltwater", "saltwater", "warm");
        }

        addProfile("tide:midas_fish", "profile.item.tide.midas_fish", "legendary", "any", "lucky");
        addProfile("tide:voidseeker", "profile.item.tide.voidseeker", "legendary", "end", "full_new_moon");
        addProfile("tide:shooting_starfish", "profile.item.tide.shooting_starfish", "legendary", "saltwater", "night_full_moon");
    }

    private void addPage(String id, String name, String content, String icon, boolean unlocked) {
        pages.add(new Page(id, name, content, icon, unlocked));
    }

    private void addProfile(String fish, String desc, String page, String location, String climate) {
        profiles.add(new Profile(fish, desc, page, location, climate));
    }

    public List<Page> getPageConfigs() {
        return pages;
    }

    public List<Profile> getProfileConfigs() {
        return profiles;
    }

    public void addPageConfigs(List<Page> newPages) {
        pages.addAll(newPages);
    }

    public void addProfileConfigs(List<Profile> newProfiles) {
        profiles.addAll(newProfiles);
    }

    public void removeProfileConfigs(List<JournalRemovalCustomData.Removal> removals) {
        profiles.removeIf(profile -> removals.stream()
                .map(JournalRemovalCustomData.Removal::item)
                .toList().contains(profile.fishItem));
    }

    public record Page(String id, String name, String content, String icon, boolean unlockedByDefault) {
        public static final Codec<Page> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                Codec.STRING.fieldOf("id").forGetter(JournalLayout.Page::id),
                Codec.STRING.fieldOf("name").forGetter(JournalLayout.Page::name),
                Codec.STRING.fieldOf("content").forGetter(JournalLayout.Page::content),
                Codec.STRING.fieldOf("icon").forGetter(JournalLayout.Page::icon),
                Codec.BOOL.fieldOf("unlocked_by_default").forGetter(JournalLayout.Page::unlockedByDefault)
        ).apply(instance, JournalLayout.Page::new));
    }

    public record Profile(String fishItem, String description, String journalPage, String location, String climate) {
        public static final Codec<Profile> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                Codec.STRING.fieldOf("item").forGetter(JournalLayout.Profile::fishItem),
                Codec.STRING.fieldOf("description").forGetter(JournalLayout.Profile::description),
                Codec.STRING.fieldOf("page").forGetter(JournalLayout.Profile::journalPage),
                Codec.STRING.fieldOf("location").forGetter(JournalLayout.Profile::location),
                Codec.STRING.fieldOf("climate").forGetter(JournalLayout.Profile::climate)
        ).apply(instance, JournalLayout.Profile::new));

        public ItemStack getFish() {
            return TideUtils.getItemFromName(fishItem).getDefaultInstance();
        }
    }
}