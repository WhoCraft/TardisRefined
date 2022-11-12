package whocraft.tardis_refined.registry;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.dimension.DimensionType;
import whocraft.tardis_refined.TardisRefined;

public class DimensionTypes {

    public static ResourceKey<DimensionType> TARDIS;

    public static void register() {
        TARDIS = ResourceKey.create(Registry.DIMENSION_TYPE_REGISTRY, new ResourceLocation(TardisRefined.MODID, "tardis"));
    }
}
