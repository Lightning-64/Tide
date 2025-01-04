package com.li64.tide.data.rods;

import com.li64.tide.data.TideDataLoader;
import com.mojang.serialization.Codec;

public class AccessoryDataLoader extends TideDataLoader<AccessoryData> {
    public AccessoryDataLoader() {
        super("rod_accessories");
    }

    @Override
    protected Codec<AccessoryData> entryCodec() {
        return AccessoryData.CODEC;
    }

    @Override
    protected String getDataTypeMessage() {
        return "fishing rod accessory data entries";
    }
}
