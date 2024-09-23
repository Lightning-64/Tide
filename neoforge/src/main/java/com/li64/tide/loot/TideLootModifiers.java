package com.li64.tide.loot;

import com.li64.tide.Tide;
import com.li64.tide.loot.modifiers.*;
import com.mojang.serialization.MapCodec;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.loot.IGlobalLootModifier;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.function.Supplier;

public class TideLootModifiers {
    public static final DeferredRegister<MapCodec<? extends IGlobalLootModifier>> MODIFIER_SERIALIZERS =
            DeferredRegister.create(NeoForgeRegistries.GLOBAL_LOOT_MODIFIER_SERIALIZERS, Tide.MOD_ID);

    public static final Supplier<MapCodec<? extends IGlobalLootModifier>> FISHING_JUNK =
            MODIFIER_SERIALIZERS.register("fishing_junk", () -> FishingJunkModifier.CODEC);
    public static final Supplier<MapCodec<? extends IGlobalLootModifier>> BAIT_COMMON =
            MODIFIER_SERIALIZERS.register("bait_common", () -> CommonBaitModifier.CODEC);
    public static final Supplier<MapCodec<? extends IGlobalLootModifier>> BAIT_UNCOMMON =
            MODIFIER_SERIALIZERS.register("bait_uncommon", () -> UncommonBaitModifier.CODEC);
    public static final Supplier<MapCodec<? extends IGlobalLootModifier>> BAIT_RARE =
            MODIFIER_SERIALIZERS.register("bait_rare", () -> RareBaitModifier.CODEC);
    public static final Supplier<MapCodec<? extends IGlobalLootModifier>> NOTE_IN_CRATES =
            MODIFIER_SERIALIZERS.register("note_in_crates", () -> NoteInCratesModifier.CODEC);

    public static void register(IEventBus bus) {
        MODIFIER_SERIALIZERS.register(bus);
    }
}