package com.li64.tide.network;

import com.li64.tide.network.messages.*;
import com.li64.tide.platform.services.TideNetworkPlatform;

public class TideMessages {
    public static void init(TideNetworkPlatform network) {
        network.registerClientBoundPacket(OpenJournalMsg.class, OpenJournalMsg.ID, OpenJournalMsg::encode, OpenJournalMsg::new, OpenJournalMsg::handle);
        network.registerClientBoundPacket(MinigameClientMsg.class, MinigameClientMsg.ID, MinigameClientMsg::encode, MinigameClientMsg::new, MinigameClientMsg::handle);
        network.registerClientBoundPacket(SyncDataMsg.class, SyncDataMsg.ID, SyncDataMsg::encode, SyncDataMsg::new, SyncDataMsg::handle);
        network.registerClientBoundPacket(ShowToastMsg.class, ShowToastMsg.ID, ShowToastMsg::encode, ShowToastMsg::new, ShowToastMsg::handle);

        network.registerServerBoundPacket(MinigameServerMsg.class, MinigameServerMsg.ID, MinigameServerMsg::encode, MinigameServerMsg::new, MinigameServerMsg::handle);
        network.registerServerBoundPacket(ReadProfileMsg.class, ReadProfileMsg.ID, ReadProfileMsg::encode, ReadProfileMsg::new, ReadProfileMsg::handle);
    }
}
