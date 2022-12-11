package whocraft.tardis_refined.common.tardis.themes;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;


public class DesktopTheme {

    public boolean availableByDefault = false;
    public String id = "";
    public ResourceLocation location;



    public DesktopTheme(String id, ResourceLocation location, boolean availableByDefault) {
        this.id = id;
        this.availableByDefault = availableByDefault;
        this.location = location;
    }

    public Component getDisplayName() {
        return Component.translatable("tardis_refined.desktop." + id);
    }

}
