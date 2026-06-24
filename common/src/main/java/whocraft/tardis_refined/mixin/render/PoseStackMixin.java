package whocraft.tardis_refined.mixin.render;

import com.mojang.blaze3d.vertex.PoseStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import whocraft.tardis_refined.client.PoseStackExtension;
import whocraft.tardis_refined.client.renderer.RenderHelper;

@Mixin(PoseStack.class)
public class PoseStackMixin implements PoseStackExtension {

    @Unique
    private boolean tardis_refined$updateProjectionZOffset = false;

    @Inject(method = "translate(FFF)V", at = @At("HEAD"))
    public void translate(float x, float y, float z, CallbackInfo ci) {
        if (tardis_refined$updateProjectionZOffset) {
            RenderHelper.currentProjectionZOffset = -z;
        }
    }

    @Override
    public void tardis_refined$setUpdateZOffset(boolean updateOffset) {
        this.tardis_refined$updateProjectionZOffset = updateOffset;
    }
}
