package whocraft.tardis_refined.mixin.fabric;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.context.UseOnContext;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import whocraft.tardis_refined.common.util.MiscHelper;

@Mixin(BlockItem.class)
public class BlockItemMixin {

    @Inject(method = "useOn(Lnet/minecraft/world/item/context/UseOnContext;)Lnet/minecraft/world/InteractionResult;", at = @At("HEAD"), cancellable = true)
    private void restrict(UseOnContext useOnContext, CallbackInfoReturnable<InteractionResult> cir) {
        if (MiscHelper.shouldStopItem(useOnContext.getLevel(), useOnContext.getPlayer(), useOnContext.getClickedPos(), useOnContext.getItemInHand())) {
            if (useOnContext.getPlayer() instanceof ServerPlayer serverPlayer) {
                serverPlayer.inventoryMenu.sendAllDataToRemote();
            }
            cir.setReturnValue(InteractionResult.PASS);
        }
    }


}
