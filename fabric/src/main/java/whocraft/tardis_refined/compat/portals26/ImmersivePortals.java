package whocraft.tardis_refined.compat.portals26;

import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;

import java.util.UUID;

/** 26.3-safe fallback while the optional portal integration is being ported. */
public final class ImmersivePortals {
    private ImmersivePortals() {}

    public static void init() {}
    public static void onServerStopping(MinecraftServer server) {}
    public static boolean doPortalsExistForTardis(UUID id) { return false; }
    public static boolean isShellThemeSupported(Identifier theme) { return false; }
    public static ServerLevel createDimension(Level level, ResourceKey<Level> id) { return null; }
    public static void teleportViaIp(Entity entity, ServerLevel destination, double x, double y, double z) {}
}
