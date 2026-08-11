package whocraft.tardis_refined.common.util.forge;

import com.mojang.serialization.DataResult;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.loading.FMLEnvironment;
import net.minecraftforge.fml.loading.FMLLoader;
import net.minecraftforge.forgespi.language.IModInfo;
import net.minecraftforge.server.ServerLifecycleHooks;
import org.apache.maven.artifact.versioning.ArtifactVersion;
import org.apache.maven.artifact.versioning.DefaultArtifactVersion;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import whocraft.tardis_refined.common.util.Platform;

import java.util.Collection;

public class PlatformImpl {

    public static boolean isProduction() {
        return FMLLoader.isProduction();
    }

    public static DataResult<Platform.CommonVersion> parseVersion(String version) {
        return DataResult.success(new ForgeVersion(new DefaultArtifactVersion(version)));
    }

    public static boolean isModLoaded(String id, @Nullable Platform.CommonVersionRange range) {
        if (ModList.get().isLoaded(id)) {
            if (range != null) {
                var modFile = ModList.get().getModContainerById(id);
                if (modFile.isEmpty()) return false;
                var version = modFile.get().getModInfo().getVersion();
                return range.contains(new ForgeVersion(version));
            }
            return true;
        }
        return false;
    }

    public static Collection<String> getModIds() {
        return ModList.get().getMods().stream().map(IModInfo::getModId).toList();
    }

    public static boolean isClient() {
        return FMLEnvironment.dist == Dist.CLIENT;
    }

    public static boolean isServer() {
        return FMLEnvironment.dist == Dist.DEDICATED_SERVER;
    }

    public static MinecraftServer getServer() {
        return ServerLifecycleHooks.getCurrentServer();
    }

    public static boolean isForge() {
        return true;
    }

    public static String getModName(String namespace) {
        return ModList.get().getModContainerById(namespace)
                .map(modContainer -> modContainer.getModInfo().getDisplayName())
                .orElse(namespace);
    }

    public record ForgeVersion(ArtifactVersion version) implements Platform.CommonVersion {

        @Override
        public int compareTo(@NotNull Platform.CommonVersion version) {
            if (version instanceof ForgeVersion otherVersion) {
                return this.version.compareTo(otherVersion.version);
            } else {
                throw new IllegalArgumentException("Tried to compare mismatching version formats");
            }
        }

        @Override
        public String versionString() {
            return version.toString();
        }
    }
}