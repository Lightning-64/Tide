package com.li64.tide.network.messages;

import com.li64.tide.Tide;
import com.li64.tide.data.player.TidePlayerData;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class ReadProfileMsg {
    public static final ResourceLocation ID = Tide.resource("read_profile");

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
}