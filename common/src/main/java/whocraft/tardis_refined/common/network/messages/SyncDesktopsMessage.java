package whocraft.tardis_refined.common.network.messages;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import whocraft.tardis_refined.common.network.MessageContext;
import whocraft.tardis_refined.common.network.MessageS2C;
import whocraft.tardis_refined.common.network.MessageType;
import whocraft.tardis_refined.common.network.TardisNetwork;
import whocraft.tardis_refined.common.tardis.TardisDesktops;
import whocraft.tardis_refined.common.tardis.themes.DesktopTheme;

import java.util.ArrayList;
import java.util.List;

public class SyncDesktopsMessage extends MessageS2C {

    private final List<DesktopTheme> desktops;

    public SyncDesktopsMessage(List<DesktopTheme> desktops) {
        this.desktops = desktops;
    }

    public SyncDesktopsMessage(FriendlyByteBuf buf) {
        int size = buf.readInt();
        desktops = new ArrayList<>();

        for (int i = 0; i < size; i++) {
            desktops.add(getDesktopTheme(buf));
        }
    }

    private static DesktopTheme getDesktopTheme(FriendlyByteBuf buf) {
        ResourceLocation id = buf.readResourceLocation(); // ID
        ResourceLocation structure = buf.readResourceLocation(); // structure
        String name = buf.readUtf(); // name
        boolean avaliableByDefault = buf.readBoolean(); // availableByDefault
        DesktopTheme theme = new DesktopTheme(id, structure, avaliableByDefault);
        theme.setName(name);

        return theme;
    }


    @Override
    public MessageType getType() {
        return TardisNetwork.SYNC_DESKTOPS;
    }

    @Override
    public void toBytes(FriendlyByteBuf buf) {
        buf.writeInt(desktops.size());

        desktops.forEach(desktopTheme -> {
            writeDesktop(desktopTheme, buf);
        });
    }


    private void writeDesktop(DesktopTheme desktopTheme, FriendlyByteBuf buf) {
        buf.writeResourceLocation(desktopTheme.identifier); // ID
        buf.writeResourceLocation(desktopTheme.location); // texture
        buf.writeUtf(desktopTheme.name); // name
        buf.writeBoolean(desktopTheme.availableByDefault); // glow
    }


    @Override
    public void handle(MessageContext context) {
        TardisDesktops.clear();
        for (DesktopTheme desktop : desktops) {
            TardisDesktops.addDesktop(desktop);
        }
    }
}
