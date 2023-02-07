package whocraft.tardis_refined.registry;

import net.minecraft.tags.TagKey;
import whocraft.tardis_refined.common.util.RegistryHelper;

public class TagKeys {
    public static TagKey<Biome> IS_MOUNTAIN_OR_OCEAN = RegistryHelper.makeGenericBiomeTagCollection("is_mountain_or_ocean");
    public static TagKey<Biome> TARDIS_ROOT_CLUSTER = RegistryHelper.makeBiomeTagForFeature("tardis_root_cluster");

    public static void init(){}
}