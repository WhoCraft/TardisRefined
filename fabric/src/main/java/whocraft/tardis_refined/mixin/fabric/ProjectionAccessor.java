package whocraft.tardis_refined.mixin.fabric;

import net.minecraft.client.renderer.Projection;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(Projection.class)
public interface ProjectionAccessor {
    @Accessor("matrix")
    Matrix4f tardisRefined$getMatrix();

    @Accessor("matrixVersion")
    long tardisRefined$getMatrixVersion();

    @Accessor("matrixVersion")
    void tardisRefined$setMatrixVersion(long version);
}
