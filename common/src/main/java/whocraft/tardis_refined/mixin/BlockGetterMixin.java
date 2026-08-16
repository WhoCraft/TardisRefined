package whocraft.tardis_refined.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import whocraft.tardis_refined.common.block.shell.RedirectBlock;

@Mixin(BlockGetter.class)
public interface BlockGetterMixin {

    @Shadow
    BlockState getBlockState(BlockPos pos);

    @Inject(
            method = "method_17743",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/level/BlockGetter;getFluidState(Lnet/minecraft/core/BlockPos;)Lnet/minecraft/world/level/material/FluidState;"
            )
    )
    private void clip(
            ClipContext clipContext, BlockPos orgPos, CallbackInfoReturnable<BlockHitResult> cir,
            @Local(argsOnly = true) LocalRef<BlockPos> blockPos, @SuppressWarnings("LocalMayUseName") @Local LocalRef<BlockState> blockState
    ) {
        if (blockState.get().getBlock() instanceof RedirectBlock redirect) {
            redirect.findSource((BlockGetter) this, orgPos, blockState.get()).ifPresent(source -> {
                blockPos.set(source);
                blockState.set(getBlockState(source));
            });
        }
    }

}
