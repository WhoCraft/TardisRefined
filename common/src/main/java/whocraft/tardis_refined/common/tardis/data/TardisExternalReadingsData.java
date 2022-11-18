package whocraft.tardis_refined.common.tardis.data;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import whocraft.tardis_refined.NbtConstants;
import whocraft.tardis_refined.common.capability.TardisLevelOperator;

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

    public ServerLevel getLevel() {
        return this.lastKnownLocation.level;
    }

    public CompoundTag saveData(CompoundTag tag) {
        tag.put(NbtConstants.TARDIS_EXT_READ_LAST_KNOWN_POS, NbtUtils.writeBlockPos(this.lastKnownLocation.position));
        tag.putInt(NbtConstants.TARDIS_EXT_READ_LAST_KNOWN_ROT, this.lastKnownLocation.rotation.get2DDataValue());
        tag.putString(NbtConstants.TARDIS_EXT_READ_LAST_KNOWN_LVL_MODID, this.lastKnownLocation.level.dimension().location().getNamespace());
        tag.putString(NbtConstants.TARDIS_EXT_READ_LAST_KNOWN_LVL_LOC, this.lastKnownLocation.level.dimension().location().getPath());
        return tag;
    }

    public void loadData(CompoundTag tag) {
        BlockPos lkPosition = NbtUtils.readBlockPos(tag.getCompound(NbtConstants.TARDIS_EXT_READ_LAST_KNOWN_POS));
        int lkRotation = tag.getInt(NbtConstants.TARDIS_EXT_READ_LAST_KNOWN_ROT);
        String lkLevelModID = tag.getString(NbtConstants.TARDIS_EXT_READ_LAST_KNOWN_LVL_MODID);
        String lkLevelLoc = tag.getString(NbtConstants.TARDIS_EXT_READ_LAST_KNOWN_LVL_LOC);

        if (lkLevelLoc != null && lkLevelModID != null && lkLevelLoc != null) {
            // Fetch the level.

            ServerLevel level = operator.getLevel().getServer().levels.get(ResourceKey.create(Registry.DIMENSION_REGISTRY, new ResourceLocation(lkLevelModID, lkLevelLoc)));
            if (level != null) {

                this.lastKnownLocation = new TardisNavLocation(lkPosition, Direction.from2DDataValue(lkRotation), level);
            }
        }
    }
}
