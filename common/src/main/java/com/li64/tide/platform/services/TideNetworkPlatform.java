package com.li64.tide.platform.services;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;

import java.util.function.BiConsumer;
import java.util.function.Function;

public interface TideNetworkPlatform {
    <T> void registerClientBoundPacket(Class<T> msgClass, ResourceLocation id,
                                       BiConsumer<T, FriendlyByteBuf> encoder,
                                       Function<FriendlyByteBuf, T> decoder,
                                       BiConsumer<T, Player> handler);

    <T> void registerServerBoundPacket(Class<T> msgClass, ResourceLocation id,
                                       BiConsumer<T, FriendlyByteBuf> encoder,
                                       Function<FriendlyByteBuf, T> decoder,
                                       BiConsumer<T, Player> handler);

    <T> void sendToPlayer(T message, ServerPlayer player);

    <T> void sendToServer(T message);

    default void registerHandlers() {};
}
