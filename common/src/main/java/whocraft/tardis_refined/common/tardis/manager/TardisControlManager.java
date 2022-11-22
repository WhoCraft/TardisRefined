package whocraft.tardis_refined.common.tardis.manager;

import net.minecraft.nbt.CompoundTag;
import whocraft.tardis_refined.common.capability.TardisLevelOperator;
import whocraft.tardis_refined.common.tardis.TardisNavLocation;

public class TardisControlManager {

    private TardisLevelOperator operator;

    // Location based.
    private TardisNavLocation believedLocation;
    private TardisNavLocation targetLocation;

    public TardisControlManager(TardisLevelOperator operator) {
        this.operator = operator;
    }

    public void load(CompoundTag tag) {

    }

    public CompoundTag save(CompoundTag tag) {
        return tag;
    }

}
