package com.li64.tide.data;

import net.minecraft.nbt.CompoundTag;

public interface TidePlayer {
    CompoundTag tide$getTidePlayerData();

    void tide$setTidePlayerData(CompoundTag tag);
}
