package whocraft.tardis_refined.mixin.fabric;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import org.apache.commons.compress.utils.Lists;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import whocraft.tardis_refined.common.blockentity.shell.RootedShellBlockEntity;


@Mixin(MinecraftServer.class)
public class MinecraftServerMixin {
    @ModifyExpressionValue(method = "tickChildren(Ljava/util/function/BooleanSupplier;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/MinecraftServer;getAllLevels()Ljava/lang/Iterable;"))
    public Iterable<ServerLevel> getAllLevels(Iterable<ServerLevel> original) {
        if (RootedShellBlockEntity.setUpOnNextTick) {
            // if we are going to modify the levels map in this tick, make sure we iterate over a copy of it instead.
            RootedShellBlockEntity.setUpOnNextTick = false;
            return Lists.newArrayList(original.iterator());
        }
        return original;
    }
}
