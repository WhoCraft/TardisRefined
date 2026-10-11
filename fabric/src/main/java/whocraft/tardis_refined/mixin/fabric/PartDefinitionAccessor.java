package whocraft.tardis_refined.mixin.fabric;

import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.List;

@Mixin(PartDefinition.class)
public interface PartDefinitionAccessor {
    @Accessor("partPose") PartPose tr$getPartPose();
    @Accessor("cubes") List<CubeDefinition> tr$getCubes();
}
