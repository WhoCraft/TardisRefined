package whocraft.tardis_refined.common.tardis.interior.arctypes;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;

public class ArchitectureTheme {

    public ResourceLocation resourceLocation;
    public String name;
    public BlockPos offset;

    public ArchitectureTheme(ResourceLocation resourceLocation, String name, BlockPos offset) {
        this.resourceLocation = resourceLocation;
        this.name = name;
        this.offset = offset;
    }

}
