package whocraft.tardis_refined.mixin.forge;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import whocraft.tardis_refined.client.TardisClientData;

@Mixin(ClientLevel.class)
public class ClientLevelMixin {

    @Inject(method = "tickEntities", at = @At("HEAD"))
    private void startWorldTick(CallbackInfo ci) {
        TardisClientData.getAllEntries().forEach((levelResourceKey, tardisClientData) -> tardisClientData.tickClientside());

        if (Minecraft.getInstance().level == null) {
            TardisClientData.clearAll();
        }
    }

}
