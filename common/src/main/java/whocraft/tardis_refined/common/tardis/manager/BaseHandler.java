package whocraft.tardis_refined.common.tardis.manager;

import net.minecraft.nbt.CompoundTag;

public abstract class BaseHandler {

    public abstract void tick();
    public abstract CompoundTag saveData(CompoundTag tag);
    public abstract void loadData(CompoundTag tag);
}
