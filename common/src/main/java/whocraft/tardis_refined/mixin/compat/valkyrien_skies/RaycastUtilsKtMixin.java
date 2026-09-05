package whocraft.tardis_refined.mixin.compat.valkyrien_skies;

import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.valkyrienskies.mod.common.world.RaycastUtilsKt;
import whocraft.tardis_refined.common.block.shell.RedirectBlock;

// Psuedo means the mixin fails silently instead of crashing.
// This means we don't need to disable this mixin manually when Valkyrien Skies is missing.
@Pseudo
@Mixin(RaycastUtilsKt.class)
public class RaycastUtilsKtMixin {

    @Inject(
            method = "clip$lambda$0",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/level/Level;getFluidState(Lnet/minecraft/core/BlockPos;)Lnet/minecraft/world/level/material/FluidState;"
            )
    )
    private static void clipRedirect(
            Level $this_clip, Vec3 $realStart, Vec3 $realEnd, ClipContext clipContext, BlockPos orgPos,
            CallbackInfoReturnable<BlockHitResult> cir,
            @Local(argsOnly = true) LocalRef<BlockPos> blockPos, @SuppressWarnings("LocalMayUseName") @Local LocalRef<BlockState> blockState
    ) {
        RedirectBlock.handleClip($this_clip, clipContext, orgPos, blockPos, blockState);
    }

    @Inject(
            method = "vanillaClip$lambda$4",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/level/BlockGetter;getFluidState(Lnet/minecraft/core/BlockPos;)Lnet/minecraft/world/level/material/FluidState;"
            )
    )
    private static void clipRedirect(
            BlockGetter $this_vanillaClip, ClipContext clipContext, BlockPos orgPos,CallbackInfoReturnable<BlockHitResult> cir,
            @Local(argsOnly = true) LocalRef<BlockPos> blockPos, @SuppressWarnings("LocalMayUseName") @Local LocalRef<BlockState> blockState
    ) {
        RedirectBlock.handleClip($this_vanillaClip, clipContext, orgPos, blockPos, blockState);
    }

}
