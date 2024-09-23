package com.li64.tide.loot;

import com.li64.tide.Tide;
import com.li64.tide.loot.modifiers.*;
import com.mojang.serialization.Codec;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class TideLootModifiers {
    public static final DeferredRegister<Codec<? extends IGlobalLootModifier>> MODIFIER_SERIALIZERS =
            DeferredRegister.create(ForgeRegistries.Keys.GLOBAL_LOOT_MODIFIER_SERIALIZERS, Tide.MOD_ID);

    public static final RegistryObject<Codec<? extends IGlobalLootModifier>> FISHING_JUNK =
            MODIFIER_SERIALIZERS.register("fishing_junk", FishingJunkModifier::newCodec);
    public static final RegistryObject<Codec<? extends IGlobalLootModifier>> BAIT_COMMON =
            MODIFIER_SERIALIZERS.register("bait_common", CommonBaitModifier::newCodec);
    public static final RegistryObject<Codec<? extends IGlobalLootModifier>> BAIT_UNCOMMON =
            MODIFIER_SERIALIZERS.register("bait_uncommon", UncommonBaitModifier::newCodec);
    public static final RegistryObject<Codec<? extends IGlobalLootModifier>> BAIT_RARE =
            MODIFIER_SERIALIZERS.register("bait_rare", RareBaitModifier::newCodec);
    public static final RegistryObject<Codec<? extends IGlobalLootModifier>> NOTE_IN_CRATES =
            MODIFIER_SERIALIZERS.register("note_in_crates", NoteInCratesModifier::newCodec);

    public static void register(IEventBus bus) {
        MODIFIER_SERIALIZERS.register(bus);
    }
}
