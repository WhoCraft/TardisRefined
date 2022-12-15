package whocraft.tardis_refined.mixin.fabric;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import whocraft.tardis_refined.common.capability.TardisLevelOperator;
import whocraft.tardis_refined.registry.DimensionTypes;

@Mixin(BlockItem.class)
public abstract class BlockItemMixin {


    @Inject(at = @At("HEAD"), method = "place(Lnet/minecraft/world/item/context/BlockPlaceContext;)Lnet/minecraft/world/InteractionResult;", cancellable = true)
    private void place(BlockPlaceContext context, CallbackInfoReturnable<InteractionResult> cir) {

        Level level = context.getLevel();
        if (level.dimensionTypeId() == DimensionTypes.TARDIS && level instanceof ServerLevel serverLevel) {
            TardisLevelOperator data = TardisLevelOperator.get(serverLevel).get();
            if (data.getInteriorManager().isInAirlock(context.getPlayer())) {
                cir.setReturnValue(InteractionResult.FAIL);
            }
        }
    }

}
