package whocraft.tardis_refined.mixin;

import net.minecraft.world.level.dimension.end.EnderDragonFight;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(EnderDragonFight.class)
public interface EndDragonFightAccessor {

    @Accessor("dragonKilled")
    public boolean isDragonKilled();

}
