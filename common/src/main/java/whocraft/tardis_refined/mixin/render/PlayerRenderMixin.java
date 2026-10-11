package whocraft.tardis_refined.mixin.render;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.state.AvatarRenderState;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import whocraft.tardis_refined.common.capability.player.TardisPlayerInfo;

@Mixin(LivingEntityRenderer.class)
public abstract class PlayerRenderMixin {
    @Inject(method = "submit(Lnet/minecraft/client/renderer/entity/state/LivingEntityRenderState;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/SubmitNodeCollector;Lnet/minecraft/client/renderer/state/level/CameraRenderState;)V", at = @At("HEAD"), cancellable = true)
    private void tardisRefined$hideViewedPlayer(LivingEntityRenderState state, PoseStack poseStack,
                                               SubmitNodeCollector collector, CameraRenderState cameraState,
                                               CallbackInfo callback) {
        if (!(state instanceof AvatarRenderState avatarState)) return;
        if (Minecraft.getInstance().level == null) return;
        Entity entity = Minecraft.getInstance().level.getEntity(avatarState.id);
        if (entity instanceof Player player) {
            TardisPlayerInfo.get(player).ifPresent(info -> {
                if (info.isViewingTardis()) callback.cancel();
            });
        }
    }
}
