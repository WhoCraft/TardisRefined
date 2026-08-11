package whocraft.tardis_refined.common.util;

import com.mojang.authlib.GameProfile;
import com.mojang.serialization.Dynamic;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.*;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.datafix.DataFixTypes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.storage.LevelResource;
import net.minecraft.world.level.storage.PlayerDataStorage;
import net.minecraft.world.phys.Vec3;
import whocraft.tardis_refined.TardisRefined;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.SignStyle;
import java.time.temporal.ChronoField;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class DisconnectedPlayerHelper {

    static final DateTimeFormatter FORMATTER = new DateTimeFormatterBuilder()
            .appendValue(ChronoField.YEAR, 4, 10, SignStyle.EXCEEDS_PAD)
            .appendLiteral('-')
            .appendValue(ChronoField.MONTH_OF_YEAR, 2)
            .appendLiteral('-')
            .appendValue(ChronoField.DAY_OF_MONTH, 2)
            .appendLiteral('_')
            .appendValue(ChronoField.HOUR_OF_DAY, 2)
            .appendLiteral('-')
            .appendValue(ChronoField.MINUTE_OF_HOUR, 2)
            .appendLiteral('-')
            .appendValue(ChronoField.SECOND_OF_MINUTE, 2)
            .toFormatter(Locale.ROOT);

    private static GameProfile getProfile(UUID id, MinecraftServer server) {
        return Optional.ofNullable(server.getProfileCache()).flatMap(cache -> cache.get(id)).orElse(
                new GameProfile(id, "missingno")
        );
    }

    public static Optional<CompoundTag> getPlayerData(MinecraftServer server, UUID uuid) {
        var profile = getProfile(uuid, server);
        return loadPlayerData(profile, server.playerDataStorage, false);
    }

    /**
     * Allows direct modification of the player data for a specific player.
     * Can be used to modify players while they are offline.
     * @param server The server to run on.
     * @param uuid The UUID of the player to modify.
     * @param playerAction The modification to run. Return true to save the player data, false to skip saving (and thus not change anything).
     */
    public static void forDisconnectedPlayer(
            MinecraftServer server, UUID uuid,
            Predicate<CompoundTag> playerAction
    ) {
        var profile = getProfile(uuid, server);
        var nbt = loadPlayerData(profile, server.playerDataStorage, true).orElse(null);
        if (nbt != null) {
            if (playerAction.test(nbt)) {
                savePlayerData(profile, nbt, server.playerDataStorage);
            }
        }
    }

    /**
     * Runs code for all players who are not connected to the server at that point.
     * @param server The server to load and create players for.
     * @param isPlayerOnline A predicate telling if the player is online or not.
     * @param playerAction What to do with the player. It's a predicate to allow you to choose weather or not to save. Return true to save the player data, false to not save.
     */
    protected static void forAllDisconnectedPlayers(
            MinecraftServer server, Predicate<UUID> isPlayerOnline, Predicate<CompoundTag> playerAction
    ) {
        var playerDir = server.storageSource.getLevelPath(LevelResource.PLAYER_DATA_DIR).toFile();
        String[] ids = Optional.ofNullable(playerDir.list()).map(
                playerFiles -> Arrays.stream(playerFiles).filter(id -> id.endsWith(".dat")).map(
                        id -> id.substring(0, id.length()-4)
                ).toArray(String[]::new)
        ).orElse(new String[0]);
        for (String playerId : ids) {
            try {
                UUID uuid = UUID.fromString(playerId);

                if (isPlayerOnline.test(uuid)) continue;

                forDisconnectedPlayer(server, uuid, playerAction);
            } catch (IllegalArgumentException e) {
                TardisRefined.LOGGER.error("Found invalid player data file {} in player data folder.", playerId);
            }
        }

        // Also update the singleplayer data if present.
        var loadedSingleplayer = server.getWorldData().getLoadedPlayerTag();
        if (loadedSingleplayer != null) {
            playerAction.test(loadedSingleplayer);
        }
        // This is just an extra safeguard if this runs after shutdown.
        var singleplayerProfile = server.getSingleplayerProfile();
        if (singleplayerProfile != null && !isPlayerOnline.test(singleplayerProfile.getId())) {
            loadedSingleplayer = server.getPlayerList().getSingleplayerData();
            if (loadedSingleplayer != null) {
                playerAction.test(loadedSingleplayer);
            }
        }
    }

    private static void savePlayerData(GameProfile player, CompoundTag nbt, PlayerDataStorage handler) {
        try {
            NbtUtils.addCurrentDataVersion(nbt);
            var uuid = player.getId().toString();
            Path path = handler.playerDir.toPath();
            Path tmp = Files.createTempFile(path, uuid + "-", ".dat");
            NbtIo.writeCompressed(nbt, tmp.toFile());
            Path data = path.resolve(uuid + ".dat");
            Path old = path.resolve(uuid + ".dat_old");
            Util.safeReplaceFile(data, tmp, old);
        } catch (Exception var7) {
            TardisRefined.LOGGER.warn("Failed to save player data for {}", player.getName());
        }
    }

    private static Optional<CompoundTag> loadPlayerData(GameProfile player, String extension, PlayerDataStorage handler) {
        File path = handler.playerDir;
        String uuid = player.getId().toString();
        File file = new File(path, uuid + extension);
        if (file.exists() && file.isFile()) {
            try {
                return Optional.of(NbtIo.readCompressed(file));
            } catch (Exception var5) {
                TardisRefined.LOGGER.warn("Failed to load player data for {}", player.getName());
            }
        }

        return Optional.empty();
    }

    private static void backupCorruptedPlayerData(GameProfile player, String extension, PlayerDataStorage handler) {
        Path path = handler.playerDir.toPath();
        String uuid = player.getId().toString();
        Path data = path.resolve(uuid + extension);
        Path corrupted = path.resolve(uuid + "_corrupted_" + LocalDateTime.now().format(FORMATTER) + extension);
        if (Files.isRegularFile(data)) {
            try {
                Files.copy(data, corrupted, StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.COPY_ATTRIBUTES);
            } catch (Exception exception) {
                TardisRefined.LOGGER.warn("Failed to copy the player.dat file for {}", player.getName(), exception);
            }

        }
    }

    private static Optional<CompoundTag> loadPlayerData(GameProfile player, PlayerDataStorage handler, boolean doBackups) {
        Optional<CompoundTag> optional = loadPlayerData(player, ".dat", handler);
        if (optional.isEmpty() && doBackups) {
            backupCorruptedPlayerData(player, ".dat", handler);
        }

        return optional.or(() -> loadPlayerData(player, ".dat_old", handler)).map((nbt) -> {
            int i = NbtUtils.getDataVersion(nbt, -1);
            nbt = DataFixTypes.PLAYER.updateToCurrentVersion(handler.fixerUpper, nbt, i);
            return nbt;
        });
    }

    /**
     * Runs code for all players who are not connected to the server at that point.
     * Use caution, certain things might crash the game, these are not real player entities in the world, just abstract ones for easier representation!
     * @param server The server to get players from.
     * @param playerAction What to do with the player. It's a predicate to allow you to choose weather or not to save. Return true to save the player data, false to not save.
     */
    public static void forAllDisconnectedPlayers(MinecraftServer server, Predicate<CompoundTag> playerAction) {
        forAllDisconnectedPlayers(server, uuid -> {
            for (ServerPlayer onlinePlayer : server.getPlayerList().getPlayers()) {
                if (onlinePlayer.getUUID().equals(uuid)) return true;
            }
            return false;
        }, playerAction);
    }

    public static ResourceKey<Level> getPlayerDimension(CompoundTag nbt) {
        return DimensionType.parseLegacy(new Dynamic<>(NbtOps.INSTANCE, nbt.get("Dimension"))).resultOrPartial(err -> {}).orElse(Level.OVERWORLD);
    }

    public static ResourceKey<Level> getRespawnDimension(CompoundTag nbt) {
        if (nbt.contains("SpawnDimension")) {
            return Level.RESOURCE_KEY_CODEC.parse(
                    NbtOps.INSTANCE, nbt.get("SpawnDimension")
            ).resultOrPartial(TardisRefined.LOGGER::error).orElse(Level.OVERWORLD);
        }
        return Level.OVERWORLD;
    }

    public static BlockPos getRespawnPosition(CompoundTag nbt) {
        return new BlockPos(nbt.getInt("SpawnX"), nbt.getInt("SpawnY"), nbt.getInt("SpawnZ"));
    }

    public static boolean isRespawnForced(CompoundTag nbt) {
        return nbt.getBoolean("SpawnForced");
    }

    public static float getRespawnAngle(CompoundTag nbt) {
        return nbt.getFloat("SpawnAngle");
    }

    public static void setPlayerDimension(CompoundTag nbt, ResourceKey<Level> dim) {
        nbt.putString("Dimension", dim.location().toString());
    }

    public static void modifyPassengersAndRootVehicle(CompoundTag player, Consumer<CompoundTag> nbtModifier) {
        nbtModifier.accept(player);
        if (player.contains("RootVehicle")) {
            var entity = player.getCompound("RootVehicle").getCompound("Entity");
            modifyPassengersAndRootVehicle(entity, nbtModifier);
        }
        if (player.contains(Player.PASSENGERS_TAG)) {
            var list = player.getList(Player.PASSENGERS_TAG, Tag.TAG_COMPOUND);
            for (var e : list) {
                if (e instanceof CompoundTag passenger) {
                    modifyPassengersAndRootVehicle(passenger, nbtModifier);
                }
            }
        }
    }

    public static void deleteVehicleAndPassengers(CompoundTag player) {
        player.remove("RootVehicle");
        player.remove(Player.PASSENGERS_TAG);
    }

    public static void setHealth(CompoundTag player, float health) {
        player.putFloat("Health", health);
    }

    public static void kill(CompoundTag player) {
        setHealth(player, 0);
    }

    private static ListTag toNbtList(double... values) {
        ListTag nbtList = new ListTag();

        for(double d : values) {
            nbtList.add(DoubleTag.valueOf(d));
        }

        return nbtList;
    }

    private static ListTag toNbtList(float... values) {
        ListTag nbtList = new ListTag();

        for(float f : values) {
            nbtList.add(FloatTag.valueOf(f));
        }

        return nbtList;
    }

    public static void setPosition(CompoundTag nbt, Vec3 pos) {
        nbt.put("Pos", toNbtList(pos.x, pos.y, pos.z));
    }

    public static void setRotation(CompoundTag nbt, float yaw, float pitch) {
        nbt.put("Rotation", toNbtList(yaw, pitch));
    }

    public static void moveToSpawn(CompoundTag playerData, MinecraftServer server, boolean isShutdown) {
        BlockPos pos = getRespawnPosition(playerData);
        ResourceKey<Level> dim = getRespawnDimension(playerData);
        float angle = getRespawnAngle(playerData);
        var respawnLevel = server.getLevel(dim);
        Vec3 actualTargetPos = null;
        if (respawnLevel != null) {
            if (!isShutdown) {
                actualTargetPos = Player.findRespawnPositionAndUseSpawnBlock(respawnLevel, pos, angle, isRespawnForced(playerData), false).orElse(null);
            } else {
                actualTargetPos = Vec3.atBottomCenterOf(pos); // Will hopefully never be needed.
            }
        }
        if (actualTargetPos == null) {
            respawnLevel = server.overworld();
            actualTargetPos = Vec3.atBottomCenterOf(respawnLevel.getSharedSpawnPos());
            angle = respawnLevel.getSharedSpawnAngle();
        }
        var finalTarget = actualTargetPos;
        var finalAngle = angle;
        var finalDim = respawnLevel.dimension();
        modifyPassengersAndRootVehicle(playerData, modifier -> {
            setPlayerDimension(playerData, finalDim);
            setPosition(playerData, finalTarget);
            setRotation(playerData, finalAngle, 0);
        });
    }

    /**
     * Runs code for all players who are not connected to the server at that point.
     * @param world The world to get players from. Only players in this world will be affected.
     * @param playerAction What to do with the player. It's a predicate to allow you to choose weather or not to save. Return true to save the player data, false to not save.
     */
    public static void forAllDisconnectedPlayers(ServerLevel world, Predicate<CompoundTag> playerAction) {
        forAllDisconnectedPlayers(world.getServer(), world.dimension(), playerAction);
    }

    /**
     * Runs code for all players who are not connected to the server at that point.
     * @param server The server instance.
     * @param dimension The dimension to get players from. Only players in this dimension will be affected.
     * @param playerAction What to do with the player. It's a predicate to allow you to choose weather or not to save. Return true to save the player data, false to not save.
     */
    public static void forAllDisconnectedPlayers(MinecraftServer server, ResourceKey<Level> dimension, Predicate<CompoundTag> playerAction) {
        Predicate<CompoundTag> isInCorrectWorld = player -> dimension == getPlayerDimension(player);
        forAllDisconnectedPlayers(server, isInCorrectWorld.and(playerAction));
    }

}
