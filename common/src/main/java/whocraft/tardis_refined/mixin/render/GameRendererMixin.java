package whocraft.tardis_refined.mixin.render;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.client.renderer.GameRenderer;
import org.joml.Matrix4f;
import org.joml.Matrix4fStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import whocraft.tardis_refined.client.MatrixExtension;
import whocraft.tardis_refined.client.renderer.RenderHelper;

@Mixin(value = GameRenderer.class, priority = 950)
public class GameRendererMixin {

    @WrapOperation(
            method = "render",
            at = @At(
                    value = "INVOKE",
                    target = "Lorg/joml/Matrix4fStack;translation(FFF)Lorg/joml/Matrix4f;"
            )
    )
    public Matrix4f onTranslate(Matrix4fStack instance, float x, float y, float z, Operation<Matrix4f> original) {
        // Try to catch inside in case mod wraps with redirect.
        if (instance instanceof MatrixExtension) {
            ((MatrixExtension) instance).tardis_refined$setUpdateZOffset(true);
            var result = original.call(instance, x, y, z);
            ((MatrixExtension) instance).tardis_refined$setUpdateZOffset(false);
            return result;
        } else { // Fallback for NeoForge.
            RenderHelper.currentProjectionZOffset = -z;
            return original.call(instance, x, y, z);
        }
    }

}
