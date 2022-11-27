package whocraft.tardis_refined.common.dimension;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.phys.Vec3;
import whocraft.tardis_refined.TardisRefined;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

// we can't teleport players from onBlockActivated as there are assumptions
// in the right click processing that assume a player's world does not change
// so what we'll do is schedule a teleport to occur at the end of the world tick
public class DelayedTeleportData extends SavedData {
    public static final String DATA_KEY = TardisRefined.MODID + ":delayed_events";

    private List<TeleportEntry> delayedTeleports = new ArrayList<>();

    public static DelayedTeleportData getOrCreate(ServerLevel level) {
        return level.getDataStorage().computeIfAbsent(DelayedTeleportData::load, DelayedTeleportData::create, DATA_KEY);
    }

    public static DelayedTeleportData load(CompoundTag nbt) {
        // NOOP, data is transient
        return DelayedTeleportData.create();
    }

    public static DelayedTeleportData create() {
        return new DelayedTeleportData();
    }

    protected DelayedTeleportData() {
    }

    /**
     * This is to be called from the world tick event, if the world being ticked
     * is a ServerWorld and if the tick phase is the end of the world tick.
     * <p>
     * Does *not* create dynamic worlds that don't already exist,
     * So dynamic worlds should be created by the thing that schedules the tick, if possible
     *
     * @param level The world that is being ticked and contains a data instance
     */
    public static void tick(ServerLevel level) {
        MinecraftServer server = level.getServer();
        DelayedTeleportData eventData = getOrCreate(level);

        // handle teleports
        List<TeleportEntry> teleports = eventData.delayedTeleports;
        eventData.delayedTeleports = new ArrayList<>();
        for (TeleportEntry entry : teleports) {
            @Nullable ServerPlayer player = server.getPlayerList().getPlayer(entry.playerUUID);
            @Nullable ServerLevel targetWorld = server.getLevel(entry.targetLevel);
            if (player != null && targetWorld != null && player.level == level) {
                player.teleportTo(targetWorld, entry.targetVec.x(), entry.targetVec.y(), entry.targetVec.z(), entry.dir, 0);
            }
        }
    }

    public void schedulePlayerTeleport(Player player, ResourceKey<Level> destination, Vec3 targetVec, int dir) {
        this.delayedTeleports.add(new TeleportEntry(player.getGameProfile().getId(), destination, targetVec, dir));
    }

    @Override
    public CompoundTag save(CompoundTag compound) {
        return compound;
    }

    private record TeleportEntry(UUID playerUUID, ResourceKey<Level> targetLevel, Vec3 targetVec, int dir) {
    }
}