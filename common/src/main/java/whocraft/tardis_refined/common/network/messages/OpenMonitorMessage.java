package whocraft.tardis_refined.common.network.messages;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;
import whocraft.tardis_refined.client.screen.CancelDesktopScreen;
import whocraft.tardis_refined.client.screen.MonitorScreen;
import whocraft.tardis_refined.common.network.MessageContext;
import whocraft.tardis_refined.common.network.MessageS2C;
import whocraft.tardis_refined.common.network.MessageType;
import whocraft.tardis_refined.common.network.TardisNetwork;
import whocraft.tardis_refined.common.tardis.TardisNavLocation;
import whocraft.tardis_refined.common.util.Platform;


public class OpenMonitorMessage extends MessageS2C {

    private boolean desktopGenerating;
    private BlockPos currentPos;
    private Direction currentDir;
    private String currentDim;
    private BlockPos targetPos;
    private Direction targetDir;
    private String targetDim;

    public OpenMonitorMessage(boolean desktopGenerating, TardisNavLocation currentLocation, TardisNavLocation targetLocation) {
        this.desktopGenerating = desktopGenerating;
        this.currentPos = currentLocation.position;
        this.currentDir = currentLocation.rotation;
        this.currentDim = currentLocation.level.dimension().location().getPath().toUpperCase();
        this.targetPos = targetLocation.position;
        this.targetDir = targetLocation.rotation;
        this.targetDim = targetLocation.level.dimension().location().getPath().toUpperCase();
    }

    public OpenMonitorMessage(FriendlyByteBuf friendlyByteBuf) {
        this.desktopGenerating = friendlyByteBuf.readBoolean();
        this.currentPos = friendlyByteBuf.readBlockPos();
        this.currentDir = Direction.from2DDataValue(friendlyByteBuf.readInt());
        this.currentDim = friendlyByteBuf.readComponent().getString();
        this.targetPos = friendlyByteBuf.readBlockPos();
        this.targetDir = Direction.from2DDataValue(friendlyByteBuf.readInt());
        this.targetDim = friendlyByteBuf.readComponent().getString();
    }

    @NotNull
    @Override
    public MessageType getType() {
        return TardisNetwork.OPEN_MONITOR;
    }

    @Override
    public void toBytes(FriendlyByteBuf buf) {
        buf.writeBoolean(this.desktopGenerating);
        buf.writeBlockPos(this.currentPos);
        buf.writeInt(this.currentDir.get2DDataValue());
        buf.writeComponent(Component.literal(currentDim));
        buf.writeBlockPos(this.targetPos);
        buf.writeInt(this.targetDir.get2DDataValue());
        buf.writeComponent(Component.literal(targetDim));
    }


    @Override
    public void handle(MessageContext context) {
        handleScreens();
    }

    @Environment(EnvType.CLIENT)
    private void handleScreens() {
        // Open the monitor.
        if (this.desktopGenerating) {
            Minecraft.getInstance().setScreen(new CancelDesktopScreen());
        } else {
            Minecraft.getInstance().setScreen(new MonitorScreen(this.currentPos, this.currentDir, this.currentDim, this.targetPos, this.targetDir, this.targetDim));
        }
    }
}
