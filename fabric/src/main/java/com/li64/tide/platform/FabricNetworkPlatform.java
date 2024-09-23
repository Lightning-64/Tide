package com.li64.tide.platform;

import com.li64.tide.networking.ClientMsgRegistration;
import com.li64.tide.platform.services.TideNetworkPlatform;
import io.netty.buffer.ByteBuf;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.codec.StreamMemberEncoder;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;

import java.util.ArrayList;
import java.util.function.BiConsumer;
import java.util.function.Function;

public class FabricNetworkPlatform implements TideNetworkPlatform {
    public ArrayList<ClientMsgRegistration<? extends CustomPacketPayload>> clientMessages = new ArrayList<>();

    @Override
    public <T extends CustomPacketPayload> void registerClientBoundPacket(Class<T> msgClass, CustomPacketPayload.Type<T> type, BiConsumer<T, RegistryFriendlyByteBuf> encoder, Function<RegistryFriendlyByteBuf, T> decoder, BiConsumer<T, Player> handler) {
        PayloadTypeRegistry.playS2C().register(type, CustomPacketPayload.codec(encoder::accept, decoder::apply));

        clientMessages.add(new ClientMsgRegistration<>(type, handler));
    }

    @Override
    public <T extends CustomPacketPayload> void registerServerBoundPacket(Class<T> msgClass, CustomPacketPayload.Type<T> type, BiConsumer<T, RegistryFriendlyByteBuf> encoder, Function<RegistryFriendlyByteBuf, T> decoder, BiConsumer<T, Player> handler) {
        PayloadTypeRegistry.playC2S().register(type, CustomPacketPayload.codec(encoder::accept, decoder::apply));

        ServerPlayNetworking.registerGlobalReceiver(type, (payload, context) ->
                context.player().server.execute(() -> handler.accept(payload, context.player())));
    }

    @Override
    public void sendToPlayer(CustomPacketPayload message, ServerPlayer player) {
        ServerPlayNetworking.send(player, message);
    }

    @Override
    public void sendToServer(CustomPacketPayload message) {
        ClientPlayNetworking.send(message);
    }

    @Override
    public void registerHandlers() {
        clientMessages.forEach(this::registerClientHandler);
    }

//    public <B extends FriendlyByteBuf, T extends CustomPacketPayload> StreamCodec<B, T> getCodec(BiConsumer<T, B> encodeFunc, Function<B, T> decodeFunc) {
//        return StreamCodec.of((b, t) -> encodeFunc.accept(t, b), decodeFunc::apply);
//    }

    public <T extends CustomPacketPayload> void registerClientHandler(ClientMsgRegistration<T> reg) {
        BiConsumer<T, Player> handler = reg.handler();
        ClientPlayNetworking.registerGlobalReceiver(reg.type(), (payload, context) ->
                context.client().execute(() -> handler.accept(payload, context.player())));
    }
}
