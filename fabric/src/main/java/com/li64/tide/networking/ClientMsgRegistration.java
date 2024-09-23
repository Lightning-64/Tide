package com.li64.tide.networking;

import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.entity.player.Player;

import java.util.function.BiConsumer;

public record ClientMsgRegistration<T extends CustomPacketPayload>(CustomPacketPayload.Type<T> type,
                                                                   BiConsumer<T, Player> handler) {
}
