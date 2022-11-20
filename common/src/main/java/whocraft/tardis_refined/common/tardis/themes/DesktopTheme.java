package whocraft.tardis_refined.common.tardis.themes;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import whocraft.tardis_refined.common.tardis.interior.arctypes.ArchitectureTheme;


public class DesktopTheme extends ArchitectureTheme {

    public boolean availableByDefault = false;
    public String id = "";

    public DesktopTheme(String id, ResourceLocation location, String name, BlockPos offset, boolean availableByDefault) {
        super(location,name,offset);
        this.id = id;
        this.availableByDefault = availableByDefault;
    }

}
