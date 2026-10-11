package whocraft.tardis_refined.common.network.messages.screens;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.Identifier;
import org.jetbrains.annotations.NotNull;
import whocraft.tardis_refined.client.ScreenHandler;
import whocraft.tardis_refined.common.capability.tardis.upgrades.UpgradeHandler;
import whocraft.tardis_refined.common.network.MessageContext;
import whocraft.tardis_refined.common.network.MessageS2C;
import whocraft.tardis_refined.common.network.MessageType;
import whocraft.tardis_refined.common.network.TardisNetwork;
import whocraft.tardis_refined.common.tardis.TardisNavLocation;

import java.util.Objects;


public class S2COpenMonitor extends MessageS2C {

    private final boolean desktopGenerating;
    private final TardisNavLocation currentLocation;
    private final TardisNavLocation targetLocation;
    private final CompoundTag upgradeHandlerNbt;
    private final Identifier currentShellTheme;
    private final Identifier currentShellPattern;

    public S2COpenMonitor(boolean desktopGenerating, TardisNavLocation currentLocation, TardisNavLocation targetLocation, UpgradeHandler upgradeHandler, Identifier currentShellTheme, Identifier currentShellPattern) {
        this.desktopGenerating = desktopGenerating;
        this.currentLocation = currentLocation;
        this.targetLocation = targetLocation;
        this.upgradeHandlerNbt = upgradeHandler.saveData(new CompoundTag());
        this.currentShellTheme = currentShellTheme;
        this.currentShellPattern = currentShellPattern;

    }

    public S2COpenMonitor(FriendlyByteBuf friendlyByteBuf) {
        this.desktopGenerating = friendlyByteBuf.readBoolean();
        this.currentLocation = TardisNavLocation.deserialize(Objects.requireNonNull(friendlyByteBuf.readNbt()));
        this.targetLocation = TardisNavLocation.deserialize(Objects.requireNonNull(friendlyByteBuf.readNbt()));
        this.upgradeHandlerNbt = friendlyByteBuf.readNbt();
        this.currentShellTheme = friendlyByteBuf.readIdentifier();
        this.currentShellPattern = friendlyByteBuf.readIdentifier();
    }

    @NotNull
    @Override
    public MessageType getType() {
        return TardisNetwork.OPEN_MONITOR;
    }

    @Override
    public void toBytes(FriendlyByteBuf buf) {
        buf.writeBoolean(this.desktopGenerating);
        buf.writeNbt(currentLocation.serialise());
        buf.writeNbt(targetLocation.serialise());
        buf.writeNbt(upgradeHandlerNbt);
        buf.writeIdentifier(this.currentShellTheme);
        buf.writeIdentifier(this.currentShellPattern);
    }


    @Override
    public void handle(MessageContext context) {
        handleScreens();
    }

    @Environment(EnvType.CLIENT)
    private void handleScreens() {
        // Open the monitor.
        ScreenHandler.openMonitorScreen(desktopGenerating, upgradeHandlerNbt, currentLocation, targetLocation, currentShellTheme, currentShellPattern);
    }

}
