package com.li64.tide.data.rods;

import com.li64.tide.data.TideDataLoader;
import com.mojang.serialization.Codec;

public class BaitDataLoader extends TideDataLoader<BaitData> {
    public BaitDataLoader() {
        super("bait");
    }

    @Override
    protected Codec<BaitData> entryCodec() {
        return BaitData.CODEC;
    }

    @Override
    protected String getDataTypeMessage() {
        return "bait data entries";
    }
}
