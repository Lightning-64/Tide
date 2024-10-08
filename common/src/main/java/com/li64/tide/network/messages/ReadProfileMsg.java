package com.li64.tide.network.messages;

import com.li64.tide.Tide;
import com.li64.tide.data.player.TidePlayerData;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class ReadProfileMsg implements CustomPacketPayload {
    public static final Type<ReadProfileMsg> TYPE = new Type<>(Tide.resource("read_profile"));

    public final int fish;

    public ReadProfileMsg(ItemStack fishItem) {
        this.fish = Item.getId(fishItem.getItem());
    }

    public ReadProfileMsg(FriendlyByteBuf buf) {
        this.fish = buf.readInt();
    }

    public static void encode(ReadProfileMsg message, FriendlyByteBuf buf) {
        buf.writeInt(message.fish);
    }

    public static void handle(ReadProfileMsg message, Player player) {
        TidePlayerData data = TidePlayerData.getOrCreate((ServerPlayer) player);
        data.markAsRead(message.fish);
        data.syncTo((ServerPlayer) player);
    }

    @Override
    public @NotNull Type<? extends CustomPacketPayload> type() { return TYPE; }
}