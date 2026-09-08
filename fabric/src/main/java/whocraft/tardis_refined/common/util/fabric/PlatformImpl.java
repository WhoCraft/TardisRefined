package whocraft.tardis_refined.common.util.fabric;

import com.mojang.serialization.DataResult;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;
import net.fabricmc.loader.api.Version;
import net.fabricmc.loader.api.VersionParsingException;
import net.fabricmc.loader.api.metadata.ModMetadata;
import net.minecraft.client.Minecraft;
import net.minecraft.server.MinecraftServer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import whocraft.tardis_refined.common.util.Platform;

import java.util.Collection;

public class PlatformImpl {

    public static MinecraftServer MINECRAFT_SERVER;


    public static boolean isProduction() {
        return !FabricLoader.getInstance().isDevelopmentEnvironment();
    }

    public static DataResult<Platform.CommonVersion> parseVersion(String version) {
        try {
            return DataResult.success(new FabricVersion(Version.parse(version)));
        } catch (VersionParsingException e) {
            return DataResult.error(e::getMessage);
        }
    }

    public static boolean isModLoaded(String id, @Nullable Platform.CommonVersionRange range) {
        if (FabricLoader.getInstance().isModLoaded(id)) {
            if (range != null) {
                var container = FabricLoader.getInstance().getModContainer(id);
                if (container.isEmpty()) return false;
                var version = new FabricVersion(container.get().getMetadata().getVersion());
                return range.contains(version);
            }
            return true;
        } else {
            return false;
        }
    }

    public static Collection<String> getModIds() {
        return FabricLoader.getInstance().getAllMods().stream().map(ModContainer::getMetadata).map(ModMetadata::getId).toList();
    }

    public static boolean isClient() {
        return FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT;
    }

    public static boolean isServer() {
        return FabricLoader.getInstance().getEnvironmentType() == EnvType.SERVER;
    }

    public static MinecraftServer getServer() {
        MinecraftServer server;
        if (Platform.isClient()) {
            server = getServerFromClient();
        } else {
            server = MINECRAFT_SERVER;
        }
        return server;
    }

    public static void init() {
        ServerLifecycleEvents.SERVER_STARTING.register(server -> MINECRAFT_SERVER = server);
        ServerLifecycleEvents.SERVER_STARTED.register(server -> MINECRAFT_SERVER = server);
    }

    @Environment(EnvType.CLIENT)
    private static MinecraftServer getServerFromClient() {
        return Minecraft.getInstance().getSingleplayerServer();
    }

    public static boolean isForge() {
        return false;
    }

    public static String getModName(String namespace) {
        return FabricLoader.getInstance().getModContainer(namespace)
                .map(modContainer -> modContainer.getMetadata().getName())
                .orElse(namespace);
    }

    public record FabricVersion(Version version) implements Platform.CommonVersion {

        @Override
        public int compareTo(@NotNull Platform.CommonVersion commonVersion) {
            if (commonVersion instanceof FabricVersion otherVersion) {
                return version.compareTo(otherVersion.version);
            } else {
                throw new IllegalArgumentException("Tried to compare mismatching version formats");
            }
        }

        @Override
        public String versionString() {
            return version.getFriendlyString();
        }
    }
}
