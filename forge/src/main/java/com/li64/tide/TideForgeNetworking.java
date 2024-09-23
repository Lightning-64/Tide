package com.li64.tide;

import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;

import java.util.function.BiConsumer;
import java.util.function.Function;

public class TideForgeNetworking {
    private static SimpleChannel CHANNEL_INSTANCE;
    private static final ResourceLocation CHANNEL_ID = Tide.resource("messages");

    private static int packetId = 0;
    private static int id(){
        return packetId++;
    }

    public static void init() {
        CHANNEL_INSTANCE = NetworkRegistry.ChannelBuilder
                .named(CHANNEL_ID)
                .networkProtocolVersion(() -> "1.0")
                .clientAcceptedVersions(s -> true)
                .serverAcceptedVersions(s -> true)
                .simpleChannel();
    }

    public static <T> void registerPacket(Class<T> msgClass,
                                          BiConsumer<T, FriendlyByteBuf> encoder,
                                          Function<FriendlyByteBuf, T> decoder,
                                          BiConsumer<T, Player> handler,
                                          NetworkDirection direction) {

        CHANNEL_INSTANCE.messageBuilder(msgClass, id(), direction)
                .decoder(decoder)
                .encoder(encoder)
                .consumerMainThread((msg, ctx) -> handler.accept(msg, ctx.get().getSender()))
                .add();
    }

    public static <T> void sendToServer(T message) {
        CHANNEL_INSTANCE.sendToServer(message);
    }

    public static <T> void sendToPlayer(T msg, ServerPlayer player) {
        CHANNEL_INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), msg);
    }
}
