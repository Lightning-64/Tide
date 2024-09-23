package com.li64.tide.platform;

import com.li64.tide.TideForgeNetworking;
import com.li64.tide.platform.services.TideNetworkPlatform;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.network.NetworkDirection;

import java.util.function.BiConsumer;
import java.util.function.Function;

public class ForgeNetworkPlatform implements TideNetworkPlatform {
    @Override
    public <T extends CustomPacketPayload> void registerClientBoundPacket(Class<T> msgClass,
                                                                          CustomPacketPayload.Type<T> type,
                                                                          BiConsumer<T, RegistryFriendlyByteBuf> encodeFunc,
                                                                          Function<RegistryFriendlyByteBuf, T> decodeFunc,
                                                                          BiConsumer<T, Player> handler) {

        TideForgeNetworking.registerPacket(msgClass, encodeFunc, decodeFunc, handler, NetworkDirection.PLAY_TO_CLIENT);
    }

    @Override
    public <T extends CustomPacketPayload> void registerServerBoundPacket(Class<T> msgClass,
                                                                          CustomPacketPayload.Type<T> type,
                                                                          BiConsumer<T, RegistryFriendlyByteBuf> encodeFunc,
                                                                          Function<RegistryFriendlyByteBuf, T> decodeFunc,
                                                                          BiConsumer<T, Player> handler) {

        TideForgeNetworking.registerPacket(msgClass, encodeFunc, decodeFunc, handler, NetworkDirection.PLAY_TO_SERVER);
    }


    @Override
    public void sendToPlayer(CustomPacketPayload message, ServerPlayer player) {
        TideForgeNetworking.sendToPlayer(message, player);
    }

    @Override
    public void sendToServer(CustomPacketPayload message) {
        TideForgeNetworking.sendToServer(message);
    }
}
