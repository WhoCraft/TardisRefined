package whocraft.tardis_refined.common.network.messages.player;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import org.jetbrains.annotations.NotNull;
import whocraft.tardis_refined.common.capability.player.TardisPlayerInfo;
import whocraft.tardis_refined.common.network.MessageC2S;
import whocraft.tardis_refined.common.network.MessageContext;
import whocraft.tardis_refined.common.network.MessageType;
import whocraft.tardis_refined.common.network.TardisNetwork;

public class C2SExitTardisView extends MessageC2S {

    public C2SExitTardisView() {
    }

    public C2SExitTardisView(FriendlyByteBuf buf) {
    }

    @Override
    public @NotNull MessageType getType() {
        return TardisNetwork.TARDIS_EXIT;
    }

    @Override
    public void toBytes(FriendlyByteBuf buf) {

    }

    @Override
    public void handle(MessageContext context) {
        ServerPlayer player = context.getPlayer();

        TardisPlayerInfo.get(player).ifPresent(tardisInfo -> {
            if (tardisInfo.isViewingTardis()) {
                // Ending shell view only needs the saved return location.
                // Requiring the viewed TARDIS dimension/operator to be loaded
                // could leave the player permanently flagged as viewing it.
                tardisInfo.endShellView(player);
            }
        });

    }
}
