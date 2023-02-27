package whocraft.tardis_refined.common.util;

import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;
import whocraft.tardis_refined.TRConfig;

public class DimensionUtil {

    public static boolean isAllowedDimension(ResourceKey<Level> level) {
        var bannedDimensions = TRConfig.COMMON.BANNED_DIMENSIONS.get();
        return !level.location().getNamespace().toString().contains("tardis") && !bannedDimensions.contains(level.location().toString());
    }

}
