package whocraft.tardis_refined.mixins;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Matrix4f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.CubeMap;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CubeMap.class)
public class CubeMapMixin {


    @Inject(method = "render(Lnet/minecraft/client/Minecraft;FFF)V", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/systems/RenderSystem;setProjectionMatrix(Lcom/mojang/math/Matrix4f;)V", shift = At.Shift.AFTER))
    private void inputEdit(CallbackInfo ci) {

    }

}
