package whocraft.tardis_refined.mixin.fabric;

import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import whocraft.tardis_refined.common.util.MiscHelper;

@Mixin(BlockPlaceContext.class)
public class BlockItemMixin {


    @Inject(at = @At("HEAD"), method = "canPlace()Z", cancellable = true)
    private void place(CallbackInfoReturnable<Boolean> cir) {
        BlockPlaceContext blockPlaceContext = (BlockPlaceContext) (Object) this;

        Level level = blockPlaceContext.getLevel();
        if (MiscHelper.shouldStopItem(level, blockPlaceContext.getPlayer(), blockPlaceContext.getClickedPos())) {
            level.removeBlock(blockPlaceContext.getClickedPos(), true);
            cir.setReturnValue(false);
        }
    }

}
