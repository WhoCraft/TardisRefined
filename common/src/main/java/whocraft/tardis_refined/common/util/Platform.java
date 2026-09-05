package whocraft.tardis_refined.common.util;

import com.mojang.serialization.DataResult;
import dev.architectury.injectables.annotations.ExpectPlatform;
import dev.architectury.injectables.annotations.PlatformOnly;
import dev.architectury.injectables.targets.ArchitecturyTarget;
import net.minecraft.server.MinecraftServer;
import org.jetbrains.annotations.Nullable;
import whocraft.tardis_refined.TardisRefined;

import java.util.Collection;

public class Platform {

    private static final boolean FORGE = ArchitecturyTarget.getCurrentTarget().equals(PlatformOnly.FORGE);

    @ExpectPlatform
    public static boolean isProduction() {
        throw new AssertionError();
    }

    public static boolean isModLoaded(String id) {
        return isModLoaded(id, null);
    }

    @ExpectPlatform
    public static DataResult<CommonVersion> parseVersion(String version) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static boolean isModLoaded(String id, @Nullable Platform.CommonVersionRange range) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static Collection<String> getModIds() {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static boolean isClient() {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static boolean isServer() {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static boolean isForge() {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static MinecraftServer getServer() {
        throw new AssertionError();
    }


    @ExpectPlatform
    public static String getModName(String namespace) {
        throw new AssertionError();
    }

    public interface CommonVersion extends Comparable<CommonVersion> {
        String versionString();
    }

    public record CommonVersionRange(CommonVersion minVersion, boolean minInclusive, CommonVersion maxVersion, boolean maxInclusive) {
        public boolean contains(CommonVersion version) {
            if (minVersion != null) {
                int comp = version.compareTo(minVersion);
                if (comp < 0) {
                    return false;
                }
                if (!minInclusive && comp == 0) {
                    return false;
                }
            }
            if (maxVersion != null) {
                int comp = version.compareTo(maxVersion);
                if (comp > 0) {
                    return false;
                }
                if (!maxInclusive && comp == 0) {
                    return false;
                }
            }
            return true;
        }

        public static class Builder {

            private CommonVersion minVersion;
            private boolean minInclusive = true;
            private CommonVersion maxVersion;
            private boolean maxInclusive = true;

            public static Builder builder() {
                return new Builder();
            }

            public Builder atLeast(CommonVersion minVersion) {
                this.minVersion = minVersion;
                return this;
            }

            public Builder atLeast(String minVersion) {
                var parsed = parseVersion(minVersion).get();
                parsed.left().ifPresent(this::atLeast);
                parsed.right().ifPresent(err -> TardisRefined.LOGGER.error(err));
                return this;
            }

            public Builder atMost(CommonVersion maxVersion) {
                this.maxVersion = maxVersion;
                return this;
            }

            public Builder atMost(String maxVersion) {
                var parsed = parseVersion(maxVersion).get();
                parsed.left().ifPresent(this::atMost);
                parsed.right().ifPresent(err -> TardisRefined.LOGGER.error(err));
                return this;
            }

            public Builder minExclusive() {
                minInclusive = false;
                return this;
            }

            public Builder maxExclusive() {
                maxInclusive = false;
                return this;
            }

            public CommonVersionRange build() {
                return new CommonVersionRange(minVersion, minInclusive, maxVersion, maxInclusive);
            }
        }
    }

}