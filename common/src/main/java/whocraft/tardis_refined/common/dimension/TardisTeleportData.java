package whocraft.tardis_refined.common.dimension;

import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import whocraft.tardis_refined.common.util.Platform;
import whocraft.tardis_refined.common.util.TRTeleporter;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

// we can't teleport players from onBlockActivated as there are assumptions
// in the right click processing that assume a player's world does not change
// so what we'll do is schedule a teleport to occur at the end of the world tick
public class TardisTeleportData {

    private static List<TeleportEntry> queuedTeleports = new ArrayList<>();

    private static TardisTeleportData INSTANCE = new TardisTeleportData();

    public List<TeleportEntry> getQueuedTeleports() {
        return queuedTeleports;
    }

    /**
     * This is to be called from the world tick event, if the world being ticked
     * is a ServerWorld and if the tick phase is the end of the world tick.
     * <p>
     * Does *not* create dynamic worlds that don't already exist,
     * So dynamic worlds should be created by the thing that schedules the tick, if possible
     *
     */
    public static void tick() {
        TardisTeleportData eventData = TardisTeleportData.INSTANCE;
        MinecraftServer server = Platform.getServer();

        // handle teleports
        List<TeleportEntry> teleports = eventData.getQueuedTeleports();

        Set<Entity> teleportedEntities = new HashSet<>();

        if (!teleports.isEmpty()) {
            for (TeleportEntry entry : teleports) {

                if (!entry.getIsCurrentTeleporting() && !entry.getSuccessfulTeleport()) {
                    entry.setIsCurrentTeleporting(true);

                    Entity entity = entry.getEntity();

                    if (teleportedEntities.contains(entity)) continue;

                    @Nullable ServerLevel targetWorld = server.getLevel(entry.getDestination());
                    if (entity != null && targetWorld != null) {
                        if (TRTeleporter.fullTeleport(entity, targetWorld, entry.getX(), entry.getY(), entry.getZ(), entry.getyRot(), entry.getxRot(), teleportedEntities)) {
                            teleportedEntities.add(entity);
                            entry.setSuccessfulTeleport(true);
                        }
                        else {
                            entry.setSuccessfulTeleport(false);
                        }
                    }
                }
            }

            // remove all entities that were successfully teleported. Also remove failed teleports to prevent a backlog from building up
            teleports.removeIf(teleportEntry -> teleportedEntities.contains(teleportEntry.getEntity()) || !teleportEntry.getSuccessfulTeleport());

            queuedTeleports = teleports;
        }


    }

    public static void scheduleEntityTeleport(Entity entity, ResourceKey<Level> destination, double x, double y, double z, float yRot, float xRot) {
        if(entity != null && !entity.level().isClientSide() && !isEntityQueuedToTeleportAlready(entity)) {
            queuedTeleports.add(new TeleportEntry(entity, destination, x, y, z, yRot, xRot));
        }

    }

    public static boolean isEntityQueuedToTeleportAlready(Entity entity) {
        return queuedTeleports.stream().anyMatch(entry -> entry.getEntity().equals(entity));
    }

    private static final class TeleportEntry{

        private final Entity entity;
        private final ResourceKey<Level> destination;

        private final double x, y, z;
        private final float yRot, xRot;
        private boolean isCurrentTeleporting = false;

        private boolean successfulTeleport = false;

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

        public boolean getSuccessfulTeleport() {
            return successfulTeleport;
        }

        public void setSuccessfulTeleport(boolean successfulTeleport) {
            this.isCurrentTeleporting = successfulTeleport;
        }
    }
}