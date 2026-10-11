package whocraft.tardis_refined.common.network.messages.screens;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.Identifier;
import org.jetbrains.annotations.NotNull;
import whocraft.tardis_refined.client.ScreenHandler;
import whocraft.tardis_refined.common.network.MessageContext;
import whocraft.tardis_refined.common.network.MessageS2C;
import whocraft.tardis_refined.common.network.MessageType;
import whocraft.tardis_refined.common.network.TardisNetwork;


public class S2COpenShellSelection extends MessageS2C {

    private final Identifier currentShell;

    public S2COpenShellSelection(Identifier currentShell) {
        this.currentShell = currentShell;
    }

    public S2COpenShellSelection(FriendlyByteBuf friendlyByteBuf) {
        this.currentShell = friendlyByteBuf.readIdentifier();
    }

    @NotNull
    @Override
    public MessageType getType() {
        return TardisNetwork.OPEN_SHELL_SELECT;
    }

    @Override
    public void toBytes(FriendlyByteBuf buf) {
        buf.writeIdentifier(this.currentShell);
    }


    @Override
    public void handle(MessageContext context) {
        handleScreens();
    }

    @Environment(EnvType.CLIENT)
    private void handleScreens() {
        // Open the monitor.
        ScreenHandler.openShellSelection(currentShell);
    }

}
