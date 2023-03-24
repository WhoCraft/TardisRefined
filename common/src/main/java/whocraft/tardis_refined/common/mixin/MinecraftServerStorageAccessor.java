package whocraft.tardis_refined.common.mixin;

import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.storage.LevelStorageSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(MinecraftServer.class)
public interface MinecraftServerStorageAccessor {

    @Accessor("storageSource")
    LevelStorageSource.LevelStorageAccess getStorageSource();
}