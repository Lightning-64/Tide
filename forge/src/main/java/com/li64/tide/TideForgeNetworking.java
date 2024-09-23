package com.li64.tide;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.network.ChannelBuilder;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.SimpleChannel;

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
        CHANNEL_INSTANCE = ChannelBuilder
                .named(CHANNEL_ID)
                .networkProtocolVersion(1)
                .clientAcceptedVersions((status, version) -> version == 1)
                .serverAcceptedVersions((status, version) -> version == 1)
                .simpleChannel();
    }

    public static <T extends CustomPacketPayload> void registerPacket(Class<T> msgClass,
                                                                         BiConsumer<T, RegistryFriendlyByteBuf> encodeFunc,
                                                                         Function<RegistryFriendlyByteBuf, T> decodeFunc,
                                                                         BiConsumer<T, Player> handler,
                                                                         NetworkDirection<RegistryFriendlyByteBuf> direction) {

        CHANNEL_INSTANCE.messageBuilder(msgClass, id(), direction)
                .decoder(decodeFunc)
                .encoder(encodeFunc)
                .consumerMainThread((msg, ctx) -> handler.accept(msg, ctx.getSender()))
                .add();
    }

    public static <T> void sendToServer(T message) {
        CHANNEL_INSTANCE.send(message, PacketDistributor.SERVER.noArg());
    }

    public static <T> void sendToPlayer(T msg, ServerPlayer player) {
        CHANNEL_INSTANCE.send(msg, PacketDistributor.PLAYER.with(player));
    }
}
