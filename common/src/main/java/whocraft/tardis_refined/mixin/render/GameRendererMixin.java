package whocraft.tardis_refined.mixin.render;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.client.renderer.GameRenderer;
import org.joml.Matrix4f;
import org.joml.Matrix4fStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import whocraft.tardis_refined.client.MatrixExtension;

@Mixin(GameRenderer.class)
public class GameRendererMixin {

    @WrapOperation(
            method = "render",
            at = @At(
                    value = "INVOKE",
                    target = "Lorg/joml/Matrix4fStack;translation(FFF)Lorg/joml/Matrix4f;"
            )
    )
    public Matrix4f onTranslateFabric(Matrix4fStack instance, float x, float y, float z, Operation<Matrix4f> original) {
        // We can't take z from here because some mods like Journeymap set it manually with a redirect, so we catch it inside the PoseStack instead.
        ((MatrixExtension) instance).tardis_refined$setUpdateZOffset(true);
        var result = original.call(instance, x, y, z);
        ((MatrixExtension) instance).tardis_refined$setUpdateZOffset(false);
        return result;
    }

}
