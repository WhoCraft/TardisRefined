package whocraft.tardis_refined.mixin.fabric;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.GameRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import whocraft.tardis_refined.client.PoseStackExtension;

@Mixin(GameRenderer.class)
public class GameRendererMixin {

    @WrapOperation(
            method = "render",
            at = @At(
                    value = "INVOKE",
                    target = "Lcom/mojang/blaze3d/vertex/PoseStack;translate(FFF)V"
            )

    )
    public void render(PoseStack instance, float x, float y, float z, Operation<Void> original) {
        // We can't take z from here because some mods like Journeymap set it manually with a redirect, so we catch it inside the PoseStack instead.
        ((PoseStackExtension) instance).tardis_refined$setUpdateZOffset(true);
        original.call(instance, x, y, z);
        ((PoseStackExtension) instance).tardis_refined$setUpdateZOffset(false);
    }

}
