package whocraft.tardis_refined.mixin.fabric;

import net.minecraft.client.model.geom.builders.CubeDefinition;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.UVPair;
import org.joml.Vector3fc;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Set;
import net.minecraft.core.Direction;

@Mixin(CubeDefinition.class)
public interface CubeDefinitionAccessor {
    @Accessor("origin") Vector3fc tr$getOrigin();
    @Accessor("dimensions") Vector3fc tr$getDimensions();
    @Accessor("grow") CubeDeformation tr$getGrow();
    @Accessor("mirror") boolean tr$isMirror();
    @Accessor("texCoord") UVPair tr$getTexCoord();
}
