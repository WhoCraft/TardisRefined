package whocraft.tardis_refined.compat.valkyrienskies;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import whocraft.tardis_refined.common.tardis.TardisNavLocation;

/** No-op adapter used while the optional Valkyrien Skies integration is disabled on 26.3. */
public final class VSHelper {
    private VSHelper() {}

    public static boolean isChunkInShipyard(ChunkPos pos) {
        return false;
    }

    public static TardisNavLocation toWorldLocation(TardisNavLocation location) {
        return location;
    }

    public static BlockPos toWorldPosition(Level level, BlockPos pos) {
        return pos;
    }

    public static Vec3 toWorldPosition(Level level, BlockPos pos, Vec3 vector) {
        return vector;
    }
}
