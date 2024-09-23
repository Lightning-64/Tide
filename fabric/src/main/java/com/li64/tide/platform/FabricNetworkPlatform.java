package com.li64.tide.platform;

import com.li64.tide.platform.services.TideNetworkPlatform;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Function;

public class FabricNetworkPlatform implements TideNetworkPlatform {
    private static final Map<Class<?>, RegisteredMessage<?>> messages = new HashMap<>();
    public ArrayList<ClientHandlerRegistration<?>> clientMessages = new ArrayList<>();

    @Override
    public <T> void registerClientBoundPacket(Class<T> msgClass, ResourceLocation id, BiConsumer<T, FriendlyByteBuf> encoder, Function<FriendlyByteBuf, T> decoder, BiConsumer<T, Player> handler) {
        messages.put(msgClass, new RegisteredMessage<>(id, encoder));
        clientMessages.add(new ClientHandlerRegistration<>(id, decoder, handler));
    }

    @Override
    public <T> void registerServerBoundPacket(Class<T> msgClass, ResourceLocation id, BiConsumer<T, FriendlyByteBuf> encoder, Function<FriendlyByteBuf, T> decoder, BiConsumer<T, Player> handler) {
        messages.put(msgClass, new RegisteredMessage<>(id, encoder));
        ServerPlayNetworking.registerGlobalReceiver(id, ((server, player, listener, buf, responseSender) -> {
            T message = decoder.apply(buf);
            server.execute(() -> {
                handler.accept(message, player);
            });
        }));
    }

    @Override @SuppressWarnings("unchecked")
    public <T> void sendToPlayer(T message, ServerPlayer player) {
        RegisteredMessage<T> messageRegistration = (RegisteredMessage<T>) messages.get(message.getClass());

        ResourceLocation identifier = messageRegistration.id();
        FriendlyByteBuf buf = PacketByteBufs.create();

        messageRegistration.encoder().accept(message, buf);
        ServerPlayNetworking.send(player, identifier, buf);
    }

    @Override @SuppressWarnings("unchecked")
    public <T> void sendToServer(T message) {
        RegisteredMessage<T> messageRegistration = (RegisteredMessage<T>) messages.get(message.getClass());

        ResourceLocation identifier = messageRegistration.id();
        FriendlyByteBuf buf = PacketByteBufs.create();

        messageRegistration.encoder().accept(message, buf);
        ClientPlayNetworking.send(identifier, buf);
    }

    @Override
    public void registerHandlers() {
        clientMessages.forEach(this::registerClientHandler);
    }

    public <T> void registerClientHandler(ClientHandlerRegistration<T> reg) {
        ResourceLocation identifier = reg.id();
        BiConsumer<T, Player> handler = reg.handler();

        ClientPlayNetworking.registerGlobalReceiver(identifier, ((client, listener, buf, responseSender) -> {
            T message = reg.decoder().apply(buf);
            client.execute(() -> handler.accept(message, client.player));
        }));
    }

    public record RegisteredMessage<T>(ResourceLocation id, BiConsumer<T, FriendlyByteBuf> encoder) {}
    public record ClientHandlerRegistration<T>(ResourceLocation id, Function<FriendlyByteBuf, T> decoder, BiConsumer<T, Player> handler) {}
}
