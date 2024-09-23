package com.li64.tide.platform.services;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;

import java.util.function.BiConsumer;
import java.util.function.Function;

public interface TideNetworkPlatform {
    <T extends CustomPacketPayload> void registerClientBoundPacket(Class<T> msgClass,
                                CustomPacketPayload.Type<T> type,
                                BiConsumer<T, RegistryFriendlyByteBuf> encoder,
                                Function<RegistryFriendlyByteBuf, T> decoder,
                                BiConsumer<T, Player> handler);

    <T extends CustomPacketPayload> void registerServerBoundPacket(Class<T> msgClass,
                                CustomPacketPayload.Type<T> type,
                                BiConsumer<T, RegistryFriendlyByteBuf> encoder,
                                Function<RegistryFriendlyByteBuf, T> decoder,
                                BiConsumer<T, Player> handler);

    void sendToPlayer(CustomPacketPayload message, ServerPlayer player);

    void sendToServer(CustomPacketPayload message);

    default void registerHandlers() {};
}
