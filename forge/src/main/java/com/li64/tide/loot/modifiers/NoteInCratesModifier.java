package com.li64.tide.loot.modifiers;

import com.mojang.serialization.MapCodec;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.server.network.Filterable;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.WrittenBookContent;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.common.loot.LootModifier;

import java.util.List;
import java.util.Random;

public class NoteInCratesModifier extends LootModifier {
    public static final MapCodec<NoteInCratesModifier> CODEC = newCodec();

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

        String contents = switch (new Random().nextInt(0, 3)) {
            case 0 -> "note.tide.midas_fish.contents";
            case 1 -> "note.tide.voidseeker.contents";
            default -> "note.tide.shooting_starfish.contents";
        };

        ItemStack note = Items.WRITTEN_BOOK.getDefaultInstance();
        note.set(DataComponents.WRITTEN_BOOK_CONTENT,
            new WrittenBookContent(
                Filterable.passThrough(Component.translatable("note.tide.title").getString()),
                Component.translatable("note.tide.author").getString(),
                0,
                List.of(Filterable.passThrough(Component.translatable(contents))),
                true
            )
        );
        generatedLoot.add(note);

        return generatedLoot;
    }

    public static MapCodec<NoteInCratesModifier> newCodec() {
        return MapCodec.unit(NoteInCratesModifier::new);
    }

    @Override
    public MapCodec<? extends IGlobalLootModifier> codec() {
        return CODEC;
    }
}