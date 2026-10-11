package whocraft.tardis_refined.mixin.render;

import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.fog.FogData;
import net.minecraft.client.renderer.fog.FogRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import org.joml.Vector4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import whocraft.tardis_refined.client.TardisClientLogic;
import whocraft.tardis_refined.common.util.TardisHelper;
import whocraft.tardis_refined.registry.TRDimensionTypes;

@Mixin(FogRenderer.class)
public class FogRendererMixin {
    @Inject(method = "setupFog(Lnet/minecraft/client/Camera;ILnet/minecraft/client/DeltaTracker;FLnet/minecraft/client/multiplayer/ClientLevel;)Lnet/minecraft/client/renderer/fog/FogData;", at = @At("RETURN"))
    private void tardisRefined$modifyFog(Camera camera, int renderDistance, DeltaTracker deltaTracker,
                                         float partialTick, ClientLevel level,
                                         CallbackInfoReturnable<FogData> callback) {
        Minecraft minecraft = Minecraft.getInstance();
        if (minecraft.player == null || level == null || !level.dimensionTypeRegistration().is(TRDimensionTypes.TARDIS)) return;
        BlockPos pos = minecraft.player.blockPosition();
        FogData fog = callback.getReturnValue();
        float transition = TardisClientLogic.getFogTickDelta(pos);
        if (transition > 0.0f) {
            fog.color = new Vector4f(0, 0, 0, 1);
            fog.environmentalStart = Mth.lerp(transition, 16f, -8f);
            fog.environmentalEnd = 16f;
            return;
        }
        if (TardisHelper.isInArsArea(pos)) {
            var tardisData = whocraft.tardis_refined.client.TardisClientData.getInstance(level.dimension());
            Vec3 color = tardisData.fogColor(tardisData.isCrashing());
            fog.color = new Vector4f((float) color.x, (float) color.y, (float) color.z, 1f);
            fog.environmentalStart = -8f;
            fog.environmentalEnd = 30f;
        }
    }
}
