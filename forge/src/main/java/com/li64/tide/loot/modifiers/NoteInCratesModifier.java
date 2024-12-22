package com.li64.tide.loot.modifiers;

import com.mojang.serialization.Codec;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.common.loot.LootModifier;

import java.util.Random;

public class NoteInCratesModifier extends LootModifier {
    public static final Codec<NoteInCratesModifier> CODEC = newCodec();

    public NoteInCratesModifier() {
        super(new LootItemCondition[0]);
    }

    public NoteInCratesModifier(LootItemCondition[] conditionsIn) {
        super(conditionsIn);
    }

    @Override
    protected ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot, LootContext context) {
        if (generatedLoot.isEmpty()) return generatedLoot;
        if (!(context.getQueriedLootTableId().toString().contains("crates/overworld/water_ocean")
            || context.getQueriedLootTableId().toString().contains("crates/overworld/water_river"))) return generatedLoot;

        String contents = switch (new Random().nextInt(0, 4)) {
            case 0 -> "note.tide.midas_fish.contents";
            case 1 -> "note.tide.voidseeker.contents";
            case 2 -> "note.tide.aquathorn.contents";
            default -> "note.tide.shooting_starfish.contents";
        };

        ItemStack note = Items.WRITTEN_BOOK.getDefaultInstance();
        CompoundTag tag = note.getOrCreateTag();

        ListTag pages = new ListTag();
        pages.add(StringTag.valueOf(Component.Serializer.toJson(Component.translatable(contents))));

        tag.put("pages", pages);
        tag.putString("title", Component.translatable("note.tide.title").getString());
        tag.putString("author", Component.translatable("note.tide.author").getString());

        note.setTag(tag);

        generatedLoot.add(note);
        return generatedLoot;
    }

    public static Codec<NoteInCratesModifier> newCodec() {
        return Codec.unit(NoteInCratesModifier::new);
    }

    @Override
    public Codec<? extends IGlobalLootModifier> codec() {
        return CODEC;
    }
}