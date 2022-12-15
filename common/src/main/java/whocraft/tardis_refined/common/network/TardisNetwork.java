package whocraft.tardis_refined.common.network;

import net.minecraft.resources.ResourceLocation;
import whocraft.tardis_refined.TardisRefined;
import whocraft.tardis_refined.common.network.messages.*;

public class TardisNetwork {

    public static final NetworkManager NETWORK = NetworkManager.create(new ResourceLocation(TardisRefined.MODID, "channel"));

    public static MessageType SYNC_LEVELS, INT_REACTION, OPEN_MONITOR, CHANGE_SHELL, CHANGE_DESKTOP, CANCEL_CHANGE_DESKTOP;

    public static void init() {
        SYNC_LEVELS = NETWORK.registerS2C("sync_levels", SyncLevelListMessage::new);
        INT_REACTION = NETWORK.registerS2C("int_reaction", SyncIntReactionsMessage::new);
        OPEN_MONITOR = NETWORK.registerS2C("open_monitor", OpenMonitorMessage::new);
        CHANGE_SHELL = NETWORK.registerC2S("change_shell", ChangeShellMessage::new);
        CHANGE_DESKTOP = NETWORK.registerC2S("change_desktop", ChangeDesktopMessage::new);
        CANCEL_CHANGE_DESKTOP = NETWORK.registerC2S("cancel_change_desktop", CancelDesktopChangeMessage::new);
    }

}
