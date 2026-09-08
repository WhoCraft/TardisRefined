package whocraft.tardis_refined.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.network.Connection;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.players.PlayerList;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Slice;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import whocraft.tardis_refined.common.dimension.DimensionHandler;
import whocraft.tardis_refined.constants.ModMessages;

@Mixin(PlayerList.class)
public class PlayerListMixin {
    
    @Inject(
            method = "placeNewPlayer",
            at = @At(
                    value = "INVOKE",
                    target = "Lorg/slf4j/Logger;warn(Ljava/lang/String;Ljava/lang/Object;)V"
            ),
            slice = @Slice(
                    from = @At(
                            value = "INVOKE",
                            target = "Lnet/minecraft/server/MinecraftServer;getLevel(Lnet/minecraft/resources/ResourceKey;)Lnet/minecraft/server/level/ServerLevel;"
                    ),
                    to = @At(
                            value = "INVOKE",
                            target = "Lnet/minecraft/server/MinecraftServer;overworld()Lnet/minecraft/server/level/ServerLevel;"
                    )
            ),
            cancellable = true
    )
    private void kickIfDimensionStillDeleting(
            Connection connection, ServerPlayer serverPlayer, CallbackInfo ci,
            @SuppressWarnings("LocalMayUseName") // We don't want to use local name when code is obfuscated.
            @Local ResourceKey<Level> resourceKey
    ) {
        if (DimensionHandler.isDimensionBeingDeleted(resourceKey)) {
            serverPlayer.connection.disconnect(Component.translatable(ModMessages.DELETED_TARDIS));
            ci.cancel();
        }
    }
}
