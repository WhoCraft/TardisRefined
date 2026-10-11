package whocraft.tardis_refined.mixin.render.ui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.spectator.SpectatorGui;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import whocraft.tardis_refined.common.capability.player.TardisPlayerInfo;

@Mixin(SpectatorGui.class)
public class SpectatorGuiMixin {

    @Inject(method = "extractHotbar(Lnet/minecraft/client/gui/GuiGraphicsExtractor;)V", at = @At("HEAD"), cancellable = true)
    public void tardisRefined$hideSpectatorHotbar(GuiGraphicsExtractor guiGraphics, CallbackInfo ci) {
        TardisPlayerInfo.get(Minecraft.getInstance().player).ifPresent(tardisPlayerInfo -> {
            if (tardisPlayerInfo.isViewingTardis()) {
                ci.cancel();
            }
        });
    }
}
