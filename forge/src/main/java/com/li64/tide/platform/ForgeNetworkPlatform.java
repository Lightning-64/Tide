package com.li64.tide.platform;

import com.li64.tide.TideForgeNetworking;
import com.li64.tide.platform.services.TideNetworkPlatform;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.network.NetworkDirection;

import java.util.function.BiConsumer;
import java.util.function.Function;

public class ForgeNetworkPlatform implements TideNetworkPlatform {
    @Override
    public <T> void registerClientBoundPacket(Class<T> msgClass, ResourceLocation id,
                                              BiConsumer<T, FriendlyByteBuf> encoder,
                                              Function<FriendlyByteBuf, T> decoder,
                                              BiConsumer<T, Player> handler) {

        TideForgeNetworking.registerPacket(msgClass, encoder, decoder, handler, NetworkDirection.PLAY_TO_CLIENT);
    }

    @Override
    public <T> void registerServerBoundPacket(Class<T> msgClass, ResourceLocation id,
                                              BiConsumer<T, FriendlyByteBuf> encodeFunc,
                                              Function<FriendlyByteBuf, T> decodeFunc,
                                              BiConsumer<T, Player> handler) {

        TideForgeNetworking.registerPacket(msgClass, encodeFunc, decodeFunc, handler, NetworkDirection.PLAY_TO_SERVER);
    }


    @Override
    public <T> void sendToPlayer(T message, ServerPlayer player) {
        TideForgeNetworking.sendToPlayer(message, player);
    }

    @Override
    public <T> void sendToServer(T message) {
        TideForgeNetworking.sendToServer(message);
    }
}
