package whocraft.tardis_refined.common.tardis.data;

import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.Rotation;
import whocraft.tardis_refined.common.capability.TardisLevelOperator;
import whocraft.tardis_refined.common.util.NbtUtil;

/**
 * External Shell data.
 * **/
public class TardisExternalReadingsData {

    private TardisLevelOperator operator;
    private TardisNavLocation lastKnownLocation;

    public TardisExternalReadingsData(TardisLevelOperator operator) {
        this.operator = operator;
    }

    public void setLastKnownLocation(TardisNavLocation lastKnownLocation) {
        this.lastKnownLocation = lastKnownLocation;
    }

    public TardisNavLocation getLastKnownLocation() {
        return this.lastKnownLocation;
    }

    public CompoundTag saveData(CompoundTag tag) {
        tag.putIntArray(NBT_LAST_KNOWN_POS, NbtUtil.getIntArrayFromBlockPos(this.lastKnownLocation.position));
        tag.putInt(NBT_LAST_KNOWN_ROT, this.lastKnownLocation.rotation);
        tag.putString(NBT_LAST_KNOWN_LVL_MODID, this.lastKnownLocation.level.dimension().location().getNamespace());
        tag.putString(NBT_LAST_KNOWN_LVL_LOC, this.lastKnownLocation.level.dimension().location().getPath());
        return tag;
    }

    public void loadData(CompoundTag tag) {
        int[] lkPosition = tag.getIntArray(NBT_LAST_KNOWN_POS);
        int lkRotation = tag.getInt(NBT_LAST_KNOWN_ROT);
        String lkLevelModID = tag.getString(NBT_LAST_KNOWN_LVL_MODID);
        String lkLevelLoc = tag.getString(NBT_LAST_KNOWN_LVL_LOC);

        if (lkLevelLoc != null && lkLevelModID != null && lkLevelLoc != null) {
            // Fetch the level.

            ServerLevel level = operator.getLevel().getServer().levels.get(ResourceKey.create(Registry.DIMENSION_REGISTRY, new ResourceLocation(lkLevelModID, lkLevelLoc)));
            if (level != null) {
                this.lastKnownLocation = new TardisNavLocation(NbtUtil.getBlockPosFromIntArray(lkPosition),lkRotation, level);
            }
        }
    }

    private static String NBT_LAST_KNOWN_POS = "terd_lk_position";
    private static String NBT_LAST_KNOWN_ROT = "terd_lk_rotation";
    private static String NBT_LAST_KNOWN_LVL_MODID = "terd_lk_lvl_id";
    private static String NBT_LAST_KNOWN_LVL_LOC = "terd_lk_lvl_loc";

}
