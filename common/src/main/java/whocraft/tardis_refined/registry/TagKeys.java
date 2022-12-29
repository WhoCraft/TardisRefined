package whocraft.tardis_refined.registry;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;
import whocraft.tardis_refined.TardisRefined;
import whocraft.tardis_refined.common.util.RegistryHelper;

public class TagKeys {
    public static TagKey<Biome> IS_MOUNTAIN_OR_OCEAN = RegistryHelper.makeGenericBiomeTagCollection("is_mountain_or_ocean");
    public static TagKey<Biome> TARDIS_ROOT_CLUSTER = RegistryHelper.makeBiomeTagForFeature("tardis_root_cluster");

    public static void init(){}
}
