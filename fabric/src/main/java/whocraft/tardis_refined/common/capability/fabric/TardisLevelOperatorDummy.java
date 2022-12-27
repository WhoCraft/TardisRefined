package whocraft.tardis_refined.common.capability.fabric;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Level;

public class TardisLevelOperatorDummy extends TardisLevelOperatorImpl{

    public TardisLevelOperatorDummy(Level level) {
        super(level);
    }

    @Override
    public void writeToNbt(CompoundTag tag) {

    }

    @Override
    public void readFromNbt(CompoundTag tag) {

    }


}
