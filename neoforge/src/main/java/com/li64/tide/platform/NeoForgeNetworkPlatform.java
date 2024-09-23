package com.li64.tide.platform;

import com.li64.tide.TideNeoForge;
import com.li64.tide.platform.services.TideNetworkPlatform;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.PacketDistributor;

import java.util.function.BiConsumer;
import java.util.function.Function;

public class NeoForgeNetworkPlatform implements TideNetworkPlatform {
    @Override
    public <T extends CustomPacketPayload> void registerClientBoundPacket(Class<T> msgClass,
                                          CustomPacketPayload.Type<T> type,
                                          BiConsumer<T, RegistryFriendlyByteBuf> encodeFunc,
                                          Function<RegistryFriendlyByteBuf, T> decodeFunc,
                                          BiConsumer<T, Player> handler) {

        TideNeoForge.REGISTRAR.playToClient(
            type,
            StreamCodec.of(
                    (buf, msg) -> encodeFunc.accept(msg, buf),
                    decodeFunc::apply
            ),
            (msg, context) -> handler.accept(msg, context.player())
        );
    }

    @Override
    public <T extends CustomPacketPayload> void registerServerBoundPacket(Class<T> msgClass,
                                        CustomPacketPayload.Type<T> type,
                                          BiConsumer<T, RegistryFriendlyByteBuf> encodeFunc,
                                          Function<RegistryFriendlyByteBuf, T> decodeFunc,
                                          BiConsumer<T, Player> handler) {

        TideNeoForge.REGISTRAR.playToServer(
                type,
                StreamCodec.of(
                        (buf, msg) -> encodeFunc.accept(msg, buf),
                        decodeFunc::apply
                ),
                (msg, context) -> handler.accept(msg, context.player())
        );
    }


    @Override
    public void sendToPlayer(CustomPacketPayload message, ServerPlayer player) {
        PacketDistributor.sendToPlayer(player, message);
    }

    @Override
    public void sendToServer(CustomPacketPayload message) {
        PacketDistributor.sendToServer(message);
    }
}
