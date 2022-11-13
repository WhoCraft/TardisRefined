package whocraft.tardis_refined.common.capability;

import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;

import java.util.Optional;

public class TardisLevelOperator {

    //private ServerLevel level;

    public TardisLevelOperator(ServerLevel level) {
        //this.level = level;
    }

    @ExpectPlatform
    public static Optional<TardisLevelOperator> get(ServerLevel level) {
        throw new AssertionError();
    }

    public CompoundTag serializeNBT() {
        CompoundTag compoundTag = new CompoundTag();
        return compoundTag;
    }

    public void deserializeNBT(CompoundTag tag) {

    }

    public void tick(Level level) {
        System.out.println("Tick!!!");
    }
}
