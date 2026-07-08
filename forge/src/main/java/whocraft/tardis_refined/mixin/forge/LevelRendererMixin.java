package whocraft.tardis_refined.mixin.forge;

import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Camera;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.LightTexture;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import whocraft.tardis_refined.client.ModelRegistry;
import whocraft.tardis_refined.client.TardisClientData;
import whocraft.tardis_refined.client.renderer.vortex.RenderTargetHelper;

@Mixin(LevelRenderer.class)
public class LevelRendererMixin {

    @Inject(
            method = "renderLevel",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/renderer/LevelRenderer;renderDebug(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;Lnet/minecraft/client/Camera;)V"
            ),
            remap = false
    )
    public void onRenderLast(
            DeltaTracker arg, boolean bl, Camera camera, GameRenderer arg3, LightTexture arg4, Matrix4f matrix4f,
            Matrix4f matrix4f2, CallbackInfo ci, @Local PoseStack poseStack
    ) {
        var mc = Minecraft.getInstance();
        ClientLevel world = mc.level;
        if (world == null) return;
        TardisClientData tardisClientData = TardisClientData.getInstance(world.dimension());
        poseStack.pushPose();
        RenderTargetHelper.renderZeitonGlass(
                camera,
                ModelRegistry.getZeitonGlassModel(),
                poseStack,
                mc.renderBuffers().bufferSource(),
                LightTexture.FULL_BLOCK,
                tardisClientData,
                true
        );
        poseStack.popPose();
    }

}
