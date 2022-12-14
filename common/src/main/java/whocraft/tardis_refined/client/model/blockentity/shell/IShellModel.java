package whocraft.tardis_refined.client.model.blockentity.shell;

import net.minecraft.resources.ResourceLocation;

public interface IShellModel {

    void setDoorPosition(boolean open);

    ResourceLocation texture();
}
