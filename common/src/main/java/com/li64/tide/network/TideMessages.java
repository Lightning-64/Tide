package com.li64.tide.network;

import com.li64.tide.network.messages.*;
import com.li64.tide.platform.services.TideNetworkPlatform;

public class TideMessages {
    public static void init(TideNetworkPlatform network) {
        network.registerClientBoundPacket(OpenJournalMsg.class, OpenJournalMsg.TYPE, OpenJournalMsg::encode, OpenJournalMsg::new, OpenJournalMsg::handle);
        network.registerClientBoundPacket(MinigameClientMsg.class, MinigameClientMsg.TYPE, MinigameClientMsg::encode, MinigameClientMsg::new, MinigameClientMsg::handle);
        network.registerClientBoundPacket(SyncDataMsg.class, SyncDataMsg.TYPE, SyncDataMsg::encode, SyncDataMsg::new, SyncDataMsg::handle);
        network.registerClientBoundPacket(ShowToastMsg.class, ShowToastMsg.TYPE, ShowToastMsg::encode, ShowToastMsg::new, ShowToastMsg::handle);
        network.registerClientBoundPacket(UpdateJournalMsg.class, UpdateJournalMsg.TYPE, UpdateJournalMsg::encode, UpdateJournalMsg::new, UpdateJournalMsg::handle);

        network.registerServerBoundPacket(MinigameServerMsg.class, MinigameServerMsg.TYPE, MinigameServerMsg::encode, MinigameServerMsg::new, MinigameServerMsg::handle);
        network.registerServerBoundPacket(ReadProfileMsg.class, ReadProfileMsg.TYPE, ReadProfileMsg::encode, ReadProfileMsg::new, ReadProfileMsg::handle);
    }
}
