package com.li64.tide.network.messages;

import com.google.common.collect.ImmutableList;
import com.li64.tide.Tide;
import com.li64.tide.data.rods.BaitData;
import com.mojang.serialization.Codec;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class UpdateBaitDataMsg implements CustomPacketPayload {
    public static final Type<UpdateBaitDataMsg> TYPE = new Type<>(Tide.resource("update_bait_data"));

    private final List<BaitData> baitData;

    public UpdateBaitDataMsg() {
        baitData = Tide.BAIT_DATA.get();
    }

    public UpdateBaitDataMsg(FriendlyByteBuf buf) {
        baitData = readEntries(BaitData.CODEC, buf);
    }

    public static void encode(UpdateBaitDataMsg message, FriendlyByteBuf buf) {
        writeEntries(message.baitData, BaitData.CODEC, buf);
    }

    public static <T> ImmutableList<T> readEntries(Codec<T> codec, FriendlyByteBuf buf) {
        ArrayList<T> tempValues = new ArrayList<>();
        int size = buf.readInt();
        for (int i = 0; i < size; i++) {
            tempValues.add(buf.readJsonWithCodec(codec));
        }
        return ImmutableList.copyOf(tempValues);
    }

    public static <T> void writeEntries(List<T> entries, Codec<T> codec, FriendlyByteBuf buf) {
        buf.writeInt(entries.size());
        entries.forEach(entry -> buf.writeJsonWithCodec(codec, entry));
    }

    public static void handle(UpdateBaitDataMsg message, Player player) {
        Tide.BAIT_DATA.set(message.baitData);
    }

    @Override
    public @NotNull Type<? extends CustomPacketPayload> type() { return TYPE; }
}