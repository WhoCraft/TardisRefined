package whocraft.tardis_refined.mixin.forge;

import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.neoforge.client.ClientHooks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ClientHooks.class)
public class ClientHooksMixin {

    @Inject(method = "isBlockEntityRendererVisible(Lnet/minecraft/client/renderer/blockentity/BlockEntityRenderDispatcher;Lnet/minecraft/world/level/block/entity/BlockEntity;Lnet/minecraft/client/renderer/culling/Frustum;)Z", at = @At("HEAD"), cancellable = true)
    private static void getRenderBoundingBox(BlockEntityRenderDispatcher dispatcher, BlockEntity blockEntity, Frustum frustum, CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(blockEntity.getClass().getPackageName().contains("whocraft.tardis_refined"));
    }

}
