package com.li64.tide.data;

import net.minecraft.nbt.CompoundTag;

public interface TideEntity {
    CompoundTag getTidePlayerData();

    void setTidePlayerData(CompoundTag tag);
}
