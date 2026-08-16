package whocraft.tardis_refined.mixin;

import com.llamalad7.mixinextras.expression.Definition;
import com.llamalad7.mixinextras.expression.Expression;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.Share;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;
import net.minecraft.core.BlockPos;
import net.minecraft.network.protocol.game.ServerboundUseItemOnPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import whocraft.tardis_refined.common.block.shell.RedirectBlock;

@Mixin(ServerGamePacketListenerImpl.class)
public class ServerGamePacketListenerImplMixin {

    @Shadow
    public ServerPlayer player;

    @Inject(
            method = "handleUseItemOn",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/phys/Vec3;subtract(Lnet/minecraft/world/phys/Vec3;)Lnet/minecraft/world/phys/Vec3;"
            )
    )
    public void beforeDistChecks(
            ServerboundUseItemOnPacket packet, CallbackInfo ci,
            @Share("isValidRedirect") LocalRef<Boolean> isValidRedirect
    ) {
        BlockPos pos = BlockPos.containing(packet.getHitResult().getLocation());
        var redirectBlock = player.level().getBlockState(pos);
        if (redirectBlock.getBlock() instanceof RedirectBlock redirect) {
            redirect.findSource(player.level(), pos, redirectBlock).ifPresentOrElse(sourcePos -> {
                isValidRedirect.set(sourcePos.equals(packet.getHitResult().getBlockPos()));
            }, () -> {
                isValidRedirect.set(false);
            });
        } else {
            isValidRedirect.set(false);
        }
    }

    @Definition(id = "abs", method = "Ljava/lang/Math;abs(D)D")
    @Definition(id = "vec33", local = @Local(type = Vec3.class, ordinal = 2))
    @Definition(id = "x", method = "Lnet/minecraft/world/phys/Vec3;x()D")
    @Expression("abs(vec33.x()) < 1.0000001")
    @ModifyExpressionValue(
            method = "handleUseItemOn",
            at = @At("MIXINEXTRAS:EXPRESSION")
    )
    public boolean checkXDist(boolean xDistValid, @Share("isValidRedirect") LocalRef<Boolean> isValidRedirect) {
        return xDistValid || isValidRedirect.get();
    }

    @Definition(id = "abs", method = "Ljava/lang/Math;abs(D)D")
    @Definition(id = "vec33", local = @Local(type = Vec3.class, ordinal = 2))
    @Definition(id = "y", method = "Lnet/minecraft/world/phys/Vec3;y()D")
    @Expression("abs(vec33.y()) < 1.0000001")
    @ModifyExpressionValue(
            method = "handleUseItemOn",
            at = @At("MIXINEXTRAS:EXPRESSION")
    )
    public boolean checkYDist(boolean yDistValid, @Share("isValidRedirect") LocalRef<Boolean> isValidRedirect) {
        return yDistValid || isValidRedirect.get();
    }

    @Definition(id = "abs", method = "Ljava/lang/Math;abs(D)D")
    @Definition(id = "vec33", local = @Local(type = Vec3.class, ordinal = 2))
    @Definition(id = "z", method = "Lnet/minecraft/world/phys/Vec3;z()D")
    @Expression("abs(vec33.z()) < 1.0000001")
    @ModifyExpressionValue(
            method = "handleUseItemOn",
            at = @At("MIXINEXTRAS:EXPRESSION")
    )
    public boolean checkZDist(boolean zDistValid, @Share("isValidRedirect") LocalRef<Boolean> isValidRedirect) {
        return zDistValid || isValidRedirect.get();
    }

}
