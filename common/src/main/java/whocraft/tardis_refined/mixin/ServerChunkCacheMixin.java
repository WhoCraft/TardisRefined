package whocraft.tardis_refined.mixin;

import net.minecraft.server.level.ServerChunkCache;
import net.minecraft.server.level.ServerLevel;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import whocraft.tardis_refined.common.dimension.DimensionHandler;

import java.io.IOException;

@Mixin(ServerChunkCache.class)
public class ServerChunkCacheMixin {

    @Shadow
    @Final
    ServerLevel level;

    @Inject(
            method = "save",
            at = @At("HEAD"),
            cancellable = true
    )
    public void save(boolean flush, CallbackInfo ci) throws IOException {
        if (DimensionHandler.isDimensionBeingDeleted(level.dimension())) {
            ci.cancel();
        }
    }
    
}
