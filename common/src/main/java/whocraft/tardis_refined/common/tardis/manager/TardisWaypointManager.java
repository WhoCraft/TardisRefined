package whocraft.tardis_refined.common.tardis.manager;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import whocraft.tardis_refined.common.capability.tardis.TardisLevelOperator;
import whocraft.tardis_refined.common.tardis.TardisNavLocation;
import whocraft.tardis_refined.common.tardis.TardisWaypoint;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class TardisWaypointManager extends BaseHandler {

    private final TardisLevelOperator operator;
    private List<TardisWaypoint> waypoints = new ArrayList<>();

    public TardisWaypointManager(TardisLevelOperator tardisLevelOperator) {
        this.operator = tardisLevelOperator;
    }

    public void addWaypoint(TardisNavLocation tardisNavLocation, String name) {
        waypoints.add(new TardisWaypoint(tardisNavLocation.copy().generateSublevelData().setName(name)));
    }

    public void editWaypoint(TardisWaypoint waypoint) {
        deleteWaypoint(waypoint.getId());
        waypoints.add(waypoint);
        waypoint.getLocation().generateSublevelData();
    }

    public void deleteWaypoint(UUID id) {
        waypoints.removeIf(x -> {
            if (x.getId().equals(id)) {
                x.getLocation().removeSublevelData();
                return true;
            }
            return false;
        });
    }

    public List<TardisWaypoint> getWaypoints() {
        return this.waypoints;
    }

    public TardisWaypoint getWaypointById(UUID id) {
        Optional<TardisWaypoint> waypoint = waypoints.stream().filter(x -> x.getId().equals(id)).findFirst();
        return waypoint.orElse(null);
    }

    @Override
    public CompoundTag saveData(CompoundTag compoundTag) {
        ListTag waypointsList = new ListTag();
        for (TardisWaypoint waypoint : waypoints) {
            CompoundTag waypointTag = waypoint.serialise();
            waypointsList.add(waypointTag);
        }
        compoundTag.put("Waypoints", waypointsList);
        return compoundTag;
    }

    @Override
    public void loadData(CompoundTag tag) {
        waypoints.clear();
        ListTag waypointsList = tag.getList("Waypoints", 10);
        for (int i = 0; i < waypointsList.size(); i++) {
            CompoundTag locationTag = waypointsList.getCompound(i);
            TardisWaypoint waypoint = TardisWaypoint.deserialise(locationTag);
            waypoints.add(waypoint);
        }
    }
}
