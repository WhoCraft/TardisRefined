package whocraft.tardis_refined.mixin.fabric;

import net.minecraft.client.model.geom.builders.CubeDeformation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(CubeDeformation.class)
public interface CubeDeformationAccessor {
    @Accessor("growX") float tr$getGrowX();
    @Accessor("growY") float tr$getGrowY();
    @Accessor("growZ") float tr$getGrowZ();
}
