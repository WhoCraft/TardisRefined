package whocraft.tardis_refined.common.dimension;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.datafix.DataFixTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.saveddata.SavedData;
import whocraft.tardis_refined.TardisRefined;
import whocraft.tardis_refined.common.util.TRTeleporter;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

// we can't teleport players from onBlockActivated as there are assumptions
// in the right click processing that assume a player's world does not change
// so what we'll do is schedule a teleport to occur at the end of the world tick
public class TardisTeleportData extends SavedData {
    public static final String DATA_KEY = new ResourceLocation(TardisRefined.MODID, "tardis_teleports").toString();

    private List<TeleportEntry> queuedTeleports = new ArrayList<>();

    public static TardisTeleportData getOrCreate(ServerLevel level) {
        return level.getDataStorage().computeIfAbsent(new Factory<>(TardisTeleportData::new, TardisTeleportData::load, DataFixTypes.SAVED_DATA_MAP_DATA), DATA_KEY);
    }

    public static TardisTeleportData load(CompoundTag nbt) {
        // NOOP, data is transient
        return TardisTeleportData.create();
    }

    public static TardisTeleportData create() {
        return new TardisTeleportData();
    }

    protected TardisTeleportData() {
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
        TardisTeleportData eventData = getOrCreate(level);

        // handle teleports
        List<TeleportEntry> teleports = eventData.queuedTeleports;

        Set<Entity> teleportedEntities = new HashSet<>();

        if (teleports.size() != 0){
            for (TeleportEntry entry : teleports) {

                if (!entry.getIsCurrentTeleporting()) {
                    entry.setIsCurrentTeleporting(true);

                    Entity entity = entry.getEntity();

                    if (teleportedEntities.contains(entity)) continue;

                    @Nullable ServerLevel targetWorld = server.getLevel(entry.getDestination());
                    if (entity != null && targetWorld != null && entity.level() == level) {
                        if (TRTeleporter.performTeleport(entity, targetWorld, entry.getX(), entry.getY(), entry.getZ(), entry.getyRot(), entry.getxRot(), teleportedEntities)) {
                            teleportedEntities.add(entity);
                        }
                    }
                }
            }

            // remove all entities that were teleported from the queue
            teleports.removeIf(teleportEntry -> teleportedEntities.contains(teleportEntry.getEntity()));

            eventData.queuedTeleports = teleports;
        }


    }

    public void scheduleEntityTeleport(Entity entity, ResourceKey<Level> destination, double x, double y, double z, float yRot, float xRot) {
        if(entity != null && !entity.level().isClientSide() && !isEntityQueuedToTeleportAlready(entity)) {
            this.queuedTeleports.add(new TeleportEntry(entity, destination, x, y, z, yRot, xRot));
        }

    }

    public boolean isEntityQueuedToTeleportAlready(Entity entity) {
        return this.queuedTeleports.stream().anyMatch(entry -> entry.getEntity().equals(entity));
    }

    @Override
    public CompoundTag save(CompoundTag compound) {
        return compound;
    }

    private static final class TeleportEntry{

        private final Entity entity;
        private final ResourceKey<Level> destination;

        private final double x, y, z;
        private final float yRot, xRot;
        private boolean isCurrentTeleporting = false;

        public TeleportEntry(Entity entity, ResourceKey<Level> destination, double x, double y, double z, float yRot, float xRot) {
            this.entity = entity;
            this.destination = destination;
            this.x = x;
            this.y = y;
            this.z = z;
            this.yRot = yRot;
            this.xRot = xRot;
        }

        public Entity getEntity() {
            return this.entity;
        }

        public ResourceKey<Level> getDestination() {
            return this.destination;
        }

        public double getX() {
            return x;
        }

        public double getY() {
            return y;
        }

        public double getZ() {
            return z;
        }

        public float getxRot() {
            return xRot;
        }

        public float getyRot() {
            return yRot;
        }

        public boolean getIsCurrentTeleporting() {
            return isCurrentTeleporting;
        }

        public void setIsCurrentTeleporting(boolean isCurrentTeleporting) {
            this.isCurrentTeleporting = isCurrentTeleporting;
        }
    }
}