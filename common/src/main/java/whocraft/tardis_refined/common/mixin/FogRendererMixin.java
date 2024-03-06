package whocraft.tardis_refined.common.mixin;

import com.mojang.blaze3d.shaders.FogShape;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.FogRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import whocraft.tardis_refined.client.TardisClientData;
import whocraft.tardis_refined.common.util.TardisHelper;

@Mixin(FogRenderer.class)
public class FogRendererMixin {

    @Inject(at = @At("TAIL"), method = "setupFog(Lnet/minecraft/client/Camera;Lnet/minecraft/client/renderer/FogRenderer$FogMode;FZF)V")
    private static void setupFog(Camera camera, FogRenderer.FogMode fogMode, float farPlaneDistance, boolean bl, float f, CallbackInfo ci) {
        if (Minecraft.getInstance().player != null) {
            BlockPos blockPosition = Minecraft.getInstance().player.blockPosition();
            if (TardisHelper.isInArsArea(blockPosition)) {
                RenderSystem.setShaderFogStart(-8);
                RenderSystem.setShaderFogEnd(60 * 0.5F);
                RenderSystem.setShaderFogShape(FogShape.SPHERE);
            }
        }
    }

    @Inject(at = @At("HEAD"), cancellable = true, method = "setupFog")
    private static void setupColor(CallbackInfo callbackInfo) {
        if (Minecraft.getInstance().player != null) {
            BlockPos blockPosition = Minecraft.getInstance().player.blockPosition();

            TardisClientData reactions = TardisClientData.getInstance(Minecraft.getInstance().level.dimension());

            if (reactions.getFuel() == 0d) {
                RenderSystem.setShaderFogColor(0, 0, 0, 1); // This sets the fog to a pitch black
                RenderSystem.setShaderFogStart(-8); // This makes the fog really close to the player
                RenderSystem.setShaderFogEnd(16);
                RenderSystem.setShaderFogShape(FogShape.SPHERE);

                callbackInfo.cancel();
            }

            if (TardisHelper.isInArsArea(blockPosition)) {
                Vec3 fogColor = reactions.fogColor(reactions.isCrashing() || reactions.isInDangerZone());
                RenderSystem.setShaderFogColor((float) fogColor.x, (float) fogColor.y, (float) fogColor.z);
                callbackInfo.cancel();
            }
        }
    }


}
