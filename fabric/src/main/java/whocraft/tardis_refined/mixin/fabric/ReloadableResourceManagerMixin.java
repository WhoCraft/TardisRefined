package whocraft.tardis_refined.mixin.fabric;

import whocraft.tardis_refined.client.model.pallidium.ModelLayerManager;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.resources.PreparableReloadListener;
import net.minecraft.server.packs.resources.ReloadableResourceManager;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(ReloadableResourceManager.class)
public class ReloadableResourceManagerMixin {

    @Shadow
    @Final
    private PackType type;

    @Shadow
    @Final
    private List<PreparableReloadListener> listeners;

    @Inject(at = @At("RETURN"), method = "registerReloadListener")
    private void registerReloadListener(PreparableReloadListener listener, CallbackInfo callbackInfo) {
        if (this.type == PackType.CLIENT_RESOURCES && listener instanceof BlockEntityRenderDispatcher) {
            // Layer definitions must be applied after EntityModelSet loads its
            // vanilla roots, but before block entity renderers bake their layers.
            this.listeners.add(this.listeners.size() - 1, new ModelLayerManager());
        }
    }

}
