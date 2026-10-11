package whocraft.tardis_refined.mixin.render.ui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.Hud;
import net.minecraft.resources.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import whocraft.tardis_refined.common.capability.player.TardisPlayerInfo;

@Mixin(Hud.class)
public class GuiMixin {

    @Inject(method = "extractHotbarAndDecorations(Lnet/minecraft/client/gui/GuiGraphicsExtractor;Lnet/minecraft/client/DeltaTracker;)V", at = @At("HEAD"), cancellable = true)
    private void tardisRefined$hideHotbar(GuiGraphicsExtractor graphics, DeltaTracker deltaTracker, CallbackInfo ci) {
        tardisRefined$cancelWhileViewing(ci);
    }

    @Inject(method = "extractCrosshair(Lnet/minecraft/client/gui/GuiGraphicsExtractor;Lnet/minecraft/client/DeltaTracker;)V", at = @At("HEAD"), cancellable = true)
    private void tardisRefined$hideCrosshair(GuiGraphicsExtractor graphics, DeltaTracker deltaTracker, CallbackInfo ci) {
        tardisRefined$cancelWhileViewing(ci);
    }

    @Inject(method = "extractSpyglassOverlay(Lnet/minecraft/client/gui/GuiGraphicsExtractor;F)V", at = @At("HEAD"), cancellable = true)
    private void tardisRefined$hideSpyglassOverlay(GuiGraphicsExtractor graphics, float partialTick, CallbackInfo ci) {
        tardisRefined$cancelWhileViewing(ci);
    }

    @Inject(method = "extractTextureOverlay(Lnet/minecraft/client/gui/GuiGraphicsExtractor;Lnet/minecraft/resources/Identifier;F)V", at = @At("HEAD"), cancellable = true)
    private void tardisRefined$hideTextureOverlay(GuiGraphicsExtractor graphics, Identifier texture, float alpha, CallbackInfo ci) {
        tardisRefined$cancelWhileViewing(ci);
    }

    @Inject(method = "extractPlayerHealth(Lnet/minecraft/client/gui/GuiGraphicsExtractor;)V", at = @At("HEAD"), cancellable = true)
    private void tardisRefined$hidePlayerHealth(GuiGraphicsExtractor graphics, CallbackInfo ci) {
        tardisRefined$cancelWhileViewing(ci);
    }

    @Unique
    private static void tardisRefined$cancelWhileViewing(CallbackInfo ci) {
        if (Minecraft.getInstance().player == null) return;
        TardisPlayerInfo.get(Minecraft.getInstance().player).ifPresent(info -> {
            if (info.isViewingTardis()) ci.cancel();
        });
    }
}
