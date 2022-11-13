package whocraft.tardis_refined.common.tardis.interior.arctypes;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;


public class DesktopTheme extends ArchitectureTheme {

    public boolean availableByDefault = false;

    public DesktopTheme(ResourceLocation location, String name, BlockPos offset, boolean availableByDefault) {
        super(location,name,offset);

        this.availableByDefault = availableByDefault;
    }

}
