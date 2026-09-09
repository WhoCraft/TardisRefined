package whocraft.tardis_refined.mixin.render;

import org.joml.Matrix4f;
import org.joml.Matrix4fStack;
import org.spongepowered.asm.mixin.Intrinsic;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import whocraft.tardis_refined.client.MatrixExtension;
import whocraft.tardis_refined.client.renderer.RenderHelper;

@Mixin(Matrix4fStack.class)
public abstract class MatrixMixin extends Matrix4f implements MatrixExtension {

    @Unique
    private boolean tardis_refined$updateProjectionZOffset = false;

    @Intrinsic(displace = true)
    public Matrix4f translation(float x, float y, float z) {
        if (tardis_refined$updateProjectionZOffset) {
            RenderHelper.currentProjectionZOffset = -z;
        }
        return super.translation(x, y, z);
    }

    @Override
    public void tardis_refined$setUpdateZOffset(boolean updateOffset) {
        this.tardis_refined$updateProjectionZOffset = updateOffset;
    }
}
